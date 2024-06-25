package com.kimseungzzang.MiniWorld.Repository;

import com.kimseungzzang.MiniWorld.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String Email);
    Optional<User> findByNickname(String Nickname);


}
