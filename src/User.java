public class User {
    private String name;
    private String password;

    public User(){}
    public User(String name,String password){
        this.name=name;
        this.password=password;
    }

    private String getName() {
        return name;
    }
    private String getPassword() {
        return password;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
