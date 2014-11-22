package com.artemz.demo;

import org.apache.felix.bundlerepository.RepositoryAdmin;
import org.apache.felix.bundlerepository.impl.Activator;
import org.apache.felix.framework.util.FelixConstants;
import org.osgi.framework.*;
import org.osgi.framework.launch.Framework;
import org.osgi.framework.launch.FrameworkFactory;
import org.osgi.util.tracker.ServiceTracker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: artemz
 * Date: 10/24/14
 * Time: 8:44 AM
 * To change this template use File | Settings | File Templates.
 */
public class Launcher {
    private static ServiceTracker adminTracker = null;
    public static BundleContext launch() throws BundleException, IOException {
        FrameworkFactory frameworkFactory = ServiceLoader.load(FrameworkFactory.class).iterator().next();
        System.out.println("FrameworkFactory loaded");
        Map config = new HashMap();

        List<BundleActivator> activators = new ArrayList<BundleActivator>();

        config.put(FelixConstants.SYSTEMBUNDLE_ACTIVATORS_PROP, activators);

        config.put(Constants.FRAMEWORK_SYSTEMPACKAGES_EXTRA,
                "org.apache.felix.bundlerepository; version=2.1," +
                        "ru.multicabinet.module.api" +
                        "ru.mutlicabinet.bundle"
        );

        // automated bundles deployment
        config.put("felix.fileinstall.dir", "./base-bundles");
        config.put("felix.fileinstall.noInitialDelay", "true");
        config.put("felix.fileinstall.start.level", "4");


        Framework framework = frameworkFactory.newFramework(config);

        System.out.println("Framework has been produced");
        framework.init();
        framework.start();
        System.out.println("Framework has been started");
        /*adminTracker = new ServiceTracker(adminConsumer.getContext(), RepositoryAdmin.class.getName(), null);
        adminTracker.open();*/
        return framework.getBundleContext();
    }
    public static Bundle installLocalBundle(BundleContext ctx, String bundlePath) throws FileNotFoundException, BundleException{
        File bundleFile = new File(bundlePath);
        if (!bundleFile.exists()){
            throw new FileNotFoundException();
        }
        return ctx.installBundle("file://" + bundleFile.getAbsolutePath());
    }
    public static void execute(String name)
    {
        // See if any of the currently tracked command services
        // match the specified command name, if so then execute it.
        Object[] services = adminTracker.getServices();
        System.out.println("Number of services in adminTracker: " + services.length);
        for (int i = 0; (services != null) && (i < services.length); i++)
        {
            System.out.println(services[i].getClass().getName());
            try
            {
                if (((RepositoryAdmin)services[i]).getClass().getName().equals(name))
                {
                    System.out.println("found");
                }
            }
            catch (Exception ex)
            {
                // Since the services returned by the tracker could become
                // invalid at any moment, we will catch all exceptions, log
                // a message, and then ignore faulty services.
                System.err.println(ex);
            }
        }
    }
}
