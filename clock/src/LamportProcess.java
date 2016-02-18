import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Random;

public class LamportProcess implements Runnable {
  private int port;
  private int processId;
  private String processName;
  MulticastSocket socket;
  InetAddress group;
  int localClock = 0;
  
  
  public LamportProcess(int receiverId, int port){
    this.processId = receiverId;
    this.port = port;
    initializeChannel(port);
  }
  
  public void initializeChannel(int port){
    try{
      
      processName = "P"+processId;
      group = InetAddress.getByName("239.1.2.3");
      socket = new MulticastSocket(port);
      socket.joinGroup(group);
      System.out.println("Initialized process - "+processName+" (clock: "+localClock+")");
    }catch(Exception ex){
      ex.printStackTrace();
    }
  }

  @Override
  public void run() {
    try{
      
    while(true){
      try {
      Message messageData = null;
      String incomingMessage;
      String localEventType="Local";
      int remoteClock = 0, eventCount = 0;
      
      incomingMessage = listenChannel();//Listen channel for incoming message
      
      try {
        messageData = new Message(incomingMessage).parse();
      } catch (InvalidMessageException e) {
        e.printStackTrace();
      }
      
      if(isStopSignal(messageData))
        break;
      
      //validate identity
      if(messageData.getEventType().equalsIgnoreCase("RECEIVE") && messageData.getTo().equalsIgnoreCase(Integer.toString(processId))){
        remoteClock = messageData.getSenderClockTime();
        
        if(findMaximumValue(localClock, remoteClock) == remoteClock)
        {
          localEventType="Receive";
          eventCount ++;
          //System.out.println("Receive => "+processName+" : "+localClock+" : "+ remoteClock+" : "+localEventType);
          localClock = remoteClock + 1; //Increment remote clock by 1
        }  
      }else if(messageData.getEventType().equalsIgnoreCase("SEND") && messageData.getFrom().equalsIgnoreCase(Integer.toString(processId))){
        localEventType = "Send";
        //messageData.setEventType("Receive");//Change event type so that infinite looping can be avoided
        //System.out.println("Send => "+processName+" : "+localClock+" : "+localEventType+" : "+messageData.getTo());
        postMessage(framePostMessage("Receive"));
      }
      
      System.out.println(processName+" : "+localClock+" : "+localEventType);
      
      localClock = localClock +1;
      //Reset fields
      localEventType="Local";
      
      }catch(Exception ex){
        ex.printStackTrace();
      }
    } 
    }catch(Exception ex){
      
    }finally {
      
      //close socket connection
      if(socket != null && !socket.isClosed())
        socket.close();
    }
  }
  
  public boolean postMessage(String msg) throws IOException{
    DatagramPacket packet = new DatagramPacket(msg.getBytes(),
        msg.length(), group, port);
    socket.send(packet);
    
    return true;
  }
  
  private String framePostMessage(String event){
    String msg = processId+"-"+getRandom(processId)+"-"+getLocalClock()+"-"+event;
    
    return msg;
  }
  
  /**
   * Generates a random number depicting a process ID
   * @param numProcesses
   * @return
   */
  private int getRandom(int numProcesses) {
    Random rand = new Random();
    return rand.nextInt(numProcesses) + 1;
  }
  
  public int getLocalClock() {
    return localClock;
  }
  
  private int findMaximumValue(int value1, int value2){
    return Math.max(value1, value2);
  }
  
  private boolean isStopSignal(Message message){
    if(message != null && message.getEventType().equalsIgnoreCase("STOP")) {
      System.out.println("------ END of " + processName + " (clock time: "+ localClock + ") ------" );
      return true;
    }
    
    return false;
  }
  
  /**
   * 
   * @return increments local clock, returns the local clock
   */
  public int incrementClock() {
    localClock+=1;
    return localClock;
  }
  
  private String listenChannel(){
    String rawMessage = "";
    try{
    byte[] buf = new byte[100];
    DatagramPacket recv = new DatagramPacket(buf, buf.length);
    
    socket.receive(recv);
    rawMessage = new String(buf);
    
    }catch(Exception ex){
      ex.printStackTrace();
    }
    return rawMessage;
  }
}
