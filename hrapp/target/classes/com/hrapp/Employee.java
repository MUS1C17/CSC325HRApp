//Employee class. I thought these four properites were good, but I'm open to adding more.
public class Employee {
    private String name;
    private String id;
    private String position;
    private String department;
    
    public Employee(String name, String id, String position, String department) {
        this.name = name;
        this.id = id;
        this.position = position;
        this.department = department;
    }

    //getter and setters
    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getPosition() {
        return position;
    }

    public String getDepartment() {
        return department;
    }
}