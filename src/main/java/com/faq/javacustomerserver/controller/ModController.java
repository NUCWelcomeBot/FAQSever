package com.faq.javacustomerserver.controller;/*
 *   #-*- coding = utf-8 -*-
 *   #@Time: 2021/9/17 13:36
 *   #@Author：dodo
 *   #@Software：IntelliJ IDEA
 */


import com.alibaba.fastjson.JSONObject;
import com.faq.javacustomerserver.dao.Mapper.ModMapper;
import com.faq.javacustomerserver.dao.Mapper.UserMapper;
import com.faq.javacustomerserver.dao.Model.ModEntity;
import com.faq.javacustomerserver.dao.Model.UserEntity;
import com.faq.javacustomerserver.utils.Code;
import com.faq.javacustomerserver.utils.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.Collection;
import java.util.Collections;
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
        UserEntity userEntity = userMapper.findByUuid(uuid);
        ModEntity modEntity = new ModEntity();
        for (ModEntity entity : userEntity.getModEntities()) {
            if (entity.getModName().equals(modName)) {
                throw new KeyAlreadyExistsException("该模板已存在");
            }
        }
        modEntity.setModName(modName);
        modEntity.setUserEntity(userEntity);
        modMapper.save(modEntity);
        return new ResponseResult<>(Code.SUCCESS, modEntity);
    }

    @ApiOperation(value = "通过模板id获取模板", notes = "需要传入模板的id(modId)")
    @GetMapping("/getMod/{modId}")
    public ResponseResult<ModEntity> getMod(@PathVariable Integer modId) {
        return new ResponseResult<>(Code.SUCCESS, modMapper.getById(modId));
    }

    @ApiOperation(value = "获取全部模板")
    @GetMapping("/getAllModByUser/{userId}")
    public ResponseResult<Collection<ModEntity>> getAllUser(@PathVariable Integer userId) {
        Collection<ModEntity> modEntities= userMapper.findById(userId).get().getModEntities();
        return new ResponseResult<>(Code.SUCCESS, modEntities);
    }

    @ApiOperation(value = "通过模板id删除模板", notes = "需要传入模板的id(id)")
    @DeleteMapping("/deleteMod/{id}")
    @Transactional
    public ResponseResult<String> deleteMod(@PathVariable Integer id) {
        modMapper.deleteModQa(id);
        modMapper.deleteModKey(id);
        modMapper.deleteMod(id);
        return new ResponseResult<>(Code.SUCCESS, "删除成功！");
    }

    @ApiOperation(value = "通过模板id修改模板", notes = "需要传入模板的id(id)和模板的名称(modName)")
    @PostMapping("/updateMod/{id}/{modName}")
    public ResponseResult<String> updateMod(@PathVariable Integer id,
                                            @PathVariable String modName) {
        ModEntity modEntity = modMapper.getById(id);
        modEntity.setModName(modName);
        modMapper.save(modEntity);
        return new ResponseResult<>(Code.SUCCESS, "更新成功！");
    }
}
