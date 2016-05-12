package jm.midi.event;

public abstract interface VoiceEvt
  extends Event
{
  public abstract short getMidiChannel();
  
  public abstract void setMidiChannel(short paramShort);
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\midi\event\VoiceEvt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */