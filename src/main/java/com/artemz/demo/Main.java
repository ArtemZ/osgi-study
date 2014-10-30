package com.artemz.demo;


import com.artemz.demo.obr.ObrManager;
import org.apache.felix.bundlerepository.Repository;
import org.apache.felix.bundlerepository.RepositoryAdmin;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.launch.Framework;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: artemz
 * Date: 10/23/14
 * Time: 4:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    private static Framework framework;

    public static void main(String[] args) throws InterruptedException{
        BundleContext context = null;
        Bundle admin = null;
        try {
            context = Launcher.launch();

            //Installing repository admin bundle
            admin = Launcher.installLocalBundle(context, "base-bundles/org.apache.felix.bundlerepository-2.0.2.jar");
            admin.start();

        } catch (BundleException e){
            System.out.println("BundleException : " + e.getMessage());
            System.exit(1);
        } catch (IOException exception){
            exception.printStackTrace();
            System.exit(1);
        }
        final RepositoryAdminConsumer adminConsumer = Launcher.getAdminConsumer();

        //ObrManager manager = new ObrManager(context);
        //System.out.println(adminConsumer.getContext().getBundles());
/*
        Repository[] repos = manager.listRepositories();
        System.out.println("Repositories: " + repos);
        System.out.println("Local repo: " + manager.getLocalRepository().getURI());
*/
        /*for (int i = 0; i < admin.getRegisteredServices().length; i++){
            System.out.println("repo: " + admin.getRegisteredServices()[i]);
            ServiceReference ref = admin.getRegisteredServices()[i];

            String[] props = ref.getPropertyKeys();

            for (int m = 0; m < props.length; m++){
                System.out.println("property: " + props[m]);
                if (props[m].equals("objectClass")){
                    for (int o = 0; o < ((String[])ref.getProperty(props[i])).length; o++){
                        if (((String[])ref.getProperty(props[i]))[o].equals(RepositoryAdmin.class.getName())){
                            RepositoryAdminHolder.setRepositoryAdmin((RepositoryAdmin)admin.getBundleContext().getService(ref));
                        }
                        System.out.println(((String[])ref.getProperty(props[i]))[o]);
                    }
                }
            }


        }*/
        /*
            Printing all installed bundles from main context
         */
        Bundle[] bundles = context.getBundles();
        for (int i = 0; i < bundles.length; i++){
            System.out.println("Installed bundle: " + bundles[i].getSymbolicName());
            ServiceReference<?>[] services = bundles[i].getRegisteredServices();
            /*
                Printing out all available services in this bundle
             */
            for (int o = 0; o < services.length; o++){
                System.out.println(services[o].getProperty("objectClass"));
            }

        }

        RepositoryAdmin repositoryAdmin = null;

        BundleManager bundleManager = new BundleManager(admin);
        bundleManager.listAvailableServices();
        repositoryAdmin = (RepositoryAdmin)bundleManager.getService(RepositoryAdmin.class.getName());

        /*ServiceReference ref =
                admin.getBundleContext().getServiceReference(RepositoryAdmin.class.getName());


        if (ref != null) {
            repositoryAdmin = (RepositoryAdmin)admin.getBundleContext().getService(ref);
        } else {
            System.out.println("ref is null");
            System.exit(1);
        }*/
        Repository[] repositories = repositoryAdmin.listRepositories();

        System.out.println("Repo count: " + repositories.length);
        for (int i = 0; i < repositories.length; i++){
            System.out.println(repositories[i].getName() + " uri: " + repositories[i].getURI());
        }

        System.out.println("OK");
        //System.exit(0);
    }
}
