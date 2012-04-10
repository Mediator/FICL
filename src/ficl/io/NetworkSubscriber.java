package ficl.io;

import java.util.Arrays;
import java.util.HashSet;

import org.zeromq.ZMQ;

public class NetworkSubscriber implements ISubscriber, Runnable {
	private HashSet<ISubscriptionListener> listeners;
	private String addr;
	private int port;
	private ZMQ.Context context;
	private ZMQ.Socket subscriber;
	
	public NetworkSubscriber(String addr, int port)
	{
		listeners = new HashSet<ISubscriptionListener>();
		this.addr = addr;
		this.port = port;
	}
	
	@Override
	public void addListener(ISubscriptionListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeListener(ISubscriptionListener listener) {
		listeners.remove(listener);
		
	}

	@Override
	public void startSubscriber() {
		 context = ZMQ.context(1);
		    subscriber = context.socket(ZMQ.SUB);

		    subscriber.connect("tcp://" + addr + ":" + port);
		    subscriber.subscribe("UPDATE".getBytes());
		    Thread executor = new Thread(this);
		    executor.start();
		  
	}

	@Override
	public void run() {
		  while (true) {
		      String address = new String(subscriber.recv(0));
		      String className = new String(subscriber.recv(0));
		      byte[] classData = subscriber.recv(0);	
		      System.out.println(address + " : " + className + " " + Arrays.toString(classData));
		      for (ISubscriptionListener listener : this.listeners)
		      {
		    	  listener.update(className, classData);
		      }
		      
		    }
		
	}
	
	
}
