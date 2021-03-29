package com.xiongyayun.athena.system.controller;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xiongyayun.athena.core.annotation.Log;
import com.xiongyayun.athena.core.exception.AthenaRuntimeException;
import com.xiongyayun.athena.core.pagination.IPage;
import com.xiongyayun.athena.core.pagination.mybatisplus.Page;
import com.xiongyayun.athena.core.utils.ClassUtil;
import com.xiongyayun.athena.system.constant.CacheConstant;
import com.xiongyayun.athena.system.mapper.SysDictMapper;
import com.xiongyayun.athena.system.model.SysDict;
import com.xiongyayun.athena.system.service.SysDictService;
import com.xiongyayun.athena.system.vo.dict.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * DictController
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2020/12/24
 */
@Validated
@RestController
@RequestMapping("/sys/dict")
@Api(tags = {"系统数据字典服务"})
public class SysDictController {
	private static final Logger LOG = LoggerFactory.getLogger(SysDictController.class);
	@Resource
	private SysDictMapper dictMapper;

	@Resource
	private SysDictService sysDictService;

	@Resource
	private RedisTemplate<String, Object> redisTemplate;

	@Log("数据字典分页查询")
	@ApiOperation("数据字典分页查询")
	@PostMapping("/selectPage")
	public IPage<SysDict> selectPage(@RequestBody(required = false) SysDictQueryVO vo) {
		SysDict sysDict = new SysDict();
		BeanUtils.copyProperties(vo, sysDict);
		Page<SysDict> page = new Page<>(vo.getPageIndex(), vo.getPageSize());
		QueryWrapper<SysDict> wrapper = Wrappers.query(sysDict)
				.and(!ObjectUtils.isEmpty(vo.getCreateTime()) && vo.getCreateTime().length == 2, qw -> qw
						.ge(ClassUtil.getFieldName(SysDict.class, "createTime"), DateUtil.beginOfDay(vo.getCreateTime()[0]))
						.le(ClassUtil.getFieldName(SysDict.class, "createTime"), DateUtil.endOfDay(vo.getCreateTime()[1])))
				.and(!ObjectUtils.isEmpty(vo.getUpdateTime()) && vo.getUpdateTime().length == 2, qw -> qw
						.ge(ClassUtil.getFieldName(SysDict.class, "updateTime"), DateUtil.beginOfDay(vo.getUpdateTime()[0]))
						.le(ClassUtil.getFieldName(SysDict.class, "updateTime"), DateUtil.endOfDay(vo.getUpdateTime()[1])));
		if (vo.getSort() != null) {
			Arrays.stream(vo.getSort()).forEach(sort -> wrapper.orderBy(!ObjectUtils.isEmpty(sort), "ASC".equals(sort.getValue()), ClassUtil.getFieldName(SysDict.class, sort.getKey())));
		}
		return dictMapper.selectPage(page, wrapper);
	}

	@Log("获取全部字典数据")
	@ApiOperation("获取全部字典数据")
	@Cacheable(cacheNames = CacheConstant.SYS_DICT_CACHE)
	@GetMapping("/queryAllDictItems")
	public Map<String, List<SysDictItemVO>> queryAllDictItems() {
		return sysDictService.queryAllDictItems();
	}

	@Log("根据字典代码查询字典数据")
	@ApiOperation("根据字典代码查询字典数据")
	@GetMapping("/queryDictItems/{code}")
	public List<SysDictItemVO> queryDictItemsByCode(@ApiParam("字典代码") @PathVariable("code") String code) {
		return sysDictService.queryDictItemsByCode(code).stream().map(item -> new SysDictItemVO().setLabel(item.getLabel()).setValue(item.getValue())).collect(Collectors.toList());
	}

	@Log("根据主键查询数据字典")
	@ApiOperation("根据主键查询数据字典")
	@Cacheable(cacheNames = CacheConstant.SYS_DICT_CACHE, key = "#id")
	@GetMapping("/get/{id}")
	public SysDict selectById(@ApiParam("主键ID") @PathVariable("id") String id) {
		return dictMapper.selectById(id);
	}

	@Log("数据字典新增")
	@ApiOperation(value = "数据字典新增", notes = "系统内置数据字典", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	// TODO 添加 consumes produces swagger界面打不开，会报错
//	@PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PostMapping("/add")
	public SysDict add(@RequestBody @Validated({Grpup.Add.class}) DictAddVO vo) {
		SysDict sysDict = new SysDict();
		SysDict exist = this.sysDictService.queryDictByCode(vo.getDictCode());
		if (exist != null) {
			throw new AthenaRuntimeException("数据字典已存在!");
		}
		BeanUtils.copyProperties(vo, sysDict);
		sysDict.setId(null);
		if (dictMapper.insert(sysDict) > 0) {
			return this.sysDictService.queryDictByCode(sysDict.getDictCode());
		}
		return null;
	}

	@Log("数据字典修改")
	@CacheEvict(cacheNames = CacheConstant.SYS_DICT_CACHE, allEntries = true)
	@ApiOperation(value = "数据字典修改", notes = "系统内置数据字典", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PostMapping("/update")
	public int update(@RequestBody @Validated({Grpup.Update.class}) DictUpdateVO vo) {
		SysDict sysDict = new SysDict();
		SysDict exist = this.sysDictService.queryDictByCode(vo.getDictCode());
		if (exist == null) {
			throw new AthenaRuntimeException("数据字典不存在!");
		}
		BeanUtils.copyProperties(vo, sysDict);
		return dictMapper.updateById(sysDict);
	}

	@Log("数据字典删除")
	// 注意：使用@CacheEvict注解的方法必须是controller层直接调用，service里间接调用不生效。@CacheEvict的方法和@Cache的方法放到一个java文件中写，他俩在两个java文件的话，会导致@CacheEvict失效。
	// @see https://docs.spring.io/spring/docs/current/spring-framework-reference/integration.html#cache-annotations-evict
	@CacheEvict(cacheNames = CacheConstant.SYS_DICT_CACHE, allEntries = true)
	@ApiOperation("数据字典删除")
	@DeleteMapping("/delete/{id}")
	public int delete(@ApiParam("主键ID") @PathVariable("id") String id) {
		return dictMapper.deleteById(id);
	}

	@Log("数据字典删除")
	@CacheEvict(cacheNames = CacheConstant.SYS_DICT_CACHE, allEntries = true)
	@ApiOperation("数据字典删除")
	@DeleteMapping("/deletePhysic/{id}")
	public int deletePhysic(@PathVariable String id) {
		return dictMapper.deleteById(id);
	}

	/**
	 * 批量删除
	 * allEntries = true 调用之后清空所有缓存
	 *
	 * @param ids
	 * @return
	 */
	@DeleteMapping("/deleteBatch")
	@CacheEvict(value = CacheConstant.SYS_DICT_CACHE, allEntries = true)
	public int deleteBatch(@RequestParam(name = "ids") String ids) {
		if (StringUtils.hasLength(ids)) {
			return dictMapper.deleteBatchIds(Arrays.asList(ids.split(",")));
		}
		throw new AthenaRuntimeException("参数错误");
	}

	/**
	 * 刷新缓存
	 */
	@RequestMapping(value = "/refreshCache")
	public void refreshCache() {
		// 清空字典缓存
		Set keys = redisTemplate.keys(CacheConstant.SYS_DICT_CACHE + "*");
		keys.stream().forEach(key -> LOG.debug(key.toString()));
//		keys.stream().forEach(System.out::println);
		redisTemplate.delete(keys);
	}

}
