import java.util.InputMismatchException;
import java.util.Scanner;

public class adminView {

    private Admin admin=null;

    private static Scanner scanner=new Scanner(System.in);
    private static boolean loop=true;

    public adminView(Admin admin){
        this.admin=admin;
        Menu();
    }

    //显示菜单
    public void Menu(){
        int select;
        do {
            System.out.println("*****管理员菜单****");
            System.out.println("1.增加商品");
            System.out.println("2.修改商品");
            System.out.println("3.删除商品");
            System.out.println("4.退出");
            System.out.println("**********************");
            System.out.println("请选择菜单：");

            try {
                select = Integer.parseInt(scanner.next());
                switch (select) {
                    case 1:
                        System.out.print("您选择的菜单是：增加商品\n");
                        admin.addItem();
                        break;
                    case 2:
                        System.out.print("您选择的菜单是：修改商品\n");
                        admin.changItem();
                        break;
                    case 3:
                        System.out.print("您选择的菜单是：删除商品\n");
                        admin.delItem();
                        break;
                    case 4:
                        System.out.print("您选择的菜单是：退出\n");
                        exit();
                        break;
                    default:
                        System.out.print("请选择1-4\n");
                        break;
                }
            }catch(InputMismatchException |NumberFormatException e){
                System.out.println("输入错误");
            }
        }while(loop);
    }

    //退出
    public void exit(){
        loop=false;
    }
}
