package com.springboot.dietapplication.model.psql.menu;

import com.springboot.dietapplication.model.type.MenuSendingType;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "menus")
public class PsqlMenu implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "patient_id")
    private long patientId;

    @Column(name = "measurement_id")
    private long measurementId;

    @Column(name = "food_properties_id")
    private long foodPropertiesId;

    @Column(name = "start_date")
    private String startDate;

    @Column(name = "end_date")
    private String endDate;

    @Column(name = "activity_level")
    private float activityLevel;

    public PsqlMenu() {

    }

    public PsqlMenu(MenuSendingType menuSendingType) {
        this.startDate = menuSendingType.getStartDate();
        this.activityLevel = menuSendingType.getActivityLevel();
        this.measurementId = Long.parseLong(menuSendingType.getMeasurementId());
        this.patientId = Long.parseLong(menuSendingType.getPatientId());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPatientId() {
        return patientId;
    }

    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }

    public long getMeasurementId() {
        return measurementId;
    }

    public void setMeasurementId(long measurementId) {
        this.measurementId = measurementId;
    }

    public long getFoodPropertiesId() {
        return foodPropertiesId;
    }

    public void setFoodPropertiesId(long foodPropertiesId) {
        this.foodPropertiesId = foodPropertiesId;
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

    public float getActivityLevel() {
        return activityLevel;
    }

    public void setActivityLevel(float activityLevel) {
        this.activityLevel = activityLevel;
    }
}