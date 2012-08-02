package com.testbed;

public class ServiceInstance implements IServiceInstance{

	public void lightService(){
		long j = (long)99999999;
		Object[] o = new Object[50000];
	    for (int i=0; i < 50000; i++){
			o[i] = new Object();
		}
		for (long i=0; i < j; i++){
		
		}
	}
	
	public void intensiveService(){
		long j = (long)999999999;
		Object[] o = new Object[500000];
	    for (int i=0; i < 500000; i++){
			o[i] = new Object();
		}
		for (long i=0; i < j; i++){
			
		}
	}
}
