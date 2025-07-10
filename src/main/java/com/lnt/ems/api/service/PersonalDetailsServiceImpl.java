package com.lnt.ems.api.service;

import com.lnt.ems.api.model.PersonalDetails;
import com.lnt.ems.api.repository.PersonalDetailsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@Slf4j
public class PersonalDetailsServiceImpl {

    private final PersonalDetailsRepository personalDetailsRepository;

    public PersonalDetailsServiceImpl(PersonalDetailsRepository personalDetailsRepository) {
        this.personalDetailsRepository = personalDetailsRepository;
    }

    public void addPersonalDetails(PersonalDetails personalDetails){
        personalDetailsRepository.save(personalDetails);
    }
}
