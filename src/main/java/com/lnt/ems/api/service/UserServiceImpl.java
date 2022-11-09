package com.lnt.ems.api.service;


import com.lnt.ems.api.model.PersonalDetails;
import com.lnt.ems.api.model.User;
import com.lnt.ems.api.repository.PersonalDetailsRepository;
import com.lnt.ems.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl {

    private final PersonalDetailsRepository personalDetailsRepository;
    private final UserRepository userRepository;

    public void addPersonalDetails(PersonalDetails personalDetails){
        personalDetailsRepository.save(personalDetails);
    }

    public void addUser(User user){
        userRepository.save(user);
    }

    public Boolean isLoginSuccess(Integer id,String password){
        return (userRepository.getPassword(id)==password);
    }


}
