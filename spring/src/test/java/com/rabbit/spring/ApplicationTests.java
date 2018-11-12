package com.rabbit.spring;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbit.spring.entity.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.file.Files;
import java.nio.file.Paths;

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

			messageProperties.setContentType("text/plain");
			messageProperties.setHeader("头部信息","abc");


			Message message = new Message("abcd".getBytes(),messageProperties);


			rabbitTemplate.send("topic001","spring.abc",message);

//		rabbitTemplate.convertAndSend("topic001","spring.amqp","hello word topic001");
//		rabbitTemplate.convertAndSend("topic001","rabbit.amqp","hello word topic001");


		}

		@Test
		public void sendMessageJson() throws JsonProcessingException {


			Order order = new Order();
			new Order().setId("001");
			order.setName("消息订单");
			order.setContent("描述信息");
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(order);
		System.err.println("order 4 json: " + json);

		MessageProperties messageProperties = new MessageProperties();
		//这里注意一定要修改contentType为 application/json
		messageProperties.setContentType("application/json");
		Message message = new Message(json.getBytes(), messageProperties);

		rabbitTemplate.send("topic001", "spring.order", message);
	}



	@Test
	public void testSendJavaMessage() throws Exception {

		Order order = new Order();
		order.setId("001");
		order.setName("订单消息");
		order.setContent("订单描述信息");
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(order);
		System.err.println("order 4 json: " + json);

		MessageProperties messageProperties = new MessageProperties();
		//这里注意一定要修改contentType为 application/json
		messageProperties.setContentType("application/json");
		messageProperties.getHeaders().put("__TypeId__", "com.rabbit.spring.entity.Order");
		Message message = new Message(json.getBytes(), messageProperties);

		rabbitTemplate.send("topic001", "spring.order", message);
	}



	@Test
	public void testSendExtConverterMessage() throws Exception {
			byte[] body = Files.readAllBytes(Paths.get("C:\\Users\\83673\\Desktop", "sql联系orcal.sql"));
			MessageProperties messageProperties = new MessageProperties();
			messageProperties.setContentType("image/png");
			messageProperties.getHeaders().put("extName", "sql");
			Message message = new Message(body, messageProperties);
			rabbitTemplate.send("", "image_queue", message);

//		byte[] body = Files.readAllBytes(Paths.get("d:/002_books", "mysql.pdf"));
//		MessageProperties messageProperties = new MessageProperties();
//		messageProperties.setContentType("application/pdf");
//		Message message = new Message(body, messageProperties);
//		rabbitTemplate.send("", "pdf_queue", message);
	}



}
