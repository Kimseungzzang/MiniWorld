package com.kimseungzzang.MiniWorld.Repository;

import com.kimseungzzang.MiniWorld.Entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiaryRepository extends JpaRepository<Diary,Long> {
    List<Diary> findByUserId(Long userId);
}
