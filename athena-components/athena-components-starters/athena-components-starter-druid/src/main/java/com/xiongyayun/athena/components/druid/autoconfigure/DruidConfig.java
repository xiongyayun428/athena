package com.xiongyayun.athena.components.druid.autoconfigure;

import com.xiongyayun.athena.components.common.doc.ExtendPrintMsg;
import com.xiongyayun.athena.components.druid.DruidExtendPrintMsg;
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
	public ExtendPrintMsg druidExtendPrintMsg() {
		return new DruidExtendPrintMsg();
	}


}
