package ficl.io;

public interface ISubscriber {
	public void addListener(ISubscriptionListener listener);
	public void removeListener(ISubscriptionListener listener);
	public void startSubscriber();
}
