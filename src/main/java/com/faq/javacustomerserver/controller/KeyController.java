/*
 *   #-*- coding = utf-8 -*-
 *   #@Time: 2021/9/12 16:09
 *   #@Author：dodo
 *   #@Software：IntelliJ IDEA
 */

package com.faq.javacustomerserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.faq.javacustomerserver.annotation.BeforeKeyCutPoint;
import com.faq.javacustomerserver.dao.Mapper.KeyMapper;
import com.faq.javacustomerserver.dao.Mapper.ModMapper;
import com.faq.javacustomerserver.dao.Model.KeyEntity;
import com.faq.javacustomerserver.dao.Model.ModEntity;
import com.faq.javacustomerserver.dao.Model.QAEntity;
import com.faq.javacustomerserver.utils.Code;
import com.faq.javacustomerserver.utils.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Api(tags = "关键词管理")
@RestController
@RequestMapping("/key")
public class KeyController {

    @Autowired
    KeyMapper keyMapper;
    @Autowired
    ModMapper modMapper;

    @ApiOperation(value = "保存关键词", notes = "需要传入uuid模板名(modId)和关键词(keyword)")
    @PostMapping("/key")
    public ResponseResult<KeyEntity> saveKey(@RequestBody JSONObject jsonObject) {
        Integer modId = jsonObject.getInteger("modId");
        String keyword = jsonObject.getString("keyword");
        ModEntity modEntity = modMapper.getById(modId);
        for (KeyEntity key : modEntity.getKeyEntities()) {
            if (key.getKeyword().equals(keyword)) {
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
    @BeforeKeyCutPoint
    @ApiOperation(value = "通过关键词获取关键词及其所属问题", notes = "需要传入关键词id")
    @GetMapping("/key/{id}")
    public ResponseResult<KeyEntity> getQAByAnswer(@PathVariable Integer id) {
        return new ResponseResult<>(Code.SUCCESS, keyMapper.getById(id));
    }
    @ApiOperation(value = "通过问题id获取Mod QA List", notes = "需要传入问题id(id)")
    @GetMapping("/modAllKey/{modId}")
    public ResponseResult<Collection<KeyEntity>> getModAllQA(@PathVariable Integer modId) {
        return new ResponseResult<>(Code.SUCCESS, modMapper.getById(modId).getKeyEntities());
    }
    @ApiOperation(value = "获取常用的关键词(默认五个)", notes = "传入你需要的点击数前多少名的数量(num)大小")
    @GetMapping("/freQuentKey/{num}")
    public ResponseResult<List<KeyEntity>> getFrequentQA(@PathVariable Integer num) {
        int numTemp = 5;
        if (num != null) {
            numTemp = num;
        }
        PageRequest pageable = PageRequest.of(0, numTemp, Sort.Direction.DESC, "count");
        return getListResponseResult(pageable);
    }

    @NotNull
    private ResponseResult<List<KeyEntity>> getListResponseResult(PageRequest pageable) {
        Page<KeyEntity> keyEntities = keyMapper.findAll(pageable);
        List<KeyEntity> keyEntityList = new ArrayList<>();
        for (KeyEntity keyEntity : keyEntities) {
            keyEntityList.add(keyEntity);
        }
        return new ResponseResult<>(Code.SUCCESS, keyEntityList);
    }

    @ApiOperation(value = "通过关键词id删除关键词", notes = "需要传入关键词id(id)")
    @DeleteMapping("/key/{id}")
    public ResponseResult<String> deleteQA(@PathVariable Integer id) {
        keyMapper.deleteKey(id);
        return new ResponseResult<>(Code.SUCCESS, "删除成功");
    }

    @ApiOperation(value = "通过关键词id修改关键词", notes = "需要传入关键词id(id)和关键词(keyword)")
    @PutMapping("/key/{id}/{keyword}")
    public ResponseResult<String> updateQA(@PathVariable Integer id,
                                           @PathVariable String keyword) {
        KeyEntity keyEntity = keyMapper.getById(id);
        keyEntity.setKeyword(keyword);
        keyMapper.save(keyEntity);
        return new ResponseResult<>(Code.SUCCESS, "更新成功");
    }

}
