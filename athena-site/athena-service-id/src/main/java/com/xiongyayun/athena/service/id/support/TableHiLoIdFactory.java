package com.xiongyayun.athena.service.id.support;

import org.springframework.stereotype.Service;

/**
 * TableHiLoIdFactory
 *
 * @author Yayun.Xiong
 * @date 2019-04-14 16:51
 */
@Service
public class TableHiLoIdFactory extends TableIdFactory {
	private int step = 10;
	private long hi = 0L;
	private int lo = this.step + 1;

	public void setStep(int i) {
		this.step = i;
		this.lo = (this.step + 1);
	}

	@Override
	protected synchronized long internalGenerate() {
		if (this.lo > this.step) {
			long hival = super.internalGenerate();
			this.lo = 1;
			this.hi = (hival * this.step);
			log.info("new hi[type=" + this.type + ",step=" + this.step + "]: " + hival);
		}
		return this.hi + this.lo++;
	}
}
