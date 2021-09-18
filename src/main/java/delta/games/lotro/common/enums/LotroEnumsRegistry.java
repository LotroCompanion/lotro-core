package delta.games.lotro.common.enums;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import delta.games.lotro.common.enums.io.xml.EnumXMLParser;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;

/**
 * Registry for lotro enums.
 * @author DAM
 */
public class LotroEnumsRegistry
{
  private static final Logger LOGGER=Logger.getLogger(LotroEnumsRegistry.class);

  private static LotroEnumsRegistry _instance=null;

  private Map<Class<? extends LotroEnumEntry>, LotroEnum<?>> _enums;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static LotroEnumsRegistry getInstance()
  {
    if (_instance==null)
    {
      _instance=new LotroEnumsRegistry();
    }
    return _instance;
  }

  /**
   * Constructor.
   */
  public LotroEnumsRegistry()
  {
    _enums=new HashMap<Class<? extends LotroEnumEntry>, LotroEnum<?>>();
  }

  /**
   * Get an enum.
   * @param implClass Entry implementation class.
   * @return An enum or <code>null</code> if not found.
   */
  public <T extends LotroEnumEntry> LotroEnum<T> get(Class<T> implClass)
  {
    @SuppressWarnings("unchecked")
    LotroEnum<T> ret=(LotroEnum<T>)_enums.get(implClass);
    if (ret==null)
    {
      ret=load(implClass);
      _enums.put(implClass,ret);
    }
    return ret;
  }

  private <T extends LotroEnumEntry> LotroEnum<T> load(Class<T> implClass)
  {
    File enumsDir=LotroCoreConfig.getInstance().getFile(DataFiles.ENUMS_DIR);
    String fileName=implClass.getSimpleName()+".xml";
    File enumFile=new File(enumsDir,fileName);
    LotroEnum<T> ret=new EnumXMLParser<T>().parseXML(enumFile,implClass);
    if (ret!=null)
    {
      LOGGER.debug("Loaded enum: "+implClass.getName());
    }
    return ret;
  }
}
