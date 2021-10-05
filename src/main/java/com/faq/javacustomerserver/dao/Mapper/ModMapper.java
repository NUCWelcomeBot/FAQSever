package com.faq.javacustomerserver.dao.Mapper;


import com.faq.javacustomerserver.dao.Model.ModEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ModMapper extends JpaRepository<ModEntity, Integer> {

    @Modifying
    @Transactional
    @Query(value = "delete from QAEntity qa where qa.modEntity.id =?1")
    void deleteModQa(int id);

    @Modifying
    @Transactional
    @Query(value = "delete from KeyEntity ke where ke.modEntity.id =?1")
    void deleteModKey(int id);

    @Modifying
    @Transactional
    @Query(value = "delete from ModEntity m where m.id =?1")
    void deleteMod(int id);

}