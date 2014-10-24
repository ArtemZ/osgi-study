package com.artemz.demo;

import org.osgi.framework.BundleException;
import org.osgi.framework.launch.Framework;
import org.apache.felix.framework.Logger;
import org.osgi.framework.launch.FrameworkFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * Created with IntelliJ IDEA.
 * User: artemz
 * Date: 10/24/14
 * Time: 8:44 AM
 * To change this template use File | Settings | File Templates.
 */
public class Launcher {
    private static Logger log = new Logger();

    public static Framework launch() throws BundleException{
        FrameworkFactory frameworkFactory = ServiceLoader.load(FrameworkFactory.class).iterator().next();
        System.out.println("FrameworkFactory loaded");
        Map config = new HashMap();

        config.put("felix.log.level", "4");

        Framework framework = frameworkFactory.newFramework(config);
        System.out.println("Framework has been produced");
        framework.init();
        framework.start();
        System.out.println("Framework has been started");
        return framework;
    }
}
