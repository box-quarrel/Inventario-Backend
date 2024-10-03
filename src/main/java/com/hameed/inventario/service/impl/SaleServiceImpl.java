package com.hameed.inventario.service.impl;

import com.hameed.inventario.exception.ResourceNotFoundException;
import com.hameed.inventario.mapper.SaleItemMapper;
import com.hameed.inventario.mapper.SaleMapper;
import com.hameed.inventario.model.dto.SaleItemCreateDTO;
import com.hameed.inventario.model.dto.SaleCreateDTO;
import com.hameed.inventario.model.dto.SaleDTO;
import com.hameed.inventario.model.entity.*;
import com.hameed.inventario.repository.SaleRepository;
import com.hameed.inventario.service.InventoryStockService;
import com.hameed.inventario.service.SaleService;
import com.hameed.inventario.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final CustomerService customerService;
    private final InventoryStockService inventoryStockService;

    @Autowired
    public SaleServiceImpl(SaleRepository saleRepository, CustomerService customerService, InventoryStockService inventoryStockService) {
        this.saleRepository = saleRepository;
        this.customerService = customerService;
        this.inventoryStockService = inventoryStockService;
    }

    @Override
    public String sell(SaleCreateDTO saleCreateDTO) {
        // Map the SaleCreateDTO to Sale object
        Sale sale = SaleMapper.INSTANCE.saleCreateDTOToSale(saleCreateDTO);

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
        Set<SaleItem> saleItems =  saleItemCreateDTOS.stream().map(SaleItemMapper.INSTANCE::saleItemCreateDTOToSaleItem).collect(Collectors.toSet());
        sale.setSaleItems(saleItems);

        // create sale number using date part and sequential part
        String salesNumber = this.generateSalesNumber();
        sale.setSalesNumber(salesNumber);


        //save
        saleRepository.save(sale);

        // return PO number
        return salesNumber;
    }

    @Override
    // this update should be restricted to very specific users
    public void updateSale(Long saleId, SaleCreateDTO saleCreateDTO) {
        saleRepository.findById(saleId).ifPresentOrElse(
                sale -> {
                    sale.setDiscount(saleCreateDTO.getDiscount());
                    sale.setTotalAmount(saleCreateDTO.getTotalAmount());
                    // getting the customer and setting it
                    Customer customer = customerService.getCustomerEntityById(saleCreateDTO.getCustomerId());
                    sale.setCustomer(customer);

                    // get lines from DTO and add it to po
                    Set<SaleItemCreateDTO> saleItemCreateDTOS = saleCreateDTO.getSaleItemCreateDTOS();
                    Set<SaleItem> saleItems =  saleItemCreateDTOS.stream().map(SaleItemMapper.INSTANCE::saleItemCreateDTOToSaleItem).collect(Collectors.toSet());
                    sale.setSaleItems(saleItems);

                    // save
                    saleRepository.save(sale);
                },
                () -> {
                    throw new ResourceNotFoundException("Sale  with this Id: " + saleId + " could not be found");
                }
        );
    }

    @Override
    // this update should be restricted to very specific users
    public void removeSale(Long saleId) {
        saleRepository.deleteById(saleId);
    }

    @Override
    public Page<SaleDTO> getAllSales(Pageable pageable) {
        Page<Sale> pageSales = saleRepository.findAll(pageable);
        return pageSales.map(SaleMapper.INSTANCE::saleToSaleDTO);
    }

    @Override
    public SaleDTO getSaleById(Long saleId) {
        Sale sale = this.getSaleEntityById(saleId);
        return SaleMapper.INSTANCE.saleToSaleDTO(sale);
    }

    @Override
    public Sale getSaleEntityById(Long saleId) {
        return saleRepository.findById(saleId).orElseThrow(() -> new ResourceNotFoundException("Sale  with this Id: " + saleId + " could not be found"));
    }

    private String generateSalesNumber() {
        // Get the current date
        String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        // Fetch the latest PO number from the database (assuming POs are sequential)
        Optional<Sale> lastPO = saleRepository.findFirstByOrderByIdDesc();

        String sequencePart;
        if (lastPO.isPresent()) {
            String lastPONumber = lastPO.get().getSalesNumber();
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
