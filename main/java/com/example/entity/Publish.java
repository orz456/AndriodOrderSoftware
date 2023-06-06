package com.example.entity;

public class Publish {
    public int id;
    public String username;
    public String title;
    public String content ;
    public String price;
    public String startDate;
    public String startTime;
    public String finishDate;
    public String finishTime;
    public String region;
    public int status;
    public String orderNumber;
    public String receiver;
    public Publish(){

    }

    public Publish(String username, String title, String content, String price, String startDate, String startTime, String finishDate, String finishTime, String region, int status, String orderNumber, String receiver) {
        this.username = username;
        this.title = title;
        this.content = content;
        this.price = price;
        this.startDate = startDate;
        this.startTime = startTime;
        this.finishDate = finishDate;
        this.finishTime = finishTime;
        this.region = region;
        this.status = status;
        this.orderNumber = orderNumber;
        this.receiver = receiver;
    }

    public int getStatus() {
        return status;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPrice() {
        return price;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    @Override
    public String toString() {
        return "Publish{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", price='" + price + '\'' +
                ", startDate='" + startDate + '\'' +
                ", startTime='" + startTime + '\'' +
                ", finishDate='" + finishDate + '\'' +
                ", finishTime='" + finishTime + '\'' +
                ", region='" + region + '\'' +
                ", status=" + status +
                ", orderNumber='" + orderNumber + '\'' +
                ", receiver='" + receiver + '\'' +
                '}';
    }
}
