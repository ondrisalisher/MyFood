package org.example.myfood.services.impl;

import org.example.myfood.DTO.ProductDto;
import org.example.myfood.models.ProductModel;
import org.example.myfood.models.UserModel;
import org.example.myfood.repositories.EatenRepository;
import org.example.myfood.repositories.FavoriteRepository;
import org.example.myfood.repositories.ProductRepository;
import org.example.myfood.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @InjectMocks
    private ProductServiceImpl productServiceImpl;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private FavoriteRepository favoriteRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private EatenRepository eatenRepository;

    @Mock
    private RestTemplate restTemplate;

    private ProductDto mockProductDto;
    private UserModel mockUser;
    private ProductModel mockProduct;

    @BeforeEach
    void setUp() {
        mockProductDto = new ProductDto("TestProduct", 100, 10, 20, 5, 1L, "confirmed");
        mockUser = new UserModel();
        mockUser.setId(1L);
        mockUser.setUsername("testuser");
        mockUser.setRole("ROLE_ADMIN");

        mockProduct = new ProductModel();
        mockProduct.setId(1L);
        mockProduct.setName("TestProduct");
    }

    @Test
    void testAddProduct() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(mockUser));
        when(productRepository.save(any(ProductModel.class))).thenReturn(mockProduct);
        when(restTemplate.postForEntity(anyString(), eq(null), eq(String.class)))
                .thenReturn(ResponseEntity.ok("Success"));

        String result = productServiceImpl.addProduct(mockProductDto);

        assertEquals("redirect:/product", result);
        verify(productRepository, times(1)).save(any(ProductModel.class));
        verify(restTemplate, times(1)).postForEntity(contains("/newProduct"), eq(null), eq(String.class));
    }

    @Test
    void testDeleteProduct() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(mockProduct));
        doNothing().when(favoriteRepository).deleteByProductId(mockProduct);
        doNothing().when(eatenRepository).deleteByProductId(mockProduct);
        doNothing().when(productRepository).delete(mockProduct);
        when(restTemplate.postForEntity(contains("/deleteProduct"), eq(null), eq(String.class)))
                .thenReturn(ResponseEntity.ok("Deleted"));

        String result = productServiceImpl.deleteProduct(1L);

        assertEquals("redirect:/product", result);
        verify(productRepository, times(1)).delete(mockProduct);
        verify(restTemplate, times(1)).postForEntity(contains("/deleteProduct"), eq(null), eq(String.class));
    }

    @Test
    void testLikeProduct() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(mockUser));
        when(productRepository.findById(1L)).thenReturn(Optional.of(mockProduct));
        when(favoriteRepository.existsByUserIdAndProductId(mockUser, mockProduct)).thenReturn(false);

        String result = productServiceImpl.likeProduct(1L);

        assertEquals("redirect:/product/1", result);
        verify(favoriteRepository, times(1)).save(any());
    }

    @Test
    void testUpdateProduct() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(mockProduct));
        when(productRepository.save(any(ProductModel.class))).thenReturn(mockProduct);

        String result = productServiceImpl.updateProduct(1L, mockProductDto);

        assertEquals("redirect:/product/1", result);
        verify(productRepository, times(1)).save(any(ProductModel.class));
    }

    @Test
    void testProductDetailsForAdmin() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(mockUser));
        when(productRepository.findById(1L)).thenReturn(Optional.of(mockProduct));
        when(favoriteRepository.findByProductId(mockProduct)).thenReturn(null);
        when(restTemplate.getForEntity(anyString(), eq(String.class)))
                .thenReturn(ResponseEntity.ok("Product Info"));

        // Add actual logic for Model if needed to verify behavior
    }
}
