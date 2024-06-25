package com.kimseungzzang.MiniWorld.Repository;

import com.kimseungzzang.MiniWorld.Entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
