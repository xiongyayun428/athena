package com.xiongyayun.athena.system.modules.menu.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xiongyayun.athena.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * Menu
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2021/2/26
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("`menu`")
public class Menu extends BaseEntity {
}
