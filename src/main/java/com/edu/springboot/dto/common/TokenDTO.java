package com.edu.springboot.dto.common;

import com.edu.springboot.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TokenDTO {

    private long id;
    private String name;
    private UserRole userRole;
    private Date createDateTime;
    private Long lastLogoutTime;

}
