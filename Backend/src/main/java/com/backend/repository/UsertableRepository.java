/**
 * 
 */
package com.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.entity.Usertable;

/**
 * @author Daniel Fesalbon
 *
 */
public interface UsertableRepository extends JpaRepository<Usertable, String>{

}
