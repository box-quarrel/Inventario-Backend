package com.hameed.inventario.service.impl;

import com.hameed.inventario.exception.ResourceNotFoundException;
import com.hameed.inventario.mapper.ProductReturnMapper;
import com.hameed.inventario.model.dto.request.ProductReturnRequestDTO;
import com.hameed.inventario.model.dto.response.ProductReturnResponseDTO;
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
    public ProductReturnResponseDTO addProductReturn(ProductReturnRequestDTO productReturnRequestDTO) {
        ProductReturn productReturn = productReturnMapper.ProductReturnRequestDTOToProductReturn(productReturnRequestDTO);
        // calling services to get product and uom
        Product productReturnProduct = productService.getProductEntityById(productReturnRequestDTO.getProductId());
        Customer customer = customerService.getCustomerEntityById(productReturnRequestDTO.getCustomerId());
        productReturn.setProduct(productReturnProduct);
        productReturn.setCustomer(customer);
        // save
        ProductReturn resultProductReturn = productReturnRepository.save(productReturn);

        // increase the stock for the product again
        inventoryStockService.increaseStock(resultProductReturn.getId(), resultProductReturn.getQuantityReturned());

        return productReturnMapper.productReturnToProductReturnResponseDTO(resultProductReturn);
    }

    @Override
    public ProductReturnResponseDTO getProductReturnById(Long productReturnId) {
        ProductReturn productReturn = getProductReturnEntityById(productReturnId);
        return productReturnMapper.productReturnToProductReturnResponseDTO(productReturn);
    }

    @Override
    public Page<ProductReturnResponseDTO> getAllProductReturns(Pageable pageable) {
        Page<ProductReturn> pageProductReturns = productReturnRepository.findAll(pageable);
        return pageProductReturns.map(productReturnMapper::productReturnToProductReturnResponseDTO);
    }

    @Override
    public ProductReturn getProductReturnEntityById(Long productReturnId) {
        return productReturnRepository.findById(productReturnId).orElseThrow(() -> new ResourceNotFoundException("ProductReturn with this Id: " + productReturnId + " could not be found"));
    }
}
