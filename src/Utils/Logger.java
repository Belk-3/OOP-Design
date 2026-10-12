package Utils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import Users.User;

public class Logger implements Serializable {
    private static final long serialVersionUID = 1L;
    private static Logger instance;
    private String logFile;
    private List<String> logs;

    private Logger() {
        this.logFile = "system.log";
        this.logs = new ArrayList<>();
    }

    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    public void log(String action, User user) {
        String entry = "[LOG] " + action + " by " + (user != null ? user.getUsername() : "system");
        logs.add(entry);
        System.out.println(entry);
    }

    public List<String> getLogs() {
        return logs;
    }

    public String getLogFile() {
        return logFile;
    }
}
