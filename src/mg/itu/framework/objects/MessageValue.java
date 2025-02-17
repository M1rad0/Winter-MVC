package mg.itu.framework.objects;

public class MessageValue {
    String message;
    Object value;

    public void setMessage(String message) {
        this.message = message;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public Object getValue() {
        return value;
    }

    public MessageValue(String message, Object value){
        this.message=message;
        this.value=value;
    }
}
