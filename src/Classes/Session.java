package Classes;

import Interfaces.Printable;
import java.util.Date;

public class Session implements Printable {
    private String sessionId;
    private Person doctor;
    private Date date;
    private Date time;
    private int maxPatients;
    private String sessionStatus; // Active, Ongoing, On Hold;
    private int currentPatients;
    private Consultation[] consultations;

    public Session(String sessionId, Person doctor, Date date, Date time, int maxPatients, String sessionStatus) {
        this.sessionId = sessionId;
        this.doctor = doctor;
        this.date = date;
        this.time = time;
        this.maxPatients = maxPatients;
        this.sessionStatus = sessionStatus;
        this.currentPatients = 0;

        consultations = new Consultation[maxPatients];
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Person getDoctor() {
        return doctor;
    }

    public void setDoctor(Person doctor) {
        this.doctor = doctor;
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

    public int getMaxPatients() {
        return maxPatients;
    }

    public void setMaxPatients(int maxPatients) {
        this.maxPatients = maxPatients;
    }

    public String getSessionStatus() {
        return sessionStatus;
    }

    public void setSessionStatus(String sessionStatus) {
        this.sessionStatus = sessionStatus;
    }

    public int getCurrentPatients() {
        return currentPatients;
    }

    public void setCurrentPatients(int currentPatients) {
        this.currentPatients = currentPatients;
    }

    public Consultation[] getConsultations() {
        return consultations;
    }

    public int getAvailableConsultations() {
        int availableConsultations = maxPatients;
        for (int i = 0; i < consultations.length; i++) {
            if (consultations[i] != null) {
                availableConsultations--;
            }
        }
        return availableConsultations;
    }

    public void addConsultation(Consultation consultation){
        if (currentPatients < maxPatients){
            consultations[currentPatients] = consultation;
            currentPatients ++;
        }
    }

    public String getStringDate() {
        int year = Integer.parseInt(String.valueOf(date.getYear()))+1900;
        int month = Integer.parseInt(String.valueOf(date.getMonth()))+1;
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

    public String getStringTime(){
        int hour = Integer.parseInt(String.valueOf(time.getHours()));
        int minute = Integer.parseInt(String.valueOf(time.getMinutes()));
        return hour + ":" + minute;
    }
    @Override
    public String print() {
        Doctor doc = (Doctor) this.doctor;
        return "Session ID: " + sessionId + " Doctor: " + doc.getMedicalLicenceNumber() + " Date: " + date + " Time: " + time + " Max Patients: " + maxPatients + " Session Status: " + sessionStatus;
    }
}
