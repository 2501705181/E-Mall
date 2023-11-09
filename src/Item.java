import java.math.BigDecimal;

public class Item {
    private String name;
    private String number;
    private int amount;
    private BigDecimal price;


    public Item(){
        this.amount=0;
        this.price=new BigDecimal(0);
    }
    public Item(String number,String name,BigDecimal price,int amount){
        this.name=name;
        this.number=number;
        this.amount=amount;
        this.price=price;
    }

    public void setName(String name) {this.name = name;}

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public int getAmount() {
        return amount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString(){
        return "名称："+this.name+" 编号：" +this.number
                +" 价格："+this.price+" 数量："+this.amount;
    }

}