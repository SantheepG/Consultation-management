package Classes;

import java.util.Date;

public class Doctor extends Person{
    static int numberOfDoctors;
    private String medicalLicenceNumber;
    private String specialisation;
    private String availability;

    public Doctor(String name, String surName, Date dateOfBirth, String mobileNumber, String medicalLicenceNumber, String specialisation, String availability) {
        super(name, surName, dateOfBirth, mobileNumber);
        this.medicalLicenceNumber = medicalLicenceNumber;
        this.specialisation = specialisation;
        this.availability = availability;
        numberOfDoctors ++;
    }

    public String getMedicalLicenceNumber() {
        return medicalLicenceNumber;
    }

    public void setMedicalLicenceNumber(String medicalLicenceNumber) {
        this.medicalLicenceNumber = medicalLicenceNumber;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getSpecialisation() {
        return specialisation;
    }

    public void setSpecialisation(String specialisation) {
        this.specialisation = specialisation;
    }

    @Override
    public String print() {
        return "Name: "+super.getName()+ " Surname: "+super.getSurName()+" DOB: "+ super.getStringDateOfBirth()+" Age: "+ super.getAge()+" Mobile Number: " + super.getMobileNumber() + " Medical Licence Number: "+medicalLicenceNumber+ " Specialisation: "+specialisation;
    }
}

