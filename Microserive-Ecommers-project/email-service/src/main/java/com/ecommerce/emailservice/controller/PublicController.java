package com.ecommerce.emailservice.controller;


import com.ecommerce.emailservice.service.SimpleEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mail")
@RequiredArgsConstructor
public class PublicController {
     private final SimpleEmailService service;


    @GetMapping("/health-check")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> getHealth(){
        return ResponseEntity.ok("working fine");
    }

    @GetMapping("/demo")
    public ResponseEntity<String> demoEmailSend(){
        // call email service ....
        Boolean isSend=service.sendEmail("khakredheeraj@gmail.com","information","hello email service is working fine ...");
        if(isSend)
          return ResponseEntity.ok("email has send successfully");
        return ResponseEntity.ok("email has not  send ... we got error while sending the email");
    }
}
