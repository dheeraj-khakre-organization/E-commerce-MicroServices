package com.Ecommerce.productService;

//import com.Ecommerce.productService.DTOs.ProductRequest;
//import com.Ecommerce.productService.repository.ProductRepository;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.DynamicPropertyRegistrar;
//import org.springframework.test.context.DynamicPropertyRegistry;
//import org.springframework.test.context.DynamicPropertySource;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.testcontainers.containers.MongoDBContainer;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;

@SpringBootTest
//@Testcontainers
class ProductServiceApplicationTests {

//	@Container
//	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");
//	@Autowired
//	private MockMvc mockMvc;
//	@Autowired
//	private ObjectMapper objectMapper;
//	@Autowired
//	private ProductRepository productRepository;
//
//	static {
//		mongoDBContainer.start();
//	}
//
//	@DynamicPropertySource
//	static void setProperties(DynamicPropertyRegistry dymDynamicPropertyRegistry) {
//		dymDynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
//	}
//
//	@Test
//	void shouldCreateProduct() throws Exception {
//		ProductRequest productRequest = getProductRequest();
//		String productRequestString = objectMapper.writeValueAsString(productRequest);
//		mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
//						.contentType(MediaType.APPLICATION_JSON)
//						.content(productRequestString))
//				.andExpect(MockMvcResultMatchers.status().isCreated());
//		Assertions.assertEquals(1, productRepository.findAll().size());
//	}
//
//	private ProductRequest getProductRequest() {
//		return ProductRequest.builder()
//				.name("iPhone 13")
//				.description("iPhone 13")
//				.price("12000")
//				.build();
//	}

}
