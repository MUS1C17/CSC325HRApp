package com.hrapp;

import java.util.ArrayList;

//Manages list of employees. Allows adding, retrieving, and managing employee data.

public class EmployeeManager {
    //List to store employee instances
    private ArrayList<Employee> employees;

    //A Constructor to initialize EmployeeManager
    public void EmployeeManger() {
        this.employees = new ArrayList<>();
    }

    //Method to add a new employee
    public void addEmployee(Employee employee) {
        if (employee != null) {
            employees.add(employee); //Adds the employee to the list
        }else{
            System.out.println("Unable to add: null employee.");
        }
        }
    

    //Method to retrieve the list of employees
    public ArrayList<Employee> getEmployees() {
        return new ArrayList<>(employees); //Returns a copy of the list for integrity
    }

    //Methods can be added as needed
    public Employee findEmployeeByName(String name) {
        for (Employee employee : employees) {
            if (employee.getName().equals(name))
                return employee;
            }
        return null; // Returns null if no employee is found
        }

    // Method to remove an employee
    public boolean removeEmployee(Employee employee) {
        return employees.remove(employee); // Remove the employee if exists
    }
}




