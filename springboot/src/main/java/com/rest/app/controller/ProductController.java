/**
 * 
 */
package com.rest.app.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.rest.app.service.ProductRepository;
import com.rest.app.table.Product;

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
	private ProductRepository productService;

	@GetMapping("/list")
	public List<Product> getProducts() {
		try {

			return productService.findAll();

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return new ArrayList<Product>();
	}

	@PostMapping("/save")
	public ResponseEntity<Map<String, Object>> saveProduct(@RequestBody Product product) {

		Map<String, Object> res = new HashMap<String, Object>();

		try {
			res.put("event", "Save product " + product.getProductid() + ". Priced at " + product.getDeclaredprice());
			productService.save(product);
			res.put("flag", "success");

		} catch (Exception e) {
			e.printStackTrace();
			res.put("flag", "failed");
			return ResponseEntity.badRequest().body(res);
			// TODO: handle exception
		}
		return ResponseEntity.ok().body(res);
	}

	@DeleteMapping("/delete/{productid}")
	public ResponseEntity<Map<String, Object>> deleteProduct(@PathVariable String productid) {

		Map<String, Object> res = new HashMap<String, Object>();

		try {
			res.put("event", "Deleted product " + productid);
			productService.delete(productService.findById(productid).get());
			res.put("flag", "success");

		} catch (Exception e) {
			e.printStackTrace();
			res.put("flag", "failed");
			return ResponseEntity.badRequest().body(res);
			// TODO: handle exception
		}
		return ResponseEntity.ok().body(res);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("/get/{productid}")
	public ResponseEntity<Product> getProductdetails(@PathVariable String productid) {
		try {

			Product p = productService.findById(productid).get();
			if (p != null) {
				return ResponseEntity.ok(p);
			}

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return new ResponseEntity("Invalid product search", HttpStatus.NOT_FOUND);
	}

}
