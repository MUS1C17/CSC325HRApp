# CSC325HRApp

This is a document where we will keep the documentation of our project as well as any other notes that are needed for the success of our team!

# Quick Set Up Repository On Your Computer Guide
I use regular git instead of Desktop GitHub so all the instructions will be done for git.

1. When you are added to this repository as a collaborator you will need to clone this repository on your local machine. Open VSCode and inside VSCode open the folder where you will store the project.
2. Open the terminal inside the VSCode and write `git clone "repository link"`. You can find the repository link on the **<>Code** page after you click on the green **Code** button. Make sure to use HTTPS.
   ![image](https://github.com/user-attachments/assets/2513b98e-73e2-4520-8f6b-c2a7050551d3)
3. In the terminal run `cd CSC325HRApp`, this will navigate to the actual repository folder that will have all our classes and documents in it.
4. Run the `git status` command in the terminal and you should see this output:
   ![image](https://github.com/user-attachments/assets/a9bd5fbc-18f2-43f9-b2c3-5bbc5e66a9fd)
5. If you see this output then you have successfully completed this guide! Please do not make any changes to the file at this point because we will avoid making changes on the main branch in order to keep it always stable and workable.

# Development Flow
1. For every new feature or development task we will be creating a new branch based on the main branch. When naming a branch please follow this format `FirstLastname-<feature/task name>`. For example, when adding a new menu button the branch name wold be `MarkSharovarov-MenuButton`
2. In GitHub, navigate to the tab **Branch** and click **New Branch** button. Name your branch and choose main as the source branch.
   ![image](https://github.com/user-attachments/assets/79f4bbc1-c670-401c-9aed-7de9bba30d6f)
3. In VSCode, run `git fetch`. This command will fetch all new branches that were created on the remote git hub. Make sure you can see your branch in the output
   ![image](https://github.com/user-attachments/assets/5df7169e-9f7a-4f80-926d-041b32270c87)
4. Checkout in your branch by making by running `git checkout <branch name>"
   ![image](https://github.com/user-attachments/assets/942246c4-2143-4f03-85e1-772c08ac89ec)
5. At this point you can edit the files in the folder and work on what you need to. Please avoid working on any features that are different from what your main task requires you to do. That way it is easier for us to track all the changes that are being made and in case something breaks we know where and what to look for.
6. After you have done working on your branch, run `git status` to make sure git sees all the changes.
   ![image](https://github.com/user-attachments/assets/d3d9adbf-0872-4183-b8e2-609afadf5484)
8. Run `git add *` to add all the changes that you want to add. And after that run `git status` again to verify that files were added for git to commit. The files should be colored in green
   ![image](https://github.com/user-attachments/assets/e26bf096-46ac-4f47-a882-fba07185befc)
9. Now commit your changes, run `git commit -m "<short description of what have you done>"`. Sometimes, it's a good idea to do commits often if you are working on a difficult code and you will want to revert back to what you had earlier.
  ![image](https://github.com/user-attachments/assets/bdc21069-7113-421d-9a62-f43f756084a9)
10. Run `git push` to push all your changes to a remote repository.
11. Create a pull request in GitHub and add a description to explain what you have done. Only create a pull request if your code is working successfully, we want to always make sure the main branch works and is stable!
12. After the pull request is created, we will have several people review it and after that, it will be merged into the main branch. 


# SQLExecuter Class Documentation
## Overview
The SQLExecuter class is designed to simplify interactions with an SQLite database in your Java application. It provides methods to establish a connection, retrieve data, modify data, and close the connection when done. This class abstracts the underlying SQL operations, allowing you to work with the database without needing in-depth knowledge of SQL. This approach simplifies data management while keeping everything organized in one place. The database is made up of several tables, each storing data about specific categories. These tables are linked together to maintain relationships between different sets of information. Each table contains columns that hold specific details for each row of data. This is what the Employee table looks like: ![image](https://github.com/user-attachments/assets/ae1168a5-a351-4402-9a3b-87164d6eebab)

## How it works
### Database Connection
**Database Path:** The class constructs the path to the SQLite database file `testDB.db` located in the project database directory.
**Connection Object:** Uses a `Connection` object `java.sql.Connection` to establish and manage the connection to the database.

## Class Structure and Methods
### 1. Constructor: `public SQLExecuter() throws SQLException`
This constructor initializes a connection to the database when an instance of the SQLExecuter class is created. If your class needs to interact with the database, you must instantiate the SQLExecuter by calling its constructor. For example:
```java
public method()
{
      SQLExecuter nameOfThisInstance = new SQLExecuter();

      //Your code is here...
}
```

### 2. Method: `public ResultSet getDataFromDatabase(String query) throws SQLException`
* **Purpose**
   <br>The purpose of the `getDataFromDatabase(String query)` method is only to execute the `SELECT` query to retrieve data from the database. When we execute the `SELECT` query, data gets returned as a `ResultSet`. `ResultSet` is a table of data representing a database result set. It maintains a cursor pointing to its current row of data. Initially, the cursor is positioned before the first row. The `next()` method moves the cursor to the next row.
* **Parameters**<br>
 `String query`: The method takes an SQL String query as a parameter. For example, if you wanted to select all elements in the Employee table your method would look like this `getDataFromDatabase("SELECT * FROM Employee");`
### 3. Method: `public void setDataInDatabase(String query) throws SQLException`
* **Purpose**<br>
The purpose of the `public void setDataInDatabase(String query)` method is to execute all SQL queries that change data inside the database and do **NOT** return any data. This method will be used when using `INSERT`, `UPDATE`, or `DELETE` SQL statements to modify data.
   * **`INSERT` SQL Statement**: Adds a new row of data in the table. This SQL Statement will be used when the user is trying to **add** a new employee, job history, etc. For example, the following SQL Statement `INSERT INTO Employee (FirstName, LastName, Age) VALUES ('Tom', 'Smith', 19)` would add a new worker in the Employee table whose name is Tom, last name is Smith and age is 19.
   * **`UPDATE` SQL Statement**: Updates an existing row of data in the table. This SQL Statement will be used when the user is trying to **edit** existing employees, job history, etc. For example, the following SQL Statement `UPDATE Employee SET FirstName = 'Josh' WHERE LastName = 'Smith'` would change name of Tom Smith to Josh Smith assuming the example above.
   * **`DELETE` SQL Statement** Deletes existing rows of data in the table. We will try to avoid using this SQL statement as much as possible in our program because there is a chance that it can create bugs or duplication issues in our data. An example of the `DELETE` statement looks like this: `DELETE FROM Employee WHERE LastName = 'Smith'`. This query will delete **ALL** employees that have the last name Smith. <br>
   **IMPORTANT NOTE:** When the user chooses the option to delete an employee or job history we will use the `UPDATE` SQL statement. Each table has a column called `IsDeleted` that holds two values: 0 or 1. By default when a new employee is added to the database `IsDeleted` is set to 0 meaning the employee is not deleted and when `IsDeleted = 1`it indicates the employee is deleted. For example, when the user decides to delete Josh Smith from list of employees in the app, we will execute following statement: `UPDATE Employee SET IsDeleted = 1 WHERE FirstName = 'Josh' and LastName 'Smith'`.
* **Parameters**<br>
 `String query`: The method takes an SQL String query as a parameter. Any of the examples above can be used in this method, for example, `setDataInDatabase("UPDATE Employee SET FirstName = 'Josh' WHERE LastName = 'Smith'");`

### 4. Method: `public void closeConnection()`
* **Purpose**<br>
Closes the database connection to free up resources. This method **MUST** be used after all queries are executed.

 ### 4. **Usage Example**<be>
   Here is a simple example of how to use these methods in your code. Make sure to always execute all methods in the try-catch-finally statement. This will prevent our program from crashing in case we have issues with executing SQL queries. All execute queries methods should be in the try statement, while catch part and finally will most of the time will stay the same as in this example. Use this picture as a reference for the code below:![image](https://github.com/user-attachments/assets/7993d9ae-8794-41d7-8619-0e932598873b)

```java
   import java.sql.ResultSet;
   
   //Your code here...

   SQLExecuter executer = new SQLExecuter(); //Initialize Connection to the database

   ResultSet resultFromQuery = null; //Declare resultset variable before try-catch-finally that will hold in data
   String firstName, lastName, age; // Declare String variables

   try
   {
      resultFromQuery = executer.getDataFromDatabase("SELECT FirstName, LastName, Age FROM Employee WHERE EmployeeID = 1); //Get First name, last name and age for the employee with EmployeeId = 1. In this case, it is 38 year old Bob Smith
      if (executer.next()) //Checks if the first row of data exists and moves the cursor to that row
      {
         firstName = getString("FirstName"); //Get first name
         lastName = getString("LastName"); // get last name
         age = getString("Age"); //get age
      }

      executer.setDataInDatabase("UPDATE Employee SET FirstName = 'Josh' WHERE EmployeeID = 1"); //Change name from Bob to Josh
      executer.setDataInDatabase("INSERT INTO Employee (FirstName, LastName, Age) VALUES ('Tom', 'Duncan', 19)"); //Add a new employee Tom Duncan who is 19 years old. EmployeeID will increment automatically
   }
   catch(SQLExecption e)
   {
      e.getMessage(); //Outputs error message
   }
   finally
   {
      executer.closeConnection(); //Close connection to the database
   }

   //your code...
```






 



