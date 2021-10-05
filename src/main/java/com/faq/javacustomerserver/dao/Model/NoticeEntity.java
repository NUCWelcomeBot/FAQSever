package com.faq.javacustomerserver.dao.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Entity
@Table(name = "notice", schema = "qa", catalog = "")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@ApiModel("通知类")
public class NoticeEntity {
    @ApiModelProperty("通知id")
    private Integer id = 0;
    @ApiModelProperty("通知内容")
    private String content;
    @ApiModelProperty("通知标题")
    private String title;
    @ApiModelProperty("编辑时间")
    private String time;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Column(name = "time")
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
