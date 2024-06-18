package com.kimseungzzang.MiniWorld.Controller;

import com.kimseungzzang.MiniWorld.DTO.UserDto;
import com.kimseungzzang.MiniWorld.Entity.User;
import com.kimseungzzang.MiniWorld.Service.UserService;
import com.kimseungzzang.MiniWorld.ServiceImpl.ValidateMemberException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;



    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/login")
    public ResponseEntity<String> getMemberProfile(
             @RequestBody UserDto request
    ) {
        String token = this.userService.authenticateUser(request);
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }


    @PostMapping("/join")
    public ResponseEntity<Long> addMember( @RequestBody UserDto userDto) throws ValidateMemberException {
        User entity = modelMapper.map(userDto, User.class);
        Long id = userService.join(entity);
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }

}
