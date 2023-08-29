package com.ktb.clmapiauthen.services;
import com.ktb.clmapiauthen.entity.User;
import com.ktb.clmapiauthen.models.authen.AuthenRequest;
import com.ktb.clmapiauthen.models.interfaces.ad.ADResponse;

import javax.naming.NamingException;

import org.springframework.stereotype.Component;

@Component
public interface AuthenService {
    String authen(AuthenRequest request, String uuid);
    ADResponse ADInterface(String username ,String password) throws NamingException;
    User findUserByUsername(String username, String password);
}
