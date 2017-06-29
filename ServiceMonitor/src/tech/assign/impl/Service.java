package tech.assign.impl;

import java.util.Date;

/**
 * The service
 * @author kanchana_jkcs
 *
 */
public class Service {
	private String host;
	
	private int port;
	
	private Date outageStart;
	 
	private Date outageEnd;	

	public Service(String host, int port) {
		super();
		this.host = host;
		this.port = port;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public Date getOutageStart() {
		return outageStart;
	}

	public void setOutageStart(Date outageStart) {
		this.outageStart = outageStart;
	}

	public Date getOutageEnd() {
		return outageEnd;
	}

	public void setOutageEnd(Date outageEnd) {
		this.outageEnd = outageEnd;
	}	 
}
