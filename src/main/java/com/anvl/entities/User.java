/**
 * 
 */
package com.anvl.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

	@Column(name = "email")
	@Email(message = "user.email.invalid.msg")
	@NotBlank(message = "user.email.empty.msg")
	private String email;

	@Column(name = "user_name", unique = true)
	@NotBlank(message = "user.name.empty.msg")
	private String userName;

	@Column(name = "address")
	private String address;

	@JsonIgnore
	@Column(name = "password")
	private String password;

	@ManyToMany
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Role> roles;

	@ManyToMany
	@JoinTable(name = "user_course", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "course_id"))
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Course> cources;

}
