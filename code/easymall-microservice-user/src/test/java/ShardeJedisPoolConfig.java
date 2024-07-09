

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;
@Configuration
@ConfigurationProperties(prefix="spring.redis.shardedpool")
public class ShardeJedisPoolConfig {
	private List<String> nodes;
	private Integer maxTotal;
	private Integer maxIdle;
	private Integer minIdle;
	@Bean
	public ShardedJedisPool sjPoolInit() {
			// 利用属性完成连按池的初始化任务
			//第一步 收集节点信息
			List<JedisShardInfo> infolist = new ArrayList<JedisShardInfo>();
//			System.out.println(nodes);
			for (String node : nodes){
				
//				nodes="192.168.243.133:6379" "192.168 243.133:6380" "192.168,243.133:6381"
//				 node="192.168.243 133:6379"
				String ip = node.split(":")[0];
				int port = Integer.parseInt(node.split(":")[1]);
				infolist.add(new JedisShardInfo(ip, port));
				
			}
			//封装一个config，设置最大连按数最大空闲
			GenericObjectPoolConfig config = new GenericObjectPoolConfig();
			config.setMaxTotal(maxTotal);
			config.setMaxIdle(maxIdle);
			config.setMinIdle(minIdle);
			return new ShardedJedisPool(config,infolist);
	}
	public List<String> getNodes(){
		return nodes;
	}
	public Integer getMaxTotal() {
		return maxTotal;
	}
	public void setMaxTotal(Integer maxTotal) {
		this.maxTotal = maxTotal;
	}
	public Integer getMaxIdle() {
		return maxIdle;
	}
	public void setMaxIdle(Integer maxIdle) {
		this.maxIdle = maxIdle;
	}
	public Integer getMinIdle() {
		return minIdle;
	}
	public void setMinIdle(Integer minIdle) {
		this.minIdle = minIdle;
	}
	public void setNodes(List<String> nodes) {
		this.nodes = nodes;
	}
	
}

