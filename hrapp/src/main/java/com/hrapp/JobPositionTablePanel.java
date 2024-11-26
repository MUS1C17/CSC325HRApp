/*package com.hrapp;

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
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class JobPositionTablePanel extends JPanel
{
    //Properties
    private JTable table;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> sorter;
    private JobPositionDAO jobPositionDAO;    

    //Constructor
    public JobPositionTablePanel() 
    {
        setLayout(new BorderLayout());

        // Column Names
        String[] columnNames = {"Position Name", "Hard Skill 1", "Hard Skill 2", "Soft Skill 1", "Soft Skill 2", "ID"};

        // Initialize the table model with column names and zero rows
        tableModel = new DefaultTableModel(columnNames, 0) 
        {
            // Make cells non-editable
            @Override
            public boolean isCellEditable(int row, int column) 
            {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) 
            {
                if(columnIndex == 5)
                {
                    return Integer.class;
                }

                return String.class;
            }
        };

        // Create the JTable with the model
        table = new JTable(tableModel);

        // Enable sorting
        sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);

        // Adjust column widths
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.getColumnModel().getColumn(0).setPreferredWidth(150); // Position Name
        table.getColumnModel().getColumn(1).setPreferredWidth(100); // Hard Skill 1
        table.getColumnModel().getColumn(2).setPreferredWidth(100); // Hard Skill 2
        table.getColumnModel().getColumn(3).setPreferredWidth(100); // Soft Skill 1
        table.getColumnModel().getColumn(4).setPreferredWidth(25); // Soft Skill 2
        table.getColumnModel().getColumn(5).setPreferredWidth(25);   //ID

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);

        // Add the scroll pane to the panel
        add(scrollPane, BorderLayout.CENTER);

         // Add MouseListener to the panel to detect clicks outside the table
        addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseClicked(MouseEvent e) 
            {
                // Get the component that was clicked
                Component clickedComponent = SwingUtilities.getDeepestComponentAt(JobPositionTablePanel.this,e.getX(), e.getY());

                // If the clicked component is not the table or a child of the table, clear selection
                if (!isDescendant(clickedComponent, table)) 
                {
                    table.clearSelection();
                }
            }
        });

        

        // Initialize JobPositionDAO and load data
        try 
        {
            jobPositionDAO = new JobPositionDAO();
            loadJobPositionData();
        } 
        catch (SQLException e) 
        {
            JOptionPane.showMessageDialog(this, "Error loading job position data: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Loads job position data from the database and populates the table.
     
    public void loadJobPositionData() throws SQLException 
    {
        // Clear existing data
        tableModel.setRowCount(0);

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
            tableModel.addRow(rowData);
            System.out.println(rowData);
        }
    }

    /**
     * Filters the table based on the search query.
     *
     * @param query The search string to filter the table.
     */
   /*  public void filterTable(String query) 
    {
        if (query.trim().length() == 0) 
        {
            sorter.setRowFilter(null);
        } 
        else 
        {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + query));
        }
    }

     /**
     * Helper method to determine if 'child' is a descendant of 'parent'.
     * 
     * @param child  The component that was clicked.
     * @param parent The parent component to check against.
     * @return true if 'child' is 'parent' or a descendant of 'parent', false otherwise.
     
    private boolean isDescendant(Component child, Component parent) 
    {
        if (child == null) 
        {
            return false;
        }
        if (child == parent) 
        {
            return true;
        }
        return isDescendant(child.getParent(), parent);
    }



}
*/