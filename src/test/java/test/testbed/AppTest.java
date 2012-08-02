package test.testbed;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.testbed.*;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
	public static Object proxy;
	public static Map<String, Integer> map = new HashMap<String, Integer>();

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
    }

    

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
    	proxy = new Monitor(new ServiceInstance()).getProxy();
    	new Executor1().start();
    	
    }
    public static void main (String[] args) {
    	List<Object> l = new LinkedList<Object>();
    	l.add(new Object());
    	l.add(new Object());
    	int j = 0;
    	for (Object i : l) {
    		if(j==0){
    			l.remove(i);
    		}
    		j++;
    	}
    }
    
    public static void main1 (String[] args) {
        int loop1 = 0;
        int loop2 = 0;
    	map.put("lightService", loop2 = Integer.parseInt(args[0]));
		map.put("intensiveService", loop1 = Integer.parseInt(args[1]));
		//map.put("lightService", loop2 = 5);
		//map.put("intensiveService", loop1 = 0);
    	proxy = new Monitor(new ServiceInstance()).getProxy();
    	AppTest ap = new AppTest("tt");
    	for (int i = 0; i < loop1; i++) {
    		ap.new Executor1().start();
    	}
    	for (int i = 0; i < loop2; i++) {
    	    ap.new Executor2().start();
    	}
    }
    
    public class Executor1 extends Thread {
    	
    	public void run(){
    		try {
				Thread.sleep((long)2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		((IServiceInstance)proxy).intensiveService();
    	}
    }
  public class Executor2 extends Thread {
    	
    	public void run(){
    		try {
				Thread.sleep((long)2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		((IServiceInstance)proxy).lightService();
    	}
    }
  
}

// 1vcpu 128m 100 0: 9398ms  0ms
// 1vcpu 128m 70 30: 14217ms 47854ms
// 1vcpu 512m 70 30: 13121ms 46701ms
// 2vcpu 512m 70 30: 12576ms 41427ms
// 2vcpu 2048m 70 30: 9131ms 37418ms
// 4vcpu 2048m 70 30: 5908ms 23130ms