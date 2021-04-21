package com.xiongyayun.athena.core.validation.dict;

import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

/**
 * DictValidator
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2021/4/20
 */
@Slf4j
public class DictValidator implements ConstraintValidator<Dict, String> {
	private String[] dictCode;

	private DictService dictService;

	public DictValidator() {
		dictService = SpringUtil.getBean(DictService.class);
	}

	@Override
	public void initialize(Dict constraintAnnotation) {
		this.dictCode = constraintAnnotation.value();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		List<String> values = dictService.queryDictItems(dictCode);
		if (ObjectUtils.isEmpty(values) && log.isDebugEnabled()) {
			log.debug("字典数据未配置：" + String.join(", ", dictCode));
		}
		return ObjectUtils.isEmpty(values) ? false : values.contains(value);
	}
}
