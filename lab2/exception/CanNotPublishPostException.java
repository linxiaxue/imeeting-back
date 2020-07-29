package fudan.se.lab2.exception;

public class CanNotPublishPostException extends RuntimeException {
    public CanNotPublishPostException(){super("此文章的讨论贴已经存在");}
}
