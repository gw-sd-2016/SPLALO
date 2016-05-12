package jm.midi;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Enumeration;
import java.util.Vector;
import javax.sound.midi.MidiDevice.Info;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Transmitter;
import jm.midi.event.Event;
import jm.midi.event.VoiceEvt;

public class RTMidiIn
  implements Receiver
{
  private int oldStatus;
  private Vector listeners = new Vector();
  private Transmitter trans = null;
  
  public RTMidiIn()
  {
    init();
  }
  
  public void addMidiInputListener(MidiInputListener paramMidiInputListener)
  {
    this.listeners.add(paramMidiInputListener);
  }
  
  public void notifyListeners(Event paramEvent)
  {
    Enumeration localEnumeration = this.listeners.elements();
    while (localEnumeration.hasMoreElements()) {
      ((MidiInputListener)localEnumeration.nextElement()).newEvent(paramEvent);
    }
  }
  
  public void send(MidiMessage paramMidiMessage, long paramLong)
  {
    System.out.println("New MIDI message");
    Object localObject = null;
    ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(paramMidiMessage.getMessage());
    DataInputStream localDataInputStream = new DataInputStream(localByteArrayInputStream);
    try
    {
      localDataInputStream.mark(2);
      int i = localDataInputStream.readUnsignedByte();
      int j = 0;
      if (i < 128)
      {
        i = this.oldStatus;
        localDataInputStream.reset();
      }
      int k;
      if (i >= 255)
      {
        k = localDataInputStream.readUnsignedByte();
        j = MidiUtil.readVarLength(localDataInputStream);
        localObject = MidiUtil.createMetaEvent(k);
      }
      else if (i >= 240)
      {
        System.out.println("SysEX---");
        j = MidiUtil.readVarLength(localDataInputStream);
      }
      else if (i >= 128)
      {
        k = (short)(i / 16);
        short s = (short)(i - k * 16);
        VoiceEvt localVoiceEvt = (VoiceEvt)MidiUtil.createVoiceEvent(k);
        localVoiceEvt.setMidiChannel(s);
        localObject = localVoiceEvt;
        if (localObject == null) {
          throw new IOException("Read Error");
        }
      }
      if (localObject != null)
      {
        ((Event)localObject).setTime((int)paramLong);
        ((Event)localObject).read(localDataInputStream);
      }
      this.oldStatus = i;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      System.exit(1);
    }
    notifyListeners((Event)localObject);
  }
  
  public void close()
  {
    this.trans.close();
  }
  
  private boolean init()
  {
    if (this.trans == null) {
      try
      {
        if (MidiSystem.getReceiver() == null)
        {
          System.err.println("MidiSystem Receiver Unavailable");
          return false;
        }
        MidiDevice.Info[] arrayOfInfo = MidiSystem.getMidiDeviceInfo();
        for (int i = 0; i < arrayOfInfo.length; i++) {
          System.out.println(arrayOfInfo[i]);
        }
        this.trans = MidiSystem.getTransmitter();
        this.trans.setReceiver(this);
      }
      catch (MidiUnavailableException localMidiUnavailableException)
      {
        System.err.println("Midi System Unavailable:" + localMidiUnavailableException);
        return false;
      }
    }
    return true;
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\midi\RTMidiIn.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */