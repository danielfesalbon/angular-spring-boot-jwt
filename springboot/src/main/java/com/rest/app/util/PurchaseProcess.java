/**
 * 
 */
package com.rest.app.util;

import java.util.List;

import com.rest.app.table.Productpertransaction;
import com.rest.app.table.Transactions;

/**
 * @author danielf
 *
 */
public class PurchaseProcess {

	private List<Productpertransaction> prodpertrans;
	private Transactions purchasetx;

	public List<Productpertransaction> getProdpertrans() {
		return prodpertrans;
	}

	public void setProdpertrans(List<Productpertransaction> prodpertrans) {
		this.prodpertrans = prodpertrans;
	}

	public Transactions getPurchasetx() {
		return purchasetx;
	}

	public void setPurchasetx(Transactions purchasetx) {
		this.purchasetx = purchasetx;
	}

}
