package com.xiongyayun.athena.system.vo.dict;

import com.xiongyayun.athena.core.BaseVO;
import com.xiongyayun.athena.system.vo.DictItemVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

/**
 * DictVO
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2020/12/24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("数据字典")
public class DictVO extends BaseVO {
	@ApiModelProperty(value = "数据字典主键ID", required = true)
	@NotBlank(message = "数据字典主键ID不能为空", groups = {Grpup.Update.class})
	private String id;
	@ApiModelProperty("数据字典名称")
	@NotBlank(message = "数据字典名称不能为空", groups = {Grpup.Add.class})
	private String dictName;
	@ApiModelProperty("数据字典代码")
	@NotBlank(message = "数据字典名称不能为空", groups = {Grpup.Add.class})
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
