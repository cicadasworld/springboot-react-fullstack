package cn.jin.todo.service.impl;

import cn.jin.todo.dto.LoginDto;
import cn.jin.todo.dto.RegisterDto;
import cn.jin.todo.entity.Role;
import cn.jin.todo.entity.User;
import cn.jin.todo.exception.TodoAPIException;
import cn.jin.todo.repository.RoleRepository;
import cn.jin.todo.repository.UserRepository;
import cn.jin.todo.security.JwtTokenProvider;
import cn.jin.todo.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final UserRepository userRepository;

	private final RoleRepository roleRepository;

	private final PasswordEncoder passwordEncoder;

	private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

	@Override
	public String register(RegisterDto registerDto) {
		// check username whether already exists in db
		if (userRepository.existsByUsername(registerDto.getUsername())) {
			throw new TodoAPIException(HttpStatus.BAD_REQUEST, "Username already exists!");
		}

		// check email whether already exists in db
		if (userRepository.existsByEmail(registerDto.getEmail())) {
			throw new TodoAPIException(HttpStatus.BAD_REQUEST, "Email already exists");
		}

		User user = new User();
		user.setName(registerDto.getName());
		user.setUsername(registerDto.getUsername());
		user.setEmail(registerDto.getEmail());
		user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

		Set<Role> roles = new HashSet<>();
		Role userRole = roleRepository.findByName("ROLE_USER");
		roles.add(userRole);

		user.setRoles(roles);

		userRepository.save(user);

		return "User registered successfully!";
	}

	@Override
	public String login(LoginDto loginDto) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtTokenProvider.generateToken(authentication);
	}

}
