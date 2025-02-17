package mg.itu.framework.utils.errors;

import java.util.ArrayList;
import java.util.HashMap;

import mg.itu.framework.objects.MessageValue;

public class ValidationException extends Exception{
    HashMap<String,ArrayList<MessageValue>> messages;

    public ValidationException(HashMap<String,ArrayList<MessageValue>> messages){
        super("Erreur de validation. Redirection");

        this.messages=messages;
    }

    public HashMap<String, ArrayList<MessageValue>> getMessages() {
        return messages;
    }

    public void setMessages(HashMap<String, ArrayList<MessageValue>> messages) {
        this.messages = messages;
    }
}
