package vinos;

public abstract class DBTable {
	
	DBConnection conn = null;
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
	abstract boolean createTable();
	abstract boolean insertEntry();
	abstract boolean updateEntry();
//	abstract boolean deleteEntry();
	abstract void getEntryChanges();	
}