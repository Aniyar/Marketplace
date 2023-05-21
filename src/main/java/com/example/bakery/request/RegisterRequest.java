package com.example.bakery.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {
    @NotNull
    private String firstname;
    private String lastname;
    @NotNull
    private String phoneNumber;
    @NotNull
    private String password;
}
