package com.openclassrooms.shopmanager.product;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.openclassrooms.shopmanager.order.Cart

/**
 * Take this test method as a template to write your test methods for ProductService and OrderService.
 * A test method must check if a definite method does its job:
 *
 * Naming follows this popular convention : http://osherove.com/blog/2005/4/3/naming-standards-for-unit-tests.html
 */


@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    @InjectMocks
    ProductService productService;

    @Mock
    ProductRepository productRepository;

    @Test
    public void getAllProducts_DbHasData_allDataReturned(){

        ProductEntity product1 = new ProductEntity();
        product1.setId(1L);
        product1.setName("First product");

        ProductEntity product2 = new ProductEntity();
        product2.setId(2L);
        product2.setName("First product");

        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));

        List<ProductEntity> products = productService.getAllProducts();

        assertEquals(2, products.size());
        assertEquals(1L, products.get(0).getId() , 0);
        assertEquals(2L, products.get(1).getId() , 0);
    }
    
    @Test
    public void getAllAdminProducts() {
    	ProductEntity product1 = new ProductEntity();
        product1.setId(1L);
        product1.setName("First product");

        ProductEntity product2 = new ProductEntity();
        product2.setId(2L);
        product2.setName("First product");
        
        when(productRepository.findAllByOrderByIdDesc()).thenReturn(Arrays.asList(product1 , product2));
        
        List<ProductEntity> products = productService.getAllAdminProducts();

        assertEquals(2, products.size());
        assertEquals(1L, products.get(0).getId() , 0);
        assertEquals(2L, products.get(1).getId() , 0);
    }
    
    @Test
	public void updateProductQuantities() {

		ProductEntity product = new ProductEntity();
		product.setId(1L);
		product.setName("First product");
		product.setQuantity(10);

		Cart cart = new Cart();
		cart.addItem(product, 1);

		when(productRepository.findById(1L)).thenReturn(Optional.of(product));
		productService.updateProductQuantities(cart);
		verify(productRepository, times(1)).save(product);

	}
    
    @Test
    public void createProductTest() {
    	ProductEntity productEntity = new ProductEntity();
    	productEntity.setId(1L);
    	productEntity.setPrice(32.50);
    	productEntity.setDescription("Long and Black");
    	productEntity.setDetails("54 inches long");
    	productEntity.setName("A20 Samsung");
    	productEntity.setQuantity(40);
    	when(productRepository.save(Mockito.any(ProductEntity.class))).thenReturn(productEntity);
    	
    	
    	Product product = new Product();
    	product.setId(productEntity.getId());
        product.setDescription(productEntity.getDescription());
        product.setDetails(productEntity.getDetails());
        product.setName(productEntity.getName());
        product.setPrice(productEntity.getPrice());
        product.setQuantity(productEntity.getQuantity());
        productService.createProduct(product);
         
        ProductEntity createdProduct = productService.createProduct(product);
        
    	assertEquals(1L, createdProduct.getId(), 0);
        assertEquals(32.50, createdProduct.getPrice(), 0);
        assertEquals("Long and Black", createdProduct.getDescription());
        assertEquals("54 inches long", createdProduct.getDetails());
        assertEquals("A20 Samsung", createdProduct.getName());
        assertEquals(40, createdProduct.getQuantity(), 0);
    }
    
    @Test
    public void deleteProductTest() {
    	Long productIdTest = 1L;
    	
        productService.deleteProduct(productIdTest);
        verify(productRepository, times(1)).deleteById(productIdTest);
    }
        
}
