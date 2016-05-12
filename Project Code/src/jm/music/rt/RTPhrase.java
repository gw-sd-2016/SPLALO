package jm.music.rt;

import jm.JMC;
import jm.audio.Instrument;
import jm.music.data.Note;
import jm.music.data.Phrase;

public class RTPhrase
  extends RTLine
  implements JMC
{
  private Phrase phrase;
  private int noteCounter = 0;
  private boolean waitForStartTime = true;
  private Note aRest = new Note(Integer.MIN_VALUE, 1.0D);
  
  public RTPhrase(Phrase paramPhrase, Instrument paramInstrument)
  {
    this(paramPhrase, new Instrument[] { paramInstrument });
  }
  
  public RTPhrase(Phrase paramPhrase, Instrument[] paramArrayOfInstrument)
  {
    super(paramArrayOfInstrument);
    this.phrase = paramPhrase;
    if (paramPhrase.getTempo() != -1.0D) {
      setTempo(paramPhrase.getTempo());
    }
    if (paramPhrase.getStartTime() == 0.0D) {
      this.waitForStartTime = false;
    }
  }
  
  public synchronized Note getNextNote()
  {
    this.aRest.setRhythmValue(1.0D);
    if (this.waitForStartTime)
    {
      this.waitForStartTime = false;
      this.aRest.setRhythmValue(this.phrase.getStartTime());
      return this.aRest;
    }
    if (this.noteCounter < this.phrase.getSize()) {
      return this.phrase.getNote(this.noteCounter++);
    }
    return this.aRest;
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\music\rt\RTPhrase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */