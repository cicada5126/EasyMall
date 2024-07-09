package cn.edu.scnu.order.mapper;

import java.util.List;

import com.easymall.common.pojo.Order;
public interface OrderMapper {
	List<Order> queryOrder(String userId);
	void saveOrder(Order order);
	void deleteOrder(String orderId);
}
