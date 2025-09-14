package tn.rnu.isetr.tp.Entity;

public class Tasks {
    private int id;
    private String title;
    private String description;
    private String date;
    private String priority;
    private String status;

    public Tasks() {
    }

    public Tasks(String title, String description, String date, String priority, String status) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.priority = priority;
        this.status = status;
    }

    public Tasks(int id, String title, String description, String date, String priority, String status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.priority = priority;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
