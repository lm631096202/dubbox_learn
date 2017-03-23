package wsh.timer.trigger;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.*; 
import org.quartz.impl.StdSchedulerFactory;

import util.HttpUtil;

public class SimpleTriggerConfig {
	private static SchedulerFactory gSchedulerFactory = new StdSchedulerFactory();
	private long currentTimeMillis=System.currentTimeMillis();//当前时间戳
	 private String trigger_name="trigger_name"+Long.toString(currentTimeMillis);
	 private String trigger_group="trigger_group"; 
	 private int with_interval_in_seconds=0;// 重复间隔
	 private int with_repeat_rount=0;// 重复次数
	 private Boolean repeat_forever=false;// 一直重复
	 private Boolean start_now=false;//立刻执行 
	 //private Date now = new Date();//当前时间
	 private String start_time=null;// = DateFormat.getDateTimeInstance().format(now);//DateFormat.getDateTimeInstance() 格式化时间后为：2008-6-16 20:54:53
	 private String end_time=null;
	 private Trigger trigger=null;
	 TriggerKey triggerKey=new TriggerKey(trigger_name, trigger_group);
	public Trigger getTrigger() {
		Scheduler sched = null;
		try {
			sched = gSchedulerFactory.getScheduler();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		try {
			
			if(sched.checkExists(triggerKey)){
				return sched.getTrigger(triggerKey);
			}
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TriggerBuilder builder=TriggerBuilder
        .newTrigger()
        .withIdentity(trigger_name,trigger_group);
		
		setStartAt(builder);//设置开始时间 
		
		SimpleScheduleBuilder simpleScheduleBuilder=SimpleScheduleBuilder.simpleSchedule();
		
		setWithInterval(simpleScheduleBuilder);
		setWithRepeatCount(simpleScheduleBuilder);
		setEndAt(builder); 
		builder.withSchedule(simpleScheduleBuilder);
		trigger=builder.build();
		return trigger;
	}
	//注意，开始时间要在离现在一分钟以后
	private void setStartAt(TriggerBuilder builder){
		if(start_now){
			builder.startNow();
		}else if(start_time!=null&&start_time!="null"&&!start_time.isEmpty()){//开始时间
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");  
		    Date date;
		    Date now=new Date();
		    now.setTime(new Date().getTime() + 60*1000); 
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
		 if(end_time!=null&&end_time!="null"&&!end_time.isEmpty()){//开始时间
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");  
		    Date date;
		    Date now=new Date();
		    now.setTime(new Date().getTime()+ 60*1000); 
			try {
				date = sdf.parse(end_time);
				if(date.after(now)){
			    	builder.endAt(date);
			    }
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{ 
		    Date now=new Date();
		    now.setTime(new Date().getTime()+ 24*60*60*1000); 
		    builder.endAt(now);
		}
	}
	//注意，开始时间要在里现在一分钟以后
	private void setWithInterval(SimpleScheduleBuilder simpleScheduleBuilder){
		if(with_interval_in_seconds>0){ 
			simpleScheduleBuilder.withIntervalInSeconds(with_interval_in_seconds);//每隔多少秒执行一次
		}
	}
	//注意，开始时间要在里现在一分钟以后
	private void setWithRepeatCount(SimpleScheduleBuilder simpleScheduleBuilder){
		if(repeat_forever){
			simpleScheduleBuilder.repeatForever();//一直重复
		}else if(with_repeat_rount>0){ 
			simpleScheduleBuilder.withRepeatCount(with_repeat_rount);//执行多少次
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
	public int getWith_interval_in_seconds() {
		return with_interval_in_seconds;
	}
	public void setWith_interval_in_seconds(int with_interval_in_seconds) {
		this.with_interval_in_seconds = with_interval_in_seconds;
	}
	public int getWith_repeat_rount() {
		return with_repeat_rount;
	}
	public void setWith_repeat_rount(int with_repeat_rount) {
		this.with_repeat_rount = with_repeat_rount;
	}
	public Boolean getRepeat_forever() {
		return repeat_forever;
	}
	public void setRepeat_forever(Boolean repeat_forever) {
		this.repeat_forever = repeat_forever;
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
	 
	 
	 
}
