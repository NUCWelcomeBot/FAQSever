package com.faq.javacustomerserver.dao.Mapper;//package com.faq.javacustomerserver.DAO.Mapper;


import com.faq.javacustomerserver.dao.Model.KeyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface KeyMapper extends JpaRepository<KeyEntity,Integer> {
    @Modifying
    @Transactional
    @Query(value="delete from KeyEntity qa where qa.id =?1")
    void deleteKey(int id);
    KeyEntity getByKeyword(String keyword);
}