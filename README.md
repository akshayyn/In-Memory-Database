# In-Memory-Database
This is Solution to Thumbstack's In memory database challenge
Turo Coding Assesment(Simple Database)

To run the solution in interactive mode: cd into Database folder >>cd into bin folder >> java DatabaseUI

To run the only test case come with the original problem: cd into SimpleDB folder >cd into bin folder  >>java DatabaseUI < test.txt

Approach>

Main database class contains of a Stack<DataBlock>
Each Datablock hold two HashMaps 
1. MapFor Holding name value pairs for main database functionality 
2. 2.Map> For holding all the unique Keys for a particular values(Set will avoid duplication) to give NUMEQUALTO functionality
The latest Database Map / ValueCounter Map is maintained in the Peek of the database,at the beggining of session or whenever BEGIN is encounterd new Datablock is pushed to the stack and all the contents of previous block copied to peek.

Following are data operations and its aproach- 
SET- if key not present already - new entry to Database Map if key not present- the value corresponding to key is updated/the key is removed from key set corresponding to value in value Counter Map if value already present/absent in valueCounter Map -key is added to the corresponding set(Set takes care of avoiding duplication)

UNSET- key-pair value removed from databaseMap the value corresponding to key is updated/the key is removed from key set corresponding to value in value Counter Map

NUMEQUALTO Return the value corresponding to the given value from valueCounter

GET -If value corresponding to key is present in Database Map is returned /else: error

For transactions such as BEGIN,COMMIT, ROLLBACK and END i have implmented Memento patter with a Stack which will hold all the save points(i.e Memento objects) 
-BEGIN- new save point will be created and pushed to Stack,Both Maps from previous peek copied to new block

-COMMIT- Stack will be cleared except peek

-ROLLBACK- One data block is Popped(latest transaction discarded.)

-END- Except first element clear the stack

