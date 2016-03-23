
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * This is a Trasaction Block
 * 
 * @author Akshay Nakhawa
 *
 */
public class DataBlock {
	/**
	 * Copy of DB map
	 */
	private HashMap<String, Integer> databaseMap;
	// Copy of valueCounterMap
	private  HashMap<Integer, Integer> valueConterMap;

	public HashMap<String, Integer> getDatabaseMap() {
		return databaseMap;
	}

	public HashMap<Integer, Integer> getValueConterMap() {
		return valueConterMap;
	}

	/**
	 * Create new block for each transaction
	 * 
	 * @param databaseMap
	 * @param valueCounterMap
	 */
	public DataBlock() {
		this.databaseMap = new HashMap<>();
		this.valueConterMap = new HashMap<>();
	}

}
