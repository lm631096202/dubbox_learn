package wsh.timer;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration; 
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import wsh.timer.job.UpdateJobs;

@ComponentScan(basePackageClasses = App.class)
//@RestController
@EnableAutoConfiguration
//@RequestMapping("/test")
public class App {
 
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
		UpdateJobs.go(null);
		Logger log = Logger.getLogger("myTest1");
		log.info("app开启");
	}
} 