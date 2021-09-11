package com.xiongyayun.athena.core.validation.dict;

import cn.hutool.extra.spring.SpringUtil;
import com.xiongyayun.athena.core.constant.CommonConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.Optional;

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
