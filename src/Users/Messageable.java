package Users;
import java.util.List;

import Utils.Message;

public interface Messageable {
    void sendMessage(Employee to, String msg);
    List<Message> getMessages();
}
