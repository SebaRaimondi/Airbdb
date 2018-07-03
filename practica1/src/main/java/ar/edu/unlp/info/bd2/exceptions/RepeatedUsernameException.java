package ar.edu.unlp.info.bd2.exceptions;

public class RepeatedUsernameException extends Exception{
    public RepeatedUsernameException() { super("There is an existing user with that username !!!"); }

}
