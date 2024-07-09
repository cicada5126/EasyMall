package cn.edu.scnu.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.easymall.common.pojo.Product;
import com.easymall.common.pojo.User;

public interface UserMapper {
	
	Integer queryUsername(String userName);
	void userSave(User user);
	User queryUserByNameAndPassword(User user);
}
