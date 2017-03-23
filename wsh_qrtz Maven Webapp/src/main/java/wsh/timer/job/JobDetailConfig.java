package wsh.timer.job;

import java.util.Map;

import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;

public class JobDetailConfig {
	private static SchedulerFactory gSchedulerFactory = new StdSchedulerFactory();
	private Long currentTimeMillis=System.currentTimeMillis();//当前时间戳
	
	private String jobName="jobName"+Long.toString(currentTimeMillis);
	private String jobGroup="jobGroup";
	private Long job_id;
	private String type;
	private String addtime;
	private String  url;
	private String  function_name;
	private Boolean is_del;
	private Boolean running;
	private String start_time;
	private String end_time;
	private String false_function;
	private String name;
	private Boolean res;
	private String deltime;
	private String controller_name;
	private Long trigger_id;
	private Long server_id;
	private String method_name;
	private String model;
	public JobKey jobKey=new JobKey(jobName, jobGroup);
	
	public JobDetail getJob(){
		Scheduler sched = null;
		try {
			sched = gSchedulerFactory.getScheduler();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try { 
			if(sched.checkExists(jobKey)){
				return sched.getJobDetail(jobKey);
			}
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JobDetail job = JobBuilder.newJob(SimpleJob.class)
			      .withIdentity(jobName, jobGroup) // name "myJob", group "group1"
			      .usingJobData("currentTimeMillis", currentTimeMillis)
			      .usingJobData("jobName", jobName)
			      .usingJobData("job_id", job_id)
			      .usingJobData("jobGroup", jobGroup)
			      .usingJobData("type", type)
			      .usingJobData("addtime", addtime)
			      .usingJobData("url", url)
			      .usingJobData("function_name", function_name)
			      .usingJobData("is_del", is_del)
			      .usingJobData("running", running)
			      .usingJobData("start_time", start_time)
			      .usingJobData("end_time", end_time)
			      .usingJobData("false_function", false_function)
			      .usingJobData("name", name)
			      .usingJobData("res", res)
			      .usingJobData("model", model)
			      .usingJobData("deltime", deltime)
			      .usingJobData("controller_name", controller_name)
			      .usingJobData("trigger_id", trigger_id)
			      .usingJobData("server_id", server_id)
			      .usingJobData("method_name", method_name) 
			      .build();
		return job;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getJobGroup() {
		return jobGroup;
	}
	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}
	 
	public Long getJob_id() {
		return job_id;
	}
	public void setJob_id(Long job_id) {
		this.job_id = job_id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getFunction_name() {
		return function_name;
	}
	public void setFunction_name(String function_name) {
		this.function_name = function_name;
	}
	public Boolean getIs_del() {
		return is_del;
	}
	public void setIs_del(Boolean is_del) {
		this.is_del = is_del;
	}
	public Boolean getRunning() {
		return running;
	}
	public void setRunning(Boolean running) {
		this.running = running;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public String getFalse_function() {
		return false_function;
	}
	public void setFalse_function(String false_function) {
		this.false_function = false_function;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getRes() {
		return res;
	}
	public void setRes(Boolean res) {
		this.res = res;
	}
	public String getDeltime() {
		return deltime;
	}
	public void setDeltime(String deltime) {
		this.deltime = deltime;
	}
	public String getController_name() {
		return controller_name;
	}
	public void setController_name(String controller_name) {
		this.controller_name = controller_name;
	}
	public Long getTrigger_id() {
		return trigger_id;
	}
	public void setTrigger_id(Long trigger_id) {
		this.trigger_id = trigger_id;
	}
	public Long getServer_id() {
		return server_id;
	}
	public void setServer_id(Long server_id) {
		this.server_id = server_id;
	}
	public String getMethod_name() {
		return method_name;
	}
	public void setMethod_name(String method_name) {
		this.method_name = method_name;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	

}
