package kr.ac.hanyang;

import org.ros.message.MessageListener;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.DefaultNodeMainExecutor;
import org.ros.node.NodeConfiguration;
import org.ros.node.NodeMain;
import org.ros.node.NodeMainExecutor;
import org.ros.node.topic.Publisher;
import org.ros.node.topic.Subscriber;

import kr.ac.hanyang.util.RosMessage;

public class KMinterface extends AbstractNodeMain{

	public static Publisher<std_msgs.String> publisher_km;
	public static Subscriber<std_msgs.String> subscriber_km;
	
	public KMinterface(){
		
	}

	public static void main(String[] args) {
		KMinterface node = new KMinterface();
		NodeMain commander = node;
		NodeConfiguration conf = NodeConfiguration.newPrivate();
		NodeMainExecutor executor = DefaultNodeMainExecutor.newDefault();
		executor.execute(commander, conf);
		executor.shutdown();
	}

	public void onStart(ConnectedNode connectedNode){
		System.out.println("KM Interface start!\n");
		
		// KM 객체 생성
		KnowledgeManager km = new KnowledgeManager();
		
		// publisher, subscriber 선언부
		publisher_km = connectedNode.newPublisher("/taskCompletion", std_msgs.String._TYPE);
		subscriber_km = connectedNode.newSubscriber("/taskExecution", std_msgs.String._TYPE);
		
		// 메시지 리스너
		subscriber_km.addMessageListener(new MessageListener<std_msgs.String>(){
			@Override
			public void onNewMessage(std_msgs.String arg) {
				
				RosMessage rosMessage = new RosMessage();
				String msg = arg.getData();
				rosMessage.parse(msg); // 로스메시지 파싱
				
				// 로스메시지 중 헤더의 타겟에 knowledge가 포함되어있는 경우에만 메시지 처리
				if(rosMessage.getHeader().getTarget().contains("knowledge")) {
					
					System.out.println("KM got a new ros message!");
					System.out.println(rosMessage.prettify()); // 로스메시지 출력

					// 로스메시지에 소셜컨텍스트가 포함되어있는 경우에만 메시지 처리
					if(rosMessage.getSocialContext() != null){
						
						RosMessage resultRosMessage = km.querySocialContext(rosMessage); // 로스메시지 처리
						
						System.out.println("KM result :");
						System.out.println(resultRosMessage.prettify()); // 결과물 출력

						std_msgs.String resultMsg;
						
						resultMsg = publisher_km.newMessage();
						resultMsg.setData(resultRosMessage.prettify());
						publisher_km.publish(resultMsg);
						
					}

				}

			}
			
		});
	}

	@Override
	public GraphName getDefaultNodeName() {
		return GraphName.of("KMinterface");
	}


}
