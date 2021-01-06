/**
 * 
 */
package com.rest.app.util;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author danielf
 *
 */
public class ReportUtil {

	@Value("${business.tin}")
	private String TIN;
	@Value("${business.contact}")
	private String CONTACT;
	@Value("${business.address}")
	private String ADDRESS;

}
