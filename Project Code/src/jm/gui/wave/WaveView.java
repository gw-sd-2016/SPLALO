package jm.gui.wave;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.Panel;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.PrintStream;
import jm.audio.RTMixer;
import jm.music.rt.RTLine;
import jm.util.AudioRTLine;

public class WaveView
  implements ActionListener, ComponentListener
{
  private String lastFileName = "Drunk.au";
  private String lastDirectory = "";
  private WaveFileReader afr;
  private int width = 600;
  private int channelHeight = 200;
  private int amplitude = 1;
  private int channels;
  private float[] data;
  private int resolution = 256;
  private int segmentSize;
  private int startPos = 0;
  private MenuItem size1;
  private MenuItem size2;
  private MenuItem size4;
  private MenuItem size8;
  private MenuItem size16;
  private MenuItem size32;
  private MenuItem size64;
  private MenuItem size128;
  private MenuItem size256;
  private MenuItem size512;
  private MenuItem size1024;
  private MenuItem size2048;
  private MenuItem size4096;
  private MenuItem openFile;
  private MenuItem quit;
  private MenuItem changeColor;
  private MenuItem vSmall;
  private MenuItem small;
  private MenuItem medium;
  private MenuItem large;
  private MenuItem huge;
  private MenuItem times1;
  private MenuItem times2;
  private MenuItem times3;
  private MenuItem times4;
  private Frame f = new Frame();
  private WaveCanvas[] canvases = new WaveCanvas[8];
  private ScrollPane sp = new ScrollPane(2);
  private WaveScrollPanel scrollPanel = new WaveScrollPanel();
  private boolean whiteColor = true;
  private RTMixer mixer;
  private int lastStartPos = -1;
  
  public WaveView()
  {
    openFile();
    drawWave(0, 0);
  }
  
  public WaveView(String paramString)
  {
    this(paramString, 0, 0);
  }
  
  public WaveView(String paramString, int paramInt1, int paramInt2)
  {
    this.lastFileName = paramString;
    this.afr = new WaveFileReader(paramString);
    init();
    drawWave(paramInt1, paramInt2);
    this.f.setTitle("jMusic Wave Viewer: " + this.lastFileName);
  }
  
  public void openFile()
  {
    FileDialog localFileDialog = new FileDialog(this.f, "Select a 16 bit audio file in .au format (no compression).", 0);
    localFileDialog.setDirectory(this.lastDirectory);
    localFileDialog.setFile(this.lastFileName);
    localFileDialog.show();
    String str = localFileDialog.getFile();
    if (str != null)
    {
      this.lastFileName = str;
      this.lastDirectory = localFileDialog.getDirectory();
      this.afr = new WaveFileReader(this.lastDirectory + str);
      updateScrollInfo();
      init();
      setupPanel();
      this.lastFileName = str;
      this.f.setTitle("jMusic Wave Viewer: " + this.lastFileName);
      if (!this.whiteColor)
      {
        changeColor();
        this.whiteColor = (!this.whiteColor);
      }
      if (this.channels <= 2) {
        setHeight(200);
      }
      if (this.channels > 2) {
        setHeight(100);
      }
      if (this.channels > 4) {
        setHeight(50);
      }
      this.f.repaint();
    }
  }
  
  private void init()
  {
    this.channels = this.afr.getChannels();
    if (this.channels > 8)
    {
      System.out.println("jMusic wave viewer error: Files with more than 8 channels are not supported :(");
      System.exit(1);
    }
    this.segmentSize = (this.width * this.resolution * this.channels);
    this.data = this.afr.getSamples(this.segmentSize, this.startPos);
    setupChannels();
    if (this.channels <= 2) {
      setHeight(200);
    }
    if (this.channels > 2) {
      setHeight(100);
    }
    if (this.channels > 4) {
      setHeight(50);
    }
    this.scrollPanel.setScrollbarAttributes(this.afr.getWaveSize(), this.width, this.resolution);
  }
  
  private void drawWave(int paramInt1, int paramInt2)
  {
    this.f.setName("jMusic Wave Viewer: " + this.lastFileName);
    this.f.setLocation(paramInt1, paramInt2);
    this.f.setLayout(new BorderLayout());
    this.sp.setSize(new Dimension(this.width, (this.channelHeight + 1) * this.channels));
    setupPanel();
    this.f.add(this.sp, "Center");
    this.scrollPanel.setViewer(this);
    updateScrollInfo();
    this.scrollPanel.setScrollbarAttributes(this.afr.getWaveSize(), this.width, this.resolution);
    this.f.add(this.scrollPanel, "South");
    MenuBar localMenuBar = new MenuBar();
    Menu localMenu1 = new Menu("Wave", true);
    Menu localMenu2 = new Menu("Height", true);
    Menu localMenu3 = new Menu("Resolution", true);
    Menu localMenu4 = new Menu("Amplitude", true);
    this.size1 = new MenuItem("1:1");
    this.size1.addActionListener(this);
    localMenu3.add(this.size1);
    this.size2 = new MenuItem("1:2");
    this.size2.addActionListener(this);
    localMenu3.add(this.size2);
    this.size4 = new MenuItem("1:4");
    this.size4.addActionListener(this);
    localMenu3.add(this.size4);
    this.size8 = new MenuItem("1:8");
    this.size8.addActionListener(this);
    localMenu3.add(this.size8);
    this.size16 = new MenuItem("1:16");
    this.size16.addActionListener(this);
    localMenu3.add(this.size16);
    this.size32 = new MenuItem("1:32");
    this.size32.addActionListener(this);
    localMenu3.add(this.size32);
    this.size64 = new MenuItem("1:64");
    this.size64.addActionListener(this);
    localMenu3.add(this.size64);
    this.size128 = new MenuItem("1:128");
    this.size128.addActionListener(this);
    localMenu3.add(this.size128);
    this.size256 = new MenuItem("1:256");
    this.size256.addActionListener(this);
    localMenu3.add(this.size256);
    this.size512 = new MenuItem("1:512");
    this.size512.addActionListener(this);
    localMenu3.add(this.size512);
    this.size1024 = new MenuItem("1:1024");
    this.size1024.addActionListener(this);
    localMenu3.add(this.size1024);
    this.size2048 = new MenuItem("1:2048");
    this.size2048.addActionListener(this);
    localMenu3.add(this.size2048);
    this.openFile = new MenuItem("Open...", new MenuShortcut(79));
    this.openFile.addActionListener(this);
    localMenu1.add(this.openFile);
    this.changeColor = new MenuItem("Change Color", new MenuShortcut(67));
    this.changeColor.addActionListener(this);
    localMenu1.add(this.changeColor);
    this.quit = new MenuItem("Quit", new MenuShortcut(81));
    this.quit.addActionListener(this);
    localMenu1.add(this.quit);
    this.vSmall = new MenuItem("X Small");
    this.vSmall.addActionListener(this);
    localMenu2.add(this.vSmall);
    this.small = new MenuItem("Small");
    this.small.addActionListener(this);
    localMenu2.add(this.small);
    this.medium = new MenuItem("Medium");
    this.medium.addActionListener(this);
    localMenu2.add(this.medium);
    this.large = new MenuItem("Large");
    this.large.addActionListener(this);
    localMenu2.add(this.large);
    this.huge = new MenuItem("X Large");
    this.huge.addActionListener(this);
    localMenu2.add(this.huge);
    this.times1 = new MenuItem("x1");
    this.times1.addActionListener(this);
    localMenu4.add(this.times1);
    this.times2 = new MenuItem("x2");
    this.times2.addActionListener(this);
    localMenu4.add(this.times2);
    this.times3 = new MenuItem("x3");
    this.times3.addActionListener(this);
    localMenu4.add(this.times3);
    this.times4 = new MenuItem("x4");
    this.times4.addActionListener(this);
    localMenu4.add(this.times4);
    localMenuBar.add(localMenu1);
    localMenuBar.add(localMenu2);
    localMenuBar.add(localMenu3);
    localMenuBar.add(localMenu4);
    this.f.setMenuBar(localMenuBar);
    this.sp.setSize(new Dimension(this.width, (this.channelHeight + 1) * this.channels));
    this.f.pack();
    this.width = this.f.getSize().width;
    this.f.setVisible(true);
    this.f.addComponentListener(this);
  }
  
  private void updateScrollInfo()
  {
    this.scrollPanel.setFileName(this.lastFileName);
    this.scrollPanel.setBitSize(this.afr.getBitResolution());
    this.scrollPanel.setSampleRate(this.afr.getSampleRate());
    this.scrollPanel.setChannels(this.afr.getChannels());
    this.scrollPanel.getWaveRuler().setMarkerWidth(this.afr.getSampleRate() / this.resolution);
    this.scrollPanel.setScrollbarValue(this.startPos);
    this.scrollPanel.setScrollbarResolution(this.resolution);
  }
  
  private void setupChannels()
  {
    this.channels = this.afr.getChannels();
    float[][] arrayOfFloat = new float[this.channels][this.segmentSize / this.channels];
    int i = 0;
    int j = 0;
    while (j < this.segmentSize)
    {
      for (int k = 0; k < this.channels; k++) {
        arrayOfFloat[k][i] = this.data[(j + k)];
      }
      i++;
      j += this.channels;
    }
    for (j = 0; j < this.channels; j++)
    {
      this.canvases[j] = new WaveCanvas();
      this.canvases[j].setSize(new Dimension(this.width, this.channelHeight + 1));
      this.canvases[j].setData(arrayOfFloat[j]);
      this.canvases[j].setResolution(this.resolution);
      this.canvases[j].setHeight(this.channelHeight);
      this.canvases[j].setAmplitude(this.amplitude);
      this.canvases[j].setWaveSize(this.afr.getWaveSize());
    }
  }
  
  public void setStartPos(int paramInt)
  {
    this.startPos = paramInt;
    reRead();
    for (int i = 0; i < this.channels; i++)
    {
      this.canvases[i].setFastDraw(false);
      this.canvases[i].repaint();
    }
  }
  
  public int getStartPos()
  {
    return this.startPos;
  }
  
  private void reRead()
  {
    this.segmentSize = (this.width * this.resolution * this.channels);
    if (this.segmentSize < 0) {
      return;
    }
    if (this.segmentSize > this.afr.getWaveSize() * this.channels * this.resolution) {
      this.segmentSize = (this.afr.getWaveSize() * this.channels * this.resolution);
    }
    if (this.startPos != this.lastStartPos)
    {
      this.data = this.afr.getSamples(this.segmentSize, this.startPos);
      updateChannelData();
      this.lastStartPos = this.startPos;
    }
  }
  
  private void updateChannelData()
  {
    float[][] arrayOfFloat = new float[this.channels][this.segmentSize / this.channels];
    int i = 0;
    int j = 0;
    while (j < this.segmentSize)
    {
      for (int k = 0; k < this.channels; k++) {
        arrayOfFloat[k][i] = this.data[(j + k)];
      }
      i++;
      j += this.channels;
    }
    for (j = 0; j < this.channels; j++)
    {
      this.canvases[j].setData(arrayOfFloat[j]);
      this.canvases[j].setResolution(this.resolution);
    }
  }
  
  private void setupPanel()
  {
    Panel localPanel = new Panel();
    localPanel.setLayout(new GridLayout(this.channels, 1));
    localPanel.setSize(new Dimension(this.width, this.channelHeight * this.channels));
    for (int i = 0; i < this.channels; i++) {
      localPanel.add(this.canvases[i]);
    }
    this.sp.add(localPanel);
  }
  
  public void actionPerformed(ActionEvent paramActionEvent)
  {
    if (paramActionEvent.getSource() == this.size1) {
      setResolution(1);
    }
    if (paramActionEvent.getSource() == this.size2) {
      setResolution(2);
    }
    if (paramActionEvent.getSource() == this.size4) {
      setResolution(4);
    }
    if (paramActionEvent.getSource() == this.size8) {
      setResolution(8);
    }
    if (paramActionEvent.getSource() == this.size16) {
      setResolution(16);
    }
    if (paramActionEvent.getSource() == this.size32) {
      setResolution(32);
    }
    if (paramActionEvent.getSource() == this.size64) {
      setResolution(64);
    }
    if (paramActionEvent.getSource() == this.size128) {
      setResolution(128);
    }
    if (paramActionEvent.getSource() == this.size256) {
      setResolution(256);
    }
    if (paramActionEvent.getSource() == this.size512) {
      setResolution(512);
    }
    if (paramActionEvent.getSource() == this.size1024) {
      setResolution(1024);
    }
    if (paramActionEvent.getSource() == this.size2048) {
      setResolution(2048);
    }
    if (paramActionEvent.getSource() == this.openFile) {
      openFile();
    }
    if (paramActionEvent.getSource() == this.quit) {
      System.exit(0);
    }
    if (paramActionEvent.getSource() == this.vSmall) {
      setHeight(25);
    }
    if (paramActionEvent.getSource() == this.small) {
      setHeight(50);
    }
    if (paramActionEvent.getSource() == this.medium) {
      setHeight(100);
    }
    if (paramActionEvent.getSource() == this.large) {
      setHeight(200);
    }
    if (paramActionEvent.getSource() == this.huge) {
      setHeight(300);
    }
    if (paramActionEvent.getSource() == this.times1) {
      setAmplitude(1);
    }
    if (paramActionEvent.getSource() == this.times2) {
      setAmplitude(2);
    }
    if (paramActionEvent.getSource() == this.times3) {
      setAmplitude(3);
    }
    if (paramActionEvent.getSource() == this.times4) {
      setAmplitude(4);
    }
    if (paramActionEvent.getSource() == this.changeColor) {
      changeColor();
    }
  }
  
  private void changeColor()
  {
    for (int i = 0; i < this.channels; i++) {
      this.canvases[i].toggleColor();
    }
    this.whiteColor = (!this.whiteColor);
  }
  
  public void setResolution(int paramInt)
  {
    if ((paramInt > 0) && (paramInt <= 2048))
    {
      this.resolution = paramInt;
      if (this.afr.getWaveSize() / this.resolution < this.width) {
        this.startPos = 0;
      }
      reRead();
      this.scrollPanel.setResolution(paramInt);
      this.scrollPanel.setScrollbarResolution(paramInt);
      for (int i = 0; i < this.channels; i++) {
        this.canvases[i].setResolution(paramInt);
      }
    }
  }
  
  public int getResolution()
  {
    return this.resolution;
  }
  
  public int getSampleRate()
  {
    return this.afr.getSampleRate();
  }
  
  public int getChannels()
  {
    return this.afr.getChannels();
  }
  
  public String getFileName()
  {
    return this.lastDirectory + this.lastFileName;
  }
  
  public int getWidth()
  {
    return this.width;
  }
  
  public void repaint()
  {
    this.sp.setSize(this.f.getSize().width, this.f.getSize().height);
    for (int i = 0; i < this.channels; i++)
    {
      this.canvases[i].setSize(this.f.getSize().width, this.canvases[i].getSize().height);
      this.canvases[i].repaint();
    }
  }
  
  public void componentResized(ComponentEvent paramComponentEvent)
  {
    if (this.f.getSize().width > this.width)
    {
      this.width = this.f.getSize().width;
      reRead();
    }
    else
    {
      this.width = this.f.getSize().width;
    }
    for (int i = 0; i < this.channels; i++)
    {
      this.canvases[i].setSize(this.width, this.canvases[i].getSize().height);
      this.canvases[i].setResized(true);
    }
  }
  
  public void componentHidden(ComponentEvent paramComponentEvent) {}
  
  public void componentMoved(ComponentEvent paramComponentEvent) {}
  
  public void componentShown(ComponentEvent paramComponentEvent) {}
  
  public void setHeight(int paramInt)
  {
    this.channelHeight = paramInt;
    setupChannels();
    setupPanel();
    this.sp.setSize(new Dimension(this.width, (this.channelHeight + 1) * this.channels));
    if (!this.whiteColor)
    {
      changeColor();
      this.whiteColor = (!this.whiteColor);
    }
    this.f.pack();
    repaint();
    this.sp.repaint();
  }
  
  public void setAmplitude(int paramInt)
  {
    this.amplitude = paramInt;
    for (int i = 0; i < this.channels; i++) {
      this.canvases[i].setAmplitude(paramInt);
    }
  }
  
  public void playFile()
  {
    if (this.channels > 2)
    {
      System.out.println("jMusic Wave View notification: Sorry, only mono and stereo files can be played at present.");
    }
    else
    {
      System.out.println("---- Playing audio file '" + getFileName() + "'... Sample rate = " + this.afr.getSampleRate() + " Channels = " + this.afr.getChannels() + " ----");
      RTLine[] arrayOfRTLine = { new AudioRTLine(getFileName()) };
      this.mixer = new RTMixer(arrayOfRTLine);
      this.mixer.begin();
    }
  }
  
  public void pauseFile()
  {
    if (this.mixer != null) {}
    this.mixer.pause();
  }
  
  public void unPauseFile()
  {
    if (this.mixer != null) {
      this.mixer.unPause();
    }
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\gui\wave\WaveView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */