package com.springboot.dietapplication.model.type;

import com.springboot.dietapplication.model.mongo.menu.MongoMenu;
import com.springboot.dietapplication.model.psql.menu.PsqlMenu;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "Menus")
public class MenuType {

    private String id;

    private String patientId; // Ref - Dane pacjenta

    private String measurementId; // Ref - Dane pomiarowe

    private List<String> weekMealList; // Lista odnośników do tygodniowych jadłospisów

    private List<FoodType> foodTypes; // Rodzaje posiłków do menu

    private String startDate; // Najwcześniejsza data z listy DayMeals

    private String endDate; // Najpóźniejsza data z listy DayMeals

    private FoodPropertiesType foodPropertiesType;

    private Float activityLevel;

    public MenuType() {

    }

    public MenuType(MenuSendingType menuSendingType) {
        this.startDate = menuSendingType.getStartDate();
        this.foodTypes = menuSendingType.getFoodTypes();
        this.activityLevel = menuSendingType.getActivityLevel();
        this.measurementId = menuSendingType.getMeasurementId();
        this.patientId = menuSendingType.getPatientId();
    }

    public MenuType(MongoMenu menu) {
        this.id = menu.getId();
        this.patientId = menu.getPatientId();
        this.measurementId = menu.getMeasurementId();
        this.weekMealList = menu.getWeekMealList();
        this.foodTypes = menu.getFoodTypes();
        this.startDate = menu.getStartDate();
        this.endDate = menu.getEndDate();
        this.foodPropertiesType = menu.getFoodPropertiesType();
        this.activityLevel = menu.getActivityLevel();
    }

    public MenuType(PsqlMenu menu) {
        if (menu.getId() > 0)
            this.id = String.valueOf(menu.getId());
        if (menu.getPatientId() > 0)
            this.patientId = String.valueOf(menu.getPatientId());
        if (menu.getMeasurementId() > 0)
            this.measurementId = String.valueOf(menu.getMeasurementId());
        this.startDate = menu.getStartDate();
        this.endDate = menu.getEndDate();
        this.activityLevel = menu.getActivityLevel();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getMeasurementId() {
        return measurementId;
    }

    public void setMeasurementId(String measurementId) {
        this.measurementId = measurementId;
    }

    public List<String> getWeekMealList() {
        return weekMealList;
    }

    public void setWeekMealList(List<String> weekMealList) {
        this.weekMealList = weekMealList;
    }

    public List<FoodType> getFoodTypes() {
        return foodTypes;
    }

    public void setFoodTypes(List<FoodType> foodTypes) {
        this.foodTypes = foodTypes;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public FoodPropertiesType getFoodPropertiesType() {
        return foodPropertiesType;
    }

    public void setFoodPropertiesType(FoodPropertiesType foodPropertiesType) {
        this.foodPropertiesType = foodPropertiesType;
    }

    public Float getActivityLevel() {
        return activityLevel;
    }

    public void setActivityLevel(Float activityLevel) {
        this.activityLevel = activityLevel;
    }
}
