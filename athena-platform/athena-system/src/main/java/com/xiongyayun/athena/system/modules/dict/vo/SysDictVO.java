package com.xiongyayun.athena.system.modules.dict.vo;

import com.xiongyayun.athena.core.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * SysDictVO
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2021/4/19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("数据字典")
public class SysDictVO extends BaseVO {
	@ApiModelProperty("数据字典主键ID")
	private String id;
	@ApiModelProperty("数据字典名称")
	private String dictName;
	@ApiModelProperty("数据字典代码")
	private String dictCode;
	@ApiModelProperty("数据字典描述")
	private String description;
	@ApiModelProperty("数据字典是否启用？默认启用")
	private Boolean enabled;
	@ApiModelProperty("数据字典是否允许修改？默认允许")
	private Boolean allowUpdate;
	@ApiModelProperty("数据字典是否允许删除？默认允许")
	private Boolean allowDelete;

	private String createBy;
	private Date[] createTime;
	private String updateBy;
	private Date[] updateTime;
}
