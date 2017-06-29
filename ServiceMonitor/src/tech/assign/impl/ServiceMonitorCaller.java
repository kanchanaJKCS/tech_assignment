package tech.assign.impl;

/**
 * The service monitor caller
 * @author kanchana_jkcs
 *
 */
public class ServiceMonitorCaller {

	/**
	 * The service to check
	 */
	private Service service;	
	
	/**
	 * pollingFrequency in milliseconds
	 */
	private long pollingFrequency;
	
	/**
	 * graceTime in milliseconds
	 */
	private long graceTime;	
	
	/**
	 * The caller name
	 */
	private String callerName;

	/**
	 * Returns the service
	 * @return the service
	 */
	public Service getService() {
		return service;
	}

	/**
	 * Sets the service
	 * @param service the service to set
	 */
	public void setService(Service service) {
		this.service = service;
	}
	
	/**
	 * Returns polling frequency in milliseconds
	 * @return
	 */
	public long getPollingFrequency() {
		return pollingFrequency;
	}
	
	/**
	 * Sets the polling frequency in seconds
	 * @param pollingFrequency the polling 
	 */
	public void setPollingFrequency(long pollingFrequency) {
		this.pollingFrequency = pollingFrequency * 1000;
	}

	/**
	 * Returns grace time in milliseconds
	 * @return
	 */
	public long getGraceTime() {
		return graceTime;
	}

	/**
	 * Sets grace time in seconds
	 * @param graceTime
	 */
	public void setGraceTime(long graceTime) {
		this.graceTime = graceTime * 1000;
	}

	/**
	 * Returns caller name
	 * @return caller name
	 */
	public String getCallerName() {
		return callerName;
	}

	/**
	 * Sets the caller name
	 * @param callerName
	 */
	public void setCallerName(String callerName) {
		this.callerName = callerName;
	}	

	/**
	 * Receives caller notification
	 * @param msg the notification message
	 */
	public void notifyMe(String msg) {
		System.out.println(callerName + " received notification : " + msg);
		
	}
	
	/**
	 * Register the caller for the service.
	 * @throws Exception
	 */
	public void registerMe() throws Exception {
		//Checks whether service is properly defined
		if (service == null || (service.getHost() == null || service.getHost().isEmpty())
				|| !(service.getPort() > 0)) {
			throw new Exception(callerName + " : service should be properly defined");
		} 
		
		//Checks whether polling frequency is NOT less than 1 sec.
		if (pollingFrequency < 1000) {
			throw new Exception(callerName + " : pollingFrequency should be greater than 1 sec.");
		}
		
		//Starts the monitoring task for the caller
		ServiceMonitoringTask monitorTask = new ServiceMonitoringTask(this);
		Thread serviceMonitorScheduler = new Thread(monitorTask, callerName);
		serviceMonitorScheduler.start();
		System.out.println(callerName + " registered");
	}
}
