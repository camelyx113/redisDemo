package com.yx.scheduled;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.yx.service.RedissonDistributedLocker;

@Component
public class ScheduledTask {
	@Value("${systemFlag}")
	private String flag;

	@Autowired
	private RedissonDistributedLocker redissonDistributedLocker;
	
	 @Scheduled(cron="*/10 * * * * ?")
	    public void clearFlashSaleTimeOut() {
		 	String scheduledKey = "scheduledKey";
	    	try {
	    		redissonDistributedLocker.lock(scheduledKey);
	    		System.out.println("当前时间:"+LocalDateTime.now()+",当前系统:"+flag);
	    		Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				// TODO: handle finally clause
				redissonDistributedLocker.unlock(scheduledKey);
			}
	    	
	    }
}
