package com.marketmito.apporder.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.marketmito.apporder.entity.Role;
import com.marketmito.apporder.entity.Users;
import com.marketmito.apporder.service.RoleService;
import com.marketmito.apporder.service.UserService;

@Controller
@RequestMapping("/users")
@SessionAttributes("user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@GetMapping(value = "/new")
	public String newUser(Model model) {
		Users user=new Users();
		
		List<Role> allRoles;
		try {
			allRoles = roleService.getAll();
		} catch (Exception e) {
			allRoles = new ArrayList<>();
		}
		
		model.addAttribute("title", "Registrar");
		model.addAttribute("allRoles", allRoles);
		model.addAttribute("user", user);
		return "user/form";
	}
	
	@PostMapping(value = "/save")
	public String saveUser(@Valid Users user,BindingResult result,
			RedirectAttributes flash,SessionStatus status, Model model) {

		try {
			if(result.hasErrors()) {
				model.addAttribute("title","Registrar");
				return "user/form";
			}
			
			String messageFlash= "";
			if (user.getId()!= null) {
				messageFlash = "Usuario editado correctamente!";
			} else {
				messageFlash = "Usuario registrado correctamente!";
				user.setEnabled(Boolean.parseBoolean("true"));
			}
			userService.saveOrUpdate(user);
			status.setComplete();
			flash.addFlashAttribute("success", messageFlash);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "redirect:/";
	}
}
