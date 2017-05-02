package lab3;

import java.util.HashSet;

/**
 * 
 * @author Joshua DeNoble and Paul Bayruns
 * Test is only a test class to test the productionworker and shiftsupervisor classes
 * All items kept private to avoid any classes using this class
 *
 */
public class Test 
{

	private static final int NUM_PROD_WORKERS = 10;
	private static final int NUM_SHIFT_SUPERVISORS = 10;
	private static HashSet<Employee> allEmployees = new HashSet<Employee>();
	
	public static void main(String[] args) 
	{
		PopulateSet();
		for(Employee e: allEmployees)
		{
			System.out.println(e);
		}
	}

	public Test()
	{
	}
	
	
	/**
	 * 
	 */
	private static void PopulateSet()
	{
		while(allEmployees.size()<NUM_PROD_WORKERS)
		{
			ProductionWorker randProductionWorker = ProductionWorker.randomProductionWorker();
			allEmployees.add(randProductionWorker);
			System.out.println(randProductionWorker);
		}
		
		while(allEmployees.size()< NUM_PROD_WORKERS + NUM_SHIFT_SUPERVISORS)
		{
			ShiftSupervisor randShiftSupervisor = ShiftSupervisor.randomShiftSupervisor();
			allEmployees.add(randShiftSupervisor);
			System.out.println(randShiftSupervisor);
		}
	}
	
}
