package jm.music.tools.ga;

import java.awt.Panel;

public abstract class GAComponent
{
  protected Panel panel = new Panel();
  protected static String label = "Genetic Algorithm Component";
  
  public GAComponent() {}
  
  public Panel getPanel()
  {
    return this.panel;
  }
  
  public String getLabel()
  {
    return label;
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\music\tools\ga\GAComponent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */