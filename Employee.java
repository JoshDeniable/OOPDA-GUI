package lab3;

/**
 * A class to represent an employee in a company. Abstract with subclasses ProductionWorker, ShiftSupervisor, and Manager.
 * @author Joshua DeNoble and Paul Bayruns
 *
 */
public abstract class Employee {

	private String employeeName; // Employee Name
	private String employeeID; // Employee number in the format XXX-L, where
									// each X is a digit within the range 0-9
									// and the L is a letter within the range
									// A-M or a-m
	private int hireYear; // Year hired
	private double weeklyEarnings; // Weekly Earnings. An amount earned by an
									// employee during the last week; a
									// positive value less than $10,000
	protected static String[] allEmployeeNames = {"Josh", "Paul", "Justice", "Derek", "Alec", "James", "Amy", "Robin", "Gary"
			+ "Barney", "Ted", "Terry", "Greg", "Bob", "Spencer", "Penny", "Sheldon", "Leonard", "Sandy", "Patrick"}; 
			//all employee names, mostly for use in generating random employees
	
	public static final double MAX_WEEKLY_EARNINGS = 10000;
	public static final double MIN_WEEKLY_EARNINGS = 0;
	public static final int CURRENT_YEAR = 2015;
	public static final int MIN_HIRE_YEAR = 1940;
	public static final String DEFAULT_EMPLOYEE_ID = "221-B";
	public static final String DEFAULT_EMPLOYEE_NAME = "Arthur Conan Doyle";


	/**
	 * Default employee constructor
	 */
	public Employee()
	{
		setEmployeeName(DEFAULT_EMPLOYEE_NAME);
		try{setEmployeeID(DEFAULT_EMPLOYEE_ID);}
		catch(InvalidIDException e){};
		setHireYear(CURRENT_YEAR);
		setWeeklyEarnings(MAX_WEEKLY_EARNINGS);
		
	}

	/**
	 * @param employeeName the name of the employee
	 * @param employeeID the employeeID
	 * @param hireYear the employee's hire year
	 * @param weeklyEarnings the employee's weekly earnings
	 */
	public Employee(String employeeName, String employeeID, int hireYear, double weeklyEarnings) throws InvalidIDException
	{
		setEmployeeName(employeeName);
		try{setEmployeeID(employeeID);}
		catch(InvalidIDException e)
		{
			throw new InvalidIDException();
		}
		setHireYear(hireYear);

		setWeeklyEarnings(weeklyEarnings);
			
	}
	
	/**
	 * @return returns the salary or wage of the employee with currency formatting
	 */
	abstract public String getSalary(); 


	/**
	 * @return the employee's name
	 */
	public String getEmployeeName() 
	{
		return employeeName;
	}

	/**
	 * @param employeeName sets the employee's name to the given string
	 */
	public void setEmployeeName(String employeeName) 
	{
		this.employeeName = employeeName;
	}

	/**
	 * @return the employee's ID
	 */
	public String getEmployeeID() 
	{
		return employeeID;
	}
	
	/**
	 * Checks that a given employee ID is valid
	 * Using regex because ID's must match the form XXX-L, where X is a digit
	 * and L is a letter from a-m/A-M
	 * @param employeeID the employee's ID
	 * @return
	 */
	private boolean isValidEmployeeID(String employeeID)
	{
		return employeeID.matches("\\d\\d\\d-[A-Ma-m]");
	}

	/**
	 * @param employeeID the ID to set this.employeeID to
	 */
	public void setEmployeeID(String employeeID) throws InvalidIDException
	{
		if (isValidEmployeeID(employeeID)) 
		{
			this.employeeID = employeeID;
		}
		else
		{
			throw new InvalidIDException();
		}
		
	}

	/**
	 * @return the year the employee was hired
	 */
	public int getHireYear() 
	{
		return hireYear;
	}
	
	/**
	 * Checks that the hireYear is within reasonable limits
	 * Checks against current year because an employee cannot be hired in the future
	 * @param hireYear
	 * @return whether or not the hireYear is a valid year
	 */
	

	/**
	 * @param hireYear the year the employee was hired
	 */
	public void setHireYear(int hireYear) 
	{
		
		this.hireYear = hireYear;

	}
	
	/**
	 * @param weeklyEarnings
	 * @return boolean - whether the weekly earnings given are within the required range
	 */


	/**
	 * @return the employee's weekly earnings
	 */
	public double getWeeklyEarnings() 
	{
		return weeklyEarnings;
	}

	/**
	 * @param weeklyEarnings
	 */
	public void setWeeklyEarnings(double weeklyEarnings) 
	{
			this.weeklyEarnings = weeklyEarnings;
	}


	@Override
	public String toString() 
	{
		return "Employee Name: " + employeeName 
				+ ", Employee ID: " + employeeID + "\n" + "Employee Hired In: " + hireYear 
				+ "\n" + "Weekly Earnings: " + weeklyEarnings + "\n" + this.getSalary();
	}

	/**
	 * Checks if this employee equals the given employee. 
	 * Checks each field instead of only ID's because ID's are not completely guaranteed to be unique
	 */
	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj) 
		{
			return true;
		}
		
		if (obj == null) 
		{
			return false;
		}
		
		if(!(obj instanceof Employee))
		{
			return false;
		}
		
		Employee temp = (Employee) obj;
		if(!(temp.getEmployeeName().equals(this.getEmployeeName())))
		{
			return false;
		}
		
		if(!(temp.getHireYear()==this.getHireYear())) 
		{
			return false;

		}
		if(!(this.getWeeklyEarnings()==temp.getWeeklyEarnings()))
		{
			return false;
		}
		if(!(temp.getEmployeeID().equals(this.getEmployeeID())))
		{
			return false;
		}
		return true;
		
	}

}
