package com.xiongyayun.athena.service.id.support;

import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * <p><b>新的UUID生成器，利用系统<code>java.util.UUID</code>产生</b></p>
 *  线程安全
 *
 * @author Yayun.Xiong
 * @date 2019-04-14 17:01
 */
@Service
public class NewUUIDFactory extends AbstractIdFactory {
	/**
	 * 默认生成多少个
	 */
	@Setter
	private int step = 20;
	private List<String> uuid = new LinkedList<>();

	@Override
	public Object generate() {
		synchronized (this) {
			if (this.uuid.size() == 0) {
				for (int i = 0; i < this.step; i++) {
					this.uuid.add(UUID.randomUUID().toString());
				}
			}
			return this.uuid.remove(0);
		}
	}

	public static void main(String[] args) {
		NewUUIDFactory f = new NewUUIDFactory();
		for (int i = 0; i < 100; i++) {
			System.out.println(f.generate());
		}
	}
}
