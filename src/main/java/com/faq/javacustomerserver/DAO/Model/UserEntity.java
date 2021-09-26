package com.faq.javacustomerserver.DAO.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "user", schema = "qa", catalog = "")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@ApiModel("用户类")
public class UserEntity {
    @ApiModelProperty("用户id")
    private Integer id = 0;
    @ApiModelProperty("用户uuid")
    private Integer uuid = 0;
    @ApiModelProperty("所拥有的问题模板")
    private Collection<ModEntity> modEntities;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "uuid")
    public Integer getUuid() {
        return uuid;
    }

    public void setUuid(Integer uuid) {
        this.uuid = uuid;
    }


    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    public Collection<ModEntity> getModEntities() {
        return modEntities;
    }

    public void setModEntities(Collection<ModEntity> modEntities) {
        this.modEntities = modEntities;
    }

}
