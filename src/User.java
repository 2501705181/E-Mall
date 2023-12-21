import java.util.ArrayList;
import java.util.Scanner;

public class User {
    public String name;//用户名
    public String password;//密码
    private ArrayList<String> records=new ArrayList<>();//购买记录

    private static Shop shop=null;
    private static Scanner scanner=new Scanner(System.in);

    public User(){

    }
    public User(String name,String password,Shop shop){
        this.name=name;
        this.password=password;
        User.shop =shop;
    }

    public String getName() {return name;}
    public String getPassword() {return password;}

    //用户登录
    public static User Login(){
        String loginName;
        String loginPassword;
        Scanner scanner=new Scanner(System.in);

        System.out.println("请输入用户名：");loginName = scanner.next();
        for(User user: shop.Users){
            if(loginName.equals(user.getName())){
                System.out.println("请输入密码：");
                loginPassword = scanner.next();
                while (!loginPassword.equals(user.getPassword())) {
                    System.out.println("密码错误，请重新输入：");
                    loginPassword = scanner.next();
                }
                return user;
            }
        }
        System.out.println("未找到该用户");
        return null;
    }

    //新增购买记录
    public void addRecord(String record){
        records.add(record);
    }

    //打印当前登录用户的购买记录
    public void getRecord(){
        if(records.isEmpty()) {
            System.out.println("购买记录为空。");
            return;
        }
        System.out.println("-----"+this.name+"的购买记录-----");
        for (int i=0;i<records.size();i++)
            System.out.println(i+1+" "+records.get(i));
        System.out.println("-----共"+records.size()+"条记录------");
    }

}
