/** A savings account that belongs to a named owner. */
public class SavingsAccount extends BankAccount {
  /** The name of the account owner. */
  private final String ownerName;

  /**
   * Initializes the account owner's name. Balance starts at 0 and the account is not frozen.
   *
   * @param ownerName the name of the account owner
   */
  public SavingsAccount(String ownerName) {
    super();
    this.ownerName = ownerName;
  }

  /**
   * Returns the name of the account owner.
   *
   * @return the owner's name
   */
  public String getOwnerName() {
    return ownerName;
  }
}
