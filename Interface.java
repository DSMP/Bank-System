import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Created by damia_000 on 29.05.2016.
 */
public class Interface {
    BankingSystem bankingSystem = new BankingSystem();
    DBConnection dbConnection = new DBConnection();
    Interface() throws IOException, SQLException {
        System.out.println("Witaj w systemie bankowym");
        //runUserInterface();
    }
    private void showOptionsForRunUserInterfaceFunc()
    {
        System.out.print("Menu: \n");
        System.out.print("1 - Create account \n");
        System.out.print("2 - Delete account \n");
        System.out.print("3 - Make transfer \n");
        System.out.print("4 - Show account \n");
        System.out.print("5 - Add money \n");
        System.out.print("6 - Sub money \n");
        System.out.println("7 - Quit \n");
    }
    void runUserInterface()
    {
        Scanner scanner = new Scanner(System.in);
        boolean QuitBankingMenuLoop = true;
        while (QuitBankingMenuLoop) {
            showOptionsForRunUserInterfaceFunc();
            try {
                switch (scanner.next().charAt(0)) {
                    case '1':
                        bankingSystem.Accounts.add(createAccount());
                        break;
                    case '2':
                        bankingSystem.Accounts.remove(questionAboutID(scanner));
                        break;
                    case '3':
                        makePayMent(bankingSystem.Accounts.get(questionAboutSourceNumber()), bankingSystem.Accounts.get(questionAboutDestinationNumber()), questionAboutHowMuchMoney());
                        break;
                    case '4':
                        questionAboutAllAccounts(bankingSystem.Accounts);
                        break;
                    case '5':
                        addMoney(bankingSystem.Accounts.get(questionAboutID(scanner)), questionAboutHowMuchMoney());
                        break;
                    case '6':
                        subMoney(bankingSystem.Accounts.get(questionAboutID(scanner)), questionAboutHowMuchMoney());
                        break;
                    case '7':
                        QuitBankingMenuLoop = QuitBankingSystem();
                        bankingSystem.safeAccountsToFile();
                        break;
                    case 'q':
                        QuitBankingMenuLoop = QuitBankingSystem();
                        bankingSystem.safeAccountsToFile();
                        break;
                    default:
                        System.out.println("Zla opcja");
                }
            }catch (IndexOutOfBoundsException e)
            {
                System.out.println("Nie ma takiego ID");
                continue;
            }catch (NoSuchElementException e)
            {
                bankingSystem.safeAccountsToFile();
                System.exit(0);
            }
        }
    }
    public boolean QuitBankingSystem()
    {
        System.out.println("Czy Napewno chcesz wyjsc z systemu bankowego? T/N");
        Scanner scanner = new Scanner(System.in);
        char yesNo = scanner.next().charAt(0);
        if (yesNo == 'T' || yesNo == 't') return false;
        else if (yesNo == 'N' || yesNo == 'n') return true;
        return true;
    }
    public int questionAboutID(Scanner scanner)
    {
        System.out.println("Podaj id konta");
        return scanner.nextInt();
    }
    public boolean addMoney(Account tempAccount, int cash)
    {
        System.out.println("Czy Napewno chcesz wplacic gotowke? T/N");
        Scanner scanner = new Scanner(System.in);
        char yesNo = scanner.next().charAt(0);
        if (yesNo == 'T' || yesNo == 't') return tempAccount.addMoney(cash);
        else if (yesNo == 'N' || yesNo == 'n') return false;
        return false;
    }
    public boolean subMoney(Account tempAccount, int cash)
    {
        System.out.println("Czy Napewno chcesz wyplacic gotowke? T/N");
        Scanner scanner = new Scanner(System.in);
        char yesNo = scanner.next().charAt(0);
        if (yesNo == 'T' || yesNo == 't') return tempAccount.subMoney(cash);
        else if (yesNo == 'N' || yesNo == 'n') return false;
        return false;
    }
    //create account
    public Account createAccount() {
        boolean quitAcceptingCreateAcc = false;
        do {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Podaj imie: ");
            String name = scanner.next();
            System.out.print("Podaj nazwisko: ");
            String surname = scanner.next();
            String pesel;
            do {
                System.out.print("Podaj pesel: ");
                pesel = scanner.next();
            } while(!checkPesel(pesel));
            System.out.print("Podaj miasto: ");
            String miasto = scanner.next();
            miasto += scanner.nextLine();
            System.out.print("Czy napewno chcesz stworzyc takie konto? T/N");
            char yesNo = scanner.next().charAt(0);
            if (yesNo == 'T' || yesNo == 't') return new Account(name, surname, pesel, miasto);
            else if (yesNo == 'N' || yesNo == 'n') quitAcceptingCreateAcc = true;
            else System.out.println("T or N. U cannot type another letter");
        } while (quitAcceptingCreateAcc);
        return new Account("NULL", "NULL", "NULL", "NULL");
    }
    private boolean checkPeselLength(String pesel) {
        if (pesel.length() == 11)
        {
            return true;
        }else
        {
            System.out.println("Nie poprawna dlugosc peselu");
            return false;
        }
    }
    private boolean checkPeselIsNumeric(String pesel)
    {
        return pesel.matches("-?\\d+(\\.\\d+)?");
    }
    private boolean checkPesel(String pesel)
    {
        return checkPeselLength(pesel) && (checkPeselIsNumeric(pesel));
    }
    public int questionAboutSourceNumber()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj numer zrodlowy konta: ");
        return scanner.nextInt();
    }
    public int questionAboutDestinationNumber()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj numer docelowy konta: ");
        return scanner.nextInt();
    }
    public int questionAboutHowMuchMoney()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj wartosc gotowki: ");
        return checkPositiveValueOfMoney(scanner.nextInt());
    }
    private int checkPositiveValueOfMoney(int cash)
    {
        return cash >= 0 ? cash : questionAboutHowMuchMoney();
    }
    public boolean makePayMent(Account sAccount, Account dAccount, int cash)
    {
        Scanner scanner = new Scanner(System.in);
        do
        {
            System.out.println("Zaakceptować transfer pieniedzy? T/N");
            char yesNo = scanner.next().charAt(0);
            if (yesNo == 'T' || yesNo == 't')
            {
                if (sAccount.subMoney(cash)) {
                    dAccount.addMoney(cash);
                    return true;
                }
            }
            else if (yesNo == 'N' || yesNo == 'n')
            {
                return false;
            }
            else System.out.println("T or N. U cannot type another letter");
        } while (true);
    }
    public char questionAboutAllAccounts(List<Account> tempAccounts)
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Wyswietlic wszystkie konta? T/N ");
        char answerQuestion = scanner.next().charAt(0);
        if (answerQuestion == 'T' || answerQuestion == 't' )
        {
            showAllAccounts(tempAccounts);
        } else
        {
            try {
                showAccount(tempAccounts).getInfo();
            }catch (NullPointerException e)
            {
                System.out.println("brak takiego konta");
            }
        }
        return answerQuestion;
    }
    private void showOptionsForShowAccountFunc()
    {
        System.out.println("Wybierz kryterium: ");
        System.out.println("1 - Po imieniu?");
        System.out.println("2 - Po nazwisku?");
        System.out.println("3 - Po peselu?");
        System.out.println("4 - Po adresie?");
        System.out.println("5 - Po ID?");
    }
    public Account showAccount(List<Account> tempAccounts)
    {
        showOptionsForShowAccountFunc();
        Scanner scanner = new Scanner(System.in);
        Account LOL = null;
        switch(scanner.next().charAt(0))
        {
            case '0':
                System.out.print("Podaj szukane wyrazenie: ");
                LOL = searchAccount(tempAccounts, scanner.next()+scanner.nextLine());
                break;
            case '1':
                System.out.print("Podaj imie: ");
                LOL = searchAccount(tempAccounts, scanner.next()+scanner.nextLine());
                break;
            case '2':
                System.out.print("Podaj nazwisko: ");
                LOL = searchAccount(tempAccounts, scanner.next()+scanner.nextLine());
                break;
            case '3':
                System.out.print("Podaj pesel: ");
                LOL = searchAccount(tempAccounts, scanner.next()+scanner.nextLine());
                break;
            case '4':
                System.out.print("Podaj adres: ");
                LOL = searchAccount(tempAccounts, scanner.next() +scanner.nextLine());
                break;
            case '5':
                LOL = showAccountByID(tempAccounts, questionAboutID(scanner));
                break;
            default:
                System.out.println("Bledna opcja");
        }
        return LOL;
    }
    private Account searchAccount(List<Account> tempAccounts,String szukanaWartosc)
    {
        int iterator = 0;
        while(iterator != tempAccounts.size()) {
            if (tempAccounts.get(iterator).getName().equals(szukanaWartosc) || tempAccounts.get(iterator).getSurname().equals(szukanaWartosc)
                    || tempAccounts.get(iterator).getPesel().equals(szukanaWartosc) || tempAccounts.get(iterator).getMiasto().equals(szukanaWartosc)) {
                return tempAccounts.get(iterator);
            }
            iterator++;
        }
        return null;
    }
    public Account showAccountByID(List<Account> tempAccounts, int indexOfAccount)
    {
        return tempAccounts.get(indexOfAccount);
    }
    public void showAllAccounts(List<Account> tempAccounts)
    {
        int iterator = 0;
        while(iterator != tempAccounts.size())
        {
            System.out.println("id konta: " + Integer.toString(iterator));
            tempAccounts.get(iterator).getInfo();
            System.out.println();
            iterator++;
        }
    }
}
