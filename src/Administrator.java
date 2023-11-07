import java.math.BigDecimal;
import java.util.Scanner;
public class Administrator extends User{

    Scanner scanner=new Scanner(System.in);
    boolean loop=true;

    public Administrator(){
        this.setName("admin");
        this.setPassword("admin");
    }

    public Administrator(String userName,String password){
        this.setName(userName);
        this.setPassword(password);
    }

    public boolean Login() {
        String loginName;
        String loginPassword;
        System.out.println("请输入管理员用户名：");
        loginName=scanner.next();

        if(!loginName.equals(this.getName())) {
            System.out.println("该管理员不存在！");
            return false;
        }
            System.out.println("请输入密码：");
            loginPassword=scanner.next();

            while(!loginPassword.equals(this.getPassword())) {
                System.out.println("密码错误，请重新输入：");
                loginPassword=scanner.next();
            }
            System.out.println("登录成功！");
            return true;

    }

    public void adminMenu(){
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
                case 2: System.out.print("您选择的菜单是：修改商品\n");changeItem();break;
                case 3: System.out.print("您选择的菜单是：删除商品\n");deleteItem();break;
                case 4: System.out.print("您选择的菜单是：退出\n");exit();break;
                default: System.out.print("输入错误！\n");break;
            }
        }while(loop);
    }

    public void itemList(){
        if (eMall.ItemAmount == 0) {
            System.out.println("商品列表为空！");
            return;
        }
        System.out.println("-----商品列表-----");
        for (int i = 0; i < eMall.ItemAmount; i++) {
            if (!eMall.Items[i].getNumber().equals("-1"))
                System.out.println(eMall.Items[i].toString());
        }
        System.out.println("---------------");
    }

    public void addItem(){
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

            for (int i = 0; i < eMall.ItemAmount; i++) {
                if (number.equals(eMall.Items[i].getNumber())) {
                    System.out.println("该商品已存在，请输入数量：");
                    amount = scanner.nextInt();
                    eMall.Items[i].setAmount(amount);
                    System.out.println("已将商品" + eMall.Items[i].getName() + "的数量设为" + amount);
                    isExist=true;
                }
            }

            if(!isExist) {
                System.out.println("请输入商品的名称、价格、数量：");
                name = scanner.next();
                price = scanner.nextBigDecimal();
                amount = scanner.nextInt();
                eItem newItem = new eItem(number, name, price, amount);


                eMall.Items[eMall.ItemAmount++] = newItem;
                //A.ItemAmount++;
                System.out.println("成功添加物品：" + name);
            }

                System.out.println("是否继续添加物品？y/n");
                char select = scanner.next().charAt(0);
                if(select=='y')
                    loop=true;
                else if(select=='n')
                    loop=false;
                else {
                    System.out.println("输入错误");
                    loop=false;
                }
            }
        }


    public void deleteItem() {
        String number;
        boolean flag=true;
        itemList();
        /*
            有bug，以下代码只是假删，所以删完之后其实还是有商品
         */

        int pos = -1;
        System.out.println("请输入你要删除的商品编号：");
        number = scanner.next();

        do {
            for (int i = 0; i < eMall.ItemAmount; i++)
                if (number.equals(eMall.Items[i].getNumber())) {
                    pos = i;
                    break;
                }
            if(pos==-1){
                System.out.println("未找到该商品，请重新输入商品编号：");
                number=scanner.next();
            }
        }while(pos==-1);

        do {
            System.out.println("确认要删除这个商品吗？y/n");
            char ch = scanner.next().charAt(0);
            if (ch == 'y') {
                System.out.println("已删除商品" + eMall.Items[pos].getNumber());
                eMall.Items[pos].setNumber("-1");
                flag=false;
            }
            else if(ch=='n') {
                System.out.println("取消删除");
                flag = false;
                break;
            }
        }while(flag);

    }




    public void changeItem() {
        String number;//编号
        String name;//名称
        BigDecimal price;//价格
        int amount;//数量

        itemList();

        BigDecimal zero = new BigDecimal(0);
        int pos = -1;
        System.out.println("请输入要修改的商品的编号：");
        number = scanner.next();

        do {
            for (int i = 0; i < eMall.ItemAmount; i++)
                if (number.equals(eMall.Items[i].getNumber())) {
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

        eMall.Items[pos].setName(name);
        eMall.Items[pos].setPrice(price);
        eMall.Items[pos].setAmount(amount);
        System.out.println("修改成功！");

    }


    public void exit(){
        loop=false;
    }
}
