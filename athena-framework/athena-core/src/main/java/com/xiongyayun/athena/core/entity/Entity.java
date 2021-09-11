package com.xiongyayun.athena.core.entity;

import java.io.Serializable;

/**
 * 数据实体对象<br>
 * 数据实体类充当两个角色：<br>
 * 1. 数据的载体，一个Entity对应数据库中的一个row<br>
 * 2. SQL条件，Entity中的每一个字段对应一个条件，字段值对应条件的值
 *
 * @author Yayun.Xiong
 * @date 2020/11/28
 */
public interface Entity extends Serializable {
}
