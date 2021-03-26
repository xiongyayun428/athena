package com.xiongyayun.athena.system.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xiongyayun.athena.db.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Menu
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2021/2/26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("`menu`")
public class Menu extends BaseModel {
}
