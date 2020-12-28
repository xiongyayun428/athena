package com.xiongyayun.athena.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xiongyayun.athena.db.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Dict
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2020/12/24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("`sys_dict`")
public class Dict extends BaseModel {
	// 指定主键生成策略使用雪花算法（默认策略
	@TableId(type = IdType.ASSIGN_ID)
	private String id;
	private String dictName;
	private String dictCode;
	private String description;
	private Boolean enabled;
	private Boolean allowUpdate;
	private Boolean allowDelete;
}
