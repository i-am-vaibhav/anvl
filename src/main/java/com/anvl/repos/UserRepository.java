/**
 * 
 */
package com.anvl.repos;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anvl.entities.User;

/**
 * @author Vaibhav
 *
 */
@Repository
public interface UserRepository extends JpaRepository<User, BigDecimal> {

	Optional<User> findByUserName(String userName);
	
}
