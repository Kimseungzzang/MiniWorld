package com.kimseungzzang.MiniWorld.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiaryDto {
    private Long id;
    private String nickname;
    private String title;
    private String content;
    private String image;
    private LocalDateTime time;

}
