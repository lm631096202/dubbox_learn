package wsh.timer.trigger;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.*; 

public class CronTriggerConfig {
	private long currentTimeMillis=System.currentTimeMillis();//当前时间戳
	 private String trigger_name="trigger_name"+Long.toString(currentTimeMillis);
	 private String trigger_group="trigger_group";
	 private String cron=null;
	 private String job_corn=null;
	 private Boolean start_now=false;//立刻执行 
	 //private Date now = new Date();//当前时间
	 private String start_time=null;// = DateFormat.getDateTimeInstance().format(now);//DateFormat.getDateTimeInstance() 格式化时间后为：2008-6-16 20:54:53
	 private String end_time=null;
	 private Trigger trigger=null;
	  
	public Trigger getTrigger() {
		TriggerBuilder builder=TriggerBuilder
        .newTrigger()
        .withIdentity(trigger_name,trigger_group);
		
		setStartAt(builder);//设置开始时间 
		System.out.println(cron);
		System.out.println(job_corn);
		CronScheduleBuilder cronScheduleBuilder=null;
		if(job_corn!=null&&job_corn!="null"&&!job_corn.isEmpty()){
			 cronScheduleBuilder=CronScheduleBuilder.cronSchedule(job_corn);
		}else{
			 cronScheduleBuilder=CronScheduleBuilder.cronSchedule(cron);
		}
		
		builder.withSchedule(cronScheduleBuilder);
		setEndAt(builder); 
		trigger=builder.build();
		return trigger;
	}
	//注意，开始时间要在离现在一分钟以后
	private void setStartAt(TriggerBuilder builder){
		if(start_now){
			builder.startNow();
		}else if(start_time!=null&&start_time!="null"){//开始时间
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");  
		    Date date;
		    Date now=null;
		    now.setTime(new Date().getTime() + 10*1000); 
			try {
				date = sdf.parse(start_time);
				if(date.after(now)){
			    	builder.startAt(date);
			    }
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	//注意，开始时间要在离现在一分钟以后
	private void setEndAt(TriggerBuilder builder){
		 if(end_time!=null&&end_time!="null"){//开始时间
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");  
		    Date date;
		    Date now=null;
		    now.setTime(new Date().getTime() + 60*1000); 
			try {
				date = sdf.parse(end_time);
				if(date.after(now)){
			    	builder.endAt(date);
			    }
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	 
	public void setTrigger(Trigger trigger) {
		this.trigger = trigger;
	}
	public String getTrigger_name() {
		return trigger_name;
	}
	public void setTrigger_name(String trigger_name) {
		this.trigger_name = trigger_name;
	}
	public String getTrigger_group() {
		return trigger_group;
	}
	public void setTrigger_group(String trigger_group) {
		this.trigger_group = trigger_group;
	}
	public String getCron() {
		return cron;
	}
	public void setCron(String cron) {
		this.cron = cron;
	}
	public Boolean getStart_now() {
		return start_now;
	}
	public void setStart_now(Boolean start_now) {
		this.start_now = start_now;
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
	public String getJob_corn() {
		return job_corn;
	}
	public void setJob_corn(String job_corn) {
		this.job_corn = job_corn;
	} 
	 
	 
	 
}
