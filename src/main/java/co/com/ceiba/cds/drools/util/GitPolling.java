package co.com.ceiba.cds.drools.util;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullCommand;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.IOException;
import java.util.Date;

public class GitPolling {

    public static void cloneRepo(co.com.ceiba.cds.drools.model.Git gitDTO) throws IOException, GitAPIException {
        FileUtils.cleanDirectory(gitDTO.getLocalRepository());
        CloneCommand gitCC = Git.cloneRepository();
        Git git = gitCC.setURI(gitDTO.getRemoteUrl())
                    .setDirectory(gitDTO.getLocalRepository())
                        .setCredentialsProvider(
                            new UsernamePasswordCredentialsProvider(gitDTO.getUserName(),gitDTO.getPassword()))
                .call();
    }

    public static Long pullRepo(co.com.ceiba.cds.drools.model.Git gitDTO) throws IOException, GitAPIException {
        PullCommand cloneCommand = Git.open(gitDTO.getLocalRepository()).pull();
        cloneCommand.setCredentialsProvider( new UsernamePasswordCredentialsProvider(gitDTO.getUserName(),gitDTO.getPassword()) );
        PullResult pr = cloneCommand.call();
        System.out.println("Last modification: "+ new Date(gitDTO.getLocalRepository().lastModified()));
        System.out.println("Pull success: "+pr.isSuccessful());
        return gitDTO.getLocalRepository().lastModified();
    }
}
