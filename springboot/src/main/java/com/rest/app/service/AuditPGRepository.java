/**
 * 
 */
package com.rest.app.service;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.rest.app.table.Audittrail;

/**
 * @author danielf
 *
 */
public interface AuditPGRepository extends PagingAndSortingRepository<Audittrail, Long> {

}
