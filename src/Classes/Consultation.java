package Classes;

import Interfaces.Printable;
import Classes.SubModels.EncryptAndDecrypt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class Consultation implements Printable {
    private String consultationId;
    private Person doctor;
    private Session session;
    private Person patient;
    private double price;
    private Date date;
    private Date time;
    private int tokenNumber;

    // Encrypted data
    private String notes;
    private ArrayList<String> imagesPaths;

    private double hours;

    public Consultation(String consultationId, Session session, Person doctor, Person patient, double hours, String notes, ArrayList<String> imagePaths) throws IOException {
        this.consultationId = consultationId;
        this.session = session;
        this.doctor = doctor;
        this.patient = patient;
        this.hours = hours;
        this.notes = EncryptAndDecrypt.encryptText(notes, 5);
        this.date = session.getDate();

        this.imagesPaths = imagePaths;

        // Encrypt images
        if (imagePaths != null) {
            for (String imagePath : imagePaths) {
                EncryptAndDecrypt.encryptImage(imagePath, 5);
            }
        }

        // Set consultation time
        time = new Date();
        Consultation[] consultations = session.getConsultations();
        Date timeTemp = session.getTime();
        int minutes = 0;
        for (int i = 0; i < consultations.length; i++) {
            if (consultations[i] != null) {
                minutes += consultations[i].getHours() * 60;
            }
        }

        // Calculate patients time slot
        int timeInMinutes = timeTemp.getHours() * 60 + timeTemp.getMinutes();
        timeInMinutes += minutes;
        time.setHours(timeInMinutes / 60);
        time.setMinutes(timeInMinutes % 60);

        // Calculate cost
        boolean isFirstConsultation = true;
        Patient patient1 = (Patient) patient;
        for (Session session1 : WestminsterSkinConsultationManager.getSessionsArrayList()) {
            for (Consultation consultation : session1.getConsultations()) {
                if (consultation != null) {
                    Patient patient2 = (Patient) consultation.getPatient();
                    if (patient2.getPatientId().equals(patient1.getPatientId())) {
                        isFirstConsultation = false;
                    }
                }
            }
        }
        if (isFirstConsultation) {
            if (hours <= 1) {
                this.price = 15;
            } else {
                this.price = 15 * hours;
            }
        } else {
            if (hours <= 1) {
                this.price = 25;
            } else {
                this.price = 25 * hours;
            }
        }

        // Calculate token number
        for (int i = 0; i < consultations.length; i++) {
            if (consultations[i] != null) {
                tokenNumber = i + 1;
            }
        }

    }

    public Person getDoctor() {
        return doctor;
    }

    public void setDoctor(Person doctor) {
        this.doctor = doctor;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Person getPatient() {
        return patient;
    }

    public void setPatient(Person patient) {
        this.patient = patient;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }


    public double getHours() {
        return hours;
    }

    public void setHours(double hours) {
        this.hours = hours;
    }

    public String getConsultationId() {
        return consultationId;
    }

    public int getTokenNumber() {
        return tokenNumber;
    }

    public void setTokenNumber(int tokenNumber) {
        this.tokenNumber = tokenNumber;
    }

    public void setConsultationId(String consultationId) {
        this.consultationId = consultationId;
    }

    @Override
    public String print() {
        Doctor doctor = (Doctor) this.doctor;
        Patient patient = (Patient) this.patient;

        return "Consultation ID: " + consultationId + " Doctor: " + doctor.getName() + " " + doctor.getSurName() + " Patient: " + patient.getName() + " " + patient.getSurName() + " Date: " + date + " Time: " + time + " Price: " + price + " Notes: " + notes + " Hours: " + hours;
    }

    public ArrayList<String> getImagesPaths() {
        return imagesPaths;
    }

    public void setImagesPaths(ArrayList<String> imagesPaths) {
        this.imagesPaths = imagesPaths;
    }

    public String getStringDate() {
        int year = Integer.parseInt(String.valueOf(date.getYear())) + 1900;
        int month = Integer.parseInt(String.valueOf(date.getMonth())) + 1;
        int day = Integer.parseInt(String.valueOf(date.getDate()));
        String birthMonthString = switch (month) {
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
        return day + "-" + birthMonthString + "-" + year;
    }

    public String getStringTime() {
        int hour = Integer.parseInt(String.valueOf(time.getHours()));
        int minute = Integer.parseInt(String.valueOf(time.getMinutes()));
        return hour + ":" + minute;
    }
}
