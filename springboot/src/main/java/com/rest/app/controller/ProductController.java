/**
 * 
 */
package com.rest.app.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rest.app.service.ProductService;
import com.rest.app.table.Product;
import com.rest.app.util.ProductStatus;

/**
 * @author danielf
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/product")
@Component
public class ProductController {

	@Autowired
	private ProductService productService;

	@GetMapping("/list")
	public List<Product> getProducts() {
		return productService.getProducts();
	}

	@PostMapping("/save")
	public ResponseEntity<Map<String, Object>> saveProduct(@RequestBody Product product) {
		return productService.saveProduct(product);
	}

	@DeleteMapping("/delete/{productid}")
	public ResponseEntity<Map<String, Object>> deleteProduct(@PathVariable String productid) {
		return productService.deleteProduct(productid);
	}

	@GetMapping("/get/{productid}")
	public ResponseEntity<Product> getProductdetails(@PathVariable String productid) {
		return productService.getProductdetails(productid);
	}

	@GetMapping("/order/list")
	public List<ProductStatus> getProductwithStatus() {
		return productService.getProductwithStatus();
	}

}
