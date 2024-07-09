package cn.edu.scnu.seckill.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.easymall.common.pojo.Seckill;

import cn.edu.scnu.seckill.mapper.SecMapper;

@Service
public class SecService {
	@Autowired
	private SecMapper secMapper;
	public List<Seckill> queryAll() {
		return secMapper.queryAll();
	}
	public Seckill queryOne(String seckillId){
		long seckillId_long=Long.parseLong(seckillId);
		return secMapper.queryOne(seckillId_long);
	}
}
