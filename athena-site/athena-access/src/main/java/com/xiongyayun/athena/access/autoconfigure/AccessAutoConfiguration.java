package com.xiongyayun.athena.access.autoconfigure;

import com.xiongyayun.athena.access.properties.DataAccessProperties;
import com.xiongyayun.athena.access.properties.OperationAccessProperties;
import com.xiongyayun.athena.access.properties.PageAccessProperties;
import com.xiongyayun.athena.access.service.PageAccessService;
import com.xiongyayun.athena.access.service.impl.PageAccessServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 访问权限自动配置类
 *
 * @author: Yayun.Xiong
 * @date 2019-04-22
 */
@Configuration
//@EnableConfigurationProperties({PageAccessProperties.class, OperationAccessProperties.class, DataAccessProperties.class})
@Import({DataAccessAutoConfiguration.class, OperationAccessAutoConfiguration.class, DataAccessAutoConfiguration.class})
public class AccessAutoConfiguration {
    @Autowired
    private PageAccessProperties pageAccessProperties;
    @Autowired
    private OperationAccessProperties operationAccessProperties;
    @Autowired
    private DataAccessProperties dataAccessProperties;

    /**
     * @ConditionalOnMissingBean(PageAccessService.class)//这个配置就是SpringBoot可以优先使用自定义Bean的核心所在，如果没有我们的自定义Bean那么才会自动配置一个新的Bean
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(PageAccessService.class)
    public PageAccessService autoPageAccessService(){
        PageAccessService pas = new PageAccessServiceImpl();
        return pas;
    }
}
