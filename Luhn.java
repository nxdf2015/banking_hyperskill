package banking;

public class Luhn {
    public static boolean check(Long number){

          return (sum(number / 10 ) + number % 10 ) % 10 == 0;
    }


    public static long sum(long number){
        int  i = 0 ;
        long total = 0;
        while (number > 0){
            long digit = number % 10 ;

            if  (i % 2 == 0){
                digit = 2*(digit);
               if (digit > 9){
                   digit -= 9;
               }


           }

            total += digit;
            number = number /10;
           i++;
        }

        return total ;
    }

    public static long checkSum(long number){
        long n = sum(number);
        for(int i = 0 ; i <10 ; i++){
            if ((n + i) % 10 == 0){
                return i;
            }
        }
        return -1;
    }
}
