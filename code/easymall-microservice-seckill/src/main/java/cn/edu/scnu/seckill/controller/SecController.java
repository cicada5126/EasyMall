package cn.edu.scnu.seckill.controller;

import java.util.List;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easymall.common.pojo.Seckill;
import com.easymall.common.pojo.Success;
import com.easymall.common.vo.SysResult;
import com.netflix.loadbalancer.RandomRule;

import cn.edu.scnu.seckill.config.RabbitmqConfig;
import cn.edu.scnu.seckill.mapper.SucMapper;
import cn.edu.scnu.seckill.service.SecService;

@RestController
public class SecController {
	@Autowired
	private SecService secService;
	//查询所有可以秒杀的商品数据
	@RequestMapping("seckill/manage/list")
	public List<Seckill> queryAll(){
		return secService.queryAll();
	}
	
	//根据秒杀商品id查询详情信息
	@RequestMapping("seckill/manage/detail")
	public Seckill queryOne(String seckillId){
		return secService.queryOne(seckillId);
	}
	//接收秒杀商品的请求
	@Autowired
	private RabbitTemplate client;
	@RequestMapping("seckill/manage/{seckillId}")
	public SysResult startSeckill(@PathVariable Long seckillId){
		//确定用户身份，userId,username,userPhone
		//前端没有实现js限制未登录的秒杀，
		String userId ="180888"+RandomUtils.nextInt(10000,99999);
		//发送消息确定用户身份，秒杀商品信息
		String msg =userId +"/"+seckillId;
		client.convertAndSend(RabbitmqConfig.exName,RabbitmqConfig.routingKey,msg);
		return SysResult.ok();
	}
	//展示成功者
	@Autowired 
	private SucMapper sucMapper;
	//@RequestMapping("seckill/manage/{seckillId}/userPhone")
	//public List<Success> querySuccess(@PathVariable Long seckillId) {
		//return sucMapper.queryAllSuccess(seckillId);	}
}
