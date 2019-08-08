package com.ws;

import org.jboss.resteasy.plugins.server.netty.NettyJaxrsServer;
import org.jboss.resteasy.spi.ResteasyDeployment;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.ws.rs.ext.Provider;
import java.util.Collection;

public class Bootstrap {
    public static void main(String[] args) {
        try {
            ApplicationContext ac = new ClassPathXmlApplicationContext("root-context.xml");
            Assert.notNull(ac, "spring context must be not null!");
            ResteasyDeployment dp = new ResteasyDeployment();
            Collection<Object> providers = ac.getBeansWithAnnotation(Provider.class).values();
            Collection<Object> controllers = ac.getBeansWithAnnotation(Controller.class).values();
            // extract providers
            if (!CollectionUtils.isEmpty(providers)) {
                dp.getProviders().addAll(providers);
            }
            // extract only controller annotated beans
            if (!CollectionUtils.isEmpty(controllers)) {
                dp.getResources().addAll(controllers);
            }
            NettyJaxrsServer netty = new NettyJaxrsServer();
            netty.setDeployment(dp);
            netty.setHostname("0.0.0.0");
            netty.setPort(8039);
            netty.setRootResourcePath("/stickeasy");
            netty.setExecutorThreadCount(8);
            netty.setIoWorkerCount(4);
            netty.setMaxRequestSize(1000);
            netty.start();
            System.out.println("http server started success!!! at [{0.0.0.0}:{8039}]");
        } catch (Exception e) {
            System.out.println("server start error!!!");
        }
    }
}
