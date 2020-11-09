package cj.netos.flow.receipt.ports;

import cj.netos.flow.receipt.entities.GeoDocumentMedia;
import cj.netos.rabbitmq.IRabbitMQProducer;
import cj.studio.ecm.annotation.CjService;
import cj.studio.ecm.annotation.CjServiceRef;
import cj.studio.ecm.net.CircuitException;
import cj.studio.openport.ISecuritySession;
import cj.ultimate.gson2.com.google.gson.Gson;
import cj.ultimate.util.StringUtil;
import com.rabbitmq.client.AMQP;

import java.util.HashMap;

@CjService(name = "/geosphere.service")
public class DefaultGeoTaskPorts implements IGeosphereTaskPorts {
    @CjServiceRef(refByName = "@.rabbitmq.producer.geosphere")
    IRabbitMQProducer rabbitMQProducer;

    @Override
    public void pushGeoDocument(ISecuritySession securitySession, String receptor, String docid) throws CircuitException {
        AMQP.BasicProperties properties = new AMQP.BasicProperties().builder()
                .type("/geosphere/document.mq")
                .headers(new HashMap() {
                    {
                        put("command", "pushGeoDocument");
                        put("receptor", receptor);
                        put("docid", docid);
                        put("sender", securitySession.principal());
                    }
                }).build();
        byte[] body = new byte[0];
        rabbitMQProducer.publish("jobCenter", properties, body);
    }

    @Override
    public void pushGeoDocumentLike(ISecuritySession securitySession, String receptor, String docid) throws CircuitException {
        AMQP.BasicProperties properties = new AMQP.BasicProperties().builder()
                .type("/geosphere/document/like.mq")
                .headers(new HashMap() {
                    {
                        put("command", "pushGeoDocumentLike");
                        put("receptor", receptor);
                        put("docid", docid);
                        put("liker", securitySession.principal());
                        
                    }
                }).build();
        byte[] body = new byte[0];
        rabbitMQProducer.publish("jobCenter", properties, body);
    }

    @Override
    public void pushGeoDocumentUnlike(ISecuritySession securitySession, String receptor, String docid) throws CircuitException {
        AMQP.BasicProperties properties = new AMQP.BasicProperties().builder()
                .type("/geosphere/document/unlike.mq")
                .headers(new HashMap() {
                    {
                        put("command", "pushGeoDocumentUnlike");
                        put("receptor", receptor);
                        put("docid", docid);
                        put("unliker", securitySession.principal());
                        
                    }
                }).build();
        byte[] body = new byte[0];
        rabbitMQProducer.publish("jobCenter", properties, body);
    }

    @Override
    public void pushGeoDocumentComment(ISecuritySession securitySession, String receptor, String docid, String commentid, String comments) throws CircuitException {
        AMQP.BasicProperties properties = new AMQP.BasicProperties().builder()
                .type("/geosphere/document/comment.mq")
                .headers(new HashMap() {
                    {
                        put("command", "pushGeoDocumentComment");
                        put("receptor", receptor);
                        put("docid", docid);
                        put("commenter", securitySession.principal());
                        put("commentid", commentid);
                        
                    }
                }).build();
        byte[] body = StringUtil.isEmpty(comments) ? new byte[0] : comments.getBytes();
        rabbitMQProducer.publish("jobCenter", properties, body);
    }

    @Override
    public void pushGeoDocumentUncomment(ISecuritySession securitySession, String receptor, String docid, String commentid) throws CircuitException {
        AMQP.BasicProperties properties = new AMQP.BasicProperties().builder()
                .type("/geosphere/document/uncomment.mq")
                .headers(new HashMap() {
                    {
                        put("command", "pushGeoDocumentUncomment");
                        put("receptor", receptor);
                        put("docid", docid);
                        put("uncommenter", securitySession.principal());
                        put("commentid", commentid);
                        
                    }
                }).build();
        byte[] body = new byte[0];
        rabbitMQProducer.publish("jobCenter", properties, body);
    }

    @Override
    public void pushGeoDocumentMedia(ISecuritySession securitySession, GeoDocumentMedia media) throws CircuitException {
        AMQP.BasicProperties properties = new AMQP.BasicProperties().builder()
                .type("/geosphere/document/media.mq")
                .headers(new HashMap() {
                    {
                        put("command", "pushGeoDocumentMedia");
                        put("creator", securitySession.principal());
                        
                    }
                }).build();
        byte[] body = new Gson().toJson(media).getBytes();
        rabbitMQProducer.publish("jobCenter", properties, body);
    }
}
