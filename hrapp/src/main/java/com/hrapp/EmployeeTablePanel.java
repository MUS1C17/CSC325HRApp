package com.hrapp;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
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

        // Set Selection Mode to Single Selection
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

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


         // Add MouseListener to the panel to detect clicks outside the table
         addMouseListener(new MouseAdapter() 
         {
            @Override
            public void mouseClicked(MouseEvent e) 
            {
                // Get the component that was clicked
                Component clickedComponent = SwingUtilities.getDeepestComponentAt(EmployeeTablePanel.this,e.getX(), e.getY());

                // If the clicked component is not the table or a child of the table, clear selection
                if (!isDescendant(clickedComponent, table)) 
                {
                    table.clearSelection();
                }
            }
        });

        // Add MouseListener to the scroll pane's viewport to handle clicks on empty space
        scrollPane.getViewport().addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseClicked(MouseEvent e) 
            {
                // Get the component that was clicked within the viewport
                Component clickedComponent = SwingUtilities.getDeepestComponentAt(scrollPane.getViewport(), e.getX(), e.getY());

                // If the clicked component is not the table or a child of the table, clear selection
                if (!isDescendant(clickedComponent, table)) 
                {
                    table.clearSelection();
                }
            }
        });

        // **Add MouseListener to the JTable for row clicks**
        table.addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseClicked(MouseEvent e) 
            {
                // Determine if the click was on a table row, not on the header
                int row = table.rowAtPoint(e.getPoint());
                int column = table.columnAtPoint(e.getPoint());

                // Get the table header
                JTableHeader header = table.getTableHeader();
                int headerHeight = header.getHeight();

                // If the click is within the header area, ignore
                if (e.getY() <= headerHeight) 
                {
                    return; // Click was on the header, do nothing
                }
            }
        });
    }

    /**
     * Helper method to determine if 'child' is a descendant of 'parent'.
     * 
     * @param child  The component that was clicked.
     * @param parent The parent component to check against.
     * @return true if 'child' is 'parent' or a descendant of 'parent', false otherwise.
     */
    private boolean isDescendant(Component child, Component parent) {
        if (child == null) {
            return false;
        }
        if (child == parent) {
            return true;
        }
        return isDescendant(child.getParent(), parent);
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
