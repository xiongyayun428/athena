package com.xiongyayun.athena.system.controller;

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
import com.xiongyayun.athena.system.vo.DictVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * DictController
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2020/12/24
 */
@Validated
@RestController
@RequestMapping("/dict")
public class DictController {
	private static final Logger log = LoggerFactory.getLogger(DictController.class);
	@Resource
	private DictMapper dictMapper;
	@Resource
	private DictItemMapper dictItemMapper;

	@Log("数据字典分页查询")
	@PostMapping("/selectPage")
	public IPage<Dict> selectPage(@RequestBody DictVO vo) {
		Dict dict = new Dict();
		BeanUtils.copyProperties(vo, dict);

		Page<Dict> page = new Page<>(vo.getPageIndex(), vo.getPageSize());
		return dictMapper.selectPage(page, Wrappers.lambdaQuery(dict));
	}

	@Log("根据主键查询数据字典")
	@GetMapping("/getDictById/{id}")
	public Dict selectById(@PathVariable("id") String id) {
		return dictMapper.selectById(id);
	}

	@Log("数据字典新增")
	@PostMapping("/add")
	public void add(@RequestBody DictVO vo) {
		Dict dict = new Dict();
		Dict exist = getExistDict(vo.getDictCode());
		if (exist != null) {
			throw new AthenaRuntimeException("数据字典已存在!");
		}
		BeanUtils.copyProperties(vo, dict);
		dictMapper.insert(dict);
	}

	private Dict getExistDict(String dictCode) {
		if (!StringUtils.hasLength(dictCode)) {
			throw new AthenaRuntimeException("字典编码不能为空!");
		}
		return dictMapper.selectOne(Wrappers.<Dict>lambdaQuery().eq(Dict::getDictCode, dictCode));
	}

	@Log("数据字典项查询")
	@GetMapping("/item/{id}")
	public List<DictItem> item(@PathVariable("id") String id) {
		if (StringUtils.hasLength(id)) {
			return dictItemMapper.selectList(Wrappers.<DictItem>lambdaQuery()
					.eq(DictItem::getDictId, id)
					.orderByAsc(DictItem::getSortOrder, DictItem::getCreateTime)
			);
		}
		return null;
	}

	@Log("数据字典项新增")
	@PostMapping("/item/add")
	public void itemAdd(@RequestBody DictItemVO vo) {
		if (!StringUtils.hasLength(vo.getDictId())) {
			throw new AthenaRuntimeException("数据字典ID不能为空!");
		}
		Dict dict = dictMapper.selectById(vo.getDictId());
		if (dict == null) {
			throw new AthenaRuntimeException("数据字典不存在!");
		}
		if (!StringUtils.hasLength(vo.getItemValue())) {
			throw new AthenaRuntimeException("字典编码项值不能为空!");
		}
		DictItem exist = dictItemMapper.selectOne(Wrappers.<DictItem>lambdaQuery()
				.eq(DictItem::getDictId, vo.getDictId())
				.eq(DictItem::getItemValue, vo.getItemValue()));
		if (exist != null) {
			throw new AthenaRuntimeException("数据字典项已存在!");
		}
		DictItem dictItem = new DictItem();
		BeanUtils.copyProperties(vo, dictItem);
		dictItemMapper.insert(dictItem);
	}
}
