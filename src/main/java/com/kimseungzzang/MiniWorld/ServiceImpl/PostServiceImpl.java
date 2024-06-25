package com.kimseungzzang.MiniWorld.ServiceImpl;

import com.kimseungzzang.MiniWorld.DTO.PostDto;
import com.kimseungzzang.MiniWorld.Entity.Post;
import com.kimseungzzang.MiniWorld.Entity.User;
import com.kimseungzzang.MiniWorld.Repository.PostRepository;
import com.kimseungzzang.MiniWorld.Repository.UserRepository;
import com.kimseungzzang.MiniWorld.Service.PostService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository, ModelMapper mapper) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Transactional
    @Override
    public void SavePost(PostDto postDto) {
        Post post=mapper.map(postDto,Post.class);
        Optional <User> OpUser=userRepository.findByNickname(postDto.getNickname());
        if(OpUser.isPresent()) {
            User user = OpUser.get();
            post.setUser(user);
            postRepository.save(post);
            List<Post> posts = user.getPosts();
            posts.add(post);
            user.setPosts(posts);
            userRepository.save(user);
            log.info("{},{},{}", post.getContent(), post.getIsSecrete(), post.getId(), post.getUser());

        }

    }

    @Override
    public void DeletePost(Long PostId) {

    }

    @Override
    public void UpdatePost(PostDto postDto, Long PostId) {

    }

    @Override
    public PostDto findById(Long PostId) {
    Optional<Post> OpPost=postRepository.findById(PostId);
    if(OpPost.isPresent())
    {
        Post post=OpPost.get();
        return mapper.map(post,PostDto.class);
    }
    return null;
    }

    @Override
    public List<PostDto> findAll() {

        return postRepository.findAll().stream()
                .map(post -> {
                    PostDto postDto = mapper.map(post, PostDto.class);
                    String nickname = Optional.ofNullable(post.getUser())
                            .map(User::getNickname)
                            .orElse("Anonymous");  // or any default value
                    postDto.setNickname(nickname);
                    return postDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Boolean checkPassword(PostDto postDto) {
        Optional<Post> OpPost=postRepository.findById(postDto.getId());
        if(OpPost.isPresent())
        {
            Post post=OpPost.get();
            if(post.getPassword().equals(postDto.getPassword()))
            {
                return true;
            }
            else return false;
        }
        return false;
    }
}
