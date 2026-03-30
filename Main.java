import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        List<Patient> patients = new ArrayList<>();
        patients.add(new Patient("George", 19, 3000));
        patients.add(new Patient("Shem", 20, 4500));
        patients.add(new Patient("Lily", 25, 2000));

        Map<String, Doctor> doctors = new HashMap<>();
        doctors.put("cardio", new Doctor("Dr. Oduor", 55, "Cardiology"));
        doctors.put("neuro", new Doctor("Dr. Kim", 42, "Neurology"));

        Set<String> memberNames = new HashSet<>();
        for (Patient p : patients) {
            memberNames.add(p.getName());
        }
        for (Doctor d : doctors.values()) {
            memberNames.add(d.getName());
        }

        System.out.println("=== HOSPITAL SYSTEM ===");
        System.out.println("Members: " + memberNames);

        patients.get(0).assignTreatment("Checkup");
        patients.get(1).assignTreatment("X-Ray");
        doctors.get("cardio").assignTreatment("Heart Surgery");

        Path saveFile = Path.of("hospital-data.txt");
        try {
            saveHospitalData(saveFile, patients, doctors);
            System.out.println("Saved hospital data to " + saveFile.toAbsolutePath());
        } catch (IOException e) {
            System.err.println("Failed to save data: " + e.getMessage());
        }

        try {
            LoadedHospital loaded = loadHospitalData(saveFile);
            System.out.println("Loaded " + loaded.patients.size() + " patients and " + loaded.doctors.size() + " doctors from file");

            for (Patient p : loaded.patients) {
                System.out.println(p.getName() + " (" + p.getRole() + ") bill=$" + p.billAmount());
            }
            for (Doctor d : loaded.doctors.values()) {
                System.out.println(d.getName() + " (" + d.getRole() + ") dept=" + d.getDepartment());
            }
        } catch (IOException e) {
            System.err.println("Failed to load data: " + e.getMessage());
        }
    }

    private static void saveHospitalData(Path path, List<Patient> patients, Map<String, Doctor> doctors) throws IOException {
        List<String> lines = new ArrayList<>();
        lines.add("PATIENTS");
        for (Patient p : patients) {
            lines.add(p.toCSV());
        }
        lines.add("DOCTORS");
        for (Doctor d : doctors.values()) {
            lines.add(d.toCSV());
        }

        Files.write(path, lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    private static LoadedHospital loadHospitalData(Path path) throws IOException {
        List<String> lines = Files.readAllLines(path);
        List<Patient> patients = new ArrayList<>();
        Map<String, Doctor> doctors = new HashMap<>();

        boolean readingPatients = false;
        boolean readingDoctors = false;

        for (String line : lines) {
            if (line.isBlank()) continue;
            if (line.equals("PATIENTS")) {
                readingPatients = true;
                readingDoctors = false;
                continue;
            }
            if (line.equals("DOCTORS")) {
                readingPatients = false;
                readingDoctors = true;
                continue;
            }
            if (readingPatients) {
                patients.add(Patient.fromCSV(line));
            } else if (readingDoctors) {
                Doctor d = Doctor.fromCSV(line);
                doctors.put(d.getName().toLowerCase().replace(" ", ""), d);
            }
        }

        return new LoadedHospital(patients, doctors);
    }

    private static class LoadedHospital {
        List<Patient> patients;
        Map<String, Doctor> doctors;

        LoadedHospital(List<Patient> patients, Map<String, Doctor> doctors) {
            this.patients = patients;
            this.doctors = doctors;
        }
    }
}
