package com.faq.javacustomerserver.DAO.Mapper;


import com.faq.javacustomerserver.DAO.Model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface UserMapper extends JpaRepository<UserEntity,Integer> {
    UserEntity findByUuid(Integer uuid);

    @Transactional
    void removeByUuid(Integer uuid);
}