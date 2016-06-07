import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Created by damia_000 on 29.05.2016.
 */
public class BankingSystem
{
    public List<Account> Accounts = new ArrayList<Account>();
    private File file = new File("BankingSystemAccounts.txt");
    BankingSystem() throws IOException {
        try {
            loadAccountsFromFile();
        }finally {
            file = new File("tempBankingSystem.txt");
            safeAccountsToFile();
        }
    }
    public void safeAccountsToFile() {
        try {
            if (!file.exists())
            {
                Files.createFile(file.toPath());
            } else
            {
                Files.deleteIfExists(file.toPath());
                Files.createFile(file.toPath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            List<String> listaIloscKont = new ArrayList<>();
            listaIloscKont.add(Integer.toString(Accounts.size()));
            Files.write(file.toPath(), listaIloscKont, Charset.forName("UTF-8"), StandardOpenOption.APPEND);
            int iterator = 0;
            while(iterator != Accounts.size())
            {
                Files.write(file.toPath(), Accounts.get(iterator).getListInfo(), Charset.forName("UTF-8"), StandardOpenOption.APPEND);
                iterator++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void loadAccountsFromFile() throws IOException {
        int iterator = 0, quantityOfAccountsInFile;
        if (!file.exists())
        {
            System.out.println("Brak Bazy kont");
        } else {
            List<String> AllFILE = Files.readAllLines(file.toPath());
            quantityOfAccountsInFile = Integer.parseInt(AllFILE.get(0));
            List<String> FILE = AllFILE.subList(1, AllFILE.size());
            int iterBase;
            while (iterator != quantityOfAccountsInFile)
            {
                iterBase = iterator*6;
                Accounts.add(iterator, new Account(Integer.parseInt(FILE.get(iterBase)), FILE.get(iterBase + 1), FILE.get(iterBase + 2), FILE.get(iterBase + 3), FILE.get(iterBase + 4), Integer.parseInt(FILE.get(iterBase + 5))));
                iterator++;
            }
        }
    }
    /*private void showOptionsForRunUserInterfaceFunc()
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
        Interface interface1 = new Interface();
        Scanner scanner = new Scanner(System.in);
        boolean QuitBankingMenuLoop = true;
        while (QuitBankingMenuLoop) {
            showOptionsForRunUserInterfaceFunc();
            try {
                switch (scanner.next().charAt(0)) {
                    case '1':
                        Accounts.add(interface1.createAccount());
                        break;
                    case '2':
                        Accounts.remove(interface1.questionAboutID(scanner));
                        break;
                    case '3':
                        interface1.makePayMent(Accounts.get(interface1.questionAboutSourceNumber()), Accounts.get(interface1.questionAboutDestinationNumber()), interface1.questionAboutHowMuchMoney());
                        break;
                    case '4':
                        interface1.questionAboutAllAccounts(Accounts);
                        break;
                    case '5':
                        interface1.addMoney(Accounts.get(interface1.questionAboutID(scanner)), interface1.questionAboutHowMuchMoney());
                        break;
                    case '6':
                        interface1.subMoney(Accounts.get(interface1.questionAboutID(scanner)), interface1.questionAboutHowMuchMoney());
                        break;
                    case '7':
                        QuitBankingMenuLoop = interface1.QuitBankingSystem();
                        safeAccountsToFile();
                        break;
                    case 'q':
                        QuitBankingMenuLoop = interface1.QuitBankingSystem();
                        safeAccountsToFile();
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
                safeAccountsToFile();
                System.exit(0);
            }
        }
    }*/
}
