package wsh.timer.job; 
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator; 
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject; 

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.httpclient.NameValuePair;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import util.HttpUtil;
import wsh.timer.QuartzManager;
import wsh.timer.trigger.CronTriggerConfig;
import wsh.timer.trigger.SimpleTriggerConfig;

public class UpdateJobs implements Job {

	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		//JobDetail jobDetail=context.getJobDetail();
		//JobDataMap data_map =jobDetail.getJobDataMap();
		String Url=QuartzManager.fUrl;
		NameValuePair[] data={//提交短信 
				//new NameValuePair("job_id", "1")
		};
		
		String json_str=HttpUtil.getFromHttp(Url,data);
		if(json_str.isEmpty()){
			System.out.println(json_str+"ssss");
			return ;
		}
		JSONArray json = JSONArray.fromObject(json_str); 
		update_jobs(json);
		 
	}
	public void  update_jobs(JSONArray json){
		 Iterator it = json.iterator();  
         while (it.hasNext()) {   
             JSONObject job_json = (JSONObject) it.next();
             System.out.println(job_json.opt("corn").getClass());
             int timer_type=1;
             try {
            	 timer_type=job_json.getInt("timer_type");
			} catch (Exception e) {
				// TODO: handle exception
			}
             
             String sync_type=job_json.getString("sync_type");
             System.out.println(sync_type+(sync_type.equals("addJob"))+(sync_type.equals("del")));
             if(timer_type==1){
            	 if(sync_type.equals("addJob")){
            		 addSimpleTrigger(job_json);
            	 }else if(sync_type.equals("del")){
            		 removeSimpleTrigger(job_json);
            	 }else{
            		 modifySimpleTrigger(job_json);
            	 }
            	 
             }else if(timer_type==2){
            	 if(sync_type.equals("addJob")){
            		 addCronTrigger(job_json);
            	 }else if(sync_type.equals("del")){
            		 removeCronTrigger(job_json);
            	 }else{
            		 modifyCronTrigger(job_json);
            	 }
            	 
             }else{
            	 if(sync_type.equals("addJob")){
            		 addSimpleTrigger(job_json);
            	 }else if(sync_type.equals("del")){
            		 removeSimpleTrigger(job_json);
            	 }else{
            		 modifySimpleTrigger(job_json);
            	 }
             }
             
            
         } 
	}
	public static void go(String[] args) {
		try {
			new UpdateJobs().execute(null);
		} catch (JobExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static void addSimpleTrigger(JSONObject job_json){
		 JobDetailConfig jobDetailConfig=new JobDetailConfig();
		 SimpleTriggerConfig TriggerConfig = new SimpleTriggerConfig();
         try {
			BeanUtils.copyProperties(TriggerConfig, job_json);
			
			BeanUtils.copyProperties(jobDetailConfig, job_json); 
			
			TriggerConfig.setTrigger_name(Long.toString(jobDetailConfig.getJob_id()));
			jobDetailConfig.setJobName(Long.toString(jobDetailConfig.getJob_id()));
			QuartzManager.addJob(jobDetailConfig.getJob(), TriggerConfig.getTrigger());
			HttpUtil.showObj(TriggerConfig);
			HttpUtil.showObj(jobDetailConfig);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void addCronTrigger(JSONObject job_json){
		 JobDetailConfig jobDetailConfig=new JobDetailConfig();
		 CronTriggerConfig TriggerConfig = new CronTriggerConfig();
        try {
			BeanUtils.copyProperties(TriggerConfig, job_json);
			TriggerConfig.setCron(job_json.opt("corn").toString());
			BeanUtils.copyProperties(jobDetailConfig, job_json); 
			TriggerConfig.setTrigger_name(Long.toString(jobDetailConfig.getJob_id()));
			jobDetailConfig.setJobName(Long.toString(jobDetailConfig.getJob_id()));
			QuartzManager.addJob(jobDetailConfig.getJob(), TriggerConfig.getTrigger());
			HttpUtil.showObj(TriggerConfig);
			HttpUtil.showObj(jobDetailConfig);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void removeSimpleTrigger(JSONObject job_json){
		 JobDetailConfig jobDetailConfig=new JobDetailConfig();
		 SimpleTriggerConfig TriggerConfig = new SimpleTriggerConfig();
        try {
			BeanUtils.copyProperties(TriggerConfig, job_json);
			
			BeanUtils.copyProperties(jobDetailConfig, job_json); 
			TriggerConfig.setTrigger_name(Long.toString(jobDetailConfig.getJob_id()));
			jobDetailConfig.setJobName(Long.toString(jobDetailConfig.getJob_id()));
			QuartzManager.removeJob(jobDetailConfig.getJob(), TriggerConfig.getTrigger());
			String Url=QuartzManager.fUrl+"/JobApi/end";
    		NameValuePair[] data={//提交短信 
    				new NameValuePair("job_id",String.valueOf(jobDetailConfig.getJob_id()))
    		};
    		String json_str=HttpUtil.getFromHttp(Url,data); 
			HttpUtil.showObj(TriggerConfig);
			HttpUtil.showObj(jobDetailConfig);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void removeCronTrigger(JSONObject job_json){
		 JobDetailConfig jobDetailConfig=new JobDetailConfig();
		 CronTriggerConfig TriggerConfig = new CronTriggerConfig();
       try {
			BeanUtils.copyProperties(TriggerConfig, job_json);
			jobDetailConfig.setJobName(Long.toString(jobDetailConfig.getJob_id()));
			BeanUtils.copyProperties(jobDetailConfig, job_json); 
			TriggerConfig.setTrigger_name(Long.toString(jobDetailConfig.getJob_id()));
			
			QuartzManager.removeJob(jobDetailConfig.getJob(), TriggerConfig.getTrigger()); 
			
    		String Url=QuartzManager.fUrl+"/JobApi/end";
    		NameValuePair[] data={//提交短信 
    				new NameValuePair("job_id",String.valueOf(jobDetailConfig.getJob_id()))
    		};
    		String json_str=HttpUtil.getFromHttp(Url,data); 
    		
			HttpUtil.showObj(TriggerConfig);
			HttpUtil.showObj(jobDetailConfig);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void modifySimpleTrigger(JSONObject job_json){
		 JobDetailConfig jobDetailConfig=new JobDetailConfig();
		 SimpleTriggerConfig TriggerConfig = new SimpleTriggerConfig();
       try {
			BeanUtils.copyProperties(TriggerConfig, job_json); 
			BeanUtils.copyProperties(jobDetailConfig, job_json); 
			
			jobDetailConfig.setJobName(Long.toString(jobDetailConfig.getJob_id()));
			TriggerConfig.setTrigger_name(Long.toString(jobDetailConfig.getJob_id()));
			
			QuartzManager.modifyJobTime(jobDetailConfig.getJob(), TriggerConfig.getTrigger());
			
			String Url=QuartzManager.fUrl+"/JobApi/end";
    		NameValuePair[] data={//提交短信 
    				new NameValuePair("job_id",String.valueOf(jobDetailConfig.getJob_id()))
    		};
    		String json_str=HttpUtil.getFromHttp(Url,data); 
    		
			HttpUtil.showObj(TriggerConfig);
			HttpUtil.showObj(jobDetailConfig);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void modifyCronTrigger(JSONObject job_json){
		 JobDetailConfig jobDetailConfig=new JobDetailConfig();
		 CronTriggerConfig TriggerConfig = new CronTriggerConfig();
      try {
			BeanUtils.copyProperties(TriggerConfig, job_json);
			
			BeanUtils.copyProperties(jobDetailConfig, job_json); 
			TriggerConfig.setTrigger_name(Long.toString(jobDetailConfig.getJob_id()));
			jobDetailConfig.setJobName(Long.toString(jobDetailConfig.getJob_id()));
			QuartzManager.modifyJobTime(jobDetailConfig.getJob(), TriggerConfig.getTrigger());
			HttpUtil.showObj(TriggerConfig);
			HttpUtil.showObj(jobDetailConfig);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
