package dev.twozer00.projectm.model;

import java.io.Serializable;

import static dev.twozer00.projectm.utils.Constants.BASE_URL_IMG;

public class PersonCredit extends Person implements Serializable {
    private String known_for_department;
    private String original_name;

    public PersonCredit(String known_for_department, String name, float id, String profile_path) {
        this.known_for_department = known_for_department;
        this.original_name = original_name;
    }

    public PersonCredit() {
    }

    public String getKnown_for_department() {
        return known_for_department;
    }

    public void setKnown_for_department(String known_for_department) {
        this.known_for_department = known_for_department;
    }
    public String getOriginal_name() {
        return original_name;
    }

    public void setOriginal_name(String original_name) {
        this.original_name = original_name;
    }


}
