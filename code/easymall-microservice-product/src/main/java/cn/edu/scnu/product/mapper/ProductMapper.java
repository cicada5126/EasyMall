package cn.edu.scnu.product.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.easymall.common.pojo.Product;

public interface ProductMapper {
	//查找商品总数量
	Integer queryTotal();
	//分页查询
	List<Product> productByPage(@Param("start")Integer start,@Param("rows") Integer rows);
	Product queryById(String productId);
	void productSave(Product product);
	//商品修改
	void productUpdate(Product product);

	

}
