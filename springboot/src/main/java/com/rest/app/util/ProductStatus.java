/**
 * 
 */
package com.rest.app.util;

import java.math.BigDecimal;

/**
 * @author danielf
 *
 */
public class ProductStatus {
	private String productid;
	private String productname;
	private BigDecimal declaredprice;
	private Integer stock;
	private Boolean active;
	private String imgpath;
	private Integer lastmax;
	private String status;
	private Integer quantity;

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}

	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	public BigDecimal getDeclaredprice() {
		return declaredprice;
	}

	public void setDeclaredprice(BigDecimal declaredprice) {
		this.declaredprice = declaredprice;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getImgpath() {
		return imgpath;
	}

	public void setImgpath(String imgpath) {
		this.imgpath = imgpath;
	}

	public Integer getLastmax() {
		return lastmax;
	}

	public void setLastmax(Integer lastmax) {
		this.lastmax = lastmax;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
