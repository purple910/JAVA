package org.example.rocketmq;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.UUID;

/**
 * @ClassName produce
 * @Description
 * @PackageName org.example.rocketmq.produce
 * @Author fate
 * @Date 2020/12/8    11:03
 **/
public class SyncProducer {
    private static DefaultMQProducer producer = null;

    public static void main(String[] args) {
        System.out.print("[----------]Start\n");
        int pro_count = 1;
        if (args.length > 0) {
            pro_count = Integer.parseInt(args[0]);
        }
        boolean result = false;
        try {
            ProducerStart();
            for (int i = 1; i < pro_count; i++) {
                String msg = "hello rocketmq "+ i+"".toString();
                SendMessage("qch_20170706",              //topic
                        "Tag"+i,                           //tag
                        "Key"+i,                           //key
                        msg);                                  //body
                System.out.print(msg + "\n");
            }
        }finally {
            producer.shutdown();
        }
        System.out.print("[----------]Succeed\n");
    }

    private static boolean ProducerStart() {
        producer = new DefaultMQProducer("pro_qch_test");
        producer.setNamesrvAddr("192.168.69.173:9876");
        producer.setInstanceName(UUID.randomUUID().toString());
        try {
            producer.start();
        } catch(MQClientException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private static boolean SendMessage(String topic,String tag,String key, String str) {
        Message msg = new Message(topic,tag,key,str.getBytes());
        try {
            SendResult result = producer.send(msg);
            SendStatus status = result.getSendStatus();
            System.out.println("___________________________SendMessage: "+status.name());
        } catch (MQClientException | RemotingException | MQBrokerException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
