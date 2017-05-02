package lab3;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
/**
 * 
 * @author Joshua DeNoble and Paul Bayruns
 * Manager represents a manager in a company that works alongside
 * other types of employees
 *
 */
public class Manager extends Employee implements SalariedEmployee
{
	private static final double MAX_YEARLY_SALARY = 80000;
	private static final double MIN_YEARLY_SALARY = 40000;
	private static final double HEALTH_INSURANCE_RANGE = 20000;
	private static final double HEALTH_INSURANCE_80K = .035;
	private static final double HEALTH_INSURANCE_100K = .0425;
	private static final double HEALTH_INSURANCE_120K = .0495;
	private static final Department DEFAULT_DEPARTMENT = Department.ACCOUNTING;
	private static final Random rand = new Random();
	
	private double yearlySalary; //the manager's salary
	private Department managerDepartment; //the manager's department
	public enum Department //enum for the different possible departments
	{
		PAYROLL(0), PRODUCTION(1), ACCOUNTING(2), RESEARCH(3), MARKETING(4);
		private int value;
		private Department(int i)
			{
			this.value = i;
			}
			  private static final List<Department> values =Collections.unmodifiableList(Arrays.asList(values()));
			  private static final int size = values.size(); 
			  private static final Random r = new Random();

			  /**
			   * used for selecting a random enum, done because issues occured
			   * when trying to use random.nextInt and toString in Department.valueOf()
			   * @return a random department
			   */
			  public static Department randomDepartment()  
			  {
			    return values.get(r.nextInt(size));
			  }
	}

	/**
	 * @throws InvalidIDException 
	 * 
	 */
	public Manager()
	{
		super();
		setYearlySalary(MIN_YEARLY_SALARY);
		this.managerDepartment = DEFAULT_DEPARTMENT;
	}


	/**
	 * Passes the parameters to the appropriate fields, calls the employee constructor
	 * @param employeeName
	 * @param employeeID
	 * @param hireYear
	 * @param weeklyEarnings
	 * @param yearlySalary
	 * @param department
	 */
	public Manager(String employeeName, String employeeID, int hireYear,
			double weeklyEarnings, double yearlySalary, Department department) throws InvalidIDException
	{
		super(employeeName, employeeID, hireYear, weeklyEarnings);
		this.yearlySalary = yearlySalary;
		this.managerDepartment = department;
	}
	
	
	/**
	 * @return
	 */
	public Department getManagerDepartment() 
	{
		return managerDepartment;
	}
	
	
	
	/* (non-Javadoc)
	 * @see Work.SalariedEmployee#getPaycheckAfterDeductions()
	 */
	public double getPaycheckAfterDeductions()
	{
		if(this.getYearlySalary()>=MIN_YEARLY_SALARY+HEALTH_INSURANCE_RANGE&&this.getYearlySalary()<MIN_YEARLY_SALARY+2*HEALTH_INSURANCE_RANGE)
			return this.getWeeklyEarnings()-this.getWeeklyEarnings()*HEALTH_INSURANCE_80K;
		
		if(this.getYearlySalary()>MIN_YEARLY_SALARY+HEALTH_INSURANCE_RANGE && this.getYearlySalary()<=MIN_YEARLY_SALARY+3*HEALTH_INSURANCE_RANGE)
			return this.getWeeklyEarnings() - this.getWeeklyEarnings()*HEALTH_INSURANCE_100K;
		
		if(this.getYearlySalary()>MIN_YEARLY_SALARY+2*HEALTH_INSURANCE_RANGE && this.getYearlySalary()<=MIN_YEARLY_SALARY+4*HEALTH_INSURANCE_RANGE)
			return this.getWeeklyEarnings() - this.getWeeklyEarnings()*HEALTH_INSURANCE_120K;
		else
			return this.getWeeklyEarnings();
	}
	

	/**
	 * Returns a string representation of the manager's salary,
	 * formatted for US currency
	 */
	public String getSalary()
	{
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		String moneyString = formatter.format(this.getYearlySalary());
		return "Salary: " + moneyString + " per year.";
	}

	/* (non-Javadoc)
	 * @see Work.SalariedEmployee#getYearlySalaryBeforeTax()
	 */
	public double getYearlySalaryBeforeTax()
	{
		return this.getYearlySalary();
	}


	/**
	 * @return
	 */
	public double getYearlySalary() {
		return yearlySalary;
	}
	
	/**
	 * @param yearlySalary
	 * @return
	 */
	
	
	
	
	
	/**
	 * Generates and returns a random manager, using Random and randomDepartment()
	 * Created for use in WeekSchedule
	 * @return Manager a manager with randomly generated fields
	 */
	public static Manager randomManager()
	{
		String alphabet = "ABCDEFGHIJKLM";
		char randomLetter = alphabet.charAt(rand.nextInt(alphabet.length()));
		String randomName = allEmployeeNames[rand.nextInt(allEmployeeNames.length)];
		String employeeID = "" + rand.nextInt(10)+rand.nextInt(10)+rand.nextInt(10) + "-" + randomLetter;
		int hireYear = MIN_HIRE_YEAR + rand.nextInt(CURRENT_YEAR-MIN_HIRE_YEAR+1);
		double weeklyEarnings = rand.nextInt((int) MAX_WEEKLY_EARNINGS);
		double yearlySalary = rand.nextInt((int) MIN_YEARLY_SALARY +1) + MAX_YEARLY_SALARY - MIN_YEARLY_SALARY;
		Department department = Department.randomDepartment();
		Manager RandManager = new Manager();
		try{RandManager = new Manager(randomName, employeeID, hireYear, weeklyEarnings,
				yearlySalary, department);}
		catch(InvalidIDException e)
		{
			
		}
		return RandManager;
	}

	/* (non-Javadoc)
	 * @see Work.Employee#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if(!(super.equals(obj)))
		{
			return false;
		}
		if(!(obj instanceof Manager))
		{
			return false;
		}
		Manager temp = (Manager) obj;
		if(!(temp.getYearlySalary() == this.getYearlySalary()))
		{
			return false;
		}
		if(!(temp.getManagerDepartment()==temp.getManagerDepartment()))
		{
			return false;
		}
			return true;
	}

	/* (non-Javadoc)
	 * @see Work.Employee#toString()
	 */
	@Override
	public String toString() {
			return "==========" + "\n" + "Position: Manager " + "\n" + super.toString() + "\n"
					+ "Yearly Salary: " + yearlySalary + "\n" + "Department: " + managerDepartment;

	}

	/**
	 * @param yearlySalary
	 */
	/**
	 * @param yearlySalary
	 */
	public void setYearlySalary(double yearlySalary)
	{
		this.yearlySalary = yearlySalary;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(randomManager());
	}
}
