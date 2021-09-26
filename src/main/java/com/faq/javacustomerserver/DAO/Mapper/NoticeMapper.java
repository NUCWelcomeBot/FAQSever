package com.faq.javacustomerserver.DAO.Mapper;

import com.faq.javacustomerserver.DAO.Model.NoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeMapper extends JpaRepository<NoticeEntity, Integer> {
}
