package com.faq.javacustomerserver.controller;
import com.faq.javacustomerserver.dao.Model.UserEntity;
import com.faq.javacustomerserver.dao.Mapper.UserMapper;
import com.faq.javacustomerserver.utils.Code;
import com.faq.javacustomerserver.utils.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "用户管理")
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserMapper userMapper;

    public UserController(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @ApiOperation(value = "保存用户", notes = "需要传入用户(uuid)")
    @PostMapping("/saveUser")
    public ResponseResult<UserEntity> saveUser(@RequestBody UserEntity userEntity) {
        userMapper.save(userEntity);
        return new ResponseResult<>(Code.SUCCESS, userEntity);
    }

    @ApiOperation(value = "通过uuid获取用户所有信息", notes = "需要传入(uuid)")
    @GetMapping("/getUser/{uuid}")
    public ResponseResult<UserEntity> getUser(@PathVariable Integer uuid){
        UserEntity userEntity = userMapper.findByUuid(uuid);
        return new ResponseResult<>(Code.SUCCESS, userEntity);
    }

    @ApiOperation(value = "获取全部用户", notes = "不需要参数")
    @GetMapping("/getAllUser")
    public ResponseResult<List<UserEntity>> getUser() {
        List<UserEntity> all = userMapper.findAll();
        return new ResponseResult<>(Code.SUCCESS, all);
    }

    @ApiOperation(value = "通过uuid删除用户", notes = "需要传入(uuid)")
    @DeleteMapping("/deleteUser/{uuid}")
    public ResponseResult<String> deleteUser(@PathVariable Integer uuid) {
        userMapper.removeByUuid(uuid);
        return new ResponseResult<>(Code.SUCCESS, "删除成功！");
    }
}

