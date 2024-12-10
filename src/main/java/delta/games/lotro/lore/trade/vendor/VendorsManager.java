package delta.games.lotro.lore.trade.vendor;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import delta.games.lotro.common.IdentifiableComparator;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.lore.trade.vendor.io.xml.VendorXMLParser;
import delta.games.lotro.utils.PerfUtils;

/**
 * Facade for vendors access.
 * @author DAM
 */
public class VendorsManager
{
  private static VendorsManager _instance=null;

  private HashMap<Integer,VendorNpc> _cache;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static VendorsManager getInstance()
  {
    if (_instance==null)
    {
      _instance=new VendorsManager(true);
    }
    return _instance;
  }

  /**
   * Private constructor.
   * @param load Indicates if the vendors database shall be loaded or not.
   */
  private VendorsManager(boolean load)
  {
    _cache=new HashMap<Integer,VendorNpc>(100);
    if (load)
    {
      loadAll();
    }
  }

  /**
   * Load all vendors.
   */
  private void loadAll()
  {
    _cache.clear();
    LotroCoreConfig cfg=LotroCoreConfig.getInstance();
    File vendorsFile=cfg.getFile(DataFiles.VENDORS);
    long now=System.currentTimeMillis();
    List<VendorNpc> vendors=new VendorXMLParser().parseXML(vendorsFile);
    for(VendorNpc vendor : vendors)
    {
      _cache.put(Integer.valueOf(vendor.getIdentifier()),vendor);
    }
    long now2=System.currentTimeMillis();
    long duration=now2-now;
    PerfUtils.showLoadedLog(_cache.size(),"vendors",duration);
  }

  /**
   * Get a list of all vendors, sorted by identifier.
   * @return A list of vendors.
   */
  public List<VendorNpc> getAll()
  {
    ArrayList<VendorNpc> vendors=new ArrayList<VendorNpc>();
    vendors.addAll(_cache.values());
    Collections.sort(vendors,new IdentifiableComparator<VendorNpc>());
    return vendors;
  }

  /**
   * Get a vendor using its identifier.
   * @param id Vendor identifier.
   * @return A vendor or <code>null</code> if not found.
   */
  public VendorNpc getVendor(int id)
  {
    VendorNpc ret=null;
    ret=_cache.get(Integer.valueOf(id));
    return ret;
  }
}
