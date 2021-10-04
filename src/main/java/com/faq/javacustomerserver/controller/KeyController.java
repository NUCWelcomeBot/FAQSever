/*
 *   #-*- coding = utf-8 -*-
 *   #@Time: 2021/9/12 16:09
 *   #@Author：dodo
 *   #@Software：IntelliJ IDEA
 */

package com.faq.javacustomerserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.faq.javacustomerserver.dao.Mapper.KeyMapper;
import com.faq.javacustomerserver.dao.Mapper.ModMapper;
import com.faq.javacustomerserver.dao.Mapper.QAMapper;
import com.faq.javacustomerserver.dao.Model.KeyEntity;
import com.faq.javacustomerserver.dao.Model.ModEntity;
import com.faq.javacustomerserver.utils.Code;
import com.faq.javacustomerserver.utils.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.Collection;

@Api(tags = "关键词管理")
@RestController
@RequestMapping("/key")
public class KeyController {

    @Autowired
    private QAMapper qaMapper;

    @Autowired
    KeyMapper keyMapper;
    @Autowired
    ModMapper modMapper;

    @ApiOperation(value = "保存关键词", notes = "需要传入关键词(keyword)")
    @PostMapping("/saveKey")
    public ResponseResult<KeyEntity> saveKey(@RequestBody JSONObject jsonObject) {
        String mod = jsonObject.getString("mod");
        String keyword = jsonObject.getString("keyword");
        ModEntity modEntity = modMapper.findByModName(mod);
        for(KeyEntity key : modEntity.getKeyEntities()){
            if(key.getKeyword().equals(keyword)){
                throw new KeyAlreadyExistsException("key已存在或该应用不存在");
            }
        }
        KeyEntity keyEntity = new KeyEntity();
        keyEntity.setKeyword(keyword);
        keyEntity.setModEntity(modEntity);
        modEntity.addKeyword(keyEntity);
        keyMapper.save(keyEntity);
        modMapper.save(modEntity);
        return new ResponseResult<>(Code.SUCCESS, keyEntity);
    }
//    @KeyWordLog
    @ApiOperation(value = "通过关键词获取关键词及其所属问题", notes = "需要传入关键词(keyword)")
    @GetMapping("/getKey/{keyword}")
    public ResponseResult<KeyEntity> getQAByAnswer(@PathVariable String keyword) {
        KeyEntity keyEntity = keyMapper.findByKeyword(keyword);
        return new ResponseResult<>(Code.SUCCESS, keyEntity);
    }

    @ApiOperation(value = "获取应用全部关键词")
    @GetMapping("/{applyId}/getAllKey")
    public ResponseResult<Collection<KeyEntity>> getFrequentQA(@PathVariable Integer applyId) {
        ModEntity modEntity = modMapper.findById(applyId).get();
        Collection<KeyEntity> keyEntities = modEntity.getKeyEntities();
        return new ResponseResult<>(Code.SUCCESS, keyEntities);
    }

    @ApiOperation(value = "通过关键词id删除关键词", notes = "需要传入关键词id(id)")
    @DeleteMapping("/deleteKey")
    public ResponseResult<String> deleteQA(@RequestBody JSONObject jsonObject) {
        Integer id = jsonObject.getInteger("id");
        keyMapper.deleteById(id);
        return new ResponseResult<>(Code.SUCCESS, "删除成功");
    }

    @ApiOperation(value = "通过关键词id修改关键词", notes = "需要传入关键词id(id)和关键词(keyword)")
    @PutMapping("/updateKey")
    public ResponseResult<KeyEntity> updateQA(@RequestBody JSONObject jsonObject) {
        Integer id = jsonObject.getInteger("id");
        String keyword = jsonObject.getString("keyword");
        KeyEntity keyEntity = keyMapper.getById(id);
        keyEntity.setKeyword(keyword);
        keyMapper.save(keyEntity);
        return new ResponseResult<>(Code.SUCCESS, keyEntity);
    }

}
