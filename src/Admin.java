import java.math.BigDecimal;
import java.util.Scanner;
public class Admin extends User{

    static Scanner scanner=new Scanner(System.in);
    static boolean loop=true;

    public Admin(){
        this.setName("admin");
        this.setPassword("admin");
    }

    public Admin(String userName, String password){
        this.setName(userName);
        this.setPassword(password);
    }

    //确认
    public static boolean YorN(){
        char select = scanner.next().charAt(0);
        if(select=='y')
            return true;
        else if(select=='n')
            return false;
        else {
            return false;
        }
    }

    //管理员登陆
    public static Admin Login() {
        String loginName;
        String loginPassword;
        System.out.println("请输入管理员用户名：");
        loginName=scanner.next();

        //static静态方法同类一起加载，不能使用this、super
        if(!loginName.equals("admin")) {
            System.out.println("该管理员不存在！");
            return null;
        }
            System.out.println("请输入密码：");
            loginPassword=scanner.next();

            while(!loginPassword.equals("admin")) {
                System.out.println("密码错误，请重新输入：");
                loginPassword=scanner.next();
            }
            Admin admin=new Admin(loginName,loginPassword);
            System.out.println("登录成功！");
            return admin;
    }

    //显示菜单
    public static void Menu(){
        int select;
        do {
            System.out.println("*****管理员菜单****");
            System.out.println("1.增加商品");
            System.out.println("2.修改商品");
            System.out.println("3.删除商品");
            System.out.println("4.退出");
            System.out.println("**********************");
            System.out.println("请选择菜单：");

            select = scanner.nextInt();
            switch (select) {
                case 1: System.out.print("您选择的菜单是：增加商品\n");addItem();break;
                case 2: System.out.print("您选择的菜单是：修改商品\n");changItem();break;
                case 3: System.out.print("您选择的菜单是：删除商品\n");delItem();break;
                case 4: System.out.print("您选择的菜单是：退出\n");exit();break;
                default: System.out.print("输入错误！\n");break;
            }
        }while(loop);
    }

    //打印商品列表
    public static void itemList(){
        if (shop.ItemAmount == 0) {
            System.out.println("商品列表为空！");
            return;
        }
        System.out.println("-----商品列表-----");
        for (int i = 0; i < shop.ItemAmount; i++)
                System.out.println(shop.Items[i].toString());
        System.out.println("---------------");
    }

    //添加商品
    public static void addItem(){
        String number;//编号
        String name;//名称
        BigDecimal price;//价格
        int amount;//数量
        boolean loop=true;
        while(loop)
        {
            boolean isExist=false;
            System.out.println("请输入商品的编号:");
            number = scanner.next();

            for (int i = 0; i < shop.ItemAmount; i++) {
                if (number.equals(shop.Items[i].getNumber())) {
                    System.out.println("该商品已存在，请输入数量：");
                    amount = scanner.nextInt();
                    shop.Items[i].setAmount(amount);
                    System.out.println("已将商品" + shop.Items[i].getName() + "的数量设为" + amount);
                    isExist=true;
                }
            }

            if(!isExist) {
                System.out.println("请输入商品的名称、价格、数量：");
                name = scanner.next();
                price = scanner.nextBigDecimal();
                amount = scanner.nextInt();
                Item newItem = new Item(number, name, price, amount);


                shop.Items[shop.ItemAmount++] = newItem;
                //A.ItemAmount++;
                System.out.println("成功添加物品：" + name);
            }

                System.out.println("是否继续添加物品？y/n");
                loop=YorN();

            }
    }

    //删除商品
    public static void delItem(){
        String number;
        boolean flag=true;

        //打印商品列表
        itemList();
        if(shop.ItemAmount==0)
            return;

        int index = -1;
        System.out.println("请输入你要删除的商品编号：");
        number = scanner.next();

        do {
            for (int i = 0; i < shop.ItemAmount; i++)
                if (number.equals(shop.Items[i].getNumber())) {
                    index = i;
                    break;
                }
            if(index==-1){
                System.out.println("未找到该商品，请重新输入商品编号：");
                number=scanner.next();
            }
        }while(index==-1);

        while(true){
            System.out.println("确认要删除这个商品吗？y/n");
            char ch = scanner.next().charAt(0);
            if (ch == 'y'||ch=='Y') {
                System.out.println("已删除商品" + shop.Items[index].getName());
                for(int i = index; i< shop.ItemAmount-1; i++)//移动item
                    shop.Items[i]= shop.Items[i+1];
                shop.Items[shop.ItemAmount-1]=null;
                shop.ItemAmount--;
                break;
            }
            else if(ch=='n'||ch=='N') {
                System.out.println("取消删除");
                break;
            }
        }

    }

    //修改商品
    public static void changItem() {
        String number;//编号
        String name;//名称
        BigDecimal price;//价格
        int amount;//数量

        //打印商品列表
        itemList();
        if(shop.ItemAmount==0)
            return;

        BigDecimal zero = new BigDecimal(0);
        int pos = -1;
        System.out.println("请输入要修改的商品的编号：");
        number = scanner.next();

        do {
            for (int i = 0; i < shop.ItemAmount; i++)
                if (number.equals(shop.Items[i].getNumber())) {
                    pos = i;
                    break;
                }
            if (pos==-1) {
                System.out.println("该商品不存在，请重新输入商品编号：");
                number = scanner.next();
            }
        } while (pos == -1);

        System.out.println("请输入该商品的名称、价格、数量");
        do {
            name = scanner.next();
            price = scanner.nextBigDecimal();
            amount = scanner.nextInt();

            if(price.compareTo(zero) < 1)
                System.out.println("商品价格必须大于0,请重新输入：");

            if(amount <= 0)
                System.out.println("商品数量必须大于0，请重新输入：");

        }while(price.compareTo(zero) < 1||amount <= 0);

        shop.Items[pos].setName(name);
        shop.Items[pos].setPrice(price);
        shop.Items[pos].setAmount(amount);
        System.out.println("修改成功！");

    }

    //退出
    public static void exit(){
        loop=false;
    }
}