package com.openclassrooms.shopmanager.product;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

//The SpringRunner is essentially the entry-point to start using the Spring Test framework
@RunWith(SpringRunner.class)
@SpringBootTest

public class ProductControllerTest {

	/* It loads all the application beans and controllers into the context. */
	@Autowired
	private WebApplicationContext webContext;

	/*
	 * MockMvc provides support for Spring MVC testing. It encapsulates all web
	 * application beans and make them available for testing.
	 */
	private MockMvc mockMvc;

	/*
	 * We can use the @MockBean to add mock objects to the Spring application
	 * context. The mock will replace any existing bean of the same type in the
	 * application context. If no bean of the same type is defined, a new one will
	 * be added.
	 */
	@MockBean
	private ProductService productService;

	/* initialize MockMvc */
	@Before
	public void setupMockmvc() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
	}


	//Write test methods here nikola
	
	@Test
	public void createValidProduct() throws Exception {
		mockMvc.perform(post("/admin/product").param("name", "Nokia").param("price", "2.0").param("quantity", "10"))
				.andExpect(view().name("redirect:/admin/products"))
				.andExpect(model().errorCount(0))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/admin/products"));
	}
	
	@Test
	public void missingNameTest() throws Exception {
		mockMvc.perform(post("/admin/product")
				.param("price", "1.0")
				.param("quantity", "1"))
				.andExpect(view().name("product"))
				.andExpect(model().errorCount(1))
				.andExpect(model().attributeHasFieldErrors("product", "name"))
				.andExpect(status().isOk());
		
	}
	
    @Test
   public void missingPriceTest() throws Exception {
    	mockMvc.perform(post("/admin/product")
				.param("name", "Nokia")
				.param("quantity", "10"))
				.andExpect(view().name("product"))
				.andExpect(model().errorCount(1))
				.andExpect(model().attributeHasFieldErrors("product", "price"))
				.andExpect(status().isOk());
   }
   
    @Test
   public void missingQuantitnyTest() throws Exception {
    	mockMvc.perform(post("/admin/product")
				.param("price", "3.0")
				.param("name", "Nokia"))
				.andExpect(view().name("product"))
				.andExpect(model().errorCount(1))
				.andExpect(model().attributeHasFieldErrors("product", "quantity"))
				.andExpect(status().isOk());
   }
    
    @Test
    public void invalidPriceTest() throws Exception {
    	mockMvc.perform(post("/admin/product")
    			.param("name", "Nokia")
				.param("price", "-1.0")
				.param("quantity", "1"))
				.andExpect(view().name("product"))
				.andExpect(model().errorCount(1))
				.andExpect(model().attributeHasFieldErrors("product", "price"))
				.andExpect(status().isOk());
    }
}
