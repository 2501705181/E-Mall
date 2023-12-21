import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

public class Shop {

    public static List<User> Users=new ArrayList<>();
    public static List<Item> Items=new ArrayList<>();

    public static User userNow=null;//当前登录的用户
    public static Admin adminNow=null;//当前登录的管理员

    private Scanner scanner=new Scanner(System.in);
    private boolean loop=true;//控制菜单显示


    //执行顺序 1静态代码块 2普通代码块 3构造器
    {
        //测试用
        Items.add(new Item(10001,"apple", new BigDecimal(5),20));
        Items.add(new Item(10002,"pear", new BigDecimal(4),40));
        Items.add(new Item(10003,"banana", new BigDecimal(3),50));

        userNow=new User("name","123abc",this);

    }

    //注册功能
    public void Register(){
        String name,password;
        boolean isExist;
        do{
            isExist=false;
            System.out.println("请输入用户名：");name= scanner.next();
            if(name.length()<3)
                System.out.println("用户名长度不能小于3位!");
            for (User user : Users) {
                if (name.equals(user.getName())) {
                    isExist = true;
                    System.out.println("用户名已存在！");
                }
            }
        }while(name.length()<3||isExist);

        do{
            System.out.println("请输入密码：");password= scanner.next();
            if(password.length()<6)
                System.out.println("密码长度不能小于6位!");
            if(!Validator.isAlphaNumeric(password))
                System.out.println("密码必须包含字母和数字！");
        }while(password.length()<6||!Validator.isAlphaNumeric(password));
        //当password为字母和数字的组合时返回true，否则返回false
        Users.add(new User(name,password,this));
        System.out.println("注册成功！");
    }

    //登录
    public void Login() {

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
    public void ViewShop() {

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

        int id;//要购买的商品的编号
        int num;//要购买的数量
        System.out.println("请输入你要购买的商品编号：输入no取消购买");String checkId = scanner.next();
        if ("no".equals(checkId)) {
            System.out.println("取消购买");
            return;
        }
        try {
            id = Integer.parseInt(checkId);
            for (Item item : Items) {
                if (id == item.getId()) {
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
                    userNow.addRecord("商品名称：" + item.getName() + " 单价：" + item.getPrice() + " 购买数量：" + num);
                    System.out.println("购买成功！");
                    return;
                }
            }
        }catch (NumberFormatException e){
            System.out.print("编号错误，");
        }
        System.out.println("未找到该商品");
    }

    public void ViewMyPurchases(){
        if(userNow==null)
            System.out.println("请先登录！");
        else
            userNow.getRecord();//打印购买记录
    }


    public void AdminLogin(){
        if(Admin.Login(this)!=null)//返回true为登录成功
            new adminView(adminNow);//启动管理员菜单
    }

//    public void Exit(){
//        char ch;
//        do {
//            System.out.println("确认要退出吗？y/n");ch = scanner.next().charAt(0);
//            if(ch=='y'||ch=='Y')
//                loop = false;
//            else if (ch=='n'||ch=='N') {
//                break;
//            }
//        }while(loop);
//    }
}