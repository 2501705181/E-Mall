import java.math.BigDecimal;
import java.util.Scanner;

public class shop {

    public static User []Users=new User[1000];//存1000个用户
    public static Item[]Items=new Item[10000];//存10000件商品
    public static int UserAmount=0;
    public static int ItemAmount=0;


    private Scanner scanner=new Scanner(System.in);
    private boolean loop=true;//控制菜单显示
    private User userNow=null;//当前登录的用户

    //执行顺序 1静态代码块 2普通代码块 3构造器
    static {
        //测试用
        Items[0]=new Item("10001","apple", new BigDecimal(5),20);
        Items[1]=new Item("10002","pear", new BigDecimal(4),40);
        Items[2]=new Item("10003","banana", new BigDecimal(3),50);
        ItemAmount=3;

        //userNow=new User("name","123abc");

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
                case 3: System.out.print("您选择的菜单是：查看商城\n");ViewMall();break;
                case 4: System.out.print("您选择的菜单是：查看我购买的商品\n");ViewMyPurchases();break;
                case 5: System.out.print("您选择的菜单是：管理员登陆\n");AdministratorLogin();break;
                case 6: System.out.print("您选择的菜单是：退出系统\n");Exit();break;
                default: System.out.print("输入错误！\n");break;
            }
        }while(loop);
    }

    //注册
    private void Register(){
        shop.Users[UserAmount]=User.Register();
        UserAmount++;
    }

    //登录
    private void Login() {

        if(userNow!=null){
            System.out.println("当前已登录用户"+userNow.getName()+",不能重复登录");
            return;
        }

        if(UserAmount==0){
            System.out.println("请先注册");
            return;
        }

        //设置当前登录用户
        userNow=User.Login();
        if(userNow!=null)
            System.out.println("登录成功！");
        else
            System.out.println("登录失败！");

    }

    //查看商城
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

        String number;//要购买的商品的编号
        int num;//要购买的数量
        int index=-1;//-1表示未找到商品

        System.out.println("请输入你要购买的商品编号：输入no取消购买");
        number=scanner.next();
        if(number.equals("no")) {
            System.out.println("取消购买");
            return;
        }

        while(index==-1){
            for(int i=0;i<ItemAmount;i++)
                if(number.equals(Items[i].getNumber())) {
                    index = i;//搜寻到目标商品为第i个
                    break;
                }
            if(index==-1) {
                System.out.println("未找到该商品，请重新输入：");
                number = scanner.next();
                if(number.equals("no")) {
                    System.out.println("取消购买");
                    return;
                }
            }
        }

        if(Items[index].getAmount()==0){
            System.out.println("商品库存不足，无法购买");
            return;
        }

            System.out.println("请输入要购买的数量：");
            num = scanner.nextInt();

        while(num>Items[index].getAmount()||num<=0) {
            if (num > Items[index].getAmount())
                System.out.println("商品数量不足!请重新输入：");
            if(num<=0)
                System.out.println("购买数量必须大于零！请重新输入");
            num = scanner.nextInt();
        }
        //原来的数量
        int n = Items[index].getAmount();
        //数量减少num个
        Items[index].setAmount(n - num);
        //新增购买记录
        userNow.addRecord("商品名称："+Items[index].getName()+" 价格："+Items[index].getPrice()+" 购买数量："+num);
        System.out.println("购买成功！");
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
            System.out.println("确认要退出吗？y/n");
            ch = scanner.next().charAt(0);
            if(ch=='y'||ch=='Y')
                loop = false;
            else if (ch=='n'||ch=='N') {
                break;
            }
        }while(loop);
    }

}
