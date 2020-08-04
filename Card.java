package banking;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Card {
    int IIN = 400000;
    long   pin;
    long number;

    public Card(long id, long p) {
        number = id;
        pin = p;
    }

    public static Card of(long id, long p) {
        Card card = new Card(id,p);
        return card;

    }

    public Card() {
    }

    public  void create(){
        pin = generate(4);
        number = generate(9);

        long checkSum = Luhn.checkSum(4 * (long)Math.pow(10,16) + number);
        number = Long.parseLong(number+""+checkSum);
    }

    public long getPin() {
        return pin;
    }



    public long getNumber() {
        return number;
    }

    public long getIdentifier(){
        return Long.parseLong(IIN +""+number);
    }



    private long generate(int n){
        Random random = new Random();
      String  value ="";
      int v ;
      while ((v = random.nextInt(10)) == 0){

      }
      value += v;
      for(int i = 0 ; i < n-1 ; i++){

          value += random.nextInt(10);
      }
        return Long.parseLong(value);
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return getPin() == card.getPin() &&
                getNumber() == card.getNumber();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPin(), getNumber());
    }

    @Override
    public String toString() {
        return "Card{" +
                "IIN=" + IIN +
                ", pin=" + pin +
                ", number=" + number +
                '}';
    }
}
