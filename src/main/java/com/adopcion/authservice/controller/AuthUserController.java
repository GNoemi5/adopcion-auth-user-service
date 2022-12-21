package com.adopcion.authservice.controller;

import com.adopcion.authservice.dto.AuthUserDto;
import com.adopcion.authservice.dto.TokenDto;
import com.adopcion.authservice.entity.AuthUser;
import com.adopcion.authservice.service.AuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/auth")
public class AuthUserController {

    @Autowired
    private AuthUserService authUserService;

    @PostMapping("login")
    public ResponseEntity<TokenDto> login(@RequestBody AuthUserDto dto){
        TokenDto tokenDto = authUserService.login(dto);

        if(tokenDto==null)
            return  ResponseEntity.badRequest().build();

        return ResponseEntity.ok(tokenDto);
    }

    @PostMapping("validate")
    public ResponseEntity<TokenDto> validate(@RequestParam("token") String token){
        TokenDto tokenDto = authUserService.validate(token);
        if(tokenDto==null)
            return  ResponseEntity.badRequest().build();

        return ResponseEntity.ok(tokenDto);
    }

    @PostMapping("create")
    public ResponseEntity<AuthUser> create(@RequestBody AuthUserDto dto){
        AuthUser authUser = authUserService.save(dto);
        if(authUser==null)
            return ResponseEntity.badRequest().build();

        return ResponseEntity.ok(authUser);
    }

}
