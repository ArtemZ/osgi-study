package com.artemz.demo;


import org.apache.felix.bundlerepository.*;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.launch.Framework;
import ru.multicabinet.bundle.BundleType;
import ru.multicabinet.bundle.MulticabinetBundle;

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

    public static void main(String[] args) throws Exception{
        BundleContext context = null;
        Bundle admin = null, mcom = null;
        try {
            context = Launcher.launch();

            //Installing repository admin bundle
            admin = Launcher.installLocalBundle(context, "base-bundles/org.apache.felix.bundlerepository-2.0.2.jar");
            admin.start();

            //Installing multicabinet OSGi manager
            mcom = Launcher.installLocalBundle(context, "base-bundles/multicabinet-osgi-manager-1.0-SNAPSHOT.jar");
            mcom.start();

        } catch (BundleException e){
            System.out.println("BundleException : " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        } catch (IOException exception){
            exception.printStackTrace();
            System.exit(1);
        }


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
        for (Bundle bundle : context.getBundles()){
            System.out.println("Installed bundle: " + bundle.getSymbolicName());
            ServiceReference<?>[] services = bundle.getRegisteredServices();

            if (services != null){

                for (ServiceReference reference : bundle.getRegisteredServices()){
                    for(String objectClass : (String[])reference.getProperty("objectClass")){
                        System.out.println(objectClass);
                    }
                }

            } else {
                System.out.println("No registered services");
            }
        }

        RepositoryAdmin repositoryAdmin = null;

        BundleManager bundleManager = new BundleManager(admin);
        bundleManager.listAvailableServices();
        repositoryAdmin = (RepositoryAdmin)bundleManager.getService(RepositoryAdmin.class);

        repositoryAdmin.addRepository("file:///home/artemz/.m2/repository/repository.xml");

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

        TestMsoc msoc = new TestMsoc(mcom);
        System.out.println("msoc printing all available bundles");
        for(MulticabinetBundle mcBundle : msoc.getBms().getAvailableBundles(BundleType.PAYMENTGATEWAY)){
            System.out.println("Available MC Bundle status: " + mcBundle.isDeployed().toString());
            System.out.println("Available MC Bundle filter: " + mcBundle.getFilter());
            System.out.println("Installing bundle:");
            msoc.getBms().deployBundle(mcBundle);
            System.out.println("Available MC Bundle status: " + mcBundle.isDeployed().toString());
        }
        System.out.println("Deployed payment gateways: " + msoc.getBms().getDeployedBundles(BundleType.PAYMENTGATEWAY).size());
        for (MulticabinetBundle mcBundle : msoc.getBms().getDeployedBundles(BundleType.PAYMENTGATEWAY)){
            System.out.println("Uninstalling MC Bundle.." );
            msoc.getBms().removeBundle(mcBundle);
        }
        System.out.println("Deployed payment gateways: " + msoc.getBms().getDeployedBundles(BundleType.PAYMENTGATEWAY).size());
        /*
            Discover available resources
         */
        Resolver resolver = repositoryAdmin.resolver();
        Resource[] resources = repositoryAdmin.discoverResources("(category=gateway)");
        for (int i = 0; i < resources.length; i++ ){
            System.out.println("Discovered resource: " + resources[i].getPresentationName());

            /*
                Discrover bundle categories
             */
            String[] categories = resources[i].getCategories();
            if (categories != null){
                for (int c = 0; c < categories.length; c++){
                    System.out.println("Category: " + categories[c]);
                }
            }
            /*
                Print bundle description
             */
            System.out.println("Description: " + resources[i].getProperties().get(Resource.DESCRIPTION));
            resolver.add(resources[i]);
        }
        if (resolver.resolve()){
            System.out.println("Resolved");
            //resolver.deploy(Resolver.START);
        } else {
            Reason[] reqs = resolver.getUnsatisfiedRequirements();
            for (int i = 0; i < reqs.length; i++)
            {
                /*
                    LDAP filters can be parsed with https://directory.apache.org/api/gen-docs/latest/apidocs/org/apache/directory/api/ldap/model/filter/FilterParser.html
                 */
                System.out.println("Unable to resolve: " + reqs[i].getRequirement().getFilter().toString());
            }
        }

        //System.exit(0);
    }
}
