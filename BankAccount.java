/**
 * A simple bank account that holds a balance and can be frozen.
 * A frozen account rejects deposits and withdrawals.
 */
public class BankAccount {
  /** The current balance of the account. */
  private double balance;

  /** Whether the account is currently frozen. */
  private boolean frozen;

  /** Initializes balance with 0 and frozen with false. */
  public BankAccount() {
    this.balance = 0;
    this.frozen = false;
  }

  /**
   * Deposits money into the account.
   *
   * @param amount the amount to deposit; must be a positive, finite number
   * @throws IllegalArgumentException if the amount is not a positive, finite number
   * @throws IllegalStateException if the account is frozen
   */
  public void deposit(double amount) {
    validateAmount(amount);
    ensureNotFrozen();
    balance += amount;
  }

  /**
   * Withdraws money from the account.
   *
   * @param amount the amount to withdraw; must be a positive, finite number
   * @throws IllegalArgumentException if the amount is not a positive, finite number
   *     or exceeds the current balance
   * @throws IllegalStateException if the account is frozen
   */
  public void withdraw(double amount) {
    validateAmount(amount);
    ensureNotFrozen();
    if (amount > balance) {
      throw new IllegalArgumentException("Insufficient funds");
    }
    balance -= amount;
  }

  /**
   * Returns the current balance.
   *
   * @return the current balance
   */
  public double getBalance() {
    return balance;
  }

  /** Freezes the account, blocking deposits and withdrawals. */
  public void freezeAccount() {
    frozen = true;
  }

  /** Unfreezes the account, allowing deposits and withdrawals again. */
  public void unfreezeAccount() {
    frozen = false;
  }

  /**
   * Tells whether the account is frozen.
   *
   * @return {@code true} if the account is frozen, otherwise {@code false}
   */
  public boolean isFrozen() {
    return frozen;
  }

  /**
   * Ensures the account is not frozen.
   *
   * @throws IllegalStateException if the account is frozen
   */
  private void ensureNotFrozen() {
    if (frozen) {
      throw new IllegalStateException("Account is frozen");
    }
  }

  /**
   * Validates a transaction amount.
   *
   * @param amount the amount to validate
   * @throws IllegalArgumentException if the amount is not a positive, finite number
   */
  private static void validateAmount(double amount) {
    if (amount <= 0 || !Double.isFinite(amount)) {
      throw new IllegalArgumentException("Invalid amount. Must be greater than 0");
    }
  }
}
