package com.training.cst.quanlytienantrua.DataManager.Object;

/**
 * Created by longdg on 04/12/2016.
 */

public class Food {
    private String nameFood;
    private long priceFood;

    public Food(String nameFood, long priceFood) {
        this.nameFood = nameFood;
        this.priceFood = priceFood;
    }

    public String getNameFood() {
        return nameFood;
    }

    public void setNameFood(String nameFood) {
        this.nameFood = nameFood;
    }

    public long getPriceFood() {
        return priceFood;
    }

    public void setPriceFood(long priceFood) {
        this.priceFood = priceFood;
    }
}
