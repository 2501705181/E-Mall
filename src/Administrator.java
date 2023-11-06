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



    public void addItem(){
        String number;
        String name;
        BigDecimal price;
        int amount;

        System.out.println("请输入商品的编号、名称、价格、数量：");
        number=scanner.next();
        name= scanner.next();
        price=scanner.nextBigDecimal();
        amount=scanner.nextInt();
        eItem newItem=new eItem(number,name,price,amount);

        eMall A=new eMall();
        A.Items[A.ItemAmount]=newItem;
        A.ItemAmount++;
        System.out.println("成功添加物品："+name);
    }

    public void deleteItem(){

    }

    public void changeItem(){

    }

    public void exit(){
        loop=false;
    }
}
