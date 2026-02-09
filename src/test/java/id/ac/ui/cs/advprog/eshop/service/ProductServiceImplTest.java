package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

   @Mock
   private ProductRepository productRepository;

   @InjectMocks
   private ProductServiceImpl productService;

   @Test
   void testEditProduct() {
      String productId = "ac338e9f-1c39-460e-9960-71af6af63bd6";
      Product product = new Product();
      product.setProductName("Sampo Cap Bebek");
      product.setProductQuantity(1000);

      when(productRepository.edit(product)).thenReturn(product);
      productService.editProduct(productId, product);
      assertEquals(productId, product.getProductId());

      verify(productRepository, times(1)).edit(product);
   }

   @Test
   void testDeleteProductByIdPositive() {
      String productId = "product-id-yang-valid";

      productService.deleteProductById(productId);
      verify(productRepository, times(1)).delete(productId);
   }
}