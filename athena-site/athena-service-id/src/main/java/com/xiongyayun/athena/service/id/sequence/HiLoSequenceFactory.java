package com.xiongyayun.athena.service.id.sequence;

/**
 * HiLoSequenceFactory
 *
 * @author Yayun.Xiong
 * @date 2019-04-14
 */
public class HiLoSequenceFactory extends TableSequenceFactory {
	private int step;
	private long hi;
	private int lo;
	private long timestamp;

	public HiLoSequenceFactory() {
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
		if (this.lo > this.step || (isDateCutoff() && getTable().getTimeService().isCutoff(5, this.timestamp))) {
			long[] tt = super.internalGenerate(1);
			long hival = tt[0];
			this.lo = 1;
			this.hi = (hival * this.step);
			this.timestamp = tt[1];
			this.log.info("new hi[type=" + getType() + ",step=" + this.step + "]: " + hival);
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
