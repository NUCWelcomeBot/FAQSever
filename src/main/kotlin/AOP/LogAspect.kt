package AOP

import com.faq.javacustomerserver.Config.Redis.RedisService
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.After
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import javax.servlet.http.HttpServletRequest

@Aspect
@Component
class LogAspect {
    /**
     * Controller的切入点
     */
    @Autowired
    private lateinit var redisService: RedisService

    @Pointcut("@annotation(AOP.KeyWordLog)")
    private fun keyWordAspect(){

    }

    @After(value = "KeyWordAspect()") // 写了一半，没写完
    private fun doBefore(joinPoint: JoinPoint){
        val attributes:ServletRequestAttributes = RequestContextHolder.getRequestAttributes()
                as ServletRequestAttributes
        val request: HttpServletRequest = attributes.request
        val args = joinPoint.args
        val key  = ""
        val value = ""
        redisService.add(key, value)
    }
}