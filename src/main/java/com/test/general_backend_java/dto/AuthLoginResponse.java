package com.test.general_backend_java.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"username", "message", "jwt", "status"})
public record AuthLoginResponse(String username, String message, String jwt, boolean status) {
}
