package jm.audio.math;

public class Factorize
{
  public Factorize() {}
  
  public static int[] factor(int paramInt, int[] paramArrayOfInt)
  {
    int[] arrayOfInt1 = new int[64];
    int i = 0;
    int j = paramInt;
    if (paramInt <= 0) {
      throw new Error("Number (" + paramInt + ") must be positive integer");
    }
    for (int m = 0; (m < paramArrayOfInt.length) && (j != 1); m++)
    {
      k = paramArrayOfInt[m];
      while (j % k == 0)
      {
        j /= k;
        arrayOfInt1[(i++)] = k;
      }
    }
    int k = 2;
    while ((j % k == 0) && (j != 1))
    {
      j /= k;
      arrayOfInt1[(i++)] = k;
    }
    k = 3;
    while (j != 1)
    {
      while (j % k != 0) {
        k += 2;
      }
      j /= k;
      arrayOfInt1[(i++)] = k;
    }
    m = 1;
    for (int n = 0; n < i; n++) {
      m *= arrayOfInt1[n];
    }
    if (m != paramInt) {
      throw new Error("factorization failed for " + paramInt);
    }
    int[] arrayOfInt2 = new int[i];
    System.arraycopy(arrayOfInt1, 0, arrayOfInt2, 0, i);
    return arrayOfInt2;
  }
  
  public static int log2(int paramInt)
  {
    int i = 0;
    int j = 1;
    while (j < paramInt)
    {
      j *= 2;
      i++;
    }
    if (paramInt != 1 << i) {
      return -1;
    }
    return i;
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\audio\math\Factorize.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */