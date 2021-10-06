package com.faq.javacustomerserver.config.redis2mysql

import com.faq.javacustomerserver.config.redis.RedisService
import com.faq.javacustomerserver.dao.Mapper.KeyMapper
import com.faq.javacustomerserver.dao.Mapper.QAMapper
import com.faq.javacustomerserver.dao.Model.KeyEntity
import com.faq.javacustomerserver.dao.Model.QAEntity
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component


@Component
@Slf4j
class RedisToMysql {
    var prefix: String? = "QAAndKeyCount" // 前缀键值

    @Autowired
    private val redisService: RedisService? = null
    @Autowired
    private val keyMapper: KeyMapper? = null
    @Autowired
    private val QAMapper: QAMapper? = null
    @Scheduled(initialDelay = 5000,fixedDelay = 6 * 3600)
    private fun redisToMySQL(){
        val keys = redisService?.getAllKeysSet(prefix);
        if(!keys.isNullOrEmpty()){
            for(key in keys){
                val args = key.split(":")
                val id = args[2].toInt()
                val count = redisService?.get(key) as Int;
                when(args[1]){
//                    "Key"->{
//                        val keyObj: KeyEntity = keyMapper!!.findById(id).get()
//                        keyObj.count = count
//                        keyMapper.save(keyObj)
//                    }
//                    "Q"->{
//                        val QAObj: QAEntity = QAMapper!!.findById(id).get()
//                        QAObj.count = count
//                        QAMapper.save(QAObj)
//                    }
                }
                redisService.deleteKeys(key)
            }
        }
    }
}