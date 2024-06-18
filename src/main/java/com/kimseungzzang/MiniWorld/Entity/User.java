package com.kimseungzzang.MiniWorld.Entity;

import com.kimseungzzang.MiniWorld.EnumType.RoleType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.autoconfigure.web.WebProperties;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String username;
    private String encodedPassword;

    @Enumerated(EnumType.STRING)
    private RoleType role;

}
