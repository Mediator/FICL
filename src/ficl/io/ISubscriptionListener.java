package ficl.io;

public interface ISubscriptionListener {
	public void update(String className, byte[] classData);
}
