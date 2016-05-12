package jm.midi;

import java.io.PrintStream;
import java.util.Enumeration;
import java.util.Vector;
import jm.midi.event.Event;

public class Track
{
  private Vector eventList = new Vector();
  
  public Track() {}
  
  public void addEvent(Event paramEvent)
  {
    this.eventList.addElement(paramEvent);
  }
  
  public Vector getEvtList()
  {
    return this.eventList;
  }
  
  public void print()
  {
    System.out.println("------------------");
    System.out.println("Track");
    Enumeration localEnumeration = this.eventList.elements();
    while (localEnumeration.hasMoreElements()) {
      Event localEvent = (Event)localEnumeration.nextElement();
    }
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\midi\Track.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */