package com.example.anshul.tpocampus.UserData;

/**
 * Created by ANSHUL KISHORE on 08-10-2017.
 */

public class AddUserAcademicData {

    String curr_cpi, _10th_school, _10th_board, _10th_year, _10th_percentage, _12th_school, _12th_board,
           _12th_year, _12th_percentage;

    public AddUserAcademicData(){}

    public AddUserAcademicData(String curr_cpi, String _10th_school, String _10th_board, String _10th_year, String _10th_percentage,
                               String _12th_school, String _12th_board, String _12th_year, String _12th_percentage){

        this.curr_cpi = curr_cpi;
        this._10th_school = _10th_school;
        this._10th_board = _10th_board;
        this._10th_year = _10th_year;
        this._10th_percentage = _10th_percentage;
        this._12th_school = _12th_school;
        this._12th_board = _12th_board;
        this._12th_year = _12th_year;
        this._12th_percentage = _12th_percentage;

    }

    public String getCurrCpi(){
        return curr_cpi;
    }
    public String get_10th_school(){
        return _10th_school;
    }
    public String get_10th_board(){
        return _10th_board;
    }
    public String get_10th_year(){
        return _10th_year;
    }
    public String get_10th_percentage(){
        return _10th_percentage;
    }
    public String get_12th_school(){
        return _12th_school;
    }
    public String get_12th_board(){
        return _12th_board;
    }
    public String get_12th_year(){
        return _12th_year;
    }
    public String get_12th_percentage(){
        return _12th_percentage;
    }

    public void setCurrCpi(String curr_cpi){
        this.curr_cpi = curr_cpi;
    }
    public void set_10th_school(String _10th_school){
        this._10th_school = _10th_school;
    }
    public void set_10th_board(String _10th_board){
        this._10th_board = _10th_board;
    }
    public void set_10th_year(String _10th_year){
        this._10th_year = _10th_year;
    }
    public void set_10th_percentage(String _10th_percentage){
        this._10th_percentage = _10th_percentage;
    }
    public void set_12th_school(String _12th_school){
        this._12th_school = _12th_school;
    }
    public void set_12th_board(String _12th_board){
        this._12th_board = _12th_board;
    }
    public void set_12th_year(String _12th_year){
        this._12th_year = _12th_year;
    }
    public void set_12th_percentage(String _12th_percentage){
        this._12th_percentage = _12th_percentage;
    }
}
