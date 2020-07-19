
package com.bhaa.finalproject;

public class Lesson {

    String subject;
    String topic;
    String date;
    String time;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    String userName;

    public String getID() {
        return id;
    }

    public void steID(String id) {
        this.id = id;
    }

     String id;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Lesson(String subject, String topic, String date, String time,String id,String userName){
        this.subject=subject;
        this.topic=topic;
        this.date=date;
        this.time=time;
        this.id=id;
        this.userName=userName;
    }

    public Lesson(){

    }



}
