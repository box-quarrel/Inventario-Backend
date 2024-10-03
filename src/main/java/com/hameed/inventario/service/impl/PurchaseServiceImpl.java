package com.hameed.inventario.service.impl;

import com.hameed.inventario.enums.PurchaseStatus;
import com.hameed.inventario.exception.ResourceNotFoundException;
import com.hameed.inventario.mapper.POLineMapper;
import com.hameed.inventario.mapper.PurchaseMapper;
import com.hameed.inventario.model.dto.POLineCreateDTO;
import com.hameed.inventario.model.dto.PurchaseCreateDTO;
import com.hameed.inventario.model.dto.PurchaseDTO;
import com.hameed.inventario.model.entity.*;
import com.hameed.inventario.repository.PurchaseRepository;
import com.hameed.inventario.service.InventoryStockService;
import com.hameed.inventario.service.PurchaseService;
import com.hameed.inventario.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final SupplierService supplierService;
    private final InventoryStockService inventoryStockService;

    @Autowired
    public PurchaseServiceImpl(PurchaseRepository purchaseRepository, SupplierService supplierService, InventoryStockService inventoryStockService) {
        this.purchaseRepository = purchaseRepository;
        this.supplierService = supplierService;
        this.inventoryStockService = inventoryStockService;
    }

    @Override
    public String addPurchaseOrder(PurchaseCreateDTO purchaseCreateDTO) {
        // Map the PurchaseCreateDTO to PurchaseOrder object
        PurchaseOrder purchaseOrder = PurchaseMapper.INSTANCE.purchaseCreateDTOToPurchaseOrder(purchaseCreateDTO);

        // getting the supplier and setting it
        Supplier supplier = supplierService.getSupplierEntityById(purchaseCreateDTO.getSupplierId());
        purchaseOrder.setSupplier(supplier);

        // set the purchase status with pending
        purchaseOrder.setPurchaseStatus(PurchaseStatus.PENDING.toString());

        // get lines from DTO
        List<POLineCreateDTO> poLineCreateDTOS = purchaseCreateDTO.getPoLineCreateDTOS();
        List<PurchaseLine> purchaseLines =  poLineCreateDTOS.stream().map(POLineMapper.INSTANCE::poLineCreateDTOToPurchaseLine).toList();
        purchaseOrder.setPurchaseLines(purchaseLines);

        // create purchase number using date part and sequential part
        String purchaseNumber = this.generatePONumber();
        purchaseOrder.setPurchaseNumber(purchaseNumber);

        //save
        purchaseRepository.save(purchaseOrder);

        // return PO number
        return purchaseNumber;
    }

    @Override
    // this update should be restricted to very specific users
    public void updatePurchase(Long purchaseId, PurchaseCreateDTO purchaseCreateDTO) {
        purchaseRepository.findById(purchaseId).ifPresentOrElse(
                purchaseOrder -> {
                    purchaseOrder.setDiscount(purchaseCreateDTO.getDiscount());
                    purchaseOrder.setTotalAmount(purchaseCreateDTO.getTotalAmount());
                    // getting the supplier and setting it
                    Supplier supplier = supplierService.getSupplierEntityById(purchaseCreateDTO.getSupplierId());
                    purchaseOrder.setSupplier(supplier);

                    // get lines from DTO and add it to po
                    List<POLineCreateDTO> poLineCreateDTOS = purchaseCreateDTO.getPoLineCreateDTOS();
                    List<PurchaseLine> purchaseLines =  poLineCreateDTOS.stream().map(POLineMapper.INSTANCE::poLineCreateDTOToPurchaseLine).toList();
                    purchaseOrder.setPurchaseLines(purchaseLines);

                    // save
                    purchaseRepository.save(purchaseOrder);
                },
                () -> {
                    throw new ResourceNotFoundException("Purchase Order with this Id: " + purchaseId + " could not be found");
                }
        );
    }

    @Override
    public void removePurchase(Long purchaseId) {
        purchaseRepository.deleteById(purchaseId);
    }

    @Override
    public Page<PurchaseDTO> getAllPurchases(Pageable pageable) {
        Page<PurchaseOrder> pagePurchaseOrders = purchaseRepository.findAll(pageable);
        return pagePurchaseOrders.map(PurchaseMapper.INSTANCE::purchaseOrderToPurchaseDTO);
    }

    @Override
    public PurchaseDTO getPurchaseById(Long purchaseId) {
        PurchaseOrder purchaseOrder = this.getPurchaseEntityById(purchaseId);
        return PurchaseMapper.INSTANCE.purchaseOrderToPurchaseDTO(purchaseOrder);
    }

    @Override
    public PurchaseOrder getPurchaseEntityById(Long purchaseId) {
        return purchaseRepository.findById(purchaseId).orElseThrow(() -> new ResourceNotFoundException("Purchase Order with this Id: " + purchaseId + " could not be found"));
    }

    @Override
    public void receiveOrder(String purchaseNumber) {
        purchaseRepository.findByPurchaseNumber(purchaseNumber).ifPresentOrElse(
                purchaseOrder -> {
                    // change status of the PO
                    int totalRequested = purchaseOrder.getPurchaseLines().stream().map(PurchaseLine::getRequestedQuantity).reduce(0, Integer::sum);
                    int totalReceived = purchaseOrder.getPurchaseLines().stream().map(PurchaseLine::getReceivedQuantity).reduce(0, Integer::sum);

                    if (totalRequested > totalReceived) {
                        purchaseOrder.setPurchaseStatus(PurchaseStatus.PARTIALLY_RECEIVED.toString());
                    } else {
                        purchaseOrder.setPurchaseStatus(PurchaseStatus.RECEIVED.toString());
                    }

                    // save to repository
                    purchaseRepository.save(purchaseOrder);

                    // increase status of repository, and add the supplier to the list of suppliers of each product in the PO
                    purchaseOrder.getPurchaseLines().forEach(
                            purchaseLine -> {
                                inventoryStockService.increaseStock(purchaseLine.getProduct().getId(), purchaseLine.getReceivedQuantity());
                                purchaseLine.getProduct().addSupplier(purchaseOrder.getSupplier());
                            }
                    );

                },
                () -> {
                    throw new ResourceNotFoundException("Purchase Order with this PO Number: " + purchaseNumber + " could not be found");
                }
        );
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
