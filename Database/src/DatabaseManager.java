import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * This is a Helper class which provides helper methods for all transactions
 * 
 * @author Akshay
 *
 */
public class DatabaseManager {

	private Database database;

	public DatabaseManager(Database database) {
		this.database = database;
		createSavePoint();//create initial Save point (DB created)
	}

	/**
	 * This method supports BEGIN command on inovoking it will create a save
	 * point and store in transactionHelper
	 */
	public void beginTransaction() {
		createSavePoint();
	}
	/**
	 * ROLLBACK functinality 
	 * POP the latest trnsaction block out from stack
	 * 
	 * @return
	 */
	public boolean rollback() {
		return database.removeLatestDataBlock();
	}

	/**
	 * All the Datablocks Except Latest Daablock removed from stack
	 * 
	 * @return
	 */
	public boolean commit() {
		if (database.activeTransaction()) {
			DataBlock db = database.getLatestDataBlock();
			database.clearSavepoints();
			database.saveDataBlock(db);
			return true;
		}
		return false;
	}

	/**
	 * All non commited changes are lost' i.e. DB restored to original state
	 * Except the First element in Stack all other blocks cleared out
	 */
	public void end() {
		DataBlock db = database.getOriginalState();
		database.clearSavepoints();
		database.saveDataBlock(db);
	}

	/**
	 * This Method supports GET KEY functionality
	 * 
	 * @param key
	 * @return String-Value
	 */
	public String getValue(String key) {
		if (null != database.getLatestDataBlock() && null != database.getLatestDataBlock().getDatabaseMap()
				&& database.getLatestDataBlock().getDatabaseMap().containsKey(key)) {
			return String.valueOf(database.getLatestDataBlock().getDatabaseMap().get(key));
		}
		return new String("Error: Value not found for given key");
	}

	/**
	 * This supports SET KEY VALUE functionality
	 * 
	 * @param key
	 * @param value
	 */
	public void setValue(String key, int value) {
		if (null != database.getLatestDataBlock()) {
			HashMap<String, Integer> dbMap = database.getLatestDataBlock().getDatabaseMap();
			HashMap<Integer, Integer> counterMap = database.getLatestDataBlock().getValueConterMap();
			/** Removing Old value for the given key id present */
			if (dbMap.containsKey(key)) {
				int oldCount = counterMap.get(dbMap.get(key));
				counterMap.put(dbMap.get(key), oldCount--);
			}
			dbMap.put(key, value);// Setting Value
			/** Update Counter */
			if (null == counterMap.get(value)) {
				counterMap.put(value, new Integer(1));
			} else {
				int oldCount = counterMap.get(value);
				counterMap.put(value, oldCount++);
			}
		}
	}

	/**
	 * Supports UNSET functionality KEy-Value Pair is removed from HashMap
	 * 
	 * @param key
	 */
	public void unsetValue(String key) {
		HashMap<String, Integer> dbMap = database.getLatestDataBlock().getDatabaseMap();
		HashMap<Integer, Integer> counterMap = database.getLatestDataBlock().getValueConterMap();
		if (dbMap.containsKey(key)) {
			int oldCount = counterMap.get(dbMap.get(key));
			counterMap.put(dbMap.get(key), oldCount - 1);
			dbMap.remove(key);
		}
	}

	/**
	 * Method for creating save point
	 */
	public void createSavePoint() {
		database.saveDataBlock(new DataBlock());
	}
	/**
	 * Supports NUMEQUALTO
	 * @param value
	 * @return
	 */
	public int numberOfValues(int value) {
		if (database.getLatestDataBlock().getValueConterMap().containsKey(value)) {
			return database.getLatestDataBlock().getValueConterMap().get(value);
		}
		return new Integer(0);
	}

}
