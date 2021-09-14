package com.edu.springboot.service.impl;

import com.edu.springboot.dto.common.TokenDTO;
import com.edu.springboot.exception.CustomOauthException;
import com.edu.springboot.repository.UserRepository;
import com.edu.springboot.service.Oauth2UserService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service(value = "userService")
public class Oauth2UserServiceImpl implements UserDetailsService, Oauth2UserService {

    private static final Logger LOGGER = LogManager.getLogger(Oauth2UserServiceImpl.class);

    private final UserRepository userRepository;

    @Autowired
    public Oauth2UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public TokenDTO getUserTokenData(String username) {

        try {
            LOGGER.info("Find Username  : " + username);

            TokenDTO tokenDTO = new TokenDTO();

            com.edu.springboot.entity.User user = userRepository.findByUsername(username);

            if (user == null) {
                LOGGER.error("User Details not found : " + username);
                throw new CustomOauthException("The username you entered is incorrect.");
            }

            tokenDTO.setId(user.getId());
            tokenDTO.setName(user.getName());
            tokenDTO.setUserRole(user.getUserRole());

            return tokenDTO;

        } catch (Exception e) {
            LOGGER.error("Function getUserTokenData  : " + e.getMessage());
            throw e;
        }
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        try {

            com.edu.springboot.entity.User user = userRepository.findByUsername(s);

            if (user == null) {
                LOGGER.error("Invalid Grant  : Bad credentials, username : " + s);
                throw new CustomOauthException("Invalid credentials");
            }

            return new User(user.getUsername(), user.getPassword(), getUserAuthority(user));


        } catch(Exception e){
            LOGGER.error("Function loadUserByUsername  : " + e.getMessage());
            throw e;
        }
    }

    private List<SimpleGrantedAuthority> getUserAuthority(com.edu.springboot.entity.User user) {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getUserRole()));
    }

}
