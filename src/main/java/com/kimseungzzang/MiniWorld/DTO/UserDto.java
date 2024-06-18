package com.kimseungzzang.MiniWorld.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserDto {
    private String email;
    private String nickname;
    private String password;
}
