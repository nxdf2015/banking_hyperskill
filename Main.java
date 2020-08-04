package banking;

import org.sqlite.SQLiteDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {

        String nameDatabase = args[1];


        Scanner scanner = new Scanner(System.in);
        Accounts accounts = new Accounts();
        accounts.load(nameDatabase);
        while(true){
            System.out.println("1. Create an account\n" +
                    "2. Log into account\n" +
                    "0. Exit\n");
            int choice = Integer.parseInt(scanner.nextLine());

            if(choice == 0){
                break;
            }
            switch (choice){
                case 1:
                    accounts.create();
                    break;
                case 2:
                    System.out.println("Enter your card number:");
                    String number = scanner.nextLine();
                    System.out.println("Enter your PIN:");
                    String pin = scanner.nextLine();
                    Account account;

                    if((account = accounts.log(number,pin)) != null){

                        do{
                            System.out.println("1. Balance\n" +
                                    "2. Add income\n" +
                                    "3. Do transfer\n" +
                                    "4. Close account\n" +
                                    "5. Log out\n" +
                                    "0. Exit");
                            choice = Integer.parseInt(scanner.nextLine());
                            switch (choice){
                                case 1:
                                    System.out.println("Balance: "+account.getBalance());
                                    break;
                                case 2:
                                    System.out.println("Enter income");
                                    long income = Long.parseLong(scanner.nextLine());
                                    account.addIncome(income);
                                    accounts.updateBalance(number,income);
                                    break;
                                case 3:
                                    System.out.println("Transfer");
                                    System.out.println("Enter card number:");
                                    String numberTransfer = scanner.nextLine();

                                    if (!Luhn.check(Long.parseLong(numberTransfer))){
                                        System.out.println("Probably you made mistake in the card number. Please try again!");
                                    }
                                    else if (!accounts.isValidNumber(numberTransfer)) {
                                        System.out.println("Such a card does not exist.");
                                    }
                                    else if(numberTransfer.equals(number)){
                                        System.out.println("You can't transfer money to the same account!");
                                    }
                                    else  {
                                        System.out.println("Enter how much money you want to transfer:");
                                        long valueToTransfer = Long.parseLong(scanner.nextLine());
                                        if (!account.transferIsValid(valueToTransfer)) {
                                            System.out.println("Not enough money!");
                                        }
                                        else{
                                            Account accountTransfer = accounts.findById(numberTransfer);
                                            accountTransfer.addIncome(valueToTransfer);
                                            account.addIncome(-valueToTransfer);
                                            accounts.updateBalance(number,-valueToTransfer);
                                            accounts.updateBalance(numberTransfer,valueToTransfer);
                                            System.out.println("success!");
                                        }
                                    }




                                    break;
                                case 4:
                                    accounts.delete(number);
                                    System.out.println("The account has been closed!");
                                    break;
                                case 5:
                                case 0:
                                    System.out.println("You hava successfully logged out!");
                                    break;
                            }
                        }while(!(choice == 0 || choice == 5));
                    }


            }
            if (choice == 0){
                break;
            }
            System.out.println();

        }

        System.out.println("Bye!");

    }
}
