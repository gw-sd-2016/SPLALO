package jm.audio.synth;

public class SpringPipe
{
  int totalLength = 500;
  double width = 1.0D;
  double pluckAmt = 1.0D;
  private SpringObject[] springObjectArray;
  private MassObject[] massObjectArray;
  
  public SpringPipe(int paramInt, double paramDouble1, double paramDouble2, double paramDouble3)
  {
    SpringObject[] arrayOfSpringObject = new SpringObject[paramInt + 1];
    MassObject[] arrayOfMassObject = new MassObject[paramInt];
    int i = (int)(this.totalLength - paramInt * this.width) / (paramInt + 1);
    for (int j = 0; j < paramInt; j++)
    {
      arrayOfSpringObject[j] = new SpringObject(paramDouble1);
      arrayOfSpringObject[j].setRestingLength(i);
      arrayOfMassObject[j] = new MassObject(paramDouble2, 1.0D + (Math.random() * paramDouble3 - paramDouble3 / 2.0D));
      arrayOfMassObject[j].setYPosition(i * (j + 1.0D) + this.width * j);
    }
    arrayOfSpringObject[paramInt] = new SpringObject();
    arrayOfSpringObject[paramInt].setRestingLength(i);
    arrayOfMassObject[0].setYPosition(arrayOfMassObject[0].getYPosition() - arrayOfMassObject[0].getYPosition() * this.pluckAmt);
    this.springObjectArray = arrayOfSpringObject;
    this.massObjectArray = arrayOfMassObject;
  }
  
  private void updateSpringMassNetwork()
  {
    double[] arrayOfDouble = new double[this.springObjectArray.length];
    arrayOfDouble[0] = this.springObjectArray[0].getCurrentForce(0.0D, this.massObjectArray[0].getYPosition());
    for (int i = 1; i < this.massObjectArray.length; i++) {
      arrayOfDouble[i] = this.springObjectArray[i].getCurrentForce(this.massObjectArray[(i - 1)].getYPosition() + this.width, this.massObjectArray[i].getYPosition());
    }
    arrayOfDouble[(arrayOfDouble.length - 1)] = this.springObjectArray[(arrayOfDouble.length - 1)].getCurrentForce(this.massObjectArray[(this.massObjectArray.length - 1)].getYPosition() + this.width, this.totalLength);
    for (i = 0; i < this.massObjectArray.length; i++) {
      this.massObjectArray[i].setYPosition(this.massObjectArray[i].getYPosition() + this.massObjectArray[i].getDisplacement(arrayOfDouble[i] - arrayOfDouble[(i + 1)]));
    }
  }
  
  public double getNextNodePosition(int paramInt)
  {
    updateSpringMassNetwork();
    return this.massObjectArray[paramInt].getYPosition();
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\audio\synth\SpringPipe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */