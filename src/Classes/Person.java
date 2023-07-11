package Classes;
import Interfaces.Printable;
import java.time.LocalDateTime;
import java.util.Date;

public class Person implements Printable {
    private String name;
    private String surName;
    private Date dateOfBirth;
    private String mobileNumber;

    public Person(String name, String surName, Date dateOfBirth, String mobileNumber) {
        this.name = name;
        this.surName = surName;
        this.dateOfBirth = dateOfBirth;
        this.mobileNumber = mobileNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getStringDateOfBirth() {
        int birthYear = Integer.parseInt(String.valueOf(dateOfBirth.getYear()))+1900;
        int birthMonth = Integer.parseInt(String.valueOf(dateOfBirth.getMonth()))+1;
        int birthDay = Integer.parseInt(String.valueOf(dateOfBirth.getDate()));
        String birthMonthString = switch (birthMonth) {
            case 1 -> "January";
            case 2 -> "February";
            case 3 -> "March";
            case 4 -> "April";
            case 5 -> "May";
            case 6 -> "June";
            case 7 -> "July";
            case 8 -> "August";
            case 9 -> "September";
            case 10 -> "October";
            case 11 -> "November";
            case 12 -> "December";
            default -> "";
        };
        return birthDay + "-" + birthMonthString + "-" + birthYear;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public int getAge() {
        LocalDateTime now = LocalDateTime.now();
        int birthYear = Integer.parseInt(String.valueOf(dateOfBirth.getYear()))+1900;
        return now.getYear() - birthYear;
    }

    @Override
    public String print() {
        return "Name: "+name+ " Surname: "+surName+" DOB: "+ dateOfBirth+" Age: "+ getAge()+" Mobile Number: " + mobileNumber;
    }
}

