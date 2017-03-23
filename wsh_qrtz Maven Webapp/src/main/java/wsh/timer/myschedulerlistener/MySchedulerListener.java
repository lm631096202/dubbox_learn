package wsh.timer.myschedulerlistener; 
import org.apache.commons.httpclient.NameValuePair;
import org.apache.log4j.Logger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SchedulerListener;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;

import util.HttpUtil;

public class MySchedulerListener implements SchedulerListener{
	private static SchedulerFactory gSchedulerFactory = new StdSchedulerFactory();
	@Override
	public void jobAdded(JobDetail arg0) {
	System.out.println("SchedulerListener监听器：MySchedulerListener.jobAdded()");	
	}

	@Override
	public void jobDeleted(JobKey arg0) {
		System.out.println("SchedulerListener监听器：MySchedulerListener.jobDeleted()");	
	}

	@Override
	public void jobPaused(JobKey arg0) {
		System.out.println("SchedulerListener监听器：MySchedulerListener.jobPaused()");	
	}

	@Override
	public void jobResumed(JobKey arg0) {
		System.out.println("SchedulerListener监听器：MySchedulerListener.jobResumed()");	
	}

	@Override
	public void jobScheduled(Trigger arg0) {
		System.out.println("SchedulerListener监听器：MySchedulerListener.jobScheduled()");	
	}

	@Override
	public void jobUnscheduled(TriggerKey arg0) {
		System.out.println("SchedulerListener监听器：MySchedulerListener.jobUnscheduled()");	
	}

	@Override
	public void jobsPaused(String arg0) {
		System.out.println("SchedulerListener监听器：MySchedulerListener.jobsPaused()");	
	}

	@Override
	public void jobsResumed(String arg0) {
		System.out.println("SchedulerListener监听器：MySchedulerListener.jobsResumed()");	
	}

	@Override
	public void schedulerError(String arg0, SchedulerException arg1) {
		System.out.println("SchedulerListener监听器：MySchedulerListener.schedulerError()");	
	}

	@Override
	public void schedulerInStandbyMode() {
		System.out.println("SchedulerListener监听器：MySchedulerListener.schedulerInStandbyMode()");	
	}

	@Override
	public void schedulerShutdown() {
		System.out.println("SchedulerListener监听器：MySchedulerListener.schedulerShutdown()");	
	}

	@Override
	public void schedulerShuttingdown() {
		System.out.println("SchedulerListener监听器：MySchedulerListener.schedulerShuttingdown()");	
	}

	@Override
	public void schedulerStarted() {
		System.out.println("SchedulerListener监听器：MySchedulerListener.schedulerStarted()");	
	}

	@Override
	public void schedulerStarting() {
		System.out.println("SchedulerListener监听器：MySchedulerListener.schedulerStarting()");	
	}

	@Override
	public void schedulingDataCleared() {
		System.out.println("SchedulerListener监听器：MySchedulerListener.schedulingDataCleared()");	
	}

	@Override
	public void triggerFinalized(Trigger trigger) {
		System.out.println("SchedulerListener监听器：MySchedulerListener.triggerFinalized()");	 
		Scheduler sched=null;
		try {
			sched= gSchedulerFactory.getScheduler();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(trigger.getJobKey());
		JobDetail jobDetail = null;
		try {
			jobDetail=sched.getJobDetail(trigger.getJobKey());
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JobDataMap data_map =jobDetail.getJobDataMap();
		HttpUtil.showObj(data_map);
		String Url=data_map.getString("url")+"/JobApi/end";
		NameValuePair[] data={//提交短信 
				new NameValuePair("job_id",String.valueOf(data_map.getLong("job_id")))
		};
		String json_str=HttpUtil.getFromHttp(Url,data); 
		Logger end = Logger.getLogger("end");
		end.info("end");
		end.info(json_str);
		System.out.println(json_str);
	}

	@Override
	public void triggerPaused(TriggerKey arg0) {
		System.out.println("SchedulerListener监听器：MySchedulerListener.triggerPaused()");	
	}



	@Override
	public void triggersPaused(String arg0) {
		System.out.println("SchedulerListener监听器：MySchedulerListener.triggersPaused()");	
	}

	@Override
	public void triggersResumed(String arg0) {
		System.out.println("SchedulerListener监听器：MySchedulerListener.triggersResumed()");	
	}

	@Override
	public void triggerResumed(TriggerKey arg0) {
		System.out.println("SchedulerListener监听器：MySchedulerListener.triggerResumed()");	
		
	}

}
