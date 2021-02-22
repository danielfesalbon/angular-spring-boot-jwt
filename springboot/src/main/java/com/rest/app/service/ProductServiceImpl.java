/**
 * 
 */
package com.rest.app.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.rest.app.repo.ProductRepository;
import com.rest.app.table.Product;
import com.rest.app.util.ImageBody;
import com.rest.app.util.ProductStatus;
import com.rest.app.util.SettingsProperties;

/**
 * @author danielf
 *
 */
@Component
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository ProductRepository;

	@Autowired
	private SettingsProperties properties;

	public List<Product> getProducts() {
		try {
			List<Product> list = new ArrayList<Product>();
			list = ProductRepository.findAll();
			for (Product l : list) {
				if (l.getImgpath() != null && !l.getImgpath().isEmpty()) {
					l.setImgpath(encoder(properties.getImgpath() + "\\" + l.getImgpath()));
				} else {
					l.setImgpath(encoder(properties.getImgpath() + "\\" + "noimage.png"));
				}
			}
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

	public ResponseEntity<Map<String, Object>> deleteProduct(String productid) {

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
	public ResponseEntity<Product> getProductdetails(String productid) {
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
				if (n.getImgpath() != null && !n.getImgpath().isEmpty()) {
					n.setImgpath(encoder(properties.getImgpath() + "\\" + n.getImgpath()));
				} else {
					n.setImgpath(encoder(properties.getImgpath() + "\\" + "noimageavailable.jpg"));
				}
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

	@Override
	public ResponseEntity<Map<String, Object>> saveImage(ImageBody image) {
		Map<String, Object> res = new HashMap<String, Object>();

		try {
			res.put("event", "Update " + image.getProductid() + "'s image");
			Base64.Decoder decoder = Base64.getDecoder();
			File file = new File(properties.getImgpath() + "\\" + image.getFilename());
			byte[] bytes = decoder.decode(image.getBase64().split("base64,")[1]);
			OutputStream os = new FileOutputStream(file);
			os.write(bytes);
			os.close();
			Product p = ProductRepository.findById(image.getProductid()).get();
			p.setImgpath(image.getFilename());
			ProductRepository.save(p);
			res.put("flag", "success");

		} catch (Exception e) {
			e.printStackTrace();
			res.put("flag", "failed");
			return ResponseEntity.badRequest().body(res);
			// TODO: handle exception
		}
		return ResponseEntity.ok().body(res);
	}

	private static String encoder(String filePath) {
		String base64File = "";
		File file = new File(filePath);
		try (FileInputStream imageInFile = new FileInputStream(file)) {
			// Reading a file from file system
			byte fileData[] = new byte[(int) file.length()];
			imageInFile.read(fileData);
			base64File = Base64.getEncoder().encodeToString(fileData);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return base64File;
	}

}