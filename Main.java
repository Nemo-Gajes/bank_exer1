import java.util.Scanner;

/**
 * Interactive console front-end for a savings account.
 *
 * <p>The user enters the account owner's name and then drives the account from a
 * menu: deposit, withdraw, freeze, unfreeze, show the balance, or quit.
 */
public class Main {
  /** Menu shown on every loop iteration. */
  private static final String MENU =
      "\n===== Bank Account =====\n"
          + "1. Deposit money\n"
          + "2. Withdraw money\n"
          + "3. Freeze account\n"
          + "4. Unfreeze account\n"
          + "5. Show balance and status\n"
          + "6. Quit\n"
          + "Choose an option (1-6): ";

  /**
   * The account the user operates on.
   *
   * <p>It is declared as a {@link BankAccount} reference even though the object created is a
   * {@link SavingsAccount}. This is <em>upcasting</em>: assigning a subclass object to a
   * superclass reference. The reference type only exposes what {@link BankAccount} declares,
   * while the object behind it keeps its {@link SavingsAccount} behaviour.
   */
  private final BankAccount account;

  /** The name of the account owner. */
  private final String ownerName;

  /**
   * Creates the app around an account.
   *
   * <p>The account is received as a {@link BankAccount} reference even though the object passed
   * in is a {@link SavingsAccount}. This is <em>upcasting</em>: assigning a subclass object to a
   * superclass reference. The reference type only exposes what {@link BankAccount} declares,
   * while the object behind it keeps its {@link SavingsAccount} behaviour.
   *
   * @param ownerName the name of the account owner
   * @param account the account the user operates on, held as a superclass reference
   */
  public Main(String ownerName, BankAccount account) {
    this.ownerName = ownerName;
    this.account = account;
  }

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    System.out.print("Enter account holder's name: ");
    String name = in.nextLine().trim();
    while (name.isEmpty()) {
      System.out.print("Name cannot be empty. Enter account holder's name: ");
      name = in.nextLine().trim();
    }

    // Upcasting: the SavingsAccount object is assigned to a BankAccount reference.
    BankAccount account = new SavingsAccount(name);
    Main app = new Main(name, account);
    System.out.println("Account Owner: " + name + ".");
    System.out.println("Upcasting demo: created a " + account.getClass().getSimpleName()
        + " stored in a " + BankAccount.class.getSimpleName() + " reference.");
    app.run(in);
  }

  /**
   * Runs the menu loop until the user chooses to quit.
   *
   * @param in the scanner to read the choices from
   */
  public void run(Scanner in) {
    boolean running = true;
    while (running) {
      System.out.print(MENU);
      String choice = in.nextLine().trim();

      switch (choice) {
        case "1" -> doDeposit(in);
        case "2" -> doWithdraw(in);
        case "3" -> doFreeze();
        case "4" -> doUnfreeze();
        case "5" -> showStatus();
        case "6" -> {
          System.out.println("Goodbye!");
          running = false;
        }
        default -> System.out.println("Invalid option. Please choose a number from 1 to 6.");
      }
    }
    in.close();
  }

  /**
   * Asks for an amount and deposits it.
   *
   * @param in the scanner to read the amount from
   */
  private void doDeposit(Scanner in) {
    Double amount = readAmount(in, "Enter deposit amount: ");
    if (amount == null) {
      return;
    }
    try {
      account.deposit(amount);
      System.out.printf("Amount Deposited: %.2f %n", amount);
      System.out.printf("Current balance: %.2f%n", account.getBalance());
    } catch (IllegalStateException e) {
      System.out.println(e.getMessage() + ". Unable to Transact.");
    } catch (IllegalArgumentException e) {
      System.out.println("Deposit failed: " + e.getMessage());
    }
  }

  /**
   * Asks for an amount and withdraws it.
   *
   * @param in the scanner to read the amount from
   */
  private void doWithdraw(Scanner in) {
    Double amount = readAmount(in, "Enter withdrawal amount: ");
    if (amount == null) {
      return;
    }
    try {
      account.withdraw(amount);
      System.out.printf("Amount Withdrawn: %.2f %n", amount);
      System.out.printf("Current balance: %.2f%n", account.getBalance());
    } catch (IllegalStateException e) {
      System.out.println(e.getMessage() + ". Unfreeze the account first.");
    } catch (IllegalArgumentException e) {
      System.out.println("Withdrawal failed: " + e.getMessage());
      System.out.printf("Current balance: %.2f%n", account.getBalance());
    }
  }

  /** Freezes the account. */
  private void doFreeze() {
    account.freezeAccount();
    System.out.println("Account has been frozen. Deposits and withdrawals are blocked.");
  }

  /** Unfreezes the account. */
  private void doUnfreeze() {
    account.unfreezeAccount();
    System.out.println("Account has been unfrozen. Deposits and withdrawals are allowed again.");
  }

  /** Prints the current balance and frozen status. */
  private void showStatus() {
    System.out.printf("Owner: %s%n", ownerName);
    System.out.printf("Balance: %.2f%n", account.getBalance());
    System.out.println("Status: " + (account.isFrozen() ? "FROZEN" : "ACTIVE"));
  }

  /**
   * Reads an amount from the console.
   *
   * @param in the scanner to read from
   * @param prompt the text shown before reading
   * @return the amount, or {@code null} if the input was not a valid number
   */
  private Double readAmount(Scanner in, String prompt) {
    System.out.print(prompt);
    String raw = in.nextLine().trim();
    try {
      return Double.parseDouble(raw);
    } catch (NumberFormatException e) {
      System.out.println("'" + raw + "' is not a valid number. Please enter a numeric amount.");
      return null;
    }
  }
}