package com.marketmito.apporder.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.marketmito.apporder.entity.Customer;
import com.marketmito.apporder.entity.Invoice;
import com.marketmito.apporder.entity.InvoiceLine;
import com.marketmito.apporder.entity.Product;
import com.marketmito.apporder.pagination.PageRender;
import com.marketmito.apporder.service.CustomerService;
import com.marketmito.apporder.service.InvoiceService;
import com.marketmito.apporder.service.ProductService;

@Controller
@RequestMapping("/invoices")
@SessionAttributes("invoice")
public class InvoiceController {

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private InvoiceService invoiceService;
	
	@GetMapping(value = "/list")
	public String getAllCustomers(@RequestParam(name="page",defaultValue = "0")int page,			
			Model model) {
		try {
			Pageable pageRequest=PageRequest.of(page, 3);
			Page<Customer> customers=customerService.getAll(pageRequest);
			PageRender<Customer> pageRender=new PageRender<Customer>("/invoices/list/", customers);
			
			model.addAttribute("title", "Listado de Clientes");
			model.addAttribute("customers",customers );
			model.addAttribute("page",pageRender);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "invoice/invoice";
	}
	
	
	@GetMapping("/form/{customerId}")
	public String newInvoice(@PathVariable(value="customerId") Long customerId, Model model) {
		try {
			Optional<Customer> customer=customerService.getOne(customerId);
			if(!customer.isPresent()) {
				model.addAttribute("info","Cliente no existe");
				return "redirect:/invoices/list";
			}else {
				Invoice invoice=new Invoice();
				invoice.setCustomer(customer.get());
				model.addAttribute("invoice",invoice);
				model.addAttribute("title","Factura");
			}			
		} catch (Exception e) {
			model.addAttribute("error",e.getMessage());
		}
		return "invoice/form";
	}
	
	@PostMapping("/save")
	public String saveInvoice(Invoice invoice, Model model, 
			@RequestParam(name="item_id[]", required = true) Long[] itemId,
			@RequestParam(name="quantity[]", required = true) Integer[] quantity,SessionStatus status) {
		
		try {
			
			if(itemId==null || itemId.length==0) {
				model.addAttribute("info","Factura no tiene items(productos)");
				return "invoice/form";
			}
			
			for (int i = 0; i < itemId.length; i++) {
				Optional<Product> product=productService.getOne(itemId[i]);
				if(product.isPresent()) {
					InvoiceLine itemLine=new InvoiceLine();
					itemLine.setQuantity(quantity[i]);
					itemLine.setProduct(product.get());
					//Invoice----InvoiceLine
					invoice.addItemInvoice(itemLine);
				}
			}
			
			invoiceService.saveOrUpdate(invoice);
			status.setComplete();
			model.addAttribute("success","Factura Generada");
			
		} catch (Exception e) {
			model.addAttribute("error",e.getMessage());
		}
		
		return "redirect:/invoices/detail/"+ invoice.getCustomer().getId();
		
	}
			
	@GetMapping("/detail/{id}")
	public String detailInvoice(@PathVariable(value="id")Long invoiceId, Model model) {
		try {
			
			Optional<Invoice> invoice=invoiceService
						.getInvoiceByIdWithCustomerWithItemInvoiceWithProduct(invoiceId);
			
			if(!invoice.isPresent()) {
				model.addAttribute("info","Factura no existe");
				return "redirect:/invoices/list";
			}
			
			model.addAttribute("invoice",invoice.get());
			model.addAttribute("title","Factura");
			
		} catch (Exception e) {
			model.addAttribute("error",e.getMessage());
		}
		return "invoice/detailinvoice";
	}
	
	
	@GetMapping(value="/customers/{id}")
	public String invoiceByCustomer(@PathVariable(value="id")Long customerId, Model model, RedirectAttributes flash) {
		Optional<Customer> customer;
		List<Invoice> invoices=new ArrayList<>();
		
		try {
			customer=customerService.getOne(customerId);
			
			if(!customer.isPresent()) {
				flash.addFlashAttribute("error", "El cliente no existe");
				return "redirect:/invoices/list";
			}else {
				invoices=invoiceService.getInvoicesByCustomerId(customerId);
				model.addAttribute("customer",customer.get());
				model.addAttribute("invoices",invoices);
				model.addAttribute("title","Facturas de Cliente");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return "invoice/invoicecustomer";
	}
	
	
}




