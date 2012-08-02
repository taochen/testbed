package test.testbed;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;


public class Monitor implements InvocationHandler{

	private Object realInstance;
	private Map<String, Counter> map = new HashMap<String, Counter>();
	
	public Monitor (Object realInstance){
		this.realInstance = realInstance;
	}
	
	public Object invoke(Object obj, Method method, Object[] args)
			throws Throwable {
	    long pre = System.currentTimeMillis();
		Object ret = method.invoke(realInstance, args);
		long result = System.currentTimeMillis() - pre;
	
		synchronized (map) {
			
			if (!map.containsKey(method.getName())) {
				map.put(method.getName(), new Counter());
			}
			map.get(method.getName()).calculate(result);
			
			if (AppTest.map.get(method.getName()) == map.get(method.getName()).getCount()) {
				print(method.getName());
			}
		}
		return ret;
	}
	
	
	public Object getProxy() {

		return Proxy.newProxyInstance(
				realInstance.getClass().getClassLoader(), realInstance
						.getClass().getInterfaces(), this);
	}
	
	public void print(String name){		
		System.out.print ("Average result for " + name + " is " + map.get(name).getTime() + " ms, worest is " +  map.get(name).getWorest() + "ms\n");
		
	}

	private class Counter {
		long time = 0;
		int count = 0;
		long worest = 0;
		
		public void calculate (long newTime){
			time = time * count;
			time += newTime;
			count++;
			time = time/count;
			if (worest < newTime) {
				worest = newTime;
			}
		}
		
		public long getTime(){
			return time;
		}
		
		public long getCount(){
			return count;
		}
		
		public long getWorest(){
			return worest;
		}
	}
}
