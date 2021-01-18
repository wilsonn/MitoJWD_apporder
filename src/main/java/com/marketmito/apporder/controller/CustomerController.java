package com.marketmito.apporder.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.marketmito.apporder.entity.Customer;
import com.marketmito.apporder.pagination.PageRender;
import com.marketmito.apporder.service.CustomerService;

@Controller
@RequestMapping("/customers")
@SessionAttributes("customer")

public class CustomerController {

	@Autowired
	private CustomerService customerService;

	// request: http://localhost:8080/customer/list?page=1
//	@Secured("ROLE_USER")
	@GetMapping(value = "/list")
	public String getAllCustomers(@RequestParam(name="page",defaultValue = "0")int page,			
			Model model) {
		try {
			Pageable pageRequest=PageRequest.of(page, 3);
			Page<Customer> customers=customerService.getAll(pageRequest);
			PageRender<Customer> pageRender=new PageRender<Customer>("/customers/list/", customers);
			
			model.addAttribute("title", "Listado de Clientes");
			model.addAttribute("customers",customers );
			model.addAttribute("page",pageRender);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "customer/customer";
	}

	// request: http://localhost:8080/customer/new
	@GetMapping(value = "/new")
	public String newCustomer(Model model) {
		Customer customer=new Customer();
		model.addAttribute("title", "Registrar");
		model.addAttribute("customer", customer);
		return "customer/form";
	}

	// request: http://localhost:8080/customer/save
	//@Secured("ROLE_ADMIN")
	@PostMapping(value = "/save")
	public String saveCustomer(@Valid Customer customer,BindingResult result,
			RedirectAttributes flash,SessionStatus status, Model model) {

		try {
			if(result.hasErrors()) {
				model.addAttribute("title","Registrar Cliente");
				return "customer/form";
			}
			
			String messageFlash=(customer.getId()!= null) ? "Cliente editado correctamente!":
						"Cliente registrado correctamente!";
			
			customerService.saveOrUpdate(customer);
			status.setComplete();
			flash.addFlashAttribute("success", messageFlash);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "redirect:/customers/list";
	}
	
	// request: http://localhost:8080/customer/edit/1
	@GetMapping(value="/edit/{id}")
	public String editCustomer(@PathVariable(value="id") Long id, Model model,
			RedirectAttributes flash) {
		
		Optional<Customer> customer;
		
		try {
			if(id> 0) {
				customer=customerService.getOne(id);
				if(!customer.isPresent()) {
					flash.addFlashAttribute("error", "El codigo del cliente no existe");
					return "redirect:/customers/list";
				}
			}else {
				flash.addFlashAttribute("error", "El codigo del cliente no puede ser cero");
				return "redirect:/customers/list";
			}
			
			model.addAttribute("customer",customer);
			model.addAttribute("title","Editar Cliente");
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return "customer/form";
		
	}
	
}



