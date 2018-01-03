package com.example.anshul.tpocampus.UserData;

/**
 * Created by ANSHUL KISHORE on 08-10-2017.
 */

public class AddUserProjectData {

    String projects, description, organisation;

    public AddUserProjectData(){}

    public AddUserProjectData(String projects, String description, String organisation){

        this.projects = projects;
        this.description = description;
        this.organisation = organisation;

    }

    public String getProjects(){
        return projects;
    }

    public String getOrganisation(){
        return organisation;
    }

    public String getDescription(){
        return description;
    }

    public void setProjects(String projects){
        this.projects = projects;
    }

    public void setOrganisation(String organisation){
        this.organisation = organisation;
    }

    public void setDescription(String description){
        this.description = description;
    }

}
