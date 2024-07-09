package cn.edu.scnu.order.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.easymall.common.pojo.Cart;
import com.easymall.common.pojo.Order;
import com.easymall.common.pojo.Product;

import cn.edu.scnu.order.mapper.OrderMapper;

@Service
public class OrderService {
	@Autowired
	private OrderMapper orderMapper;
	
	
	public void saveOrder(Order order) {
		//补充数据
		order.setOrderId(UUID.randomUUID().toString());
		order.setOrderTime(new Date());
		orderMapper.saveOrder(order);
	}
	public void deleteOrder(String orderId) {
		orderMapper.deleteOrder(orderId);
	}
	public List<Order> queryMyOrder(String userId) {
		return orderMapper.queryOrder(userId);
	}
}
