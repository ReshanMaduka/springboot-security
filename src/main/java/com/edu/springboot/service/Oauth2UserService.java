package com.edu.springboot.service;

import com.edu.springboot.dto.common.TokenDTO;

public interface Oauth2UserService {

    TokenDTO getUserTokenData(String username);

}
