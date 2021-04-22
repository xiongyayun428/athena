package com.xiongyayun.athena.system.modules.dict.vo;

import com.xiongyayun.athena.core.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * DictItemVO
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2020/12/24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("数据字典项")
public class DictItemVO extends BaseVO {
	@ApiModelProperty("数据字典项主键ID")
	private String id;
	@ApiModelProperty("数据字典主键ID")
	private String dictId;
	@ApiModelProperty("数据字典编码")
	private String dictCode;
	@ApiModelProperty("数据字典项显示值")
	private String label;
	@ApiModelProperty("数据字典项值")
	private String value;
	@ApiModelProperty("数据字典项描述")
	private String description;
	@ApiModelProperty("数据字典项排序顺序")
	private Integer sortOrder;
	@ApiModelProperty("数据字典项是否启用？默认启用")
	private Boolean enabled;
	@ApiModelProperty("数据字典项是否允许修改？默认允许")
	private Boolean allowUpdate;
	@ApiModelProperty("数据字典项是否允许删除？默认允许")
	private Boolean allowDelete;
}
