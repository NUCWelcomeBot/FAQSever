package com.faq.javacustomerserver.DAO.Mapper;//package com.faq.javacustomerserver.DAO.Mapper;


import com.faq.javacustomerserver.DAO.Model.QAEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface QAMapper extends JpaRepository<QAEntity,Integer> {

    @Modifying
    @Transactional
    @Query(value="delete from QAEntity qa where qa.id =?1")
    void deleteQA(int id);

    QAEntity findByQuestion(String question);
}