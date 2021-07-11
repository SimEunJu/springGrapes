package com.sej.grapes.dto.jwt;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenDto {

    private String accessToken;
    private String type = "Bearer";
    private String refreshToken;

    private String email;
}
