import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by damia_000 on 29.05.2016.
 */
public class DBConnection {
    private java.sql.PreparedStatement pst = null;
    private ResultSet rs = null;
    private Connection conn = null;
    private int uprs;

    DBConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Brak drivera JDBC dla mySQL");
        }

        try {
            conn = DriverManager.getConnection("jdbc:mysql://85.10.205.173:3306/bankingsystem?allowMultiQueries=true", "dsmp", "MaciejGuzy"); // MySQL

            if (conn != null) System.out.println("Nazwiązano polaczenie z baza");
        } catch (Exception se) {
            System.out.println("Brak polaczenia z baza");
        }
        //Testing

       /* for (String stringi:getAllUsers()) {
            System.out.println(stringi);
        }*/

        //setCashBySurname(60,"Cebula");
//        removeByName("Damian");
        /*add("Michal","Soczynski","12123211","lodz");
        add("Zbigniew","Cebula","96080105123","lodz");

        for (String stringi:getAllUsers()) {
            System.out.println(stringi);
        }

        System.out.println("");
        System.out.println(getUserByID(1));*/

    }

    public ArrayList<String> getAllUsers()
    {
        ArrayList<String> usersArray = new ArrayList<>();
        try {
            pst = conn.prepareStatement("SELECT * FROM users");
            rs = pst.executeQuery();
            while (rs.next()) {
                usersArray.add(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4) + " " + rs.getString(5) + " " + rs.getInt(6));
            }
        } catch (SQLException se) {
            se.printStackTrace();
            System.out.println("Nie udalo sie wyswietlic klientow");
        }
        return usersArray;
    }

    private String getUser(String querry)
    {
        try {
            pst = conn.prepareStatement(querry);
            rs = pst.executeQuery();
            if(rs.next())
                return rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4) + " " + rs.getString(5) + " " + rs.getInt(6);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Nie udalo sie wyswietlic klienta");
        }
        return null;
    }
    public String getUserByID(int id)
    {
        return getUser("SELECT * FROM users WHERE id=" + id + ";\n");
    }

   /* public String getTestUser(int id, String name, String surname, String pesel, String adres)
    {
        return getUser("SELECT * FROM users WHERE id=" + id + " AND Imie=" + name + " AND Nazwisko= " + surname + " AND Pesel= " + pesel + "AND Adres= " + adres + ";\n");
    }*/

    public String getUserByName(String name)
    {
        return getUser("SELECT * FROM users WHERE Imie='" + name + "';\n");
    }
    public String getUserByPesel(String pesel)
    {
        return getUser("SELECT * FROM users WHERE Pesel='" + pesel + "';\n");
    }

    public String getUserBySurname(String surname)
    {
        return getUser("SELECT * FROM users WHERE Nazwisko='" + surname + "';\n");
    }

    public String getUserByAdres(String adres)
    {
        return getUser("SELECT * FROM users WHERE Adres='" + adres + "';\n");
    }

    private void setCash(String querry)
    {
        try {
            pst = conn.prepareStatement(querry);
            uprs = pst.executeUpdate();
        } catch(SQLException se)
        {
            se.printStackTrace();
            System.out.println("Nie udalo sie wykonac operacji aktualizacji transferu gotówki");
        }
    }

    public void setCashByID(int cash, int id)
    {
        setCash("Update users SET Gotowka = " + cash + " WHERE id=" + id + ";\n");
    }

    public void setCashByName(int cash, String name)
    {
        setCash("Update users SET Gotowka = " + cash + " WHERE Imie='" + name + "';\n");
    }

    public void setCashBySurname(int cash, String surname)
    {
        setCash("Update users SET Gotowka = " + cash + " WHERE Nazwisko='" + surname + "';\n");
    }

    public void setCashByAdres(int cash, String adres)
    {
        setCash("Update users SET Gotowka = " + cash + " WHERE Adres='" + adres + "';\n");
    }
    public void setCashByPesel(int cash, String pesel)
    {
        setCash("Update users SET Gotowka = " + cash + " WHERE Pesel='" + pesel + "';\n");
    }

    private void remove(String querry)
    {
        try {
            pst = conn.prepareStatement(querry);
            uprs = pst.executeUpdate();
        } catch(SQLException se)
        {
            se.printStackTrace();
            System.out.println("Nie udalo sie wykonac operacji usuwania konta");
        }
    }
    public void removeByID(int id)
    {
        remove("Delete FROM users WHERE id=" + id + ";\n");
    }

    public void removeByName(String name)
    {
        remove("Delete FROM users WHERE Imie='" + name + "';\n");
    }

    public void removeBySurname(String surname)
    {
        remove("Delete FROM users WHERE Nazwisko='" + surname + "';\n");
    }

    public void removeByAdres(String adres)
    {
        remove("Delete FROM users WHERE Adres='" + adres + "';\n");
    }

    public void removeByPesel(String pesel)
    {
        remove("Delete FROM users WHERE Pesel='" + pesel + "';\n");
    }

    public void add(String name, String surname, String pesel, String adres)
    {
        try {
            pst = conn.prepareStatement("INSERT INTO users(Imie,Nazwisko,Pesel,Adres) values ('" + name + "','" + surname + "','" + pesel + "','" + adres + "')");
            uprs = pst.executeUpdate();
        } catch(MySQLIntegrityConstraintViolationException se)
        {
            System.out.println("Istnieje juz konto z takim peselem");
        } catch(SQLException se)
        {
            se.printStackTrace();
            System.out.println("Nie udalo sie wykonac operacji tworzenia konta");
        }
    }

    public int getLastID()
    {
        try {
        pst = conn.prepareStatement("SELECT id FROM users ORDER BY id DESC LIMIT 1;");
        rs = pst.executeQuery();
            rs.next();
        return rs.getInt(1);
    } catch(SQLException se)
    {
        se.printStackTrace();
        System.out.println("Nie udalo sie wykonac operacji pobrania id usera");
    }
        return 0;
    }

    public Connection getConn() {
        return conn;
    }
}
