package co.com.ceiba.cds.drools.model;

import java.io.File;

public class Git {

    private String remoteUrl;
    private String localRepositoryURI;
    private String userName;
    private String password;
    private File localRepository;

    public Git(String remoteUrl, String localRepositoryURI, String userName, String password){
        this.remoteUrl = remoteUrl;
        this.localRepositoryURI = localRepositoryURI;
        this.localRepository =  new File(localRepositoryURI);
        this.userName = userName;
        this.password = password;
    }



    public String getRemoteUrl() {
        return remoteUrl;
    }

    public void setRemoteUrl(String remoteUrl) {
        this.remoteUrl = remoteUrl;
    }

    public String getLocalRepositoryURI() {
        return localRepositoryURI;
    }

    public void setLocalRepositoryURI(String localRepositoryURI) {
        this.localRepositoryURI = localRepositoryURI;
        localRepository = new File(localRepositoryURI);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public File getLocalRepository() {
        return localRepository;
    }
}
