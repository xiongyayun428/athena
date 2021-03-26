package com.xiongyayun.athena.system.vo.dict;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * DictAddVO
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2021/2/25
 */
@Data
@ApiModel("数据字典新增")
public class DictAddVO {
	@ApiModelProperty(value = "数据字典名称", required = true)
	@NotBlank(message = "数据字典名称不能为空", groups = {Grpup.Add.class})
	private String dictName;
	@ApiModelProperty(value = "数据字典代码", required = true)
	@NotBlank(message = "数据字典代码不能为空", groups = {Grpup.Add.class})
	private String dictCode;
	@ApiModelProperty("数据字典描述")
	private String description;
	@ApiModelProperty("数据字典是否启用？默认启用")
	private Boolean enabled;
	@ApiModelProperty("数据字典是否允许修改？默认允许")
	private Boolean allowUpdate;
	@ApiModelProperty("数据字典是否允许删除？默认允许")
	private Boolean allowDelete;
}

