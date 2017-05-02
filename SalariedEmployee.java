package lab3;

/**
 * 
 * @author Joshua DeNoble and Paul Bayruns
 * SalariedEmployee is an interface designed to be implemented by
 * any class that represents a salaried employee
 * In this case, it is used in Manager and ShiftSupervisor
 *
 */
public interface SalariedEmployee 
{
/**
 * @return the paycheck after deductions are taken
 */
public double getPaycheckAfterDeductions();
/**
 * @return the yearly salary before taxes
 */
public double getYearlySalaryBeforeTax();
}
