package cn.jin.todo.controller;

import cn.jin.todo.dto.LoginDto;
import cn.jin.todo.dto.RegisterDto;
import cn.jin.todo.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("api/auth")
@AllArgsConstructor
public class AuthController {

	private final AuthService authService;

	// Build Register REST API
	@PostMapping("register")
	public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
		String response = authService.register(registerDto);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	// Build Login REST API
	@PostMapping("login")
	public ResponseEntity<Map<String, String>> login(@RequestBody LoginDto loginDto) {
		String token = authService.login(loginDto);
        HashMap<String, String> response = new HashMap<>();
        response.put("token", token);
        return ResponseEntity.ok(response);
	}

}
