package hospitaladmission;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// This class tests the main hospital system functions.
public class HospitalSystemTest {

    // Test whether a patient can be registered.
    @Test
    public void testRegisterPatient() {

        HospitalSystem hospital =
                new HospitalSystem();

        Patient patient =
                new Patient(
                        "P001",
                        "John",
                        "Smith",
                        25,
                        "Male",
                        "Flu",
                        PatientCategory.OUTPATIENT
                );

        // The patient should be registered successfully.
        assertTrue(
                hospital.registerPatient(patient)
        );

        // There should now be one patient.
        assertEquals(
                1,
                hospital.getPatientCount()
        );
    }

    // Test that duplicate patient IDs are rejected.
    @Test
    public void testDuplicatePatientId() {

        HospitalSystem hospital =
                new HospitalSystem();

        Patient patient1 =
                new Patient(
                        "P001",
                        "John",
                        "Smith",
                        25,
                        "Male",
                        "Flu",
                        PatientCategory.OUTPATIENT
                );

        Patient patient2 =
                new Patient(
                        "P001",
                        "Peter",
                        "Jones",
                        30,
                        "Male",
                        "Cold",
                        PatientCategory.OUTPATIENT
                );

        // First patient should be accepted.
        assertTrue(
                hospital.registerPatient(patient1)
        );

        // Second patient has the same ID and should fail.
        assertFalse(
                hospital.registerPatient(patient2)
        );
    }

    // Test patient searching.
    @Test
    public void testSearchPatient() {

        HospitalSystem hospital =
                new HospitalSystem();

        Patient patient =
                new Patient(
                        "P002",
                        "Mary",
                        "Jones",
                        30,
                        "Female",
                        "Fever",
                        PatientCategory.EMERGENCY
                );

        hospital.registerPatient(patient);

        // Search using the patient ID.
        Patient result =
                hospital.searchPatient("P002");

        // Patient should be found.
        assertNotNull(result);

        assertEquals(
                "Mary",
                result.getFirstName()
        );
    }

    // Test updating patient information.
    @Test
    public void testUpdatePatient() {

        HospitalSystem hospital =
                new HospitalSystem();

        Patient patient =
                new Patient(
                        "P003",
                        "John",
                        "Brown",
                        20,
                        "Male",
                        "Cold",
                        PatientCategory.OUTPATIENT
                );

        hospital.registerPatient(patient);

        // Update the patient's information.
        assertTrue(
                hospital.updatePatient(
                        "P003",
                        "James",
                        "Brown",
                        21,
                        "Male",
                        "Flu"
                )
        );

        // Check that the first name changed.
        assertEquals(
                "James",
                hospital.searchPatient("P003")
                        .getFirstName()
        );
    }

    // Test deleting a patient.
    @Test
    public void testDeletePatient() {

        HospitalSystem hospital =
                new HospitalSystem();

        Patient patient =
                new Patient(
                        "P004",
                        "Sarah",
                        "White",
                        35,
                        "Female",
                        "Fever",
                        PatientCategory.OUTPATIENT
                );

        hospital.registerPatient(patient);

        // Delete the patient.
        assertTrue(
                hospital.deletePatient("P004")
        );

        // Patient should no longer exist.
        assertNull(
                hospital.searchPatient("P004")
        );
    }

    // Test allocating a bed.
    @Test
    public void testAllocateBed() {

        HospitalSystem hospital =
                new HospitalSystem();

        Inpatient patient =
                new Inpatient(
                        "P005",
                        "David",
                        "Mokoena",
                        40,
                        "Male",
                        "Pneumonia",
                        1,
                        "Not allocated"
                );

        hospital.registerPatient(patient);

        // Allocate bed B01.
        assertTrue(
                hospital.allocateBed(
                        "P005",
                        "B01"
                )
        );

        // There should be one occupied bed.
        assertEquals(
                1,
                hospital.getOccupiedBedCount()
        );
    }

    // Test that two patients cannot use the same bed.
    @Test
    public void testCannotAllocateOccupiedBed() {

        HospitalSystem hospital =
                new HospitalSystem();

        Inpatient patient1 =
                new Inpatient(
                        "P006",
                        "Peter",
                        "Smith",
                        40,
                        "Male",
                        "Flu",
                        1,
                        "Not allocated"
                );

        Inpatient patient2 =
                new Inpatient(
                        "P007",
                        "James",
                        "Jones",
                        50,
                        "Male",
                        "Cold",
                        1,
                        "Not allocated"
                );

        hospital.registerPatient(patient1);
        hospital.registerPatient(patient2);

        // First patient gets the bed.
        assertTrue(
                hospital.allocateBed(
                        "P006",
                        "B01"
                )
        );

        // Second patient cannot use the occupied bed.
        assertFalse(
                hospital.allocateBed(
                        "P007",
                        "B01"
                )
        );
    }

    // Test releasing a bed.
    @Test
    public void testReleaseBed() {

        HospitalSystem hospital =
                new HospitalSystem();

        Inpatient patient =
                new Inpatient(
                        "P008",
                        "Mike",
                        "Brown",
                        45,
                        "Male",
                        "Infection",
                        1,
                        "Not allocated"
                );

        hospital.registerPatient(patient);

        // Allocate a bed first.
        hospital.allocateBed(
                "P008",
                "B01"
        );

        // One bed should be occupied.
        assertEquals(
                1,
                hospital.getOccupiedBedCount()
        );

        // Release the bed.
        assertTrue(
                hospital.releaseBed("P008")
        );

        // The bed should now be available.
        assertEquals(
                0,
                hospital.getOccupiedBedCount()
        );
    }
}
