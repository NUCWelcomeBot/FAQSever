package com.faq.javacustomerserver.dao.Mapper;


import com.faq.javacustomerserver.dao.Model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface UserMapper extends JpaRepository<UserEntity,Integer> {
    UserEntity findByUuid(Integer uuid);

    @Transactional
    void removeByUuid(Integer uuid);
}