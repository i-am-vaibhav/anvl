/**
 * 
 */
package com.anvl.repos;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anvl.entities.User;

/**
 * @author Vaibhav
 *
 */
public interface UserRepository extends JpaRepository<User, BigDecimal> {

}
