package org.mutu.example.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Zaw Than Oo
 * @since 14-04-2023
 */
@RestController
@RequestMapping("view")
public class ViewController {

	@GetMapping("/test")
    public ResponseEntity<String> getEmployeeById() {
		return ResponseEntity.ok("Hello");
    }
}
