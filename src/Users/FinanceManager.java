package Users;

import java.util.Date;
import java.io.Serializable;

import Utils.Database;

/**
 * This class represents finance manager in the university system.
 * 
 * Finance manager works with budget,
 * pays salaries and checks student debts.
 */
public class FinanceManager extends Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    private double budget;

    /**
     * Creates a finance manager object.
     *
     * @param id finance manager id
     * @param username finance manager username
     * @param password finance manager password
     * @param name finance manager name
     * @param surname finance manager surname
     * @param salary finance manager salary
     * @param department department name
     * @param hireDate hire date
     */
    public FinanceManager(String id, String username, String password, String name, String surname,
                          double salary, String department, Date hireDate) {

        super(id, username, password, name, surname, salary, department, hireDate);
        this.budget = 0.0;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    /**
     * Shows current budget.
     *
     * @return current budget
     */
    public double viewBudget() {

        System.out.println("Current budget: " + String.format("%,.0f ₸", budget));
        return budget;
    }

    /**
     * Pays salary to one employee.
     *
     * @param e employee who gets salary
     */
    public void giveSalary(Employee e) {

        if (budget >= e.getSalary()) {
            budget -= e.getSalary();

            System.out.println("Salary paid to " + e.getName() + ": " + e.getSalary());
        } else {
            System.out.println("Not enough budget to pay " + e.getName());
        }
    }

    /**
     * Shows salary payment information.
     */
    public void checkSalaryPaid() {

        System.out.println("Checking salary payments... Budget remaining: " + budget);
    }

    /**
     * Checks debts of one student.
     *
     * @param s student whose debts are checked
     */
    public void checkDebts(Student s) {

        s.viewDebts();
    }

    /**
     * Shows all students who have debts.
     */
    public void viewAllDebts() {

    	System.out.println("========================================================");
    	System.out.println("                 STUDENTS WITH DEBTS");
    	System.out.println("========================================================");

        boolean any = false;
        double grandTotal = 0;

        for (User u : Database.getInstance().getAllUsers()) {

            if (u instanceof Student) {

                Student s = (Student) u;
                double debt = s.calcDebt();

                if (debt > 0) {

                    String fullName = s.getName() + " " + s.getSurname();

                    if (fullName.length() > 22) {
                        fullName = fullName.substring(0, 22);
                    }

                    System.out.printf(
                        "%-22s | Debt: %12.0f KZT%n",
                        fullName,
                        debt
                    );

                    grandTotal += debt;
                    any = true;
                }
            }
        }

        if (!any) {

            System.out.println("| No students with debt.                              |");

        } else {

        	System.out.println("========================================================");

        	System.out.printf(
        	    "Grand total debts: %20.0f KZT%n",
        	    grandTotal
        	);
        }
        	System.out.println("========================================================");
    }

    /**
     * Pays salary to all employees in the system.
     */
    public void manageSalary() {

        for (User u : Database.getInstance().getAllUsers()) {
            if (u instanceof Employee) {
                giveSalary((Employee) u);
            }
        }
    }

    /**
     * Returns short information about finance manager.
     *
     * @return finance manager information
     */
    @Override
    public String toString() {

        return "FinanceManager: " + getName()
                + " | Budget: " + String.format("%,.0f ₸", budget);
    }
}