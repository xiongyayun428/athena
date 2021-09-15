package com.xiongyayun.athena.components.autoconfigure.druid;

import com.xiongyayun.athena.components.common.doc.ExtendPrintMsg;
import com.xiongyayun.athena.components.druid.DruidExtendPrintMsg;
import com.xiongyayun.athena.components.swagger.SwaggerExtendPrintMsg;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * DruidConfig
 *
 * @author Yayun.Xiong
 * @date 2021/9/13
 */
@Configuration
public class DruidConfig {
	@Bean
	@ConditionalOnClass(SwaggerExtendPrintMsg.class)
	public ExtendPrintMsg druidExtendPrintMsg() {
		return new DruidExtendPrintMsg();
	}


}
