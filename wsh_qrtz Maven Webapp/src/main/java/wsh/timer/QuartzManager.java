package wsh.timer; 
import org.apache.commons.httpclient.NameValuePair;
import org.apache.log4j.Logger;
import org.quartz.CronTrigger;  
import org.quartz.JobDataMap;
import org.quartz.JobDetail;  
import org.quartz.Scheduler;  
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;  
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;  
import org.quartz.impl.matchers.KeyMatcher;

import util.HttpUtil;
import wsh.timer.job.JobDetailConfig;
import wsh.timer.myschedulerlistener.MySchedulerListener;
import wsh.timer.trigger.SimpleTriggerConfig;
import wsh.timer.triggerlistener.MyTriggerListener;
  
/** 
 * @Description: 定时任务管理类 
 *  
 * @ClassName: QuartzManager 
 * @Copyright: Copyright (c) 2014 
 *  
 * @author Comsys-LZP 
 * @date 2014-6-26 下午03:15:52 
 * @version V2.0 
 */  
public class QuartzManager {  
	private static SchedulerFactory gSchedulerFactory = new StdSchedulerFactory();
    public static String fUrl="http://w.lxy716.com/home/JobApi/getModifyJobs";//刷新列表//url/job_id/1
    static {
    	Scheduler sched;
		try {
			sched = gSchedulerFactory.getScheduler();
			MySchedulerListener schedulerListener = new MySchedulerListener();         
			sched.getListenerManager().addSchedulerListener(schedulerListener);
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    } 
    public static void addJob(JobDetail jobDetail,Trigger trigger) {
        try {  
        	
            Scheduler sched = gSchedulerFactory.getScheduler();
            System.out.println(jobDetail.getKey());
            System.out.println(sched.checkExists(jobDetail.getKey()));
            if(sched.checkExists(jobDetail.getKey())||sched.checkExists(trigger.getKey())){
            	// 启动  
                if (!sched.isShutdown()) {  
                    sched.start();  
                }  
            	return ;
            }
         // 使用自己的监听器
            MyTriggerListener triggerListener = new MyTriggerListener();
            sched.getListenerManager().addTriggerListener(triggerListener,KeyMatcher.keyEquals(trigger.getKey()));
            

            sched.scheduleJob(jobDetail, trigger);  
         // 启动  
            if (!sched.isShutdown()) {  
                sched.start();  
            }   
         
            
    		JobDataMap data_map =jobDetail.getJobDataMap();
    		
            String runningUrl=data_map.getString("url")+"/JobApi/running";
    		NameValuePair[] enddata={
    				new NameValuePair("job_id",String.valueOf(data_map.getLong("job_id")))
    		};
    		String enddata_str=HttpUtil.getFromHttp(runningUrl,enddata);
    		System.out.println(enddata_str);
    		Logger running = Logger.getLogger("running");
    		running.info("running");
    		running.info(enddata_str);
        } catch (Exception e) {  
        	Logger stdlog = Logger.getLogger("stdlog");
        	stdlog.info("stdlog");
        	stdlog.debug(e);
            //throw new RuntimeException(e);  
        }
    }  
  
     
    /** 
     * @Description: 修改一个任务的触发时间(使用默认的任务组名，触发器名，触发器组名) 
     *  
     * @param jobName 
     * @param time 
     *  
     * @Title: QuartzManager.java 
     * @Copyright: Copyright (c) 2014 
     *  
     * @author Comsys-LZP 
     * @date 2014-6-26 下午03:49:21 
     * @version V2.0 
     */  
      
    public static void modifyJobTime(JobDetail jobDetail,Trigger trigger) {  
        try {   
            removeJob( jobDetail,trigger);
            addJob(jobDetail, trigger); 
              
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }  
    }  
   
      
    public static void removeJob(JobDetail jobDetail,Trigger trigger) {  
        try {  
            Scheduler sched = gSchedulerFactory.getScheduler();  
            sched.pauseTrigger(trigger.getKey());// 停止触发器  
            sched.unscheduleJob(trigger.getKey());// 移除触发器  
            sched.deleteJob(jobDetail.getKey());// 删除任务  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }  
    }  
  
  
    /** 
     * @Description:启动所有定时任务 
     *  
     *  
     * @Title: QuartzManager.java 
     * @Copyright: Copyright (c) 2014 
     *  
     * @author Comsys-LZP 
     * @date 2014-6-26 下午03:50:18 
     * @version V2.0 
     */  
    public static void startJobs() {  
        try {  
            Scheduler sched = gSchedulerFactory.getScheduler();  
            sched.start();  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }  
    }  
  
    /** 
     * @Description:关闭所有定时任务 
     *  
     *  
     * @Title: QuartzManager.java 
     * @Copyright: Copyright (c) 2014 
     *  
     * @author Comsys-LZP 
     * @date 2014-6-26 下午03:50:26 
     * @version V2.0 
     */  
    public static void shutdownJobs() {  
        try {  
            Scheduler sched = gSchedulerFactory.getScheduler();  
            if (!sched.isShutdown()) {  
                sched.shutdown();  
            }  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }  
    }   
}  