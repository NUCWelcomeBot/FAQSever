package com.faq.javacustomerserver.dao.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "mould", schema = "qa", catalog = "")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@ApiModel("模板类")
public class ModEntity {
    @ApiModelProperty("模板id")
    private Integer id = 0;
    @ApiModelProperty("模板名字")
    private String modName;
    @ApiModelProperty("所属用户")
    private UserEntity userEntity;
    @ApiModelProperty("关联问题")
    private Collection<QAEntity> qaEntities;
    @ApiModelProperty("关联Key")
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

    @Column(name = "mod_name")
    public String getModName() {
        return modName;
    }

    public void setModName(String modName) {
        this.modName = modName;
    }

    @ManyToOne()
    @JsonIgnoreProperties({"modEntities"})
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    @JsonIgnoreProperties({"modEntity"})
    @OneToMany(mappedBy = "modEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Collection<QAEntity> getQaEntities() {
        return qaEntities;
    }

    @JsonIgnoreProperties({"modEntity"})
    @OneToMany(mappedBy = "modEntity", cascade = CascadeType.ALL)
    public Collection<KeyEntity> getKeyEntities() {
        return keyEntities;
    }
    public void setKeyEntities(Collection<KeyEntity> keyEntities) {
        this.keyEntities = keyEntities;
    }
    public void addKeyword(KeyEntity keyEntity){
        this.keyEntities.add(keyEntity);
    }
    public void setQaEntities(Collection<QAEntity> qaEntities) {
        this.qaEntities = qaEntities;
    }

    public boolean equals(ModEntity modEntity) {
        return modEntity.id.equals(this.id);
    }

    @Override
    public String toString() {
        return "ModEntity{" +
                "id=" + id +
                ", modName='" + modName + '\'' +
                ", userEntity=" + userEntity +
                ", qaEntities=" + qaEntities +
                '}';
    }
}
