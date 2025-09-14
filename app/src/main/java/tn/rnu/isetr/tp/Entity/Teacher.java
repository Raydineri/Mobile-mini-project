package tn.rnu.isetr.tp.Entity;

import java.io.Serializable;

public class Teacher  implements Serializable {
    private String name;
    private String email;
    private String id;
    public Teacher(String name, String email) {
        this.name = name;
        this.email = email;
    }
    public Teacher(String name, String email , String id) {
        this.id=    id;
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "name='" + name + '\'' +
                '}';
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }




}