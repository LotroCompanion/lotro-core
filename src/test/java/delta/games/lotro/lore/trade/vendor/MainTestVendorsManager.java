package delta.games.lotro.lore.trade.vendor;

import java.util.List;

/**
 * Simple test class for the vendors manager.
 * @author DAM
 */
public class MainTestVendorsManager
{
  /**
   * Main method for this test.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    VendorsManager mgr=VendorsManager.getInstance();
    List<VendorNpc> vendors=mgr.getAll();
    for(VendorNpc vendor : vendors)
    {
      System.out.println(vendor.dump());
    }
  }
}
