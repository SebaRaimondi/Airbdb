package ar.edu.unlp.info.bd2.model;

public class UsernameException extends Exception {
    public UsernameException() { super("There is an existing user with that username !!!"); }
}
