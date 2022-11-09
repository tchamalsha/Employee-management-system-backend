package com.lnt.ems.api.repository;

import com.lnt.ems.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    User findUserById(Integer id);

    @Query("SELECT user.password from User user where user.password=?1")
    String getPassword(Integer id);

}
