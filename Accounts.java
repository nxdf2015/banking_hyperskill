package banking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Accounts {
    List<Card> numbers ;
    Map<Long,Account> account ;
    Data data;
    private boolean log;

    public Accounts() {
        numbers = new ArrayList<>();
        account = new HashMap<>();
    }

    public void create() throws Exception {
        Card card = new Card();
        while(true){
            card.create();
            if (!numbers.contains(card)){
                numbers.add(card);
                account.put(card.getNumber(),new Account());
                if(!data.save(card.getIdentifier(),card.pin,0)){
                    throw new Exception("error base");
                }
                break;
            }
        }

        System.out.printf("Your card has been created\n" +
                "Your card number:\n" +
                "%d\n" +
                "Your card PIN:\n" +
                "%d\n",card.getIdentifier(),card.getPin());
    }
    private boolean isValid(String number){
        return Luhn.check(Long.parseLong(number));
    }

    public Account   log(String number, String pin) {
        String iin = number.substring(0,6);
        long id  = Long.parseLong(number.substring(6,number.length()));
        long p = Long.parseLong(pin);

        if (!(iin.equals("400000") && number.length() == 16 && pin.length() == 4)){
            return null;
        }
        System.out.println();
        if(!isValid(number)){
            System.out.println("Wrong card number or PIN!\n");
            return null;
        }

            Card card = Card.of(id,p);

            if ( numbers.contains(card)){
                log = true;
                System.out.println("you have successfully logged in!");
                return account.get(id);
            }
            else {
                return null;
            }


    }

    public void load(String nameDatabase) {
        data = new Data(nameDatabase);
        data.tableExist("card");
        if((numbers = data.getCard())== null){
            numbers = new ArrayList<>();
        }

        if((account = data.getAccount()) == null){
            account = new HashMap<>();
        }

    }


}
