package com.xiongyayun.athena.system.modules.dict.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xiongyayun.athena.core.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * Dict
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2020/12/24
 */
@ApiModel("数据字典")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("sys_dict")
public class SysDict extends BaseEntity {
	/** 指定主键生成策略使用雪花算法（默认策略）*/
	@TableId(type = IdType.ASSIGN_ID)
	@ApiModelProperty("主键ID")
	private String id;
	@ApiModelProperty("字典名称")
	private String dictName;
	@ApiModelProperty("字典编码")
	private String dictCode;
	@ApiModelProperty("描述")
	private String description;
	@ApiModelProperty("是否启用？")
	private Boolean enabled;
	@ApiModelProperty("是否允许修改")
	private Boolean allowUpdate;
	@ApiModelProperty("是否允许删除")
	private Boolean allowDelete;
}
