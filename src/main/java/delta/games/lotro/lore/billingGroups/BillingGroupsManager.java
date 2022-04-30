package delta.games.lotro.lore.billingGroups;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import delta.games.lotro.common.IdentifiableComparator;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.lore.billingGroups.io.xml.BillingGroupsXMLParser;

/**
 * Facade for billing groups access.
 * @author DAM
 */
public class BillingGroupsManager
{
  private static final Logger LOGGER=Logger.getLogger(BillingGroupsManager.class);

  private static BillingGroupsManager _instance=null;

  private HashMap<Integer,BillingGroupDescription> _cache;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static BillingGroupsManager getInstance()
  {
    if (_instance==null)
    {
      _instance=new BillingGroupsManager(true);
    }
    return _instance;
  }

  /**
   * Private constructor.
   * @param load Indicates if the billing groups shall be loaded or not.
   */
  private BillingGroupsManager(boolean load)
  {
    _cache=new HashMap<Integer,BillingGroupDescription>(100);
    if (load)
    {
      loadAll();
    }
  }

  /**
   * Load all billing groups.
   */
  private void loadAll()
  {
    _cache.clear();
    LotroCoreConfig cfg=LotroCoreConfig.getInstance();
    File billingGroupsFile=cfg.getFile(DataFiles.BILLING_GROUPS);
    long now=System.currentTimeMillis();
    List<BillingGroupDescription> billingGroups=new BillingGroupsXMLParser().parseXML(billingGroupsFile);
    for(BillingGroupDescription billingGroup : billingGroups)
    {
      _cache.put(Integer.valueOf(billingGroup.getIdentifier()),billingGroup);
    }
    long now2=System.currentTimeMillis();
    long duration=now2-now;
    LOGGER.info("Loaded "+_cache.size()+" billing groups in "+duration+"ms.");
  }

  /**
   * Get a list of all billing groups, sorted by identifier.
   * @return A list of billing groups.
   */
  public List<BillingGroupDescription> getAll()
  {
    ArrayList<BillingGroupDescription> billingGroups=new ArrayList<BillingGroupDescription>();
    billingGroups.addAll(_cache.values());
    Collections.sort(billingGroups,new IdentifiableComparator<BillingGroupDescription>());
    return billingGroups;
  }

  /**
   * Get a billing group using its identifier.
   * @param id Identifier.
   * @return A billing group description or <code>null</code> if not found.
   */
  public BillingGroupDescription getBillingGroupDescription(int id)
  {
    BillingGroupDescription ret=null;
    ret=_cache.get(Integer.valueOf(id));
    return ret;
  }
}
