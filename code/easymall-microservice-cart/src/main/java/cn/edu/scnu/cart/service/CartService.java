package cn.edu.scnu.cart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.easymall.common.pojo.Cart;
import com.easymall.common.pojo.Product;

import cn.edu.scnu.cart.mapper.CartMapper;

@Service
public class CartService {
	@Autowired
	private CartMapper cartMapper;
	
	@Autowired
	private RestTemplate restTemplate;
	
	public List<Cart> queryMyCart(String userId) {
		return cartMapper.queryMyCart(userId);
	}
	public void cartSave(Cart cart) {
		// cart中!fuserid productid num
		Cart exist = cartMapper.queryOne(cart);
		//判断购物车是否已存在该商品
		if (exist != null) {
			// 表示已存在数
			// 将新增的num加上已存在的num更新到数帮库表格。exist作为参数。先更新exist对象的num
			exist.setNum(exist.getNum() + cart.getNum());
			cartMapper.updateNum(exist);
			// update t cart set num=#(num) where user id=#(userId) and product id=#(productIdi
		}else {// 不存在数,新增之前先要补充完整所有的name price imgurl
			//发起向商品中心调用服务的代码
			Product product = restTemplate
					.getForObject("http://productservice/product/manage/item/"
			+ cart.getProductId(), Product.class);
			cart.setProductPrice(product.getProductPrice());
			cart.setProductName(product.getProductName());
			cart.setProductImage(product.getProductImgurl());
			cartMapper.saveCart(cart);
		}
	}
	public void updateNum(Cart cart){
		cartMapper.updateNum(cart);
	}
	public void deleteCart(Cart cart) {
		cartMapper.deleteCart(cart);
	}
}
