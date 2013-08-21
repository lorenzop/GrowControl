package com.growcontrol.gcCommon.meta;


public abstract class metaValue implements java.io.Serializable {
	private static final long serialVersionUID = 9L;

	public abstract metaValue clone();
	public abstract metaType getType();

	public abstract String getString();
	public abstract void set(String value);


	// new meta value
	public static metaValue newValue(String typeStr) {
		return newValue(typeStr, null);
	}
	public static metaValue newValue(metaType type) {
		return newValue(type, null);
	}
	public static metaValue newValue(String typeStr, String value) {
		metaType type = metaType.Find(typeStr);
		return newValue(type, value);
	}
	public static metaValue newValue(metaType type, String value) {
		if(type == null || type.factory == null)
			return null;
		if(value == null || value.isEmpty())
			return null;
		return type.factory.newValue(value);
	}


	// event handled
	private volatile Boolean handled = false;
	public boolean handled() {
		return handled;
	}
	public boolean handled(boolean handled) {
		boolean oldHandled;
		synchronized(this.handled) {
			oldHandled = this.handled;
			this.handled = handled;
		}
		return oldHandled;
	}


	// send to meta router
	public void Send(String toName) {
		metaRouter.get().Send(toName, this);
	}


}
