package com.kimseungzzang.MiniWorld.DTO;

import com.kimseungzzang.MiniWorld.Entity.User;
import lombok.Data;


@Data

public class PostDto {
    private Long id;
    private String nickname;
    private String content;
    private Boolean isSecrete;
    private String password;



}
