package com.growcontrol.gcCommon.pxnListener;

import java.util.ArrayList;
import java.util.List;

import com.growcontrol.gcCommon.pxnListener.pxnEvent.EventPriority;
import com.growcontrol.gcCommon.pxnLogger.pxnLog;


public class pxnListenerGroup {

	protected List<pxnListener> listeners = new ArrayList<pxnListener>();


	// register listener
	public void register(pxnListener listener) {
		if(listener == null) throw new NullPointerException("listener cannot be null!");
pxnLog.get().fine("Registered listener: ("+Integer.toString(listeners.size())+") "+listener.toString());
		synchronized(listeners) {
			if(!listeners.contains(listener))
				listeners.add(listener);
		}
	}


	// trigger event
	public boolean triggerEvent(pxnEvent event, EventPriority onlyPriority) {
		if(event        == null) throw new NullPointerException("event cannot be null!");
		if(onlyPriority == null) throw new NullPointerException("onlyPriority cannot be null!");
		synchronized(listeners) {
			// loop listeners
			for(pxnListener listener : listeners)
				if(listener.priorityEquals(onlyPriority))
					if(listener.onEvent(event))
						event.setHandled();
		}
		return event.isHandled();
	}


}
