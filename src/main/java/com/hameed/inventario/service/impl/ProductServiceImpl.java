package com.hameed.inventario.service.impl;

import com.hameed.inventario.exception.DuplicateCodeException;
import com.hameed.inventario.exception.ResourceNotFoundException;
import com.hameed.inventario.mapper.ProductMapper;
import com.hameed.inventario.model.dto.request.ProductRequestDTO;
import com.hameed.inventario.model.dto.response.ProductResponseDTO;
import com.hameed.inventario.model.entity.Category;
import com.hameed.inventario.model.entity.Product;
import com.hameed.inventario.model.entity.UnitOfMeasure;
import com.hameed.inventario.repository.ProductRepository;
import com.hameed.inventario.service.CategoryService;
import com.hameed.inventario.service.ProductService;
import com.hameed.inventario.service.UnitOfMeasureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final UnitOfMeasureService unitOfMeasureService;
    private final ProductMapper productMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryService categoryService, UnitOfMeasureService unitOfMeasureService,
                              ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.unitOfMeasureService = unitOfMeasureService;
        this.productMapper = productMapper;
    }

    @Override
    @Transactional
    public ProductResponseDTO addProduct(ProductRequestDTO productRequestDTO) {
        // service-validation
        if (productRepository.findByProductCode(productRequestDTO.getProductCode()).isPresent()) {
            throw new DuplicateCodeException("Product code " + productRequestDTO.getProductCode() + " already exists");
        }
        // map productCreateDTO to Product
        Product product = productMapper.ProductRequestDTOToProduct(productRequestDTO);
        // calling services to get category and uom
        Category productCategory = categoryService.getCategoryEntityById(productRequestDTO.getCategoryId());
        UnitOfMeasure primaryUom = unitOfMeasureService.getUnitOfMeasureEntityById(productRequestDTO.getPrimaryUomId());
        product.setCategory(productCategory);
        product.setPrimaryUom(primaryUom);
        Product resultProduct = productRepository.save(product);
        return productMapper.productToProductResponseDTO(resultProduct);
    }

    @Override
    @Transactional
    public ProductResponseDTO updateProduct(Long productId, ProductRequestDTO productRequestDTO) {

        Optional<Product> optionalProduct = productRepository.findById(productId);
        if(optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            // map fields of dto to product
            product.setProductName(productRequestDTO.getProductName());
            product.setProductCode(productRequestDTO.getProductCode());
            product.setDescription(productRequestDTO.getDescription());
            product.setBarcode(productRequestDTO.getBarcode());
            product.setCurrentPrice(productRequestDTO.getCurrentPrice());
            product.setCurrentCost(productRequestDTO.getCurrentCost());
            product.setQuantity(productRequestDTO.getQuantity());
            product.setImageUrl(productRequestDTO.getImageUrl());
            // calling services to get category and uom
            Category productCategory = categoryService.getCategoryEntityById(productRequestDTO.getCategoryId());
            UnitOfMeasure primaryUom = unitOfMeasureService.getUnitOfMeasureEntityById(productRequestDTO.getPrimaryUomId());
            product.setCategory(productCategory);
            product.setPrimaryUom(primaryUom);

            // save
            Product resultProduct = productRepository.save(product);

            // return the updated DTO
            return productMapper.productToProductResponseDTO(resultProduct);
        } else {
            throw new ResourceNotFoundException("Product with this Id: " + productId + " could not be found");
        }
    }

    @Override
    @Transactional
    public void removeProduct(Long productId) {
        productRepository.findById(productId).ifPresentOrElse(
                product -> {
                    product.setCategory(null);
                    product.setPrimaryUom(null);
                    product.getSuppliers().forEach(supplier -> supplier.removeProduct(product));
                    productRepository.delete(product);
                },
                () -> {
                    throw new ResourceNotFoundException("Product with this Id: " + productId + " could not be found");
                }
        );
    }

    @Override
    public ProductResponseDTO getProductById(Long productId) {
        Product product = getProductEntityById(productId);
        return productMapper.productToProductResponseDTO(product);
    }

    @Override
    public Page<ProductResponseDTO> getAllProducts(Pageable pageable) {
        Page<Product> pageProducts = productRepository.findAll(pageable);
        return pageProducts.map(productMapper::productToProductResponseDTO);
    }

    @Override
    public Product getProductEntityById(Long productId) {
        return productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product with this Id: " + productId + " could not be found"));
    }

    @Override
    public void saveProduct(Product product) {
        productRepository.save(product);
    }
}
