package zanydruid.team10foodrecipe.Models;

/**
 * Created by yizhu on 4/6/16.
 */
public class Unit{
    int unitId;
    String unit;

    public Unit(int id, String unit){
        this.unitId = id;
        this.unit = unit;
    }

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
