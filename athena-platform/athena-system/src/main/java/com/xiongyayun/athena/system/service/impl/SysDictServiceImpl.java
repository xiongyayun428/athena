package com.xiongyayun.athena.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiongyayun.athena.core.exception.AthenaRuntimeException;
import com.xiongyayun.athena.system.constant.CacheConstant;
import com.xiongyayun.athena.system.mapper.SysDictItemMapper;
import com.xiongyayun.athena.system.mapper.SysDictMapper;
import com.xiongyayun.athena.system.model.SysDict;
import com.xiongyayun.athena.system.model.SysDictItem;
import com.xiongyayun.athena.system.service.SysDictService;
import com.xiongyayun.athena.system.vo.dict.SysDictItemVO;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * SysDictServiceImpl
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2021/3/29
 */
@Service
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements SysDictService {
	@Resource
	private SysDictItemMapper sysDictItemMapper;

	@Override
	public SysDict queryDictByCode(String dictCode) {
		if (!StringUtils.hasLength(dictCode)) {
			throw new AthenaRuntimeException("数据字典编码不能为空!");
		}
		return getOne(Wrappers.<SysDict>lambdaQuery().eq(SysDict::getDictCode, dictCode));
	}

	@Override
	@Cacheable(value = CacheConstant.SYS_DICT_CACHE, key = "#code")
	public List<SysDictItem> queryDictItemsByCode(String code) {
		SysDict sysDict = this.queryDictByCode(code);
		if (ObjectUtils.isEmpty(sysDict)) {
//			throw new AthenaRuntimeException("数据字典编码不存在!");
			return null;
		}
		return sysDictItemMapper.selectList(Wrappers.<SysDictItem>lambdaQuery().eq(SysDictItem::getDictId, sysDict.getId()));
	}

	@Override
	public Map<String, List<SysDictItemVO>> queryAllDictItems() {
		SysDict sysDict = new SysDict();
		sysDict.setEnabled(true);
		LambdaQueryWrapper<SysDict> lqw = new LambdaQueryWrapper(sysDict);
		List<SysDict> listOfSysDict = this.list(lqw);

		Map<String, List<SysDictItemVO>> res = new HashMap<>(listOfSysDict.size());

		LambdaQueryWrapper<SysDictItem> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(SysDictItem::getEnabled, true);
		queryWrapper.orderByAsc(SysDictItem::getSortOrder);
		List<SysDictItem> sysDictItemList = sysDictItemMapper.selectList(queryWrapper);

		for (SysDict dict : listOfSysDict) {
			List<SysDictItemVO> dictModelList = sysDictItemList.stream().filter(s -> dict.getId().equals(s.getDictId())).map(item -> {
				SysDictItemVO vo = new SysDictItemVO();
				vo.setLabel(item.getLabel());
				vo.setValue(item.getValue());
				return vo;
			}).collect(Collectors.toList());
			res.put(dict.getDictCode(), dictModelList);
		}
		log.debug("-------加载所有系统字典-----" + res.toString());
		return res;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean save(SysDict sysDict, List<SysDictItem> sysDictItems) {
		boolean flag;
		try {
			flag = this.save(sysDict);
			if (sysDictItems != null) {
				int insert;
				for (SysDictItem entity : sysDictItems) {
					entity.setDictId(sysDict.getId());
					insert = sysDictItemMapper.insert(entity);
					if (insert <= 0) {
						throw new AthenaRuntimeException("数据字典项保存失败");
					}
				}
			}
		} catch (Exception e) {
			return false;
		}
		return flag;
	}
}
