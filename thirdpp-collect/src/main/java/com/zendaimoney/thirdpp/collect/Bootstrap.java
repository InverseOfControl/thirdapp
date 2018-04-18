package com.zendaimoney.thirdpp.collect;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 应用启动类
 * Created by YM20051 on 2017/7/4.
 */
public class Bootstrap {

    public static void main(String[] args) {
        try {
            ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                    new String[]{"classpath:spring-context.xml"});
            context.start();

        } catch (RuntimeException e) {
            e.printStackTrace();
            System.exit(1);
        }
        synchronized (Bootstrap.class) {
            while (true) {
                try {
                    Bootstrap.class.wait();
                } catch (Throwable e) {
                }
            }
        }
    }
}
