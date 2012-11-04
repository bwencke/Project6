import edu.purdue.cs.cs180.channel.*;
import java.util.LinkedList;

class Server{
  TCPChannel reqChannel;
  TCPChannel respChannel;
  LinkedList<Integer> requesters = new LinkedList<Integer>();
  LinkedList<Integer> responders = new LinkedList<Integer>();
  
  public Server(int reqPort, int recPort) {
    reqChannel = new TCPChannel(reqPort);
    respChannel = new TCPChannel(recPort);
    
    reqChannel.setMessageListener(new MessageListener(){
      public void messageReceived(String message, int clientID) {
        try{
        if(responders.size() == 0){
          reqChannel.sendMessage("Searching", clientID);
          requesters.add(clientID);
          
        }else{
          reqChannel.sendMessage("Someone is on the way!", clientID);
          respChannel.sendMessage("Go to ...", responders.getFirst());
        }
        }catch(ChannelException e){
          e.printStackTrace();
        }
      }
    });
    
    respChannel.setMessageListener(
                                  new MessageListener(){
      public void messageReceived(String message, int clientID) {
        try{
        if(requesters.size() == 0){
          respChannel.sendMessage("Searching", clientID);
          responders.add(clientID);
          
        }else{
          reqChannel.sendMessage("Someone is on the way!", clientID);
          respChannel.sendMessage("Go to ...", responders.getFirst());
        }
        }catch(ChannelException e){
          e.printStackTrace();
        }
      }
    });
  }
  
  public static void main(String[] args){
    Server server = new Server(8888,9999);
    
  }
}

