package com.adopcion.authservice.service;

import com.adopcion.authservice.dto.AuthUserDto;
import com.adopcion.authservice.dto.TokenDto;
import com.adopcion.authservice.entity.AuthUser;
import com.adopcion.authservice.repository.AuthUserRepository;
import com.adopcion.authservice.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthUserServiceImpl implements AuthUserService{
    @Autowired
    private AuthUserRepository authUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public AuthUser save(AuthUserDto dto) {
        Optional<AuthUser> user = authUserRepository.findByUserName(dto.getUserName());
        if(user.isPresent()) return null;

        String password = passwordEncoder.encode(dto.getPassword());
        AuthUser authUser = AuthUser.builder()
                .userName(dto.getUserName())
                .password(password)
                .rol("user")
                .build();

        return authUserRepository.save(authUser);
    }

    @Override
    public TokenDto login(AuthUserDto dto) {
        Optional<AuthUser> user = authUserRepository.findByUserName(dto.getUserName());
        if(!user.isPresent())
            return null;
        if(passwordEncoder.matches(dto.getPassword(),user.get().getPassword()))
            return TokenDto.builder()
                    .userName(user.get().getUserName())
                    .rol(user.get().getRol())
                    .token(jwtProvider.createToken((user.get())))
                    .build();

        return null;
    }

    @Override
    public TokenDto validate(String token) {
        if(!jwtProvider.validate(token))
            return null;

        String userName = jwtProvider.getUserNameFromToken(token);

        if(!authUserRepository.findByUserName(userName).isPresent())
            return null;

        return TokenDto.builder()
                .token(token)
                .build();
    }



}
