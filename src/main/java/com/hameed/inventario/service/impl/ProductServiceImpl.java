package com.hameed.inventario.service.impl;

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

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final UnitOfMeasureService unitOfMeasureService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryService categoryService, UnitOfMeasureService unitOfMeasureService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @Override
    public void addProduct(ProductCreateDTO productCreateDTO) {
        Product product = ProductMapper.INSTANCE.productCreateDTOToProduct(productCreateDTO);
        // calling services to get category and uom
        Category productCategory = categoryService.getCategoryEntityById(productCreateDTO.getCategoryId());
        UnitOfMeasure primaryUom = unitOfMeasureService.getUnitOfMeasureEntityById(productCreateDTO.getPrimaryUomId());
        product.setCategory(productCategory);
        product.setPrimaryUom(primaryUom);
        productRepository.save(product);
    }

    @Override
    public void updateProduct (ProductDTO productDTO) {
        Long productId = productDTO.getId();
        productRepository.findById(productId).ifPresentOrElse(
                product -> {
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
                    productRepository.save(product);
                },
                () -> {
                    throw new ResourceNotFoundException("Product with this Id: " + productId + " could not be found");
                }
        );
    }

    @Override
    public void removeProduct(Long productId) {
        productRepository.deleteById(productId);
    }

    @Override
    public ProductDTO getProductById(Long productId) {
        Product product = getProductEntityById(productId);
        return ProductMapper.INSTANCE.productToProductDTO(product);
    }

    @Override
    public Page<ProductDTO> getAllProducts(Pageable pageable) {
        Page<Product> pageProducts = productRepository.findAll(pageable);
        return pageProducts.map(ProductMapper.INSTANCE::productToProductDTO);
    }

    @Override
    public Product getProductEntityById(Long productId) {
        return productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product with this Id: " + productId + " could not be found"));
    }
}
