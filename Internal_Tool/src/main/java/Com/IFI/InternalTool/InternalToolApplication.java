package Com.IFI.InternalTool;

import java.util.concurrent.Executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import it.ozimov.springboot.mail.configuration.EnableEmailTools;

@SpringBootApplication
@EnableEmailTools
@EnableAsync
/*@EnableCaching*/
@EntityScan(basePackageClasses = {
		InternalToolApplication.class,
		Jsr310JpaConverters.class
})

public class InternalToolApplication extends AsyncConfigurerSupport  {	
	public static void main(String[] args) {
		SpringApplication.run(InternalToolApplication.class, args);
	}
	
	//sent email thread
	@Override
	  public Executor getAsyncExecutor() {
	    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
	    executor.setCorePoolSize(5);
	    executor.setMaxPoolSize(5);
	    executor.setQueueCapacity(500);
	    executor.setThreadNamePrefix("--email-sent-");
	    executor.initialize();
	    try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	    return executor;
	  }
	
}
