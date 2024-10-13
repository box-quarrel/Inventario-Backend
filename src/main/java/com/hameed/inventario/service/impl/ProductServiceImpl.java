package com.hameed.inventario.service.impl;

import com.hameed.inventario.exception.DuplicateCodeException;
import com.hameed.inventario.exception.ResourceNotFoundException;
import com.hameed.inventario.mapper.ProductMapper;
import com.hameed.inventario.model.dto.create.ProductCreateDTO;
import com.hameed.inventario.model.dto.update.ProductDTO;
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
    public ProductDTO addProduct(ProductCreateDTO productCreateDTO) {
        // map productCreateDTO to Product
        Product product = productMapper.productCreateDTOToProduct(productCreateDTO);
        // service-validation
        if (productRepository.findByProductCode(product.getProductCode()).isPresent()) {
            throw new DuplicateCodeException("Product code " + product.getProductCode() + " already exists");
        }
        // calling services to get category and uom
        Category productCategory = categoryService.getCategoryEntityById(productCreateDTO.getCategoryId());
        UnitOfMeasure primaryUom = unitOfMeasureService.getUnitOfMeasureEntityById(productCreateDTO.getPrimaryUomId());
        product.setCategory(productCategory);
        product.setPrimaryUom(primaryUom);
        Product resultProduct = productRepository.save(product);
        return productMapper.productToProductDTO(resultProduct);
    }

    @Override
    public ProductDTO updateProduct (ProductDTO productDTO) {
        Long productId = productDTO.getId();
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if(optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            // map fields of dto to product
            product.setProductName(productDTO.getProductName());
            product.setProductCode(productDTO.getProductCode());
            product.setDescription(productDTO.getDescription());
            product.setBarcode(productDTO.getBarcode());
            product.setCurrentPrice(productDTO.getCurrentPrice());
            product.setCurrentCost(productDTO.getCurrentCost());
            product.setQuantity(productDTO.getQuantity());
            product.setImageUrl(productDTO.getImageUrl());
            // calling services to get category and uom
            Category productCategory = categoryService.getCategoryEntityById(productDTO.getCategory().getId());
            UnitOfMeasure primaryUom = unitOfMeasureService.getUnitOfMeasureEntityById(productDTO.getPrimaryUom().getId());
            product.setCategory(productCategory);
            product.setPrimaryUom(primaryUom);

            // save
            Product resultProduct = productRepository.save(product);

            // return the updated DTO
            return productMapper.productToProductDTO(resultProduct);
        } else {
            throw new ResourceNotFoundException("Product with this Id: " + productId + " could not be found");
        }
    }

    @Override
    public void removeProduct(Long productId) {
        productRepository.findById(productId).ifPresentOrElse(
                productRepository::delete,
                () -> {
                    throw new ResourceNotFoundException("Product with this Id: " + productId + " could not be found");
                }
        );
    }

    @Override
    public ProductDTO getProductById(Long productId) {
        Product product = getProductEntityById(productId);
        return productMapper.productToProductDTO(product);
    }

    @Override
    public Page<ProductDTO> getAllProducts(Pageable pageable) {
        Page<Product> pageProducts = productRepository.findAll(pageable);
        return pageProducts.map(productMapper::productToProductDTO);
    }

    @Override
    public Product getProductEntityById(Long productId) {
        return productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product with this Id: " + productId + " could not be found"));
    }
}
