package com.ktb.clmapiauthen.models.authen;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class AuthenResponse {
    private String accessToken;
    private String messege;
    private Integer code;
    private String result;
    private String transectionID;
}
