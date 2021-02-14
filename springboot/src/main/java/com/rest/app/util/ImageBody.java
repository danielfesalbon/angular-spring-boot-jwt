/**
 * 
 */
package com.rest.app.util;

/**
 * @author danielf
 *
 */
public class ImageBody {

	private String filename;
	private String base64;
	private String productid;

	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getBase64() {
		return base64;
	}

	public void setBase64(String base64) {
		this.base64 = base64;
	}

}
