package Classes;

import java.util.Date;

public class Patient extends Person{
    private static int numberOfConsultations;
    static int numberOfPatients;
    private String patientId;
    private String gender;

    public Patient(String name, String surName, Date dateOfBirth, String mobileNumber, String patientId, String gender) {
        super(name, surName, dateOfBirth, mobileNumber);
        this.patientId = patientId;
        this.gender = gender;
        numberOfPatients ++;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }


    @Override
    public String print() {
        return "Name: "+super.getName()+ " Surname: "+super.getSurName()+" Age: "+ super.getAge()+" Mobile Number: " + super.getMobileNumber()+ " Patient ID: "+ patientId;
    }
}

