package com.xiongyayun.athena.system.dao.dict.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xiongyayun.athena.db.model.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * DictItem
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2020/12/24
 */
@ApiModel("数据字典项")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("`sys_dict_item`")
public class SysDictItem extends BaseModel {
	@TableId(type = IdType.ASSIGN_ID)
	@ApiModelProperty("主键ID")
	private String id;
	@ApiModelProperty("数据字典主键ID")
	private String dictId;
	@ApiModelProperty("字典项显示值")
	private String label;
	@ApiModelProperty("字典项值")
	private String value;
	@ApiModelProperty("描述")
	private String description;
	@ApiModelProperty("排序顺序")
	private Integer sortOrder;
	@ApiModelProperty("是否启用？")
	private Boolean enabled;
	@ApiModelProperty("语言")
	private String language;
	@ApiModelProperty("是否允许修改")
	private Boolean allowUpdate;
	@ApiModelProperty("是否允许删除")
	private Boolean allowDelete;
}
