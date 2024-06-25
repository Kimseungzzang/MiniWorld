package com.kimseungzzang.MiniWorld.Controller;

import com.kimseungzzang.MiniWorld.DTO.PostDto;
import com.kimseungzzang.MiniWorld.Security.CustomUserDetails;
import com.kimseungzzang.MiniWorld.Service.PostService;
import jdk.jshell.Snippet;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/post")
    public void SavePost(@RequestBody  PostDto postDto, Authentication authentication){
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String nickname = userDetails.getNickName();
        postDto.setNickname(nickname);
        postService.SavePost(postDto);
;
    }
    @GetMapping("/post")
    public ResponseEntity<List<PostDto>> getAllPosts()
    {
        return new ResponseEntity<>(postService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<PostDto> getPost(@PathVariable Long id)
    {
        return  new ResponseEntity<>(postService.findById(id),HttpStatus.OK);
    }
    @PostMapping("/post/check")
    public ResponseEntity<Boolean> checkPassword(@RequestBody PostDto postDto){
        Boolean result=postService.checkPassword(postDto);
        if(result) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>(result,HttpStatus.FORBIDDEN);
    }
}
