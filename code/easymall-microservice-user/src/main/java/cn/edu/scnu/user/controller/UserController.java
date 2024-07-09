package cn.edu.scnu.user.controller;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easymall.common.pojo.User;
import com.easymall.common.utils.CookieUtils;
import com.easymall.common.vo.SysResult;

import cn.edu.scnu.user.service.UserService;
@RestController
public class UserController {
	@Autowired
	private UserService userService;
	@RequestMapping("user/manage/checkUserName")
	public SysResult checkUsername(String userName){
		// 1.检查输入的用户是否存在
		Integer exist=userService.checkUsername(userName);//exist判断返回结果
		if(exist==0){//不存在就是可用数据return 
			return SysResult.ok();}
		else{return SysResult.build(201,"己存在",null);
		}
	}
	@RequestMapping("user/manage/save")
	public SysResult userSave(User user){
		// 1.检查输入的用户是否存在
		Integer a = userService.checkUsername(user .getUserName());
		if (a > 0)return SysResult.build(201,"用户名已存在",null);
		//2。注册
		try{
			userService.userSave(user);
			return SysResult.ok();
		}catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(201, e.getMessage(), null);
		}
		
	}
	//用户登录校验功能，校验成功将数据保存在redis供后续访问
	@RequestMapping("user/manage/login")
	public SysResult doLogin(User user, HttpServletRequest request, 
			HttpServletResponse response) {
			// 调用业务层确定合法并且存储数据
			String ticket = userService.doLogin(user);
			// 控制层将业务层存储登录成功的rediskey值
			// !"",equals(ticket)&&ticket!=null
			if (StringUtils.isNotEmpty(ticket)) {
				// ticket非空，表示redis已经存好登录的查询结果
				// 将ticket作为cookie的值返四，cookie的名称根据接口文件的规定来定义
				//调用CookieUtils工具类 ，将ticket添加到cookie返回纷前端
				CookieUtils.setCookie(request,response,"EM_TICKET", ticket);
				return SysResult.ok(); }
			else {
				//直接返回番录失败
				return SysResult.build(201,"登录失败",null);
			}
			
	}
	@RequestMapping("/user/manage/query/{ticket}")
	public SysResult checkLoginUser(@PathVariable String ticket){
		String userJson = userService.queryUserJson(ticket);// 判断非空
		if (StringUtils.isNotEmpty(userJson)) {//确实曾经登录过。也正在登录使用状态中
			return SysResult.build(200,"ok", userJson);
		}else {
			return SysResult.build(201,"", null);
		}
				
	}
	

}