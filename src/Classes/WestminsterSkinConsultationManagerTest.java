package Classes;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class WestminsterSkinConsultationManagerTest {

    @AfterEach
    void tearDown() {
        WestminsterSkinConsultationManager.consultationArrayList.clear();
        WestminsterSkinConsultationManager.patientArrayList.clear();
        WestminsterSkinConsultationManager.sessionArrayList.clear();
        for (int i = 0; i < WestminsterSkinConsultationManager.doctorArray.length; i++) {
            WestminsterSkinConsultationManager.doctorArray[i] = null;
        }
    }

    @Test
    void testAddANewDoctor() {
        WestminsterSkinConsultationManager manager = new WestminsterSkinConsultationManager();
        assertEquals("Doctor added successfully", (manager.addANewDoctor("John", "Mike", "12-May-2000", "0702010059", "DOC12345", "Cosmetic-Dermatology", "Available")));
    }

    @Test
    void testAddMoreThanTenDoctors() {
        WestminsterSkinConsultationManager manager = new WestminsterSkinConsultationManager();
        manager.addANewDoctor("John", "Mike", "12-May-2000", "0702010059", "DOC12345", "Cosmetic-Dermatology", "Available");
        manager.addANewDoctor("John", "Mike", "12-May-2000", "0702010059", "DOC12346", "Cosmetic-Dermatology", "Available");
        manager.addANewDoctor("John", "Mike", "12-May-2000", "0702010059", "DOC12347", "Cosmetic-Dermatology", "Available");
        manager.addANewDoctor("John", "Mike", "12-May-2000", "0702010059", "DOC12348", "Cosmetic-Dermatology", "Available");
        manager.addANewDoctor("John", "Mike", "12-May-2000", "0702010059", "DOC12349", "Cosmetic-Dermatology", "Available");
        manager.addANewDoctor("John", "Mike", "12-May-2000", "0702010059", "DOC12310", "Cosmetic-Dermatology", "Available");
        manager.addANewDoctor("John", "Mike", "12-May-2000", "0702010059", "DOC12311", "Cosmetic-Dermatology", "Available");
        manager.addANewDoctor("John", "Mike", "12-May-2000", "0702010059", "DOC12312", "Cosmetic-Dermatology", "Available");
        manager.addANewDoctor("John", "Mike", "12-May-2000", "0702010059", "DOC12313", "Cosmetic-Dermatology", "Available");
        manager.addANewDoctor("John", "Mike", "12-May-2000", "0702010059", "DOC12314", "Cosmetic-Dermatology", "Available");
        assertEquals("Doctor Array is Full", (manager.addANewDoctor("John", "Mike", "12-May-2000", "0702010059", "DOC12315", "Cosmetic-Dermatology", "Available")));
    }

    @Test
    void testDeleteADoctor() {
        WestminsterSkinConsultationManager manager = new WestminsterSkinConsultationManager();
        manager.addANewDoctor("John", "Mike", "12-May-2000", "0702010059", "DOC12345", "Cosmetic-Dermatology", "Available");
        assertEquals("Doctor John Mike deleted successfully.", (manager.deleteADoctor("DOC12345")));
    }


    @Test
    void testDeleteANotAvailableDoctor() {
        WestminsterSkinConsultationManager manager = new WestminsterSkinConsultationManager();
        manager.addANewDoctor("John", "Mike", "12-May-2000", "0702010059", "DOC12345", "Cosmetic-Dermatology", "Not Available");
        assertEquals("Doctor not found by Medical Licence Number: DOC00000", (manager.deleteADoctor("DOC00000")));
    }

    @Test
    void saveDoctorsToFile() {
        WestminsterSkinConsultationManager manager = new WestminsterSkinConsultationManager();
        manager.addANewDoctor("John", "Mike", "12-May-2000", "0702010059", "DOC12345", "Cosmetic-Dermatology", "Available");
        manager.addANewDoctor("John", "Mike", "12-May-2000", "0702010059", "DOC12346", "Cosmetic-Dermatology", "Available");
        manager.addANewDoctor("John", "Mike", "12-May-2000", "0702010059", "DOC12347", "Cosmetic-Dermatology", "Available");
        manager.addANewDoctor("John", "Mike", "12-May-2000", "0702010059", "DOC12348", "Cosmetic-Dermatology", "Available");
        manager.addANewDoctor("John", "Mike", "12-May-2000", "0702010059", "DOC12349", "Cosmetic-Dermatology", "Available");
        manager.addANewDoctor("John", "Mike", "12-May-2000", "0702010059", "DOC12310", "Cosmetic-Dermatology", "Available");
        manager.addANewDoctor("John", "Mike", "12-May-2000", "0702010059", "DOC12311", "Cosmetic-Dermatology", "Available");
        manager.addANewDoctor("John", "Mike", "12-May-2000", "0702010059", "DOC12312", "Cosmetic-Dermatology", "Available");
        manager.addANewDoctor("John", "Mike", "12-May-2000", "0702010059", "DOC12313", "Cosmetic-Dermatology", "Available");
        manager.addANewDoctor("John", "Mike", "12-May-2000", "0702010059", "DOC12314", "Cosmetic-Dermatology", "Available");
        assertEquals("Doctors saved to file successfully", (manager.saveDoctorsToFile()));
    }

    @AfterEach
    @Test
    void testLoadDoctorsFromFile() {
        WestminsterSkinConsultationManager manager = new WestminsterSkinConsultationManager();
        manager.saveDoctorsToFile();
        assertEquals("Doctors loaded successfully", (manager.loadDoctorsFromFile()));
    }

    @Test
    void testLoadDoctorsFromFileWhenFileIsNotAvailable() {
        WestminsterSkinConsultationManager manager = new WestminsterSkinConsultationManager();
        manager.doctorFile.delete();
        assertEquals("File not exists!", (manager.loadDoctorsFromFile()));
    }

    @Test
    void testGetNumberOfDoctors() {
        WestminsterSkinConsultationManager manager = new WestminsterSkinConsultationManager();
        manager.addANewDoctor("John", "Mike", "12-May-2000", "0702010059", "DOC12345", "Cosmetic-Dermatology", "Available");
        manager.addANewDoctor("John", "Mike", "12-May-2000", "0702010059", "DOC12346", "Cosmetic-Dermatology", "Available");
        manager.addANewDoctor("John", "Mike", "12-May-2000", "0702010059", "DOC12347", "Cosmetic-Dermatology", "Available");
        manager.addANewDoctor("John", "Mike", "12-May-2000", "0702010059", "DOC12348", "Cosmetic-Dermatology", "Available");
        manager.addANewDoctor("John", "Mike", "12-May-2000", "0702010059", "DOC12349", "Cosmetic-Dermatology", "Available");
        assertEquals(5, (WestminsterSkinConsultationManager.getNumberOfDoctors(WestminsterSkinConsultationManager.getDoctorArray())));
    }

    @Test
    void addNewPatient() {
        WestminsterSkinConsultationManager manager = new WestminsterSkinConsultationManager();
        assertEquals("Patient John Mike added successfully.", (manager.addNewPatient("John", "Mike", "12-May-2000", "0702010059", "P12345", "Male")));
    }

    @Test
    void testDeleteAPatient() {
        WestminsterSkinConsultationManager manager = new WestminsterSkinConsultationManager();
        manager.addNewPatient("John", "Mike", "12-May-2000", "0702010059", "P123", "Male");
        assertEquals("Patient John Mike deleted successfully.", (manager.deleteAPatient("P123")));
    }

    @Test
    void testDeleteANotAvailablePatient() {
        WestminsterSkinConsultationManager manager = new WestminsterSkinConsultationManager();
        assertEquals("Patient not found by Medical Licence Number: P1234", (manager.deleteAPatient("P1234")));
    }

    @Test
    void testAddNewConsultation() throws IOException {
        WestminsterSkinConsultationManager manager = new WestminsterSkinConsultationManager();
        manager.addANewDoctor("John", "Mike", "12-May-2000", "0702010059", "DOC12345", "Cosmetic-Dermatology", "Available");
        manager.addNewPatient("John", "Mike", "12-May-2000", "070201005", "P001", "Male");
        manager.addNewSession("S0001", "DOC12345", "12-May-2020", "12:00", 10, "Available");
        Session session = null;
        for (Session s : WestminsterSkinConsultationManager.sessionArrayList) {
            if (s.getSessionId().equals("S0001")) {
                session = s;
            }
        }
        Person patient = null;
        for (Person p : WestminsterSkinConsultationManager.patientArrayList) {
            Patient patient1 = (Patient) p;
            if (patient1.getPatientId().equals("P001")) {
                patient = p;
            }
        }
        assertEquals("Consultation C0001 added successfully.", (manager.addNewConsultation("C0001", session, session.getDoctor(), patient, 2.5, "Skin damage", null)));
    }

    @Test
    void testDeleteAConsultation() throws IOException {
        WestminsterSkinConsultationManager manager = new WestminsterSkinConsultationManager();
        manager.addANewDoctor("John", "Mike", "12-May-2000", "0702010059", "DOC12345", "Cosmetic-Dermatology", "Available");
        manager.addNewPatient("John", "Mike", "12-May-2000", "070201005", "P001", "Male");
        manager.addNewSession("S0001", "DOC12345", "12-May-2020", "12:00", 10, "Available");
        Session session = null;
        for (Session s : WestminsterSkinConsultationManager.sessionArrayList) {
            if (s.getSessionId().equals("S0001")) {
                session = s;
            }
        }
        Person patient = null;
        for (Person p : WestminsterSkinConsultationManager.patientArrayList) {
            Patient patient1 = (Patient) p;
            if (patient1.getPatientId().equals("P001")) {
                patient = p;
            }
        }
        manager.addNewConsultation("C0001", session, session.getDoctor(), patient, 2.5, "Skin damage", null);
        assertEquals("Consultation C0001 deleted successfully.", (manager.deleteAConsultation("C0001")));
    }

    @Test
    void deleteNotAvailableConsultation() {
        WestminsterSkinConsultationManager manager = new WestminsterSkinConsultationManager();
        assertEquals("Consultation not found by Consultation ID: C0002", (manager.deleteAConsultation("C0002")));
    }


    @Test
    void testInvalidStrToDate() {
        assertEquals(null, (WestminsterSkinConsultationManager.strToDate("2000-08-14")));
    }
}