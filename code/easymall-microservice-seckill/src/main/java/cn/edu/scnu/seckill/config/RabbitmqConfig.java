package cn.edu.scnu.seckill.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {
	public static final String exName = "seckillEx";
	public static final String qName = "seckillQueue";
	public static final String routingKey = "seckill";

	// 声明交换机 direct
	@Bean
	public DirectExchange exInit() {
		return new DirectExchange(exName, false, false);
	}

	// 声明队列
	@Bean
	public Queue queueInit() {
		return new Queue(qName, false, false, false);
	}

	// 绑定关系
	@Bean
	public Binding bindInit() {
		// 将队列携带路由key绑定交换机
		return BindingBuilder.bind(queueInit()).to(exInit()).with(routingKey);
	}
}
