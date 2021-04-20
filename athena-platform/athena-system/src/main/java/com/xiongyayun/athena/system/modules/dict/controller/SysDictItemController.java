package com.xiongyayun.athena.system.modules.dict.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.xiongyayun.athena.core.ValidationGroup;
import com.xiongyayun.athena.core.annotation.Log;
import com.xiongyayun.athena.core.exception.AthenaRuntimeException;
import com.xiongyayun.athena.core.pagination.IPage;
import com.xiongyayun.athena.core.pagination.mybatisplus.Page;
import com.xiongyayun.athena.core.utils.ClassUtil;
import com.xiongyayun.athena.system.core.constant.CacheConstant;
import com.xiongyayun.athena.system.modules.dict.dto.SysDictItemDTO;
import com.xiongyayun.athena.system.modules.dict.mapper.SysDictItemMapper;
import com.xiongyayun.athena.system.modules.dict.model.SysDictItem;
import com.xiongyayun.athena.system.modules.dict.service.SysDictService;
import com.xiongyayun.athena.system.modules.dict.vo.DictItemVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * 系统数据字典项
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2021/3/29
 */
@Validated
@RestController
@RequestMapping("/sys/dictItem")
@Api(tags = {"系统数据字典项服务"})
public class SysDictItemController {
	@Resource
	private SysDictItemMapper sysDictItemMapper;
	@Resource
	private SysDictService sysDictService;

	@Log("数据字典项分页查询")
	@ApiOperation("数据字典项分查询")
	@PostMapping("/page")
	public IPage<SysDictItem> page(@RequestBody(required = false) DictItemVO vo) {
		Page<SysDictItem> page = new Page<>(vo.getPageIndex(), vo.getPageSize());
		return sysDictItemMapper.selectPage(page, dictItemQueryWrapper(vo));
	}

	@Log("数据字典项查询")
	@Cacheable(cacheNames = "dictItemListCache", key = "#dictCode")
	@ApiOperation("数据字典项查询")
	@PostMapping("/list")
	public List<SysDictItem> list(@RequestBody(required = false) DictItemVO vo) {
		return sysDictItemMapper.selectList(dictItemQueryWrapper(vo));
	}

	private QueryWrapper<SysDictItem> dictItemQueryWrapper(DictItemVO vo) {
		if (!StringUtils.hasLength(vo.getDictId()) && StringUtils.hasLength(vo.getDictCode())) {
			vo.setDictId(this.sysDictService.queryDictByCode(vo.getDictCode()).getId());
		}
		SysDictItem sysDictItem = new SysDictItem();
		BeanUtils.copyProperties(vo, sysDictItem);

		if (!StringUtils.hasLength(sysDictItem.getDictId())) {
			throw new AthenaRuntimeException("字典ID不能为空!");
		}
		QueryWrapper<SysDictItem> wrapper = Wrappers.query(sysDictItem);
		if (vo.getSort() != null) {
			Arrays.stream(vo.getSort()).forEach(sort -> wrapper.orderBy(!ObjectUtils.isEmpty(sort), "ASC".equals(sort.getValue()), ClassUtil.getFieldName(SysDictItem.class, sort.getKey())));
		}
		return wrapper;
	}

	@Log("数据字典项新增")
	@ApiOperation("数据字典项新增")
	@CacheEvict(value = CacheConstant.SYS_DICT_CACHE, allEntries = true)
	@PostMapping("/create")
	public boolean create(@RequestBody @Validated({ValidationGroup.Create.class}) SysDictItemDTO dto) {
		return this.sysDictService.create(dto);
	}

	@Log("数据字典项修改")
	@ApiOperation("数据字典项修改")
	@CacheEvict(value = CacheConstant.SYS_DICT_CACHE, allEntries = true)
	@PostMapping("/update")
	public boolean update(@RequestBody @Validated({ValidationGroup.Update.class}) SysDictItemDTO dto) {
		return this.sysDictService.update(dto);
	}

	@Log("数据字典项删除")
	@ApiOperation("数据字典项删除")
	@CacheEvict(value = CacheConstant.SYS_DICT_CACHE, allEntries = true)
	@DeleteMapping("/delete/{id}")
	public boolean delete(@ApiParam("字典项ID") @PathVariable("id") String id) {
		return SqlHelper.retBool(sysDictItemMapper.deleteById(id));
	}

	@Log("数据字典项删除")
	@ApiOperation("数据字典项删除")
	@CacheEvict(value = CacheConstant.SYS_DICT_CACHE, allEntries = true)
	@DeleteMapping("/deleteBatch")
	public int deleteBatch(@ApiParam("字典项ID") @RequestParam(name = "ids") String ids) {
		if (StringUtils.hasLength(ids)) {
			return sysDictItemMapper.deleteBatchIds(Arrays.asList(ids.split(",")));
		}
		throw new AthenaRuntimeException("参数错误");
	}
}
