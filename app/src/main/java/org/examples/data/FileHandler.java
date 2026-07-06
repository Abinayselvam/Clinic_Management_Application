package org.examples.data;

import org.examples.enums.Shift;
import org.examples.enums.Specialization;
import org.examples.model.Doctor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class FileHandler {
    public static ArrayList<Doctor> bulkLoadDoctors(String filename, int startId)
    {
        ArrayList<Doctor> newDoctors = new ArrayList<>();
        int currentId = startId;
        try(BufferedReader br = new BufferedReader(new FileReader(filename)))
        {
          String line;
          while ((line = br.readLine()) != null)
          {
              if(line.trim().isEmpty())continue;//remove the spaces
            String[] fields = line.split(",");
            if(fields.length == 4)
            {
                String id = String.format("D%04d", currentId++);
                String name = fields[0];
                //Convert String to enum case-sensitive check
                Specialization specialization = Specialization.valueOf( fields[1].trim().toUpperCase());
                int experience = Integer.parseInt(fields[2].trim());
                Shift shift = Shift.valueOf(fields[3].trim().toUpperCase());
                newDoctors.add(new Doctor(id, name, specialization, experience, shift));
            }
          }
        }
        catch (Exception e)
        {
            System.err.println(">>> Bulk upload Error: Check file formate or enum values" + e.getMessage());
        }
        return newDoctors;
    }
}
