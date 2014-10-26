package com.artemz.demo;

import org.apache.felix.bundlerepository.RepositoryAdmin;

/**
 * Created with IntelliJ IDEA.
 * User: artemz
 * Date: 10/26/14
 * Time: 10:24 AM
 * To change this template use File | Settings | File Templates.
 */
public class RepositoryAdminHolder {
    private static RepositoryAdmin repositoryAdmin;
    public static void setRepositoryAdmin(RepositoryAdmin admin) {
        repositoryAdmin = admin;
    }
    public static RepositoryAdmin getRepositoryAdmin(){
        return repositoryAdmin;
    }
}
