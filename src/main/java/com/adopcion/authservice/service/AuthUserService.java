package com.adopcion.authservice.service;


import com.adopcion.authservice.dto.AuthUserDto;
import com.adopcion.authservice.dto.TokenDto;
import com.adopcion.authservice.entity.AuthUser;

public interface AuthUserService {
    AuthUser save(AuthUserDto dto);
    TokenDto login(AuthUserDto dto);
    TokenDto validate(String token);

}
