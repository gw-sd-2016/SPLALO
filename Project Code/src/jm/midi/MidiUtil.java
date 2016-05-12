package jm.midi;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Vector;
import jm.JMC;
import jm.midi.event.ATouch;
import jm.midi.event.CChange;
import jm.midi.event.CPres;
import jm.midi.event.EndTrack;
import jm.midi.event.Event;
import jm.midi.event.KeySig;
import jm.midi.event.NoteOff;
import jm.midi.event.NoteOn;
import jm.midi.event.PChange;
import jm.midi.event.PWheel;
import jm.midi.event.TempoEvent;
import jm.midi.event.TimeSig;

public final class MidiUtil
  implements JMC
{
  private static final boolean VERBOSE = false;
  
  public MidiUtil() {}
  
  public static int readVarLength(DataInputStream paramDataInputStream)
    throws IOException
  {
    int i = (short)paramDataInputStream.readUnsignedByte();
    int j = i;
    if ((i & 0x80) != 0)
    {
      j &= 0x7F;
      do
      {
        i = (short)paramDataInputStream.readUnsignedByte();
        j = (j << 7) + (i & 0x7F);
      } while ((i & 0x80) != 0);
    }
    return j;
  }
  
  public static int writeVarLength(int paramInt, DataOutputStream paramDataOutputStream)
    throws IOException
  {
    int i = 0;
    for (long l = paramInt & 0x7F; paramInt >>= 7 > 0; l |= paramInt & 0x7F | 0x80) {
      l <<= 8;
    }
    for (;;)
    {
      paramDataOutputStream.writeByte((byte)(int)l);
      i++;
      if ((l & 0x80) == 0L) {
        break;
      }
      l >>= 8;
    }
    return i;
  }
  
  public static int varLengthBytes(int paramInt)
  {
    int i = 0;
    for (long l = paramInt & 0x7F; paramInt >>= 7 > 0; l |= paramInt & 0x7F | 0x80) {
      l <<= 8;
    }
    for (;;)
    {
      i++;
      if ((l & 0x80) == 0L) {
        break;
      }
      l >>= 8;
    }
    return i;
  }
  
  public static double getEndEvt(short paramShort, Vector paramVector, int paramInt)
  {
    double d = 0.0D;
    paramInt++;
    while (paramInt < paramVector.size())
    {
      Event localEvent = (Event)paramVector.elementAt(paramInt);
      d += localEvent.getTime();
      switch (localEvent.getID())
      {
      case 5: 
        NoteOn localNoteOn = (NoteOn)localEvent;
        if ((localNoteOn.getPitch() == paramShort) && (localNoteOn.getVelocity() == 0) && (d > 0.0D))
        {
          localNoteOn.setPitch((short)255);
          return d;
        }
        break;
      case 4: 
        NoteOff localNoteOff = (NoteOff)localEvent;
        if ((localNoteOff.getPitch() == paramShort) && (d > 0.0D))
        {
          localNoteOff.setPitch((short)255);
          return d;
        }
        break;
      }
      paramInt++;
    }
    System.out.println("Error reading file - sorry!");
    System.out.println("Try to continue reading anyway");
    return 0.0D;
  }
  
  public static Event createVoiceEvent(int paramInt)
    throws IOException
  {
    switch (paramInt)
    {
    case 8: 
      return new NoteOff();
    case 9: 
      return new NoteOn();
    case 10: 
      return new ATouch();
    case 11: 
      return new CChange();
    case 12: 
      return new PChange();
    case 13: 
      return new CPres();
    case 14: 
      return new PWheel();
    }
    return null;
  }
  
  public static Event createMetaEvent(int paramInt)
    throws IOException
  {
    switch (paramInt)
    {
    case 81: 
      return new TempoEvent();
    case 47: 
      return new EndTrack();
    case 88: 
      return new TimeSig();
    case 89: 
      return new KeySig();
    }
    return null;
  }
  
  public static Event createSysExEvent(int paramInt)
    throws IOException
  {
    switch (paramInt)
    {
    }
    return null;
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\midi\MidiUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */