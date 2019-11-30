package aib.projektZaliczeniowy.messenger.messagesutils;

public class messagesClass {
    private String author;
    private String message;

    public messagesClass(String author, String message){
        this.author=author;
        this.message=message;
    }

    public String getMessage() {
        return message;
    }

    public String getAuthor() {
        return author;
    }
}
