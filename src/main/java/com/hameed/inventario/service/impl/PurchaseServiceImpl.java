package com.hameed.inventario.service.impl;

import com.hameed.inventario.enums.PurchaseStatus;
import com.hameed.inventario.exception.RecordCannotBeModifiedException;
import com.hameed.inventario.exception.ResourceNotFoundException;
import com.hameed.inventario.mapper.POLineMapper;
import com.hameed.inventario.mapper.PurchaseMapper;
import com.hameed.inventario.model.dto.request.POLineRequestDTO;
import com.hameed.inventario.model.dto.request.PurchaseRequestDTO;
import com.hameed.inventario.model.dto.response.PurchaseResponseDTO;
import com.hameed.inventario.model.dto.response.POLineResponseDTO;
import com.hameed.inventario.model.dto.request.ReceiveOrderDTO;
import com.hameed.inventario.model.dto.request.ReceivedLineDTO;
import com.hameed.inventario.model.entity.*;
import com.hameed.inventario.repository.PurchaseRepository;
import com.hameed.inventario.service.InventoryStockService;
import com.hameed.inventario.service.ProductService;
import com.hameed.inventario.service.PurchaseService;
import com.hameed.inventario.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final SupplierService supplierService;
    private final ProductService productService;
    private final InventoryStockService inventoryStockService;
    private final PurchaseMapper purchaseMapper;
    private final POLineMapper poLineMapper;

    @Autowired
    public PurchaseServiceImpl(PurchaseRepository purchaseRepository,
                               ProductService productService,
                               SupplierService supplierService,
                               InventoryStockService inventoryStockService,
                               PurchaseMapper purchaseMapper,
                               POLineMapper poLineMapper) {
        this.purchaseRepository = purchaseRepository;
        this.productService = productService;
        this.supplierService = supplierService;
        this.inventoryStockService = inventoryStockService;
        this.purchaseMapper = purchaseMapper;
        this.poLineMapper = poLineMapper;
    }

    @Override
    @Transactional
    public PurchaseResponseDTO addPurchaseOrder(PurchaseRequestDTO purchaseRequestDTO) {
        // Map the PurchaseCreateDTO to PurchaseOrder object
        PurchaseOrder purchaseOrder = purchaseMapper.PurchaseRequestDTOToPurchaseOrder(purchaseRequestDTO);

        // getting the supplier and setting it
        Supplier supplier = supplierService.getSupplierEntityById(purchaseRequestDTO.getSupplierId());
        purchaseOrder.setSupplier(supplier);

        // set the purchase status with pending
        purchaseOrder.setPurchaseStatus(PurchaseStatus.PENDING.toString());

        // get lines from DTO
        List<POLineRequestDTO> poLineRequestDTOS = purchaseRequestDTO.getPoLineRequestDTOS();
        List<PurchaseLine> purchaseLines =  poLineRequestDTOS.stream().map(
                poLineRequestDTO -> {
                    PurchaseLine purchaseLine = poLineMapper.poLineRequestDTOToPurchaseLine(poLineRequestDTO);
                    Product product = productService.getProductEntityById(poLineRequestDTO.getProductId());
                    purchaseLine.setProduct(product);
                    return purchaseLine;
                }
        ).toList();
        purchaseLines.forEach(purchaseOrder::addPurchaseLine);

        // create purchase number using date part and sequential part
        String purchaseNumber = this.generatePONumber();
        purchaseOrder.setPurchaseNumber(purchaseNumber);

        //save
        PurchaseOrder resultPurchaseOrder = purchaseRepository.save(purchaseOrder);

        // return Purchase order response
        return purchaseMapper.purchaseOrderToPurchaseResponseDTO(resultPurchaseOrder);
    }

    @Override
    @Transactional
    // this update should be restricted to very specific users
    public PurchaseResponseDTO updatePurchase(Long purchaseId, PurchaseRequestDTO purchaseRequestDTO) {

        Optional<PurchaseOrder> optionalPurchaseOrder = purchaseRepository.findById(purchaseId);
        if(optionalPurchaseOrder.isPresent()) {
            PurchaseOrder purchaseOrder = optionalPurchaseOrder.get();
            // service-validation
            if (!purchaseOrder.getPurchaseStatus().equals(PurchaseStatus.PENDING.toString())) {
                throw new RecordCannotBeModifiedException("Purchase Order " + purchaseOrder.getId() + " cannot be modified because it is already received");
            }
            // map fields of dto to purchaseOrder
            // getting the supplier and setting it
            Supplier supplier = supplierService.getSupplierEntityById(purchaseRequestDTO.getSupplierId());
            purchaseOrder.setSupplier(supplier);
            purchaseOrder.setNotes(purchaseRequestDTO.getNotes());


            // get lines from DTO and add it to po
            List<POLineRequestDTO> poLineRequestDTOS = purchaseRequestDTO.getPoLineRequestDTOS();
            List<PurchaseLine> purchaseLines =  poLineRequestDTOS.stream().map(
                    poLineRequestDTO -> {
                        PurchaseLine purchaseLine = poLineMapper.poLineRequestDTOToPurchaseLine(poLineRequestDTO);
                        Product product = productService.getProductEntityById(poLineRequestDTO.getProductId());
                        purchaseLine.setProduct(product);
                        return purchaseLine;
                    }
            ).toList();
            purchaseLines.forEach(purchaseOrder::updatePurchaseLine);

            // save purchase order
            PurchaseOrder resultPurchaseOrder = purchaseRepository.save(purchaseOrder);

            // return the updated DTO
            return purchaseMapper.purchaseOrderToPurchaseResponseDTO(resultPurchaseOrder);
        } else {
            throw new ResourceNotFoundException("Purchase order with this Id: " + purchaseId + " could not be found");
        }
    }

    @Override
    @Transactional
    public void removePurchase(Long purchaseId) {
        purchaseRepository.findById(purchaseId).ifPresentOrElse(
                purchaseOrder -> {
                    // service-validation
                    if (!purchaseOrder.getPurchaseStatus().equals(PurchaseStatus.PENDING.toString())) {
                        throw new RecordCannotBeModifiedException("Purchase Order " + purchaseOrder.getId() + " cannot be modified because it is already received");
                    }
                    purchaseOrder.setSupplier(null);
                    purchaseRepository.delete(purchaseOrder);
                },
                () -> {
                    throw new ResourceNotFoundException("Purchase order with this Id: " + purchaseId + " could not be found");
                }
        );
    }

    @Override
    public Page<PurchaseResponseDTO> getAllPurchases(Pageable pageable) {
        Page<PurchaseOrder> pagePurchaseOrders = purchaseRepository.findAll(pageable);
        return pagePurchaseOrders.map(purchaseMapper::purchaseOrderToPurchaseResponseDTO);
    }

    @Override
    public PurchaseResponseDTO getPurchaseById(Long purchaseId) {
        PurchaseOrder purchaseOrder = this.getPurchaseEntityById(purchaseId);
        return purchaseMapper.purchaseOrderToPurchaseResponseDTO(purchaseOrder);
    }

    @Override
    public PurchaseOrder getPurchaseEntityById(Long purchaseId) {
        return purchaseRepository.findById(purchaseId).orElseThrow(() -> new ResourceNotFoundException("Purchase Order with this Id: " + purchaseId + " could not be found"));
    }

    @Override
    @Transactional
    public PurchaseResponseDTO receiveOrder(Long purchaseOrderId, ReceiveOrderDTO receiveOrderDTO) {
//        Long purchaseOrderId = receiveOrderDTO.getPurchaseOrderId();
        Optional<PurchaseOrder> optionalPurchaseOrder = purchaseRepository.findById(purchaseOrderId);
        if (optionalPurchaseOrder.isPresent()) {

            PurchaseOrder purchaseOrder = optionalPurchaseOrder.get();
            // service-validation
            if (!purchaseOrder.getPurchaseStatus().equals(PurchaseStatus.PENDING.toString())) {
                throw new RecordCannotBeModifiedException("Purchase Order " + purchaseOrder.getId() + " cannot be modified because it is already received");
            }
            // set the discount for the received dto to the purchase order
            if (receiveOrderDTO.getDiscount() != null) {
                purchaseOrder.setDiscount(receiveOrderDTO.getDiscount());
            }
//            purchaseOrder.getPurchaseLines().forEach(purchaseLine -> {
//                Optional<ReceivedLineDTO> optionalReceivedLineDTO = receiveOrderDTO.getReceivedLines().stream().filter(receivedLineDTO -> Objects.equals(receivedLineDTO.getPurchaseLineId(), purchaseLine.getId())).findFirst();
//                if (optionalReceivedLineDTO.isPresent()) {
//                    ReceivedLineDTO receivedLine = optionalReceivedLineDTO.get();
//                    purchaseLine.setReceivedQuantity(receivedLine.getReceivedQuantity());
//                    purchaseLine.setUnitPrice(receivedLine.getUnitPrice());
//                    // increase stock of the product with the newly received quantity
//                    inventoryStockService.increaseStock(purchaseLine.getProduct().getId(), purchaseLine.getReceivedQuantity());
//                    // add the supplier to the list of suppliers of each product in the PO
//                    purchaseLine.getProduct().addSupplier(purchaseOrder.getSupplier());
//                    // update the current cost of the product based on the unit price of the received line
//                    purchaseLine.getProduct().setCurrentCost(receivedLine.getUnitPrice());
//                } else {
//                    throw new ResourceNotFoundException("Received Line with this Id: " + purchaseLine.getId() + " could not be found");
//                }
//            });

            receiveOrderDTO.getReceivedLines().forEach(receivedLine -> {
                Optional<PurchaseLine> optionalPurchaseLine = purchaseOrder.getPurchaseLines().stream().filter(purchaseLine -> Objects.equals(purchaseLine.getId(), receivedLine.getPurchaseLineId())).findFirst();
                if (optionalPurchaseLine.isPresent()) {
                    PurchaseLine purchaseLine = optionalPurchaseLine.get();
                    purchaseLine.setReceivedQuantity(receivedLine.getReceivedQuantity());
                    purchaseLine.setUnitPrice(receivedLine.getUnitPrice());
                    // increase stock of the product with the newly received quantity
                    inventoryStockService.increaseStock(purchaseLine.getProduct().getId(), purchaseLine.getReceivedQuantity());
                    // add the supplier to the list of suppliers of each product in the PO
                    purchaseLine.getProduct().addSupplier(purchaseOrder.getSupplier());
                    // update the current cost of the product based on the unit price of the received line
                    purchaseLine.getProduct().setCurrentCost(receivedLine.getUnitPrice());
                } else {
                    throw new ResourceNotFoundException("Received Line with this purchase Line Id: " + receivedLine.getPurchaseLineId() + " could not be found");
                }
            });


            // change status of the PO based on the received quantity against the ordered quantity
            int totalRequested = purchaseOrder.getPurchaseLines().stream().map(PurchaseLine::getRequestedQuantity).reduce(0, Integer::sum);
            int totalReceived = purchaseOrder.getPurchaseLines().stream().map(PurchaseLine::getReceivedQuantity).reduce(0, Integer::sum);

            if (totalRequested > totalReceived) {
                purchaseOrder.setPurchaseStatus(PurchaseStatus.PARTIALLY_RECEIVED.toString());
            } else {
                purchaseOrder.setPurchaseStatus(PurchaseStatus.RECEIVED.toString());
            }

            // save to repository
            PurchaseOrder resultPurchaseOrder = purchaseRepository.save(purchaseOrder);
            resultPurchaseOrder.updateTotalAmount();
            // return the received purchase order
            return purchaseMapper.purchaseOrderToPurchaseResponseDTO(resultPurchaseOrder);
        } else {
            throw new ResourceNotFoundException("Purchase Order with this Purchase Order Id: " + purchaseOrderId + " could not be found");
        }
    }

    private String generatePONumber() {
        // Get the current date
        String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        // Fetch the latest PO number from the database (assuming POs are sequential)
        Optional<PurchaseOrder> lastPO = purchaseRepository.findFirstByOrderByIdDesc();

        String sequencePart;
        if (lastPO.isPresent()) {
            String lastPONumber = lastPO.get().getPurchaseNumber();
            // Extract the numeric sequence and increment it
            String lastSequence = lastPONumber.split("-")[1];
            int newSequence = Integer.parseInt(lastSequence) + 1;
            sequencePart = String.format("%04d", newSequence); // Keep 4 digits
        } else {
            sequencePart = "0001"; // Start with 0001 if no POs exist yet
        }

        // Combine the date and sequence to form the PO number
        // Example of returned PO number: PO20251001-0001
        return "PO" + datePart + "-" + sequencePart;
    }
}
