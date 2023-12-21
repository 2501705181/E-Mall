import java.util.Scanner;

public class ShopView {
    Shop shop=new Shop();

    public static void main(String[] args) {
        ShopView shopView=new ShopView();
        shopView.Menu();
        System.out.println("已退出商城");
    }

    public void Menu(){
        Scanner scanner=new Scanner(System.in);
        boolean loop=true;
        int select;
        do {
            System.out.println("*****欢迎进入电子商城****");
            System.out.println("\t1.注册");
            System.out.println("\t2.登录");
            System.out.println("\t3.查看商城");
            System.out.println("\t4.查看我购买的物品");
            System.out.println("\t5.管理员登陆");
            System.out.println("\t6.退出系统");
            System.out.println("**********************");
            System.out.println("请选择菜单：");

            try {
                select = Integer.parseInt(scanner.next());
                switch (select) {
                    case 1:
                        System.out.print("您选择的菜单是：注册\n");
                        shop.Register();
                        break;
                    case 2:
                        System.out.print("您选择的菜单是：登录\n");
                        shop.Login();
                        break;
                    case 3:
                        System.out.print("您选择的菜单是：查看商城\n");
                        shop.ViewShop();
                        break;
                    case 4:
                        System.out.print("您选择的菜单是：查看我购买的商品\n");
                        shop.ViewMyPurchases();
                        break;
                    case 5:
                        System.out.print("您选择的菜单是：管理员登陆\n");
                        shop.AdminLogin();
                        break;
                    case 6:
                        System.out.print("您选择的菜单是：退出系统\n");
                        loop=false;
                        break;
                    default:
                        System.out.print("请选择1-6\n");
                        break;
                }
            }catch(NumberFormatException e){
                System.out.println("请选择1-6\n");
            }
        }while(loop);
    }
}
