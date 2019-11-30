package aib.projektZaliczeniowy.messenger.MessagesUtils;

public class MessagesListData {
    private String username;
    private String message;
    private int imageId;

    public MessagesListData(String username, String message, int imageId){
        this.imageId=imageId;
        this.message=message;
        this.username=username;
    }

    public String getUsername() {
        return username;
    }

    public int getImageId() {
        return imageId;
    }

    public String getMessage() {
        return message;
    }
}
