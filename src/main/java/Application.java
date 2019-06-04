import enums.RecordType;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.*;
import entities.*;


import org.hibernate.Session;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Application {

    public static void main(String[] args) {


        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.close();

        Application app = new Application();

        int currentLevel = 0, currentPage = 0;

        Scanner scanner = new Scanner(System.in);

        showMenu(currentLevel, currentPage);

        while(true){

            int command = Integer.parseInt(scanner.nextLine());

            if (currentLevel == 0){
                if (command == 5) break;
                else {
                    currentLevel = 1;
                    currentPage = command;
                    showMenu(currentLevel, currentPage);
                }

            } else {
                if (app.doCommand(currentPage, command)) {
                    currentLevel = 0;
                    currentPage = 0;
                }
                showMenu(currentLevel, currentPage);
            }
        }

    }

    static void showMenu(int currentLevel, int currentPage){

        switch (currentLevel){

            case 0: //main menu
                System.out.println("Введите команду:");
                System.out.println("1) Счета");
                System.out.println("2) Статьи дохода/расхода");
                System.out.println("3) Документы дохода/расхода");
                System.out.println("4) Отчеты");
                System.out.println("5) Выход");
                break;

            case 1: //one of chapters was chosen before

                switch(currentPage){

                    case 1: //accounts
                        System.out.println("Введите команду:");
                        System.out.println("1) Добавить счет");
                        System.out.println("2) Вывести список счетов");
                        System.out.println("3) Удалить счет");
                        System.out.println("4) В главное меню");
                        break;

                    case 2: //budget rows
                        System.out.println("Введите команду:");
                        System.out.println("1) Добавить статью дохода/расхода");
                        System.out.println("2) Вывести список статей");
                        System.out.println("3) Удалить статью");
                        System.out.println("4) В главное меню");
                        break;

                    case 3: //budget records
                        System.out.println("Введите команду:");
                        System.out.println("1) Добавить документ дохода/расхода");
                        System.out.println("2) Вывести список документов");
                        System.out.println("3) Удалить документ");
                        System.out.println("4) В главное меню");
                        break;

                    case 4: //reports
                        System.out.println("Введите команду:");
                        System.out.println("1) Отчет об остатках");
                        System.out.println("2) Отчет по оборотам");
                        System.out.println("3) В главное меню");
                        break;

                }
        }

    }

    boolean doCommand(int currentPage, int command){

        boolean backToMainMenu = false;

        switch (currentPage){

            case 1: //accounts

                switch(command){

                    case 1: //add account

                        addAccount();
                        break;

                    case 2: //view accounts

                        listAccounts();
                        break;

                    case 3: //delete account

                        deleteAccount();
                        break;


                    case 4: backToMainMenu = true; break;

                    default:
                        System.out.println("Некорректная комманда!");
                        break;

                }
                break;


            case 2: //budget rows

                switch(command){

                    case 1: //add br

                        addBudgetRow();
                        break;

                    case 2: //view br

                        listBudgetRow();
                        break;

                    case 3: //delete br

                        deleteBudgetRow();
                        break;


                    case 4: backToMainMenu = true; break;

                    default:
                        System.out.println("Некорректная комманда!");
                        break;



                }
                break;



            case 3: //records

                switch(command){

                    case 1: //add record

                        addRecord();
                        break;

                    case 2: //view records

                        System.out.println("Вывод списка документов");
                        break;

                    case 3: //delete record

                        System.out.println("Удаление документа");
                        break;


                    case 4: backToMainMenu = true; break;

                    default:
                        System.out.println("Некорректная комманда!");
                        break;



                }
                break;


            case 4: //reports

                switch(command){

                    case 1: //results

                        System.out.println("Отчет по остаткам");
                        break;

                    case 2: //movements

                        System.out.println("Отчет по оборотм");
                        break;

                    case 3: backToMainMenu = true; break;

                    default:
                        System.out.println("Некорректная комманда!");
                        break;

                }
                break;

        }

        return backToMainMenu;

    }

    void addAccount(){

        System.out.println("Добавление счета. Введите имя счета:");

        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();

        if (!name.equals("")){

            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.openSession();

            Account account = new Account(name);

            Transaction transaction = session.beginTransaction();
            session.save(account);
            transaction.commit();

            session.close();

            System.out.println("Успешно добавлен новый счет");

        }
        else System.out.println("ОШИБКА!!! Некорректное имя счета, операция не выполнена");

    }

    void listAccounts(){

        System.out.println("Вывод списка счетов");

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        Query query = session.createQuery("From "+Account.class.getSimpleName());

        for(Account acc: (List<Account>)query.list())
            System.out.println(acc);

        System.out.println("");

        session.close();

    }

    void deleteAccount(){

        System.out.println("Удаление счета");

        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите код удаляемого счета:");

        int num = Integer.parseInt(scanner.nextLine());

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();
        Query query = session.createQuery("DELETE FROM "+Account.class.getSimpleName() +" where id = :paramId");
        query.setParameter("paramId", num);
        int result = query.executeUpdate();
        session.getTransaction().commit();
        session.close();

        if (result==1)
            System.out.println("Счет успешно удален");
        else
            System.out.println("Введен некорректный номер счета");

    }





    void addBudgetRow(){

        Scanner scanner = new Scanner(System.in);

        System.out.println("Добавление статьи доходов и расходов.");

        int rowType;
        do {
            System.out.println("Введите вид статьи (0 - расход, 1 - доход, 2 - перемещение):");

            try {
                rowType = Integer.parseInt(scanner.nextLine());
                if (!(rowType == 0 || rowType == 1 || rowType == 2)) throw new ParseException("Incorrect record type",0);
                break;

            }catch (ParseException e){
                System.out.println("Введены некорректные данные! Повторите ввод.");
            }

        } while(true);

        String name;

        do {
            System.out.println("Введите название статьи:");
            name = scanner.nextLine();
        }
        while(name.equals(""));


            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.openSession();

            BudgetRow budgetRow;
            if (rowType == 0 )
                budgetRow = BudgetRow.costBudgetRow(name);
            else if (rowType == 1)
                budgetRow = BudgetRow.incomeBudgetRow(name);
            else
                budgetRow = BudgetRow.movementBudgetRow(name);

            Transaction transaction = session.beginTransaction();
            session.save(budgetRow);
            transaction.commit();

            session.close();

            System.out.println("Успешно добавлена новая статья");


    }

    void listBudgetRow(){

        System.out.println("Вывод списка статей");

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        Query query = session.createQuery("From "+BudgetRow.class.getSimpleName());

        for(BudgetRow br: (List<BudgetRow>)query.list())
            System.out.println(br);

        System.out.println("");

        session.close();

    }

    void deleteBudgetRow(){

        System.out.println("Удаление статьи");

        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите код удаляемой статьи:");

        int num = Integer.parseInt(scanner.nextLine());

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();
        Query query = session.createQuery("DELETE FROM "+BudgetRow.class.getSimpleName() +" where id = :paramId");
        query.setParameter("paramId", num);
        int result = query.executeUpdate();
        session.getTransaction().commit();
        session.close();

        if (result==1)
            System.out.println("Статья успешно удалена");
        else
            System.out.println("Введен некорректный номер статьи");

    }



    void addRecord(){

        System.out.println("Добавление записи о доходе/расходе.");

        Scanner scanner = new Scanner(System.in);

        Date docDate;
        do{
            System.out.println("Введите дату документа в формате ДД/ММ/ГГГГ: ");

            try {
                docDate = new SimpleDateFormat("dd/MM/yyyy").parse(scanner.nextLine());
                break;
            }

            catch(ParseException e)
                {
                    System.out.println("Дата введена в некорректном формате!");
                }

        } while(true);


        Record doc = new Record(docDate);

        BudgetRow row;

        do {
            System.out.println("Введите название статьи (cancel для отмены):");
            String name = scanner.nextLine();

            if (name.equals("cancel")){
                System.out.println("Операция отменена");
                return;
            }

            //try to find budget row in database
            row = findBudgetRow(name);

            if (row == null) System.out.println("По данному имени либо не найдена статья, либо имя не уникально. Уточните имя статьи.");

        }while(row == null);

        System.out.println("Будет использована статья: "+row.toString());

        doc.setBudgetRow(row);

        Account sender, recipient;

        do {
            System.out.println("Введите счет списания:");
            String name = scanner.nextLine();

            //try to find account in database
            sender = findAccount(name);

            if (sender == null) System.out.println("По данному имени либо не найден счет, либо имя не уникально. Уточните имя счета.");

        }while(sender == null);

        System.out.println("Будет использован счет списания: "+sender.toString());
        doc.setSender(sender);

        if (row.getRecordType() == RecordType.MOVEMENT){
            //требуется указание получателя

            do {
                System.out.println("Введите счет поступления:");
                String name = scanner.nextLine();

                //try to find account in database
                recipient = findAccount(name);

                if (recipient == null) System.out.println("По данному имени либо не найден счет, либо имя не уникально. Уточните имя счета.");

            }while(recipient == null);

            System.out.println("Будет использован счет поступления: "+recipient.toString());
            doc.setRecipient(recipient);
        }




        System.out.println("Введите сумму операции:");
        int sum = 0;

        do {
            try {
                sum = Integer.parseInt(scanner.nextLine());
                if (sum == 0) throw new ParseException("Incorrect sum", 0);
                break;
            } catch (ParseException e) {
                System.out.println("Введена некорректная сумма, повторите ввод.");
            }

        }while(true);

        doc.setSum(sum);


                SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
                Session session = sessionFactory.openSession();

                Transaction transaction = session.beginTransaction();
                session.save(doc);
                transaction.commit();

                session.close();

                System.out.println("Операция успешно добавлена");



    }

    /**
     *
     * @param name - part of name of budget row to search
     */
    BudgetRow findBudgetRow(String name){

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        Query query = session.createQuery("from BudgetRow where name like :name");
        query.setParameter("name", "%" + name + "%");
        List<BudgetRow> rows = query.list();

        session.close();

        if (rows.size()>0) return rows.get(0);
        else return null;

    }



    Account findAccount(String name){

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        Query query = session.createQuery("from Account where name like :name");
        query.setParameter("name", "%" + name + "%");
        List<Account> rows = query.list();

        session.close();

        if (rows.size()>0) return rows.get(0);
        else return null;

    }



}
