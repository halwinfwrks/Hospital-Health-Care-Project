package app.develope.dto;

import lombok.Data;

@Data
public class RefreshTokenRequest {
    private String username;
    private String refreshToken;
}

