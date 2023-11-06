import java.util.Scanner;

public class eMall {

    private User []Users=new User[1000];//存1000个用户
    private int UserAmount=0;
    private Scanner scanner=new Scanner(System.in);
    private boolean loop=true;

    public void Menu(){
        int select;
        do {
            System.out.println("*****欢迎进入电子商城****");
            System.out.println("1.注册");
            System.out.println("2.登录");
            System.out.println("3.查看商城");
            System.out.println("4.查看我购买的物品");
            System.out.println("5.管理员登陆");
            System.out.println("6.退出系统");
            System.out.println("**********************");
            System.out.println("请选择菜单：");

            select = scanner.nextInt();
            switch (select) {
                case 1: System.out.print("您选择的菜单是：注册\n");Register();break;
                case 2: System.out.print("您选择的菜单是：登录\n");Login();break;
                case 3: System.out.print("您选择的菜单是：查看商城\n");CheckMall();break;
                case 4: System.out.print("您选择的菜单是：查看我购买的商品\n");ViewMyPurchases();break;
                case 5: System.out.print("您选择的菜单是：管理员登陆\n");AdministratorLogin();break;
                case 6: System.out.print("您选择的菜单是：退出系统\n");Exit();break;
                default: System.out.print("输入错误！\n");break;
            }
        }while(loop);
    }


    private void Register(){
        String name,password;
        Validator validator=new Validator();
        //Validator.isAlphaNumeric(password)
        //当password为字母和数字的组合时返回true，否则返回false

        do{
            System.out.println("请输入用户名：");
            name= scanner.next();
            if(name.length()<3)
                System.out.println("用户名长度不能小于3位!");
        }while(name.length()<3);

        do{
            System.out.println("请输入密码：");
            password= scanner.next();
            if(password.length()<6)
                System.out.println("密码长度不能小于6位!");
            if(!Validator.isAlphaNumeric(password))
                System.out.println("密码必须包含字母和数字！");
        }while(password.length()<6||!Validator.isAlphaNumeric(password));

        Users[UserAmount]=new User(name,password);
        UserAmount++;
        System.out.println("注册成功！");

    }
    private void Login(){

    }
    private void CheckMall(){

    }

    private void ViewMyPurchases(){

    }
    private void AdministratorLogin(){

    }
    private void Exit(){
        loop=false;
    }


}