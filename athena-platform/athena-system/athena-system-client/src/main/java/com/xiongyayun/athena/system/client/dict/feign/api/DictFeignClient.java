package com.xiongyayun.athena.system.client.dict.feign.api;

import com.xiongyayun.athena.system.client.dict.dto.DictDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 系统用户接口
 * @author Yayun.Xiong
 * @date 2021-09-11 16:29
 */
@FeignClient("athena/system")
public interface DictFeignClient {
	/**
	 * 新增数据字典
	 * @param dictDTO
	 * @return
	 */
	@PostMapping("/sys/dict/create")
	DictDTO create(@RequestBody DictDTO dictDTO);

	/**
	 * 查询数据字典列表
	 * @param dictDTO
	 * @return
	 */
	@PostMapping("/sys/dict/list")
	List<DictDTO> list(@RequestBody DictDTO dictDTO);

	/**
	 * 分页查询数据字典列表
	 * @param dictDTO
	 * @return
	 */
	@PostMapping("/sys/dict/page")
	List<DictDTO> page(@RequestBody DictDTO dictDTO);
}
