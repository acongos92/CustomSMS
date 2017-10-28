package Backend;

import java.util.ArrayList;

/**
 * Created by zigza on 10/28/2017.
 */

public class MessageSender {
    private ArrayList<String> sender;
    private ArrayList<String> message;
    private int size;

    public MessageSender(){
        sender = new ArrayList<String>();
        message = new ArrayList<String>();
        this.size = 0;

    }
    public void addNewMessage(String name, String body) {
        this.size++;
        sender.add(name);
        message.add(body);
    }
    public String getSender(int i){
        return sender.get(i);
    }
    public String getMessage(int i){
        return message.get(i);
    }
    public int size(){
        return this.size;
    }
}
