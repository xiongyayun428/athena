package com.xiongyayun.athena.system.modules.dict.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.xiongyayun.athena.core.exception.AthenaRuntimeException;
import com.xiongyayun.athena.core.utils.SpringContextUtil;
import com.xiongyayun.athena.system.core.constant.CacheConstant;
import com.xiongyayun.athena.system.modules.dict.dto.SysDictDTO;
import com.xiongyayun.athena.system.modules.dict.dto.SysDictItemDTO;
import com.xiongyayun.athena.system.modules.dict.mapper.SysDictItemMapper;
import com.xiongyayun.athena.system.modules.dict.mapper.SysDictMapper;
import com.xiongyayun.athena.system.modules.dict.model.SysDict;
import com.xiongyayun.athena.system.modules.dict.model.SysDictItem;
import com.xiongyayun.athena.system.modules.dict.service.SysDictService;
import com.xiongyayun.athena.system.modules.dict.vo.SysDictItemVO;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
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
	public List<String> queryDictItems(String... code) {
		SysDictServiceImpl sysDictService = SpringContextUtil.getBean(SysDictServiceImpl.class);
		return Arrays.stream(code)
				.filter(value -> StringUtils.hasLength(value))
				// 因为@Cacheable 是使用AOP 代理实现的，通过创建内部类来代理缓存方法，这样就会导致类内部的方法调用类内部的缓存方法不会走代理，不会走代理，就不能正常创建缓存，所以每次都需要去调用数据库。
				// @Cacheable 方法不能进行内部调用，否则缓存无法创建
//				.map(this::queryDictItemsByCode)
				.map(sysDictService::queryDictItemsByCode)
				.filter(value -> !ObjectUtils.isEmpty(value))
				.map(value -> value.stream().map(item -> item.getValue()).collect(Collectors.toList()))
				.flatMap(Collection::stream)
				.collect(Collectors.toList())
				;
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
		log.debug("-------加载所有系统字典-----");
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

	@Override
	public boolean updateById(SysDict entity) {
		SysDict existEntity = this.getOne(Wrappers.<SysDict>lambdaQuery().eq(SysDict::getId, entity.getId()));
		if (existEntity == null) {
			throw new AthenaRuntimeException("数据字典不存在");
		}
		return super.updateById(entity);
	}

	@Override
	public boolean create(SysDictDTO dto) {
		SysDict sysDict = new SysDict();
		SysDict exist = this.queryDictByCode(dto.getDictCode());
		if (exist != null) {
			throw new AthenaRuntimeException("数据字典已存在!");
		}
		BeanUtils.copyProperties(dto, sysDict);
		sysDict.setId(null);
		return this.save(sysDict);
	}

	@Override
	public boolean create(SysDictItemDTO dto) {
		SysDictItem exist = checkExist(dto);
		if (exist != null) {
			throw new AthenaRuntimeException("数据字典项已存在!");
		}
		SysDictItem sysDictItem = new SysDictItem();
		BeanUtils.copyProperties(dto, sysDictItem);
		sysDictItem.setId(null);
		return SqlHelper.retBool(this.sysDictItemMapper.insert(sysDictItem));
	}

	@Override
	public boolean update(SysDictDTO dto) {
		SysDict sysDict = new SysDict();
		BeanUtils.copyProperties(dto, sysDict);
		return this.updateById(sysDict);
	}

	@Override
	public boolean update(SysDictItemDTO dto) {
		dto.setId(null);
		SysDictItem exist = checkExist(dto);
		if (exist == null) {
			throw new AthenaRuntimeException("数据字典项不存在!");
		}
		BeanUtils.copyProperties(dto, exist);
		return SqlHelper.retBool(sysDictItemMapper.updateById(exist));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean delete(String id) {
		SysDict existEntity = this.getOne(Wrappers.<SysDict>lambdaQuery().eq(SysDict::getId, id));
		if (existEntity == null) {
			throw new AthenaRuntimeException("数据字典不存在");
		}
		if (!this.removeById(id)) {
			return false;
		}
		LambdaQueryWrapper<SysDictItem> queryWrapper = Wrappers.<SysDictItem>lambdaQuery().eq(SysDictItem::getDictId, id);
		int sysDictItemCount = sysDictItemMapper.selectCount(queryWrapper);
		if (sysDictItemCount <= 0) {
			return true;
		}
		if (sysDictItemMapper.delete(queryWrapper) <= 0) {
			throw new AthenaRuntimeException("数据字典项删除失败");
		}
		log.debug("删除数据字典[" + existEntity.getDictName() + ":" + existEntity.getDictCode() + "], 以及数据项：" + sysDictItemCount + "条");
		return true;

	}

	private SysDictItem checkExist(SysDictItemDTO dto) {
		if (!StringUtils.hasLength(dto.getDictId())) {
			throw new AthenaRuntimeException("数据字典ID不能为空!");
		}
		SysDict sysDict = this.getOne(Wrappers.<SysDict>lambdaQuery().eq(SysDict::getId, dto.getDictId()));
		if (sysDict == null) {
			throw new AthenaRuntimeException("数据字典不存在!");
		}
		return sysDictItemMapper.selectOne(Wrappers.<SysDictItem>lambdaQuery()
				.eq(SysDictItem::getDictId, dto.getDictId())
				.eq(SysDictItem::getValue, dto.getValue()));
	}
}
