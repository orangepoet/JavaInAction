package cn.orangepoet.inaction.app.jgit;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.MergeCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author chengzhi
 * @date 2021/07/09
 */
public class Application {
    private static final String REMOTE_URL = "ssh://git@gitlab.jiawa.online:9922/once/once-devops.git";
    private static final String LOCAL_URL = "/Users/Orange/Codes/once-devops";

    public static void main(String[] args) {
        listBranches();
        //
        //createBranch();
        //
        //checkBranch();

        //merge();
    }

    private static void listBranches() {
        try {
            Map<String, Ref> remoteRefs = Git.lsRemoteRepository()
                .setHeads(true)
                .setTags(true)
                .setRemote(REMOTE_URL)
                .callAsMap();

            System.out.println("--------------------------------------------");
            remoteRefs.forEach((k, v) -> {
                System.out.println(StringUtils.replace(k, "refs/heads/", ""));
            });
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
    }

    private static void createBranch() {
        try (Repository repository = getLocalRepository()) {
            try (Git git = new Git(repository)) {
                git.branchCreate().setName("testBranch").call();
            }
        } catch (IOException | GitAPIException e) {
            e.printStackTrace();
        }
    }

    private static void checkBranch() {
        try (Repository repository = getLocalRepository()) {
            try (Git git = new Git(repository)) {
                git.checkout().setName("testBranch").call();

                CredentialsProvider credentialsProvider = new UsernamePasswordCredentialsProvider("orangepoet", "Cheng5210");
                git.pull().setCredentialsProvider(credentialsProvider).call();

                List<Ref> refs = git.branchList().call();
                for (Ref ref : refs) {
                    System.out.println(ref.getName());
                }
            }
        } catch (IOException | GitAPIException e) {
            e.printStackTrace();
        }
    }

    private static Repository getLocalRepository() throws IOException {
        return new FileRepositoryBuilder().readEnvironment().findGitDir().build();
    }

    private static void merge() {
        try (Repository repository = getLocalRepository()) {
            try (Git git = new Git(repository)) {
                git.branchCreate()
                    .setForce(true)
                    .setStartPoint("master")
                    .setName("release/release3")
                    .call();

                git.checkout().setName("release/release3").call();

                ObjectId mergeBase = repository.resolve("testBranch");

                git.merge()
                    .include(mergeBase)
                    .setCommit(true)
                    .setFastForward(MergeCommand.FastForwardMode.NO_FF)
                    .call();
            }
        } catch (IOException | GitAPIException e) {
            e.printStackTrace();
        }
    }
}
