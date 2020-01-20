package netty.Serializable;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by admin on 2019/10/31.
 */
public class RequestMessage implements Serializable {

    private static final long serialVersionUID = -2760609577319959811L;
    private long id;
    private String message;
    private byte[] attachment;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public byte[] getAttachment() {
        return attachment;
    }

    public void setAttachment(byte[] attachment) {
        this.attachment = attachment;
    }

    @Override
    public String toString() {
        return "RequestMessage{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", attachment=" + Arrays.toString(attachment) +
                '}';
    }
}
