import java.math.BigDecimal;

public class eItem {
    private String name;
    private String number;
    private long amount;
    private BigDecimal price;


    public eItem(){
        this.amount=0;
        this.price=new BigDecimal(0);
    }
    public eItem(String name,String number,long amount,BigDecimal price){
        this.name=name;
        this.number=number;
        this.amount=amount;
        this.price=price;
    }
    @Override
    public  String toString(){
        return "名称："+this.name+" 编号：" +this.number
                +" 数量："+this.amount+" 价格："+this.price;
    }

}