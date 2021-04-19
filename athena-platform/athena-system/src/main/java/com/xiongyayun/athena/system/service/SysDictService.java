package com.xiongyayun.athena.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiongyayun.athena.core.ValidationGroup;
import com.xiongyayun.athena.system.dto.SysDictDTO;
import com.xiongyayun.athena.system.dto.SysUserDTO;
import com.xiongyayun.athena.system.model.SysDict;
import com.xiongyayun.athena.system.model.SysDictItem;
import com.xiongyayun.athena.system.vo.SysDictItemVO;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 字典服务
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2021/3/29
 */
@Validated
public interface SysDictService extends IService<SysDict> {

	/**
	 * 根据代码查询字典数据
	 * @param code
	 * @return
	 */
	SysDict queryDictByCode(String code);

	/**
	 * 根据代码查询字典项数据
	 * @param code
	 * @return
	 */
	List<SysDictItem> queryDictItemsByCode(String code);

	/**
	 * 查询所有有效数据字典
	 * @return
	 */
	Map<String, List<SysDictItemVO>> queryAllDictItems();

	/**
	 * 保存数据字典和字典项
	 * @param sysDict
	 * @param sysDictItems
	 * @return
	 */
	boolean save(SysDict sysDict, List<SysDictItem> sysDictItems);

	/**
	 * 创建数据字典
	 * @param dto
	 * @return
	 */
	@Validated({ValidationGroup.Create.class})
	boolean create(@Valid SysDictDTO dto);

	/**
	 * 更新数据字典
	 * @param dto
	 * @return
	 */
	@Validated({ValidationGroup.Update.class})
	boolean update(@Valid SysDictDTO dto);
}
