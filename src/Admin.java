import java.io.IOException;
import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Scanner;
public class Admin extends User{

    private static Shop shop=null;
    private static final Scanner scanner=new Scanner(System.in);

    public Admin(){

    }
    public Admin(String userName, String password,Shop shop){
        this.name=userName;
        this.password=password;
        Admin.shop =shop;
    }


    //管理员登陆
    public static Admin Login(Shop shop) {
        String loginName;
        String loginPassword;
        System.out.println("请输入管理员用户名：");
        loginName=scanner.next();

        //static静态方法同类一起加载，不能使用this、super
        if(!"admin".equals(loginName)) {
            System.out.println("该管理员不存在！");
            return null;
        }
        System.out.println("请输入密码：");
        loginPassword=scanner.next();

        while(!"admin".equals(loginPassword)) {
            System.out.println("密码错误，请重新输入：");
            loginPassword=scanner.next();
        }
        Admin admin=new Admin(loginName,loginPassword,shop);
        System.out.println("登录成功！");
        shop.adminNow=admin;
        return admin;
    }

    //打印商品列表
    public static void itemList(){
        if (shop.Items.isEmpty()) {
            System.out.println("商品列表为空！");
            return;
        }
        System.out.println("-----商品列表-----");
        for (Item item: shop.Items)
            System.out.println(item);
        System.out.println("---------------");
    }

    //添加商品
    public static void addItem() throws IOException {
        int id=0;//编号
        String name;//名称
        BigDecimal price;//价格
        int amount;//数量
        boolean loop=true;//循环添加商品

        while (loop) {
            //itemList();//打印商品列表
            System.out.println("请输入新增商品的编号:");
            try {
                id = Integer.parseInt(scanner.next());//用nextInt()会有异常
                Item temp = new Item(id, "name", new BigDecimal(0), 0);

                if (shop.Items.contains(temp)) {
                    System.out.println("该商品已存在，请输入数量：");
                    Item target = shop.Items.get(shop.Items.indexOf(temp));
                    amount = scanner.nextInt();
                    target.setAmount(amount);
                    System.out.println("已将" + target.getName() + "的数量设为" + amount);
                    break;
                } else {
                    System.out.println("请输入商品的名称、价格、数量：");
                    name = scanner.next();
                    price = scanner.nextBigDecimal();
                    amount = scanner.nextInt();
                    shop.Items.add(new Item(id, name, price, amount));//往商城中加入新商品
                    System.out.println("成功添加商品：" + name + "\n是否继续添加物品？y/n");
                    loop = YorN();
                }
                shop.save();//保存数据
            } catch (InputMismatchException |NumberFormatException e) {
                System.out.println("输入有误");
                return;
            }
        }
    }

    //删除商品
    public static void delItem() throws NumberFormatException, IOException {
        int id;

        itemList();//打印商品列表
        if(shop.Items.isEmpty()) {
            return;
        }

        System.out.println("请输入你要删除的商品编号：");
        id = Integer.parseInt(scanner.next());
        Item temp=new Item(id, "name", new BigDecimal(0), 0);

        if(!shop.Items.contains(temp)){
            System.out.println("该商品不存在");
        }else{
            Item target = shop.Items.get(shop.Items.indexOf(temp));
            System.out.println("确认要删除这个商品吗？y/n");
            char ch = scanner.next().charAt(0);
            if (ch == 'y' || ch == 'Y') {
                shop.Items.remove(target);
                System.out.println("已删除商品" + target.getName());
                shop.save();//刷新商城
            } else if (ch == 'n' || ch == 'N') {
                System.out.println("取消删除");
            }
        }
    }

    //修改商品
    public static void changeItem() throws NumberFormatException{
        int id;//编号
        String name;//名称
        BigDecimal price;//价格
        int amount;//数量

        itemList();//打印商品列表
        if (shop.Items.isEmpty())
            return;

        BigDecimal zero = new BigDecimal(0);
        System.out.println("请输入要修改的商品的编号：");
        try {
            id = Integer.parseInt(scanner.next());
            Item temp = new Item(id, "name", new BigDecimal(0), 0);
            if (!shop.Items.contains(temp)) {
                System.out.println("该商品不存在");
                return;
            }

            Item target = shop.Items.get(shop.Items.indexOf(temp));
            System.out.println("请输入该商品的名称、价格、数量");
            do {//循环输入
                name = scanner.next();
                price = BigDecimal.valueOf(Double.parseDouble(scanner.next()));
                amount = Integer.parseInt(scanner.next());
                if (price.compareTo(zero) < 1)
                    System.out.println("商品价格必须大于0,请重新输入：");
                if (amount <= 0)
                    System.out.println("商品数量必须大于0，请重新输入：");
            } while (price.compareTo(zero) < 1 || amount <= 0);

            target.setAmount(amount);
            target.setName(name);
            target.setPrice(price);
            System.out.println("修改成功！");
            shop.save();//刷新商城
        } catch (InputMismatchException | NumberFormatException e) {
            System.out.println("输入有误");
        } catch (IOException e) {
            //System.out.println("保存失败");
        }
    }

    //确认
    public static boolean YorN(){
        char select = scanner.next().charAt(0);
        if(select=='y'||select=='Y')
            return true;
        else if(select=='n'||select=='N')
            return false;
        else {
            return false;
        }
    }
}