package com.ozomall.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.HashMap;
import java.util.Map;


/**
 * Broker:它提供一种传输服务,它的角色就是维护一条从生产者到消费者的路线，保证数据能按照指定的方式进行传输,
 * Exchange：消息交换机,它指定消息按什么规则,路由到哪个队列。
 * Queue:消息的载体,每个消息都会被投到一个或多个队列。
 * Binding:绑定，它的作用就是把exchange和queue按照路由规则绑定起来.
 * Routing Key:路由关键字,exchange根据这个关键字进行消息投递。
 * vhost:虚拟主机,一个broker里可以有多个vhost，用作不同用户的权限分离。
 * Producer:消息生产者,就是投递消息的程序.
 * Consumer:消息消费者,就是接受消息的程序.
 * Channel:消息通道,在客户端的每个连接里,可建立多个channel.
 */
@Configuration
public class RabbitConfig {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private int port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    // 订单超时死信队列
    public static final String Order_EX_Delay = "order.ex.delay";
    public static final String Order_QUEUE_Delay = "order.queue.delay";
    public static final String Order_ROUTINGKEY_Delay = "order.routingKey.delay";

    // 订单超时处理队列
    public static final String Order_EX = "order.exchange";
    public static final String Order_QUEUE = "order.queue";
    public static final String Order_ROUTINGKEY = "order.routingKey";

    // 订单创建队列
    public static final String Order_EX_Add = "order.exchange";
    public static final String Order_QUEUE_Add = "order.queue";
    public static final String Order_ROUTINGKEY_Add = "order.routingKey";

    @Bean
    public CachingConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host, port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setPublisherConfirms(true);
        return connectionFactory;
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    //必须是prototype类型
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        return template;
    }

    /**
     * 针对消费者配置
     * 1. 设置交换机类型
     * 2. 将队列绑定到交换机
     * FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念
     * HeadersExchange ：通过添加属性key-value匹配
     * DirectExchange:按照routingkey分发到指定队列
     * TopicExchange:多关键字匹配
     */
    /**
     * 死信队列
     */
    @Bean
    public DirectExchange orderDelayEx() {
        return new DirectExchange(Order_EX_Delay);
    }

    @Bean
    public Queue orderDelayQueue() {
        Map<String, Object> params = new HashMap<>();
        // x-dead-letter-exchange 声明了队列里的死信转发到的DLX名称，
        params.put("x-dead-letter-exchange", Order_EX);
        // x-dead-letter-routing-key 声明了这些死信在转发时携带的 routing-key 名称。
        params.put("x-dead-letter-routing-key", Order_ROUTINGKEY);
        return new Queue(Order_QUEUE_Delay, true, false, false, params);
    }

    @Bean
    public Binding orderDelayBinding() {
        return BindingBuilder.bind(orderDelayQueue()).to(orderDelayEx()).with(RabbitConfig.Order_ROUTINGKEY_Delay);
    }

    /**
     * 订单
     */
    // 订单交换机
    @Bean
    public DirectExchange orderExchange() {
        return new DirectExchange(Order_EX);
    }

    // 订单队列
    @Bean
    public Queue orderQueue() {
        return new Queue(Order_QUEUE, true); //队列持久
    }

    // 订单队列绑定交换机
    @Bean
    public Binding orderBinding() {
        return BindingBuilder.bind(orderQueue()).to(orderExchange()).with(RabbitConfig.Order_ROUTINGKEY);
    }


    /**
     * 创建订单
     */
    // 订单交换机
    @Bean
    public DirectExchange orderAddEx() {
        return new DirectExchange(Order_EX);
    }

    // 订单队列
    @Bean
    public Queue orderAddQueue() {
        return new Queue(Order_QUEUE_Add, true); //队列持久
    }

    // 订单队列绑定交换机
    @Bean
    public Binding orderAddBinding() {
        return BindingBuilder.bind(orderAddQueue()).to(orderAddEx()).with(RabbitConfig.Order_ROUTINGKEY_Add);
    }

}
