package com.ktb.clmapiauthen.models.authen;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

@Data
public class AuthenRequest {

    @NotBlank
    @Size(min = 3,max = 40)
    private String username;

    @NotBlank
    @Size(min = 3, max = 40)
    private String password;
}
