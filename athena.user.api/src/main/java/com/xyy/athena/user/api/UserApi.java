package com.xyy.athena.user.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "athena-user", fallback=UserApi.HystrixUserApi.class)
public interface UserApi {
	@RequestMapping("/user/users")
	String users(@RequestParam(value = "userId") String userId);
	
	@RequestMapping("/user/get")
	String get(@RequestParam(value = "userId") String userId);
	
    @Component
    class HystrixUserApi implements UserApi {
    	@Override
    	public String users(String userId) {
    		return "------error------";
    	}

    	@Override
    	public String get(String userId) {
    		return "role get error";
    	}
    }

}
