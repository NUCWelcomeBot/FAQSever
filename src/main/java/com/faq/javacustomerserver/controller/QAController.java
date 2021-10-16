/*
 *   #-*- coding = utf-8 -*-
 *   #@Time: 2021/9/12 16:09
 *   #@Author：dodo
 *   #@Software：IntelliJ IDEA
 */

package com.faq.javacustomerserver.controller;

import GsonModel.DemoOkhttp;
import GsonModel.ExtractKeyword;
import com.alibaba.fastjson.JSONObject;
import com.faq.javacustomerserver.annotation.BeforeQACutPoint;
import com.faq.javacustomerserver.dao.Mapper.KeyMapper;
import com.faq.javacustomerserver.dao.Mapper.ModMapper;
import com.faq.javacustomerserver.dao.Mapper.QAMapper;
import com.faq.javacustomerserver.dao.Model.KeyEntity;
import com.faq.javacustomerserver.dao.Model.ModEntity;
import com.faq.javacustomerserver.dao.Model.QAEntity;
import com.faq.javacustomerserver.utils.Code;
import com.faq.javacustomerserver.utils.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.*;
import java.util.stream.Collector;

@Api(tags = "问题管理")
@RestController
@RequestMapping("/qa")
public class QAController {
    @Autowired
    private QAMapper qaMapper;
    @Autowired
    private ModMapper modMapper;
    @Autowired
    KeyMapper keyMapper;
    @Value("${keywords.url}")
    String extractKeywordUrl;

    @ApiOperation(value = "保存问题", notes = "需要传入问题(question)答案(answer),模板id(modId)和关键词Id(keyId)")
    @PostMapping("/saveQA")
    public ResponseResult<QAEntity> saveUser(@RequestBody JSONObject jsonObject) {
        String question = jsonObject.getString("question");
        String answer = jsonObject.getString("answer");
        Integer modId = jsonObject.getInteger("modId");
        ModEntity modEntity = modMapper.getById(modId);
        QAEntity qaEntity = new QAEntity();
        if (modEntity.getQaEntities()
                .stream()
                .parallel()
                .anyMatch(tqa->tqa.getQuestion().equals(question))){
            throw new KeyAlreadyExistsException("该问题已存在");
        }
        qaEntity.setQuestion(question);
        qaEntity.setAnswer(answer);
        qaEntity.setModEntity(modEntity);
        HashMap headers = new HashMap();
        headers.put("sentence",question);
        ExtractKeyword responseDataClass = DemoOkhttp.Companion.post(extractKeywordUrl,headers);
        var keys  = modEntity.getKeyEntities();
        for(String key : Objects.requireNonNull(responseDataClass.component3())){
            var tKey = keys.stream().parallel().filter(tkey -> tkey.getKeyword().equals(key)).findFirst();
            KeyEntity keyEntity = tKey.orElse(null);
            saveKeyAndQA(keyEntity,qaEntity,modEntity,key);
        }
        return new ResponseResult<>(Code.SUCCESS, qaEntity);
    }
    private void saveKeyAndQA(KeyEntity keyEntity,QAEntity qaEntity,ModEntity modEntity,String key){
        if (keyEntity == null){
            keyEntity = new KeyEntity();
            keyEntity.setKeyword(key);
            keyEntity.setModEntity(modEntity);
        }
        keyEntity.addQaEntities(qaEntity);
        qaEntity.addKeyEntities(keyEntity);
        qaMapper.save(qaEntity);
    }
    // @KeyWordLog // 之后采用该注解直接实现数据统计
    @BeforeQACutPoint
    @ApiOperation(value = "通过问题id获取问题实体(答案)", notes = "需要传入问题id(id)")
    @GetMapping("/getQA/{QAId}")
    public ResponseResult<QAEntity> getQA(@PathVariable Integer QAId) {
        return new ResponseResult<>(Code.SUCCESS, qaMapper.getById(QAId));
    }

    @ApiOperation(value = "通过问题id获取Mod QA List", notes = "需要传入问题id(id)")
    @GetMapping("/getModAllQA/{modId}")
    public ResponseResult< Collection<QAEntity>> getModAllQA(@PathVariable Integer modId) {
        return new ResponseResult<>(Code.SUCCESS, modMapper.getById(modId).getQaEntities());
    }
    @ApiOperation(value = "获取当前页面(num)问题", notes = "需要传入页数(num)[从1开始]")
    @GetMapping("/getQAByPage/{modelId}/{num}")
    public ResponseResult<List<QAEntity>> getQAByPage(@PathVariable Integer modelId,@PathVariable Integer num) {
        PageRequest pageable = PageRequest.of(num - 1, 10);
        return getListResponseResult(pageable);
    }

    @ApiOperation(value = "获取常用的问题(默认五个)", notes = "传入你需要的点击数前多少名的数量(num)大小")
    @GetMapping("/getFreQuentQA/{modelId}/{num}")
    public ResponseResult<List<QAEntity>> getFrequentQA(@PathVariable Integer modelId,@PathVariable(required = false) Integer num) {
        int numTemp = 5;
        if (num != null) {
            numTemp = num;
        }
        PageRequest pageable = PageRequest.of(0, numTemp, Sort.Direction.DESC, "count");
        return getListResponseResult(pageable);
    }

    @NotNull
    private ResponseResult<List<QAEntity>> getListResponseResult(PageRequest pageable) {
        Page<QAEntity> qaEntities = qaMapper.findAll(pageable);
        List<QAEntity> qaEntityList = new ArrayList<>();
        for (QAEntity qaEntity : qaEntities) {
            qaEntityList.add(qaEntity);
        }
        return new ResponseResult<>(Code.SUCCESS, qaEntityList);
    }

    @ApiOperation(value = "通过问题id删除问题实体", notes = "需要传入问题(id)")
    @DeleteMapping("/deleteQA/{id}")
    public ResponseResult<String> deleteQA(@PathVariable Integer id) {
        qaMapper.deleteQA(id);
        return new ResponseResult<>(Code.SUCCESS, "删除成功");
    }

    @ApiOperation(value = "通过问题id修改问题实体答案", notes = "需要传入问题(id)和问题(answer)")
    @PutMapping("/updateQA/{id}")
    public ResponseResult<QAEntity> updateQA(@PathVariable Integer id,
                                             @RequestParam("answer") String answer) {
        QAEntity qaEntity = qaMapper.getById(id);
        qaEntity.setAnswer(answer);
        qaMapper.save(qaEntity);
        return new ResponseResult<>(Code.SUCCESS, qaEntity);
    }
}
