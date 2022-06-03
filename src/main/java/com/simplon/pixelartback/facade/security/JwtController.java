package com.simplon.pixelartback.facade.security;

import com.simplon.pixelartback.storage.dto.UserDto;
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
    public ResponseEntity<JwtResponse> authenticateUserAndCreateJwtToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        return ResponseEntity.ok(jwtService.createJwtToken(jwtRequest));
//    public ResponseEntity<JwtResponse> createJwtToken(@RequestBody JwtRequest jwtRequest) throws Exception {
//        return ResponseEntity.ok(jwtService.createJwtToken(jwtRequest));
//    public ResponseEntity<JwtResponse> createJwtToken(@RequestBody UserDto userDto) throws Exception {
//        return ResponseEntity.ok(jwtService.createJwtToken(userDto));
    }
}
