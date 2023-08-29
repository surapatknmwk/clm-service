package com.ktb.clmapiauthen.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import com.ktb.clmapiauthen.models.annotation.Loggable;
import com.ktb.clmapiauthen.models.interfaces.ad.ADResponse;
import com.ktb.clmapiauthen.services.AuthenService;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/simple")
public class SimpleController {

    @Autowired
    private AuthenService authenService;
    
    @Loggable
    @Operation(summary = "[GET] Simple API")
	@GetMapping("/get")
    public ResponseEntity<Object> get() {
        return ResponseEntity.ok().body("OK!");
    }

    @Loggable
    @Operation(summary = "[POST] Authentication Testing")
	@PostMapping("/authen-test")
    public ResponseEntity<ADResponse> authentest() {
        ADResponse response = new ADResponse();
        try{
            response = authenService.ADInterface("19280", "P@ssw0rd");
        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            return ResponseEntity.ok().headers(new HttpHeaders()).body(response);
        }
    }

    
}
