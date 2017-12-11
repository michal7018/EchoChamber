import java.io.IOException;
 
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;


@ServerEndpoint(value = "/echo/{roomID}/{login}")
public class EchoServer {

    @OnOpen
    public void onOpen(Session session, @PathParam("roomID") String roomID, @PathParam("login") String login){
        System.out.println(session.getId() + " has opened a connection"); 
        try {
            session.getBasicRemote().sendText("Connection Established");
            session.getUserProperties().put("roomID",roomID);
            session.getUserProperties().put("login",login);
            SessionHandler.addSession(session);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
 
    @OnMessage
    public void onMessage(String message, Session session, @PathParam("roomID") String roomID, @PathParam("login") String login){
        try {
            SessionHandler.sendToAllConnectedInRoom(roomID, message, login);  
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    @OnClose
    public void onClose(Session session){
        System.out.println("Session " + session.getId() + " has ended");
    }
}