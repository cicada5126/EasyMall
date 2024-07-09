package cn.edu.scnu.order.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.easymall.common.pojo.Order;
import com.easymall.common.vo.SysResult;

import cn.edu.scnu.order.mapper.OrderMapper;
import cn.edu.scnu.order.service.OrderService;

@RestController
@RequestMapping("/order/manage")
public class OrderController {
	@Autowired
	private OrderService orderService;
	
	@RequestMapping("query/{userId}")
	public List<Order> queryMyOrder(@PathVariable String userId){
		//根据用户useridch查阅我的订单数据
		/*js请求地址 http://www.easymall.com/order/query/{userId} 
		 * 后台接收/order/manage/query/{userId} 
		 * 请求方式get 
		 * 请求参数 径参数String userId 返回数据*List<Order>数据,
		 * 根据长度判断查询是否成功,如果长度为0可能是未登录
		 * 也可能是就是没有数据，
		 * 
		 */
		return orderService.queryMyOrder(userId);
	}
	//订单数据新增
	@RequestMapping("save")
	public SysResult saveOrder(Order order){
		try {
			orderService.saveOrder(order);
			return SysResult.ok();
			
		}catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(201, e.getMessage(), null);
			// TODO: handle exception
		}
	}

	@RequestMapping("delete/{orderId}")
	public SysResult deleteOrder(@PathVariable String orderId) {
		try {
			orderService.deleteOrder(orderId);
			return SysResult.ok();}
		catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(201,"", null);
		}
	}
}
