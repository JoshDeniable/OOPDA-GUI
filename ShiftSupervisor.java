package lab3;

import java.text.NumberFormat;
import java.util.Random;

/**
 * @author Joshua DeNoble and Paul Bayruns
 *
 */
public class ShiftSupervisor extends Employee implements SalariedEmployee 
{
	private double yearlySalary; //the yearly salary of the worker
	private int productionGoals; //the number of times production goals have been met while
									//under this worker's supervision this week
	private static final Random rand = new Random();
	private static final double MAX_YEARLY_SALARY = 80000;
	private static final double MIN_YEARLY_SALARY = 40000;
	private static final int MIN_PRODUCTION_GOALS = 0;
	private static final int MAX_PRODUCTION_GOALS = 6;
	private static final double TAX_RATE = .3;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		
	}

	

	public ShiftSupervisor()
	{
		super();
		yearlySalary = MAX_YEARLY_SALARY;
		try{setProductionGoals(MIN_PRODUCTION_GOALS);}
		catch(InvalidProductionGoalsException e)
		{
			
		}
	}

	/**
	 * @param employeeName
	 * @param employeeID
	 * @param hireYear
	 * @param weeklyEarnings
	 * @param productionGoals
	 * @param yearlySalary
	 */
	public ShiftSupervisor(String employeeName, String employeeID, int hireYear, double weeklyEarnings, 
			int productionGoals, double yearlySalary) throws InvalidProductionGoalsException, InvalidIDException
	{
		super(employeeName, employeeID, hireYear, weeklyEarnings);
		try{setProductionGoals(productionGoals);}
		catch(InvalidProductionGoalsException ex)
		{
		}
		setYearlySalary(yearlySalary);
	}
	
	/**
	 * Returns a string representation of the supervisor's salary,
	 * formatted for US currency
	 */
	public String getSalary()
	{
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		String moneyString = formatter.format(this.getYearlySalary());
		return "Salary: " + moneyString + " per year.";
	}

	/**
	 * @return the yearlySalary
	 */
	public double getYearlySalary() {
		return yearlySalary;
	}


	/**
	 * @param yearlySalary the yearlySalary to set
	 */
	public void setYearlySalary(double yearlySalary) 
	{
		this.yearlySalary = yearlySalary;
	}

	/**
	 * @return the productionGoals
	 */
	public int getProductionGoals() {
		return productionGoals;
	}


	/**
	 * @param productionGoals the productionGoals to set
	 */
	public void setProductionGoals(int productionGoals) throws InvalidProductionGoalsException 
	{
		if(productionGoals >= MIN_PRODUCTION_GOALS)
		{
			this.productionGoals = productionGoals;
		}
		else
		{
			throw new InvalidProductionGoalsException();
		}
	}
	
	/* (non-Javadoc)
	 * @see Work.SalariedEmployee#getPaycheckAfterDeductions()
	 */
	public double getPaycheckAfterDeductions()
	{
		return this.getWeeklyEarnings()-this.getWeeklyEarnings()*TAX_RATE;
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
	public static ShiftSupervisor randomShiftSupervisor()
	{
		String alphabet = "ABCDEFGHIJKLM";
		char randomLetter = alphabet.charAt(rand.nextInt(alphabet.length()));
		String randomName = allEmployeeNames[rand.nextInt(allEmployeeNames.length)];
		String employeeID = "" + rand.nextInt(10)+rand.nextInt(10)+rand.nextInt(10) + "-" + randomLetter;
		int hireYear = MIN_HIRE_YEAR + rand.nextInt(CURRENT_YEAR-MIN_HIRE_YEAR+1);
		double weeklyEarnings = rand.nextInt((int) MAX_WEEKLY_EARNINGS);
		double yearlySalary = rand.nextInt((int) MIN_YEARLY_SALARY +1) + MAX_YEARLY_SALARY - MIN_YEARLY_SALARY;
		ShiftSupervisor RandShiftSupervisor = new ShiftSupervisor();
		
		try{RandShiftSupervisor = new ShiftSupervisor(randomName, employeeID, hireYear, weeklyEarnings,
				MIN_PRODUCTION_GOALS, yearlySalary);}
		catch(InvalidProductionGoalsException e)
		{
			
		}
		catch(InvalidIDException e)
		{
			
		}
		return RandShiftSupervisor;
	}

	/* (non-Javadoc)
	 * @see Work.Employee#toString()
	 */
	@Override
	public String toString() {
		return "==========" + "\n" + "Position: Shift Supervisor " + "\n" + super.toString() + "\n"
				+ "Yearly Salary: " + yearlySalary + "\n" + "Production Goals: " + productionGoals;
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
		if(!(obj instanceof ShiftSupervisor))
		{
			return false;
		}
		ShiftSupervisor temp = (ShiftSupervisor) obj;
		if(!(temp.getYearlySalary() == this.getYearlySalary()))
		{
			return false;
		}
		if(!(temp.getProductionGoals()==temp.getProductionGoals()))
		{
			return false;
		}
			return true;
	}

	
}
