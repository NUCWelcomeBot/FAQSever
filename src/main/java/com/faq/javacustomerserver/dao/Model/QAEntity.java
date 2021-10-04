package com.faq.javacustomerserver.dao.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "qa", schema = "qa", catalog = "")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@ApiModel("问题类")
public class QAEntity {
    @ApiModelProperty("问题id")
    private Integer id = 0;
    @ApiModelProperty("问题")
    private String question;
    @ApiModelProperty("答案")
    private String answer;
    @ApiModelProperty("问答查询次数")
    private Integer count = 0;
    @ApiModelProperty("所属问题模板")
    private ModEntity modEntity;
    @ApiModelProperty("关键词")
    private Collection<KeyEntity> keyEntities;
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "question")
    public String getQuestion() {

        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    @Basic
    @Column(name = "answer")
    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Basic
    @Column(name = "count",nullable = true)
    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count += count;
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

    @ManyToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    @JsonIgnore
    @JoinTable(
            name = "sys_qa_key",
            joinColumns= {
                    @JoinColumn(name = "sys_qa_id", referencedColumnName = "id",
                        foreignKey = @ForeignKey(name = "qa",value=ConstraintMode.CONSTRAINT)
                    )
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "sys_key_id", referencedColumnName = "id",
                        foreignKey=@ForeignKey(name = "keys",value=ConstraintMode.CONSTRAINT)
                    )
            }
    )
    public Collection<KeyEntity> getKeyEntities() {
        if (keyEntities == null){
            this.keyEntities = new ArrayList();
        }
        return keyEntities;
    }
    public void addKeyEntities(KeyEntity keyEntity){
        this.getKeyEntities().add(keyEntity);
    }
    public void setKeyEntities(Collection<KeyEntity> keyEntities) {
        this.keyEntities = keyEntities;
    }
}
