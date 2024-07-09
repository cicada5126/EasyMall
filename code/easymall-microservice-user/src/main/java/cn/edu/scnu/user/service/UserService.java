package cn.edu.scnu.user.service;

import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.easymall.common.pojo.User;
import com.easymall.common.utils.MD5Util;
import com.easymall.common.utils.MapperUtil;
import com.fasterxml.jackson.databind.ObjectMapper;


import cn.edu.scnu.user.mapper.UserMapper;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;


@Service
public class UserService {
	@Autowired
	private UserMapper userMapper;
	
	
//	@Autowired
//	private ShardedJedisPool pool;
	private ObjectMapper mapper=MapperUtil.MP;
	
	
	@Autowired
	private JedisCluster jedis;
	
	public Integer checkUsername(String userName) {
		return userMapper.queryUsername(userName);
	}
	public void userSave(User user){

		//user的数据填写完整 userId password加密
		user.setUserId(UUID.randomUUID().toString());
		user.setUserPassword(MD5Util.md5(user.getUserPassword()));
		//insert 数据到数据库
		userMapper.userSave(user);
	}
	public String doLogin(User user){
		// 利用user数据查询数据库，判断登录是否合法
//		ShardedJedis jedis = pool.getResource();
		try{
			user.setUserPassword(MD5Util.md5(user.getUserPassword()));
			User exist = userMapper.queryUserByNameAndPassword(user);
			if (exist == null) {// 登录失败,没有数据可以存储在redis
				return "";//返同一个"")
			}else {//为了后续访向能够获取到user对象的数据,需要建一个kev值,
				//将userJson作为value存储在redis中
				//key值返回给前端
				//前端页面下次访问就可以携带生成一个cookie将要携带回去的ticket
					String ticket = UUID.randomUUID().toString();
					//jackson的代码。将已存在的exist用户对象转化成json
					
					String userJson;
					
					try {
						userJson = mapper.writeValueAsString(exist);
						
						//判断当前用户是否曾经有人登录过
						String existTicket=jedis.get("user_logined_"+exist.getUserId());
//						//顶替实现.不允许前一个登录的人ticket存在
						if(StringUtils.isNotEmpty(existTicket)){
							jedis.del(existTicket);
						}
//						//定义当前客户端登录的信息 userId:ticket
						jedis.setex("user_logined_"+exist.getUserId(), 20,ticket);
						//用户登录超时30分钟
						jedis.setex(ticket, 60*1, userJson);
					}catch (Exception e) {e.printStackTrace();
						return "";
					}
					//将userJson存储在redis中Jedis 
					
					jedis.set(ticket, userJson);
					return ticket;
				}
		}catch (Exception e) {e.printStackTrace();
		return "";
	}
//		finally {
//		pool.returnResource(jedis);
//	}
		
		}
	//从redis中获取userJson
	public String queryUserJson(String ticket) {
//		ShardedJedis jedis = pool.getResource();
		String userJson ="" ;
		try {
			//首先判断超时剩余时间
			Long leftTime = jedis.pttl(ticket);
			//少于10分钟，延长5分钟
			if(leftTime<1000*60*101){
				jedis.pexpire(ticket, leftTime+1000*60*5);
			}		
		userJson = jedis.get(ticket);
		return userJson;}
		catch (Exception e) {
			e.printStackTrace();
			return "";
		}
//		finally {
//			pool.returnResource( jedis);
//		}
		
	}
	
	
}
