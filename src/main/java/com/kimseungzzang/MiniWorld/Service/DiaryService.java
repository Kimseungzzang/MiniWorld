package com.kimseungzzang.MiniWorld.Service;


import com.kimseungzzang.MiniWorld.DTO.DiaryDto;
import org.springframework.stereotype.Service;

import java.util.List;


public interface DiaryService {
    List<DiaryDto> findAll(Long UserId);

    DiaryDto findById(Long id);

   void save(DiaryDto diaryDto);

    DiaryDto update(Long id,DiaryDto diaryDto);

    void delete(Long id);

}
