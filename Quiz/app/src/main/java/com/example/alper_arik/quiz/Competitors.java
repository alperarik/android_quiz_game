package com.example.alper_arik.quiz;

public class Competitors  {
    //Variables
    private int _id;
    private String _name;
    private String _surname;
    private String _birthday;
    private String _username;
    private String _password;
    private int _score;

    //Constructor
    public Competitors( ) {
    }

    public Competitors(String _name, String _surname, String _birthday, String _username, String _password, int _score) {
        this._id = _id;
        this._name = _name;
        this._surname = _surname;
        this._birthday = _birthday;
        this._username = _username;
        this._password = _password;
        this._score = _score;
    }

    //Getter Setters
    public void set_id(int _id){
        this._id = _id;
    }

    public int get_id() {
        return _id;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_surname() {
        return _surname;
    }

    public void set_surname(String _surname) {
        this._surname = _surname;
    }

    public String get_birthday() {
        return _birthday;
    }

    public void set_birthday(String _birthday) {
        this._birthday = _birthday;
    }

    public String get_username() {
        return _username;
    }

    public void set_username(String _username) {
        this._username = _username;
    }

    public String get_password() {
        return _password;
    }

    public void set_password(String _password) {
        this._password = _password;
    }

    public int get_score() {
        return _score;
    }

    public void set_score(int _score) {
        this._score = _score;
    }
}

