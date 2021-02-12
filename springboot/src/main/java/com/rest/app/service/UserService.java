/**
 * 
 */
package com.rest.app.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.rest.app.table.Useraccount;

/**
 * @author danielf
 *
 */

public interface UserService {

	List<Useraccount> getUsers();

	ResponseEntity<Map<String, Object>> saveUser(Useraccount user);

	ResponseEntity<Map<String, Object>> deteleUser(Long userid);

	ResponseEntity<Map<String, Object>> validateReset(Useraccount user);

	ResponseEntity<Map<String, Object>> resetPassword(Useraccount user);

}
