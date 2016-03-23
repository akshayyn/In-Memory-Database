import java.util.Stack;

/**
 * This is main database class Contains Stack of Datablocks,Peek will contain
 * latest changes
 * 
 * @author Akshay
 *
 */
public class Database {
	// Stack of dataBlocks(this is main database
	private final Stack<DataBlock> dataBaseStack;

	public Database() {
		this.dataBaseStack = new Stack<DataBlock>();// New DataBase created
	}

	/**
	 * Add a new datablock for each new Transaction/Beginning of session
	 * 
	 * @param dataBlock
	 */
	public void saveDataBlock(DataBlock dataBlock) {
		/*
		 * Copy the data from previous datablock to latest one and push on stack
		 */
		if (null != getLatestDataBlock()) {
			dataBlock.getDatabaseMap().putAll(getLatestDataBlock().getDatabaseMap());
			dataBlock.getValueConterMap().putAll(getLatestDataBlock().getValueConterMap());
		}
		dataBaseStack.push(dataBlock);
	}

	/**
	 * Returns Peek of the databaseStack
	 * 
	 * @return
	 */
	public DataBlock getLatestDataBlock() {
		if (null != dataBaseStack && !dataBaseStack.isEmpty() && null != dataBaseStack.peek()) {
			return dataBaseStack.peek();
		}
		return null;
	}

	/**
	 * Remove the top(Except first element)
	 * 
	 * @return
	 */
	public boolean removeLatestDataBlock() {
		if (null != dataBaseStack && !dataBaseStack.isEmpty() && dataBaseStack.size() > 1) {
			dataBaseStack.pop();
			return true;
		}
		return false;
	}

	public void clearSavepoints() {
		dataBaseStack.clear();
	}

	// Returns first element of DB
	public DataBlock getOriginalState() {
		return dataBaseStack.firstElement();
	}

	// This tells whether Stack has any active transactions
	public boolean activeTransaction() {
		if (null != dataBaseStack && !dataBaseStack.isEmpty() && dataBaseStack.size() > 1) {
			return true;
		}
		return false;
	}

}
