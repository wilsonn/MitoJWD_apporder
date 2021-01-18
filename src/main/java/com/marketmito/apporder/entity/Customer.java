package com.marketmito.apporder.entity;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customers")
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "Ingrese nombre")
	@Column(name="first_name",nullable = false, length = 70)
	private String firstName;
	
	@NotEmpty(message = "Ingrese apellido")
	@Column(name="last_name",nullable = false, length = 70)
	private String lastName;
	
	@Size(min=8,max=8)
	@NotEmpty(message = "Ingrese dni")
	@Column(name="dni",nullable = false, length = 8)
	private String dni;
	
}



