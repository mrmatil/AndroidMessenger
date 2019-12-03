package aib.projektZaliczeniowy.messenger.messagesutils;

import java.time.LocalDate;
import java.util.Date;

public class messagesClass {
    private String author;
    private String message;
    private Date date;

    public messagesClass(String author, String message, Date date){
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

    public Date getDate() {
        return date;
    }
}
