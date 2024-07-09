package cn.edu.scnu.seckill.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.edu.scnu.seckill.config.RabbitmqConfig;
@RestController
public class SenderController {

	@Autowired
	private RabbitTemplate rabbitTemplate;
	//访问send接口
	@RequestMapping("send")
	public String sendMsg(String msg){
	//exchange 交换机名称
	//routingkey 路由key
	//object 消息本身
	rabbitTemplate.convertAndSend(RabbitmqConfig.exName,
			RabbitmqConfig.routingKey, msg);
		return "success";

	}
}
