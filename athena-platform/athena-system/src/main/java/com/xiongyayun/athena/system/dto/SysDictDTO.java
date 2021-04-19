package com.xiongyayun.athena.system.dto;

import com.xiongyayun.athena.core.ValidationGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * SysDictDTO
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2021/4/19
 */
@ApiModel("数据字典")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysDictDTO implements Serializable {
	@ApiModelProperty(value = "数据字典主键ID", required = true)
	@NotBlank(message = "数据字典主键ID不能为空", groups = {ValidationGroup.Update.class})
	private String id;

	@ApiModelProperty(value = "数据字典名称", required = true)
	@NotBlank(message = "数据字典名称不能为空", groups = {ValidationGroup.Create.class, ValidationGroup.Update.class})
	private String dictName;

	@ApiModelProperty(value = "数据字典代码", required = true)
	@NotBlank(message = "数据字典代码不能为空", groups = {ValidationGroup.Create.class, ValidationGroup.Update.class})
	private String dictCode;

	@ApiModelProperty("数据字典描述")
	private String description;
	@ApiModelProperty("数据字典是否启用？默认启用")
	private Boolean enabled;
	@ApiModelProperty("数据字典是否允许修改？默认允许")
	private Boolean allowUpdate;
	@ApiModelProperty("数据字典是否允许删除？默认允许")
	private Boolean allowDelete;
	@Valid
	private List<SysDictItemDTO> items;
}
