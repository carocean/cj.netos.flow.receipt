package cj.netos.flow.receipt.ports;

import cj.netos.flow.receipt.entities.ChannelDocumentMedia;
import cj.netos.rabbitmq.IRabbitMQProducer;
import cj.studio.ecm.annotation.CjService;
import cj.studio.ecm.annotation.CjServiceRef;
import cj.studio.ecm.net.CircuitException;
import cj.studio.openport.ISecuritySession;
import cj.ultimate.util.StringUtil;
import com.rabbitmq.client.AMQP;

import java.util.HashMap;

@CjService(name = "/channel.service")
public class DefaultChannelTaskPorts implements IChannelTaskPorts {
    @CjServiceRef(refByName = "@.rabbitmq.producer.channel")
    IRabbitMQProducer rabbitMQProducer;

    @Override
    public void pushChannelDocument(ISecuritySession securitySession, String channel, String docid, long interval) throws CircuitException {
        AMQP.BasicProperties properties = new AMQP.BasicProperties().builder()
                .type("/channel/document.mq")
                .headers(new HashMap() {
                    {
                        put("command", "pushChannelDocument");
                        put("creator", securitySession.principal());
                        put("channel", channel);
                        put("docid", docid);
                        put("sender", securitySession.principal());
                        put("interval", interval);
                    }
                }).build();
        byte[] body = new byte[0];
        rabbitMQProducer.publish("jobCenter", properties, body);
    }

    @Override
    public void pushChannelDocumentOfPerson(ISecuritySession securitySession, String channel, String docid, String creator, long interval) throws CircuitException {
        AMQP.BasicProperties properties = new AMQP.BasicProperties().builder()
                .type("/channel/document.mq")
                .headers(new HashMap() {
                    {
                        put("command", "pushChannelDocumentOfPerson");
                        put("creator", creator);
                        put("channel", channel);
                        put("docid", docid);
                        put("sender", securitySession.principal());
                        put("interval", interval);
                    }
                }).build();
        byte[] body = new byte[0];
        rabbitMQProducer.publish("jobCenter", properties, body);
    }

    @Override
    public void pushChannelDocumentLike(ISecuritySession securitySession, String channel, String docid, String creator, long interval) throws CircuitException {
        AMQP.BasicProperties properties = new AMQP.BasicProperties().builder()
                .type("/channel/document/like.mq")
                .headers(new HashMap() {
                    {
                        put("command", "pushChannelDocumentLike");
                        put("liker", securitySession.principal());
                        put("creator", creator);
                        put("channel", channel);
                        put("docid", docid);
                        put("interval", interval);
                    }
                }).build();
        byte[] body = new byte[0];
        rabbitMQProducer.publish("jobCenter", properties, body);
    }

    @Override
    public void pushChannelDocumentUnlike(ISecuritySession securitySession, String channel, String docid, String creator, long interval) throws CircuitException {
        AMQP.BasicProperties properties = new AMQP.BasicProperties().builder()
                .type("/channel/document/unlike.mq")
                .headers(new HashMap() {
                    {
                        put("command", "pushChannelDocumentUnlike");
                        put("unliker", securitySession.principal());
                        put("creator", creator);
                        put("channel", channel);
                        put("docid", docid);
                        put("interval", interval);
                    }
                }).build();
        byte[] body = new byte[0];
        rabbitMQProducer.publish("jobCenter", properties, body);
    }

    @Override
    public void pushChannelDocumentComment(ISecuritySession securitySession, String channel, String docid, String creator, String commentid, String comments, long interval) throws CircuitException {
        AMQP.BasicProperties properties = new AMQP.BasicProperties().builder()
                .type("/channel/document/comment.mq")
                .headers(new HashMap() {
                    {
                        put("command", "pushChannelDocumentComment");
                        put("commenter", securitySession.principal());
                        put("creator", creator);
                        put("channel", channel);
                        put("docid", docid);
                        put("commentid", commentid);
                        put("interval", interval);
                    }
                }).build();
        byte[] body = StringUtil.isEmpty(comments) ? new byte[0] : comments.getBytes();
        rabbitMQProducer.publish("jobCenter", properties, body);
    }

    @Override
    public void pushChannelDocumentUncomment(ISecuritySession securitySession, String channel, String docid, String creator, String commentid, long interval) throws CircuitException {
        AMQP.BasicProperties properties = new AMQP.BasicProperties().builder()
                .type("/channel/document/uncomment.mq")
                .headers(new HashMap() {
                    {
                        put("command", "pushChannelDocumentUncomment");
                        put("uncommenter", securitySession.principal());
                        put("creator", creator);
                        put("channel", channel);
                        put("docid", docid);
                        put("commentid", commentid);
                        put("interval", interval);
                    }
                }).build();
        byte[] body = new byte[0];
        rabbitMQProducer.publish("jobCenter", properties, body);
    }

    @Override
    public void pushChannelDocumentMedia(ISecuritySession securitySession, ChannelDocumentMedia media, long interval) throws CircuitException {
        AMQP.BasicProperties properties = new AMQP.BasicProperties().builder()
                .type("/channel/document/media.mq")
                .headers(new HashMap() {
                    {
                        put("command", "pushChannelDocumentMedia");
                        put("creator", securitySession.principal());
                        put("media", media);
                        put("interval", interval);
                    }
                }).build();
        byte[] body = new byte[0];
        rabbitMQProducer.publish("jobCenter", properties, body);
    }
}
