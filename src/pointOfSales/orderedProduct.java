package pointOfSales;
import java.util.ArrayList;
public class orderedProduct {
    private String teaType;
    private int quantity;
    private ArrayList<String> toppings = new ArrayList<>();
    private String price;
    private Double sugarLevel;
    private String iceLevel;
    private String note;
    private String pid;

    public orderedProduct(){
        teaType = "NULL";
        quantity = 0;
        price = "NULL";
    }

    public String getTeaType(){
        return this.teaType;
    }

    public int getQuantity(){
        return this.quantity;
    }

    public ArrayList<String> getToppings(){
        return this.toppings;
    }

    public String getPrice(){
        return this.price;
    }

    public Double getSugar(){
        return this.sugarLevel;
    }

    public String getIce(){
        return this.iceLevel;
    }

    public String getNote(){
        return this.note;
    }

    public String getId(){
        return this.pid;
    }

    public void setTeaType(String type){
        teaType = type;
    }

    public void setQuantity(int quant){
        quantity = quant;
    }

    public void addToList(String value){
        toppings.add(value);
    }

    public void setPrice(String money){
        price = money;
    }

    public void clearList(){
        toppings.clear();
    }

    public void setSugar(Double sugar){
        sugarLevel = sugar;
    }

    public void setIce(String ice){
        iceLevel = ice;
    }

    public void setNote(String message){
        note = message;
    }

    public void setId(String id){
        pid = id;
    }
}
