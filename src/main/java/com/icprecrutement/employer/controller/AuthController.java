package com.icprecrutement.employer.controller;

import com.icprecrutement.employer.dto.JwtResponse;
import com.icprecrutement.employer.dto.LoginRequest;
import com.icprecrutement.employer.dto.RegisterRequest;
import com.icprecrutement.employer.entity.Role;
import com.icprecrutement.employer.entity.User;
import com.icprecrutement.employer.repository.UserRepository;
import com.icprecrutement.employer.service.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    
    @PostMapping("/inscription")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            return ResponseEntity.badRequest()
                    .body("Erreur: Le nom d'utilisateur est déjà pris!");
        }
        
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            return ResponseEntity.badRequest()
                    .body("Erreur: L'email est déjà utilisé!");
        }
        
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole(Role.USER);
        
        userRepository.save(user);
        
        return ResponseEntity.ok("Utilisateur enregistré avec succès!");
    }
    @PostMapping("/addAdmin")
    public ResponseEntity<?> inscriptionAdmin(@Valid @RequestBody RegisterRequest registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            return ResponseEntity.badRequest()
                    .body("Erreur: Le nom d'utilisateur est déjà pris!");
        }
        
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            return ResponseEntity.badRequest()
                    .body("Erreur: L'email est déjà utilisé!");
        }
        
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole(Role.ADMIN);
        
        userRepository.save(user);
        
        return ResponseEntity.ok("Administrateur enregistré avec succès!");
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );
            
            User user = (User) authentication.getPrincipal();
            String jwt = jwtService.generateToken(user);
            
            return ResponseEntity.ok(new JwtResponse(jwt, user.getUsername(), user.getEmail()));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Erreur: Nom d'utilisateur ou mot de passe incorrect!");
        }
    }
}