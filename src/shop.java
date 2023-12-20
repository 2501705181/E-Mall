import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

public class shop {

    public static List<User> Users=new ArrayList<User>();
    public static List<Item> Items=new ArrayList<Item>();

    private Scanner scanner=new Scanner(System.in);
    private boolean loop=true;//控制菜单显示
    private static User userNow=null;//当前登录的用户

    //执行顺序 1静态代码块 2普通代码块 3构造器
    static {
        //测试用
        Items.add(new Item("10001","apple", new BigDecimal(5),20));
        Items.add(new Item("10002","pear", new BigDecimal(4),40));
        Items.add(new Item("10003","banana", new BigDecimal(3),50));

        userNow=new User("name","123abc");

    }

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
                case 3: System.out.print("您选择的菜单是：查看商城\n");ViewShop();break;
                case 4: System.out.print("您选择的菜单是：查看我购买的商品\n");ViewMyPurchases();break;
                case 5: System.out.print("您选择的菜单是：管理员登陆\n");AdministratorLogin();break;
                case 6: System.out.print("您选择的菜单是：退出系统\n");Exit();break;
                default: System.out.print("输入错误！\n");break;
            }
        }while(loop);
    }

    //注册
    private void Register(){
        Users.add(User.Register());
    }

    //登录
    private void Login() {

        if(userNow!=null){
            System.out.println("当前已登录用户"+userNow.getName()+",不能重复登录");
            return;
        }

        if(Users.isEmpty()){
            System.out.println("请先注册");
            return;
        }

        userNow=User.Login();//设置当前登录用户
        if(userNow!=null)
            System.out.println("登录成功！");
        else
            System.out.println("登录失败！");

    }

    //查看商城
    private void ViewShop() {

        if (userNow == null) {
            System.out.println("未登录账号，请先登录");
            return;
        }

        if (Items.isEmpty()) {
            System.out.println("商城为空");
            return;
        }

        System.out.println("-----商品列表-----");
        for (Item item : Items)
            System.out.println(item);
        System.out.println("-----------------");

        String number;//要购买的商品的编号
        int num;//要购买的数量

        System.out.println("请输入你要购买的商品编号：输入no取消购买");number = scanner.next();
        if (number.equals("no")) {
            System.out.println("取消购买");
            return;
        }

        for (Item item : Items) {
            if (number.equals(item.getNumber())) {

                if (item.getAmount() == 0) {
                    System.out.println("商品库存不足，无法购买");
                    return;
                }

                System.out.println("请输入要购买的数量：");
                num = scanner.nextInt();

                while (num > item.getAmount() || num <= 0) {
                    if (num > item.getAmount())
                        System.out.println("商品数量不足!请重新输入：");
                    if (num <= 0)
                        System.out.println("购买数量必须大于零！请重新输入");
                    num = scanner.nextInt();
                }

                int n = item.getAmount();//原来的数量
                item.setAmount(n - num);//数量减少num个

                //新增购买记录
                userNow.addRecord("商品名称：" + item.getName() + " 价格：" + item.getPrice() + " 购买数量：" + num);
                System.out.println("购买成功！");
                return;
            }
        }
        System.out.println("未找到该商品");
    }

    private void ViewMyPurchases(){
        if(userNow==null)
            System.out.println("请先登录！");
        else
            userNow.getRecord();//打印购买记录
    }


    private void AdministratorLogin(){
        if(Admin.Login()!=null)//返回true为登录成功
            Admin.Menu();//启动管理员菜单
    }

    private void Exit(){
        char ch;
        do {
            System.out.println("确认要退出吗？y/n");ch = scanner.next().charAt(0);
            if(ch=='y'||ch=='Y')
                loop = false;
            else if (ch=='n'||ch=='N') {
                break;
            }
        }while(loop);
    }
}
