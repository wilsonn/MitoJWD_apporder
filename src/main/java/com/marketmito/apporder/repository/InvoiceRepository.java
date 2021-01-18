package com.marketmito.apporder.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.marketmito.apporder.entity.Invoice;

//https://www.baeldung.com/spring-data-jpa-stored-procedures

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

	@Query("SELECT i FROM Invoice i JOIN FETCH i.customer c JOIN FETCH i.items l JOIN FETCH l.product WHERE i.id=?1")
	Optional<Invoice> findInvoiceByIdWithCustomerWithItemInvoiceWithProduct(Long invoiceId);
	
	@Query(value="SELECT * FROM invoices i WHERE i.customer_id=?1",nativeQuery = true)
	List<Invoice> findInvoicesByCustomerId(Long customerId);
	
}
