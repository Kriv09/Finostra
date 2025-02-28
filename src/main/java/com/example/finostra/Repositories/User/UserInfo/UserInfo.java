package com.example.finostra.Repositories.User.UserInfo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfo extends JpaRepository<UserInfo, Integer> {
}
