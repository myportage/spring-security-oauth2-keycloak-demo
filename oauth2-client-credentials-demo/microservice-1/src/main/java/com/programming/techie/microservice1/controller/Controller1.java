package com.programming.techie.microservice1.controller;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class Controller1 {

    private final RestTemplate restTemplate= new RestTemplateBuilder().build();

    @GetMapping("/microservice1/home")
    @ResponseStatus(HttpStatus.OK)
    public String HelloRestTemplate(){
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        HttpHeaders httpHeaders= new HttpHeaders();
        httpHeaders.add("Authorization","Bearer "+jwt.getTokenValue());

        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8084/microservice2/home",
                HttpMethod.GET,
                new HttpEntity<>(httpHeaders),
                String.class);

        return "PUY- Message from Microservice 2 is :"+ response.getBody();
    }
}
