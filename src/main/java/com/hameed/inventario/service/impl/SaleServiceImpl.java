package com.hameed.inventario.service.impl;

import com.hameed.inventario.exception.ResourceNotFoundException;
import com.hameed.inventario.mapper.SaleItemMapper;
import com.hameed.inventario.mapper.SaleMapper;
import com.hameed.inventario.model.dto.create.SaleItemCreateDTO;
import com.hameed.inventario.model.dto.create.SaleCreateDTO;
import com.hameed.inventario.model.dto.response.SaleResponseDTO;
import com.hameed.inventario.model.dto.update.SaleDTO;
import com.hameed.inventario.model.dto.update.SaleItemDTO;
import com.hameed.inventario.model.entity.*;
import com.hameed.inventario.repository.SaleRepository;
import com.hameed.inventario.service.InventoryStockService;
import com.hameed.inventario.service.ProductService;
import com.hameed.inventario.service.SaleService;
import com.hameed.inventario.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final ProductService productService;
    private final CustomerService customerService;
    private final InventoryStockService inventoryStockService;
    private final SaleMapper saleMapper;
    private final SaleItemMapper saleItemMapper;

    @Autowired
    public SaleServiceImpl(SaleRepository saleRepository,
                           ProductService productService,
                           CustomerService customerService,
                           InventoryStockService inventoryStockService,
                           SaleMapper saleMapper,
                           SaleItemMapper saleItemMapper) {
        this.saleRepository = saleRepository;
        this.customerService = customerService;
        this.inventoryStockService = inventoryStockService;
        this.productService = productService;
        this.saleMapper = saleMapper;
        this.saleItemMapper = saleItemMapper;
    }

    @Override
    public SaleResponseDTO sell(SaleCreateDTO saleCreateDTO) {
        // Map the SaleCreateDTO to Sale object
        Sale sale = saleMapper.saleCreateDTOToSale(saleCreateDTO);

        // getting the customer and setting it
        Customer customer = customerService.getCustomerEntityById(saleCreateDTO.getCustomerId());
        sale.setCustomer(customer);

        // get lines from DTO
        Set<SaleItemCreateDTO> saleItemCreateDTOS = saleCreateDTO.getSaleItemCreateDTOS();
        // update inventory stock
        for (SaleItemCreateDTO saleItemCreateDTO : saleItemCreateDTOS) {
            inventoryStockService.decreaseStock(saleItemCreateDTO.getProductId(), saleItemCreateDTO.getQuantity());
        }
        // convert to entity sale items and add to sale object
        Set<SaleItem> saleItems =  saleItemCreateDTOS.stream().map(
                    saleItemCreateDTO -> {
                        SaleItem saleItem = saleItemMapper.saleItemCreateDTOToSaleItem(saleItemCreateDTO);
                        Product product = productService.getProductEntityById(saleItemCreateDTO.getProductId());
                        product.setCurrentPrice(saleItem.getUnitPrice());
                        saleItem.setProduct(product);
                        return saleItem;
                    }
                ).collect(Collectors.toSet());
        saleItems.forEach(sale::addSaleItem);

        // create sale number using date part and sequential part
        String salesNumber = this.generateSalesNumber();
        sale.setSalesNumber(salesNumber);


        //save
        saleRepository.save(sale);

        // return PO number
        return new SaleResponseDTO(salesNumber);
    }

    @Override
    public Page<SaleDTO> getAllSales(Pageable pageable) {
        Page<Sale> pageSales = saleRepository.findAll(pageable);
        return pageSales.map(saleMapper::saleToSaleDTO);
    }

    @Override
    public SaleDTO getSaleById(Long saleId) {
        Sale sale = this.getSaleEntityById(saleId);
        return saleMapper.saleToSaleDTO(sale);
    }

    @Override
    public Sale getSaleEntityById(Long saleId) {
        return saleRepository.findById(saleId).orElseThrow(() -> new ResourceNotFoundException("Sale  with this Id: " + saleId + " could not be found"));
    }

    private String generateSalesNumber() {
        // Get the current date
        String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        // Fetch the latest PO number from the database (assuming POs are sequential)
        Optional<Sale> lastSale = saleRepository.findFirstByOrderByIdDesc();

        String sequencePart;
        if (lastSale.isPresent()) {
            String lastSalesNumber = lastSale.get().getSalesNumber();
            // Extract the numeric sequence and increment it
            String lastSequence = lastSalesNumber.split("-")[1];
            int newSequence = Integer.parseInt(lastSequence) + 1;
            sequencePart = String.format("%04d", newSequence); // Keep 4 digits
        } else {
            sequencePart = "0001"; // Start with 0001 if no POs exist yet
        }

        // Combine the date and sequence to form the PO number
        // Example of returned PO number: SN20251001-0001
        return "SN" + datePart + "-" + sequencePart;
    }
}
