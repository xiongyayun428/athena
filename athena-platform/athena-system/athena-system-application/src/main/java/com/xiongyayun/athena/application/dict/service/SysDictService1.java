//package com.xiongyayun.athena.application.dict.service;
//
//import com.baomidou.mybatisplus.extension.service.IService;
//import com.xiongyayun.athena.core.validation.ValidationGroup;
//import com.xiongyayun.athena.core.validation.dict.DictService;
//import com.xiongyayun.athena.system.modules.dict.dto.SysDictDTO;
//import com.xiongyayun.athena.system.modules.dict.dto.SysDictItemDTO;
//import com.xiongyayun.athena.system.modules.dict.entity.SysDict;
//import com.xiongyayun.athena.system.modules.dict.entity.SysDictItem;
//import com.xiongyayun.athena.system.modules.dict.vo.SysDictItemVO;
//import org.springframework.validation.annotation.Validated;
//
//import javax.validation.Valid;
//import javax.validation.constraints.NotBlank;
//import java.util.List;
//import java.util.Map;
//
///**
// * 字典服务
// *
// * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
// * @date 2021/3/29
// */
//@Validated
//public interface SysDictService extends IService<SysDict>, DictService {
//
//	/**
//	 * 根据代码查询字典数据
//	 * @param code
//	 * @return
//	 */
//	SysDict queryDictByCode(String code);
//
//	/**
//	 * 根据代码查询字典项数据
//	 * @param code
//	 * @return
//	 */
//	List<SysDictItem> queryDictItemsByCode(String code);
//
//	/**
//	 * 查询所有有效数据字典
//	 * @return
//	 */
//	Map<String, List<SysDictItemVO>> queryAllDictItems();
//
//	/**
//	 * 保存数据字典和字典项
//	 * @param sysDict
//	 * @param sysDictItems
//	 * @return
//	 */
//	boolean save(SysDict sysDict, List<SysDictItem> sysDictItems);
//
//	/**
//	 * 创建数据字典
//	 * @param dto
//	 * @return
//	 */
//	@Validated({ValidationGroup.Create.class})
//	boolean create(@Valid SysDictDTO dto);
//
//	/**
//	 * 创建数据字典项
//	 * @param dto
//	 * @return
//	 */
//	@Validated({ValidationGroup.Create.class})
//	boolean create(@Valid SysDictItemDTO dto);
//
//	/**
//	 * 更新数据字典
//	 * @param dto
//	 * @return
//	 */
//	@Validated({ValidationGroup.Update.class})
//	boolean update(@Valid SysDictDTO dto);
//	/**
//	 * 更新数据字典项
//	 * @param dto
//	 * @return
//	 */
//	@Validated({ValidationGroup.Update.class})
//	boolean update(@Valid SysDictItemDTO dto);
//
//	/**
//	 * 删除数据字典项
//	 * @param id
//	 * @return
//	 */
//	@Validated
//	boolean delete(@NotBlank(message = "字典ID不能为空") String id);
//}
