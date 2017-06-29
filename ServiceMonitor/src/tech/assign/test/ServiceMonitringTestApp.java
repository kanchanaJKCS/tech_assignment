package tech.assign.test;

import java.util.Calendar;

import tech.assign.impl.Service;
import tech.assign.impl.ServiceMonitorCaller;

/**
 * Test App for service monitoring
 * @author kanchana_jkcs
 *
 */
public class ServiceMonitringTestApp {
	
	public static void main(String[] args) {
		
		//service1 without outage period
		Service service1 = new Service("172.22.118.174", 8080);
		
		//caller1 and caller2 use service1 with different polling frequencies and grace time
		ServiceMonitorCaller caller1 = new ServiceMonitorCaller();
		caller1.setService(service1);
		caller1.setCallerName("caller1");
		//set frequency in seconds
		caller1.setPollingFrequency(60);
		//set grace time in seconds
		caller1.setGraceTime(15);
		
		ServiceMonitorCaller caller2 = new ServiceMonitorCaller();
		caller2.setService(service1);
		caller2.setCallerName("caller2");
		//set frequency in seconds
		caller2.setPollingFrequency(120);
		//set frequency in seconds
		caller2.setGraceTime(15);
		
		//service2 with outage period
		Service service2 = new Service("172.26.132.200", 8081);
		Calendar outageStart= Calendar.getInstance();
		outageStart.set(Calendar.DATE, 28);
		outageStart.set(Calendar.HOUR, 3);
		outageStart.set(Calendar.MINUTE, 30);
		outageStart.set(Calendar.MINUTE, 0);
		
		Calendar outageEnd= Calendar.getInstance();
		outageEnd.set(Calendar.DATE, 28);
		outageEnd.set(Calendar.HOUR, 4);
		outageEnd.set(Calendar.MINUTE, 0);
		outageEnd.set(Calendar.MINUTE, 0);
		
		service2.setOutageStart(outageStart.getTime());
		service2.setOutageEnd(outageEnd.getTime());
		
		ServiceMonitorCaller caller3 = new ServiceMonitorCaller();
		caller3.setService(service2);
		caller3.setCallerName("caller3");
		//set frequency in seconds
		caller3.setPollingFrequency(60);
		//set frequency in seconds
		caller3.setGraceTime(30);
		
		try {
			caller1.registerMe();
			caller2.registerMe();
			caller3.registerMe();
			System.out.println("All callers registered.");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
