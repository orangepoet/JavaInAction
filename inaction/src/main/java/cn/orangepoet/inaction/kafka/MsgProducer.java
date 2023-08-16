package cn.orangepoet.inaction.kafka;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class MsgProducer {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // KafkaProducer的配置
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        // 创建KafkaProducer实例
        KafkaProducer<String, String> producer = new KafkaProducer<>(props);

        // 创建一个ProducerRecord对象，包含要发送的消息和目标主题
        ProducerRecord<String, String> record = new ProducerRecord<>("mytopic", "Hello Kafka!");

        // 发送消息，并获取Future对象
        Future<RecordMetadata> future = producer.send(record, new Callback() {
            @Override
            public void onCompletion(RecordMetadata metadata, Exception exception) {
                if (exception == null) {
                    // 消息发送成功
                    System.out.println("Message sent successfully! Topic: " + metadata.topic() + ", Partition: " + metadata.partition() + ", Offset: " + metadata.offset());
                } else {
                    // 消息发送失败
                    exception.printStackTrace();
                }
            }
        });

        // 等待消息发送完成
        future.get();

        // 关闭KafkaProducer
        producer.close();
    }
}
