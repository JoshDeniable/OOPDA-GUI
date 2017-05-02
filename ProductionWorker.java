package lab3;

import java.text.NumberFormat;
import java.util.Random;

/**
 * @author Joshua DeNoble and Paul Bayruns
 *Production Worker represents a factory worker in the company, working 
 *alongside Managers and Shiftsupervisors
 */
public class ProductionWorker extends Employee 
{
	private double hourlyRate; //the hourly wage of the employee
	
	public static final double MAX_HOURLY_RATE = 50;
	public static final double MIN_HOURLY_RATE = 6;
	
	private static final Random rand = new Random();

	
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		
	}
	
	/**
	 * 
	 */
	public ProductionWorker() 
	{
		super(); //calls default employee constructor
		setHourlyRate(MAX_HOURLY_RATE); //uses setHourlyRate so that the hourly rate is checked to be valid first
	}
	
	/**
	 * @param employeeName
	 * @param employeeID
	 * @param hireYear
	 * @param weeklyEarnings
	 * @param hourlyRate
	 */
	public ProductionWorker(String employeeName, String employeeID, int hireYear, double weeklyEarnings, double hourlyRate) throws InvalidIDException
	{
		super(employeeName, employeeID, hireYear, weeklyEarnings);
		setHourlyRate(hourlyRate); //uses setHourlyRate so that the hourly rate is checked to be valid first
		
	}
	
	/* (non-Javadoc)
	 * @see Work.Employee#getSalary()
	 */
	public String getSalary()
	{
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		String moneyString = formatter.format(this.hourlyRate);
		return "Wage: " + moneyString + " per hour.";
	}
	
	/**
	 * @return the hourlyRate
	 */
	public double getHourlyRate() 
	{
		return hourlyRate;
	}
	
	
	
	/**
	 * @param hourlyRate the hourlyRate to set
	 */
	public void setHourlyRate(double hourlyRate) 
	{
		this.hourlyRate = hourlyRate;
	}
	
	/**
	 * Generates a random production worker
	 * Uses String alphabet to add the letter after the digits of the employee's id
	 * @return a ProductionWorker with random field values
	 */
	public static ProductionWorker randomProductionWorker()
	{
		String alphabet = "ABCDEFGHIJKLMabcdefghijklm";
		char randomLetter = alphabet.charAt(rand.nextInt(alphabet.length()));
		String randomName = allEmployeeNames[rand.nextInt(allEmployeeNames.length)];
		String employeeID = "" + rand.nextInt(10)+rand.nextInt(10)+rand.nextInt(10) + "-" + randomLetter;
		int hireYear = MIN_HIRE_YEAR + rand.nextInt(CURRENT_YEAR-MIN_HIRE_YEAR+1);
		double weeklyEarnings = rand.nextInt((int)MAX_WEEKLY_EARNINGS);
		double hourlyRate = rand.nextInt((int)MAX_HOURLY_RATE - (int)MIN_HOURLY_RATE + 1) + MIN_HOURLY_RATE;
		
		ProductionWorker RandProductionWorker = new ProductionWorker();
		try{RandProductionWorker = new ProductionWorker(randomName, employeeID, hireYear, weeklyEarnings, hourlyRate);}
		catch(InvalidIDException e)
		{
			
		}
		return RandProductionWorker;
	}
	
	/**
	 * checks if this ProductionWorker equals the given object
	 * calls the super method equals first to check the common employee fields&instance first
	 */
	public boolean equals(Object obj)
	{
		if(!(super.equals(obj)))
		{
			return false;
		}
		if(!(obj instanceof ProductionWorker))
		{
			return false;
		}
		ProductionWorker temp = (ProductionWorker) obj;
		if(!(temp.getHourlyRate() == this.getHourlyRate()))
		{
			return false;
		}
		return true;
	}
	

	public String toString()
	{
		return "==========" + "\n" + "Position: Production Worker" + "\n" + super.toString() + "\n"
				+ "Hourly Rate: " + hourlyRate;
	}
	
}
