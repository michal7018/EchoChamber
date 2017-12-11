import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

public class SessionHandler {
    private static final Set<Session> sessions = new HashSet<>();
    
    public static void addSession(Session session) {
        sessions.add(session);
    }
    
    public static void sendToSession(Session session, String message, String login) throws IOException {
        session.getBasicRemote().sendText("to me:" + message);
    }
    
    
    public static void sendToAllConnectedInRoom(String roomID, String message, String login) throws IOException {
        Iterator<Session> it=sessions.iterator();
        Session session;
        while(it.hasNext()) {
            session = it.next();
            if(session.isOpen() && session.getUserProperties().get("roomID").equals(roomID)){
                session.getBasicRemote().sendText(login + ":" + message);
            }               
        }
    }
}
