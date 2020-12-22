/**
 * 
 */
package com.rest.app.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.app.table.Audittrail;

/**
 * @author danielf
 *
 */
public interface AuditJPARepository extends JpaRepository<Audittrail, Long> {

}
