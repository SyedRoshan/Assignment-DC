
public class Message {
  private String rawMessage;
  private String from;
  private String to;
  private int senderClockTime;
  private String eventType;
  
  public Message(String rawMessage)
  {
    this.rawMessage = rawMessage;
  }
  
  public Message parse() throws InvalidMessageException{
    if(rawMessage == null) {
      throw new InvalidMessageException("Message is null or empty.");
    }
    
    String[] parts = rawMessage.split("-");
    setFrom(parts[0]);
    setTo(parts[1]);
    setSenderClockTime(Integer.parseInt(parts[2]));
    setEventType(parts[3].trim());
    
    return this;
  }
  
  public String getFrom() {
    return from;
  }

  public void setFrom(String from) {
    this.from = from;
  }

  public String getTo() {
    return to;
  }

  public void setTo(String to) {
    this.to = to;
  }

  public String getEventType() {
    return eventType;
  }

  public void setEventType(String event) {
    this.eventType = event;
  }

  public int getSenderClockTime() {
    return senderClockTime;
  }

  public void setSenderClockTime(int senderClockTime) {
    this.senderClockTime = senderClockTime;
  }
}
