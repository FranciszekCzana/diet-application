package com.springboot.dietapplication.model.mongo.menu;

import com.springboot.dietapplication.model.type.DayMealType;
import com.springboot.dietapplication.model.type.DayType;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "DayMeals")
public class MongoDayMeal {

    private String id;

    private String weekMealId;

    private List<String> mealList; // Lista odnośników do posiłków

    private DayType dayType; //Day name

    private String date;

    //private Map<String, Float> limits; // Limity kaloryczności

    public MongoDayMeal() {

    }

    public MongoDayMeal(String weekMealId, List<String> mealList, DayType dayType, String date) {
        this.weekMealId = weekMealId;
        this.mealList = mealList;
        this.dayType = dayType;
        this.date = date;
    }

    public MongoDayMeal(DayMealType dayMealType) {
        this.id = dayMealType.getId();
        this.weekMealId = dayMealType.getWeekMealId();
        this.mealList = dayMealType.getMealList();
        this.dayType = dayMealType.getDayType();
        this.date = dayMealType.getDate();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWeekMealId() {
        return weekMealId;
    }

    public void setWeekMealId(String weekMealId) {
        this.weekMealId = weekMealId;
    }

    public List<String> getMealList() {
        return mealList;
    }

    public void setMealList(List<String> mealList) {
        this.mealList = mealList;
    }

    public DayType getDayType() {
        return dayType;
    }

    public void setDayType(DayType dayType) {
        this.dayType = dayType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
