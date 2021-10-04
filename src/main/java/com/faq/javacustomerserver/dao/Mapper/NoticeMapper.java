package com.faq.javacustomerserver.dao.Mapper;

import com.faq.javacustomerserver.dao.Model.NoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeMapper extends JpaRepository<NoticeEntity, Integer> {
}
