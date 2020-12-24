package com.veloso.auth.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;
import static org.springframework.http.ResponseEntity.ok;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.veloso.auth.dto.UserDTO;
import com.veloso.auth.jwt.JwtTokenProvider;
import com.veloso.auth.repository.UserRepository;

@RestController
@RequestMapping("/login")
public class AuthController {

	private final AuthenticationManager authenticationManager;
	private final JwtTokenProvider jwtTokenProvider;
	private final UserRepository userRepository;

	@Autowired
	public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider,
			UserRepository userRepository) {
		super();
		this.authenticationManager = authenticationManager;
		this.jwtTokenProvider = jwtTokenProvider;
		this.userRepository = userRepository;
	}
	
	@RequestMapping("/testeSecurity")
	public String teste() {
		return "testado";
	}

	@PostMapping(produces = { APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE, "application/x-yaml" }, consumes = {
			APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE, "application/x-yaml" })
	public ResponseEntity<?> login(@RequestBody UserDTO userDTO) {
		try {
			var username = userDTO.getUsername();
			var password = userDTO.getPassword();

			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

			var user = userRepository.findByUsername(username);

			var token = "";

			if (Objects.nonNull(user)) {
				token = jwtTokenProvider.createToken(username, user.getRoles());
			} else {
				throw new UsernameNotFoundException("Username not found.");
			}

			Map<Object, Object> model = new HashMap<>();
			model.put("username", username);
			model.put("token", token);

			return ok(model);
		} catch (AuthenticationException e) {
			throw new BadCredentialsException("Invalid username/password");
		}

	}

}
