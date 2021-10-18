/**
 * 
 */
package com.anvl.entities;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;

import lombok.Data;

/**
 * @author Vaibhav
 *
 */
@Data
@Entity
@Table(name = "tbl_user")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private BigDecimal id;
	
	@Email
	@Column(name = "email")
	private String email;
	
	@Column(name = "user_name")
	private String userName;
	
	@Column(name = "address")
	private String address;
	
}
