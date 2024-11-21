package com.hrapp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
 * This class will handle all CRUD (Create, Read, Update, Delete) operations for 
 * the JobPostion entity using the enhanced SQLExecuter
 */

public class JobPositionDAO 
{
    private SQLExecuter executer;

    //Constructor
    public JobPositionDAO() throws SQLException
    {
        executer = new SQLExecuter();
    }

    /**
     * Fetches all active (non-deleted) Job Postions from the database.
     * 
     * @return List of JobPostion objects.
     */
    public List<JobPosition> getAllJobPositions() throws SQLException
    {
        //List that will hold all the JobPosition objects
        List<JobPosition> jobPositions = new ArrayList<>();

        //Query to get all the non deleted jobs
        String query = "SELECT * FROM JobList WHERE isDeleted = 0";

        try(ResultSet result = executer.getDataFromDatabase(query))
        {
            //Go through all the rows of the result, create a new JobPostion and add to the list
            while(result.next())
            {
                JobPosition jobPosition = new JobPosition
                (
                    result.getString("JobPositionName"),
                    result.getString("HardSkill1"),
                    result.getString("HardSkill2"),
                    result.getString("SoftSkill1"),
                    result.getString("SoftSkill2") 
                );

                jobPosition.setJobPositionID(result.getInt("JobPositionID"));   //Set Postion ID
                jobPosition.setIsDeleted(result.getInt("IsDelted"));            //Set isDeleted
                jobPositions.add(jobPosition);  //Add JobPosition to the list
            }
        }

        return jobPositions;
    }

    /**
     * Adds a new Job Postion to the database.
     * 
     * @param JobPosition The JobPostion object to add.
     * @throws SQLException If a database access error occurs.
     */
    public void addJobPosition(JobPosition jobPosition) throws SQLException
    {
        String query = "INSERT INTO JobPosition (JobPositionName, HardSkill1, HardSkill2, SoftSkill1, SoftSkill2) " +
                        "VALUES (?, ?, ?, ?, ?)"; 

        executer.setDataInDatabase(
            query,
            jobPosition.getJobPositionName(),
            jobPosition.getHardSkill1(),
            jobPosition.getHardSkill2(),
            jobPosition.getSoftSkill1(),
            jobPosition.getSoftSkill2()
            );
    }


    /**
     * Marks a Job Position as deleted in the database.
     * 
     * @param jobPositionID The ID of the Job Position to delete.
     * @throws SQLException If a database access error occurs.
     */
    public void deleteJobPosition(int jobPositionID) throws SQLException
    {
        //String to delete JobPosition from the database
        String query = "UPDATE JobPosition SET IsDeleted = 1 WHERE JobPositionID = ?";
        //Delete Job Position by using JobPosition ID
        executer.setDataInDatabase(query, jobPositionID);
    }
}
