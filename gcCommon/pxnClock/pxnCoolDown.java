package com.growcontrol.gcCommon.pxnClock;

import com.growcontrol.gcCommon.TimeU;
import com.growcontrol.gcCommon.TimeUnitTime;


public class pxnCoolDown {

	protected final TimeUnitTime coolTime;
	protected volatile long last = 0L;
	protected final Object lock = new Object();


	public static pxnCoolDown get(String time) {
		if(time == null || time.isEmpty()) throw new NullPointerException("time cannot be null!");
		return get(TimeUnitTime.Parse(time));
	}
	public static pxnCoolDown get(TimeUnitTime time) {
		if(time == null) throw new NullPointerException("time cannot be null!");
		return new pxnCoolDown(time);
	}
	protected pxnCoolDown(TimeUnitTime time) {
		if(time == null) throw new NullPointerException("time cannot be null!");
		this.coolTime = time;
	}


	public boolean Again() {
		pxnClock.getBlocking();
		long time = pxnClock.get().Millis();
		// run again
		if(time - last > coolTime.get(TimeU.MS)) {
			last = time;
			return true;
		}
		// cooling down
		return false;
	}


}
