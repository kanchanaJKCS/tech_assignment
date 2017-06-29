package tech.assign.impl;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;

/**
 * The service monitoring task
 * @author kanchana_jkcs
 *
 */
public class ServiceMonitoringTask implements Runnable {
	ServiceMonitorCaller caller;
	
	public ServiceMonitoringTask(ServiceMonitorCaller caller) {
		this.caller = caller;
	}
	
	@Override
	public void run() {
		System.out.println("Thread " + Thread.currentThread().getName() + " started.");
		while (true) {
			Date current = new Date(); 
			synchronized (caller.getService()) {
				//if outage time do not notify the caller and wait for next round
				if (caller.getService().getOutageStart() != null && caller.getService().getOutageEnd() != null
						&& (current.after(caller.getService().getOutageStart()) 
								&& current.before(caller.getService().getOutageEnd()))) {
					try {
						System.out.println(Thread.currentThread().getName() + " wait for " + caller.getPollingFrequency() + " msec");
						caller.getService().wait(caller.getPollingFrequency());
						
					} catch (Exception e) {
			            e.printStackTrace();
			        }
					
				} else {	
					//if service not up then notify caller
					if (!checkService(caller.getCallerName(), caller.getService().getHost(), 
							caller.getService().getPort())) {
						if (caller.getGraceTime() < caller.getPollingFrequency()) {
							try {
					            // thread to sleep for grace time
								System.out.println(caller.getCallerName() + " sleep for grace time " + caller.getGraceTime() + " msec");
					            Thread.sleep(caller.getGraceTime());
					            
					            if (!checkService(caller.getCallerName(), caller.getService().getHost(), 
					            		caller.getService().getPort())) {
					            	System.out.println(Thread.currentThread().getName() + " notify caller " + caller.getService().getHost() 
					            			+ "|" + caller.getService().getPort() + " after grace time");
					            	caller.notifyMe(caller.getService().getHost() + "|" + caller.getService().getPort() 
					            			+ " service not available.");
					            }
					         } catch (Exception e) {
					            e.printStackTrace();
					         }
						}
						else {
							System.out.println(Thread.currentThread().getName() + " notify caller " 
						+ caller.getService().getHost() + "|" + caller.getService().getPort());
							caller.notifyMe(caller.getService().getHost() + "|" + caller.getService().getPort() 
									+ " service not available.");
						}
					}
					try {
						System.out.println(Thread.currentThread().getName() + " wait for " + caller.getPollingFrequency() + " msec");
						caller.getService().wait(caller.getPollingFrequency());
					} catch (Exception e) {
						e.printStackTrace();
			        }					
				}
			}
		}		
	}
	
	/**
	 * 
	 * @param host the host IP
	 * @param port the port
	 * @return the whether service is up
	 */
	private boolean checkService(String caller, String host, int port) {
		Socket serviceCon = null;
		boolean check = false;
		System.out.println(caller + " checkig service " + host + "|" + port);
		
		try {
			serviceCon = new Socket(host, port);
			check = true;			
		} catch(IOException ioe) {
			check = false;
		} finally {
			if (serviceCon != null && !serviceCon.isClosed()) {
				try {
					serviceCon.close();
				} catch (IOException ioe) {
					System.out.println("Unexpected exception on service while closing " + host + "|" + port + "|" + ioe.getMessage());
				}				
			}
			System.out.println(caller + " checkig service " + host + "|" + port + " status " + check);
		}	
		
		return check;
	}

}
