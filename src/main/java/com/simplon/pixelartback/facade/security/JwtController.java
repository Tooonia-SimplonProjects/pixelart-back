package com.simplon.pixelartback.facade.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class JwtController {

    @Autowired
    private JwtService jwtService;

    @PostMapping("/authenticate")
    public ResponseEntity<JwtResponse> createJwtTokenAndAuthenticateUser(@RequestBody JwtRequest jwtRequest) throws Exception {
        return ResponseEntity.ok(jwtService.createJwtTokenAndAuthenticateUser(jwtRequest));
    }
}
