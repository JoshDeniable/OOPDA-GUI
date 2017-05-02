package lab3;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import lab3.Shift.Day;

/**
 * @author Joshua DeNoble and Paul Bayruns
 * Week Schedule represents a week's worth of shift information
 * It keeps track of who works each shift, how much money they make during the shift,
 * and 
 */
public class WeekSchedule {
	private static final Random r = new Random();
	public static final int TOTAL_EMPLOYEES = 50;
	private static final int MIN_PRODUCTION_WORKERS = 5; 
	private static final int MAX_PRODUCTION_WORKERS = 20;
	private static final int MIN_SHIFT_SUPERVISORS = 1;
	private static final int MAX_SHIFT_SUPERVISORS = 3;
	private static final int MIN_MANAGERS = 1;
	private static final int MAX_MANAGERS = 3;
	private static final int HOURS_PER_SHIFT = 8;
	public static final double NIGHT_PRODWORKER_BONUS = .2;
	public static final double GOALS_PRODWORKER_BONUS = .05;
	public static final int CURRENT_YEAR = 2015;
	public static final int PROD_GOALS_FOR_BONUS = 4;
	public static final double FIVE_YEAR_BONUS = .05;
	public static final double TEN_YEAR_BONUS = .10;
	public static final double TWENTY_YEAR_BONUS = .12;
	public static final double MAX_BONUS = .15;
	
	private String[] columnNames = {"Name",
            "ID",
            "Hire Year",
            "Weekly Salary"};
	private Object[][] data;
	private int nextRow;

	private ArrayList<Employee> allEmployees; //an arraylist to store all of the employees in the company
	private HashSet<Shift> weekSchedule; //a collection of shifts to represent the entire week
	private HashSet<Employee> workedDuringWeek; //a collection of all employees who worked this week
	private double totalNightShiftBonus; //the total of all the bonuses paid to production workers on night shifts
	private double totalProductionGoalBonus; //the total of all bonuses paid to production workers and shift supervisors
												//for meeting production goals while on their shift


	/**
	 * Calls generateEmployees to fill the list of all employees
	 * Calls generateRandomWeekSchedule to create the week schedule randomly
	 */
	public WeekSchedule() 
	{
		allEmployees = new ArrayList<Employee>();
		weekSchedule = new HashSet<Shift>();
		workedDuringWeek = new HashSet<Employee>();
		//generateEmployees();
		generateRandomWeekSchedule();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Adds employees to the allEmployees arraylist until the desired total is reached
	 * Uses 3 productionworkers so that the loop is kept simple while also keeping productionworkers
	 * at a 3:1 ratio to managers and shift supervisors
	 * Checks if allEmployees does not contain the employee to be added so that there are no
	 * duplicate people in the company (as this is not possible)
	 */
	private void generateEmployees()
	{
		while (allEmployees.size()<TOTAL_EMPLOYEES)
		{
			ProductionWorker randProductionWorker = ProductionWorker.randomProductionWorker();
			ProductionWorker randProductionWorker2 = ProductionWorker.randomProductionWorker(); //number naming used to simplify loop, ensures more production workers than
			ProductionWorker randProductionWorker3 = ProductionWorker.randomProductionWorker(); 							//shift supervisors or managers
			if(!(allEmployees.contains(randProductionWorker)))
			{
				allEmployees.add(randProductionWorker);
				allEmployees.add(randProductionWorker2);
				allEmployees.add(randProductionWorker3);
			}
			
			ShiftSupervisor randShiftSupervisor = ShiftSupervisor.randomShiftSupervisor();
			if(!(allEmployees.contains(randShiftSupervisor)))
			{
				allEmployees.add(randShiftSupervisor);
			}
			
			Manager randManager = Manager.randomManager();
			if(!(allEmployees.contains(randManager)))
			{
				allEmployees.add(randManager);
			}
		}
	}
	
	/**
	 * @return a HashSet of randomly generated employees, with the number of employees
	 * in each position determined by constants.
	 * Uses 3 loop indexes to increase efficiency, reducing the number of contains()
	 * checks that are necessary. The instance of each employee is found and then
	 * the appropriate counter is decremented.
	 * Adds employees to workedDuringWeek for tracking of shift data later.
	 */
	public HashSet<Employee> getRandomShiftEmployees()
	{
		HashSet<Employee> employeesInShift = new HashSet<Employee>();
		
		int numberOfProductionWorkers = r.nextInt(MAX_PRODUCTION_WORKERS - MIN_PRODUCTION_WORKERS) 
				+ MIN_PRODUCTION_WORKERS + 1;
		
		int numberOfShiftSupervisors = r.nextInt(MAX_SHIFT_SUPERVISORS - MIN_SHIFT_SUPERVISORS)
				+ MIN_SHIFT_SUPERVISORS +1;
		
		int numberOfManagers = r.nextInt(MAX_MANAGERS - MIN_MANAGERS) + MIN_MANAGERS + 1;
		
		while (!((numberOfProductionWorkers == 0 && numberOfShiftSupervisors == 0) && numberOfManagers == 0))
		{
			Employee randomEmployee = allEmployees.get(r.nextInt(allEmployees.size()));
			
			
				if(randomEmployee instanceof ProductionWorker)
				{
					if(numberOfProductionWorkers>0)
						{
						employeesInShift.add(randomEmployee);
						this.workedDuringWeek.add(randomEmployee);
						numberOfProductionWorkers--;
						}
				}
				
				if(randomEmployee instanceof Manager)
					{
						if(numberOfManagers>0)
							{
								employeesInShift.add(randomEmployee);
								this.workedDuringWeek.add(randomEmployee);
								numberOfManagers--;
							}
					}
				else if(numberOfShiftSupervisors>0)
				{
						employeesInShift.add(randomEmployee);
						this.workedDuringWeek.add(randomEmployee);
						numberOfShiftSupervisors--;
				}
			
		}
		return employeesInShift;
	}
	
	/**
	 * Calculates the bonus for a shift supervisor based on production goals and years worked
	 * @param productionGoals the number of productiongoals met this week while the supervisor was on shift
	 * @param yearsWorked the number of years the supervisor has worked at the company
	 * @return a double representing the percent that the supervisor will receive as a bonus
	 */
	public double getSupervisorProdGoalBonus(int productionGoals, int yearsWorked)
	{
		double bonus;
			if(productionGoals>=PROD_GOALS_FOR_BONUS)
			{
				if(yearsWorked<=5)
					{
					bonus = FIVE_YEAR_BONUS;
					}
				else if(yearsWorked<=10)
					{
					bonus = TEN_YEAR_BONUS;
					}
				else if(yearsWorked<=20)
					{
					bonus = TWENTY_YEAR_BONUS;
					}
				else
					{
					bonus = MAX_BONUS;
					}
			}
			else
			{
				bonus = 0;
			}
			return bonus;
	}
	
	/**
	 * Generates a random week schedule to be stored in the weekSchedule field
	 * Iterates through the enum Days to represent a full week
	 * Uses i==0 as a logic check to "iterate through" boolean values, allowing day and
	 * night shifts to alternate.
	 * Uses i-1 to multiply the night shift bonus by 0 when it is a day shift
	 * Updates employee weekly pay information as they are added to the HashSet
	 * to increase efficiency
	 */
	public void generateRandomWeekSchedule()
	{
		
		for(Day D: Day.values())
		{
			for(int i=0; i<=1; i++)
			{
				boolean prodGoalsMet = r.nextBoolean();
				HashSet<Employee> shiftEmployees = getRandomShiftEmployees();
		
				for(Employee e: shiftEmployees)
					{
						if(e instanceof ShiftSupervisor && prodGoalsMet)
						{
							int productionGoals = ((ShiftSupervisor) e).getProductionGoals();
							int yearsWorked = Employee.CURRENT_YEAR - e.getHireYear();
							
							try{((ShiftSupervisor) e).setProductionGoals(productionGoals+1);}
							catch(InvalidProductionGoalsException ex){};
							if(productionGoals>=PROD_GOALS_FOR_BONUS)
								totalProductionGoalBonus += e.getWeeklyEarnings()*getSupervisorProdGoalBonus(productionGoals, yearsWorked);
							//if productiongoals are met, the shift supervisor's count for prodgoals is incremented
						}
						if(e instanceof ProductionWorker)
						{
							double weeklyEarnings = e.getWeeklyEarnings();
							double hourlyRate = ((ProductionWorker) e).getHourlyRate();
							
							
							e.setWeeklyEarnings(weeklyEarnings + //calculates the addition of a nightshift bonus for this shift, if it is a nightshift
									HOURS_PER_SHIFT*hourlyRate*(1-i)*NIGHT_PRODWORKER_BONUS); //uses (1-i) because 1-i will be 1 if it is a nightshift, and 0 if day shift.
							totalNightShiftBonus += HOURS_PER_SHIFT*hourlyRate*(1-i)*NIGHT_PRODWORKER_BONUS;
							
							if(prodGoalsMet)
							{
								e.setWeeklyEarnings(weeklyEarnings+HOURS_PER_SHIFT*hourlyRate*GOALS_PRODWORKER_BONUS); //adds this shift's hours to the weekly earnings
								totalProductionGoalBonus += HOURS_PER_SHIFT*hourlyRate*GOALS_PRODWORKER_BONUS;
							}
							
						}
						
					}
				weekSchedule.add(new Shift(shiftEmployees, i==0, D, prodGoalsMet));
				}
			}
	}
	
	public static void main(String[] args) 
	{
		WeekSchedule week = new WeekSchedule();
		double totalPayout = 0;
		
//prints info for all employees who worked during the week, commented out when printing for only salaried employees was added
		for(Employee s: week.workedDuringWeek)
		{
			System.out.println(s);
			System.out.println("This Week's Earnings: " + s.getWeeklyEarnings() + "\n");
			totalPayout =+ s.getWeeklyEarnings();
		}
		System.out.println("Total wages paid among all workers this week: " + totalPayout + "\n");
		totalPayout = 0;
		
		for(Employee s:week.workedDuringWeek)
		{
			if(s instanceof SalariedEmployee)
				{
				System.out.println(s);
				System.out.println("This Week's Earnings: " + s.getWeeklyEarnings() + "\n");
				totalPayout = totalPayout + s.getWeeklyEarnings();
				}
		}
		System.out.println("Total wages paid to salaried employees this week: " + totalPayout + "\n" + "Total night shift bonuses: "
									+ week.totalNightShiftBonus + "\n" + "Total Production Goal Bonuses: " + week.totalProductionGoalBonus);
		totalPayout = 0;

		for(Shift s: week.weekSchedule)
		{
			System.out.println(s);
		}
	}

	/**
	 * @return
	 */
	public ArrayList<Employee> getAllEmployees() {
		return allEmployees;
	}
	
	public void addEmployeeToAllEmployees(Employee e)
	{
		allEmployees.add(e);
	}


	/**
	 * @return
	 */
	public HashSet<Shift> getWeekSchedule() {
		return weekSchedule;
	}

	/**
	 * @param weekSchedule
	 */
	public void setWeekSchedule(HashSet<Shift> weekSchedule) {
		this.weekSchedule = weekSchedule;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (obj == null)
			return false;
		
		if (getClass() != obj.getClass())
			return false;
		
		WeekSchedule other = (WeekSchedule) obj;
		if(this.getWeekSchedule().size()!=other.getWeekSchedule().size())
			return false;
		
		for(Shift s: this.getWeekSchedule())
		{
			if(!other.getWeekSchedule().contains(s))
				return false;
		} 
		return true;
	}
}
