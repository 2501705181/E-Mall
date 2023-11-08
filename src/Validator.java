import java.util.regex.Pattern;
//正则表达式用来判断密码合法性
public class Validator {
    //static方法可以通过类名直接调用，不用实例化对象再调用
    public static boolean isAlphaNumeric(String str) {
        return Pattern.matches("((^[a-zA-Z]{1,}[0-9]{1,}[a-zA-Z0-9]*)+)|((^[0-9]{1,}[a-zA-Z]{1,}[a-zA-Z0-9]*)+)$", str);
    }
}