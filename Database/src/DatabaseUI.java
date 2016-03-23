import java.util.Scanner;

/**This class acts like a front End Controller
 * Takes Command from the Console/File with commands
 * Parses the command and passes to main Database class 
 * to perform operations
 * Supported Trasaction operations- BEGIN,ROLLBACK,END
 * Supported data transactions SET,GET.UNSET,NUMEQUALTO*/


public class DatabaseUI {
	public static void main(String[] args) {
		DatabaseManager db = new DatabaseManager(new Database());
		System.out.println("Welcome 2 In-memory Database System");
		Scanner scanner = new Scanner(System.in);
		scanner.useDelimiter("\\s+"); // delimiter- <Space>
		String commandLine;
		while (scanner.hasNextLine()) {
			commandLine = scanner.nextLine();
			String[] commandArray = commandLine.split("\\s+");
			String command = commandArray[0];
			String name;
			Integer value;
			try {
				switch (command) {
				case "GET":
					name = commandArray[1];
					
					System.out.println(db.getValue(name) != null ? db.getValue(name):"NULL");
					break;
				case "SET":
					name = commandArray[1];
					value = Integer.parseInt(commandArray[2]);
					db.setValue(name, value);
					
					break;
				case "UNSET":
					name = commandArray[1];
					db.unsetValue(name);
					
					break;
				case "NUMEQUALTO":
					value = Integer.parseInt(commandArray[1]);
					System.out.println(db.numberOfValues(value));
					break;
				case "BEGIN":
					db.beginTransaction();
					break;
				case "ROLLBACK":
					if (!db.rollback()) System.out.println("NO TRANSACTION");
					break;
				case "COMMIT":
					if (!db.commit()) System.out.println("NO TRANSACTION");
					break;					
				case "END":
					db.end();
					return;
				case "":
					break;
				default:
					System.out.println("Invalid command: " + command );
				}
			} catch (NumberFormatException e) {			// SET n a
				System.out.println("Invalid number format: " + commandLine );
			} catch (ArrayIndexOutOfBoundsException e) {// GET
				System.out.println("Missing Operand: " + commandLine );
			}
		}
		scanner.close();
	}
}
