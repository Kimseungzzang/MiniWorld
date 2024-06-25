package com.kimseungzzang.MiniWorld.DTO;

import com.kimseungzzang.MiniWorld.EnumType.RoleType;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomUserInfoDto extends UserDto{
    private Long userId;

    private String email;

    private String nickname;

    private String password;

    private RoleType role;

}
