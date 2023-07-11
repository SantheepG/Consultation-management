package Classes;

import GUI.MainFrame;
import GUI.Main_Frames.ConsultationsPanel;
import GUI.Main_Frames.DashBoardPanel;
import GUI.Main_Frames.DoctorsPanel;
import GUI.Main_Frames.SessionsPanel;
import Interfaces.SkinConsultationManager;
import Classes.SubModels.EncryptAndDecrypt;

import javax.swing.*;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class WestminsterSkinConsultationManager implements SkinConsultationManager {
    public static Person[] doctorArray = new Doctor[10];
    public static ArrayList<Person> patientArrayList = new ArrayList<>();
    public static ArrayList<Session> sessionArrayList = new ArrayList<>();
    public static ArrayList<Consultation> consultationArrayList = new ArrayList<>();
    File doctorFile = new File("doctorList.txt");
    File patientFile = new File("patientList.txt");
    File sessionsFile = new File("sessionsList.txt");
    File consultationsFile = new File("consultationsList.txt");

    public WestminsterSkinConsultationManager() {
    }

    public static Person[] getDoctorArray() {
        return doctorArray;
    }

    public static ArrayList<Person> getPatientArrayList() {
        return patientArrayList;
    }

    public static ArrayList<Session> getSessionsArrayList() {
        return sessionArrayList;
    }

    public static ArrayList<Consultation> getConsultationsArrayList() {
        return consultationArrayList;
    }


    /**
     * Get All details of a Doctor
     * Get date of birth as a String parameter and cast it in to Date object using strToDate method.
     * Pass all parameters to Doctor object.
     * Add Doctor object to doctorArrayList
     *
     * @param name                 String name
     * @param surName              String Surname
     * @param stringDateOfBirth    String date of birth
     * @param mobileNumber         String Mobile Number
     * @param medicalLicenceNumber String Medical Licence Number
     * @param specialisation       String Specialisation
     * @return is doctor added
     * @Catch ParseException if the date of birth is not in correct format.
     * correct format Example 15-MAR-2000
     */
    @Override
    public String addANewDoctor(String name, String surName, String stringDateOfBirth, String mobileNumber, String medicalLicenceNumber, String specialisation, String availability) {
        if (isArrayFull(doctorArray)) {
            System.out.println("Doctor Array is Full");
            return "Doctor Array is Full";
        } else {
            Date dateOfBirth = strToDate(stringDateOfBirth);
            if (dateOfBirth != null) {
                Person doctor = new Doctor(name, surName, dateOfBirth, mobileNumber, medicalLicenceNumber, specialisation, availability);
                System.out.println("Doctor added successfully");
                for (int i = 0; i < doctorArray.length; i++) {
                    if (doctorArray[i] == null) {
                        doctorArray[i] = doctor;
                        break;
                    }
                }
            }
        }
        return "Doctor added successfully";
    }

    /**
     * @param medicalLicenceNumber Medical Licence Number of the doctor we want ro delete.
     *                             Traversal through doctorArrayList ArrayList and matching (i)th Doctor objects medicalLicenceNumber with parameter.
     *                             If they matched removed matched (i)th element from the ArrayList.
     * @return is doctor deleted
     * @Variable boolean "found" to keep track item found or not. It will update it in to true if (i)th element's medicalLicenceNumber matched with the parameter
     */
    @Override
    public String deleteADoctor(String medicalLicenceNumber) {
        boolean found = false;
        int foundIndex = 0;
        Session foundSession = null;
        String returnString = "";
        for (int i = 0; i < doctorArray.length; i++) {
            if (doctorArray[i] != null) {
                Doctor doctor = (Doctor) doctorArray[i];
                if (medicalLicenceNumber.equals(doctor.getMedicalLicenceNumber())) {
                    System.out.println("Doctor " + doctor.getName() + " " + doctor.getSurName() + " deleted successfully.");
                    returnString = "Doctor " + doctor.getName() + " " + doctor.getSurName() + " deleted successfully.";
                    found = true;
                    foundIndex = i;
                    break;
                }
            }
        }
        if (!found) {
            System.out.println("Doctor not found by Medical Licence Number: " + medicalLicenceNumber);
            returnString = "Doctor not found by Medical Licence Number: " + medicalLicenceNumber;
        } else {
            // Delete Consultation Belongs to deleted doctor
            for (Consultation consultation : consultationArrayList) {
                Doctor doc = (Doctor) consultation.getDoctor();
                if (doc.getMedicalLicenceNumber().equals(medicalLicenceNumber)) {
                    consultationArrayList.remove(consultation);
                    saveConsultationsToFile();
                    ConsultationsPanel.tableReRender(consultationArrayList);
                }
            }
            // Delete Sessions Belongs to deleted doctor
            try {
                for (Session session : sessionArrayList) {
                    Doctor doc = (Doctor) session.getDoctor();
                    if (doc.getMedicalLicenceNumber().equals(medicalLicenceNumber)) {
                        foundSession = session;
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
            // Finally Delete the doctor from doctors array
            if (foundSession != null) {
                sessionArrayList.remove(foundSession);
                saveSessionsToFile();
                SessionsPanel.tableReRender(sessionArrayList);
                DashBoardPanel.tableReRender(sessionArrayList);
            }
            doctorArray[foundIndex] = null;
            saveDoctorsToFile();
            try {
                DoctorsPanel.tableReRender(doctorArray);
            } catch (Exception e) {
                System.out.println("Doctors Panel not loaded yet");
            }


            int remainingDoctors = 0;
            for (Person doctor : doctorArray) {
                if (doctor != null) {
                    remainingDoctors++;
                }
            }
            if (remainingDoctors == 0) {
                System.out.println("No Doctors Available");
            } else {
                System.out.println("Remaining number of Doctors: " + remainingDoctors);
            }
        }
        return returnString;
    }

    /**
     * Sort doctorArrayList by calling to the sort method.
     * Traversal through doctorArrayList and call (i)th Doctor's print method.
     */
    @Override
    public void printAllDoctors() {
        if (!isDoctorArrayEmpty()) {
            sortDoctor(doctorArray);
            for (int i = 0; i < doctorArray.length; i++) {
                if (doctorArray[i] != null) {
                    Doctor doctor = (Doctor) doctorArray[i];
                    System.out.println("Doctor " + (i + 1) + ": " + doctor.print());
                }
            }
        } else {
            System.out.println("No Doctors Available");
        }
    }

    public boolean isDoctorArrayEmpty() {
        boolean isEmpty = true;
        for (Person doctor : doctorArray) {
            if (doctor != null) {
                isEmpty = false;
                break;
            }
        }
        return isEmpty;
    }

    /**
     * Save all Doctor objects in doctorArrayList to a text file.
     * Used buffer to improve performance.
     * 1st argument for fileWriter object pass the text file instance to the fileWriter. 2nd argument tells FileWriter to append any given input to the file instead of overwriting it.
     *
     * @return is saved
     */
    @Override
    public String saveDoctorsToFile() {
        if (doctorFile.exists()) {
            doctorFile.delete();
        }
        saveToFile(doctorFile, "Doctor");
        System.out.println("Doctors saved to file successfully");
        return "Doctors saved to file successfully";
    }


    @Override
    public void readDoctorFile() {
        readFile(doctorFile);
    }

    /**
     * Traversal through the doctorList text file and create Doctor object from each line.
     * To avoid exception if condition has been used. if only corresponding file exists read the file.
     *
     * @return status of the operation
     */
    @Override
    public String loadDoctorsFromFile() {
        String returnString = "";
        if (doctorFile.exists()) {
            try {
                Scanner myReader = new Scanner(doctorFile);
                loadFromFile(myReader, "Doctor");
                doctorFile.delete();
                myReader.close();
                System.out.println("Doctors loaded successfully");
                returnString = "Doctors loaded successfully";
            } catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
                returnString = "An error occurred.";
                e.printStackTrace();
            }
        } else {
            System.out.println("File not exists!");
            returnString = "File not exists!";
        }
        return returnString;
    }

    public static int getNumberOfDoctors(Person[] array) {
        int count = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null) {
                count++;
            }
        }
        return count;
    }

    /**
     * Get All details of a Patient.
     * Get date of birth as a String parameter and cast it in to Date object using strToDate method.
     * Pass all parameters to Patient object.
     * Add Patient object to patientArrayList
     *
     * @param name              Name of the patient.
     * @param surName           Surname of the patient.
     * @param stringDateOfBirth String birthday of the patient.
     * @param mobileNumber      Mobile number of the patient.
     * @param patentId          Unique ID for the patient.
     * @return status of the operation
     * @Catch ParseException if the date of birth is not in correct format.
     * correct format Example 15-MAR-2000
     */
    @Override
    public String addNewPatient(String name, String surName, String stringDateOfBirth, String mobileNumber, String patentId, String gender) {
        Date dateOfBirth = strToDate(stringDateOfBirth);
        Patient patient = new Patient(name, surName, dateOfBirth, mobileNumber, patentId, gender);
        patientArrayList.add(patient);
        System.out.println("Patient " + patient.getName() + " " + patient.getSurName() + " added successfully.");
        return "Patient " + patient.getName() + " " + patient.getSurName() + " added successfully.";
    }

    /**
     * Delete a Patient from patientArrayList.
     *
     * @param patentId Unique ID for the patient need to delete.
     * @return status of the operation
     */
    @Override
    public String deleteAPatient(String patentId) {
        String returnString = "";
        boolean found = false;
        for (int i = 0; i < patientArrayList.size(); i++) {
            Patient patient = (Patient) patientArrayList.get(i);
            if (patentId.equals(patient.getPatientId())) {
                System.out.println("Patient " + patient.getName() + " " + patient.getSurName() + " deleted successfully.");
                returnString = "Patient " + patient.getName() + " " + patient.getSurName() + " deleted successfully.";
                found = true;
                patientArrayList.remove(i);
                break;
            }
        }
        if (!found) {
            System.out.println("Patient not found by Medical Licence Number: " + patentId);
            returnString = "Patient not found by Medical Licence Number: " + patentId;
        }
        return returnString;
    }

    /**
     * Traversal through patientArrayList and call (i)th Patient's print method.
     */
    @Override
    public void printAllPatients() {
        sort(patientArrayList);
        for (int i = 0; i < patientArrayList.size(); i++) {
            Patient patient = (Patient) patientArrayList.get(i);
            System.out.println("Patient " + (i + 1) + ": " + patient.print());
        }
    }

    /**
     * Save all Patient objects in patientArrayList to a text file.
     * Used buffer to improve performance.
     * 1st argument for fileWriter object pass the text file instance to the fileWriter. 2nd argument tells FileWriter to append any given input to the file instead of overwriting it.
     */
    @Override
    public void savePatientsToFile() {
        if (patientFile.exists()) {
            patientFile.delete();
        }
        saveToFile(patientFile, "Patient");
        System.out.println("Patients saved to file successfully");
    }

    @Override
    public void readPatientFile() {
        readFile(doctorFile);
    }

    /**
     * Traversal through the patientList text file and create Patient object from each line.
     * To avoid exception if condition has been used. if only corresponding file exists read the file.
     */
    @Override
    public void loadPatientsFromFile() {
        if (patientFile.exists()) {
            try {
                Scanner reader = new Scanner(patientFile);
                loadFromFile(reader, "Patient");
                patientFile.delete();
                reader.close();
                System.out.println("Patients loaded successfully");
            } catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        } else {
            System.out.println("File not exists!");
        }
    }


    // Sessions methods

    /**
     * Add a new session to sessionArrayList.
     *
     * @param sessionID     Unique ID for the session.
     * @param doctorID      Unique ID for the doctor.
     * @param date          Date of the session.
     * @param time          Time of the session.
     * @param maxPatients   Maximum number of patients for the session.
     * @param sessionStatus Status of the session.
     */
    @Override
    public void addNewSession(String sessionID, String doctorID, String date, String time, int maxPatients, String sessionStatus) {
        Date sessionDate = strToDate(date);
        Doctor doctor = null;
        for (int i = 0; i < doctorArray.length; i++) {
            if (doctorArray[i] != null) {
                Doctor tempDoctor = (Doctor) doctorArray[i];
                if (doctorID.equals(tempDoctor.getMedicalLicenceNumber())) {
                    doctor = tempDoctor;
                    break;
                }
            }
        }
        Date sessionTime = strToTime(time);
        if (doctor != null) {
            Session session = new Session(sessionID, doctor, sessionDate, sessionTime, maxPatients, sessionStatus);
            sessionArrayList.add(session);
        } else {
            System.out.println("Doctor not found by Medical Licence Number: " + doctorID);
        }
    }

    /**
     * Delete a session from sessionArrayList.
     *
     * @param sessionID Unique ID for the session need to delete.
     */
    @Override
    public void deleteASession(String sessionID) {
        boolean found = false;
        for (int i = 0; i < sessionArrayList.size(); i++) {
            Session session = sessionArrayList.get(i);
            if (sessionID.equals(session.getSessionId())) {
                System.out.println("Session " + session.getSessionId() + " deleted successfully.");
                found = true;
                sessionArrayList.remove(i);
                break;
            }
        }
        if (!found) {
            System.out.println("Session not found by Session ID: " + sessionID);
        }
    }

    @Override
    public void printAllSessions() {
        for (int i = 0; i < sessionArrayList.size(); i++) {
            Session session = (Session) sessionArrayList.get(i);
            System.out.println("Session " + (i + 1) + ": " + session.print());
        }
    }

    /**
     * Save all Session objects in sessionArrayList to a text file.
     */
    @Override
    public void saveSessionsToFile() {
        if (sessionsFile.exists()) {
            sessionsFile.delete();
        }
        saveToFile(sessionsFile, "Session");
    }

    @Override
    public void readSessionFile() {
        readFile(sessionsFile);
    }

    /**
     * Traversal through the sessionList text file and create Session object from each line.
     * To avoid exception if condition has been used. if only corresponding file exists read the file.
     */
    @Override
    public void loadSessionsFromFile() {
        if (sessionsFile.exists()) {
            try {
                Scanner reader = new Scanner(sessionsFile);
                loadFromFile(reader, "Session");
                sessionsFile.delete();
                reader.close();
                System.out.println("Sessions loaded successfully");
            } catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        } else {
            System.out.println("File not exists!");
        }
    }

    // Consultation methods

    /**
     * Add a new consultation to consultationArrayList.
     *
     * @param consultationId Unique ID for the consultation.
     * @param session        Session of the consultation.
     * @param doctor         Doctor of the consultation.
     * @param patient        Patient of the consultation.
     * @param hours          Duration of the consultation.
     * @param notes          Notes of the consultation.
     * @param imagePaths     Image paths of the consultation.
     * @return string true if consultation added successfully.
     * @throws IOException if file not found.
     */
    @Override
    public String addNewConsultation(String consultationId, Session session, Person doctor, Person patient, double hours, String notes, ArrayList<String> imagePaths) throws IOException {
        String returnString = "";
        Consultation consultation = new Consultation(consultationId, session, doctor, patient, hours, notes, imagePaths);
        consultationArrayList.add(consultation);
        session.addConsultation(consultation);
        returnString = "Consultation " + consultationId + " added successfully.";
        return returnString;
    }

    /**
     * Delete a consultation from consultationArrayList.
     *
     * @param consultationID Unique ID for the consultation need to delete.
     * @return string true if consultation deleted successfully.
     */
    @Override
    public String deleteAConsultation(String consultationID) {
        String returnString = "";
        boolean found = false;
        for (int i = 0; i < consultationArrayList.size(); i++) {
            Consultation consultation = consultationArrayList.get(i);
            if (consultationID.equals(consultation.getConsultationId())) {
                System.out.println("Consultation " + consultation.getConsultationId() + " deleted successfully.");
                returnString = "Consultation " + consultation.getConsultationId() + " deleted successfully.";
                found = true;
                consultationArrayList.remove(i);
                break;
            }
        }
        if (!found) {
            System.out.println("Consultation not found by Consultation ID: " + consultationID);
            returnString = "Consultation not found by Consultation ID: " + consultationID;
        }
        return returnString;
    }

    @Override
    public void printAllConsultations() {
        for (int i = 0; i < consultationArrayList.size(); i++) {
            Consultation consultation = consultationArrayList.get(i);
            System.out.println("Consultation " + (i + 1) + ": " + consultation.print());
        }
    }

    /**
     * Save all Consultation objects in consultationArrayList to a text file.
     */
    @Override
    public void saveConsultationsToFile() {
        if (consultationsFile.exists()) {
            consultationsFile.delete();
        }
        saveToFile(consultationsFile, "Consultation");
    }

    @Override
    public void readConsultationFile() {
        readFile(consultationsFile);
    }

    /**
     * Traversal through the consultationList text file and create Consultation object from each line.
     * To avoid exception if condition has been used. if only corresponding file exists read the file.
     */
    @Override
    public void loadConsultationsFromFile() {
        if (consultationsFile.exists()) {
            try {
                Scanner reader = new Scanner(consultationsFile);
                loadFromFile(reader, "Consultation");
                consultationsFile.delete();
                reader.close();
            } catch (FileNotFoundException e) {
                System.out.println("An error occurred while loading check #loadConsultationsFromFile()");
                e.printStackTrace();
            }
        } else {
            System.out.println("File not exists!");
        }
    }


    /**
     * Traversal through a text file and print each line. To avoid exception if condition has been used. if only corresponding file exists read the file.
     */
    public void readFile(File file) {
        if (file.exists()) {
            try {
                Scanner myReader = new Scanner(doctorFile);
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    System.out.println(data);
                }
                myReader.close();
            } catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        } else {
            System.out.println("File not exists!");
        }
    }

    /**
     * Get date of birth as a String parameter and cast it in to Date object using @Class SimpleDateFormat
     *
     * @param strDate String date.
     * @return A date typed Date.
     * @Catch ParseException if the date of birth is not in correct format.
     * correct format Example 15-MAR-2000
     */
    public static Date strToDate(String strDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        Date dateDate = null;
        try {
            dateDate = dateFormat.parse(strDate);
        } catch (ParseException e) {
            System.out.println("Date of birth is not in correct format. Please enter date of birth in correct format. Example 15-MAR-2000");
        }
        return dateDate;
    }

    /**
     * Get String time as a String parameter and cast it in to Date object using @Class SimpleDateFormat
     *
     * @param strTime String time.
     * @return A Date typed time.
     */
    public static Date strToTime(String strTime) {
        DateFormat dateFormat = new SimpleDateFormat("hh:mm");
        Date date = null;
        try {
            date = dateFormat.parse(strTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    /**
     * Read a text file and add each line to a ArrayList.
     * If text file is doctor file, create a doctor object by calling addANewDoctor method.
     * If text file is patient file, create a patient object by calling addNewPatient method.
     *
     * @param reader Scanner object to read a text file.
     * @param type   Type of the file. Doctor or Patient.
     */
    public void loadFromFile(Scanner reader, String type) {
        if (type.equals("Doctor")) {
            while (reader.hasNextLine()) {
                ArrayList<String> dataItems = lineReader(reader.nextLine());
                String name = dataItems.get(0);
                String surName = dataItems.get(1);
                String dateOfBirth = dataItems.get(4) + "-" + dataItems.get(3) + "-" + dataItems.get(7);
                String mobileNumber = dataItems.get(8);
                String medicalLicenceNumber = dataItems.get(9);
                String specialisation = dataItems.get(10);
                String availability = dataItems.get(11);

                addANewDoctor(name, surName, dateOfBirth, mobileNumber, medicalLicenceNumber, specialisation, availability);
            }
        } else if (type.equals("Patient")) {
            while (reader.hasNextLine()) {
                ArrayList<String> dataItems = lineReader(reader.nextLine());
                String name = dataItems.get(0);
                String surName = dataItems.get(1);
                String dateOfBirth = dataItems.get(4) + "-" + dataItems.get(3) + "-" + dataItems.get(7);
                String mobileNumber = dataItems.get(8);
                String patentId = dataItems.get(9);
                String gender = dataItems.get(10);

                addNewPatient(name, surName, dateOfBirth, mobileNumber, patentId, gender);
            }
        } else if (type.equals("Session")) {
            while (reader.hasNextLine()) {
                ArrayList<String> dataItems = lineReader(reader.nextLine());
                String sessionID = dataItems.get(0);
                String doctorID = dataItems.get(1);
                String date = dataItems.get(4) + "-" + dataItems.get(3) + "-" + dataItems.get(7);
                String time = dataItems.get(11);
                int maxPatients = Integer.parseInt(dataItems.get(14));
                String sessionStatus = dataItems.get(15);

                addNewSession(sessionID, doctorID, date, time, maxPatients, sessionStatus);
            }
        } else if (type.equals("Consultation")) {
            while (reader.hasNextLine()) {
                try {
                    consultationLineReader(reader.nextLine());
                } catch (Exception e) {
                    System.out.println(e + "check #loadFromFile()");
                }
            }
        }
    }

    /**
     * Read data in an array list and Store that daa in to a text file.
     *
     * @param file A file that data should save.
     * @param type The type of ArrayList.
     */
    public void saveToFile(File file, String type) {
        try {
            FileWriter fileWriter = new FileWriter(file, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            if (type.equals("Doctor")) {
                for (Person doctor : doctorArray) {
                    if (doctor != null) {
                        Doctor doctorType = (Doctor) doctor;
                        bufferedWriter.write(doctorType.getName() + " " + doctorType.getSurName() + " " + doctorType.getDateOfBirth() + " " + doctorType.getMobileNumber() + " " + doctorType.getMedicalLicenceNumber() + " " + doctorType.getSpecialisation() + " " + doctorType.getAvailability() + "\n");
                    }
                }
            } else if (type.equals("Patient")) {
                for (Person patient : patientArrayList) {
                    Patient patientType = (Patient) patient;
                    bufferedWriter.write(patientType.getName() + " " + patientType.getSurName() + " " + patientType.getDateOfBirth() + " " + patientType.getMobileNumber() + " " + patientType.getPatientId() + " " + patientType.getGender() + "\n");
                }
            } else if (type.equals("Session")) {
                for (Session session : sessionArrayList) {
                    Doctor doctorType = (Doctor) session.getDoctor();
                    bufferedWriter.write(session.getSessionId() + " " + doctorType.getMedicalLicenceNumber() + " " + session.getDate() + " " + session.getTime() + " " + session.getMaxPatients() + " " + session.getSessionStatus() + "\n");
                }
            } else if (type.equals("Consultation")) {
                for (Consultation consultation : consultationArrayList) {
                    Patient patientType = (Patient) consultation.getPatient();
                    Doctor doctorType = (Doctor) consultation.getDoctor();

                    String imagePaths = "";
                    for (String imagePath : consultation.getImagesPaths()) {
                        imagePaths += imagePath + ",";
                    }

                    bufferedWriter.write(consultation.getConsultationId() + " " + consultation.getSession().getSessionId() + " " + doctorType.getMedicalLicenceNumber() + " " + patientType.getPatientId() + " " + consultation.getHours() + " *" + consultation.getNotes() + "* <" + imagePaths + "> " + "\n");
                }
            }
            bufferedWriter.close();

        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * Get a line as a String parameter and split it by space.
     *
     * @param line A line of a text file.
     * @return An ArrayList of String.
     */
    public ArrayList<String> lineReader(String line) {
        ArrayList<String> dataItems = new ArrayList<>();
        ArrayList<String> tempItem = new ArrayList<>();
        for (int i = 0; i < line.length(); i++) {
            if (!String.valueOf(line.charAt(i)).equals(" ")) {
                tempItem.add(String.valueOf(line.charAt(i)));
            } else {
                dataItems.add(String.join("", tempItem));
                tempItem.clear();
            }
            if (i == line.length() - 1) {
                dataItems.add(String.join("", tempItem));
                tempItem.clear();
            }
        }
        return dataItems;
    }

    /**
     * Get a line as a String parameter and split it by space.
     * Once it gets a line it will create a new Consultation object.
     *
     * @param line A line of a text file.
     */
    public void consultationLineReader(String line) throws IOException {
        ArrayList<String> dataItems = new ArrayList<>();
        ArrayList<String> tempItem = new ArrayList<>();
        for (int i = 0; i < line.length(); i++) {
            if (!String.valueOf(line.charAt(i)).equals(" ")) {
                tempItem.add(String.valueOf(line.charAt(i)));
            } else {
                dataItems.add(String.join("", tempItem));
                tempItem.clear();
            }
            if (i == line.length() - 1) {
                dataItems.add(String.join("", tempItem));
                tempItem.clear();
            }
        }
        // Data items:
        String consultationID = dataItems.get(0);
        String sessionID = dataItems.get(1);
        String doctorID = dataItems.get(2);
        String patientID = dataItems.get(3);
        String hours = dataItems.get(4);
        String notes = dataItems.get(5);
        String images = dataItems.get(6);

        notes = notes.substring(1, notes.length() - 1);
        images = images.substring(1, images.length() - 1);
        notes = EncryptAndDecrypt.decryptText(notes, 5);

        ArrayList<String> imagesPaths = new ArrayList<>();
        if (!images.equals("")) {
            String[] imagesArr = images.split(",");
            for (String image : imagesArr) {
                imagesPaths.add(image);
                // Encrypt when loading
                EncryptAndDecrypt.decryptImage(image, 5);
            }
        }
        Session session = null;
        for (Session tempSes : sessionArrayList) {
            if (tempSes.getSessionId().equals(sessionID)) {
                session = tempSes;
            }
        }
        Person doctor = null;
        for (Person tempDoc : doctorArray) {
            if (tempDoc != null) {
                Doctor tempDocType = (Doctor) tempDoc;
                if (tempDocType.getMedicalLicenceNumber().equals(doctorID)) {
                    doctor = tempDoc;
                }
            }
        }
        Person patient = null;
        for (Person tempPat : patientArrayList) {
            Patient tempPatType = (Patient) tempPat;
            if (tempPatType.getPatientId().equals(patientID)) {
                patient = tempPat;
            }
        }
        double hoursDouble = Double.parseDouble(hours);
        addNewConsultation(consultationID, session, doctor, patient, hoursDouble, notes, imagesPaths);
    }

    /**
     * Sort a given ArrayList using a bubble sort algorithm.
     *
     * @param arrayList ArrayList to sort.
     */

    public void sort(ArrayList<Person> arrayList) {
        Person temp;
        for (int i = 0; i < arrayList.size(); i++) {
            for (int j = 1; j < (arrayList.size()); j++) {
                int comparisonReturn = (arrayList.get(j - 1).getSurName()).compareTo(arrayList.get(j).getSurName());
                if (comparisonReturn > 0) {
                    temp = arrayList.get(j - 1);
                    arrayList.set(j - 1, arrayList.get(j));
                    arrayList.set(j, temp);
                }
            }
        }
    }

    /**
     * Sort a given ArrayList using a bubble sort algorithm.
     * sort by Doctor's surname
     *
     * @param doctorArray ArrayList to sort.
     */
    public void sortDoctor(Person[] doctorArray) {
        Person temp;
        for (int i = 0; i < doctorArray.length; i++) {
            for (int j = 1; j < (doctorArray.length); j++) {
                if (doctorArray[j - 1] != null && doctorArray[j] != null) {
                    int comparisonReturn = (doctorArray[j - 1].getSurName()).compareTo(doctorArray[j].getSurName());
                    if (comparisonReturn > 0) {
                        temp = doctorArray[j - 1];
                        doctorArray[j - 1] = doctorArray[j];
                        doctorArray[j] = temp;
                    }
                }
            }
        }
        System.out.println("Doctors sorted by surname.");
    }

    /**
     * Sort a given ArrayList using a bubble sort algorithm.
     * sort by date
     *
     * @param arrayList ArrayList to sort.
     */
    public void sortSession(ArrayList<Session> arrayList) {
        Session temp;
        for (int i = 0; i < arrayList.size(); i++) {
            for (int j = 1; j < (arrayList.size()); j++) {
                int comparisonReturn = (arrayList.get(j - 1).getDate()).compareTo(arrayList.get(j).getDate());
                if (comparisonReturn > 0) {
                    temp = arrayList.get(j - 1);
                    arrayList.set(j - 1, arrayList.get(j));
                    arrayList.set(j, temp);
                }
            }
        }
    }

    /**
     * Sort a given ArrayList using a bubble sort algorithm.
     * Sort by consultation date
     *
     * @param arrayList ArrayList to sort.
     */
    public void sortConsultation(ArrayList<Consultation> arrayList) {
        Consultation temp;
        for (int i = 0; i < arrayList.size(); i++) {
            for (int j = 1; j < (arrayList.size()); j++) {
                int comparisonReturn = (arrayList.get(j - 1).getSession().getDate()).compareTo(arrayList.get(j).getSession().getDate());
                if (comparisonReturn > 0) {
                    temp = arrayList.get(j - 1);
                    arrayList.set(j - 1, arrayList.get(j));
                    arrayList.set(j, temp);
                }
            }
        }
    }


    /**
     * Run the GUI.
     */
    public void runGUI() {
        MainFrame mainFrame = new MainFrame();
        mainFrame.setTitle("Hospital Management System");
        mainFrame.setSize(1300, 800);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
    }


    public boolean isArrayFull(Person[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) {
                return false;
            }
        }
        return true;
    }
}
