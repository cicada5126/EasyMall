package cn.edu.scnu.seckill.mapper;



import java.util.Date;
import java.util.List;

import org.springframework.data.repository.query.Param;

import com.easymall.common.pojo.Seckill;


public interface SecMapper {
	List<Seckill> queryAll();
	public Seckill queryOne(long seckillId);
	public int updateNum(@Param("seckillId")Long seckillId, @Param("nowTime")Date nowTime);
}
