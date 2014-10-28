package com.artemz.demo.obr;

import org.apache.felix.bundlerepository.Repository;
import org.apache.felix.bundlerepository.RepositoryAdmin;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
/**
 * Created with IntelliJ IDEA.
 * User: artemz
 * Date: 10/24/14
 * Time: 6:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class ObrManager {
    private RepositoryAdmin repositoryAdmin;
    /*
        BundleContext is the main bundle context of the framework
     */
    public ObrManager(BundleContext context){
        repositoryAdmin = getRepositoryAdmin(context);
    }
    /*
        BundleContext is the main bundle context of the framework

        This method looks up for felix bundlerepository bundle and if it's installed and started
        then it tries to retrieve RepositoryAdmin service
     */
    public static RepositoryAdmin getRepositoryAdmin(BundleContext ctx){
        ServiceReference reference = ctx.getServiceReference(RepositoryAdmin.class.getName());
        if (reference != null){
            return (RepositoryAdmin)ctx.getService(reference);
        }
        System.out.println("Reference null");
        return null;
    }
    public Repository[] listRepositories(){
        return repositoryAdmin.listRepositories();
    }
    public Repository getSystemRepository(){
        return repositoryAdmin.getSystemRepository();
    }
    public Repository getLocalRepository(){
        return repositoryAdmin.getLocalRepository();
    }
}
