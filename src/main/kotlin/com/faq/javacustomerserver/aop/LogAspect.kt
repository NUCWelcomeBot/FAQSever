package com.faq.javacustomerserver.aop

//@Aspect
//@Component
//@Slf4j
//class LogAspect {
//    /**
//     * Controller的切入点
//     */
//    @Autowired
//    private lateinit var redisService: RedisService
//
//    @Pointcut("@annotation(com.faq.javacustomerserver.AOP.KeyWordLog)")
//    private fun keyWordAspect(){
//        print("?")
//    }
//
//    @After(value = "KeyWordAspect()") // 写了一半，没写完
//    private fun doBefore(joinPoint: JoinPoint){
//        val attributes:ServletRequestAttributes = RequestContextHolder.getRequestAttributes()
//                as ServletRequestAttributes
//        val request: HttpServletRequest = attributes.request
//        val args = joinPoint.args
//        val key  = ""
//        val value = ""
//        redisService.add(key, value)
//    }
//}