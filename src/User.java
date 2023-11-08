import java.util.Scanner;

public class User {
    private String name;//用户名
    private String password;//密码
    private String[] record=new String[1000];//购买记录
    private int nums=0;//购买数量

    public User(){}
    public User(String name,String password){
        this.name=name;
        this.password=password;
    }

    public String getName() {return name;}
    public String getPassword() {return password;}
    public void setName(String name) {this.name = name;}
    public void setPassword(String password) {this.password = password;}

    //注册用户
    public static User Register(){
        String name,password;
        Scanner scanner=new Scanner(System.in);
        boolean isExist;

        do{
            isExist=false;
            System.out.println("请输入用户名：");
            name= scanner.next();
            if(name.length()<3)
                System.out.println("用户名长度不能小于3位!");

            for(int i=0;i<eMall.UserAmount;i++) {
                System.out.println("you bug");
                if (name.equals(eMall.Users[i].getName())) {
                    isExist = true;
                    System.out.println("用户名已存在！");
                }
            }
        }while(name.length()<3||isExist);


        do{
            System.out.println("请输入密码：");
            password= scanner.next();
            if(password.length()<6)
                System.out.println("密码长度不能小于6位!");
            if(!Validator.isAlphaNumeric(password))
                System.out.println("密码必须包含字母和数字！");
        }while(password.length()<6||!Validator.isAlphaNumeric(password));
        //当password为字母和数字的组合时返回true，否则返回false

        User newUser=new User(name,password);
        System.out.println("注册成功！");
        return newUser;
    }

    //用户登录
    public static User Login(){
        String loginName;
        String loginPassword;
        Scanner scanner=new Scanner(System.in);
        System.out.println("请输入用户名：");
        loginName = scanner.next();

        for (int i = 0; i < eMall.UserAmount; i++) {
            if (loginName.equals(eMall.Users[i].getName())) {
                System.out.println("请输入密码：");
                loginPassword = scanner.next();

                while (!loginPassword.equals(eMall.Users[i].getPassword())) {
                    System.out.println("密码错误，请重新输入：");
                    loginPassword = scanner.next();
                }
                User userNow = new User(loginName, loginPassword);
                return userNow;
            }
        }
        System.out.println("未找到该用户");
        return null;
    }

    //向当前登录用户的购买记录里新增记录
    public void addRecord(String record){
        this.record[this.nums++]=nums+" "+record;
    }
    //打印当前登录用户的购买记录
    public void getRecord(){
        if(nums==0) {
            System.out.println("购买记录为空。");
            return;
        }
        System.out.println("-----"+this.name+"的购买记录-----");
        for (int i=0;i<nums;i++)
            System.out.println(record[i]);
        System.out.println("---------------");
    }

}
