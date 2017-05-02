package lab3;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyledDocument;

/**
 * A Graphical User Interface used to input and display Employee and Shift information
 * through Tabbed Panes. To create a new Employee or Shift, click on File --> New. To view any
 * entered information, click on View. To quit, you may either click on File --> Quit, or you
 * can just click on the X in the corner.
 * @author Joshua DeNoble and Paul Bayruns
 */
public class Gooey implements ActionListener{

	private JFrame frame;
	private JFrame aboutFrame;
	private JTabbedPane tabbedPane;
	private ArrayList<Employee> allEmployees;
	private HashSet<Shift> allShifts;
	
	public Gooey()
	{
		allEmployees = new ArrayList<Employee>();
		allShifts = new HashSet<Shift>();
	}
	
	public static void main(String[] args) {
		Gooey GUI = new Gooey();
		GUI.makeFrame();
	}
	
	public void actionPerformed(ActionEvent event)
	{
		System.out.println("Menu Item: " + event.getActionCommand());
	}

	/**
	 * Creates the outermost Frame, in which everything else goes.
	 */
	private void makeFrame()
	{
		frame = new JFrame("Lab 3: GUI");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container contentPane = frame.getContentPane();
		
		makeMenuBar(frame);
		createPanes(contentPane);
		
		frame.setSize(720, 450);
		frame.setVisible(true);
		
	}
	
	/**
	 * Creates the Tabbed Pane, in which the program is run, when the program is opened. 
	 * Creates the About tab, which displays information
	 * regarding the program on top of a Ukrainian Flag :)
	 * @param pane
	 */
	private void createPanes(Container pane)
	{
		tabbedPane = new JTabbedPane();
		JPanel aboutTab = createAboutTab();
		tabbedPane.addTab("About", aboutTab);
		pane.add(tabbedPane, BorderLayout.CENTER);
	}
	
	/**
	 * An unfinished method that would have been used to add a specific Employee
	 * to a Shift that has already been created.
	 * @param e Employee to add
	 * @param s Shift to add Employee to
	 */
	private void addEmployeeToShift(Employee e, Shift s)
	{
		
	}
	
	/**
	 * The method used to create the About Tab
	 * @return aboutTab The tab containing program info and a Ukrainian Flag
	 */
	private JPanel createAboutTab()
	{
		JPanel aboutTab = new JPanel();
		JTextArea aboutTextArea = new JTextArea();
		aboutTextArea.append("\t\tThis program is designed to organize employee information. \nSalary, hire year, and postion specific employee information"
				+ "can be stored, in addition to weekly shift schedules.");
		aboutTextArea.setEditable(false);
		aboutTextArea.setLineWrap(true);
		aboutTextArea.setWrapStyleWord(true);
		aboutTextArea.setBackground(Color.BLUE);
		aboutTab.add(aboutTextArea);
		aboutTab.setLayout(new GridLayout(2,1));
		aboutTab.setBackground(Color.YELLOW);
		return aboutTab;
	}
	
	/**
	 * The method used to create the New Employee Tab
	 * @return newEmployeeCardPanel The "card" or tab that allows the user to enter
	 * Employee information and store it in order to add that Employee to a Shift and view it later.
	 */
	private JPanel newEmployeeTab()
	{
		Manager.Department department;
		JPanel newEmployeeCardPanel = new JPanel();
		JPanel newEmployeePanel = new JPanel();
		newEmployeePanel.setLayout(new GridLayout(10,1));
		JButton createEmployeeButton = new JButton("Create Employee");
		
		JTextField hourlyRateText = new JTextField("Enter hourly rate");
		JTextField employeeIDText = new JTextField("Enter Employee ID");
		JTextField hireYearText = new JTextField("Enter hire year");
		JTextField weeklyEarningsText = new JTextField("Enter weekly earnings");
		JTextField employeeNameText = new JTextField("Enter employee name");
		JTextField productionGoalsText = new JTextField("Enter production goals met");
		JTextField yearlySalaryText = new JTextField("Enter yearly salary");
		JTextField departmentText = new JTextField("Enter department");
		
		String[] comboTypes = {"----", "Production Worker", "Manager", "Shift Supervisor" };
		// Create the combo box, and set 1st item as Default
		JLabel hourlyRateLabel = new JLabel("Hourly Rate:");
		JLabel employeeID = new JLabel("Employee ID:");
		JLabel hireYear = new JLabel("Hire Year:");
		JLabel weeklyEarnings = new JLabel("Weekly Earnings:");
		JLabel employeeNameLabel = new JLabel("Employee Name:");
		JLabel productionGoalsLabel = new JLabel("Production Goals:");
		JLabel yearlySalaryLabel = new JLabel("Yearly Salary: ");
		JLabel departmentLabel = new JLabel("Department: ");
		
		String[] departmentTypes = {"Payroll", "Production", "Accounting", "Research", "Marketing"};
		JComboBox departmentComboBox = new JComboBox(departmentTypes);
		departmentComboBox.setSelectedIndex(0);
		
		JComboBox employeeTypeComboBox = new JComboBox(comboTypes);
		employeeTypeComboBox.setSelectedIndex(0);
		employeeTypeComboBox.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				JComboBox jcmbType = (JComboBox) e.getItemSelectable();
				if (e.getStateChange() == ItemEvent.SELECTED)
				{
				String cmbType = (String) jcmbType.getSelectedItem();
					if(cmbType.equals("Production Worker"))
					{
						newEmployeePanel.add(hourlyRateLabel);
						newEmployeePanel.add(hourlyRateText);
						newEmployeePanel.remove(yearlySalaryLabel);
						newEmployeePanel.remove(yearlySalaryText);
						newEmployeePanel.remove(productionGoalsLabel);
						newEmployeePanel.remove(productionGoalsText);
						newEmployeePanel.remove(departmentComboBox);
						employeeTypeComboBox.repaint();
						frame.pack();
					}
					if(cmbType.equals("Manager"))
					{
						newEmployeePanel.add(yearlySalaryLabel);
						newEmployeePanel.add(yearlySalaryText);
						newEmployeePanel.add(departmentLabel);
						newEmployeePanel.add(departmentComboBox);
						newEmployeePanel.remove(hourlyRateLabel);
						newEmployeePanel.remove(hourlyRateText);
						newEmployeePanel.remove(productionGoalsLabel);
						newEmployeePanel.remove(productionGoalsText);
						employeeTypeComboBox.repaint();
						frame.pack();
					}
					if(cmbType.equals("Shift Supervisor"))
					{
						newEmployeePanel.add(yearlySalaryLabel);
						newEmployeePanel.add(yearlySalaryText);
						newEmployeePanel.add(productionGoalsLabel);
						newEmployeePanel.add(productionGoalsText);
						newEmployeePanel.remove(hourlyRateLabel);
						newEmployeePanel.remove(hourlyRateText);
						newEmployeePanel.remove(departmentLabel);
						newEmployeePanel.add(departmentComboBox);
						frame.pack();
					}
					
				}
				
			}
		});
		createEmployeeButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					Manager.Department department = Manager.Department.ACCOUNTING;
					double hourlyRate = 0;
					int productionGoals = 0;
					double yearlySalary = 0;
					if(!hourlyRateText.getText().equals("Enter hourly rate"))
					{
						hourlyRate = Double.parseDouble(hourlyRateText.getText());
					}
					
					if(!productionGoalsText.getText().equals("Enter production goals met"))
					{
						productionGoals = Integer.parseInt(productionGoalsText.getText());
					}
					
					if(!yearlySalaryText.getText().equals("Enter yearly salary"))
					{
						yearlySalary = Double.parseDouble(yearlySalaryText.getText());
					}
					
						
					
					int hireYear = Integer.parseInt(hireYearText.getText());
					String employeeID = employeeIDText.getText();
					double weeklyEarnings = Double.parseDouble(weeklyEarningsText.getText());
					String employeeName = employeeNameText.getText();
					String employeeType = employeeTypeComboBox.getSelectedItem().toString();
				
					if(employeeType.equals("Manager"))
					{
						Manager.Department.valueOf(((String) departmentComboBox.getSelectedItem()).toUpperCase());
						allEmployees.add(new Manager(employeeName, employeeID, hireYear, weeklyEarnings, yearlySalary, department));
						tabbedPane.remove(newEmployeeCardPanel);
						JOptionPane.showMessageDialog(frame, "Employee added successfully", null, JOptionPane.INFORMATION_MESSAGE);
					}
					if(employeeType.equals("Production Worker"))
					{
						allEmployees.add(new ProductionWorker(employeeName, employeeID, hireYear, weeklyEarnings, hourlyRate));
						tabbedPane.remove(newEmployeeCardPanel);
						JOptionPane.showMessageDialog(frame, "Employee added successfully", null, JOptionPane.INFORMATION_MESSAGE);
					}
					if(employeeType.equals("Shift Supervisor"))
					{
						allEmployees.add(new ShiftSupervisor(employeeName, employeeID, hireYear, weeklyEarnings, productionGoals, yearlySalary));
						tabbedPane.remove(newEmployeeCardPanel);
						JOptionPane.showMessageDialog(frame, "Employee added successfully", null, JOptionPane.INFORMATION_MESSAGE);
					}
				
				}
				
					catch(NumberFormatException ex)
					{
						JOptionPane.showMessageDialog(frame, "Please enter numbers for hire year, weekly earnings,"
								+ "yearly salary, and hourly rate if applicable.", null, JOptionPane.ERROR_MESSAGE);
					}
					catch(InvalidIDException ex)
					{
						JOptionPane.showMessageDialog(frame, "Please use an ID of the form ###-[A-M]", null, JOptionPane.ERROR_MESSAGE);
					}
	
					catch(InvalidProductionGoalsException ex)
					{
						JOptionPane.showMessageDialog(frame, "Production goals met cannot be negative", null, JOptionPane.ERROR_MESSAGE);
					}
				
			
		
			}});
		newEmployeePanel.add(createEmployeeButton);
		newEmployeePanel.add(employeeTypeComboBox);
		newEmployeePanel.add(employeeID);
		newEmployeePanel.add(employeeIDText);
		newEmployeePanel.add(hireYear);
		newEmployeePanel.add(hireYearText);
		newEmployeePanel.add(weeklyEarnings);
		newEmployeePanel.add(weeklyEarningsText);
		newEmployeePanel.add(employeeNameLabel);
		newEmployeePanel.add(employeeNameText);
		newEmployeeCardPanel.setLayout(new CardLayout());
		newEmployeeCardPanel.add(newEmployeePanel);
		return newEmployeeCardPanel;
	}
	
	/**
	 * The method used to create the New Shift Tab
	 * @return newShiftCardPanel The "card" or tab that allows the user to enter
	 * Shift information and store it in order to view it later.
	 */
	public JPanel newShiftTab()
	{
		JPanel newShiftCardPanel = new JPanel();
		JPanel newShiftPanel = new JPanel(new GridLayout(8, 2));
		JPanel newShiftInnerPanel = new JPanel();
		
		String[] weekDayComboTypes = { "---", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
		String[] shiftComboTypes = { "---", "Day", "Night" };
		String[] goalsComboTypes = { "---", "Achieved", "Not Achieved" };
		JLabel weekDayLabel = new JLabel("Day of Week: ");
		JLabel shiftLabel = new JLabel("Shift: ");
		JLabel goalsMetLabel = new JLabel("Goals: ");
		JLabel employeeNameLabel = new JLabel("Employee Name: ");
		JLabel employeeNameLabel2 = new JLabel("Employee Name: ");
		JLabel employeeNameLabel3 = new JLabel("Employee Name: ");
		
		JComboBox weekDayComboBox = new JComboBox(weekDayComboTypes);
		weekDayComboBox.setSelectedIndex(0);
		JComboBox shiftComboBox = new JComboBox(shiftComboTypes);
		shiftComboBox.setSelectedIndex(0);
		JComboBox goalsComboBox = new JComboBox(goalsComboTypes);
		goalsComboBox.setSelectedIndex(0);
		
		JTextField addEmployee1NameTextField = new JTextField("");
		JTextField addEmployee2NameTextField = new JTextField("");
		JTextField addEmployee3NameTextField = new JTextField("");
		
		JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(new ActionListener ()
		{
			public void actionPerformed(ActionEvent event)
			{
				try
				{
					Shift.Day day = Shift.Day.valueOf(weekDayComboBox.getSelectedItem().toString().toUpperCase());
					boolean shift = false;
					if(shiftComboBox.getSelectedIndex() == 1)
					{
						shift = true;
					}
					boolean goalsMet = false;
					if(goalsComboBox.getSelectedIndex() == 1)
					{
						goalsMet = true;
					}
					HashSet<Employee> shiftEmployees = new HashSet<Employee>();
					if(allEmployees!=null)
					{
						for(Employee e : allEmployees)
					
					{	
						if(e.getEmployeeName().equals(addEmployee1NameTextField.getText()))
						{
							shiftEmployees.add(e);
						}
						if(e.getEmployeeName().equals(addEmployee2NameTextField.getText()))
						{
							shiftEmployees.add(e);
						}
						if(e.getEmployeeName().equals(addEmployee3NameTextField.getText()))
						{
							shiftEmployees.add(e);
						}
						
					}
					Shift newShift = new Shift(shiftEmployees , shift, day, goalsMet);
					allShifts.add(newShift);
					tabbedPane.remove(newShiftCardPanel);
					JOptionPane.showMessageDialog(frame, "Shift added successfully", null, JOptionPane.INFORMATION_MESSAGE);
				}
				}
				catch(NullPointerException ex)
				{
					JOptionPane.showMessageDialog(frame, "One or more employees were not found", "Employee Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		newShiftPanel.add(weekDayLabel);
		newShiftPanel.add(weekDayComboBox);
		newShiftPanel.add(shiftLabel);
		newShiftPanel.add(shiftComboBox);
		newShiftPanel.add(goalsMetLabel);
		newShiftPanel.add(goalsComboBox);
		newShiftPanel.add(employeeNameLabel);
		newShiftPanel.add(addEmployee1NameTextField);
		newShiftPanel.add(employeeNameLabel2);
		newShiftPanel.add(addEmployee2NameTextField);
		newShiftPanel.add(employeeNameLabel3);
		newShiftPanel.add(addEmployee3NameTextField);
		newShiftInnerPanel.add(submitButton);
		newShiftPanel.add(newShiftInnerPanel);
		newShiftCardPanel.add(newShiftPanel);
		return newShiftCardPanel;
	}
	
	/**
	 * Creates the Program's Menu Bar which contains the following:
	 * File --> (Close All Tabs, New --> (Employee, Shift), Quit)
	 * View --> (Employees --> (All Employees, All Production Workers, All Managers, All Shift Supervisors), 
	 *           Shifts --> (All Shift Data, All Employees in Shift, All Production Workers in Shift, All Managers in Shift, All Shift Supervisors in Shift))
	 * Help --> About
	 * @param frame
	 */
	private void makeMenuBar(JFrame frame)
	{   
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		
		JMenuItem closeTabsItem = new JMenuItem("Close All Tabs");
		fileMenu.add(closeTabsItem);
		closeTabsItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				tabbedPane.removeAll();
			}
		});
		
		JMenu newMenu = new JMenu("New");
		fileMenu.add(newMenu);
		
		JMenuItem newEmployee = new JMenuItem("Employee");
		newEmployee.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event)
			{
		        tabbedPane.addTab("New Employee", newEmployeeTab());
		        frame.pack();
			}
		});
		newMenu.add(newEmployee);
		
		JMenuItem newShift = new JMenuItem("Shift");
		newShift.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event)
			{
				tabbedPane.addTab("New Shift", newShiftTab());
				frame.pack();
			}
		});
		newMenu.add(newShift);
		
		JMenuItem quitItem = new JMenuItem("Quit");
		quitItem.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event)
			{
				System.exit(0);
			}
		});
		fileMenu.add(quitItem);
		
		
		JMenu viewMenu = new JMenu("View");
		menuBar.add(viewMenu);
		
		JMenu employeeViewMenu = new JMenu("Employees");
		viewMenu.add(employeeViewMenu);
		
		JMenuItem allEmployeesItem = new JMenuItem("All Employees");
		allEmployeesItem.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event)
			{
				JTextPane textPane = new JTextPane();
				textPane.setEditable(false);
				JPanel textPaneOutPanel = new JPanel();
				textPaneOutPanel.add(textPane);
				tabbedPane.add("All Employee Information", textPane);
				StyledDocument doc = textPane.getStyledDocument();
				for(Employee e: allEmployees)
				{
					try{
					doc.insertString(doc.getLength()+1, e.toString(), SimpleAttributeSet.EMPTY );
					}
					catch(BadLocationException ex)
					{
						
					}
				}
			}
		});
		employeeViewMenu.add(allEmployeesItem);
		JMenuItem allManagersItem = new JMenuItem("All Managers");
		allManagersItem.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event)
			{
				for(Employee e: allEmployees)
				{
					if(e instanceof Manager)
					{
						JTextPane textPane = new JTextPane();
						textPane.setEditable(false);
						JPanel textPaneOutPanel = new JPanel();
						textPaneOutPanel.add(textPane);
						tabbedPane.add("All Manager Information", textPane);
						StyledDocument doc = textPane.getStyledDocument();
						try{
							doc.insertString(doc.getLength()+1, e.toString(), SimpleAttributeSet.EMPTY );
							}
						catch(BadLocationException ex){}
					}
				}
			}
		});
		employeeViewMenu.add(allManagersItem);
		JMenuItem allProductionWorkersItem = new JMenuItem("All Production Workers");
		allProductionWorkersItem.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event)
			{
				for(Employee e: allEmployees)
				{
					if(e instanceof ProductionWorker)
					{
						JTextPane textPane = new JTextPane();
						textPane.setEditable(false);
						JPanel textPaneOutPanel = new JPanel();
						textPaneOutPanel.add(textPane);
						tabbedPane.add("All Production Worker Information", textPane);
						StyledDocument doc = textPane.getStyledDocument();
						try{
							doc.insertString(doc.getLength()+1, e.toString(), SimpleAttributeSet.EMPTY );
							}
						catch(BadLocationException ex){}
					}
				}
			}
		});
		employeeViewMenu.add(allProductionWorkersItem);
		JMenuItem allShiftSupervisorsItem = new JMenuItem("All Shift Supervisors");
		allShiftSupervisorsItem.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event)
			{
				for(Employee e: allEmployees)
				{
					if(e instanceof ShiftSupervisor)
					{
						JTextPane textPane = new JTextPane();
						textPane.setEditable(false);
						JPanel textPaneOutPanel = new JPanel();
						textPaneOutPanel.add(textPane);
						tabbedPane.add("All Shift Supervisor Information", textPane);
						StyledDocument doc = textPane.getStyledDocument();
						try{
							doc.insertString(doc.getLength()+1, e.toString(), SimpleAttributeSet.EMPTY );
							}
						catch(BadLocationException ex){}
					}
				}
			}
		});
		employeeViewMenu.add(allShiftSupervisorsItem);
		
		JMenu shiftViewMenu = new JMenu("Shifts");
		viewMenu.add(shiftViewMenu);
		
		JMenuItem shiftDataItem = new JMenuItem("All Shift Data");
		shiftDataItem.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event)
			{
				JTextPane textPane = new JTextPane();
				textPane.setEditable(false);
				JPanel textPaneOutPanel = new JPanel();
				textPaneOutPanel.add(textPane);
				tabbedPane.add("All Shift Information", textPane);
				StyledDocument doc = textPane.getStyledDocument();
				for(Shift s: allShifts)
				{
					
					try{
						doc.insertString(doc.getLength()+1, s.toString(), SimpleAttributeSet.EMPTY );
						}
					catch(BadLocationException ex){}
				}
			}
		});
		
		shiftViewMenu.add(shiftDataItem);
		JMenu shiftEmployeeMenu = new JMenu("All Employees in Shift");
		JMenu mondayShift = new JMenu("Monday");
		JMenuItem mondayDayShift = new JMenuItem("Day");
		mondayDayShift.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try{
				JTextPane textPane = new JTextPane();
				textPane.setEditable(false);
				JPanel textPaneOutPanel = new JPanel();
				textPaneOutPanel.add(textPane);
				tabbedPane.add("Monday Day Shift Information", textPane);
				StyledDocument doc = textPane.getStyledDocument();
				for(Shift s: allShifts)
				{
					if(s.getDayOfWeek()==Shift.Day.MONDAY && s.isDayShift())
					{
						try{
							doc.insertString(doc.getLength()+1, s.toString(), SimpleAttributeSet.EMPTY );
							}
						catch(BadLocationException ex){}
					}
				}
				}
				catch(NullPointerException ex)
				{
					JOptionPane.showMessageDialog(frame, "The selected shift is empty", null, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		JMenuItem mondayNightShift = new JMenuItem("Night");
		mondayNightShift.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try{
				JTextPane textPane = new JTextPane();
				textPane.setEditable(false);
				JPanel textPaneOutPanel = new JPanel();
				textPaneOutPanel.add(textPane);
				tabbedPane.add("Monday Night Shift Information", textPane);
				StyledDocument doc = textPane.getStyledDocument();
				for(Shift s: allShifts)
				{
					if(s.getDayOfWeek()==Shift.Day.MONDAY && !s.isDayShift())
					{
						try{
							doc.insertString(doc.getLength()+1, s.toString(), SimpleAttributeSet.EMPTY );
							}
						catch(BadLocationException ex){}
					}
				}
				}
				catch(NullPointerException ex)
				{
					JOptionPane.showMessageDialog(frame, "The selected shift is empty", null, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		mondayShift.add(mondayDayShift);
		mondayShift.add(mondayNightShift);
		
		JMenu tuesdayShift = new JMenu("Tuesday");
		JMenuItem tuesdayDayShift = new JMenuItem("Day");
		JMenuItem tuesdayNightShift = new JMenuItem("Night");
		tuesdayShift.add(tuesdayDayShift);
		tuesdayDayShift.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try{
				JTextPane textPane = new JTextPane();
				textPane.setEditable(false);
				JPanel textPaneOutPanel = new JPanel();
				textPaneOutPanel.add(textPane);
				tabbedPane.add("Tuesday Day Shift Information", textPane);
				StyledDocument doc = textPane.getStyledDocument();
				for(Shift s: allShifts)
				{
					if(s.getDayOfWeek()==Shift.Day.TUESDAY && s.isDayShift())
					{
						try{
							doc.insertString(doc.getLength()+1, s.toString(), SimpleAttributeSet.EMPTY );
							}
						catch(BadLocationException ex){}
					}
				}
				}
				catch(NullPointerException ex)
				{
					JOptionPane.showMessageDialog(frame, "The selected shift is empty", null, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		tuesdayShift.add(tuesdayNightShift);
		tuesdayNightShift.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try{
				JTextPane textPane = new JTextPane();
				textPane.setEditable(false);
				JPanel textPaneOutPanel = new JPanel();
				textPaneOutPanel.add(textPane);
				tabbedPane.add("Tuesday Night Shift Information", textPane);
				StyledDocument doc = textPane.getStyledDocument();
				for(Shift s: allShifts)
				{
					if(s.getDayOfWeek()==Shift.Day.TUESDAY && !s.isDayShift())
					{
						try{
							doc.insertString(doc.getLength()+1, s.toString(), SimpleAttributeSet.EMPTY );
							}
						catch(BadLocationException ex){}
					}
				}
				}
				catch(NullPointerException ex)
				{
					JOptionPane.showMessageDialog(frame, "The selected shift is empty", null, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		JMenu wednesdayShift = new JMenu("Wednesday");
		JMenuItem wednesdayDayShift = new JMenuItem("Day");
		JMenuItem wednesdayNightShift = new JMenuItem("Night");
		wednesdayShift.add(wednesdayDayShift);
		wednesdayDayShift.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try{
				JTextPane textPane = new JTextPane();
				textPane.setEditable(false);
				JPanel textPaneOutPanel = new JPanel();
				textPaneOutPanel.add(textPane);
				tabbedPane.add("Wednesday Day Shift Information", textPane);
				StyledDocument doc = textPane.getStyledDocument();
				for(Shift s: allShifts)
				{
					if(s.getDayOfWeek()==Shift.Day.WEDNESDAY && s.isDayShift())
					{
						try{
							doc.insertString(doc.getLength()+1, s.toString(), SimpleAttributeSet.EMPTY );
							}
						catch(BadLocationException ex){}
					}
				}
				}
				catch(NullPointerException ex)
				{
					JOptionPane.showMessageDialog(frame, "The selected shift is empty", null, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		wednesdayShift.add(wednesdayNightShift);
		wednesdayNightShift.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try{
				JTextPane textPane = new JTextPane();
				textPane.setEditable(false);
				JPanel textPaneOutPanel = new JPanel();
				textPaneOutPanel.add(textPane);
				tabbedPane.add("Wednesday Night Shift Information", textPane);
				StyledDocument doc = textPane.getStyledDocument();
				for(Shift s: allShifts)
				{
					if(s.getDayOfWeek()==Shift.Day.WEDNESDAY && !s.isDayShift())
					{
						try{
							doc.insertString(doc.getLength()+1, s.toString(), SimpleAttributeSet.EMPTY );
							}
						catch(BadLocationException ex){}
					}
				}
				}
				catch(NullPointerException ex)
				{
					JOptionPane.showMessageDialog(frame, "The selected shift is empty", null, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		JMenu thursdayShift = new JMenu("Thursday");
		JMenuItem thursdayDayShift = new JMenuItem("Day");
		JMenuItem thursdayNightShift = new JMenuItem("Night");
		thursdayShift.add(thursdayDayShift);
		thursdayDayShift.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try{
				JTextPane textPane = new JTextPane();
				textPane.setEditable(false);
				JPanel textPaneOutPanel = new JPanel();
				textPaneOutPanel.add(textPane);
				tabbedPane.add("Thursday Day Shift Information", textPane);
				StyledDocument doc = textPane.getStyledDocument();
				for(Shift s: allShifts)
				{
					if(s.getDayOfWeek()==Shift.Day.THURSDAY && s.isDayShift())
					{
						try{
							doc.insertString(doc.getLength()+1, s.toString(), SimpleAttributeSet.EMPTY );
							}
						catch(BadLocationException ex){}
					}
				}
				}
				catch(NullPointerException ex)
				{
					JOptionPane.showMessageDialog(frame, "The selected shift is empty", null, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		thursdayShift.add(thursdayNightShift);
		thursdayNightShift.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try{
				JTextPane textPane = new JTextPane();
				textPane.setEditable(false);
				JPanel textPaneOutPanel = new JPanel();
				textPaneOutPanel.add(textPane);
				tabbedPane.add("Thursday Night Shift Information", textPane);
				StyledDocument doc = textPane.getStyledDocument();
				for(Shift s: allShifts)
				{
					if(s.getDayOfWeek()==Shift.Day.THURSDAY && !s.isDayShift())
					{
						try{
							doc.insertString(doc.getLength()+1, s.toString(), SimpleAttributeSet.EMPTY );
							}
						catch(BadLocationException ex){}
					}
				}
				}
				catch(NullPointerException ex)
				{
					JOptionPane.showMessageDialog(frame, "The selected shift is empty", null, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		JMenu fridayShift = new JMenu("Friday");
		JMenuItem fridayDayShift = new JMenuItem("Day");
		JMenuItem fridayNightShift = new JMenuItem("Night");
		fridayShift.add(fridayDayShift);
		fridayDayShift.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try{
				JTextPane textPane = new JTextPane();
				textPane.setEditable(false);
				JPanel textPaneOutPanel = new JPanel();
				textPaneOutPanel.add(textPane);
				tabbedPane.add("Friday Day Shift Information", textPane);
				StyledDocument doc = textPane.getStyledDocument();
				for(Shift s: allShifts)
				{
					if(s.getDayOfWeek()==Shift.Day.FRIDAY && s.isDayShift())
					{
						try{
							doc.insertString(doc.getLength()+1, s.toString(), SimpleAttributeSet.EMPTY );
							}
						catch(BadLocationException ex){}
					}
				}
				}
				catch(NullPointerException ex)
				{
					JOptionPane.showMessageDialog(frame, "The selected shift is empty", null, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		fridayShift.add(fridayNightShift);
		fridayNightShift.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try{
				JTextPane textPane = new JTextPane();
				textPane.setEditable(false);
				JPanel textPaneOutPanel = new JPanel();
				textPaneOutPanel.add(textPane);
				tabbedPane.add("Friday Night Shift Information", textPane);
				StyledDocument doc = textPane.getStyledDocument();
				for(Shift s: allShifts)
				{
					if(s.getDayOfWeek()==Shift.Day.FRIDAY && !s.isDayShift())
					{
						try{
							doc.insertString(doc.getLength()+1, s.toString(), SimpleAttributeSet.EMPTY );
							}
						catch(BadLocationException ex){}
					}
				}
				}
				catch(NullPointerException ex)
				{
					JOptionPane.showMessageDialog(frame, "The selected shift is empty", null, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		shiftEmployeeMenu.add(mondayShift);
		shiftEmployeeMenu.add(tuesdayShift);
		shiftEmployeeMenu.add(wednesdayShift);
		shiftEmployeeMenu.add(thursdayShift);
		shiftEmployeeMenu.add(fridayShift);
		shiftViewMenu.add(shiftEmployeeMenu);
//============================================================================================	
//============================================================================================
		JMenu shiftEmployeeMenuManagers = new JMenu("All Managers in Shift");
		JMenu mondayShiftManagers = new JMenu("Monday");
		JMenuItem mondayDayShiftManagers = new JMenuItem("Day");
		JMenuItem mondayNightShiftManagers = new JMenuItem("Night");
		mondayShiftManagers.add(mondayDayShiftManagers);
		mondayShiftManagers.add(mondayNightShiftManagers);
		mondayNightShiftManagers.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try{
				JTextPane textPane = new JTextPane();
				textPane.setEditable(false);
				JPanel textPaneOutPanel = new JPanel();
				textPaneOutPanel.add(textPane);
				tabbedPane.add("Monday Night Shift Managers", textPane);
				StyledDocument doc = textPane.getStyledDocument();
				for(Shift s: allShifts)
				{
					if(s.getDayOfWeek()==Shift.Day.MONDAY && !s.isDayShift())
					{
						for(Employee em: s.getEmployeesInShift())
						{
							if(em instanceof Manager)
							{
							try{
								doc.insertString(doc.getLength()+1, s.toString(), SimpleAttributeSet.EMPTY );
								}
							catch(BadLocationException ex){}
							}
						}
					}
				}
				}
				catch(NullPointerException ex)
				{
					JOptionPane.showMessageDialog(frame, "The selected shift is empty", null, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		mondayDayShiftManagers.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try{
				JTextPane textPane = new JTextPane();
				textPane.setEditable(false);
				JPanel textPaneOutPanel = new JPanel();
				textPaneOutPanel.add(textPane);
				tabbedPane.add("Monday Night Shift Managers", textPane);
				StyledDocument doc = textPane.getStyledDocument();
				for(Shift s: allShifts)
				{
					if(s.getDayOfWeek()==Shift.Day.MONDAY && s.isDayShift())
					{
						for(Employee em: s.getEmployeesInShift())
						{
							if(em instanceof Manager)
							{
							try{
								doc.insertString(doc.getLength()+1, s.toString(), SimpleAttributeSet.EMPTY );
								}
							catch(BadLocationException ex){}
							}
						}
					}
				}
				}
				catch(NullPointerException ex)
				{
					JOptionPane.showMessageDialog(frame, "The selected shift is empty", null, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		JMenu tuesdayShiftManagers = new JMenu("Tuesday");
		JMenuItem tuesdayDayShiftManagers = new JMenuItem("Day");
		JMenuItem tuesdayNightShiftManagers = new JMenuItem("Night");
		tuesdayShiftManagers.add(tuesdayDayShiftManagers);
		tuesdayShiftManagers.add(tuesdayNightShiftManagers);
		tuesdayDayShiftManagers.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try{
				JTextPane textPane = new JTextPane();
				textPane.setEditable(false);
				JPanel textPaneOutPanel = new JPanel();
				textPaneOutPanel.add(textPane);
				tabbedPane.add("Tuesday Night Shift Managers", textPane);
				StyledDocument doc = textPane.getStyledDocument();
				for(Shift s: allShifts)
				{
					if(s.getDayOfWeek()==Shift.Day.TUESDAY && s.isDayShift())
					{
						for(Employee em: s.getEmployeesInShift())
						{
							if(em instanceof Manager)
							{
							try{
								doc.insertString(doc.getLength()+1, s.toString(), SimpleAttributeSet.EMPTY );
								}
							catch(BadLocationException ex){}
							}
						}
					}
				}
				}
				catch(NullPointerException ex)
				{
					JOptionPane.showMessageDialog(frame, "The selected shift is empty", null, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		tuesdayNightShiftManagers.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try{
				JTextPane textPane = new JTextPane();
				textPane.setEditable(false);
				JPanel textPaneOutPanel = new JPanel();
				textPaneOutPanel.add(textPane);
				tabbedPane.add("Tuesday Night Shift Managers", textPane);
				StyledDocument doc = textPane.getStyledDocument();
				for(Shift s: allShifts)
				{
					if(s.getDayOfWeek()==Shift.Day.TUESDAY && !s.isDayShift())
					{
						for(Employee em: s.getEmployeesInShift())
						{
							if(em instanceof Manager)
							{
							try{
								doc.insertString(doc.getLength()+1, s.toString(), SimpleAttributeSet.EMPTY );
								}
							catch(BadLocationException ex){}
							}
						}
					}
				}
				}
				catch(NullPointerException ex)
				{
					JOptionPane.showMessageDialog(frame, "The selected shift is empty", null, JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		
		JMenu wednesdayShiftManagers = new JMenu("Wednesday");
		JMenuItem wednesdayDayShiftManagers = new JMenuItem("Day");
		JMenuItem wednesdayNightShiftManagers = new JMenuItem("Night");
		wednesdayShiftManagers.add(wednesdayDayShiftManagers);
		wednesdayShiftManagers.add(wednesdayNightShiftManagers);
		wednesdayDayShiftManagers.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try{
				JTextPane textPane = new JTextPane();
				textPane.setEditable(false);
				JPanel textPaneOutPanel = new JPanel();
				textPaneOutPanel.add(textPane);
				tabbedPane.add("Wednesday Day Shift Managers", textPane);
				StyledDocument doc = textPane.getStyledDocument();
				for(Shift s: allShifts)
				{
					if(s.getDayOfWeek()==Shift.Day.WEDNESDAY && s.isDayShift())
					{
						for(Employee em: s.getEmployeesInShift())
						{
							if(em instanceof Manager)
							{
							try{
								doc.insertString(doc.getLength()+1, s.toString(), SimpleAttributeSet.EMPTY );
								}
							catch(BadLocationException ex){}
							}
						}
					}
				}
				}
				catch(NullPointerException ex)
				{
					JOptionPane.showMessageDialog(frame, "The selected shift is empty", null, JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		wednesdayNightShiftManagers.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try{
				JTextPane textPane = new JTextPane();
				textPane.setEditable(false);
				JPanel textPaneOutPanel = new JPanel();
				textPaneOutPanel.add(textPane);
				tabbedPane.add("Wednesday Night Shift Managers", textPane);
				StyledDocument doc = textPane.getStyledDocument();
				for(Shift s: allShifts)
				{
					if(s.getDayOfWeek()==Shift.Day.WEDNESDAY && !s.isDayShift())
					{
						for(Employee em: s.getEmployeesInShift())
						{
							if(em instanceof Manager)
							{
							try{
								doc.insertString(doc.getLength()+1, s.toString(), SimpleAttributeSet.EMPTY );
								}
							catch(BadLocationException ex){}
							}
						}
					}
				}
				}
				catch(NullPointerException ex)
				{
					JOptionPane.showMessageDialog(frame, "The selected shift is empty", null, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		JMenu thursdayShiftManagers = new JMenu("Thursday");
		JMenuItem thursdayDayShiftManagers = new JMenuItem("Day");
		JMenuItem thursdayNightShiftManagers = new JMenuItem("Night");
		thursdayShiftManagers.add(thursdayDayShiftManagers);
		thursdayShiftManagers.add(thursdayNightShiftManagers);
		thursdayNightShiftManagers.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try{
				JTextPane textPane = new JTextPane();
				textPane.setEditable(false);
				JPanel textPaneOutPanel = new JPanel();
				textPaneOutPanel.add(textPane);
				tabbedPane.add("Thursday Night Shift Managers", textPane);
				StyledDocument doc = textPane.getStyledDocument();
				for(Shift s: allShifts)
				{
					if(s.getDayOfWeek()==Shift.Day.THURSDAY && !s.isDayShift())
					{
						for(Employee em: s.getEmployeesInShift())
						{
							if(em instanceof Manager)
							{
							try{
								doc.insertString(doc.getLength()+1, s.toString(), SimpleAttributeSet.EMPTY );
								}
							catch(BadLocationException ex){}
							}
						}
					}
				}
				}
				catch(NullPointerException ex)
				{
					JOptionPane.showMessageDialog(frame, "The selected shift is empty", null, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		thursdayDayShiftManagers.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try{
				JTextPane textPane = new JTextPane();
				textPane.setEditable(false);
				JPanel textPaneOutPanel = new JPanel();
				textPaneOutPanel.add(textPane);
				tabbedPane.add("Thursday Day Shift Managers", textPane);
				StyledDocument doc = textPane.getStyledDocument();
				for(Shift s: allShifts)
				{
					if(s.getDayOfWeek()==Shift.Day.THURSDAY && s.isDayShift())
					{
						for(Employee em: s.getEmployeesInShift())
						{
							if(em instanceof Manager)
							{
							try{
								doc.insertString(doc.getLength()+1, s.toString(), SimpleAttributeSet.EMPTY );
								}
							catch(BadLocationException ex){}
							}
						}
					}
				}
				}
				catch(NullPointerException ex)
				{
					JOptionPane.showMessageDialog(frame, "The selected shift is empty", null, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		JMenu fridayShiftManagers = new JMenu("Friday");
		JMenuItem fridayDayShiftManagers = new JMenuItem("Day");
		JMenuItem fridayNightShiftManagers = new JMenuItem("Night");
		fridayShiftManagers.add(fridayDayShiftManagers);
		fridayShiftManagers.add(fridayNightShiftManagers);
		fridayDayShiftManagers.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try{
				JTextPane textPane = new JTextPane();
				textPane.setEditable(false);
				JPanel textPaneOutPanel = new JPanel();
				textPaneOutPanel.add(textPane);
				tabbedPane.add("Friday Day Shift Managers", textPane);
				StyledDocument doc = textPane.getStyledDocument();
				for(Shift s: allShifts)
				{
					if(s.getDayOfWeek()==Shift.Day.FRIDAY && s.isDayShift())
					{
						for(Employee em: s.getEmployeesInShift())
						{
							if(em instanceof Manager)
							{
							try{
								doc.insertString(doc.getLength()+1, s.toString(), SimpleAttributeSet.EMPTY );
								}
							catch(BadLocationException ex){}
							}
						}
					}
				}
				}
				catch(NullPointerException ex)
				{
					JOptionPane.showMessageDialog(frame, "The selected shift is empty", null, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		fridayNightShiftManagers.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try{
				JTextPane textPane = new JTextPane();
				textPane.setEditable(false);
				JPanel textPaneOutPanel = new JPanel();
				textPaneOutPanel.add(textPane);
				tabbedPane.add("Friday Night Shift Managers", textPane);
				StyledDocument doc = textPane.getStyledDocument();
				for(Shift s: allShifts)
				{
					if(s.getDayOfWeek()==Shift.Day.FRIDAY && !s.isDayShift())
					{
						for(Employee em: s.getEmployeesInShift())
						{
							if(em instanceof Manager)
							{
							try{
								doc.insertString(doc.getLength()+1, s.toString(), SimpleAttributeSet.EMPTY );
								}
							catch(BadLocationException ex){}
							}
						}
					}
				}
				}
				catch(NullPointerException ex)
				{
					JOptionPane.showMessageDialog(frame, "The selected shift is empty", null, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		shiftEmployeeMenuManagers.add(mondayShiftManagers);
		shiftEmployeeMenuManagers.add(tuesdayShiftManagers);
		shiftEmployeeMenuManagers.add(wednesdayShiftManagers);
		shiftEmployeeMenuManagers.add(thursdayShiftManagers);
		shiftEmployeeMenuManagers.add(fridayShiftManagers);
		shiftViewMenu.add(shiftEmployeeMenuManagers);
//============================================================================================	
//============================================================================================
		
		JMenu shiftEmployeeMenuProdWorkers = new JMenu("All Production Workers in Shift");
		JMenu mondayShiftProdWorkers = new JMenu("Monday");
		JMenuItem mondayDayShiftProdWorkers = new JMenuItem("Day");
		JMenuItem mondayNightShiftProdWorkers = new JMenuItem("Night");
		mondayShiftProdWorkers.add(mondayDayShiftProdWorkers);
		mondayShiftProdWorkers.add(mondayNightShiftProdWorkers);
		mondayDayShiftProdWorkers.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try{
				JTextPane textPane = new JTextPane();
				textPane.setEditable(false);
				JPanel textPaneOutPanel = new JPanel();
				textPaneOutPanel.add(textPane);
				tabbedPane.add("Monday Day Shift Production Workers", textPane);
				StyledDocument doc = textPane.getStyledDocument();
				for(Shift s: allShifts)
				{
					if(s.getDayOfWeek()==Shift.Day.MONDAY && s.isDayShift())
					{
						for(Employee em: s.getEmployeesInShift())
						{
							if(em instanceof ProductionWorker)
							{
							try{
								doc.insertString(doc.getLength()+1, s.toString(), SimpleAttributeSet.EMPTY );
								}
							catch(BadLocationException ex){}
							}
						}
					}
				}
				}
				catch(NullPointerException ex)
				{
					JOptionPane.showMessageDialog(frame, "The selected shift is empty", null, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		mondayNightShiftProdWorkers.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try{
				JTextPane textPane = new JTextPane();
				textPane.setEditable(false);
				JPanel textPaneOutPanel = new JPanel();
				textPaneOutPanel.add(textPane);
				tabbedPane.add("Monday Night Shift Production Workers", textPane);
				StyledDocument doc = textPane.getStyledDocument();
				for(Shift s: allShifts)
				{
					if(s.getDayOfWeek()==Shift.Day.MONDAY && !s.isDayShift())
					{
						for(Employee em: s.getEmployeesInShift())
						{
							if(em instanceof ProductionWorker)
							{
							try{
								doc.insertString(doc.getLength()+1, s.toString(), SimpleAttributeSet.EMPTY );
								}
							catch(BadLocationException ex){}
							}
						}
					}
				}
				}
				catch(NullPointerException ex)
				{
					JOptionPane.showMessageDialog(frame, "The selected shift is empty", null, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		JMenu tuesdayShiftProdWorkers = new JMenu("Tuesday");
		JMenuItem tuesdayDayShiftProdWorkers = new JMenuItem("Day");
		JMenuItem tuesdayNightShiftProdWorkers = new JMenuItem("Night");
		tuesdayShiftProdWorkers.add(tuesdayDayShiftProdWorkers);
		tuesdayShiftProdWorkers.add(tuesdayNightShiftProdWorkers);
		tuesdayNightShiftProdWorkers.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try{
				JTextPane textPane = new JTextPane();
				textPane.setEditable(false);
				JPanel textPaneOutPanel = new JPanel();
				textPaneOutPanel.add(textPane);
				tabbedPane.add("Tuesday Night Shift Production Workers", textPane);
				StyledDocument doc = textPane.getStyledDocument();
				for(Shift s: allShifts)
				{
					if(s.getDayOfWeek()==Shift.Day.TUESDAY && !s.isDayShift())
					{
						for(Employee em: s.getEmployeesInShift())
						{
							if(em instanceof ProductionWorker)
							{
							try{
								doc.insertString(doc.getLength()+1, s.toString(), SimpleAttributeSet.EMPTY );
								}
							catch(BadLocationException ex){}
							}
						}
					}
				}
				}
				catch(NullPointerException ex)
				{
					JOptionPane.showMessageDialog(frame, "The selected shift is empty", null, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		tuesdayDayShiftProdWorkers.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try{
				JTextPane textPane = new JTextPane();
				textPane.setEditable(false);
				JPanel textPaneOutPanel = new JPanel();
				textPaneOutPanel.add(textPane);
				tabbedPane.add("Tuesday Day Shift Production Workers", textPane);
				StyledDocument doc = textPane.getStyledDocument();
				for(Shift s: allShifts)
				{
					if(s.getDayOfWeek()==Shift.Day.TUESDAY && s.isDayShift())
					{
						for(Employee em: s.getEmployeesInShift())
						{
							if(em instanceof ProductionWorker)
							{
							try{
								doc.insertString(doc.getLength()+1, s.toString(), SimpleAttributeSet.EMPTY );
								}
							catch(BadLocationException ex){}
							}
						}
					}
				}
				}
				catch(NullPointerException ex)
				{
					JOptionPane.showMessageDialog(frame, "The selected shift is empty", null, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		JMenu wednesdayShiftProdWorkers = new JMenu("Wednesday");
		JMenuItem wednesdayDayShiftProdWorkers = new JMenuItem("Day");
		JMenuItem wednesdayNightShiftProdWorkers = new JMenuItem("Night");
		wednesdayShiftProdWorkers.add(wednesdayDayShiftProdWorkers);
		wednesdayShiftProdWorkers.add(wednesdayNightShiftProdWorkers);
		wednesdayDayShiftProdWorkers.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try{
				JTextPane textPane = new JTextPane();
				textPane.setEditable(false);
				JPanel textPaneOutPanel = new JPanel();
				textPaneOutPanel.add(textPane);
				tabbedPane.add("Wednesday Day Shift Production Workers", textPane);
				StyledDocument doc = textPane.getStyledDocument();
				for(Shift s: allShifts)
				{
					if(s.getDayOfWeek()==Shift.Day.MONDAY && !s.isDayShift())
					{
						for(Employee em: s.getEmployeesInShift())
						{
							if(em instanceof ProductionWorker)
							{
							try{
								doc.insertString(doc.getLength()+1, s.toString(), SimpleAttributeSet.EMPTY );
								}
							catch(BadLocationException ex){}
							}
						}
					}
				}
				}
				catch(NullPointerException ex)
				{
					JOptionPane.showMessageDialog(frame, "The selected shift is empty", null, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		JMenu thursdayShiftProdWorkers = new JMenu("Thursday");
		JMenuItem thursdayDayShiftProdWorkers = new JMenuItem("Day");
		JMenuItem thursdayNightShiftProdWorkers = new JMenuItem("Night");
		thursdayShiftProdWorkers.add(thursdayDayShiftProdWorkers);
		thursdayShiftProdWorkers.add(thursdayNightShiftProdWorkers);
		thursdayDayShiftProdWorkers.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try{
				JTextPane textPane = new JTextPane();
				textPane.setEditable(false);
				JPanel textPaneOutPanel = new JPanel();
				textPaneOutPanel.add(textPane);
				tabbedPane.add("Thursday Day Production Workers", textPane);
				StyledDocument doc = textPane.getStyledDocument();
				for(Shift s: allShifts)
				{
					if(s.getDayOfWeek()==Shift.Day.THURSDAY && s.isDayShift())
					{
						for(Employee em: s.getEmployeesInShift())
						{
							if(em instanceof ProductionWorker)
							{
							try{
								doc.insertString(doc.getLength()+1, s.toString(), SimpleAttributeSet.EMPTY );
								}
							catch(BadLocationException ex){}
							}
						}
					}
				}
				}
				catch(NullPointerException ex)
				{
					JOptionPane.showMessageDialog(frame, "The selected shift is empty", null, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		thursdayNightShiftProdWorkers.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try{
				JTextPane textPane = new JTextPane();
				textPane.setEditable(false);
				JPanel textPaneOutPanel = new JPanel();
				textPaneOutPanel.add(textPane);
				tabbedPane.add("Thursday Night Production Workers", textPane);
				StyledDocument doc = textPane.getStyledDocument();
				for(Shift s: allShifts)
				{
					if(s.getDayOfWeek()==Shift.Day.THURSDAY && !s.isDayShift())
					{
						for(Employee em: s.getEmployeesInShift())
						{
							if(em instanceof ProductionWorker)
							{
							try{
								doc.insertString(doc.getLength()+1, s.toString(), SimpleAttributeSet.EMPTY );
								}
							catch(BadLocationException ex){}
							}
						}
					}
				}
				}
				catch(NullPointerException ex)
				{
					JOptionPane.showMessageDialog(frame, "The selected shift is empty", null, JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		JMenu fridayShiftProdWorkers = new JMenu("Friday");
		JMenuItem fridayDayShiftProdWorkers = new JMenuItem("Day");
		JMenuItem fridayNightShiftProdWorkers = new JMenuItem("Night");
		fridayShiftProdWorkers.add(fridayDayShiftProdWorkers);
		fridayShiftProdWorkers.add(fridayNightShiftProdWorkers);
		fridayDayShiftProdWorkers.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try{
				JTextPane textPane = new JTextPane();
				textPane.setEditable(false);
				JPanel textPaneOutPanel = new JPanel();
				textPaneOutPanel.add(textPane);
				tabbedPane.add("Friday Day Production Workers", textPane);
				StyledDocument doc = textPane.getStyledDocument();
				for(Shift s: allShifts)
				{
					if(s.getDayOfWeek()==Shift.Day.FRIDAY && s.isDayShift())
					{
						for(Employee em: s.getEmployeesInShift())
						{
							if(em instanceof ProductionWorker)
							{
							try{
								doc.insertString(doc.getLength()+1, s.toString(), SimpleAttributeSet.EMPTY );
								}
							catch(BadLocationException ex){}
							}
						}
					}
				}
				}
				catch(NullPointerException ex)
				{
					JOptionPane.showMessageDialog(frame, "The selected shift is empty", null, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		fridayNightShiftProdWorkers.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try{
				JTextPane textPane = new JTextPane();
				textPane.setEditable(false);
				JPanel textPaneOutPanel = new JPanel();
				textPaneOutPanel.add(textPane);
				tabbedPane.add("Friday Night Production Workers", textPane);
				StyledDocument doc = textPane.getStyledDocument();
				for(Shift s: allShifts)
				{
					if(s.getDayOfWeek()==Shift.Day.FRIDAY && !s.isDayShift())
					{
						for(Employee em: s.getEmployeesInShift())
						{
							if(em instanceof ProductionWorker)
							{
							try{
								doc.insertString(doc.getLength()+1, s.toString(), SimpleAttributeSet.EMPTY );
								}
							catch(BadLocationException ex){}
							}
						}
					}
				}
				}
				catch(NullPointerException ex)
				{
					JOptionPane.showMessageDialog(frame, "The selected shift is empty", null, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		shiftEmployeeMenuProdWorkers.add(mondayShiftProdWorkers);
		shiftEmployeeMenuProdWorkers.add(tuesdayShiftProdWorkers);
		shiftEmployeeMenuProdWorkers.add(wednesdayShiftProdWorkers);
		shiftEmployeeMenuProdWorkers.add(thursdayShiftProdWorkers);
		shiftEmployeeMenuProdWorkers.add(fridayShiftProdWorkers);
		shiftViewMenu.add(shiftEmployeeMenuProdWorkers);
		
		//============================================================================================	
		//============================================================================================
				
		
		JMenu shiftEmployeeMenuSupervisors = new JMenu("All Shift Supervisors in Shift");
		JMenu mondayShiftSupervisors = new JMenu("Monday");
		JMenuItem mondayDayShiftSupervisors = new JMenuItem("Day");
		JMenuItem mondayNightShiftSupervisors = new JMenuItem("Night");
		mondayShiftSupervisors.add(mondayDayShiftSupervisors);
		mondayShiftSupervisors.add(mondayNightShiftSupervisors);
		mondayDayShiftSupervisors.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try{
				JTextPane textPane = new JTextPane();
				textPane.setEditable(false);
				JPanel textPaneOutPanel = new JPanel();
				textPaneOutPanel.add(textPane);
				tabbedPane.add("Monday Day Shift Supervisors", textPane);
				StyledDocument doc = textPane.getStyledDocument();
				for(Shift s: allShifts)
				{
					if(s.getDayOfWeek()==Shift.Day.MONDAY && s.isDayShift())
					{
						for(Employee em: s.getEmployeesInShift())
						{
							if(em instanceof ShiftSupervisor)
							{
							try{
								doc.insertString(doc.getLength()+1, s.toString(), SimpleAttributeSet.EMPTY );
								}
							catch(BadLocationException ex){}
							}
						}
					}
				}
				}
				catch(NullPointerException ex)
				{
					JOptionPane.showMessageDialog(frame, "The selected shift is empty", null, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		mondayNightShiftSupervisors.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try{
				JTextPane textPane = new JTextPane();
				textPane.setEditable(false);
				JPanel textPaneOutPanel = new JPanel();
				textPaneOutPanel.add(textPane);
				tabbedPane.add("Monday Night Shift Supervisors", textPane);
				StyledDocument doc = textPane.getStyledDocument();
				for(Shift s: allShifts)
				{
					if(s.getDayOfWeek()==Shift.Day.MONDAY && !s.isDayShift())
					{
						for(Employee em: s.getEmployeesInShift())
						{
							if(em instanceof ShiftSupervisor)
							{
							try{
								doc.insertString(doc.getLength()+1, s.toString(), SimpleAttributeSet.EMPTY );
								}
							catch(BadLocationException ex){}
							}
						}
					}
				}
				}
				catch(NullPointerException ex)
				{
					JOptionPane.showMessageDialog(frame, "The selected shift is empty", null, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		JMenu tuesdayShiftSupervisors = new JMenu("Tuesday");
		JMenuItem tuesdayDayShiftSupervisors = new JMenuItem("Day");
		JMenuItem tuesdayNightShiftSupervisors = new JMenuItem("Night");
		tuesdayShiftSupervisors.add(tuesdayDayShift);
		tuesdayShiftSupervisors.add(tuesdayNightShift);
		tuesdayDayShiftSupervisors.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try{
				JTextPane textPane = new JTextPane();
				textPane.setEditable(false);
				JPanel textPaneOutPanel = new JPanel();
				textPaneOutPanel.add(textPane);
				tabbedPane.add("Tuesday Day Shift Supervisors", textPane);
				StyledDocument doc = textPane.getStyledDocument();
				for(Shift s: allShifts)
				{
					if(s.getDayOfWeek()==Shift.Day.TUESDAY && s.isDayShift())
					{
						for(Employee em: s.getEmployeesInShift())
						{
							if(em instanceof ShiftSupervisor)
							{
							try{
								doc.insertString(doc.getLength()+1, s.toString(), SimpleAttributeSet.EMPTY );
								}
							catch(BadLocationException ex){}
							}
						}
					}
				}
				}
				catch(NullPointerException ex)
				{
					JOptionPane.showMessageDialog(frame, "The selected shift is empty", null, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		tuesdayNightShiftSupervisors.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try{
				JTextPane textPane = new JTextPane();
				textPane.setEditable(false);
				JPanel textPaneOutPanel = new JPanel();
				textPaneOutPanel.add(textPane);
				tabbedPane.add("Tuesday Night Shift Supervisors", textPane);
				StyledDocument doc = textPane.getStyledDocument();
				for(Shift s: allShifts)
				{
					if(s.getDayOfWeek()==Shift.Day.TUESDAY && !s.isDayShift())
					{
						for(Employee em: s.getEmployeesInShift())
						{
							if(em instanceof ShiftSupervisor)
							{
							try{
								doc.insertString(doc.getLength()+1, s.toString(), SimpleAttributeSet.EMPTY );
								}
							catch(BadLocationException ex){}
							}
						}
					}
				}
				}
				catch(NullPointerException ex)
				{
					JOptionPane.showMessageDialog(frame, "The selected shift is empty", null, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		JMenu wednesdayShiftSupervisors = new JMenu("Wednesday");
		JMenuItem wednesdayDayShiftSupervisors = new JMenuItem("Day");
		JMenuItem wednesdayNightShiftSupervisors = new JMenuItem("Night");
		wednesdayShiftSupervisors.add(wednesdayDayShiftSupervisors);
		wednesdayDayShiftSupervisors.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try{
				JTextPane textPane = new JTextPane();
				textPane.setEditable(false);
				JPanel textPaneOutPanel = new JPanel();
				textPaneOutPanel.add(textPane);
				tabbedPane.add("Wednesday Night Shift Supervisors", textPane);
				StyledDocument doc = textPane.getStyledDocument();
				for(Shift s: allShifts)
				{
					if(s.getDayOfWeek()==Shift.Day.WEDNESDAY && s.isDayShift())
					{
						for(Employee em: s.getEmployeesInShift())
						{
							if(em instanceof ShiftSupervisor)
							{
							try{
								doc.insertString(doc.getLength()+1, s.toString(), SimpleAttributeSet.EMPTY );
								}
							catch(BadLocationException ex){}
							}
						}
					}
				}
				}
				catch(NullPointerException ex)
				{
					JOptionPane.showMessageDialog(frame, "The selected shift is empty", null, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		wednesdayShiftSupervisors.add(wednesdayNightShiftSupervisors);
		wednesdayNightShiftSupervisors.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try{
				JTextPane textPane = new JTextPane();
				textPane.setEditable(false);
				JPanel textPaneOutPanel = new JPanel();
				textPaneOutPanel.add(textPane);
				tabbedPane.add("Wednesday Night Shift Supervisors", textPane);
				StyledDocument doc = textPane.getStyledDocument();
				for(Shift s: allShifts)
				{
					if(s.getDayOfWeek()==Shift.Day.WEDNESDAY && !s.isDayShift())
					{
						for(Employee em: s.getEmployeesInShift())
						{
							if(em instanceof ShiftSupervisor)
							{
							try{
								doc.insertString(doc.getLength()+1, s.toString(), SimpleAttributeSet.EMPTY );
								}
							catch(BadLocationException ex){}
							}
						}
					}
				}
				}
				catch(NullPointerException ex)
				{
					JOptionPane.showMessageDialog(frame, "The selected shift is empty", null, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		JMenu thursdayShiftSupervisors = new JMenu("Thursday");
		JMenuItem thursdayDayShiftSupervisors = new JMenuItem("Day");
		JMenuItem thursdayNightShiftSupervisors = new JMenuItem("Night");
		thursdayShiftSupervisors.add(thursdayDayShiftSupervisors);
		thursdayShiftSupervisors.add(thursdayNightShiftSupervisors);
		thursdayNightShiftSupervisors.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try{
				JTextPane textPane = new JTextPane();
				textPane.setEditable(false);
				JPanel textPaneOutPanel = new JPanel();
				textPaneOutPanel.add(textPane);
				tabbedPane.add("Thursday Night Shift Supervisors", textPane);
				StyledDocument doc = textPane.getStyledDocument();
				for(Shift s: allShifts)
				{
					if(s.getDayOfWeek()==Shift.Day.THURSDAY && !s.isDayShift())
					{
						for(Employee em: s.getEmployeesInShift())
						{
							if(em instanceof ShiftSupervisor)
							{
							try{
								doc.insertString(doc.getLength()+1, s.toString(), SimpleAttributeSet.EMPTY );
								}
							catch(BadLocationException ex){}
							}
						}
					}
				}
				}
				catch(NullPointerException ex)
				{
					JOptionPane.showMessageDialog(frame, "The selected shift is empty", null, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		thursdayDayShiftSupervisors.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try{
				JTextPane textPane = new JTextPane();
				textPane.setEditable(false);
				JPanel textPaneOutPanel = new JPanel();
				textPaneOutPanel.add(textPane);
				tabbedPane.add("Thursday Day Shift Supervisors", textPane);
				StyledDocument doc = textPane.getStyledDocument();
				for(Shift s: allShifts)
				{
					if(s.getDayOfWeek()==Shift.Day.THURSDAY && s.isDayShift())
					{
						for(Employee em: s.getEmployeesInShift())
						{
							if(em instanceof ShiftSupervisor)
							{
							try{
								doc.insertString(doc.getLength()+1, s.toString(), SimpleAttributeSet.EMPTY );
								}
							catch(BadLocationException ex){}
							}
						}
					}
				}
				}
				catch(NullPointerException ex)
				{
					JOptionPane.showMessageDialog(frame, "The selected shift is empty", null, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		JMenu fridayShiftSupervisors = new JMenu("Friday");
		JMenuItem fridayDayShiftSupervisors = new JMenuItem("Day");
		JMenuItem fridayNightShiftSupervisors = new JMenuItem("Night");
		fridayShiftSupervisors.add(fridayDayShiftSupervisors);
		fridayShiftSupervisors.add(fridayNightShiftSupervisors);
		fridayDayShiftSupervisors.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try{
				JTextPane textPane = new JTextPane();
				textPane.setEditable(false);
				JPanel textPaneOutPanel = new JPanel();
				textPaneOutPanel.add(textPane);
				tabbedPane.add("Friday Day Shift Supervisors", textPane);
				StyledDocument doc = textPane.getStyledDocument();
				for(Shift s: allShifts)
				{
					if(s.getDayOfWeek()==Shift.Day.FRIDAY && s.isDayShift())
					{
						for(Employee em: s.getEmployeesInShift())
						{
							if(em instanceof ShiftSupervisor)
							{
							try{
								doc.insertString(doc.getLength()+1, s.toString(), SimpleAttributeSet.EMPTY );
								}
							catch(BadLocationException ex){}
							}
						}
					}
				}
				}
				catch(NullPointerException ex)
				{
					JOptionPane.showMessageDialog(frame, "The selected shift is empty", null, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		fridayNightShiftSupervisors.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try{
				JTextPane textPane = new JTextPane();
				textPane.setEditable(false);
				JPanel textPaneOutPanel = new JPanel();
				textPaneOutPanel.add(textPane);
				tabbedPane.add("Friday Night Shift Supervisors", textPane);
				StyledDocument doc = textPane.getStyledDocument();
				for(Shift s: allShifts)
				{
					if(s.getDayOfWeek()==Shift.Day.FRIDAY && !s.isDayShift())
					{
						for(Employee em: s.getEmployeesInShift())
						{
							if(em instanceof ShiftSupervisor)
							{
							try{
								doc.insertString(doc.getLength()+1, s.toString(), SimpleAttributeSet.EMPTY );
								}
							catch(BadLocationException ex){}
							}
						}
					}
				}
				}
				catch(NullPointerException ex)
				{
					JOptionPane.showMessageDialog(frame, "The selected shift is empty", null, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		shiftEmployeeMenuSupervisors.add(mondayShiftSupervisors);
		shiftEmployeeMenuSupervisors.add(tuesdayShiftSupervisors);
		shiftEmployeeMenuSupervisors.add(wednesdayShiftSupervisors);
		shiftEmployeeMenuSupervisors.add(thursdayShiftSupervisors);
		shiftEmployeeMenuSupervisors.add(fridayShiftSupervisors);
		shiftViewMenu.add(shiftEmployeeMenuSupervisors);

		JMenu helpMenu = new JMenu("Help");
		menuBar.add(helpMenu);
		JMenuItem aboutMenuItem = new JMenuItem("About");
		helpMenu.add(aboutMenuItem);
		aboutMenuItem.addActionListener(new ActionListener ()
		{
			public void actionPerformed(ActionEvent event)
			{
				tabbedPane.addTab("About", createAboutTab());
			}
		});
	}
	
}