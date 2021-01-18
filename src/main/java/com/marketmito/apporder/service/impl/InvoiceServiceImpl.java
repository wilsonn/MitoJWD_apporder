package com.marketmito.apporder.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marketmito.apporder.entity.Invoice;
import com.marketmito.apporder.repository.InvoiceRepository;
import com.marketmito.apporder.service.InvoiceService;

@Service
public class InvoiceServiceImpl implements InvoiceService {
	
	@Autowired
	private InvoiceRepository invoiceRepository;

	@Override
	public List<Invoice> getAll() throws Exception {
		return invoiceRepository.findAll();
	}

	@Override
	public Page<Invoice> getAll(Pageable pageable) throws Exception {
		return invoiceRepository.findAll(pageable);
	}

	@Transactional
	@Override
	public Invoice saveOrUpdate(Invoice entity) throws Exception {
		entity.getItems().forEach(item-> item.setInvoice(entity));
		return invoiceRepository.save(entity);
	}

	@Override
	public Optional<Invoice> getOne(Long id) throws Exception {
		return invoiceRepository.findById(id);
	}

	@Transactional
	@Override
	public void deleteById(Long id) throws Exception {
		invoiceRepository.deleteById(id);
	}

	@Override
	public Optional<Invoice> getInvoiceByIdWithCustomerWithItemInvoiceWithProduct(Long invoiceId) throws Exception {
		return invoiceRepository.findInvoiceByIdWithCustomerWithItemInvoiceWithProduct(invoiceId);
	}

	@Override
	public List<Invoice> getInvoicesByCustomerId(Long customerId) throws Exception {
		return invoiceRepository.findInvoicesByCustomerId(customerId);
	}

}
