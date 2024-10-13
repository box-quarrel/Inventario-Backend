package com.hameed.inventario.service.impl;

import com.hameed.inventario.exception.ResourceNotFoundException;
import com.hameed.inventario.mapper.ProductReturnMapper;
import com.hameed.inventario.model.dto.create.ProductReturnCreateDTO;
import com.hameed.inventario.model.dto.update.ProductReturnDTO;
import com.hameed.inventario.model.entity.Product;
import com.hameed.inventario.model.entity.ProductReturn;
import com.hameed.inventario.model.entity.Customer;
import com.hameed.inventario.repository.ProductReturnRepository;
import com.hameed.inventario.service.InventoryStockService;
import com.hameed.inventario.service.ProductService;
import com.hameed.inventario.service.ProductReturnService;
import com.hameed.inventario.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductReturnServiceImpl implements ProductReturnService {

    private final ProductReturnRepository productReturnRepository;
    private final ProductService productService;
    private final CustomerService customerService;
    private final InventoryStockService inventoryStockService;
    private final ProductReturnMapper productReturnMapper;

    @Autowired
    public ProductReturnServiceImpl(ProductReturnRepository productReturnRepository,
                                    ProductService productService,
                                    CustomerService customerService,
                                    InventoryStockService inventoryStockService,
                                    ProductReturnMapper productReturnMapper) {
        this.productReturnRepository = productReturnRepository;
        this.productService = productService;
        this.customerService = customerService;
        this.inventoryStockService = inventoryStockService;
        this.productReturnMapper = productReturnMapper;
    }

    @Override
    public ProductReturnDTO addProductReturn(ProductReturnCreateDTO productReturnCreateDTO) {
        ProductReturn productReturn = productReturnMapper.productReturnCreateDTOToProductReturn(productReturnCreateDTO);
        // calling services to get product and uom
        Product productReturnProduct = productService.getProductEntityById(productReturnCreateDTO.getProductId());
        Customer customer = customerService.getCustomerEntityById(productReturnCreateDTO.getCustomerId());
        productReturn.setProduct(productReturnProduct);
        productReturn.setCustomer(customer);
        // save
        ProductReturn resultProductReturn = productReturnRepository.save(productReturn);

        // increase the stock for the product again
        inventoryStockService.increaseStock(resultProductReturn.getId(), resultProductReturn.getQuantityReturned());

        return productReturnMapper.productReturnToProductReturnDTO(resultProductReturn);
    }

    @Override
    public ProductReturnDTO getProductReturnById(Long productReturnId) {
        ProductReturn productReturn = getProductReturnEntityById(productReturnId);
        return productReturnMapper.productReturnToProductReturnDTO(productReturn);
    }

    @Override
    public Page<ProductReturnDTO> getAllProductReturns(Pageable pageable) {
        Page<ProductReturn> pageProductReturns = productReturnRepository.findAll(pageable);
        return pageProductReturns.map(productReturnMapper::productReturnToProductReturnDTO);
    }

    @Override
    public ProductReturn getProductReturnEntityById(Long productReturnId) {
        return productReturnRepository.findById(productReturnId).orElseThrow(() -> new ResourceNotFoundException("ProductReturn with this Id: " + productReturnId + " could not be found"));
    }
}
