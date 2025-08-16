package com.ecommerce.apigateway.extrurnal;


import com.ecommerce.apigateway.dtos.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service") // Replace with actual service URL or use Eureka
public interface UserClient {

    @GetMapping("/api/user/user-name/{username}")
    UserResponse getUserByName(@PathVariable("username") String username);
}