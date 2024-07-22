package com.kimseungzzang.MiniWorld.Service;

import com.kimseungzzang.MiniWorld.DTO.UserDto;
import com.kimseungzzang.MiniWorld.Entity.User;
import com.kimseungzzang.MiniWorld.ServiceImpl.ValidateMemberException;
import org.springframework.stereotype.Service;


public interface UserService {
    void saveUser(UserDto userDto);
    User getUserByEmail(String Email);

    UserDto getUserById(Long id);
    public Long join(User user) throws ValidateMemberException;

    String authenticateUser(UserDto userDto);

}
