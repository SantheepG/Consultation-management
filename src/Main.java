import GUI.LoginFrame;
import Classes.*;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        WestminsterSkinConsultationManager westminsterSkinConsultationManager = new WestminsterSkinConsultationManager();
        Scanner input = new Scanner(System.in);

        while (true) {
            System.out.print("""
                    -----------------------------------------
                         WESTMINSTER SKIN CONSULTATION 
                    -----------------------------------------
                                 Manage doctors
                    -----------------------------------------  
                    (DA) -> Add a new Doctor
                    (DD) -> Delete a Doctor
                    (DP) -> Print of all Doctors
                    (DS) -> Save doctors to the File
                    (DR) -> Read doctors file
                    (DL) -> Load doctors from the Files
                    (DSO)-> Sort Doctors
                    -----------------------------------------  
                                 Manage patients
                    -----------------------------------------                                        
                    (PA) -> Add a new Patient
                    (PD) -> Delete a Patient
                    (PP) -> Print of all Patients
                    (PS) -> Save patients to the File
                    (PR) -> Read patients file
                    (PL) -> Load patients from the Files
                    -----------------------------------------  
                                 Manage sessions
                    -----------------------------------------                                        
                    (SA) -> Add a new Session
                    (SD) -> Delete a Session
                    (SP) -> Print of all Sessions
                    (SS) -> Save sessions to the File
                    (SR) -> Read sessions file
                    (SL) -> Load sessions from the Files
                    -----------------------------------------  
                               Manage consultations
                    -----------------------------------------                                        
                    (AC) -> Add a new Consultation
                    (DC) -> Delete a Consultation
                    (PC) -> Print of all Consultations
                                        
                    (GL)  -> Open GUI with all the data
                    (GN)  -> Open GUI with no data
                                        
                    (QQ)  -> Quit program
                    ----------------------------------------- 
                    Answer: """);
            String answer = input.nextLine();
            if (answer.equalsIgnoreCase("Q")) {
                break;
            } else if (answer.equalsIgnoreCase("DA")) {
                int availableDoctors = 0;
                for (Person doctor : WestminsterSkinConsultationManager.doctorArray) {
                    if (doctor == null) {
                        availableDoctors++;
                    }
                }
                if (availableDoctors >= 1) {
                    System.out.print("Enter Name: ");
                    String name = input.nextLine();
                    if (name.isBlank()) {
                        System.out.println("Name cannot be empty");
                        continue;
                    }
                    System.out.print("Enter Surname: ");
                    String surName = input.nextLine();
                    if (surName.isBlank()) {
                        System.out.println("Surname cannot be empty");
                        continue;
                    }
                    System.out.print("Enter Date of Birth [dd-MMM-yyyy]: ");
                    String dateOfBirth = input.nextLine();
                    if (dateOfBirth.isBlank()) {
                        System.out.println("Date of Birth cannot be empty");
                        continue;
                    }
                    System.out.print("Enter Mobile Number: ");
                    String mobileNumber = input.nextLine();
                    if (mobileNumber.isBlank()) {
                        System.out.println("Mobile Number cannot be empty");
                        continue;
                    }
                    if (!mobileNumber.matches("[0-9]+")) {
                        System.out.println("Mobile Number must be a number");
                        continue;
                    }
                    if (mobileNumber.length() != 10) {
                        System.out.println("Mobile Number must be 10 digits");
                        continue;
                    }

                    System.out.print("Enter Medical Licence Number: ");
                    String medicalLicenceNumber = input.nextLine();
                    boolean isUnique = true;
                    if (medicalLicenceNumber.isBlank()) {
                        System.out.println("Medical Licence Number cannot be empty");
                        continue;
                    }
                    for (Person doctor : WestminsterSkinConsultationManager.doctorArray) {
                        if (doctor != null) {
                            Doctor doc = (Doctor) doctor;
                            if (doc.getMedicalLicenceNumber().equals(medicalLicenceNumber)) {
                                isUnique = false;
                                break;
                            }
                        }
                    }
                    if (!isUnique) {
                        System.out.println("Medical Licence Number must be unique");
                        continue;
                    }
                    System.out.print("Enter Specialisation: ");
                    String specialisation = input.nextLine();
                    if (specialisation.isBlank()) {
                        System.out.println("Specialisation cannot be empty");
                        continue;
                    }
                    System.out.print("Enter Availability: ");
                    String availability = input.nextLine();
                    try {
                        westminsterSkinConsultationManager.addANewDoctor(name, surName, dateOfBirth, mobileNumber, medicalLicenceNumber, specialisation, availability);
                    } catch (Exception e) {
                        System.out.println("Enter a valid date Ex: 01-Jan-2000");
                    }
                } else {
                    System.out.println("No more doctors can be added");
                }

            } else if (answer.equalsIgnoreCase("DD")) {
                System.out.print("Enter Medical Licence Number: ");
                String medicalLicenceNumber = input.nextLine();
                westminsterSkinConsultationManager.deleteADoctor(medicalLicenceNumber);
            } else if (answer.equalsIgnoreCase("DP")) {
                westminsterSkinConsultationManager.printAllDoctors();
            } else if (answer.equalsIgnoreCase("DS")) {
                westminsterSkinConsultationManager.saveDoctorsToFile();
            } else if (answer.equalsIgnoreCase("DR")) {
                westminsterSkinConsultationManager.readDoctorFile();
            } else if (answer.equalsIgnoreCase("DL")) {
                westminsterSkinConsultationManager.loadDoctorsFromFile();
            } else if (answer.equalsIgnoreCase("DSO")) {
                westminsterSkinConsultationManager.sortDoctor(WestminsterSkinConsultationManager.getDoctorArray());
            }
            // Patient
            else if (answer.equalsIgnoreCase("PA")) {
                System.out.print("Enter Patient Name: ");
                String name = input.nextLine();
                System.out.print("Enter Patient Surname: ");
                String surName = input.nextLine();
                System.out.print("Enter Date of Birth [dd-MMM-yyyy]: ");
                String dateOfBirth = input.nextLine();
                System.out.print("Enter Mobile Number: ");
                String mobileNumber = input.nextLine();
                System.out.print("Enter Patient ID: ");
                String patentId = input.nextLine();
                System.out.print("Enter Gender: ");
                String gender = input.nextLine();
                westminsterSkinConsultationManager.addNewPatient(name, surName, dateOfBirth, mobileNumber, patentId, gender);
            } else if (answer.equalsIgnoreCase("PD")) {
                System.out.print("Enter Patient ID: ");
                String patentId = input.nextLine();
                westminsterSkinConsultationManager.deleteAPatient(patentId);
            } else if (answer.equalsIgnoreCase("PP")) {
                westminsterSkinConsultationManager.printAllPatients();
            } else if (answer.equalsIgnoreCase("PS")) {
                westminsterSkinConsultationManager.savePatientsToFile();
            } else if (answer.equalsIgnoreCase("PR")) {
                westminsterSkinConsultationManager.readPatientFile();
            } else if (answer.equalsIgnoreCase("PL")) {
                westminsterSkinConsultationManager.loadPatientsFromFile();
            }

            // Session
            else if (answer.equalsIgnoreCase("SA")) {
                System.out.print("Enter Session ID: ");
                String sessionId = input.nextLine();
                System.out.print("Enter Doctor ID: ");
                String doctorId = input.nextLine();
                System.out.print("Enter Date: ");
                String date = input.nextLine();
                System.out.print("Enter Time: ");
                String time = input.nextLine();
                System.out.print("Enter Maximum Patients: ");
                int maxPatients = input.nextInt();
                westminsterSkinConsultationManager.addNewSession(sessionId, doctorId, date, time, maxPatients, "Active");
            } else if (answer.equalsIgnoreCase("SD")) {
                System.out.print("Enter Session ID: ");
                String sessionId = input.nextLine();
                westminsterSkinConsultationManager.deleteASession(sessionId);
            } else if (answer.equalsIgnoreCase("SP")) {
                westminsterSkinConsultationManager.printAllSessions();
            } else if (answer.equalsIgnoreCase("SS")) {
                westminsterSkinConsultationManager.saveSessionsToFile();
            } else if (answer.equalsIgnoreCase("SR")) {
                westminsterSkinConsultationManager.readSessionFile();
            } else if (answer.equalsIgnoreCase("SL")) {
                westminsterSkinConsultationManager.loadSessionsFromFile();
            }

            // Consultation
            else if (answer.equalsIgnoreCase("AC")) {
                Session session = null;
                Person doctor = null;
                Person patient = null;
                double duration = 0;

                System.out.println("Enter Consultation ID: ");
                String consultationId = input.nextLine();
                for (Consultation consultation : WestminsterSkinConsultationManager.getConsultationsArrayList()) {
                    if (consultation.getConsultationId().equalsIgnoreCase(consultationId)) {
                        System.out.println("Consultation ID already exists");
                        break;
                    }
                }

                System.out.println("Enter Session ID: ");
                String sessionId = input.nextLine();
                for (int i = 0; i < WestminsterSkinConsultationManager.sessionArrayList.size(); i++) {
                    if (sessionId.equals(WestminsterSkinConsultationManager.sessionArrayList.get(i).getSessionId())) {
                        session = WestminsterSkinConsultationManager.sessionArrayList.get(i);
                    }
                }
                if (session == null) {
                    System.out.println("Session not found");
                    continue;
                }

                System.out.println("Enter Doctor ID: ");
                String doctorId = input.nextLine();
                for (int i = 0; i < WestminsterSkinConsultationManager.doctorArray.length; i++) {
                    if (WestminsterSkinConsultationManager.doctorArray[i] != null) {
                        Doctor doc = (Doctor) WestminsterSkinConsultationManager.doctorArray[i];
                        if (doc.getMedicalLicenceNumber().equals(doctorId)) {
                            doctor = doc;
                        }
                    }
                }
                if (doctor == null) {
                    System.out.println("Doctor not found");
                    continue;
                }

                System.out.println("Enter Patient ID: ");
                String patientId = input.nextLine();
                for (int i = 0; i < WestminsterSkinConsultationManager.patientArrayList.size(); i++) {
                    Patient pat = (Patient) WestminsterSkinConsultationManager.patientArrayList.get(i);
                    if (pat.getPatientId().equals(patientId)) {
                        patient = pat;
                    }
                }
                if (patient == null) {
                    System.out.println("Patient not found");
                    continue;
                }


                System.out.println("Enter Duration: ");
                try {
                    duration = Double.parseDouble(input.nextLine());
                } catch (Exception e) {
                    System.out.println("Invalid input");
                    continue;
                }

                System.out.println("Enter Notes: ");
                String notes = input.nextLine();

                Consultation consultation = new Consultation(consultationId, session, doctor, patient, duration, notes, null);
                WestminsterSkinConsultationManager.consultationArrayList.add(consultation);
                System.out.println("Consultation added successfully");
            } else if (answer.equalsIgnoreCase("PC")) {
                for (Consultation consultation : WestminsterSkinConsultationManager.consultationArrayList) {
                    System.out.println("Consultation ID: " + consultation.getConsultationId() + " | Session ID: " + consultation.getSession().getSessionId() + " | Doctor ID: " + consultation.getDoctor().getName() + " " + consultation.getDoctor().getSurName() + " | Patient ID: " + consultation.getPatient().getName() + " " + consultation.getPatient().getSurName() + " | Duration: " + consultation.getStringDate());
                }
            } else if (answer.equalsIgnoreCase("DC")) {
                boolean isFound = false;
                System.out.println("Enter Consultation ID: ");
                String consultationId = input.nextLine();
                for (int i = 0; i < WestminsterSkinConsultationManager.consultationArrayList.size(); i++) {
                    if (WestminsterSkinConsultationManager.consultationArrayList.get(i).getConsultationId().equals(consultationId)) {
                        WestminsterSkinConsultationManager.consultationArrayList.remove(i);
                        System.out.println("Consultation deleted successfully");
                        isFound = true;
                        break;
                    }
                }
                if (!isFound) {
                    System.out.println("Consultation not found");
                }
            }

            // GUI
            else if (answer.equalsIgnoreCase("GL")) {
                // Load all data from files
                westminsterSkinConsultationManager.loadDoctorsFromFile();
                westminsterSkinConsultationManager.saveDoctorsToFile();
                westminsterSkinConsultationManager.loadPatientsFromFile();
                westminsterSkinConsultationManager.savePatientsToFile();
                westminsterSkinConsultationManager.loadSessionsFromFile();
                westminsterSkinConsultationManager.saveSessionsToFile();
                westminsterSkinConsultationManager.loadConsultationsFromFile();
                westminsterSkinConsultationManager.saveConsultationsToFile();
                new LoginFrame();
            } else if (answer.equalsIgnoreCase("GN")) {
                new LoginFrame();
            } else if (answer.equalsIgnoreCase("QQ")) {
                break;
            } else {
                System.out.println("Invalid Input");
            }
        }
    }
}