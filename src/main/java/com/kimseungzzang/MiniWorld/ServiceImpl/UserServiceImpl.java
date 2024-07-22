package com.kimseungzzang.MiniWorld.ServiceImpl;

import com.kimseungzzang.MiniWorld.DTO.CustomUserInfoDto;
import com.kimseungzzang.MiniWorld.DTO.UserDto;
import com.kimseungzzang.MiniWorld.Entity.User;
import com.kimseungzzang.MiniWorld.EnumType.RoleType;
import com.kimseungzzang.MiniWorld.Repository.UserRepository;
import com.kimseungzzang.MiniWorld.Service.UserService;
import com.kimseungzzang.MiniWorld.Util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final ModelMapper modelMapper;

    private final JwtUtil jwtUtil;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder encoder, ModelMapper modelMapper, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.modelMapper = modelMapper;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void saveUser(UserDto userDto) {

        userRepository.save(modelMapper.map(userDto,User.class));

    }

    @Override
    public User getUserByEmail(String Email) {
        Optional<User> user=userRepository.findByEmail(Email);

        return user.get();
    }

    @Override
    public UserDto getUserById(Long id) {
        Optional<User> user=userRepository.findById(id);
        if (user.isPresent()) {
            log.info(user.get().getNickname());

            UserDto userDto = modelMapper.map(user.get(), UserDto.class);
            return userDto;
        }
    return null;
    }

    @Override
    public Long join(User user) throws ValidateMemberException {
        Optional<User> vailUser =userRepository.findByEmail(user.getEmail());
        if(vailUser.isPresent())
        {
            throw new ValidateMemberException("This member email is already exist. " + user.getEmail());

        }
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole(RoleType.USER);
        userRepository.save(user);
        return user.getId();



    }


    @Override
    public String authenticateUser(UserDto userDto) {
        String email = userDto.getEmail();
        String password = userDto.getPassword();
        Optional<User> Opuser = userRepository.findByEmail(email);
        if(Opuser.isEmpty()) {
            throw new UsernameNotFoundException("이메일이 존재하지 않습니다.");
        }
        User user=Opuser.get();
        // 암호화된 password를 디코딩한 값과 입력한 패스워드 값이 다르면 null 반환
        if(!encoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }

        CustomUserInfoDto info = modelMapper.map(user, CustomUserInfoDto.class);

        String accessToken = jwtUtil.createAccessToken(info);
        return accessToken;
    }
    }

