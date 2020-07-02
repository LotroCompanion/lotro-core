package delta.games.lotro.character.classes.traitTree.setup.io.xml;

import java.io.File;

import org.apache.log4j.Logger;

import delta.common.utils.text.EncodingNames;
import delta.games.lotro.character.classes.traitTree.setup.TraitTreeSetup;

/**
 * I/O methods for trait tree setups.
 * @author DAM
 */
public class TraitTreeSetupsIO
{
  private static final Logger LOGGER=Logger.getLogger(TraitTreeSetupsIO.class);

  /**
   * Load a setup data from a given file.
   * @param file File to read.
   * @return A setup or <code>null</code> if a problem occurs.
   */
  public static TraitTreeSetup getTraitTreeSetup(File file)
  {
    TraitTreeSetupXMLParser xmlInfoParser=new TraitTreeSetupXMLParser();
    TraitTreeSetup setup=xmlInfoParser.parseXML(file);
    if (setup!=null)
    {
      setup.setFile(file);
    }
    return setup;
  }

  /**
   * Write a trait tree setup to disk.
   * @param toFile File to write to.
   * @param setup Setup to write.
   * @return <code>true</code> it it succeeds, <code>false</code> otherwise.
   */
  public static boolean saveInfo(File toFile, TraitTreeSetup setup)
  {
    boolean ret=true;
    File parentFile=toFile.getParentFile();
    if (!parentFile.exists())
    {
      ret=parentFile.mkdirs();
      if (!ret)
      {
        LOGGER.error("Cannot create directory ["+parentFile+"]!");
      }
    }
    if (ret)
    {
      TraitTreeSetupXMLWriter writer=new TraitTreeSetupXMLWriter();
      ret=writer.write(toFile,setup,EncodingNames.UTF_8);
    }
    return ret;
  }
}
