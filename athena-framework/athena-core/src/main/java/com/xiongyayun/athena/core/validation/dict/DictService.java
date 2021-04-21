package com.xiongyayun.athena.core.validation.dict;

import java.util.List;

/**
 * DictService
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2021/4/21
 */
public interface DictService {
	/**
	 * 根据代码查询字典项数据
	 * @param code
	 * @return
	 */
	List<String> queryDictItems(String... code);
}
