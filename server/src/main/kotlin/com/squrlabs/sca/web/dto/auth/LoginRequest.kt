package com.squrlabs.sca.web.dto.auth

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class LoginRequest(
    @NotBlank @Email val email: String,
    @NotBlank val password: String
)