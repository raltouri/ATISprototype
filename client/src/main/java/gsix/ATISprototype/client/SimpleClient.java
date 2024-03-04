package gsix.ATISprototype.client;

import gsix.ATISprototype.entities.Message;
import gsix.ATISprototype.entities.Task;
import org.greenrobot.eventbus.EventBus;import org.greenrobot.eventbus.Subscribe;

import gsix.ATISprototype.client.ocsf.AbstractClient;

import java.util.List;

public class SimpleClient extends AbstractClient {
	
	private static SimpleClient client = null;

	private SimpleClient(String host, int port) {
		super(host, port);
	}

	@Override
	protected void handleMessageFromServer(Object msg) {
		Message message = (Message) msg;
		System.out.println("I am in handle from server");
		if(message.getMessage().equals("get all tasks: Done")){
			System.out.println("I am in handle from server get all done");
			//List<Task> allTasks = (List<Task>) message.getData();
			MessageEvent messageEvent=new MessageEvent(message);
			//EventBus.getDefault().post(allTasks);
			EventBus.getDefault().post(messageEvent);
		}else if(message.getMessage().equals("get all users: Done")){
			MessageEvent messageEvent=new MessageEvent(message);
			//EventBus.getDefault().post(allTasks);
			EventBus.getDefault().post(messageEvent);
		}else if(message.getMessage().equals("view task info: Done")){
			System.out.println("I am in handle from server view task info done");
			MessageEvent messageEvent=new MessageEvent(message);
			//EventBus.getDefault().post(allTasks);
			EventBus.getDefault().post(messageEvent);
		}else if(message.getMessage().equals("get task by id: Done")){
			System.out.println("I am in handle from server get task by id done");
			MessageEvent messageEvent=new MessageEvent(message);
			//EventBus.getDefault().post(allTasks);
			EventBus.getDefault().post(messageEvent);
		}else if(message.getMessage().equals("change task status: Done")){
			System.out.println("I am in handle from server change task status done");
			MessageEvent messageEvent=new MessageEvent(message);
			//EventBus.getDefault().post(allTasks);
			EventBus.getDefault().post(messageEvent);
		}else {
			EventBus.getDefault().post(new MessageEvent(message));
		}
	}
	
	public static SimpleClient getClient(String server_ip,int server_port) {
		if (client == null) {
			client = new SimpleClient(server_ip, server_port);
		}
		return client;
	}

}
