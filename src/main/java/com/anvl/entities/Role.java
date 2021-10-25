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

import com.sun.istack.NotNull;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * @author Vaibhav
 *
 */
@Entity
@Table(name = "tbl_role")
@RequiredArgsConstructor
@Data
public class Role implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private BigDecimal id;

	@Column(name = "Name")
	@NotNull
	private String name;

	@Column(name = "Description")
	private String description;

}
