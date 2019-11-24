package com.hqw.pro;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.TopicPartition;

import java.util.*;

public class KafkaServerK {

    KafkaProducer<String, String> producer;
    KafkaConsumer<String, String> consumer;
    String topic;

    //生产者消费者都是连接任何broker节点获取集群信息，bootstrap.servers
    //指定分区是在配置或者代码里面指定，例如指定partitioner.class（主要根据key路由），指定group（ConsumerRecords包含partition、offset等）
    public KafkaServerK(String topic) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "172.17.11.182:9092,172.17.11.183:9092");
        props.put("client.id", "DemoProducer");
        props.put("batch.size", 16384);//16M
        props.put("linger.ms", 1000);
        props.put("buffer.memory", 33554432);//32M
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("retries", 3);
        //指定生产acks级别，0-无确认，1-ISR中的leader确认，-1-ISR中的leader和所有follower都确认
        props.put("acks", 1);
        //指定生产发送分区策略
        props.put("partitioner.class", "com.horizon.kafka.v010.SimplePartitioner");
        producer = new KafkaProducer<>(props);
        this.topic = topic;


        Properties consumerProps = new Properties();
        consumerProps.put("enable.auto.commit", "false");
        consumerProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        consumerProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        consumerProps.put("session.timeout.ms", "30000");
        consumerProps.put("group.id", "test222");
        //指定消费分区策略，默认是RangeAssignor，可自定义实现PartitionAssignor接口
        props.put("partition.assignment.strategy", "org.apache.kafka.clients.consumer.RangeAssignor");
        consumer = new KafkaConsumer<>(consumerProps);
    }

    //producer代码中我们需要两个类，一个是指定partitioner的类，一个为真正的producer类
    public void producerMsg() throws InterruptedException {

        Random rnd = new Random();
        int events = 10;
        for (long nEvents = 0; nEvents < events; nEvents++) {
            long runtime = new Date().getTime();
            String number = String.valueOf(rnd.nextInt(255));
            try {
                producer.send(new ProducerRecord<>("topicName", number, String.valueOf(runtime)),
                        new CallBackFuntion(topic, String.valueOf(runtime)));
                System.out.println("Sent message: (" + number + ", " + runtime + ")");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Thread.sleep(10000);
    }


    public void consumerMsg() {
        try {

            //前者指定消费模式为指定topic,由kafka coordinator指定partition分配策略，
            //后者指定消费模式为指定partition,由用户自定义partition分配策略。
            //新增组成员consumer、离开组成员consumer、consumer崩溃时，由coordinator发起rebalance，https://www.cnblogs.com/songanwei/p/9202803.html
            consumer.subscribe(Collections.singletonList(this.topic));
            //consumer.assign(Arrays.asList(new TopicPartition(this.topic,0)));

            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(1000);
                for (ConsumerRecord<String, String> record : records) {
                    System.out.println("Received message: (" + record.key() + ", " + record.value() + ") at partition " + record.partition() + " offset " + record.offset());
                }
                //consumer.commitSync();

                //按分区消费，手动提交offset
                for (TopicPartition partition : records.partitions()) {
                    List<ConsumerRecord<String, String>> partitionRecords = records.records(partition);

                    for (ConsumerRecord<String, String> record : partitionRecords) {
                        System.out.println(record.offset() + ": " + record.value());
                    }
                    long lastOffset = partitionRecords.get(partitionRecords.size() - 1).offset();
                    consumer.commitSync(Collections.singletonMap(partition, new OffsetAndMetadata(lastOffset + 1)));
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Controller，整个Kafka集群一个Controller，用于partition在broker的分配和leader选举，整个集群维度，broker维度
     * Coordinator，用于消费分配partition，consumer group维度。组成员管理，位移管理。
     * 当新版本consumer group的第一个consumer启动的时候，它会去和kafka server确定谁是它们组的coordinator。之后该group内的所有成员都会和该coordinator进行协调通信。
     *
     * 选coordinator：
     * 1、看offset保存在那个partition
     * 2、该partition leader所在的broker就是被选定的coordinator
     * consumer group的coordinator，和保存consumer group offset的partition leader是同一台机器。
     *
     * Rebalance过程：Join和Sync
     * 1 Join， 顾名思义就是加入组。这一步中，所有成员都向coordinator发送JoinGroup请求，请求入组。
     * 一旦所有成员都发送了JoinGroup请求，coordinator会从中选择一个consumer担任leader的角色，
     * 并把组成员信息以及订阅信息发给leader——注意leader和coordinator不是一个概念。leader负责消费分配方案的制定。
     *
     * 2 Sync，这一步leader开始分配消费方案，即哪个consumer负责消费哪些topic的哪些partition。一旦完成分配，
     * leader会将这个方案封装进SyncGroup请求中发给coordinator，非leader也会发SyncGroup请求，只是内容为空。
     * coordinator接收到分配方案之后会把方案塞进SyncGroup的response中发给各个consumer。
     * 这样组内的所有成员就都知道自己应该消费哪些分区了。
     *
     * 新增组成员consumer、离开组成员consumer、consumer崩溃时，由coordinator发起rebalance
     *
     * 注意！！ consumer group的分区分配方案是在客户端执行的！
     * Kafka默认为你提供了两种分配策略：range和round-robin。
     * RoundRobinAssignor RangeAssignor(默认)，实现的是PartitionAssignor接口
     *
     *
     * 确定consumer group位移信息写入__consumers_offsets的哪个partition，__consumers_offsets partition =Math.abs(groupId.hashCode() % groupMetadataTopicPartitionCount)   注意：groupMetadataTopicPartitionCount由offsets.topic.num.partitions指定，默认是50个分区
     *
     */

}


class SimplePartitioner implements Partitioner {

    public SimplePartitioner() {

    }

    @Override
    public void configure(Map<String, ?> configs) {
    }

    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        String k = (String) key;
        int partitionCount = cluster.partitionCountForTopic(topic);
        return Integer.parseInt(k) % (partitionCount - 1);

    }

    @Override
    public void close() {
    }

}


class CallBackFuntion implements Callback {

    private String topic;
    private String message;

    public CallBackFuntion(String topic, String message) {
        this.topic = topic;
        this.message = message;
    }

    @Override
    public void onCompletion(RecordMetadata recordMetadata, Exception e) {
        if (e != null) {
            System.out.println(topic + ": " + message + "--消息发送失败");
        } else {
            System.out.println(topic + ": " + message + "--消息发送成功");
        }
    }
}



