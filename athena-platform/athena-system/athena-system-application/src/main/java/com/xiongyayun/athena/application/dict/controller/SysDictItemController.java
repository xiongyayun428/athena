package com.xiongyayun.athena.application.dict.controller;

import com.xiongyayun.athena.application.dict.service.SysDictService;
import io.swagger.annotations.Api;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * SysDictItemController
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
	private SysDictService sysDictService;

//	@Log("数据字典项分页查询")
//	@ApiOperation("数据字典项分查询")
//	@PostMapping("/selectPage")
//	public IPage<SysDictItem> selectPage(@RequestBody(required = false) DictItemVO vo) {
//		Page<SysDictItem> page = new Page<>(vo.getPageIndex(), vo.getPageSize());
//		return sysDictItemMapper.selectPage(page, dictItemQueryWrapper(vo));
//	}
//
//	@Log("数据字典项查询")
//	@Cacheable(cacheNames = "dictItemListCache", key = "#dictCode")
//	@ApiOperation("数据字典项查询")
//	@PostMapping("/list")
//	public List<SysDictItem> list(@RequestBody(required = false) DictItemVO vo) {
//		return sysDictItemMapper.selectList(dictItemQueryWrapper(vo));
//	}
//
//	private QueryWrapper<SysDictItem> dictItemQueryWrapper(DictItemVO vo) {
//		if (!StringUtils.hasLength(vo.getDictId()) && StringUtils.hasLength(vo.getDictCode())) {
//			vo.setDictId(this.sysDictService.queryDictByCode(vo.getDictCode()).getId());
//		}
//		SysDictItem sysDictItem = new SysDictItem();
//		BeanUtils.copyProperties(vo, sysDictItem);
//
//		if (!StringUtils.hasLength(sysDictItem.getDictId())) {
//			throw new AthenaRuntimeException("字典ID不能为空!");
//		}
//		QueryWrapper<SysDictItem> wrapper = Wrappers.query(sysDictItem);
//		if (vo.getSort() != null) {
//			Arrays.stream(vo.getSort()).forEach(sort -> wrapper.orderBy(!ObjectUtils.isEmpty(sort), "ASC".equals(sort.getValue()), ClassUtil.getFieldName(SysDictItem.class, sort.getKey())));
//		}
//		return wrapper;
//	}
//
//	@Log("数据字典项新增")
//	@ApiOperation("数据字典项新增")
//	@CacheEvict(value = CacheConstant.SYS_DICT_CACHE, allEntries = true)
//	@PostMapping("/item/add")
//	public int itemAdd(@RequestBody DictItemVO vo) {
//		SysDictItem exist = checkExist(vo);
//		if (exist != null) {
//			throw new AthenaRuntimeException("数据字典项已存在!");
//		}
//		SysDictItem sysDictItem = new SysDictItem();
//		BeanUtils.copyProperties(vo, sysDictItem);
//		sysDictItem.setId(null);
//		return sysDictItemMapper.insert(sysDictItem);
//	}
//
//	@Log("数据字典项修改")
//	@ApiOperation("数据字典项修改")
//	@CacheEvict(value = CacheConstant.SYS_DICT_CACHE, allEntries = true)
//	@PostMapping("/update")
//	public int itemUpdate(@RequestBody DictItemVO vo) {
//		vo.setId(null);
//		SysDictItem exist = checkExist(vo);
//		if (exist == null) {
//			throw new AthenaRuntimeException("数据字典项不存在!");
//		}
//		BeanUtils.copyProperties(vo, exist);
//		return sysDictItemMapper.updateById(exist);
//	}
//
//	@Log("数据字典项删除")
//	@ApiOperation("数据字典项删除")
//	@CacheEvict(value = CacheConstant.SYS_DICT_CACHE, allEntries = true)
//	@DeleteMapping("/delete/{id}")
//	public int itemDelete(@ApiParam("主键ID") @PathVariable("id") String id) {
//		return sysDictItemMapper.deleteById(id);
//	}
//
//	@Log("数据字典项删除")
//	@ApiOperation("数据字典项删除")
//	@CacheEvict(value = CacheConstant.SYS_DICT_CACHE, allEntries = true)
//	@DeleteMapping("/deleteBatch")
//	public int deleteBatch(@ApiParam("主键ID") @RequestParam(name = "ids") String ids) {
//		if (StringUtils.hasLength(ids)) {
//			return sysDictItemMapper.deleteBatchIds(Arrays.asList(ids.split(",")));
//		}
//		throw new AthenaRuntimeException("参数错误");
//	}
//
//
//	private SysDictItem checkExist(DictItemVO vo) {
//		if (!StringUtils.hasLength(vo.getDictId())) {
//			throw new AthenaRuntimeException("数据字典ID不能为空!");
//		}
//		SysDict sysDict = dictMapper.selectById(vo.getDictId());
//		if (sysDict == null) {
//			throw new AthenaRuntimeException("数据字典不存在!");
//		}
//		return sysDictItemMapper.selectOne(Wrappers.<SysDictItem>lambdaQuery()
//				.eq(SysDictItem::getDictId, vo.getDictId())
//				.eq(SysDictItem::getValue, vo.getValue()));
//	}
}
