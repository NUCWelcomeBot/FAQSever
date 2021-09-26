package com.faq.javacustomerserver.DAO.Mapper;//package com.faq.javacustomerserver.DAO.Mapper;


import com.faq.javacustomerserver.DAO.Model.KeyEntity;
import com.faq.javacustomerserver.DAO.Model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeyMapper extends JpaRepository<KeyEntity,Integer> {
    KeyEntity findByKeyword(String keyword);
}