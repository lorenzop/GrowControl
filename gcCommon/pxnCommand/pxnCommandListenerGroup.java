package com.growcontrol.gcCommon.pxnCommand;

import com.growcontrol.gcCommon.pxnListener.pxnEvent.EventPriority;
import com.growcontrol.gcCommon.pxnListener.pxnListener;
import com.growcontrol.gcCommon.pxnListener.pxnListenerGroup;


public class pxnCommandListenerGroup extends pxnListenerGroup {
	protected pxnCommandListenerGroup() {}
	@Override
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

	// main listener
	private static volatile pxnCommandListenerGroup listener = null;
	private static final Object lock = new Object();


	public static pxnCommandListenerGroup get() {
		if(listener == null) {
			synchronized(lock) {
				if(listener == null)
					listener = new pxnCommandListenerGroup();
			}
		}
		return listener;
	}


	// trigger event
	public boolean triggerCommandEvent(String line) {
		if(line == null) throw new NullPointerException("line cannot be null!");
		boolean result = false;
		if(triggerCommandEventPriority(line, EventPriority.HIGHEST)) result = true;
		if(triggerCommandEventPriority(line, EventPriority.HIGH   )) result = true;
		if(triggerCommandEventPriority(line, EventPriority.NORMAL )) result = true;
		if(triggerCommandEventPriority(line, EventPriority.LOW    )) result = true;
		if(triggerCommandEventPriority(line, EventPriority.LOWEST )) result = true;
		return result;
	}
	// trigger listeners by priority
	protected boolean triggerCommandEventPriority(String line, EventPriority onlyPriority) {
		if(line         == null) throw new NullPointerException("line cannot be null!");
		if(onlyPriority == null) throw new NullPointerException("onlyPriority cannot be null!");
		boolean result = false;
		synchronized(listeners) {
			// loop listeners
			for(pxnListener listener : listeners) {
				if(!(listener instanceof pxnCommandsHolder)) continue;
				if(!listener.priorityEquals(onlyPriority)) continue;
				pxnCommandsHolder commandListener = (pxnCommandsHolder) listener;
				// find command
				String commandStr = commandListener.FindCommand(line);
				// command/alias not found
				if(commandStr == null || commandStr.isEmpty()) continue;
				pxnCommand command = commandListener.getCommand(commandStr);
				// new event
				pxnCommandEvent event = pxnCommandEvent.newEvent(line, command.clone());
				if(event == null) continue;
				if(result) event.setHandled();
				// run event
				if(commandListener.onCommand(event))
					event.setHandled();
				if(event.isHandled()) result = true;
			}
		}
		return result;
	}


}
