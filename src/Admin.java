import java.io.IOException;
import java.math.BigDecimal;
import java.util.Scanner;
public class Admin extends User{

    private static Shop shop=null;

    public Admin(){
        //this("admin","admin");
    }
    private static Scanner scanner=new Scanner(System.in);
    public Admin(String userName, String password,Shop shop){
        this.name=userName;
        this.password=password;
        this.shop=shop;
    }


    //管理员登陆
    public static Admin Login(Shop shop) {
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
        int id;//编号
        String name;//名称
        BigDecimal price;//价格
        int amount;//数量
        boolean loop=true;

        itemList();
        while(loop)
        {
            boolean isExist=false;
            System.out.println("请输入商品的编号:");

            id = Integer.parseInt(scanner.next());
            for (Item item : shop.Items) {
                if (id == (item.getId())) {
                    System.out.println("该商品已存在，请输入数量：");
                    String checkAmount = scanner.next();
                    try {
                        amount = Integer.parseInt(checkAmount);

                        item.setAmount(amount);
                        System.out.println("已将商品" + item.getName() + "的数量设为" + amount);
                        isExist = true;
                    } catch (NumberFormatException e) {

                    }
                }
            }
            if (!isExist) {
                System.out.println("请输入商品的名称、价格、数量：");
                name = scanner.next();
                String checkPrice= scanner.next();
                String checkAmount=scanner.next();
                try {
                    price = BigDecimal.valueOf(Double.parseDouble(checkPrice));
                    amount = Integer.parseInt(checkAmount);
                }catch (NumberFormatException e){
                    //System.out.println(e);
                    System.out.println("价格或数量错误");
                    break;
                }
                Item newItem = new Item(id, name, price, amount);
                shop.Items.add(newItem);
                System.out.println("成功添加物品：" + name);
            }
            System.out.println("是否继续添加物品？y/n");
            loop=YorN();
        }
        shop.save();//刷新商城
    }

    //删除商品
    public static void delItem() throws NumberFormatException, IOException {
        int id;

        itemList();//打印商品列表

        if(shop.Items.isEmpty())
            return;
        System.out.println("请输入你要删除的商品编号：");
        id = Integer.parseInt(scanner.next());

        for(Item item: shop.Items) {
            if (id==item.getId()) {
                while(true) {
                    System.out.println("确认要删除这个商品吗？y/n");char ch = scanner.next().charAt(0);
                    if (ch == 'y' || ch == 'Y') {
                        shop.Items.remove(item);
                        System.out.println("已删除商品" + item.getName());
                        shop.save();//刷新商城
                        return;
                    } else if (ch == 'n' || ch == 'N') {
                        System.out.println("取消删除");
                        return;
                    }
                }
            }
        }
        System.out.println("未找到该商品");
    }

    //修改商品
    public static void changItem() throws NumberFormatException{
        int id;//编号
        String name;//名称
        BigDecimal price;//价格
        int amount;//数量


        itemList();//打印商品列表

        if (shop.Items.isEmpty())
            return;
        BigDecimal zero = new BigDecimal(0);
        System.out.println("请输入要修改的商品的编号：");id=Integer.parseInt(scanner.next());

        for (Item item : shop.Items) {
            if (id == item.getId()) {
                System.out.println("请输入该商品的名称、价格、数量");
                try {
                    do {
                        name = scanner.next();
                        price = BigDecimal.valueOf(Double.parseDouble(scanner.next()));
                        amount = Integer.parseInt(scanner.next());

                        if (price.compareTo(zero) < 1)
                            System.out.println("商品价格必须大于0,请重新输入：");
                        if (amount <= 0)
                            System.out.println("商品数量必须大于0，请重新输入：");
                    } while (price.compareTo(zero) < 1 || amount <= 0);
                    item.setAmount(amount);
                    item.setName(name);
                    item.setPrice(price);
                    System.out.println("修改成功！");
                    shop.save();//刷新商城
                } catch (NumberFormatException e) {
                    System.out.println("价格或者数量错误");
                } catch (IOException e) {
                    //throw new RuntimeException(e);
                }
                return;
            }
        }
        System.out.println("该商品不存在");
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