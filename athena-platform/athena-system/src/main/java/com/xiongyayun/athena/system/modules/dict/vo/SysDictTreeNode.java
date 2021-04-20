package com.xiongyayun.athena.system.modules.dict.vo;

import com.xiongyayun.athena.system.modules.dict.dto.SysDictItemDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

/**
 * SysDictTreeNode
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2021/4/20
 */
@Data
@ApiModel("数据字典树")
public class SysDictTreeNode implements Serializable {
	@ApiModelProperty(value = "数据字典主键ID")
	private String id;

	@ApiModelProperty(value = "数据字典名称")
	private String dictName;

	@ApiModelProperty(value = "数据字典代码")
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
	private List<SysDictItemDTO> children;
}
