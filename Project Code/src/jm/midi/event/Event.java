package jm.midi.event;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract interface Event
{
  public abstract int getTime();
  
  public abstract void setTime(int paramInt);
  
  public abstract short getID();
  
  public abstract Event copy()
    throws CloneNotSupportedException;
  
  public abstract void print();
  
  public abstract int write(DataOutputStream paramDataOutputStream)
    throws IOException;
  
  public abstract int read(DataInputStream paramDataInputStream)
    throws IOException;
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\midi\event\Event.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */