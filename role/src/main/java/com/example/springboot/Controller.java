package com.example.springboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@PreAuthorize("isAuthenticated()")
public class Controller {

	@GetMapping("/hello")
	@PreAuthorize("hasRole('OP_VIEWER')")
	public String hello() {
		return "Hola";
	}

	@GetMapping("/bye")
	@PreAuthorize("hasRole('OP_ADMIN')")
	public String bye() {
		return "adios";
	}

	@GetMapping("/admin")
	@PreAuthorize("hasRole('NAGRA_ADMIN')")
	public String admin() {
		return "admin!";
	}

}
