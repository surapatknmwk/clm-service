package com.ktb.clmapiauthen.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import com.ktb.clmapiauthen.models.annotation.Loggable;
import com.ktb.clmapiauthen.models.authen.AuthenRequest;
import com.ktb.clmapiauthen.models.authen.AuthenResponse;
import com.ktb.clmapiauthen.services.AuthenService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.*;
import com.ktb.clmapiauthen.util.StringUtil;
import com.ktb.clmapiauthen.util.HeaderUtil;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthenController {
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AuthenService authenService;

    @Loggable
    @Operation(summary = "[POST] Authentication")
	@PostMapping
    public ResponseEntity<AuthenResponse> authen(@Valid @RequestBody AuthenRequest request) {
        Integer code = 500;
        String messege = "Login Error";
        AuthenResponse response = new AuthenResponse();
        HeaderUtil headerUtil = new HeaderUtil(new HttpHeaders());
        String uuid = UUID.randomUUID().toString();
        String result = null;

        try{
            headerUtil.add("uuid", uuid);
            result = authenService.authen(request, uuid);
            
            if(StringUtil.isNotEmpty(result)){
                code = 200;
                messege = "login success";
            }  
        }catch (Exception e) {
            code = 500;
            messege = e.getMessage();
            e.printStackTrace();
        }finally{
            response.setAccessToken(result);
            response.setResult(messege);
            response.setCode(code);
            response.setMessege(messege);
            response.setTransectionID(uuid);
            return ResponseEntity.status(code).headers(headerUtil.response()).body(response);
        }

    }

    @Loggable
    @Operation(summary = "[POST] Refresh Token")
	@PostMapping("/refresh-token")
    public ResponseEntity<AuthenResponse> RefreshToken(AuthenRequest request) {
        
        return ResponseEntity.ok().body(null);
    }

    @Loggable
    @Operation(summary = "[POST] Logout")
	@PostMapping("/logout")
    public ResponseEntity<AuthenResponse> logout(AuthenRequest request) {
        
        return ResponseEntity.ok().body(null);
    }


}


