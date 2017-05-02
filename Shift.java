package lab3;

import java.util.HashSet;

/**
 * @author Joshua DeNoble and Paul Bayruns
 * Shift represents an 8 hour shift, day or night, of several employees in different positions
 * Shift also has information for the day of the week and whether production goals were met
 * during this shift
 *
 */
public class Shift 
{

	private HashSet<Employee> employeesInShift; //HashSet to contain the employees for a given shift
	public enum Day //enum for days of the week, used to keep track of the shift's day
	{
		MONDAY("Monday"), TUESDAY("Tuesday"), WEDNESDAY("Wednesday"),
	    THURSDAY("Thursday"), FRIDAY("Friday"), SATURDAY("Saturday"), SUNDAY("Sunday");
	    private String value;
		private Day(String s)
			{
			this.value = s;
			}
	}
	
	private boolean isDayShift; //boolean for whether the shift is day or night:
								//true if day shift, false if night shift
	private boolean productionGoalsMet; //boolean for whether production goals were met for the shift
	private Day dayOfWeek;
	
	/**
	 * 
	 */
	public Shift()
	{
		this.employeesInShift = new HashSet<Employee>();
		this.isDayShift = true;
		this.dayOfWeek = dayOfWeek.MONDAY;
		this.productionGoalsMet = false;
	}
	
	/**
	 * @param employeesInShift
	 * @param isDayShift
	 * @param dayOfWeek
	 * @param productionGoalsMet
	 */
	public Shift(HashSet<Employee> employeesInShift, boolean isDayShift, Day dayOfWeek, 
			boolean productionGoalsMet)
	{
		this.employeesInShift = employeesInShift;
		this.isDayShift = isDayShift;
		this.dayOfWeek = dayOfWeek;
		this.productionGoalsMet = productionGoalsMet;
	}
	
	/**
	 * @return
	 */
	public HashSet<Employee> getEmployeesInShift() 
	{
		return this.employeesInShift;
	}
	
	/**
	 * @return
	 */
	public boolean isDayShift() 
	{
		return this.isDayShift;
	}
	
	/**
	 * @param dayShift
	 */
	public void setDayShift(boolean dayShift)
	{
		this.isDayShift = dayShift;
	}
	
	/**
	 * @return
	 */
	public boolean isProductionGoalsMet() 
	{
		return productionGoalsMet;
	}
	
	/**
	 * @param productionGoalsMet
	 */
	public void setProductionGoalsMet(boolean productionGoalsMet) 
	{
		this.productionGoalsMet = productionGoalsMet;
	}
	
	/**
	 * @param day
	 */
	public void setDayOfWeek(Day day)
	{
		this.dayOfWeek = day;
	}
	
	/**
	 * @return
	 */
	public Day getDayOfWeek()
	{
		return this.dayOfWeek;
	}
	
	/**
	 * isDayShift and productionGoalsMet checked to print
	 * the real world meaning of their boolean values
	 */
	public String toString()
	{
		String time = "Night";
		String employeeNames = "";
		if(isDayShift())
		{
			time = "Day";
		}
		String areMet = "not met";
		if(productionGoalsMet)
		{
			areMet = "met";
		}
		for(Employee s: employeesInShift)
		{
			employeeNames = employeeNames + " " + s.getEmployeeName() 
						+ " (" + s.getClass().getSimpleName() + ")" + "\n";
		}
			
		return "Shift Time: " + getDayOfWeek() + " " + time + "\n" + "Production goals " + areMet
				+ "\n" + "Employees in shift:" + employeeNames;
		
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		HashSet<Employee> employees = new HashSet<Employee>();
		for(int i =0; i<10; i++)
		{
			if(i%2==0)
			{
				employees.add(ProductionWorker.randomProductionWorker());
			}
			else
				employees.add(ShiftSupervisor.randomShiftSupervisor());
		}
		Shift TestShift = new Shift(employees, false, Day.MONDAY, true);
		System.out.println(TestShift);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dayOfWeek == null) ? 0 : dayOfWeek.hashCode());
		result = prime * result + (isDayShift ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Shift other = (Shift) obj;
		if (dayOfWeek != other.dayOfWeek)
			return false;
		if (employeesInShift == null) {
			if (other.employeesInShift != null)
				return false;
		} else if (!employeesInShift.equals(other.employeesInShift))
			return false;
		if (isDayShift != other.isDayShift)
			return false;
		if (productionGoalsMet != other.productionGoalsMet)
			return false;
		return true;
	}

}
