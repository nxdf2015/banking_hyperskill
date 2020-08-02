package banking;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
             // System.out.println(Luhn.checkSum(400000424351057l));
        Scanner scanner = new Scanner(System.in);
        Accounts accounts = new Accounts();
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
                                    "2. Log out\n" +
                                    "0. Exit\n");
                            choice = Integer.parseInt(scanner.nextLine());
                            switch (choice){
                                case 1:
                                    System.out.println("Balance: "+account.getBalance());
                                    break;
                                case 2:
                                case 0:
                                    System.out.println("You hava successfully logged out!");
                                    break;
                            }
                        }while(!(choice == 0 || choice == 2));
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
