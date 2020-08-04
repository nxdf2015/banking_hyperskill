package banking;

import org.sqlite.SQLiteDataSource;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Data {
    private String url;
    private SQLiteDataSource dataSource;
    public Data(String nameBase) {
        this.url = String.format("jdbc:sqlite:%s",nameBase);
        dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);
    }

    public boolean tableExist(String name){
        Connection connection = null;
        try{
              connection = dataSource.getConnection();
              if (connection == null){
                  connection = DriverManager.getConnection(url);

              }
              createTable("card");
              connection.createStatement().executeQuery("select * from card");

        } catch (SQLException throwables) {
           throwables.printStackTrace(); }
        finally {
            try {

                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return true;
    }

    private void createTable(String name) {

        try(Connection connection = dataSource.getConnection()){

            Statement statement = connection.createStatement();

            statement.executeUpdate("create table if not exists card(id INTEGER PRIMARY KEY AUTOINCREMENT, number TEXT,pin TEXT, balance INTEGER DEFAULT 0)");
        } catch (SQLException throwables) {

           throwables.printStackTrace();
        }

    }

    public List<Card>  getCard(){
        List<Card> cards= new ArrayList<>();
        try(Connection connection =dataSource.getConnection()){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select number, pin from card");

            while(resultSet.next()){
                cards.add(new Card(resultSet.getLong(1),
                resultSet.getLong(2)));
            }
            return cards;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }


    public Map<Long, Account> getAccount(){
        Map<Long,Account> accounts = new HashMap<>();
        try(Connection connection = dataSource.getConnection()){
          Statement statement =   connection.createStatement();
          ResultSet resultSet = statement.executeQuery("select number,balance from card");
          while(resultSet.next()){
              long number = Long.parseLong(resultSet.getString(1));

              int balance = resultSet.getInt(2);

              accounts.put(number , new Account(balance) );

          }
          return accounts;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }


    public boolean save(long number, long pin, int balance) {
        try(Connection connection = dataSource.getConnection()){

            Statement statement = connection.createStatement();
            String query = String.format("insert into card (number,pin,balance) values(%d,%d,%d)", number, pin,balance);
            System.out.println("1---------------------------------");
            int v = statement.executeUpdate(query);
            System.out.println("number insert ---------------------"+v);
            if (v == 1){
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }


}
