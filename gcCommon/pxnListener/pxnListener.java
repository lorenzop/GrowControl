package com.growcontrol.gcCommon.pxnListener;

import com.growcontrol.gcCommon.pxnListener.pxnEvent.EventPriority;


public abstract class pxnListener {

	protected EventPriority priority = EventPriority.NORMAL;


	// event priority
	public void setPriority(EventPriority priority) {
		if(priority == null) throw new NullPointerException("priority cannot be null!");
		this.priority = priority;
	}
	public boolean priorityEquals(EventPriority priority) {
		if(this.priority == null) throw new NullPointerException("this.priority cannot be null!");
		if(     priority == null) throw new NullPointerException("priority cannot be null!");
		return this.priority.equals(priority);
	}


	public abstract boolean onEvent(pxnEvent event);


}
