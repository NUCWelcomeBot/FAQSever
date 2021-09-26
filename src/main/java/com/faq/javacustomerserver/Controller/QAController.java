/*
 *   #-*- coding = utf-8 -*-
 *   #@Time: 2021/9/12 16:09
 *   #@Author：dodo
 *   #@Software：IntelliJ IDEA
 */

package com.faq.javacustomerserver.Controller;

import com.faq.javacustomerserver.AOP.KeyWordLog;
import GsonModel.DemoOkhttp;
import GsonModel.ExtractKeyword;
import com.alibaba.fastjson.JSONObject;
import com.faq.javacustomerserver.DAO.Mapper.KeyMapper;
import com.faq.javacustomerserver.DAO.Mapper.ModMapper;
import com.faq.javacustomerserver.DAO.Mapper.QAMapper;
import com.faq.javacustomerserver.DAO.Model.KeyEntity;
import com.faq.javacustomerserver.DAO.Model.ModEntity;
import com.faq.javacustomerserver.DAO.Model.QAEntity;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

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
    @ApiOperation(value = "保存问题", notes = "需要传入问题(question)答案(answer),模板(modName)和关键词(keyword)")
    @PostMapping("/saveQA")
    public ResponseResult<QAEntity> saveUser(@RequestBody JSONObject jsonObject){
        String question = jsonObject.getString("question");
        String answer = jsonObject.getString("answer");
        String modName = jsonObject.getString("modName");
        String keyword = jsonObject.containsKey("keyword")? jsonObject.getString("keyword") :null;
        ModEntity modEntity = modMapper.findByModName(modName);
        QAEntity qaEntity = qaMapper.findByQuestion(question);
        if(Objects.requireNonNull(qaEntity).getModEntity().equals(modEntity)
                && qaMapper.findByQuestion(question) != null){
            throw new KeyAlreadyExistsException("该问题已存在");
        }else{
            qaEntity = new QAEntity();
            qaEntity.setQuestion(question);
            qaEntity.setAnswer(answer);
            qaEntity.setCount(0);
            qaEntity.setModEntity(modEntity);
        }
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("sentence",question);
        ExtractKeyword extractKeyword = DemoOkhttp.Companion.post(extractKeywordUrl,hashMap);
        if(keyword == null) {
            for (String key : Objects.requireNonNull(extractKeyword.component3())) {
                setQAKeyWord(key, qaEntity,modEntity);
            }
        }else{
            setQAKeyWord(keyword, qaEntity,modEntity);
        }
        qaMapper.save(qaEntity);
        return new ResponseResult<>(Code.SUCCESS, qaEntity);
    }

    private void setQAKeyWord(String keyword, QAEntity qaEntity,ModEntity modEntity) {
        KeyEntity keyEntity = keyMapper.findByKeyword(keyword);
        if (keyEntity == null) {
            keyEntity = new KeyEntity();
            keyEntity.setKeyword(keyword);
        }
        keyEntity.addQaEntities(qaEntity);
        keyEntity.setModEntity(modEntity);
        modEntity.addKeyword(keyEntity);
        qaEntity.addKeyEntities(keyEntity);
        keyMapper.save(keyEntity);
    }
    @KeyWordLog // 之后采用该注解直接实现数据统计
    @ApiOperation(value = "通过问题id获取问题实体(答案)", notes = "需要传入问题id(id)")
    @GetMapping("/getQA/{id}")
    public ResponseResult<QAEntity> getQA(@PathVariable Integer id) {
        QAEntity qaEntity = qaMapper.getById(id);
//        qaEntity.setCount(qaEntity.getCount() + 1);
//        qaMapper.save(qaEntity);
        return new ResponseResult<>(Code.SUCCESS, qaEntity);
    }

    @ApiOperation(value = "获取当前页面(num)问题", notes = "需要传入页数(num)[从1开始]")
    @GetMapping("/getQAByPage")
    public ResponseResult<List<QAEntity>> getQAByPage(@RequestBody JSONObject jsonObject) {
        int num = jsonObject.getInteger("num");
        PageRequest pageable = PageRequest.of(num - 1, 10);
        return getListResponseResult(pageable);
    }

    @ApiOperation(value = "获取常用的问题(默认五个)", notes = "传入你需要的点击数前多少名的数量(num)大小")
    @GetMapping("/getFreQuentQA")
    public ResponseResult<List<QAEntity>> getFrequentQA(@RequestBody(required = false) JSONObject jsonObject) {
        Integer num = 5;
        if (jsonObject.getInteger("num") != null) {
            num = jsonObject.getInteger("num");
        }
        PageRequest pageable = PageRequest.of(0, num, Sort.Direction.DESC, "count");
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
    @DeleteMapping("/deleteQA")
    public ResponseResult<String> deleteQA(@RequestBody JSONObject jsonObject) {
        Integer id = jsonObject.getInteger("id");
        qaMapper.deleteQA(id);
        return new ResponseResult<>(Code.SUCCESS, "删除成功");
    }

    @ApiOperation(value = "通过问题id修改问题实体答案", notes = "需要传入问题(id)和问题(answer)")
    @PutMapping("/updateQA")
    public ResponseResult<QAEntity> updateQA(@RequestBody JSONObject jsonObject) {
        Integer id = jsonObject.getInteger("id");
        String answer = jsonObject.getString("answer");
        QAEntity qaEntity = qaMapper.getById(id);
        qaEntity.setAnswer(answer);
        qaMapper.save(qaEntity);
        return new ResponseResult<>(Code.SUCCESS, qaEntity);
    }
}
