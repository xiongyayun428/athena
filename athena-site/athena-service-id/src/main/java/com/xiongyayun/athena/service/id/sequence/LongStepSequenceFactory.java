package com.xiongyayun.athena.service.id.sequence;

/**
 * <p><b>Long类型的序列号生成器</b></p>
 *
 * @author Yayun.Xiong
 * @date 2019-04-14
 */
public class LongStepSequenceFactory extends StepSequenceFactory {
	@Override
	public Long generate() {
		Object[] o = generate(1);
		StringBuffer sb = new StringBuffer();
		for (Object one : o) {
			sb.append(one);
		}
		return Long.valueOf(sb.toString());
	}
}
