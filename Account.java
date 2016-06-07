import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by damia_000 on 29.05.2016.
 */
public class Account
{
    private Random rnd = new Random();
    private int number;
    private String name;
    private String surname;
    private String pesel;
    private String miasto;
    private int cash;

    public String getName() {
        return name;
    }
    public String getSurname() {
        return surname;
    }
    public String getPesel() {
        return pesel;
    }
    public String getMiasto() {
        return miasto;
    }
    private void testMoney100$()
    {
        cash = 100;
    }
    public String toString()
    {
        return Integer.toString(number) + name + surname + pesel + miasto + Integer.toString(cash);
    }
    public void getInfo()
    {
        System.out.println("Numer konta: " + number);
        System.out.println("Imie: " + name);
        System.out.println("Nazwisko: " + surname);
        System.out.println("Pesel:: " + pesel);
        System.out.println("Miasto: " + miasto);
        System.out.println("Gotówka: " + cash);
    }
    public List<String> getListInfo()
    {
        return Arrays.asList(Integer.toString(number), name, surname, pesel, miasto, Integer.toString(cash));
    }
    public int getCash()
    {
        return cash;
    }
    public boolean addMoney(int cash)
    {
        this.cash += cash;
        return true;
    }
    Account(int number, String name, String surname, String pesel, String miasto, int cash)
    {
        this(name,surname,pesel,miasto);
        this.number = number;
        this.cash = cash;
    }
    Account(String name, String surname, String pesel, String miasto)
    {
        number = rnd.nextInt(1000);
        this.name = name;
        this.surname = surname;
        this.pesel = pesel;
        this.miasto = miasto;
        cash = 0;
        testMoney100$();
        //System.out.println("Konto zostalo zalozone");
    }
    public boolean subMoney(int cash)
    {
        if (this.cash > cash) {
            this.cash -= cash;
            return true;
        }
        else {
            System.out.println("Masz za malo gotowki!");
            return false;
        }
    }
}