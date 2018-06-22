package ar.edu.unlp.info.bd2.model;

public class CityNameException extends Exception{
    public CityNameException() { super("There is an existing city with that name !!!"); }
}
