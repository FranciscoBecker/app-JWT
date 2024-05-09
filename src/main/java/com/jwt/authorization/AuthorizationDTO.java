package com.jwt.authorization;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AuthorizationDTO {

    private String Login;

    private String password;
}
