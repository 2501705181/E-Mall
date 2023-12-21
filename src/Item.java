import java.math.BigDecimal;

public class Item  {
    private String name;
    private int id;
    private int amount;
    private BigDecimal price;


    public Item(){
        this.amount=0;
        this.price=new BigDecimal(0);
    }
    public Item(int id, String name, BigDecimal price, int amount){
        this.name=name;
        this.id =id;
        this.amount=amount;
        this.price=price;
    }

    public void setName(String name) {this.name = name;}

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
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
        return "编号：" +this.id+" 名称："+this.name
                +" 价格："+this.price+" 数量："+this.amount;
    }




}