import java.math.BigDecimal;
import java.util.Scanner;

public class eMall {

    private User []Users=new User[1000];//存1000个用户
    public static eItem[]Items=new eItem[10000];//存10000件商品
    //public static eItem[]MyItems=new eItem[10000];//我的购买

    private int UserAmount=0;
    public static int ItemAmount=0;
    private Scanner scanner=new Scanner(System.in);
    private boolean loop=true;//控制菜单显示
    private User userNow=null;//当前登录的用户



    public void Menu(){

        //****测试用****
        Items[0]=new eItem("10001","apple", new BigDecimal(5),20);
        Items[1]=new eItem("10002","pear", new BigDecimal(4),40);
        Items[2]=new eItem("10003","banana", new BigDecimal(3),50);
        ItemAmount=3;

        userNow=new User("name","123abc");
        //*********

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
                case 3: System.out.print("您选择的菜单是：查看商城\n");ViewMall();break;
                case 4: System.out.print("您选择的菜单是：查看我购买的商品\n");ViewMyPurchases();break;
                case 5: System.out.print("您选择的菜单是：管理员登陆\n");AdministratorLogin();break;
                case 6: System.out.print("您选择的菜单是：退出系统\n");Exit();break;
                default: System.out.print("输入错误！\n");break;
            }
        }while(loop);
    }

    private void Register(){
        String name,password;
        //Validator.isAlphaNumeric(password),当password为字母和数字的组合时返回true，否则返回false
        Validator validator=new Validator();
        boolean isExist;

        do{
            isExist=false;
            System.out.println("请输入用户名：");
            name= scanner.next();
            if(name.length()<3)
                System.out.println("用户名长度不能小于3位!");

            for(int i=0;i<UserAmount;i++)
                if(name.equals(Users[i].getName())){
                    isExist=true;
                    System.out.println("用户名已存在！");
                }

        }while(name.length()<3||isExist);

        do{
            System.out.println("请输入密码：");
            password= scanner.next();
            if(password.length()<6)
                System.out.println("密码长度不能小于6位!");
            if(!Validator.isAlphaNumeric(password))
                System.out.println("密码必须包含字母和数字！");
        }while(password.length()<6||!Validator.isAlphaNumeric(password));

        Users[UserAmount++]=new User(name,password);
        System.out.println("注册成功！");

    }

    private int Login() {
        String loginName;
        String loginPassword;

        if(userNow!=null){
            System.out.println("当前已登录用户"+userNow.getName()+",不能重复登录");
            return 0;
        }

        if(UserAmount==0){
            System.out.println("用户列表为空,请先注册");
            return 0;
        }

        System.out.println("请输入用户名：");
        loginName = scanner.next();


        for (int i = 0; i < UserAmount; i++) {
            if (loginName.equals(Users[i].getName()))
            {
                System.out.println("请输入密码：");
                loginPassword = scanner.next();

                while (!loginPassword.equals(Users[i].getPassword())){
                    System.out.println("密码错误，请重新输入：");
                    loginPassword = scanner.next();
                }
                userNow=new User(loginName,loginPassword);
                System.out.println("登录成功！");
                return 1;
            }
            else{
                System.out.println("该用户不存在！");

            }
        }
        return 0;
    }


    private void ViewMall(){

        if(userNow==null){
            System.out.println("未登录账号，请先登录");
            return;
        }

        if (ItemAmount==0){
            System.out.println("商城为空");
            return;
        }

        System.out.println("-----商品列表-----");
        for(int i=0;i<ItemAmount;i++)
            System.out.println(Items[i].toString());
        System.out.println("-----------------");

        String number;
        int num=0;
        int pos=-1;

        System.out.println("请输入你要购买的商品编号：");
        number=scanner.next();

        while(pos==-1){
        for(int i=0;i<ItemAmount;i++)
            if(number.equals(Items[i].getNumber())) {
                pos = i;
                break;
            }
        if(pos==-1) {
            System.out.println("未找到该商品，请重新输入：");
            number = scanner.next();
        }
        }

            System.out.println("请输入要购买的数量：");
            num = scanner.nextInt();

        while(num>Items[pos].getAmount()||num<=0) {
            if (num > Items[pos].getAmount())
                System.out.println("商品数量不足!请重新输入：");
            if(num<=0)
                System.out.println("购买数量必须大于零！请重新输入");
            num = scanner.nextInt();
        }

        int n = Items[pos].getAmount();
        Items[pos].setAmount(n - num);
        System.out.println("购买成功！");
        userNow.addRecord("商品名称："+Items[pos].getName()+" 价格："+Items[pos].getPrice()+" 购买数量："+num);
    }

    private void ViewMyPurchases(){
        if(userNow==null)
            System.out.println("请先登录！");
        else
            userNow.getRecord();
    }


    private void AdministratorLogin(){
        Administrator admin=new Administrator();
        if(admin.Login())//返回true为登录成功
            admin.adminMenu();
    }

    private void Exit(){
        char ch;
        do {
            System.out.println("确认要退出吗？y/n");
            ch = scanner.next().charAt(0);
            if(ch=='y')
                loop = false;
            else if (ch=='n') {
                break;
            }
        }while(loop);
    }

}
