import edu.purdue.cs.cs180.channel.*;
import java.util.LinkedList;

class Server{
  TCPChannel reqChannel;
  TCPChannel respChannel;
  LinkedList<Integer> requesters = new LinkedList<Integer>();
  LinkedList<Integer> responders = new LinkedList<Integer>();
  LinkedList<String> requestersLocation = new LinkedList<String>();
  LinkedList<String> respondersTeam = new LinkedList<String>();
  
  public Server(int reqPort, int recPort) {
    reqChannel = new TCPChannel(reqPort);
    respChannel = new TCPChannel(recPort);
    
    reqChannel.setMessageListener(new MessageListener(){
      public void messageReceived(String message, int clientID) {
        String currentRequestersLocation = message.substring(7, message.length());
        
        try{
        if(responders.size() == 0){
          reqChannel.sendMessage("Searching:", clientID);
          requesters.add(clientID);
          requestersLocation.add(currentRequestersLocation);
          
        }else{
          reqChannel.sendMessage("Assigned:" + respondersTeam.getFirst(), clientID);
          respChannel.sendMessage("Assigned:" + currentRequestersLocation, responders.getFirst());
        }
        }catch(ChannelException e){
          e.printStackTrace();
        }
      }
    });
    
    respChannel.setMessageListener(
                                  new MessageListener(){
      public void messageReceived(String message, int clientID) {
        String currentRespondersTeam = message.substring(8, message.length());
        
        try{
        if(requesters.size() == 0){
          respChannel.sendMessage("Searching:", clientID);
          responders.add(clientID);
          respondersTeam.add(currentRespondersTeam);
        }else{
          reqChannel.sendMessage("Assigned:" + currentRespondersTeam, requesters.getFirst());
          respChannel.sendMessage("Assigned:" + requestersLocation.getFirst(), clientID);
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

