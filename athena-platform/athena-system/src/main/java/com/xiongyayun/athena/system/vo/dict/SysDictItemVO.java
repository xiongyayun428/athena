package com.xiongyayun.athena.system.vo.dict;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * SysDictVO
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2021/3/29
 */
@Data
@Accessors(chain = true)
@ApiModel("数据字典项")
public class SysDictItemVO {
	@ApiModelProperty("字典项显示值")
	private String label;
	@ApiModelProperty("字典项值")
	private String value;
}
