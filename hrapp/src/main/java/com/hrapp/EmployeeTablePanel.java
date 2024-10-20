package com.hrapp;

import java.awt.BorderLayout;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class EmployeeTablePanel extends JPanel
{
    //Properties
    private JTable table;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> sorter;
    private EmployeeDAO employeeDAO;

    public EmployeeTablePanel()
    {
        setLayout(new BorderLayout());

        //Column Names
        String[] columnNames = {"ID", "First Name", "Last Name", "Email"};

        //Initialize the table model with column names and zero rows
        tableModel = new DefaultTableModel(columnNames, 0)
        {
            //Make cells non-editable
            @Override
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) 
            {
                switch (columnIndex) {
                    case 0:
                        return Integer.class;  // EmployeeID
                    case 1:
                    case 2:
                    case 3:
                        return String.class;   // FirstName, LastName, Email
                    default:
                        return Object.class;
                }
            }
        };

        //Create the Jtable with the model
        table = new JTable(tableModel);

        //Enable sorting
        sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);

        // Optional: Adjust column widths for better appearance
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.getColumnModel().getColumn(0).setPreferredWidth(80);  // EmployeeID
        table.getColumnModel().getColumn(1).setPreferredWidth(100); // FirstName
        table.getColumnModel().getColumn(2).setPreferredWidth(100); // LastName
        table.getColumnModel().getColumn(3).setPreferredWidth(200); // Email

        //Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);

        //Add the scroll pane to the panel
        add(scrollPane, BorderLayout.CENTER);

        //Initialize EMployeeDAO and load data
        try
        {
            employeeDAO = new EmployeeDAO(); //Connects to the database
            loadEmployeeData();
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(this, "Error loading employee data: " + e.getMessage(),"Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

     /**
     * Loads employee data from the database and populates the table.
     */
    public void loadEmployeeData() throws SQLException
    {
        //Clear exisitng data
        tableModel.setRowCount(0);

        List<Employee> employees = employeeDAO.getAllEmployees();
        for (Employee emp : employees)
        {
            Object[] rowData = 
            {
                emp.getEmployeeID(),
                emp.getFirstName(),
                emp.getLastName(),
                emp.getEmail()
            };
            tableModel.addRow(rowData);
        }
    }

    /**
     * Adds a new employee to the table and the database.
     * 
     * @param employee The Employee object to add.
     */
    public void addEmployee(Employee employee)
    {
        try
        {
            //Add to database
            employeeDAO.addEmployee(employee);

            //Refresh the table data
            loadEmployeeData();
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(this, "Error adding employee: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Retrieves the currently selected employee data.
     * 
     * @return An Employee object representing the selected employee, or null if no selection.
     */
    public Employee getSelectedEmployee() throws SQLException
    {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) 
        {
            int modelRow = table.convertRowIndexToModel(selectedRow);
            int employeeID = (Integer) tableModel.getValueAt(modelRow, 0);

            Employee emp = employeeDAO.getEmployeeDetails(employeeID);
            //table.clearSelection();
            return emp;
        }
        return null;
    }

    /**
     * Closes the DAO connection when the panel is no longer needed.
     */
    public void closeDAO() {
        if (employeeDAO != null) {
            employeeDAO.close();
        }
    }

    /**
     * Filters the table based on the search query.
     * 
     * @param query The search string to filter the table.
     */
    public void filterTable(String query)
    {
        if(query.trim().length() == 0)
        {
            sorter.setRowFilter(null);
        }
        else
        {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + query));
        }
    }

    public JTable getTable() 
    {
        return table;
    }

}