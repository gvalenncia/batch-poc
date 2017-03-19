package com.gvv.batch.model;

/**
 * Created by gvalenncia on 3/18/17.
 */
public class Student {

    private String student_id;
    private String student_name;
    private String student_lastname;

    public Student(){}

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getStudent_lastname() {
        return student_lastname;
    }

    public void setStudent_lastname(String student_lastname) {
        this.student_lastname = student_lastname;
    }
}
