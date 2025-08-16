package com.ecommerce.apigateway.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserResponse {

    private String id;
    private String userName;
    private String email;
    private String password;
    private String address;
    private String mobileNumber;
    private List<String> roles;

}
