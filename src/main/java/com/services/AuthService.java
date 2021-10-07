package com.services;

import com.config.jwt.JwtProvider;
import com.dto.UserDto;
import com.entities.User;
import com.response.BaseResponse;
import com.response.ResponseCode;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtProvider jwtProvider;

    public BaseResponse login(User user) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            UserDto userDto = modelMapper.map(principal, UserDto.class);
            String token = jwtProvider.generateToken(userDto);
            return BaseResponse.success(token);
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponse(ResponseCode.ERROR);
        }
    }
}
