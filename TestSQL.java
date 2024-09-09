import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestSQL {
    public static void main(String[] args) {

        try
        {
            Class.forName("org.sqlite.JDBC");
        }
        catch(ClassNotFoundException error)
        {
            error.printStackTrace();
        }
        String pathToDataBase = "jdbc:sqlite:C:U\\sers\\Mark\\Desktop\\Рабочий стол\\USA\\Murray State University\\Fall 2024\\CSC 325\\HRApp\\CSC325HRApp\\database\\testDB.db";
        try
        {
            
            Connection connection = DriverManager.getConnection(pathToDataBase);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        /* 
        // Create an instance of SQLExecuter
        SQLExecuter testExecute = null;
        ResultSet resultFromQuery = null;

        try
        {
            testExecute = new SQLExecuter();
            resultFromQuery = testExecute.executeSQLQuery("select * from TestTable");
            System.out.println("Result is: " + resultFromQuery);
        }
        catch(SQLException e)
        {
            // Handle any SQL exceptions
            e.getMessage();
        }
        finally
        {
            testExecute.closeConnection();
        }*/
    }    

      
}
