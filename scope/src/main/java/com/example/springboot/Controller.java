package com.example.springboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
public class Controller {

	@GetMapping("/api/hello")
	public String hello() {
		return "Hola";
	}

	@GetMapping("/api/hello-op-admin")
        @PreAuthorize("hasAnyAuthority('SCOPE_ns_shield/nagra_admin', 'SCOPE_ns_shield/op_admin')")
	public String helloOpAdmin() {
		return "Hola op admin";
	}

	@PostMapping("/api/bye")
	public String bye() {
		return "adios";
	}

	@DeleteMapping("/api/admin")
	public String admin() {
		return "admin!";
	}
}
