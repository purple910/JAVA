package org.example.rocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;
import java.util.UUID;

/**
 * @ClassName ConsumerTest
 * @Description
 * @PackageName org.example.rocketmq.ConsumerTest
 * @Author fate
 * @Date 2020/12/30  15:56
 **/
public class ConsumerTest {
    public static void main(String[] args) {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("con_qch_test");
        consumer.setInstanceName(UUID.randomUUID().toString());
        consumer.setConsumeMessageBatchMaxSize(32);
        consumer.setNamesrvAddr("192.168.69.173:9876");
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(
                    List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                for(MessageExt me : list) {
                    if("Tag1".equals(me.getTags())){
                        System.out.println("处理 Tag1 业务");
                        System.out.println(new String(me.getBody()) + "消费成功" + "\n");
                    }else if("Tag2".equals(me.getTags())){
                        System.out.println("处理 Tag2 业务");
                        System.out.println(new String(me.getBody()) + "消费成功" + "\n");
                    }else if("Tag3".equals(me.getTags())){
                        System.out.println("处理 Tag3 业务");
                        System.out.println(new String(me.getBody()) + "消费失败" + "\n");
                        return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                    }else{
                        //consumer.subscribe("qch_20170706", "Tag1||Tag2||Tag3");
                        System.out.println("过滤掉的业务"+ me.getKeys());
                    }
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        try {
            consumer.subscribe("qch_20170706", "Tag1||Tag2||Tag3");
            consumer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}