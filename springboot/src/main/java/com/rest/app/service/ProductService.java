/**
 * 
 */
package com.rest.app.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.rest.app.table.Product;
import com.rest.app.util.ImageBody;
import com.rest.app.util.ProductStatus;

/**
 * @author danielf
 *
 */
public interface ProductService {

	List<Product> getProducts();

	ResponseEntity<Map<String, Object>> saveProduct(Product product);

	ResponseEntity<Map<String, Object>> deleteProduct(String productid);

	ResponseEntity<Product> getProductdetails(String productid);

	List<ProductStatus> getProductwithStatus();

	ResponseEntity<Map<String, Object>> saveImage(ImageBody image);

}
