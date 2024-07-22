package com.kimseungzzang.MiniWorld.ServiceImpl;


import com.kimseungzzang.MiniWorld.DTO.DiaryDto;
import com.kimseungzzang.MiniWorld.Entity.Diary;
import com.kimseungzzang.MiniWorld.Entity.Post;
import com.kimseungzzang.MiniWorld.Entity.User;
import com.kimseungzzang.MiniWorld.Repository.DiaryRepository;
import com.kimseungzzang.MiniWorld.Repository.UserRepository;
import com.kimseungzzang.MiniWorld.Service.DiaryService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DiaryServiceImpl implements DiaryService {

    private final DiaryRepository diaryRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public DiaryServiceImpl(DiaryRepository diaryRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.diaryRepository = diaryRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<DiaryDto> findAll(Long userId) {
      return diaryRepository.findByUserId(userId).stream().map(
              diary -> {
                  DiaryDto diaryDto=modelMapper.map(diary,DiaryDto.class);
                  String nickname = Optional.ofNullable(diary.getUser())
                          .map(User::getNickname)
                          .orElse("Anonymous");  // or any default value
                  diaryDto.setNickname(nickname);
                  return diaryDto;
              }
      ).collect(Collectors.toList());
    }

    @Override
    public DiaryDto findById(Long id) {
        return modelMapper.map(diaryRepository.findById(id),DiaryDto.class);

    }

    @Override
    public void save(DiaryDto diaryDto) {
        Diary diary=modelMapper.map(diaryDto,Diary.class);

        //이미지 저장
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = "image_" + timeStamp + ".jpg"; // 파일 확장자에 맞게 변경
        String directoryPath = "../miniworld_frontend/public/images/"; // 실제 저장할 디렉토리
        File directory = new File(directoryPath);
        // 디렉토리가 존재하지 않으면 생성
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // 이미지 데이터(dataURL) 추출
        String base64Image = diaryDto.getImage();
        if(base64Image!=null) {


            // base64로 인코딩된 데이터를 디코딩하여 바이너리 데이터로 변환
            byte[] imageBytes = Base64.getDecoder().decode(base64Image);
            String imageurl =  "/images/" + fileName;
            diary.setImage(imageurl);
            // 저장할 파일 경로 지정
            File outputFile = new File(directoryPath+fileName);
            log.info("파일 저장 완료");
            try (OutputStream outputStream = new FileOutputStream(outputFile)) {
                outputStream.write(imageBytes);
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
        //유저 정보 저장부분
        Optional<User> OpUser=userRepository.findByNickname(diaryDto.getNickname());
        if(OpUser.isPresent()) {
            User user = OpUser.get();
            diary.setUser(user);
            diaryRepository.save(diary);
            List<Diary> diaries = user.getDiaries();
            diaries.add(diary);
            user.setDiaries(diaries);
            userRepository.save(user);


        }


    }

    @Override
    public DiaryDto update(Long id, DiaryDto diaryDto) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
