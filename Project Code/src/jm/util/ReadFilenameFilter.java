package jm.util;

import java.io.File;
import java.io.FilenameFilter;

class ReadFilenameFilter
  implements FilenameFilter
{
  ReadFilenameFilter() {}
  
  public boolean accept(File paramFile, String paramString)
  {
    return (!paramString.startsWith(".")) && ((paramString.endsWith(".mid")) || (paramString.endsWith(".midi")) || (paramString.endsWith(".jm")));
  }
}
