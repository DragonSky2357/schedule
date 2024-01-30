package com.dragonsky.schedule.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Member extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    //@Pattern(regexp = "(?=.*[0-9])(?=.*[a-z]).{4,10}", message = "username는 8~10자 소문자, 숫자를 사용하세요.")
    private String username;

    @Column(nullable = false)
    //@Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z]).{8,15}", message = "password는 8~15자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String password;

    @OneToMany(mappedBy = "member")
    private List<Schedule> scheduleList = new ArrayList<>();
    public Member(String username, String password){
        this.username = username;
        this.password = password;
    }
}
