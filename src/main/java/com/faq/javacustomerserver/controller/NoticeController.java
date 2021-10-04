package com.faq.javacustomerserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.faq.javacustomerserver.dao.Mapper.NoticeMapper;
import com.faq.javacustomerserver.dao.Model.NoticeEntity;
import com.faq.javacustomerserver.utils.Code;
import com.faq.javacustomerserver.utils.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Api(tags = "通知管理")
@RestController
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    private NoticeMapper noticeMapper;

    @ApiOperation(value = "保存通知", notes = "需要传入通知内容(content)")
    @PostMapping("/saveNotice")
    public ResponseResult<NoticeEntity> saveNotice(@RequestBody JSONObject jsonObject) {
        String content = jsonObject.getString("content");
        NoticeEntity noticeEntity = new NoticeEntity();
        noticeEntity.setContent(content);
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s = simpleDateFormat.format(date);
        noticeEntity.setTime(s);
        noticeMapper.save(noticeEntity);
        return new ResponseResult<>(Code.SUCCESS, noticeEntity);
    }

    @ApiOperation(value = "通过id获取通知所有信息", notes = "需要传入(id)")
    @GetMapping("/getNotice")
    public ResponseResult<NoticeEntity> getNotice(@RequestBody JSONObject jsonObject) {
        Integer id = jsonObject.getInteger("id");
        NoticeEntity noticeEntity = noticeMapper.getById(id);
        return new ResponseResult<>(Code.SUCCESS, noticeEntity);
    }

    @ApiOperation(value = "获取全部通知", notes = "不需要参数")
    @GetMapping("/getAllNotice")
    public ResponseResult<List<NoticeEntity>> getAllNotice() {
        List<NoticeEntity> all = noticeMapper.findAll();
        return new ResponseResult<>(Code.SUCCESS, all);
    }

    @ApiOperation(value = "通过id删除通知", notes = "需要传入(id)")
    @DeleteMapping("/deleteNotice")
    public ResponseResult<String> deleteNotice(@RequestBody JSONObject jsonObject) {
        Integer id = jsonObject.getInteger("id");
        noticeMapper.deleteById(id);
        return new ResponseResult<>(Code.SUCCESS, "删除成功！");
    }
}

