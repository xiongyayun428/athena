package com.xiongyayun.athena.role.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * fallback是当程序错误的时候来回调的方法
 * @author yayun.xiong
 *
 */
@FeignClient(value = "athena-role", fallback=RoleApi.HystrixRoleApi.class)
public interface RoleApi {
	
	@RequestMapping("/role/roles")
	String roles(@RequestParam(value = "userId") String userId);
	
	@RequestMapping(value = "/role/get", method = RequestMethod.GET)
	String get(@RequestParam(value = "userId") String userId);
	
    @Component
    class HystrixRoleApi implements RoleApi {
    	@Override
    	public String roles(String userId) {
    		return "------error------";
    	}

    	@Override
    	public String get(String userId) {
    		return "role get error";
    	}
    }
}
