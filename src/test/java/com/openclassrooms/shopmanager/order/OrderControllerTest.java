package com.openclassrooms.shopmanager.order;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.openclassrooms.shopmanager.product.ProductEntity;
import com.openclassrooms.shopmanager.product.ProductRepository;

//The SpringRunner is essentially the entry-point to start using the Spring Test framework
@RunWith(SpringRunner.class)
@SpringBootTest

public class OrderControllerTest {
	
private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext webContext;
	
	
	@Autowired
	private ProductRepository productRepository;
	private ProductEntity product;
	
	@Autowired
	private OrderRepository orderRepository;
	
	
	@Before
	public void setupMockmvc() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
	}
	
	@Before
	public void createProduct() {
		
	 product = new ProductEntity();
		
		product.setId(1L);
		product.setQuantity(1);
		product.setPrice(25.5);
		product.setName("A20 Samsung 2019");
		product.setDescription("description");
		product.setDetails("details");
		
		product = productRepository.save(product);
			
	}

	@After
	public void deleteProduct() {
		
		productRepository.delete(product);
	}
	
       //Sample Method for you
       @Test
       public void testAddToCart() throws Exception {
                     
              mockMvc.perform(post("/order/addToCart")
                     .param("productId", product.getId().toString()))
              .andExpect(view().name("redirect:/order/cart"))
              .andExpect(model().errorCount(0))
              .andExpect(status().is3xxRedirection())
              .andExpect(redirectedUrl("/order/cart"));                  
       }
       
       //Write other test methods here
       @Test 
       public void removeFromCartTest() throws Exception {
    	  mockMvc.perform(post("/order/removeFromCart")
    			  .param("productId", product.getId().toString()))
    	  .andExpect(view().name("redirect:/order/cart"))
    	  .andExpect(model().errorCount(0))
    	  .andExpect(status().is3xxRedirection())
    	  .andExpect(redirectedUrl("/order/cart"));
       }
       
       @Test
       public void createEmptyOrderTest() throws Exception {
    	   mockMvc.perform(post("/order"))   			
    			    .andExpect(view().name("order"))    			    
    				.andExpect(status().isOk())
    			    .andExpect(model().errorCount(1));
       }
       
       @Test
       public void createOrderWithAnInvalidProductId() throws Exception {
    	   mockMvc.perform(post("/order")
    				.param("id", "one")		
    			    .param("name", "A20 Samsung 2019")
    			    .param("address", "20 Drive Street")
    			    .param("city", "Florida"))
    			    .andExpect(view().name("order"))
    			    .andExpect(model().attributeHasFieldErrors("order", "id"))
    				.andExpect(status().isOk())
    			    .andExpect(model().errorCount(1));
       }

}
	
	