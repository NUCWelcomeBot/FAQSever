package com.faq.javacustomerserver.DAO.Model;/*
 *   #-*- coding = utf-8 -*-
 *   #@Time: 2021/9/16 18:40
 *   #@Author：dodo
 *   #@Software：IntelliJ IDEA
 */



import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "keyword", schema = "qa", catalog = "")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@ApiModel("关键词类")
public class KeyEntity {
    @ApiModelProperty("关键词id")
    private Integer id;
    @ApiModelProperty("关键词")
    private String keyword;
    @ApiModelProperty("所属mod")
    private ModEntity modEntity;
    @ApiModelProperty("所关联问题")
    private Collection<QAEntity> qaEntities;

    public void setCount(Integer count) {
        this.count += count;
    }

    @Column(name = "count",nullable = false)
    public Integer getCount() {
        return count;
    }
    public void setCount() {
        ++this.count;
    }

    @ManyToOne()
    @JsonIgnore
    @JoinColumn(name = "mod_id", referencedColumnName = "id")
    @JsonIgnoreProperties({"modEntity"})
    public ModEntity getModEntity() {
        return modEntity;
    }
    public void setModEntity(ModEntity modEntity) {
        this.modEntity = modEntity;
    }

    @ApiModelProperty("关键词调用次数")
    private Integer count = 0;
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "keyword")
    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @ManyToMany(mappedBy = "keyEntities", cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    public Collection<QAEntity> getQaEntities() {
        if(this.qaEntities == null){
            this.qaEntities = new ArrayList();
        }
        return this.qaEntities;
    }
    public void addQaEntities(QAEntity qaEntity){
        this.getQaEntities().add(qaEntity);
    }
    public void setQaEntities(Collection<QAEntity> qaEntities) {
        this.qaEntities = qaEntities;
    }

}
