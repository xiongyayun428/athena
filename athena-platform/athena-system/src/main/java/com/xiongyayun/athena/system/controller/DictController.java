package com.xiongyayun.athena.system.controller;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiongyayun.athena.core.utils.ClassUtil;
import com.xiongyayun.athena.system.vo.dict.DictAddVO;
import com.xiongyayun.athena.system.vo.dict.DictUpdateVO;
import com.xiongyayun.athena.system.vo.dict.Grpup;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xiongyayun.athena.core.annotation.Log;
import com.xiongyayun.athena.core.exception.AthenaRuntimeException;
import com.xiongyayun.athena.core.pagination.IPage;
import com.xiongyayun.athena.core.pagination.mybatisplus.Page;
import com.xiongyayun.athena.system.mapper.DictItemMapper;
import com.xiongyayun.athena.system.mapper.DictMapper;
import com.xiongyayun.athena.system.model.Dict;
import com.xiongyayun.athena.system.model.DictItem;
import com.xiongyayun.athena.system.vo.DictItemVO;
import com.xiongyayun.athena.system.vo.dict.DictVO;

/**
 * DictController
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2020/12/24
 */
@Validated
@RestController
@RequestMapping("/dict")
@Api(tags = {"数据字典服务"})
public class DictController {
	@Resource
	private DictMapper dictMapper;

	@Resource
	private RedisTemplate<String, Object> redisTemplate;

	@GetMapping("/test")
	public void test() {
		redisTemplate.boundValueOps("xyy-test").set("熊亚运");
	}

	@Log("数据字典分页查询")
	@ApiOperation("数据字典分页查询")
	@PostMapping("/selectPage")
	public IPage<Dict> selectPage(@RequestBody(required = false) DictVO vo) {
		Dict dict = new Dict();
		BeanUtils.copyProperties(vo, dict);
		Page<Dict> page = new Page<>(vo.getPageIndex(), vo.getPageSize());
		QueryWrapper<Dict> wrapper = Wrappers.query(dict)
				.and(!ObjectUtils.isEmpty(vo.getCreateTime()) && vo.getCreateTime().length == 2, qw -> qw
						.ge(ClassUtil.getFieldName(Dict.class, "createTime"), DateUtil.beginOfDay(vo.getCreateTime()[0]))
						.le(ClassUtil.getFieldName(Dict.class, "createTime"), DateUtil.endOfDay(vo.getCreateTime()[1])))
				.and(!ObjectUtils.isEmpty(vo.getUpdateTime()) && vo.getUpdateTime().length == 2, qw -> qw
						.ge(ClassUtil.getFieldName(Dict.class, "updateTime"), DateUtil.beginOfDay(vo.getUpdateTime()[0]))
						.le(ClassUtil.getFieldName(Dict.class, "updateTime"), DateUtil.endOfDay(vo.getUpdateTime()[1])));
		if (vo.getSort() != null) {
			Arrays.stream(vo.getSort()).forEach(sort -> wrapper.orderBy(!ObjectUtils.isEmpty(sort), "ASC".equals(sort.getValue()), ClassUtil.getFieldName(Dict.class, sort.getKey())));
		}
		return dictMapper.selectPage(page, wrapper);
	}

	@Resource
	private DictItemMapper dictItemMapper;

	@Log("根据主键查询数据字典")
	@ApiOperation("根据主键查询数据字典")
	@Cacheable(cacheNames = "dictCache", key = "#id")
	@GetMapping("/get/{id}")
	public Dict selectById(@ApiParam("主键ID") @PathVariable("id") String id) {
		return dictMapper.selectById(id);
	}

	@Log("数据字典新增")
	@CachePut(cacheNames = "dictCache", key = "#result.id")
	@ApiOperation(value = "数据字典新增", notes= "系统内置数据字典", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	// TODO 添加 consumes produces swagger界面打不开，会报错
//	@PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PostMapping("/add")
	public Dict add(@RequestBody @Validated({Grpup.Add.class}) DictAddVO vo) {
		Dict dict = new Dict();
		Dict exist = getExistDict(vo.getDictCode());
		if (exist != null) {
			throw new AthenaRuntimeException("数据字典已存在!");
		}
		BeanUtils.copyProperties(vo, dict);
		dict.setId(null);
		if (dictMapper.insert(dict) > 0) {
			return getExistDict(dict.getDictCode());
		}
		return null;
	}

	@Log("数据字典修改")
	@CachePut(cacheNames = "dictCache", key = "#id")
	@ApiOperation(value = "数据字典修改", notes= "系统内置数据字典", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PostMapping("/update")
	public int update(@RequestBody @Validated({Grpup.Update.class}) DictUpdateVO vo) {
		Dict dict = new Dict();
		Dict exist = getExistDict(vo.getDictCode());
		if (exist == null) {
			throw new AthenaRuntimeException("数据字典不存在!");
		}
		BeanUtils.copyProperties(vo, dict);
		return dictMapper.updateById(dict);
	}

	@Log("数据字典删除")
	// 注意：使用@CacheEvict注解的方法必须是controller层直接调用，service里间接调用不生效。@CacheEvict的方法和@Cache的方法放到一个java文件中写，他俩在两个java文件的话，会导致@CacheEvict失效。
	// @see https://docs.spring.io/spring/docs/current/spring-framework-reference/integration.html#cache-annotations-evict
	@CacheEvict(cacheNames = "dictCache", key = "#id")
	@ApiOperation("数据字典删除")
	@GetMapping("/delete/{id}")
	public int delete(@ApiParam("主键ID") @PathVariable("id") String id) {
		return dictMapper.deleteById(id);
	}

	private Dict getExistDict(String dictCode) {
		if (!StringUtils.hasLength(dictCode)) {
			throw new AthenaRuntimeException("数据字典编码不能为空!");
		}
		return dictMapper.selectOne(Wrappers.<Dict>lambdaQuery().eq(Dict::getDictCode, dictCode));
	}

	@Log("数据字典项分页查询")
	@ApiOperation("数据字典项分查询")
	@PostMapping("/itemPage")
	public IPage<DictItem> itemPage(@RequestBody(required = false) DictItemVO vo) {
		Page<DictItem> page = new Page<>(vo.getPageIndex(), vo.getPageSize());
		return dictItemMapper.selectPage(page, dictItemQueryWrapper(vo));
	}

	@Log("数据字典项查询")
	@Cacheable(cacheNames = "dictItemListCache", key = "#dictCode")
	@ApiOperation("数据字典项查询")
	@PostMapping("/item")
	public List<DictItem> item(@RequestBody(required = false) DictItemVO vo) {
		return dictItemMapper.selectList(dictItemQueryWrapper(vo));
	}

	private QueryWrapper<DictItem> dictItemQueryWrapper(DictItemVO vo) {
		if (!StringUtils.hasLength(vo.getDictId()) && StringUtils.hasLength(vo.getDictCode())) {
			vo.setDictId(getExistDict(vo.getDictCode()).getId());
		}
		DictItem dictItem = new DictItem();
		BeanUtils.copyProperties(vo, dictItem);

		if (!StringUtils.hasLength(dictItem.getDictId())) {
			throw new AthenaRuntimeException("字典ID不能为空!");
		}
		QueryWrapper<DictItem> wrapper = Wrappers.query(dictItem);
		if (vo.getSort() != null) {
			Arrays.stream(vo.getSort()).forEach(sort -> wrapper.orderBy(!ObjectUtils.isEmpty(sort), "ASC".equals(sort.getValue()), ClassUtil.getFieldName(DictItem.class, sort.getKey())));
		}
		return wrapper;
	}

	@Log("数据字典项新增")
	@CachePut(cacheNames = "dictItemListCache", key = "#dictCode")
	@ApiOperation("数据字典项新增")
	@PostMapping("/item/add")
	public int itemAdd(@RequestBody DictItemVO vo) {
		DictItem exist = checkExist(vo);
		if (exist != null) {
			throw new AthenaRuntimeException("数据字典项已存在!");
		}
		DictItem dictItem = new DictItem();
		BeanUtils.copyProperties(vo, dictItem);
		dictItem.setId(null);
		return dictItemMapper.insert(dictItem);
	}

	@Log("数据字典项修改")
	@CachePut(cacheNames = "dictItemListCache", key = "#dictCode")
	@ApiOperation("数据字典项修改")
	@PostMapping("/item/update")
	public int itemUpdate(@RequestBody DictItemVO vo) {
		vo.setId(null);
		DictItem exist = checkExist(vo);
		if (exist == null) {
			throw new AthenaRuntimeException("数据字典项不存在!");
		}
		BeanUtils.copyProperties(vo, exist);
		return dictItemMapper.updateById(exist);
	}

	private DictItem checkExist(DictItemVO vo) {
		if (!StringUtils.hasLength(vo.getDictId())) {
			throw new AthenaRuntimeException("数据字典ID不能为空!");
		}
		Dict dict = dictMapper.selectById(vo.getDictId());
		if (dict == null) {
			throw new AthenaRuntimeException("数据字典不存在!");
		}
		return dictItemMapper.selectOne(Wrappers.<DictItem>lambdaQuery()
				.eq(DictItem::getDictId, vo.getDictId())
				.eq(DictItem::getValue, vo.getValue()));
	}

	@Log("数据字典项删除")
	@CacheEvict(cacheNames = "dictItemListCache", key = "#dictCode")
	@ApiOperation("数据字典项删除")
	@GetMapping("/item/delete/{id}")
	public int itemDelete(@ApiParam("主键ID") @PathVariable("id") String id) {
		return dictItemMapper.deleteById(id);
	}
}
