package com.xiongyayun.athena.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xiongyayun.athena.db.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * DictItem
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2020/12/24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("`sys_dict_item`")
public class DictItem extends BaseModel {
	@TableId(type = IdType.ASSIGN_ID)
	private String id;
	private String dictId;
	private String itemLabel;
	private String itemValue;
	private String description;
	private Integer sortOrder;
	private Boolean enabled;
	private Boolean allowUpdate;
	private Boolean allowDelete;
}
