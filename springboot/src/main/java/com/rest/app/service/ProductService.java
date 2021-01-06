/**
 * 
 */
package com.rest.app.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.rest.app.repo.ProductRepository;
import com.rest.app.table.Product;
import com.rest.app.util.ProductStatus;

/**
 * @author danielf
 *
 */
@Component
public class ProductService {

	@Autowired
	private ProductRepository ProductRepository;

	public List<Product> getProducts() {
		try {
			List<Product> list = new ArrayList<Product>();
			list = ProductRepository.findAll();
			return list;

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return new ArrayList<Product>();
	}

	public ResponseEntity<Map<String, Object>> saveProduct(Product product) {

		Map<String, Object> res = new HashMap<String, Object>();

		try {
			res.put("event", "Save product " + product.getProductid() + ". Priced at " + product.getDeclaredprice());
			ProductRepository.save(product);
			res.put("flag", "success");

		} catch (Exception e) {
			e.printStackTrace();
			res.put("flag", "failed");
			return ResponseEntity.badRequest().body(res);
			// TODO: handle exception
		}
		return ResponseEntity.ok().body(res);
	}

	public ResponseEntity<Map<String, Object>> deleteProduct( String productid) {

		Map<String, Object> res = new HashMap<String, Object>();

		try {
			res.put("event", "Deleted product " + productid);
			ProductRepository.delete(ProductRepository.findById(productid).get());
			res.put("flag", "success");

		} catch (Exception e) {
			e.printStackTrace();
			res.put("flag", "failed");
			return ResponseEntity.badRequest().body(res);
			// TODO: handle exception
		}
		return ResponseEntity.ok().body(res);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ResponseEntity<Product> getProductdetails( String productid) {
		try {

			Product p = ProductRepository.findById(productid).get();
			if (p != null) {
				return ResponseEntity.ok(p);
			}

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return new ResponseEntity("Invalid product search", HttpStatus.NOT_FOUND);
	}

	public List<ProductStatus> getProductwithStatus() {
		try {
			List<ProductStatus> list = new ArrayList<ProductStatus>();
			List<Product> products = new ArrayList<Product>();
			products = ProductRepository.findAll();
			for (Product p : products) {
				ProductStatus n = new ProductStatus();
				n.setProductid(p.getProductid());
				n.setProductname(p.getProductname());
				n.setActive(p.getActive());
				n.setDeclaredprice(p.getDeclaredprice());
				n.setImgpath(p.getImgpath());
				n.setLastmax(p.getLastmax());
				n.setStock(p.getStock());
				n.setQuantity(1);
				int low = (int) (n.getLastmax() * 0.1);
				Integer stock = n.getStock();
				n.setStatus(stock == 0 ? "OUT-OF-STOCK" : n.getStock() <= low ? "LOW" : "IN-STOCK");
				list.add(n);
			}
			return list;

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return new ArrayList<ProductStatus>();
	}

}
