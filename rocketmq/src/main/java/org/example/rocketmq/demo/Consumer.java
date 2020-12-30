package org.example.rocketmq.demo;


import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @ClassName Consumer
 * @Description
 * @PackageName org.example.rocketmq.demo.Consumer
 * @Author fate
 * @Date 2020/12/30  15:57
 **/
public class Consumer {
 
    /**
     * 当前例子是PushConsumer用法，使用方式给用户感觉是消息从RocketMQ服务器推到了应用客户端。<br>
     * 但是实际PushConsumer内部是使用长轮询Pull方式从MetaQ服务器拉消息，然后再回调用户Listener方法<br>
     */
    public static void main(String[] args) throws InterruptedException,MQClientException {
        /**
         * Consumer组名，多个Consumer如果属于一个应用，订阅同样的消息，且消费逻辑一致，则应该将它们归为同一组
         * 一个应用创建一个Consumer，由应用来维护此对象，可以设置为全局对象或者单例<br>
         * 注意：ConsumerGroupName需要由应用来保证唯一
         */
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("ConsumerGroupName");
        consumer.setNamesrvAddr("127.0.0.1:9876");
        consumer.setInstanceName("Consumber");
 
        /**
         * 订阅指定topic下tags分别等于TagA或TagB或TagC
         */
        consumer.subscribe("TopicTest11", "TagA || TagB || TagC");
        /**
         * 订阅指定topic下所有消息<br>
         * 注意：一个consumer对象可以订阅多个topic
         */
        /*consumer.subscribe("TopicTest12", "*");*/
 
        consumer.registerMessageListener(new MessageListenerConcurrently() {
 
            /**
             * 默认msgs里只有一条消息，可以通过设置consumeMessageBatchMaxSize参数来批量接收消息
             * consumeThreadMin:消费线程池数量 默认最小值10
             * consumeThreadMax:消费线程池数量 默认最大值20
             */
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(
                    List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                System.out.println(Thread.currentThread().getName()
                        + " Receive New Messages: " + msgs.size());
 
                MessageExt msg = msgs.get(0);
                if (msg.getTopic().equals("TopicTest11")) {
                    // 执行TopicTest1的消费逻辑
                    if (msg.getTags() != null && msg.getTags().equals("TagA")) {
                        // 执行TagA的消费
                        System.out.println("TopicTest11------>"+new String(msg.getBody()));
                    } else if (msg.getTags() != null && msg.getTags().equals("TagB")) {
                        // 执行TagC的消费
                    } else if (msg.getTags() != null && msg.getTags().equals("TagC")) {
                        // 执行TagD的消费
                    }
                } else if (msg.getTopic().equals("TopicTest12")) {
                    /*System.out.println("TopicTest2------>"+new String(msg.getBody()));*/
                }
 
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
 
        /**
         * Consumer对象在使用之前必须要调用start初始化，初始化一次即可<br>
         */
        consumer.start();
 
        System.out.println("Consumer Started.");
    }
}