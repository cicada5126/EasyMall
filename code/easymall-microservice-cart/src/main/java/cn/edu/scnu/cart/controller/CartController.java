package cn.edu.scnu.cart.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easymall.common.pojo.Cart;
import com.easymall.common.vo.SysResult;

import cn.edu.scnu.cart.mapper.CartMapper;
import cn.edu.scnu.cart.service.CartService;

@RestController
@RequestMapping("cart/manage")
public class CartController {
	@Autowired
	private CartService cartService;
	@RequestMapping("query")
	public List<Cart> queryMyCart(String userId){
		//对userId进行判空
		if(!StringUtils.isNotEmpty(userId)){return null;}
		
		return cartService.queryMyCart(userId);
	}
	@RequestMapping("save")
	public SysResult cartSave(Cart cart){
		try {
			cartService.cartSave(cart);
			return SysResult.ok();
			
		}catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(201, "", null);
			// TODO: handle exception
		}
	}
	@RequestMapping("update")
	public SysResult updateNum(Cart cart) {
		try {
			cartService.updateNum(cart);
			return SysResult.ok();} 
		catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(201,"", null);
		}
	}
	@RequestMapping("delete")
	public SysResult deleteCart(Cart cart) {
		try {
			cartService.deleteCart(cart);
			return SysResult.ok();}
		catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(201,"", null);
		}
	}
}
