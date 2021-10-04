package com.faq.javacustomerserver.dao.Mapper;//package com.faq.javacustomerserver.DAO.Mapper;


import com.faq.javacustomerserver.dao.Model.KeyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeyMapper extends JpaRepository<KeyEntity,Integer> {
    KeyEntity findByKeyword(String keyword);
}