package fudan.se.lab2.exception;

import fudan.se.lab2.controller.request.HandleInvitation;

public class HasMadeEditException extends RuntimeException {
    public HasMadeEditException(){super("you has already made an edit");};
}
