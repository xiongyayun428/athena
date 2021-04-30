package com.xiongyayun.athena.core.mybatis.fieldfill;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * AthenaMetaObjectHandler
 *
 * @author <a href="mailto:xiongyayun428@163.com">Yayun.Xiong</a>
 * @date 2021/4/23
 */
@Component
public class MyBatisPlusMetaObjectHandler implements MetaObjectHandler {
	private static final String CREATE_BY = "createBy";

	private static final String CREATE_TIME = "createTime";

	private static final String LAST_UPDATE_BY = "lastUpdateBy";

	private static final String LAST_UPDATE_TIME = "lastUpdateTime";

	@Override
	public void insertFill(MetaObject metaObject) {
		this.strictInsertFill(metaObject, CREATE_BY, String.class, "admin");
		this.strictInsertFill(metaObject, CREATE_TIME, () -> LocalDateTime.now(), LocalDateTime.class);
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		this.strictUpdateFill(metaObject, LAST_UPDATE_BY, String.class, "admin");
		this.strictUpdateFill(metaObject, LAST_UPDATE_TIME, LocalDateTime.class, LocalDateTime.now());
	}
}
