package com.jiuzhang.seckill;

import com.jiuzhang.seckill.services.SeckillActivityService;
import com.jiuzhang.seckill.util.RedisService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.UUID;

@SpringBootTest
public class RedisDemoTest {
    @Resource
    private RedisService redisService;

    @Resource
    SeckillActivityService seckillActivityService;

    @Test
    public void stockTest(){
        redisService.setValue("stock:19",10L);
    }
    @Test
    public void getStockTest(){
        String stock = redisService.getValue("stock:19");
        System.out.println(stock);
    }

    @Test
    public void stockDeductValidatorTest(){
        boolean result = redisService.stockDeductValidator("stock:12");
        System.out.println("result:"+result);
        String stock = redisService.getValue("stock:12");
        System.out.println("stock:"+stock);
    }

    @Test
    public void pushSeckillInfoToRedisTest(){
        seckillActivityService.pushSeckillInfoToRedis(19);
    }

    /**
     * 测试高并发下获取锁的结果
     */
    @Test
    public void testConcurrentAddLock() {
        for (int i = 0; i < 10; i++) {
            String requestId = UUID.randomUUID().toString();
            // 打印结果 true false false false false false false false false false
            // 只有第一个能获得 锁
            System.out.println(redisService.tryGetDistributedLock("A",
                    requestId,1000));
        }
    }

    /**
     * 测试并发下获取锁然后立刻释放锁的结果
     */
    @Test
    public void testConcurrent() {
        for (int i = 0; i < 10; i++) {
            String requestId = UUID.randomUUID().toString();
            // 打印结果 true true true true true true true true true true
            System.out.println(redisService.tryGetDistributedLock("A",
                    requestId,1000));
            redisService.releaseDistributedLock("A", requestId);
        }
    }
}
