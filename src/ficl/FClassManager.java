package ficl;

import java.util.HashSet;
import java.util.Hashtable;

import ficl.io.ISubscriber;
import ficl.io.ISubscriptionListener;
import ficl.io.NetworkSubscriber;

public class FClassManager implements ISubscriptionListener {

	private Hashtable<String, Class> loadedClasses;
	private ISubscriber subscriber;
	private HashSet<INotificationReceiver> notifiers;
	private FClassManager()
	{
		notifiers = new HashSet<INotificationReceiver>();
		loadedClasses = new Hashtable<String, Class>();
	}
	public FClassManager(String addr, int port)
	{
		this();
		subscriber = new NetworkSubscriber(addr,port);
		subscriber.addListener(this);
		subscriber.startSubscriber();
	}
	@Override
	public void update(String className, byte[] classData) {
		FClassLoader classLoader = new FClassLoader();
		Class cls = null;
		try {
			cls = classLoader.inLoadClass(className, classData);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (INotificationReceiver receiver : notifiers)
		{
			receiver.receiveNotification(className, cls);
		}
	}

	
	public void addNotificationReceiver(INotificationReceiver receiver)
	{
		this.notifiers.add(receiver);
	}
	
}
