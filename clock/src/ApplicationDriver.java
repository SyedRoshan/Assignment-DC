import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Random;

public class ApplicationDriver {
  private static final int DEFAULT_PROCESS_COUNT = 3;
  private static final int DEFAULT_EVENT_COUNT = 5;
  private static final int port = 3456;
  
  MulticastSocket socket;
  InetAddress group;
  LamportProcess[] receivers;
  
  public static void main(String[] args){
    //setProcessNEventCount(args);
    
    ApplicationDriver messageSender = new ApplicationDriver();
    messageSender.initializeMulticastGroup();
    messageSender.initializeReceivers(DEFAULT_PROCESS_COUNT);
    messageSender.sendRandomMessages(DEFAULT_PROCESS_COUNT, DEFAULT_EVENT_COUNT);
  }  
  
  /**
   * This method sets the number of processes and events, from the arguments passed to the program
   * @param args
   */
  private static void setProcessNEventCount(String[] args) {
    int iProcessCount = DEFAULT_PROCESS_COUNT;
    int iEventCount = DEFAULT_EVENT_COUNT;
    //Configure process count; with 3 as minimum
    if(null != args && args.length != 0) {
      try {
        String strProcessCount = args[0];
        iProcessCount = Integer.parseInt(strProcessCount);
        if(iProcessCount < 2 || iProcessCount > 10) {
          iProcessCount = DEFAULT_PROCESS_COUNT;
          System.out.println("Setting default process count: " + iProcessCount);
        }
      } catch (Exception ex) {
        iProcessCount = DEFAULT_PROCESS_COUNT;
      }
    }
    
  //Configure process count; with 3 as minimum
    if(null != args && args.length == 2) {
      try {
        String strEventCount = args[1];
        iEventCount = Integer.parseInt(strEventCount);
        if(iEventCount < 2 || iEventCount > 100) {
          iEventCount = DEFAULT_EVENT_COUNT;
          System.out.println("Setting default event count: " + iEventCount);
        }
      } catch (Exception ex) {
        iEventCount = DEFAULT_EVENT_COUNT;
      }
    } 
  }

  /**
   * Initializes the multicast socket
   */
  private void initializeMulticastGroup() {
    try {
      group = InetAddress.getByName("239.1.2.3");
      socket = new MulticastSocket(ApplicationDriver.port);
      socket.setTimeToLive(1);
    } catch (Exception ex) {
      ex.printStackTrace();
    } // end catch
    
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
  
  /**
   * This will send random events, including local events
   * @param numProcesses
   * @param numEvents
   */
  private void sendRandomMessages(int numProcesses, int numEvents) {
    for (int i=1; i<=numEvents; i++) {
      try {
        int randSender = getRandom(numProcesses);
        int randReceiver = getRandom(numProcesses);
        
        while(randSender == randReceiver){
          randReceiver = getRandom(numProcesses);
        }
        
        if(randSender != randReceiver) {
          receivers[randSender-1].incrementClock();
        }
        int iClockSender = receivers[randSender-1].getLocalClock();
        
        String msg = randSender + "-" + randReceiver + "-" + iClockSender + "-" + ((i%2==0)?"Send" :"Receive");
        System.out.println("Sending message (sender-receiver-senderClock-eventType): " + msg);
        DatagramPacket packet = new DatagramPacket(msg.getBytes(),
            msg.length(), group, ApplicationDriver.port);
        socket.send(packet);
        Thread.sleep(3000);
      } catch (Exception ex) {
        ex.printStackTrace();
      } // end catch      
    }
    
    sendTerminationMessage();
    
    socket.close();
  }
  
  
  /**
   * Initializes the process, which will participate in distributed computing
   */
  private void initializeReceivers(int numProcesses) {
    receivers = new LamportProcess[numProcesses];
    Thread[] receiverThreads = new Thread[numProcesses];
    for (int iterator=0; iterator<numProcesses; iterator++) {
      receivers[iterator] = new LamportProcess((iterator+1), ApplicationDriver.port);
      receiverThreads[iterator] = new Thread(receivers[iterator]);
      receiverThreads[iterator].start();
    }
  }
  
  /**
   * This sends termination message to all the processes
   */
  private void sendTerminationMessage() {
    try {
      String msg = "0-0-0-STOP";
      DatagramPacket packet = new DatagramPacket(msg.getBytes(),
          msg.length(), group, ApplicationDriver.port);
      socket.send(packet);
      Thread.sleep(3000);
    } catch (Exception ex) {
      ex.printStackTrace();
    } // end catch      
  }

}
