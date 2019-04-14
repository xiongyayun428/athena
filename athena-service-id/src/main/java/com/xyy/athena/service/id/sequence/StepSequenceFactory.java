package com.xyy.athena.service.id.sequence;

/**
 * <p><b>根据步长生成序列号</b></p>
 *
 * @author XYY
 * @since 2015年4月26日
 * @since JDK 1.8
 *
 */
public class StepSequenceFactory extends TableSequenceFactory {
	private int step;
	private long hi;
	private int lo;
	private long timestamp;

	public StepSequenceFactory() {
		this.step = 10;
		this.hi = 0L;
		this.lo = (this.step + 1);
	}

	public void setStep(int i) {
		this.step = i;
		this.lo = (this.step + 1);
	}

	@Override
	protected synchronized long[] internalGenerate(int count) {
		if (count > 1) {
			throw new IllegalArgumentException("hi-lo sequence cannot generate more than 1 id once.");
		}
		if ((this.lo > this.step) || ((isDateCutoff()) && (getTable().getTimeService().isCutoff(5, this.timestamp)))) {
			// 新生产ID
			long[] tt = super.internalGenerate(this.step);
			this.lo = 1;
			this.hi = tt[0];
			this.timestamp = tt[1];
			this.log.info("new id [type=" + getType() + ", step=" + this.step + "]: " + tt[0]);
		}
		return new long[] { this.hi + this.lo++, this.timestamp };
	}
	
	@Override
	public synchronized void reverse() {
		if (!(this.lo > this.step) || ((isDateCutoff()) && (getTable().getTimeService().isCutoff(5, this.timestamp)))) {
			// 存在未使用完的ID
			int unused = step - lo + 1;
			super.internalReverse(unused);
		}
	}
}
