package jm.music.tools.fuzzy;

import java.util.Enumeration;
import java.util.Vector;

public class FuzzySet
{
  private Vector numberList = new Vector();
  private double productSum;
  private double memberSum;
  
  public FuzzySet() {}
  
  public void add(FuzzyNumber paramFuzzyNumber)
  {
    this.numberList.addElement(paramFuzzyNumber);
  }
  
  public void remove(FuzzyNumber paramFuzzyNumber)
  {
    this.numberList.removeElement(paramFuzzyNumber);
  }
  
  public double getOutput(double paramDouble)
  {
    this.productSum = 0.0D;
    this.memberSum = 0.0D;
    Enumeration localEnumeration = this.numberList.elements();
    while (localEnumeration.hasMoreElements())
    {
      FuzzyNumber localFuzzyNumber = (FuzzyNumber)localEnumeration.nextElement();
      this.productSum += localFuzzyNumber.getPeak() * localFuzzyNumber.getMembership(paramDouble);
      this.memberSum += localFuzzyNumber.getMembership(paramDouble);
    }
    return this.productSum / this.memberSum;
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\music\tools\fuzzy\FuzzySet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */