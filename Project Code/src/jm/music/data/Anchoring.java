package jm.music.data;

public class Anchoring
{
  final Phrase anchor;
  final Alignment alignment;
  final double offset;
  
  Anchoring(Phrase paramPhrase, Alignment paramAlignment, double paramDouble)
  {
    this.anchor = paramPhrase;
    this.alignment = paramAlignment;
    this.offset = paramDouble;
  }
  
  public final Phrase getAnchor()
  {
    return this.anchor;
  }
  
  public final Alignment getAlignment()
  {
    return this.alignment;
  }
  
  public final double getOffset()
  {
    return this.offset;
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\music\data\Anchoring.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */