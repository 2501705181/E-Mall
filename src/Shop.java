import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.List;

public class Shop {

    public static List<User> Users=new ArrayList<>();
    public static List<Item> Items=new ArrayList<>();

    public static User userNow=null;//当前登录的用户
    public static Admin adminNow=null;//当前登录的管理员

    private Scanner scanner=new Scanner(System.in);
    private boolean loop=true;//控制菜单显示


    public Shop() {
        try {
            read();//读取用户和商品到内存中
        }catch (IOException|ClassNotFoundException e){
            //e.printStackTrace();
        }
    }

    //注册
    public void Register() throws IOException {
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
        save();
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
        buy();
    }


    public void buy(){
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
                    save();//刷新
                    return;
                }
            }
        }catch (NumberFormatException|InputMismatchException e){
            System.out.println("输入错误");
        } catch (IOException e) {
            //throw new RuntimeException(e);//文件保存
        }
    }

    //查看购买记录
    public void ViewMyPurchases(){
        if(userNow==null)
            System.out.println("请先登录！");
        else
            userNow.getRecord();//打印购买记录
    }

    //管理员登陆
    public void AdminLogin(){
        if(Admin.Login(this)!=null)//返回true为登录成功
            new adminView(adminNow);//启动管理员菜单
    }

    //读取用户和商品信息
    @SuppressWarnings("all")
   public void read() throws IOException, ClassNotFoundException {
        String userPath = "d:\\E-mall\\users\\users.txt";
        String itemPath = "d:\\E-mall\\items\\items.txt";

        ObjectInputStream userFileReader = null;
        ObjectInputStream itemFileReader = null;
        try {
            //读取用户
            userFileReader = new ObjectInputStream(new FileInputStream(userPath));
            List<User> U = (List<User>) userFileReader.readObject();
            Users.addAll(U);
            //读取商品
            itemFileReader = new ObjectInputStream(new FileInputStream(itemPath));
            List<Item> I = (List<Item>) itemFileReader.readObject();
            Items.addAll(I);

        } catch (IOException | ClassNotFoundException e) {

        } finally {
            itemFileReader.close();
            userFileReader.close();
        }
    }

   //保存用户和商品信息到文件
    public void save() throws IOException {
        String userPath = "d:\\E-mall\\users";//用户文件路径
        String itemPath = "d:\\E-mall\\items";//商品文件路径
        ObjectOutputStream userFileWriter = null;
        ObjectOutputStream itemFileWriter = null;
        try {
            File userDir = new File(userPath);
            File itemDir = new File(itemPath);
            if (!userDir.exists()) {//判断路径是否存在
                userDir.mkdirs();
            }
            if (!itemDir.exists()) {
                itemDir.mkdirs();
            }
            //保存用户列表到文件
            userFileWriter = new ObjectOutputStream(new FileOutputStream(userPath + "\\users.txt"));
            userFileWriter.writeObject(Users);
            //保存商品列表到文件
            itemFileWriter = new ObjectOutputStream(new FileOutputStream(itemPath + "\\items.txt"));
            itemFileWriter.writeObject(Items);

        } catch (IOException e) {

        } finally {
            userFileWriter.close();
            itemFileWriter.close();
        }
    }
}