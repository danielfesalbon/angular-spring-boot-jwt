/**
 * 
 */
package com.rest.app.service;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.rest.app.table.Useraccount;

/**
 * @author danielf
 *
 */
public interface UserPGRepository extends PagingAndSortingRepository<Useraccount, Long> {

}
