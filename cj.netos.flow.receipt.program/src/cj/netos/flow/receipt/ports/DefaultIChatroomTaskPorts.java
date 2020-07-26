package cj.netos.flow.receipt.ports;

import cj.netos.rabbitmq.IRabbitMQProducer;
import cj.studio.ecm.annotation.CjService;
import cj.studio.ecm.annotation.CjServiceRef;
import cj.studio.ecm.net.CircuitException;
import cj.studio.openport.ISecuritySession;
import cj.ultimate.util.StringUtil;
import com.rabbitmq.client.AMQP;

import java.util.HashMap;

@CjService(name = "/chat.service")
public class DefaultIChatroomTaskPorts implements IChatroomTaskPorts {
    @CjServiceRef(refByName = "@.rabbitmq.producer.chatroom")
    IRabbitMQProducer rabbitMQProducer;

    @Override
    public void pushMessage(ISecuritySession securitySession, String creator, String room, String msgid, String contentType, String content, long interval) throws CircuitException {
        AMQP.BasicProperties properties = new AMQP.BasicProperties().builder()
                .type("/chat/message.mq")
                .headers(new HashMap() {
                    {
                        put("command", "pushMessage");
                        put("creator", creator);
                        put("sender", securitySession.principal());
                        put("room", room);
                        put("msgid", msgid);
                        put("contentType", contentType);
                        put("interval", interval);
                    }
                }).build();
        byte[] body = StringUtil.isEmpty(content) ? new byte[0] : content.getBytes();
        rabbitMQProducer.publish("jobCenter", properties, body);
    }
}
