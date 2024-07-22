package com.kimseungzzang.MiniWorld.Controller;

import com.kimseungzzang.MiniWorld.DTO.DiaryDto;
import com.kimseungzzang.MiniWorld.Security.CustomUserDetails;

import com.kimseungzzang.MiniWorld.Service.DiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DiaryController {
    private final DiaryService diaryService;

    @Autowired
    public DiaryController(DiaryService diaryService) {
        this.diaryService = diaryService;
    }

    @PostMapping("/diary")
    public HttpStatus saveDiary(@RequestBody DiaryDto diaryDto, Authentication authentication){
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String nickname = userDetails.getNickName();
        diaryDto.setNickname(nickname);
        diaryService.save(diaryDto);
        return HttpStatus.OK;
    }

    @GetMapping("/diaryAll/{id}")
    public ResponseEntity<List<DiaryDto>> getAllDiary(@PathVariable Long id){
        return new ResponseEntity<>(diaryService.findAll(id),HttpStatus.OK);
    }
}
