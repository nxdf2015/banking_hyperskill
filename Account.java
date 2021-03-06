package banking;

public class Account {
    private int balance;

    public Account(int balance) {
        this.balance = balance;
    }

    public Account() {
    }

    public int getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "balance=" + balance +
                '}';
    }

    public void addIncome(long income) {
        balance += income;

    }

    public boolean transferIsValid(long valueToTransfer) {
        return balance >= valueToTransfer;
    }
}
