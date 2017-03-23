package wsh.timer.job;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONObject;
 
import org.apache.commons.httpclient.NameValuePair;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import util.HttpUtil;

public class SimpleJob implements Job {

	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		JobDetail jobDetail=context.getJobDetail();
		JobDataMap data_map =jobDetail.getJobDataMap();
		HttpUtil.showObj(data_map);
		String Url=data_map.getString("url")+"/"+data_map.getString("controller_name")+"/"+data_map.getString("method_name");
		NameValuePair[] data={//提交短信 
				new NameValuePair("job_id",String.valueOf(data_map.getLong("job_id")))
		};
		String json_str=HttpUtil.getFromHttp(Url,data);

		Logger execute = Logger.getLogger("execute");
		execute.info("execute");
		execute.info(json_str);
		
		System.out.println(Url);
		System.out.println(json_str);
	}
	 

}
