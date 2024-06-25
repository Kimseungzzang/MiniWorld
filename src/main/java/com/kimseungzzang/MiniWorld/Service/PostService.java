package com.kimseungzzang.MiniWorld.Service;

import com.kimseungzzang.MiniWorld.DTO.PostDto;

import java.util.List;

public interface PostService {
    void SavePost(PostDto postDto);
    void DeletePost(Long PostId);

    void UpdatePost(PostDto postDto,Long PostId);
    PostDto findById(Long PostId);
    List<PostDto> findAll();

    Boolean checkPassword(PostDto postDto);




}
