package com.xiongyayun.athena.application.dict.service;

import org.springframework.stereotype.Service;

/**
 * 字典服务
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2021/3/29
 */
@Service
public class SysDictService {

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

//	@Resource
//	private SysDictItemMapper sysDictItemMapper;
//
//	@Override
//	public SysDict queryDictByCode(String dictCode) {
//		Assert.notNull(dictCode, "数据字典编码不能为空!");
//		return getOne(Wrappers.<SysDict>lambdaQuery().eq(SysDict::getDictCode, dictCode));
//	}
//
//	@Override
//	@Cacheable(value = CacheConstant.SYS_DICT_CACHE, key = "#code")
//	public List<SysDictItem> queryDictItemsByCode(String code) {
//		SysDict sysDict = this.queryDictByCode(code);
//		if (ObjectUtils.isEmpty(sysDict)) {
////			throw new AthenaRuntimeException("数据字典编码不存在!");
//			return null;
//		}
//		return sysDictItemMapper.selectList(Wrappers.<SysDictItem>lambdaQuery().eq(SysDictItem::getDictId, sysDict.getId()));
//	}
//
//	@Override
//	public Map<String, List<SysDictItemVO>> queryAllDictItems() {
//		SysDict sysDict = new SysDict();
//		sysDict.setEnabled(true);
//		LambdaQueryWrapper<SysDict> lqw = new LambdaQueryWrapper(sysDict);
//		List<SysDict> listOfSysDict = this.list(lqw);
//
//		Map<String, List<SysDictItemVO>> res = new HashMap<>(listOfSysDict.size());
//
//		LambdaQueryWrapper<SysDictItem> queryWrapper = new LambdaQueryWrapper<>();
//		queryWrapper.eq(SysDictItem::getEnabled, true);
//		queryWrapper.orderByAsc(SysDictItem::getSortOrder);
//		List<SysDictItem> sysDictItemList = sysDictItemMapper.selectList(queryWrapper);
//
//		for (SysDict dict : listOfSysDict) {
//			List<SysDictItemVO> dictModelList = sysDictItemList.stream().filter(s -> dict.getId().equals(s.getDictId())).map(item -> {
//				SysDictItemVO vo = new SysDictItemVO();
//				vo.setLabel(item.getLabel());
//				vo.setValue(item.getValue());
//				return vo;
//			}).collect(Collectors.toList());
//			res.put(dict.getDictCode(), dictModelList);
//		}
//		log.debug("-------加载所有系统字典-----" + res.toString());
//		return res;
//	}
//
//	@Override
//	@Transactional(rollbackFor = Exception.class)
//	public boolean save(SysDict sysDict, List<SysDictItem> sysDictItems) {
//		boolean flag;
//		try {
//			flag = this.save(sysDict);
//			if (sysDictItems != null) {
//				int insert;
//				for (SysDictItem entity : sysDictItems) {
//					entity.setDictId(sysDict.getId());
//					insert = sysDictItemMapper.insert(entity);
//					if (insert <= 0) {
//						throw new AthenaRuntimeException("数据字典项保存失败");
//					}
//				}
//			}
//		} catch (Exception e) {
//			return false;
//		}
//		return flag;
//	}
}
