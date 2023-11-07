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

    public void addRecord(String record){
        this.record[this.nums++]=nums+" "+record;
    }

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


    public String getName() {
        return name;
    }


    public String getPassword() {
        return password;
    }


    public void setName(String name) {
        this.name = name;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
