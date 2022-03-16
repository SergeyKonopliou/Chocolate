package com.chockolate.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chockolate.exception.ServiceException;
import com.chockolate.model.Product;
import com.chockolate.service.impl.ProductServiceImpl;


@Controller
public class MainController {
	
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductServiceImpl service;

	@RequestMapping(value = {"/","/main"})
	public String welcomePage() {
		return "l1";
	}
	
	@RequestMapping("/lang")
	public String welcomePageLang() {
		return "l1";
	}
	
	@RequestMapping(value = "/mainPage")
	public String welcomePageLink() {
		return "redirect:/";
	}
	
	@GetMapping("/about-me")
	public String aboutMe() {
		return "personalPage";
	}
	
	@GetMapping("/contacts")
	public String showContactInfo() {
		return "contactsPage";
	}
	
	@GetMapping("/catalog")
	public String showCatalog(Model model) {
		List<Product> products = new ArrayList<>();
		try {
			products = service.loadAll();
		} catch (ServiceException e) {
			return "error";
		}
		model.addAttribute("prod",products);
		return "catalogPage";
	}
	
	
	@GetMapping("/choc/{id}")
	public String showPersonalProductPage(@PathVariable String id,Model model) {
		Product product = new Product();
		
		try {
			product = service.loadFindProductById(Long.parseLong(id));
			model.addAttribute("product", product);
		} catch (ServiceException e) {
			return "error";
		}
		return "personalProductPage";
	}
}
