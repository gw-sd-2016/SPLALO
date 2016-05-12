package jm.util;

import jm.audio.Instrument;
import jm.music.data.Note;
import jm.music.rt.RTLine;

public class AudioRTLine
  extends RTLine
{
  private boolean firstTime = true;
  
  public AudioRTLine(String paramString)
  {
    super(new Instrument[] { new AudioSampleInst(paramString) });
  }
  
  public synchronized Note getNextNote()
  {
    Note localNote;
    if (this.firstTime)
    {
      localNote = new Note(67, 1.0D);
      this.firstTime = false;
    }
    else
    {
      localNote = new Note(Integer.MIN_VALUE, 1.0D);
    }
    return localNote;
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\util\AudioRTLine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */