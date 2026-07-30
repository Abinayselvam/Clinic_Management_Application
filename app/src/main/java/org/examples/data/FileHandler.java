package org.examples.data;

import org.examples.enums.Shift;
import org.examples.enums.Specification;
import org.examples.model.Doctor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    /**
     * Parses a CSV file of {@code name,specialization,experience,shift}
     * rows into {@link Doctor} objects. IDs are intentionally left null
     * here - the caller is responsible for assigning real, DB-generated
     * IDs (e.g. via {@code DoctorRepository.generateDoctorId()}) before
     * persisting, so bulk imports stay consistent with individually
     * registered doctors.
     */
    public static List<Doctor> bulkLoadDoctors(String filename) {

        List<Doctor> parsedDoctors = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {

            String line;

            while ((line = br.readLine()) != null) {

                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] fields = line.split(",");

                if (fields.length != 4) {
                    System.err.println(">>> Skipping malformed row: " + line);
                    continue;
                }

                String name = fields[0].trim();
                Specification specialization = Specification.valueOf(fields[1].trim().toUpperCase());
                int experience = Integer.parseInt(fields[2].trim());
                Shift shift = Shift.valueOf(fields[3].trim().toUpperCase());

                parsedDoctors.add(new Doctor(null, name, specialization, experience, shift));
            }

        } catch (Exception e) {
            System.err.println(">>> Bulk upload error: check file format or enum values - " + e.getMessage());
        }

        return parsedDoctors;
    }
}
