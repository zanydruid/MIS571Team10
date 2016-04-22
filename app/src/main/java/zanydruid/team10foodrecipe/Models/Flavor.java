package zanydruid.team10foodrecipe.Models;

/**
 * Created by yizhu on 4/6/16.
 */
public class Flavor{
    int flavorId;
    String flavorName;
    int amount;

    public Flavor(String name,int amount){
        this.flavorName = name;
        this.amount = amount;
    }

    public Flavor(int id, String name){
        this.flavorId = id;
        this.flavorName = name;
    }


    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getFlavorId() {
        return flavorId;
    }

    public void setFlavorId(int flavorId) {
        this.flavorId = flavorId;
    }

    public String getFlavorName() {
        return flavorName;
    }

    public void setFlavorName(String flavorName) {
        this.flavorName = flavorName;
    }

}
