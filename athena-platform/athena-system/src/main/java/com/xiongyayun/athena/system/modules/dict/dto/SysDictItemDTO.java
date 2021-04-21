package com.xiongyayun.athena.system.modules.dict.dto;

import com.xiongyayun.athena.core.ValidationGroup;
import com.xiongyayun.athena.core.validation.dict.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * SysDictItemDTO
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2021/4/19
 */
@ApiModel("数据字典项")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysDictItemDTO implements Serializable {
	@ApiModelProperty("主键ID")
	@NotBlank(groups = {ValidationGroup.Update.class}, message = "字典项主键不能为空")
	private String id;
	@ApiModelProperty("数据字典主键ID")
	@NotBlank(groups = {ValidationGroup.Create.class}, message = "字典主键不能为空")
	private String dictId;
	@ApiModelProperty("字典项显示值")
	@NotBlank(message = "字典项显示值不能为空")
	private String label;
	@ApiModelProperty("字典项值")
	@NotBlank(message = "字典项值不能为空")
	private String value;
	@ApiModelProperty("描述")
	private String description;
	@ApiModelProperty("排序顺序")
	private Integer sortOrder;
	@ApiModelProperty("是否启用？")
	private Boolean enabled;
	@ApiModelProperty("语言")
	@Dict(value = "language", message = "语言不正确")
	@DateTimeFormat
	private String language;
	@ApiModelProperty("是否允许修改")
	private Boolean allowUpdate;
	@ApiModelProperty("是否允许删除")
	private Boolean allowDelete;
}
