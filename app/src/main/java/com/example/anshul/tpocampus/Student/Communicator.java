package com.example.anshul.tpocampus.Student;

/**
 * Created by ANSHUL KISHORE on 06-10-2017.
 */

public interface Communicator {
    public void personal_to_project(String[] data, int size);
    public void academic_to_project(String[] data, int size);
    public void project_to_project(String[] data, int size);
}
