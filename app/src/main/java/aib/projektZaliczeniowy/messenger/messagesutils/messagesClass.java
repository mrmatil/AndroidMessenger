package aib.projektZaliczeniowy.messenger.messagesutils;


public class messagesClass {
    private String author;
    private String message;
    private Long date;

    public messagesClass(String author, String message, Long date){
        this.author=author;
        this.message=message;
        this.date=date;
    }

    public String getMessage() {
        return message;
    }

    public String getAuthor() {
        return author;
    }

    public Long getDate() {
        return date;
    }
}
