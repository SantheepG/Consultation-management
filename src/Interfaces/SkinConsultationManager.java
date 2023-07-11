package Interfaces;

import Classes.Person;
import Classes.Session;

import java.io.IOException;
import java.util.ArrayList;

public interface SkinConsultationManager {

    // Doctor Methods
    String addANewDoctor(String name, String surName, String dateOfBirth, String mobileNumber, String medicalLicenceNumber, String specialisation, String availability);

    String deleteADoctor(String medicalLicenceNumber);

    void printAllDoctors();

    String saveDoctorsToFile();

    void readDoctorFile();

    String loadDoctorsFromFile();


    //methods for patients
    String addNewPatient(String name, String surName, String stringDateOfBirth, String mobileNumber, String patentId, String gender);

    String deleteAPatient(String patentId);

    void printAllPatients();

    void savePatientsToFile();

    void readPatientFile();

    void loadPatientsFromFile();


    //methods for sessions
    void addNewSession(String sessionID, String doctorID, String date, String time, int maxPatients, String sessionStatus);

    void deleteASession(String sessionID);

    void printAllSessions();

    void saveSessionsToFile();

    void readSessionFile();

    void loadSessionsFromFile();


    // Consultation Methods
    String addNewConsultation(String consultationId, Session session, Person doctor, Person patient, double hours, String notes, ArrayList<String> imagePath) throws IOException;

    String deleteAConsultation(String consultationID);

    void printAllConsultations();

    void saveConsultationsToFile();

    void readConsultationFile();

    void loadConsultationsFromFile();
}

