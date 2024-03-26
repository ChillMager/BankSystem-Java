package BankSystem;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class ATM {
    private ArrayList<Account> accounts = new ArrayList<>();
    private Scanner sc = new Scanner(System.in);

    private Account load_account;//登录后的账户account;

    public void Menu() {
        while (true) {
            System.out.println("==========菜单==========");
            System.out.println("       1.用户登录        ");
            System.out.println("       2.用户开户        ");
            System.out.println("       3.退出系统        ");
            System.out.println("=======================");
            System.out.println("请选择》");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    LoadAccount();
                    break;
                case 2:
                    CreateAccount();
                    break;
                case 3:
                    System.out.println(" ----------退出中--------");
                    return;
                default:
                    System.out.println("        没有操作！！！    ");

            }
        }

    }

    private String CreateCardID() {
        System.out.println("账户创建》");
        while (true) {
            String cardid = "";
            for (int i = 0; i < 4; i++) {
                Random r = new Random();
                int rannum = r.nextInt(10);
                cardid += rannum;
            }
            Account account = CheckCardID(cardid);
            if (account == null) {
                return cardid;
            }
        }

    }

    private Account CheckCardID(String cardid) {
        for (int i = 0; i < accounts.size(); i++) {
            Account account = accounts.get(i);
            if (account.getCardID().equals(cardid)) {
                return account;
            }
        }
        return null;
    }

    private void CreateAccount() {
        Account account = new Account();

        System.out.println("请输入账户--名称:");
        String userName = sc.next();
        account.setUserName(userName);

        while (true) {
            System.out.println("请输入账户--性别:");
            char sex = sc.next().charAt(0);
            if (sex == '男' || sex == '女') {
                account.setSex(sex);
                break;
            } else {
                System.out.println("性别输入有误！");
            }
        } //性别

        String cardID = CreateCardID();
        account.setCardID(cardID);

        while (true) {
            System.out.println("请输入账户--密码:");
            String passWord1 = sc.next();
            System.out.println("请再次输入账户--密码:");
            String passWord2 = sc.next();
            if (passWord2.equals(passWord1)) {
                account.setPassWord(passWord1);
                break;
            } else {
                System.out.println("密码输入不一致！");
            }
        } //密码

        System.out.println("请输入账户--取现额度:");
        double limit = sc.nextDouble();
        account.setLimit(limit);

        accounts.add(account);
        System.out.println("用户" + account.getUserName() + "创建成功!!!");
        System.out.println("卡号为:" + account.getCardID());
        System.out.println("用户" + account.getLimit() + "取现额度!!!");


    } //创建用户

    private void LoadAccount() {
        System.out.println("账户登录》");
        if (accounts.size() == 0) {
            System.out.println("系统内没有账户！！！");
            return;
        }

        System.out.println("输入卡号:");
        String cardid = sc.next();
        Account account = CheckCardID(cardid);
        if (account == null) {
            System.out.println("账户不存在！！！");
        } else {
            while (true) {
                System.out.println("输入密码:");
                String password = sc.next();
                if (account.getPassWord().equals(password)) {
                    load_account = account;
                    UserOperation();
                    break;
                } else {
                    System.out.println("密码错误！！！");
                }
            }
        }
    } //登录账户

    private void UserOperation() {
        while (true) {
            System.out.println("您好!"+load_account.getUserName());
            System.out.println("===========操作===========");
            System.out.println("       1.账户--查询        ");
            System.out.println("       2.账户--存款        ");
            System.out.println("       3.账户--取款        ");
            System.out.println("       4.账户--转账        ");
            System.out.println("        5.密码修改         ");
            System.out.println("        6.退出系统         ");
            System.out.println("       7.用户--注销        ");
            System.out.println("=======================");
            System.out.println("请选择》");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    ShowAccount();
                    break;
                case 2:
                    InputMoney();
                    break;
                case 3:
                    OutputMoney();
                    break;
                case 4:
                    TansMoney();
                    break;
                case 5:
                    UpdatePassword();
                    break;
                case 6:
                    System.out.println(" ----------退出中--------");
                    return;
                case 7:
                    if(DeleteAccount()){
                        return;
                    }
                    break;
                default:
                    System.out.println("        没有操作（1-7）！！！    ");
            }
        }
    }

    private void ShowAccount(){
        System.out.println("欢迎使用查询功能》");
        System.out.println("卡号: "+load_account.getCardID());
        System.out.println("姓名: "+load_account.getUserName());
        System.out.println("性别: "+load_account.getSex());
        System.out.println("余额: "+load_account.getMoney());
        System.out.println("限额: "+load_account.getLimit());
        System.out.println("                                  ");
    }//用户查询

    private void InputMoney(){
        System.out.println("欢迎使用存款功能》");
        System.out.println("输入存钱数目:");
        double addmoney = sc.nextDouble();
        load_account.setMoney(load_account.getMoney()+addmoney);
        System.out.println("已存款"+addmoney);
        System.out.println("存款后余额为:"+load_account.getMoney());
        System.out.println("                            ");
    } //存款

    private void OutputMoney(){
        System.out.println("欢迎使用取款功能》");
        while (true) {
            System.out.println("输入取钱数目:");
            double submoney = sc.nextDouble();
            if(load_account.getMoney()>=submoney){
                if (submoney>load_account.getLimit()){
                    System.out.println("超过了限额！！！");
                }
                else {
                    load_account.setMoney(load_account.getMoney()-submoney);
                    System.out.println("已取款"+submoney);
                    System.out.println("取款后余额为:"+load_account.getMoney());
                    System.out.println("                            ");
                    break;
                }
            }
            else {
                System.out.println("余额不足以满足本次取款！！！");
            }
        }


    }//取款

    private void TansMoney(){
        if(accounts.size()<2){
            System.out.println("系统只有一位用户，不可转账！！！");
            return;
        }
        if (load_account.getMoney() == 0){
            System.out.println("余额不足，无法转账");
            return;
        }
        System.out.println("欢迎使用取款功能》");
        while (true) {
            System.out.println("输入对方卡号: ");
            String cardid = sc.next();
            Account trans_account = CheckCardID(cardid);
            if(trans_account == null){
                System.out.println("输入的卡号有误！！！");
            }
            else {
                System.out.println("输入转账金额: ");
                double transmoney = sc.nextDouble();
                if(transmoney>load_account.getMoney()){
                    System.out.println("超过余额，无法转账!!!");
                }
                else {
                    load_account.setMoney(load_account.getMoney() - transmoney);
                    trans_account.setMoney(trans_account.getMoney() + transmoney);
                    System.out.println("转款成功 "+transmoney+" 元");
                    return;
                }
            }
        }
    }//转账

    private boolean DeleteAccount(){
        System.out.println("欢迎使用销户功能》");
        if(load_account.getMoney()>0){
            System.out.println("账户有余额，不可销户！！！");
            return false;
        }
        System.out.println("是否销户？？？ y/n");
        String choice = sc.next();
        switch (choice){
            case "y":
                accounts.remove(load_account);
                return true;
            default:
                System.out.println("放弃销户~~~");
                return false;
        }
    }//销户

    private void UpdatePassword(){
        System.out.println("欢迎使用修改密码功能》");
        while (true) {
            System.out.println("输入当前密码: ");
            String oldpassword = sc.next();
            if(load_account.getPassWord().equals(oldpassword)){
                System.out.println("输入新的密码: ");
                String newpassword = sc.next();
                load_account.setPassWord(newpassword);
                System.out.println("密码已修改！！！");
                return;
            }
            else {
                System.out.println("密码有误，不允许更改！！！");
                break;
            }
        }
    }






















}
