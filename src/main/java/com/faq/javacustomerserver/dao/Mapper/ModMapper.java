package com.faq.javacustomerserver.dao.Mapper;


import com.faq.javacustomerserver.dao.Model.ModEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModMapper extends JpaRepository<ModEntity, Integer> {
    ModEntity findByModName(String modName);
}