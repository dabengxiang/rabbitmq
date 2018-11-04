package com.rabbit.spring;

import com.rabbitmq.client.AMQP;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {


	@Autowired
	private RabbitAdmin rabbitAdmin;


	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Test
	public void contextLoads() {
	}

	@Test
	public void testAdmin(){

		Exchange directExchange = new DirectExchange("spring.test.direct",true,false);
		TopicExchange topicExchange = new TopicExchange("spring.test.topic", true, false);
		FanoutExchange fanoutExchange = new FanoutExchange("spring.test.fanout", true, false);

		Queue dirctQueue = new Queue("spring.test.queue.direct",true,false,false);
		Queue topicQueue = new Queue("spring.test.queue.topic", true, false, false);
		Queue fanoutQueue = new Queue("spring.test.queue.fanout", true, false, false);


		Binding directBinding = new Binding("spring.test.queue.direct",Binding.DestinationType.QUEUE,
				"spring.test.direct","spring.direct", null);

		Binding topicBinding = new Binding("spring.test.queue.topic",Binding.DestinationType.QUEUE,
				"spring.test.topic","spring.topic", null);


		Binding fanoutBinding = new Binding("spring.test.queue.fanout",Binding.DestinationType.QUEUE,
				"spring.test.fanout","spring.fanout", null);


		rabbitAdmin.declareExchange(directExchange);
		rabbitAdmin.declareExchange(topicExchange);
		rabbitAdmin.declareExchange(fanoutExchange);

		rabbitAdmin.declareQueue(dirctQueue);
		rabbitAdmin.declareQueue(topicQueue);
		rabbitAdmin.declareQueue(fanoutQueue);


		rabbitAdmin.declareBinding(directBinding);
//		rabbitAdmin.declareBinding(topicBinding);
//		rabbitAdmin.declareBinding(fanoutBinding);

		BindingBuilder.bind(new Queue("spring.test.queue.fanout",false))
				.to(new TopicExchange("spring.test.fanout", true, false))
				.with("spring.fanout");


		rabbitAdmin.declareBinding(BindingBuilder.bind(new Queue("spring.test.queue.fanout",false))
				.to(new TopicExchange("spring.test.fanout", true, false))
				.with("spring.fanout"));

		rabbitAdmin.declareBinding(BindingBuilder.bind(new Queue("spring.test.queue.topic",false))
				.to(new TopicExchange("spring.test.topic", true, false))
				.with("spring.#"));



	}


	@Test
	public void sendMessage1(){

		MessageProperties messageProperties = new MessageProperties();

		messageProperties.setContentType("text/plan");
		messageProperties.setHeader("头部信息","abc");


		Message message = new Message("abcd".getBytes(),messageProperties);


		rabbitTemplate.send("topic001","spring.abc",message);

		rabbitTemplate.convertAndSend("topic001","spring.amqp","hello word topic001");
		rabbitTemplate.convertAndSend("topic001","rabbit.amqp","hello word topic001");


	}





}
