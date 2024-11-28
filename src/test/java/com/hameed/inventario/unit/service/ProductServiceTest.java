//package com.hameed.inventario.unit.service;
//
//
//import com.hameed.inventario.exception.DuplicateCodeException;
//import com.hameed.inventario.exception.ResourceNotFoundException;
//import com.hameed.inventario.mapper.ProductMapper;
//import com.hameed.inventario.model.dto.request.ProductRequestDTO;
//import com.hameed.inventario.model.dto.basic.CategoryDTO;
//import com.hameed.inventario.model.dto.response.ProductResponseDTO;
//import com.hameed.inventario.model.dto.basic.UnitOfMeasureDTO;
//import com.hameed.inventario.model.entity.Category;
//import com.hameed.inventario.model.entity.Product;
//import com.hameed.inventario.model.entity.UnitOfMeasure;
//import com.hameed.inventario.repository.ProductRepository;
//import com.hameed.inventario.service.impl.CategoryServiceImpl;
//import com.hameed.inventario.service.impl.ProductServiceImpl;
//import com.hameed.inventario.service.impl.UnitOfMeasureServiceImpl;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//@ExtendWith(MockitoExtension.class)
//public class ProductServiceTest {
//
//    @Mock
//    private ProductRepository productRepository;
//    @Mock
//    private ProductMapper productMapper;
//    @Mock
//    private CategoryServiceImpl categoryService;
//    @Mock
//    private UnitOfMeasureServiceImpl unitOfMeasureService;
//
//    @InjectMocks
//    private ProductServiceImpl productService;
//
//
//
//    @Test
//    @DisplayName("Product Added Successfully")
//    public void testAddProduct_shouldReturnAddedProductAsProductDTO() {
//        //  --- Arrange ---
//        Category category = new Category();
//        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
//        CategoryDTO categoryDTO = new CategoryDTO();
//        UnitOfMeasureDTO unitOfMeasureDTO = new UnitOfMeasureDTO();
//        ProductRequestDTO productRequestDTO = createProductCreateDTO("TestingProduct 1", "TESTPROD001", "New Testing Product 1", "#1234123", 1239.9, 123.2, 10, "http://example.com/magnet.jpg", 1L, 1L);
//        Product mockMappedProduct = createProduct("TestingProduct 1", "TESTPROD001", "New Testing Product 1", "#1234123", 1239.9, 123.2, 10, "http://example.com/magnet.jpg", null, null, null);
//        Product mockResultProduct = createProduct("TestingProduct 1", "TESTPROD001", "New Testing Product 1", "#1234123", 1239.9, 123.2, 10, "http://example.com/magnet.jpg", category, unitOfMeasure, 1L);
//        ProductResponseDTO expectedProductResponseDTO = createProductDTO(1L, "TestingProduct 1", "TESTPROD001", "New Testing Product 1", "#1234123", 1239.9, 123.2, 10, "http://example.com/magnet.jpg", categoryDTO, unitOfMeasureDTO );
//        // Define behavior of the mocks
//        Mockito.when(productMapper.ProductRequestDTOToProduct(productRequestDTO)).thenReturn(mockMappedProduct);
//        Mockito.when(categoryService.getCategoryEntityById(productRequestDTO.getCategoryId())).thenReturn(category);
//        Mockito.when(unitOfMeasureService.getUnitOfMeasureEntityById(productRequestDTO.getPrimaryUomId())).thenReturn(unitOfMeasure);
//        Mockito.when(productRepository.save(mockMappedProduct)).thenReturn(mockResultProduct);
//        Mockito.when(productMapper.productToProductResponseDTO(mockResultProduct)).thenReturn(expectedProductResponseDTO);
//
//        // --- Act ---
//        ProductResponseDTO resultProductResponseDTO = productService.addProduct(productRequestDTO);
//
//        // --- Assert ---
//        assertAll(
//                () -> assertNotNull(resultProductResponseDTO, "result product DTO is null"),
//                () -> assertNotNull(resultProductResponseDTO.getId(), "result product DTO does not include an ID"),
//                () -> assertEquals(expectedProductResponseDTO, resultProductResponseDTO, "Expected product DTO is not correct") // we can use this because lombok automatically generates a hasCode and equals methods for us
//        );
//    }
//
//    @Test
//    @DisplayName("Product successfully not Added because product code already exists")
//    public void testAddProduct_shouldThrowDuplicateCodeException() {
//        //  --- Arrange ---
//        ProductRequestDTO productRequestDTO = createProductCreateDTO("TestingProduct 1", "TESTPROD001", "New Testing Product 1", "#1234123", 1239.9, 123.2, 10, "http://example.com/magnet.jpg", 1L, 1L);
//        Product mockMappedProduct = createProduct("TestingProduct 1", "TESTPROD001", "New Testing Product 1", "#1234123", 1239.9, 123.2, 10, "http://example.com/magnet.jpg", null, null, null);
//
//        // Define behavior of the mocks
//        Mockito.when(productMapper.ProductRequestDTOToProduct(productRequestDTO)).thenReturn(mockMappedProduct);
//        Mockito.when(productRepository.findByProductCode(mockMappedProduct.getProductCode())).thenReturn(Optional.of(new Product()));
//
//        // --- Act and Assert ---
//        assertThrows(DuplicateCodeException.class, () -> productService.addProduct(productRequestDTO),
//                "Expected DuplicateCodeException to be thrown");
//    }
//
//    @Test
//    @DisplayName("Product updated successfully")
//    public void testUpdateProduct_shouldReturnProductAsProductDTO() {
//        //  --- Arrange ---
//        Category category = new Category();
//        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
//        CategoryDTO categoryDTO = new CategoryDTO();
//        UnitOfMeasureDTO unitOfMeasureDTO = new UnitOfMeasureDTO();
//        ProductResponseDTO productResponseDTO = createProductDTO(1L, "TestingUpdateProduct 1", "UpdatedTESTPR0001", "New Updated Testing Product 1", "#1234123", 1239.9, 123.2, 10, "http://example.com/magnet.jpg", categoryDTO, unitOfMeasureDTO);
//        Product existingProduct = createProduct("TestingProduct 1", "TESTPROD001", "New Testing Product 1", "#1234123", 1239.9, 123.2, 10, "http://example.com/magnet.jpg", new Category(), new UnitOfMeasure(), 1L);
//        Product mockResultProduct = createProduct("TestingProduct 1", "TESTPROD001", "New Testing Product 1", "#1234123", 1239.9, 123.2, 10, "http://example.com/magnet.jpg", category, unitOfMeasure, 1L);
//        ProductResponseDTO expectedProductResponseDTO = createProductDTO(1L, "TestingProduct 1", "TESTPROD001", "New Testing Product 1", "#1234123", 1239.9, 123.2, 10, "http://example.com/magnet.jpg", categoryDTO, unitOfMeasureDTO );
//
//        // Define behavior of the mocks
//        Mockito.when(productRepository.findById(productResponseDTO.getId())).thenReturn(Optional.of(existingProduct));
//        Mockito.when(categoryService.getCategoryEntityById(productResponseDTO.getCategory().getId())).thenReturn(category);
//        Mockito.when(unitOfMeasureService.getUnitOfMeasureEntityById(productResponseDTO.getPrimaryUom().getId())).thenReturn(unitOfMeasure);
//        Mockito.when(productRepository.save(existingProduct)).thenReturn(mockResultProduct);
//        Mockito.when(productMapper.productToProductResponseDTO(mockResultProduct)).thenReturn(expectedProductResponseDTO);
//
//        // --- Act ---
//        ProductResponseDTO resultProductResponseDTO = productService.updateProduct(productResponseDTO);
//
//        // --- Assert ---
//        assertAll(
//                () -> assertNotNull(resultProductResponseDTO, "result updated product DTO is null"),
//                () -> assertNotNull(resultProductResponseDTO.getId(), "result updated product DTO does not include an ID"),
//                () -> assertEquals(expectedProductResponseDTO, resultProductResponseDTO, "Expected product DTO is not correct") // we can use this because lombok automatically generates a hasCode and equals methods for us
//        );
//    }
//
//    @Test
//    @DisplayName("Product successfully not updated because id was not found")
//    public void testUpdateProduct_shouldThrowResourceNotFoundException() {
//        // --- Arrange ---
//        ProductResponseDTO productResponseDTO = createProductDTO(1L, "TestingUpdateProduct 1", "UpdatedTESTPR0001", "New Updated Testing Product 1", "#1234123", 1239.9, 123.2, 10, "http://example.com/magnet.jpg", new CategoryDTO(), new UnitOfMeasureDTO());
//
//        // Define behavior of the mocks
//        Mockito.when(productRepository.findById(productResponseDTO.getId())).thenReturn(Optional.empty());
//
//
//        // --- Act and Assert ---
//        assertThrows(ResourceNotFoundException.class, () -> productService.updateProduct(productResponseDTO));
//    }
//
//    @Test
//    @DisplayName("Product deleted successfully")
//    public void testDeleteProduct_shouldCallDeleteProductOnce() {
//        // --- Arrange ---
//        Long productId = 1L;
//        Product product = new Product();
//
//        // Define behavior of the mocks
//        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.of(product));
//
//        // --- Act ---
//        productService.removeProduct(productId);
//
//        // --- Assert ---
//        Mockito.verify(productRepository, Mockito.times(1)).delete(product);
//    }
//
//    @Test
//    @DisplayName("Product successfully not deleted because product was not found")
//    public void testDeleteProduct_shouldThrowResourceNotFoundException() {
//        // --- Arrange ---
//        Long productId = 1L;
//
//
//        // Define behavior of the mocks
//        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.empty());
//
//        // --- Act and Assert ---
//        assertThrows(ResourceNotFoundException.class, () -> productService.removeProduct(productId), "Expected ResourceNotFoundException to be thrown");
//    }
//
//    @Test
//    @DisplayName("Product retrieved successfully")
//    public void testGetProduct_shouldReturnProductAsProductDTO() {
//        //  --- Arrange ---
//        Long productId = 1L;
//        Product mockResultProduct = createProduct("TestingProduct 1", "TESTPROD001", "New Testing Product 1", "#1234123", 1239.9, 123.2, 10, "http://example.com/magnet.jpg", new Category(), new UnitOfMeasure(), 1L);
//        ProductResponseDTO expectedProductResponseDTO = createProductDTO(1L, "TestingProduct 1", "TESTPROD001", "New Testing Product 1", "#1234123", 1239.9, 123.2, 10, "http://example.com/magnet.jpg", new CategoryDTO(), new UnitOfMeasureDTO() );
//
//        // Define behavior of the mocks
//        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.of(mockResultProduct));
//        Mockito.when(productMapper.productToProductResponseDTO(mockResultProduct)).thenReturn(expectedProductResponseDTO);
//
//        // --- Act ---
//        ProductResponseDTO resultProductResponseDTO = productService.getProductById(productId);
//
//        // --- Assert ---
//        assertAll(
//                () -> assertNotNull(resultProductResponseDTO, "result product DTO is null"),
//                () -> assertNotNull(resultProductResponseDTO.getId(), "result product DTO does not include an ID"),
//                () -> assertEquals(expectedProductResponseDTO, resultProductResponseDTO, "Expected product DTO is not correct") // we can use this because lombok automatically generates a hasCode and equals methods for us
//        );
//    }
//
//    @Test
//    @DisplayName("Product successfully not retrieved because product was not found")
//    public void testGetProduct_shouldThrowResourceNotFoundException() {
//        // --- Arrange ---
//        Long productId = 1L;
//
//
//        // Define behavior of the mocks
//        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.empty());
//
//        // --- Act and Assert ---
//        assertThrows(ResourceNotFoundException.class, () -> productService.getProductById(productId), "Expected ResourceNotFoundException to be thrown");
//    }
//
//
//    // some utility builder methods
//    private ProductRequestDTO createProductCreateDTO(String name, String code, String description, String barcode,
//                                                     Double currentPrice, Double currentCost, int quantity, String imageUrl, Long categoryId, Long primaryUomId) {
//        ProductRequestDTO dto = new ProductRequestDTO();
//        dto.setProductCode(code);
//        dto.setProductName(name);
//        dto.setDescription(description);
//        dto.setBarcode(barcode);
//        dto.setCurrentPrice(currentPrice);
//        dto.setCurrentCost(currentCost);
//        dto.setQuantity(quantity);
//        dto.setImageUrl(imageUrl);
//        return dto;
//    }
//
//    private Product createProduct(String name, String code, String description, String barcode,
//                                Double currentPrice, Double currentCost, int quantity, String imageUrl, Category category, UnitOfMeasure unitOfMeasure, Long id) {
//        Product product = new Product();
//        product.setProductCode(code);
//        product.setProductName(name);
//        product.setDescription(description);
//        product.setBarcode(barcode);
//        product.setCurrentPrice(currentPrice);
//        product.setCurrentCost(currentCost);
//        product.setQuantity(quantity);
//        product.setImageUrl(imageUrl);
//        product.setCategory(category);
//        product.setPrimaryUom(unitOfMeasure);
//        if (id != null) {
//            product.setId(id);
//        }
//        return product;
//    }
//
//    private ProductResponseDTO createProductDTO(Long id, String name, String code, String description, String barcode,
//                                                Double currentPrice, Double currentCost, int quantity, String imageUrl, CategoryDTO category, UnitOfMeasureDTO unitOfMeasure) {
//        ProductResponseDTO dto = new ProductResponseDTO();
//        dto.setId(id);
//        dto.setProductCode(code);
//        dto.setProductName(name);
//        dto.setDescription(description);
//        dto.setBarcode(barcode);
//        dto.setCurrentPrice(currentPrice);
//        dto.setCurrentCost(currentCost);
//        dto.setQuantity(quantity);
//        dto.setImageUrl(imageUrl);
//        dto.setCategory(new CategoryDTO());
//        dto.setPrimaryUom(new UnitOfMeasureDTO());
//        dto.setCategory(category);
//        dto.setPrimaryUom(unitOfMeasure);
//        return dto;
//    }
//
//}
