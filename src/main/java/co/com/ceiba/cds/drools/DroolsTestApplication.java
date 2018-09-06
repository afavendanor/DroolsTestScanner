package co.com.ceiba.cds.drools;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import co.com.ceiba.cds.drools.model.Git;
import co.com.ceiba.cds.drools.util.GitPolling;

@SpringBootApplication
@EnableScheduling
@ApplicationScoped
public class DroolsTestApplication {


	public static void main(String[] args) {
		SpringApplication.run(DroolsTestApplication.class, args);
	}

	@Bean
	public KieContainer kieContainer() {
		
		 String groupId = "co.com.ceiba";
		 String artifactId = "cds_prueba_kjar";
		 String version = "[0.0,)";
		
		String userDir = System.getProperty("user.dir", "."); System.setProperty("kie.maven.settings.custom", userDir + File.separatorChar + "kie-settings.xml");
		
		KieServices kieServices = KieServices.Factory.get();
				
		ReleaseId releaseId = kieServices.newReleaseId(groupId, artifactId, version);
		
		KieContainer kieContainer = kieServices.newKieContainer(releaseId);

		KieScanner kieScanner = kieServices.newKieScanner(kieContainer);
		kieScanner.start(1000L);

		return kieContainer;

	}
	
	@Scheduled(fixedRate = 1000)
	public void runTask() throws IOException, GitAPIException, InterruptedException, ExecutionException {
		boolean firstTime = false;
		final Git gitDTO = new Git("https://github.com/afavendanor/drools.git", "repository", "afavendanor",
				"4v3nd4n0");
		if (firstTime) {
			GitPolling.cloneRepo(gitDTO);
		} else {
			ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
			final AtomicInteger count = new AtomicInteger(0);
			@SuppressWarnings("rawtypes")
			ScheduledFuture future = executor.scheduleAtFixedRate(new Runnable() {

				@Override
				public void run() {
					try {
						System.out.println("pull # " + count.addAndGet(1));
						GitPolling.pullRepo(gitDTO);
					} catch (IOException e) {
						e.printStackTrace();
					} catch (GitAPIException e) {
						e.printStackTrace();
					}
				}
			}, 5, 10, TimeUnit.SECONDS);

			System.out.println(future.get());
			executor.shutdown();
		}

	}
	
}