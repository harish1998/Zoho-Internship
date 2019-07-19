import java.io.Serializable;
import java.util.Comparator;

public class Employee implements Serializable, Comparable<Employee> {
    private static final long serialVersionUID = 1L;
    private Long employeeID;
    private String employeeName;
    private String employeeDepartment;
    private Long employeePhoneNumber;
	
	public Employee() {
		
	}
	
	public Employee(Long id, String name, String dept, Long phone) {
		setEmployeeID(id);
		setEmployeeName(name);
		setEmployeeDepartment(dept);
		setEmployeePhoneNumber(phone);
	}
    public void setEmployeeID(Long employeeID) {
        this.employeeID = employeeID;
    }
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
    public void setEmployeeDepartment(String employeeDepartment) {
        this.employeeDepartment = employeeDepartment;
    }
    public void setEmployeePhoneNumber(Long employeePhoneNumber) {
        this.employeePhoneNumber = employeePhoneNumber;
    }

    public Long getEmployeeID() {
        return this.employeeID;
    }
    public String getEmployeeName() {
        return this.employeeName;
    }
    public String getEmployeeDepartment() {
        return this.employeeDepartment;
    }
    public Long getEmployeePhoneNumber() {
        return this.employeePhoneNumber;
    }
    @Override
    public String toString() {
        return "Employee ID = "+this.getEmployeeID();
    }
    @Override
    public int compareTo(Employee e) {
        return this.getEmployeeID().compareTo(e.getEmployeeID());
    }
}