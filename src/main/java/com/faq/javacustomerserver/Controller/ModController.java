package com.faq.javacustomerserver.Controller;/*
 *   #-*- coding = utf-8 -*-
 *   #@Time: 2021/9/17 13:36
 *   #@Author：dodo
 *   #@Software：IntelliJ IDEA
 */


import com.alibaba.fastjson.JSONObject;
import com.faq.javacustomerserver.DAO.Mapper.ModMapper;
import com.faq.javacustomerserver.DAO.Mapper.UserMapper;
import com.faq.javacustomerserver.DAO.Model.ModEntity;
import com.faq.javacustomerserver.DAO.Model.UserEntity;
import com.faq.javacustomerserver.utils.Code;
import com.faq.javacustomerserver.utils.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.List;
import java.util.Objects;

@Api(tags = "模板管理")
@RestController
@RequestMapping("/mod")
public class ModController {
    @Autowired
    ModMapper modMapper;
    @Autowired
    UserMapper userMapper;

    @ApiOperation(value = "保存模板", notes = "需要传入模板的名称(modName)和用户(uuid)")
    @PostMapping("/saveMod")
    public ResponseResult<ModEntity> saveMod(@RequestBody JSONObject jsonObject) {
        String modName = jsonObject.getString("modName");
        Integer uuid = jsonObject.getInteger("uuid");
        ModEntity modEntity = new ModEntity();
        UserEntity userEntity = userMapper.findByUuid(uuid);
        if(Objects.requireNonNull(modEntity).getUserEntity().equals(userEntity)
                && modMapper.findByModName(modName) != null){
            throw new KeyAlreadyExistsException("该问题已存在");
        }else {
            modEntity.setModName(modName);
            modEntity.setUserEntity(userEntity);
            modMapper.save(modEntity);
        }
        return new ResponseResult<>(Code.SUCCESS, modEntity);
    }

    @ApiOperation(value = "通过模板名称获取模板", notes = "需要传入模板的名称(modName)")
    @GetMapping("/getMod")
    public ResponseResult<ModEntity> getMod(@RequestBody JSONObject jsonObject) {
        String modName = jsonObject.getString("modName");
        ModEntity modEntity = modMapper.findByModName(modName);
        return new ResponseResult<>(Code.SUCCESS, modEntity);
    }

    @ApiOperation(value = "获取全部模板")
    @GetMapping("/getAllMod")
    public ResponseResult<List<ModEntity>> getAllUser() {
        List<ModEntity> modEntities = modMapper.findAll();
        return new ResponseResult<>(Code.SUCCESS, modEntities);
    }

    @ApiOperation(value = "通过模板id删除模板", notes = "需要传入模板的id(id)")
    @DeleteMapping("/deleteMod")
    @Transactional
    public ResponseResult<String> deleteMod(@RequestBody JSONObject jsonObject) {
        Integer id = jsonObject.getInteger("id");
        modMapper.deleteById(id);
        return new ResponseResult<>(Code.SUCCESS, "删除成功！");
    }

    @ApiOperation(value = "通过模板id修改模板", notes = "需要传入模板的id(id)和模板的名称(modName)")
    @PostMapping("/updateMod")
    public ResponseResult<String> updateMod(@RequestBody JSONObject jsonObject) {
        Integer id = jsonObject.getInteger("id");
        String modName = jsonObject.getString("modName");
        ModEntity modEntity = modMapper.getById(id);
        modEntity.setModName(modName);
        modMapper.save(modEntity);
        return new ResponseResult<>(Code.SUCCESS, "删除成功！");
    }
}
