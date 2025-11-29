package com.example.demo.model;

public class TravelRequest {

    private String destination;
    private String budget;
    private String startDate;
    private String endDate;

    public TravelRequest() {}

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public String getBudget() { return budget; }
    public void setBudget(String budget) { this.budget = budget; }

    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }

    public String getEndDate() { return endDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }
}
