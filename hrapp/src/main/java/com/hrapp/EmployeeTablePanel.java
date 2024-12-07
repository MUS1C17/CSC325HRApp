package com.hrapp;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import com.hrapp.EmployeeTablePanel.EmployeeSelectionListener;

/*
 * EmployeeTablePanel is a JPanel that displays two tables: one for employees and one for job positions.
 * It allows the user to interact with the tables by selecting rows, filtering data, and performing actions like viewing details, editing, or deleting records.
 * The panel uses two DAOs (EmployeeDAO, JobPositionDAO) to load data from a database and supports sorting, filtering, and mouse events for table interaction.
 * It also includes listener interfaces for handling employee and job position selection.
 */

public class EmployeeTablePanel extends JPanel
{
    // Properties for Employee Table
    private JTable employeeTable;
    private DefaultTableModel employeeTableModel;
    private TableRowSorter<DefaultTableModel> employeeSorter;
    private EmployeeDAO employeeDAO;
    private EmployeeSelectionListener employeeSelectionListener;
    private JobPositionSelectionListener jobPositionSelectionListener;

    // Properties for Job Position Table
    private JTable jobPositionTable;
    private DefaultTableModel jobPositionTableModel;
    private TableRowSorter<DefaultTableModel> jobPositionSorter;
    private JobPositionDAO jobPositionDAO;
    private MainApplication mainApp;


    //Interface for the EmployeeSelectionListener
    public interface EmployeeSelectionListener
    {
        void employeeSelected(Employee employee);
    }

    //Setter for the EmployeeSelectionListener
    public void setEmployeeSelectionListener(EmployeeSelectionListener listener)
    {
        this.employeeSelectionListener = listener;
    }

    //Interface for the JobPositionSelectionListener
    public interface JobPositionSelectionListener
    {
        void jobPositionSelected(JobPosition jobPosition);
    }

    public void setJobPositionSelectionListener(JobPositionSelectionListener listener)
    {
        this.jobPositionSelectionListener = listener;
    }

    //Constructor
    public EmployeeTablePanel(MainApplication mainApp)
    {
        this.mainApp = mainApp;
        setLayout(new BorderLayout());

        // Initialize DAOs
        try 
        {
            employeeDAO = new EmployeeDAO();
            jobPositionDAO = new JobPositionDAO();
        } 
        catch (SQLException e) 
        {
            JOptionPane.showMessageDialog(this, "Error initializing DAOs: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }

        // Create panels for each table
        JPanel employeeTablePanel = createEmployeeTablePanel();
        JPanel jobPositionTablePanel = createJobPositionTablePanel();

        // Create a panel to hold both tables vertically
        JPanel tablesPanel = new JPanel();
        tablesPanel.setLayout(new BoxLayout(tablesPanel, BoxLayout.Y_AXIS));

        // Add employee table panel
        tablesPanel.add(new Label("Employees", 32));
        tablesPanel.add(employeeTablePanel);
        // Add spacing between tables
        tablesPanel.add(Box.createVerticalStrut(20));
        // Add job position table panel
        tablesPanel.add(new Label("Positions", 32));
        tablesPanel.add(jobPositionTablePanel);

        

        // Add the tables panel to a scroll pane
        JScrollPane scrollPane = new JScrollPane(tablesPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // For smoother scrolling

        // Add the scroll pane to the main panel
        add(scrollPane, BorderLayout.CENTER);
    }
    
    //Method to create Employee Table when it gets updated
    private JPanel createEmployeeTablePanel()
    {
        JPanel panel = new JPanel(new BorderLayout());
        //Column Names
        String[] columnNames = {"ID", "First Name", "Last Name", "Email"};

        //Initialize the table model with column names and zero rows
        employeeTableModel  = new DefaultTableModel(columnNames, 0)
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
        employeeTable = new JTable(employeeTableModel);
        employeeTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));

        // Set font of items in JTable
        try 
        {
            Font montserrat = Font.createFont(Font.TRUETYPE_FONT, new File("resources\\fonts\\Montserrat\\static\\Montserrat-Bold.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(montserrat);
            employeeTable.setFont(montserrat.deriveFont(Font.PLAIN, 14));
        } 
        catch(IOException | FontFormatException e) 
        {
            e.printStackTrace();
        }

        // Set Selection Mode to Single Selection
        employeeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //Enable sorting
        employeeSorter  = new TableRowSorter<>(employeeTableModel);
        employeeTable.setRowSorter(employeeSorter);

        // Optional: Adjust column widths for better appearance
        employeeTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        employeeTable.getColumnModel().getColumn(0).setPreferredWidth(80);  // EmployeeID
        employeeTable.getColumnModel().getColumn(1).setPreferredWidth(100); // FirstName
        employeeTable.getColumnModel().getColumn(2).setPreferredWidth(100); // LastName
        employeeTable.getColumnModel().getColumn(3).setPreferredWidth(200); // Email

        // Set preferred viewport size to show 15 rows
        employeeTable.setPreferredScrollableViewportSize(new Dimension(employeeTable.getPreferredSize().width,
                      employeeTable.getRowHeight() * 15));

        //Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(employeeTable);


        //Initialize EMployeeDAO and load data
        try
        {
            loadEmployeeData();
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(this, "Error loading employee data: " + e.getMessage(),"Database Error", JOptionPane.ERROR_MESSAGE);
        }

        // Add the scroll pane to the panel
        panel.add(scrollPane, BorderLayout.CENTER);

         // Add MouseListener to the panel to detect clicks outside the table
        addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseClicked(MouseEvent e) 
            {
                // Get the component that was clicked
                Component clickedComponent = SwingUtilities.getDeepestComponentAt(EmployeeTablePanel.this,e.getX(), e.getY());

                // If the clicked component is not the table or a child of the table, clear selection
                if (!isDescendant(clickedComponent, employeeTable)) 
                {
                    employeeTable.clearSelection();
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
                if (!isDescendant(clickedComponent, employeeTable)) 
                {
                    employeeTable.clearSelection();
                }
            }
        });

        // **Add MouseListener to the JTable for row clicks**
        employeeTable.addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseClicked(MouseEvent e) 
            {
                // Determine if the click was on a table row, not on the header
                int row = employeeTable.rowAtPoint(e.getPoint());

                // If the click was on a row
                if (row != -1)
                {
                    // Single-click: just select the row (default behavior)
                    // Double-click: open the employee details
                    if (e.getClickCount() == 2 && !e.isConsumed())
                    {
                        e.consume(); // Mark the event as consumed
                        try
                        {
                            int modelRow = employeeTable.convertRowIndexToModel(row);
                            int employeeID = (Integer) employeeTableModel.getValueAt(modelRow, 0);

                            Employee emp = employeeDAO.getEmployeeDetails(employeeID);

                            if (employeeSelectionListener != null)
                            {
                                employeeSelectionListener.employeeSelected(emp);
                            }
                        }
                        catch (SQLException ex)
                        {
                            JOptionPane.showMessageDialog(EmployeeTablePanel.this, "Error retrieving employee details: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });

        return panel;
    }


    // Method to create the Job Position Table Panel
    private JPanel createJobPositionTablePanel() 
    {
        JPanel panel = new JPanel(new BorderLayout());

        // Column Names for Job Position Table
        String[] columnNames = {"Position Name", "Hard Skill 1", "Hard Skill 2", "Soft Skill 1", "Soft Skill 2", "ID"};

        // Initialize the table model
        jobPositionTableModel = new DefaultTableModel(columnNames, 0) 
        {
            // Make cells non-editable
            @Override
            public boolean isCellEditable(int row, int column) 
            {
                return false;
            }

            // Specify column classes
            @Override
            public Class<?> getColumnClass(int columnIndex) 
            {
                return String.class;
            }
        };

        // Create the JTable
        jobPositionTable = new JTable(jobPositionTableModel);
        jobPositionTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));

        // Set font of items in JTable
        try 
        {
            Font montserrat = Font.createFont(Font.TRUETYPE_FONT, new File("resources\\fonts\\Montserrat\\static\\Montserrat-Bold.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(montserrat);
            jobPositionTable.setFont(montserrat.deriveFont(Font.PLAIN, 14));
        } 
        catch(IOException | FontFormatException e) 
        {
            e.printStackTrace();
        }

        // Enable sorting
        jobPositionSorter = new TableRowSorter<>(jobPositionTableModel);
        jobPositionTable.setRowSorter(jobPositionSorter);

        // Adjust column widths
        jobPositionTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        jobPositionTable.getColumnModel().getColumn(0).setPreferredWidth(150); // Position Name
        jobPositionTable.getColumnModel().getColumn(1).setPreferredWidth(100); // Hard Skill 1
        jobPositionTable.getColumnModel().getColumn(2).setPreferredWidth(100); // Hard Skill 2
        jobPositionTable.getColumnModel().getColumn(3).setPreferredWidth(100); // Soft Skill 1
        jobPositionTable.getColumnModel().getColumn(4).setPreferredWidth(100); // Soft Skill 2
        jobPositionTable.getColumnModel().getColumn(5).setWidth(0); // set ID width to 0
        jobPositionTable.getColumnModel().getColumn(5).setMinWidth(0); // set minimum ID width to 0
        jobPositionTable.getColumnModel().getColumn(5).setMaxWidth(0); // set maximum ID width to 0
        jobPositionTable.getColumnModel().getColumn(5).setResizable(false); //Make not resizable

        // Set preferred viewport size to show 10 rows
        jobPositionTable.setPreferredScrollableViewportSize(new Dimension(jobPositionTable.getPreferredSize().width,
            jobPositionTable.getRowHeight() * 10));


        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(jobPositionTable);

        // Add the scroll pane to the panel
        panel.add(scrollPane, BorderLayout.CENTER);

        // Load job position data
        try 
        {
            loadJobPositionData();
        } 
        catch (SQLException e) 
        {
            JOptionPane.showMessageDialog(this, "Error loading job position data: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }

        // **Add MouseListener to the JTable for row clicks**
        jobPositionTable.addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseClicked(MouseEvent e) 
            {
                // Determine if the click was on a table row, not on the header
                int row = jobPositionTable.rowAtPoint(e.getPoint());
  
                // If the click was on a row
                if (row != -1)
                {
                    // Single-click: just select the row (default behavior)
                    // Double-click: open the employee details
                    if (e.getClickCount() == 2 && !e.isConsumed())
                    {
                        e.consume(); // Mark the event as consumed
                        try
                        {
                            int modelRow = jobPositionTable.convertRowIndexToModel(row);
                            int jobPositionID = (Integer) jobPositionTableModel.getValueAt(modelRow, 5);
  
                            String[] contactOptions = {"Delete", "Edit", "Cancel"};

                            int choice = JOptionPane.showOptionDialog(EmployeeTablePanel.this, 
                                "What would you like to do?", 
                                "Choose One Option",
                                JOptionPane.DEFAULT_OPTION,   // Option type
                                JOptionPane.INFORMATION_MESSAGE,
                                null,
                                contactOptions,
                                contactOptions[0]);

                            //If choice is Delete, then delete job position
                            if(choice == 0)
                            {

                                //Only let managers/CEO delete Job Postion
                                if(mainApp.isCurrentUserCEO() || mainApp.isCurrentUserManager())
                                {
                                    jobPositionDAO.deleteJobPosition(jobPositionID);
                                    loadJobPositionData();
                                }
                                else
                                {
                                    JOptionPane.showMessageDialog(EmployeeTablePanel.this, "You don't have rights to delete a job Position", "Permission Issue", JOptionPane.WARNING_MESSAGE);
                                }
                            }
                            //If choice is Edit, then open Edit Job Position Panel
                            else if(choice == 1)
                            {
                                //Only let managers/CEO edit jbo postion
                                if(mainApp.isCurrentUserCEO() || mainApp.isCurrentUserManager())
                                {
                                    JobPosition position = jobPositionDAO.getJobPositionDetails(jobPositionID);
        
                                    if (jobPositionSelectionListener != null)
                                    {
                                        jobPositionSelectionListener.jobPositionSelected(position);
                                    }
                                }
                                else
                                {
                                    JOptionPane.showMessageDialog(EmployeeTablePanel.this, "You don't have rights to edit a job Position", "Permission Issue", JOptionPane.WARNING_MESSAGE);
                                }
                            }

                            //For cancel option no code needed since it will close the pop up by default
                        }
                        catch (SQLException ex)
                        {
                            JOptionPane.showMessageDialog(EmployeeTablePanel.this, "Error retrieving Job Pposition details: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        }); 

        // Add MouseListener to the panel to detect clicks outside the table
        addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseClicked(MouseEvent e) 
            {
                // Get the component that was clicked
                Component clickedComponent = SwingUtilities.getDeepestComponentAt(EmployeeTablePanel.this,e.getX(), e.getY());

                // If the clicked component is not the table or a child of the table, clear selection
                if (!isDescendant(clickedComponent, jobPositionTable)) 
                {
                    jobPositionTable.clearSelection();
                }
            }
        });

        return panel;
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
        employeeTableModel.setRowCount(0);

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
            employeeTableModel.addRow(rowData);
        }
    }

    // Method to load job position data
    public void loadJobPositionData() throws SQLException 
    {
        // Clear existing data
        jobPositionTableModel.setRowCount(0);

        List<JobPosition> jobPositions = jobPositionDAO.getAllJobPositions();
        for (JobPosition job : jobPositions) 
        {
            Object[] rowData = 
            {
                    job.getJobPositionName(),
                    job.getHardSkill1(),
                    job.getHardSkill2(),
                    job.getSoftSkill1(),
                    job.getSoftSkill2(),
                    job.getJobPositionID()
            };
            jobPositionTableModel.addRow(rowData);
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
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow != -1) 
        {
            int modelRow = employeeTable.convertRowIndexToModel(selectedRow);
            int employeeID = (Integer) employeeTableModel.getValueAt(modelRow, 0);

            Employee emp = employeeDAO.getEmployeeDetails(employeeID);
            return emp;
        }
        return null;
    }

    /**
     * Retrieves the currently selected JobPosition data.
     * 
     * @return A JobPosition object representing the selected employee, or null if no selection.
     */
    public JobPosition getSelectedJobPostion() throws SQLException
    {
        int selectedRow = jobPositionTable.getSelectedRow();
        if (selectedRow != -1) 
        {
            int modelRow = jobPositionTable.convertRowIndexToModel(selectedRow);
            int jobPostionID = (Integer) jobPositionTable.getValueAt(modelRow, 5);

            JobPosition positon = jobPositionDAO.getJobPositionDetails(jobPostionID);
            return positon;
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
            employeeSorter.setRowFilter(null);
        }
        else
        {
            employeeSorter.setRowFilter(RowFilter.regexFilter("(?i)" + query));
        }
    }

    // Method to filter the job position table
    public void filterJobPositionTable(String query) 
    {
        if (query.trim().length() == 0) 
        {
            jobPositionSorter.setRowFilter(null);
        } 
        else 
        {
            jobPositionSorter.setRowFilter(RowFilter.regexFilter("(?i)" + query));
        }
    }

    // Getter method to get employeeTable
    public JTable getEmployeeTable() 
    {
        return employeeTable;
    }

    //Getter method to get JobPositionTable
    public JTable getJobPositionTable() 
    {
        return jobPositionTable;
    }

}
