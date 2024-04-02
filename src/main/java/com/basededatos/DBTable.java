package com.basededatos;

public abstract class DBTable {
	
	protected DBConnection conn = null;
	boolean DBSync;
	
	public DBTable(DBConnection conn, boolean DBSync){
		this.conn = conn;
		this.DBSync = DBSync;
	}
	
	public void setSync(boolean status) {
		if (status) 
			getEntryChanges();
		this.DBSync = status;
	}
	
	public abstract void destroy();	
	public abstract boolean createTable();
	public abstract boolean insertEntry();
	public abstract boolean updateEntry();
	public abstract void getEntryChanges();	
}