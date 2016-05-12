package jm.music.tools.ga;

import jm.music.data.Phrase;

public class NoTerminationCriteria
  extends TerminationCriteria
{
  public NoTerminationCriteria() {}
  
  public boolean isFinished(Phrase[] paramArrayOfPhrase)
  {
    return false;
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\music\tools\ga\NoTerminationCriteria.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */