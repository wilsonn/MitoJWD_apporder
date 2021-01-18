package com.marketmito.apporder.service;

import java.util.List;
import java.util.Optional;


import com.marketmito.apporder.entity.Invoice;

public interface InvoiceService extends CrudService<Invoice> {

	Optional<Invoice> getInvoiceByIdWithCustomerWithItemInvoiceWithProduct(Long invoiceId) throws Exception;
	
	List<Invoice> getInvoicesByCustomerId(Long customerId) throws Exception;
	
}
