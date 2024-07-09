package cn.edu.scnu.product.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.easymall.common.pojo.Product;
import com.easymall.common.utils.MapperUtil;
import com.easymall.common.utils.PrefixKey;
import com.easymall.common.vo.EasyUIResult;
import cn.edu.scnu.product.mapper.ProductMapper;
import redis.clients.jedis.JedisCluster;

@Service
public class ProductService {
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private JedisCluster jedis;
	public EasyUIResult productPageQuery(Integer page,Integer rows){
		//封装数据到EasyUIResult对象
		//1.创建一个返回的对象，将查询数据set进去然后返回
		EasyUIResult result=new EasyUIResult();
		//2.封装第一个属性total的数量
		Integer total=productMapper.queryTotal();
		//3.封装第二个属性List<Product>pList
		Integer start=(page-1)*rows;
		List<Product> pList=productMapper.productByPage(start,rows);
		//根据页数计算起始位置
		
		//4.封装对象属性
		result.setTotal(total);
		result.setRows(pList);
		return result;
	}
	public Product queryById(String productId) {
		// TODO Auto-generated method stub
		String productKey = "product_query_"+ productId;
		String lock="product_update_"+productId+".lock";
		try{
			//先判断锁是否存在
			if(jedis.exists(lock)){
				return productMapper.queryById(productId);			}
		//判断缓存是否存在数据
		if(jedis.exists(productKey)) {
			//缓存有数据 product]son
			String productJson = jedis.get(productKey);
			// 调用readValue 作用就是将ison转化为Product
			// writeValueAsString方法正好相反
			Product product = MapperUtil.MP.readValue(productJson, Product.class);
			return product;} 
		else {
			// 说明缓存没有商品的json字符申数据
			// 查数据库，并且要把查询的结果存到缓存供后续使用
			Product product = productMapper.queryById(productId);
			// product转化成string
			String productJson = MapperUtil.MP.writeValueAsString(product);
			// 存储缓存数据
			jedis.setex(productKey,60 * 60 *24 * 2, productJson);
			return product;
		}}
		catch (Exception e) {
			e.printStackTrace();
		return null;
		}
		
	}
	public void productSave(Product product) {
		// TODO Auto-generated method stub
		product.setProductId(UUID.randomUUID().toString());
		productMapper.productSave(product);
	}
	public void productUpdate(Product product) {
		// TODO Auto-generated method stub
		//跟新之前，对应商品缓存删除
		//鹏除缓存成功了,才能进行商品更新，否则用户查到旧数据
		//加锁解决并发数据缓存与数据库不一致的问题
		String lock="product_update "+product.getProductId()+".lock";
		//锁的超时时间多久 商品旧数据剩余时间
		Long leftTime = jedis.ttl(PrefixKey.PRODUCT_QUERY_PREFIX+product.getProductId());
		jedis.setex(lock,Integer.parseInt(leftTime+""),""); 
		// 更新前先删除缓存
		//删除缓存成功了,才能进行商品更新，否则用户查到旧数据
		jedis.del("product_query_"+product.getProductId());
		productMapper.productUpdate(product);
		
	}
}
