package com.yx.service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RedisService {
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	@Autowired
	private RedissonDistributedLocker redissonDistributedLocker;

	public String subtraction() {
		String uuid = UUID.randomUUID().toString();
		String key = "lockKey";
		String total = "totalKey";
		try {
			Boolean flag = stringRedisTemplate.opsForValue().setIfAbsent(key, uuid, 10, TimeUnit.SECONDS);
			if(!flag) {
				log.info("获取锁失败");
				return "获取锁失败";
			}
			String totals = stringRedisTemplate.opsForValue().get(total);
			Integer ss = Integer.valueOf(totals)-1;
			log.info("库存剩余:"+ss);
			stringRedisTemplate.opsForValue().set(total, ss.toString());
			return "扣款成功";
		} catch (Exception e) {
			log.info("扣款失败");
			return "扣款失败";
		}finally {
			if(uuid.equals(stringRedisTemplate.opsForValue().get(key))) {
				stringRedisTemplate.delete(key);
			}
		}
	}
	
	public void testLock() {
		String lockKey = "lockKey";
		try {
			redissonDistributedLocker.lock(lockKey);
		} catch (Exception e) {
			log.info("扣款失败");
		}finally {
			redissonDistributedLocker.unlock(lockKey);
		}
	}
}
