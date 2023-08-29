package com.ktb.clmapiauthen.util;
import org.springframework.http.HttpHeaders;

// @Configuration
public class HeaderUtil {

    private String CACH_CONTROL = "no-cache, no-store, must-revalidate";
    private String PRAGMA = "no-cache";
    private String EXPIRES = "0";

    private HttpHeaders headers = new HttpHeaders();

    public HeaderUtil(){
        this.headers = new HttpHeaders();
    }

    public HeaderUtil(HttpHeaders headers){
        this.headers = headers;
        setDefault();
    }

    public HeaderUtil(HttpHeaders headers,String uuid){
        this.headers = headers;
        setDefault();
        this.headers.add("Trans", uuid);
    }

    public void setDefault(){
        this.headers.add("Cache-Control", CACH_CONTROL);
        this.headers.add("Pragma", PRAGMA);
        this.headers.add("Expires", EXPIRES);
    }

    public void add(String key, String value){
        this.headers.add(key, value);
    }

    public HttpHeaders response(){
        return this.headers;
    }

    
    
    
}
