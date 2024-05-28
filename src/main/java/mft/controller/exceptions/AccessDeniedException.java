package mft.controller.exceptions;

public class AccessDeniedException extends Exception {
    public AccessDeniedException() {
        super("Wrong Username/Password !!!");
    }
}
