package com.marketmito.apporder.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
@AllArgsConstructor
@Table(name = "invoices")
public class Invoice {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String description;
	private String observation;
	
	@Column(name="create_at")
	private LocalDate createAt;
	
	@ManyToOne
	@JoinColumn(name="customer_id", nullable = false)
	private Customer customer;
	
	@OneToMany(mappedBy = "invoice", cascade = {
			CascadeType.PERSIST, CascadeType.REMOVE
	}, fetch = FetchType.LAZY) 
	private List<InvoiceLine> items;
	
	public Invoice() {
		this.items=new ArrayList<>();
	}
	
	@PrePersist
	public void init() {
		createAt=LocalDate.now();
	}
	
	public void addItemInvoice(InvoiceLine item) {
		this.items.add(item);
	}
	
	public Double getTotal() {
		return items
				.stream()
				.collect(Collectors.summingDouble(InvoiceLine::calculateAmount));
	}
}




