package com.example.springboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
public class ControllerApi {

	@GetMapping("/api/hello")
	public String hello() {
		return "hallo";
	}

	@GetMapping("/api/hello-admin")
        @PreAuthorize("hasRole('OP_ADMIN')")	
	public String helloAdmin() {
		return "hallo admin";
	}

	@PostMapping("/api/bye")
	public String bye() {
		return "tschuss";
	}

	@DeleteMapping("/api/op-admin")
	public String admin() {
		return "ich bin admin!";
	}

	@GetMapping("/admin/hello")
	public String helloNagraAdmin() {
		return "hallo nagra admin";
	}

}
