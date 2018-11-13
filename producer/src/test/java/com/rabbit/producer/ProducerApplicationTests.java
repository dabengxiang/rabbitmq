package com.rabbit.producer;

import com.rabbit.entity.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProducerApplicationTests {

	
	@Autowired
	private ProducerSender producerSender;
	
	@Test
	public void contextLoads() {
	}


	@Test
	public void testSender1() throws Exception {
		Map<String, Object> properties = new HashMap<>();
		properties.put("number", "12345");
		properties.put("send_time", new Date().toString());
		properties.put("contentType", "utf-8");
		producerSender.sendMessage("Hello RabbitMQ For Spring Boot!", properties);
	}


	@Test
	public void testSender2() throws Exception {
		Order order = new Order();
		order.setId("333");
		order.setName("oooo");
		producerSender.sendOrder(order);
	}
}
