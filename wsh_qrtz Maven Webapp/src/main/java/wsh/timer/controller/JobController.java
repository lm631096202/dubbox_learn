package wsh.timer.controller;   
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap; 
import javax.servlet.http.HttpServletRequest; 
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.RestController; 
import util.HttpUtil;  
import wsh.timer.job.SimpleJob;
import wsh.timer.job.UpdateJobs;
@RestController 
public class JobController { 
	@RequestMapping("/test")
	public String test(HttpServletRequest request) {
		Map<String, String[]> map=request.getParameterMap();
		  HttpUtil.showObj(request.getParameterMap());
		  return "1";
	}
	@RequestMapping("/update")
	public String append(HttpServletRequest request) { 
		UpdateJobs.go(null);
		  return "1";
	}
}
