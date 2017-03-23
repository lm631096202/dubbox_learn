package wsh.timer.triggerlistener;

import org.apache.commons.httpclient.NameValuePair;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.Trigger.CompletedExecutionInstruction;
import org.quartz.TriggerListener;

import util.HttpUtil;

public class MyTriggerListener implements TriggerListener{

	@Override
    public String getName() {
        return "MyTriggerListener";
    }

    /**
     * (1)
     * Trigger被激发 它关联的job即将被运行
     * Called by the Scheduler when a Trigger has fired, and it's associated JobDetail is about to be executed.
     */
    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext context) {
        System.out.println("Trigger监听器：MyTriggerListener.triggerFired()");
    }

    /**
     * (2)
     * Trigger被激发 它关联的job即将被运行,先执行(1)，在执行(2) 如果返回TRUE 那么任务job会被终止
     * Called by the Scheduler when a Trigger has fired, and it's associated JobDetail is about to be executed
     */
    @Override
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
        System.out.println("Trigger监听器：MyTriggerListener.vetoJobExecution()");
        return false;
    }

    /**
     * (3) 当Trigger错过被激发时执行,比如当前时间有很多触发器都需要执行，但是线程池中的有效线程都在工作，
     *  那么有的触发器就有可能超时，错过这一轮的触发。
     * Called by the Scheduler when a Trigger has misfired.
     */
    @Override
    public void triggerMisfired(Trigger trigger) {
        System.out.println("Trigger监听器：MyTriggerListener.triggerMisfired()");
    }

    /**
     * (4) 任务完成时触发
     * Called by the Scheduler when a Trigger has fired, it's associated JobDetail has been executed
     * and it's triggered(xx) method has been called.
     */
    @Override
    public void triggerComplete(Trigger trigger, JobExecutionContext context,
            CompletedExecutionInstruction triggerInstructionCode) {
        System.out.println("Trigger监听器：MyTriggerListener.triggerComplete()"); 
        
		JobDetail jobDetail=context.getJobDetail();
		JobDataMap data_map =jobDetail.getJobDataMap();
		HttpUtil.showObj(data_map);
		String Url=data_map.getString("url")+"/JobApi/end";
		NameValuePair[] data={
				new NameValuePair("job_id",String.valueOf(data_map.getLong("job_id")))
		};
		String json_str=HttpUtil.getFromHttp(Url,data);
		System.out.println(json_str);
		
    }

}
