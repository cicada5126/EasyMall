package cn.edu.scnu.seckill.consumer;
import java.util.Date;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.easymall.common.pojo.Success;
import cn.edu.scnu.seckill.config.RabbitmqConfig;
import cn.edu.scnu.seckill.mapper.SecMapper;
import cn.edu.scnu.seckill.mapper.SucMapper;
@Component
public class SeckillConsumer {
	@Autowired
	private SecMapper secMapper;
	@Autowired
	private SucMapper sucMapper;
// 声明式注解,底层实现异步监听
	@RabbitListener(queues = RabbitmqConfig.qName)
	public void processMsg(String msg) {
		// msg包含了用户信息,和商品信息
		// 根据商品信息,秒杀的减库存。
		// 将用户信息和秒杀商品入库保存作为成功秒杀数据 success
		// 将用户userId和seckillId分离开
		String userId = msg.split("/")[0];
		Long seckillId = Long.parseLong(msg.split("/")[1]);
		// 执行减库存 对当前商品的库存减1,条件number>0 nowTime<endTime,nowTime>startTime
		Date nowTime = new Date();
		int result = secMapper.updateNum(seckillId, nowTime);
		if (result == 0) {// 库存没有减成功
			// 说明卖完了,处理失败逻辑
			System.out.println(userId + "用户秒杀失败");
			return;
		}
		// 封装成功的数据success对象,新增到success表格
		Success success = new Success();
		success.setCreateTime(nowTime);
		success.setSeckillId(seckillId);
		success.setUserId(userId);
		success.setState(1);
		sucMapper.saveSuc(success);
	}
}