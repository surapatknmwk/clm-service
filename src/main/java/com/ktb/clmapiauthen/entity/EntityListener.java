package com.ktb.clmapiauthen.entity;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;
import com.ktb.clmapiauthen.util.AppUtil;
import com.ktb.clmapiauthen.util.DateUtil;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.Field;
import org.springframework.util.ReflectionUtils;

@Log4j2
@Component
public class EntityListener {

    @PrePersist
    public void prePersistFunction(Object object) {
        this.assignValueToCommonFields(object, "CREATE");
    }

    @PreUpdate
    public void preUpdateFunction(Object object) {
        this.assignValueToCommonFields(object,"UPDATE");
    }
    
    @SneakyThrows
    private void assignValueToCommonFields(Object arg, String status) {

        // String user = null;
        // Authentication authen = SecurityContextHolder.getContext().getAuthentication();
        // log.info("authen : {}",authen);
        // if (AppUtil.isNotNull(authen) && authen.getPrincipal() != "anonymousUser") {
        // if (AppUtil.isNotNull(authen)) {
        //     UserDetails userDetails = (UserDetails) authen.getPrincipal();
        //     log.info("userDetails : {}",userDetails);
        //     if (AppUtil.isNotNull(userDetails) && AppUtil.isNotNull(userDetails.getUsername())) {
        //         user = userDetails.getUsername();
        //         log.info("user : {}",user);
        //     }
        // }

        // if (status.equals("CREATE")) {
        //     BeanUtils.setProperty(arg, "createdBy", user != null ? user : "SYSTEM");
        //     BeanUtils.setProperty(arg, "createdDate", DateUtil.getCurrentDate());
        // }else{
        //     BeanUtils.setProperty(arg, "updatedBy", user != null ? user : "SYSTEM");
        //     BeanUtils.setProperty(arg, "updatedDate", DateUtil.getCurrentDate());
        // }

        // Class<?> cls = arg.getClass();
        // for (Field field : cls.getDeclaredFields()) {
            
        //     Field strField = ReflectionUtils.findField(cls, field.getName());
        //     if (strField.getType().equals(String.class)) {

        //         strField.setAccessible(true);
        //         Object value = ReflectionUtils.getField(strField, arg);

        //         if (AppUtil.isNotNull(value) && AppUtil.isEmpty(value.toString())) {
        //             ReflectionUtils.makeAccessible(strField); //set null when emptyString
        //             ReflectionUtils.setField(strField, arg, null);
        //         }
        //     }
        // }
    }
}

