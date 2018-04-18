package com.zendaimoney.thirdpp.trade.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component("springUtils")
@Lazy(false)
public class SpringContextHelper implements ApplicationContextAware {
    private static ApplicationContext context;

    public void setApplicationContext(ApplicationContext context)
            throws BeansException {
        // 在加载Spring时自动获得context
        SpringContextHelper.context = context;
    }

    public static Object getBean(String beanName) {
        return context.getBean(beanName);
    }

}
