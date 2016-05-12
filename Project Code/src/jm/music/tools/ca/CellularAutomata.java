package jm.music.tools.ca;

import java.io.PrintStream;

public class CellularAutomata
{
  int xSize;
  int ySize;
  boolean wrapAround;
  boolean[][] cellStates;
  int[][] cellSurrounds;
  
  public CellularAutomata(int paramInt1, int paramInt2)
  {
    this(paramInt1, paramInt2, 25, false);
  }
  
  public CellularAutomata(int paramInt1, int paramInt2, int paramInt3)
  {
    this(paramInt1, paramInt2, paramInt3, false);
  }
  
  public CellularAutomata(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
  {
    this.xSize = paramInt1;
    this.ySize = paramInt2;
    this.wrapAround = paramBoolean;
    this.cellStates = new boolean[paramInt1][paramInt2];
    this.cellSurrounds = new int[paramInt1][paramInt2];
    setGrid(paramInt3);
  }
  
  public void evolve()
  {
    boolean[][] arrayOfBoolean = new boolean[this.xSize][this.ySize];
    for (int i = 0; i < this.xSize; i++) {
      for (int j = 0; j < this.ySize; j++) {
        if (getState(i, j))
        {
          if (getSurrounding(i, j) == 2) {
            arrayOfBoolean[i][j] = 1;
          } else {
            arrayOfBoolean[i][j] = 0;
          }
        }
        else if ((!getState(i, j)) && ((getSurrounding(i, j) == 2) || (getSurrounding(i, j) == 3))) {
          arrayOfBoolean[i][j] = 1;
        }
      }
    }
    this.cellStates = arrayOfBoolean;
  }
  
  public boolean getState(int paramInt1, int paramInt2)
  {
    return this.cellStates[paramInt1][paramInt2];
  }
  
  public boolean[][] getAllStates()
  {
    return this.cellStates;
  }
  
  public int getSurrounding(int paramInt1, int paramInt2)
  {
    int i = 0;
    try
    {
      if (this.cellStates[(paramInt1 - 1)][(paramInt2 - 1)] == 1) {
        i++;
      }
    }
    catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException1)
    {
      if (this.wrapAround) {
        if ((paramInt1 - 1 < 0) && (paramInt2 - 1 < 0))
        {
          if (this.cellStates[(this.xSize - 1)][(this.ySize - 1)] == 1) {
            i++;
          }
        }
        else if (paramInt1 - 1 < 0)
        {
          if (this.cellStates[(this.xSize - 1)][(paramInt2 - 1)] == 1) {
            i++;
          }
        }
        else if ((paramInt2 - 1 < 0) && (this.cellStates[(paramInt1 - 1)][(this.ySize - 1)] == 1)) {
          i++;
        }
      }
    }
    try
    {
      if (this.cellStates[paramInt1][(paramInt2 - 1)] == 1) {
        i++;
      }
    }
    catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException2)
    {
      if ((this.wrapAround) && (this.cellStates[paramInt1][(this.ySize - 1)] == 1)) {
        i++;
      }
    }
    try
    {
      if (this.cellStates[(paramInt1 + 1)][(paramInt2 - 1)] == 1) {
        i++;
      }
    }
    catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException3)
    {
      if (this.wrapAround) {
        if ((paramInt1 + 1 >= this.xSize) && (paramInt2 - 1 < 0))
        {
          if (this.cellStates[0][(this.ySize - 1)] == 1) {
            i++;
          }
        }
        else if (paramInt1 + 1 >= this.xSize)
        {
          if (this.cellStates[0][(paramInt2 - 1)] == 1) {
            i++;
          }
        }
        else if ((paramInt2 - 1 < 0) && (this.cellStates[(paramInt1 + 1)][(this.ySize - 1)] == 1)) {
          i++;
        }
      }
    }
    try
    {
      if (this.cellStates[(paramInt1 - 1)][paramInt2] == 1) {
        i++;
      }
    }
    catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException4)
    {
      if ((this.wrapAround) && (this.cellStates[(this.xSize - 1)][paramInt2] == 1)) {
        i++;
      }
    }
    try
    {
      if (this.cellStates[(paramInt1 + 1)][paramInt2] == 1) {
        i++;
      }
    }
    catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException5)
    {
      if ((this.wrapAround) && (this.cellStates[0][paramInt2] == 1)) {
        i++;
      }
    }
    try
    {
      if (this.cellStates[(paramInt1 - 1)][(paramInt2 + 1)] == 1) {
        i++;
      }
    }
    catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException6)
    {
      if (this.wrapAround) {
        if ((paramInt1 - 1 < 0) && (paramInt2 + 1 >= this.ySize))
        {
          if (this.cellStates[(this.xSize - 1)][0] == 1) {
            i++;
          }
        }
        else if (paramInt1 - 1 < 0)
        {
          if (this.cellStates[(this.xSize - 1)][(paramInt2 + 1)] == 1) {
            i++;
          }
        }
        else if ((paramInt2 + 1 >= this.ySize) && (this.cellStates[(paramInt1 - 1)][0] == 1)) {
          i++;
        }
      }
    }
    try
    {
      if (this.cellStates[paramInt1][(paramInt2 + 1)] == 1) {
        i++;
      }
    }
    catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException7)
    {
      if ((this.wrapAround) && (this.cellStates[paramInt1][0] == 1)) {
        i++;
      }
    }
    try
    {
      if (this.cellStates[(paramInt1 + 1)][(paramInt2 + 1)] == 1) {
        i++;
      }
    }
    catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException8)
    {
      if (this.wrapAround) {
        if ((paramInt1 + 1 >= this.xSize) && (paramInt2 + 1 >= this.ySize))
        {
          if (this.cellStates[0][0] == 1) {
            i++;
          }
        }
        else if (paramInt1 + 1 >= this.xSize)
        {
          if (this.cellStates[0][(paramInt2 + 1)] == 1) {
            i++;
          }
        }
        else if ((paramInt2 + 1 >= this.ySize) && (this.cellStates[(paramInt1 + 1)][0] == 1)) {
          i++;
        }
      }
    }
    return i;
  }
  
  public void print()
  {
    for (int i = 0; i < this.xSize; i++)
    {
      for (int j = 0; j < this.ySize; j++) {
        if (getState(i, j) == true) {
          System.out.print("1");
        } else {
          System.out.print("0");
        }
      }
      System.out.println("");
    }
    System.out.println("");
  }
  
  public void setGrid(int paramInt)
  {
    for (int i = 0; i < this.xSize; i++) {
      for (int j = 0; j < this.ySize; j++) {
        this.cellStates[i][j] = trueFalse(paramInt);
      }
    }
  }
  
  private boolean trueFalse(int paramInt)
  {
    int i = (int)(Math.random() * 100.0D);
    boolean bool;
    if (i < paramInt) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\music\tools\ca\CellularAutomata.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */