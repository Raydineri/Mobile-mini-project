package tn.rnu.isetr.tp.Entity;

public class User {
    private String name;
    private String email;
    private String password;
    private String ImageURL;
    private String PhoneNumber;

    public String getPhoneNumber() {
        return PhoneNumber;
        }
    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }
    public User(String name, String email, String PhoneNumber) {
        this.name = name;
        this.email = email;
        this.PhoneNumber = PhoneNumber;
    }
    public User(String name, String email, String password,  String PhoneNumber) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.ImageURL =  null ;

    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
    public String getImageURL() {
        return ImageURL;
    }
    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }
}
