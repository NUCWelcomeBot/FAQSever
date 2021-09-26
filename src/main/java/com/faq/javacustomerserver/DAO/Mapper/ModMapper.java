package com.faq.javacustomerserver.DAO.Mapper;


import com.faq.javacustomerserver.DAO.Model.ModEntity;
import com.faq.javacustomerserver.DAO.Model.QAEntity;
import com.faq.javacustomerserver.DAO.Model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ModMapper extends JpaRepository<ModEntity, Integer> {
    ModEntity findByModName(String modName);
}