package com.example.finostra.Repositories.User.UserInfo;


import com.example.finostra.Entity.User.UserInfo.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {

    UserInfo save(UserInfo userInfo);
}
