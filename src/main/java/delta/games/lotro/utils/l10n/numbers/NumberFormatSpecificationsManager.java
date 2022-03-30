package delta.games.lotro.utils.l10n.numbers;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Manager for localized number formats managers.
 * @author DAM
 */
public class NumberFormatSpecificationsManager
{
  private Map<String,LocalizedNumberFormatsManager> _managers;

  /**
   * Constructor.
   */
  public NumberFormatSpecificationsManager()
  {
    _managers=new HashMap<String,LocalizedNumberFormatsManager>();
    initSpecifications();
  }

  /**
   * Get a localized number formats manager, using its internal identifier.
   * @param id An identifier.
   * @return A manager or <code>null</code> if not found.
   */
  public LocalizedNumberFormatsManager getIntegerFormatSpecification(String id)
  {
    return _managers.get(id);
  }

  private void initSpecifications()
  {
    initAuto();
    initUS();
    initFrench();
    initEuropean();
  }

  private void initAuto()
  {
    LocalizedNumberFormatsManager spec=new LocalizedNumberFormatsManager(NumberFormatID.AUTO,Locale.getDefault());
    _managers.put(spec.getId(),spec);
  }

  private void initUS()
  {
    LocalizedNumberFormatsManager spec=new LocalizedNumberFormatsManager(NumberFormatID.US,Locale.US);
    _managers.put(spec.getId(),spec);
  }

  private void initFrench()
  {
    LocalizedNumberFormatsManager spec=new LocalizedNumberFormatsManager(NumberFormatID.FRENCH,Locale.FRENCH);
    _managers.put(spec.getId(),spec);
  }

  private void initEuropean()
  {
    LocalizedNumberFormatsManager spec=new LocalizedNumberFormatsManager(NumberFormatID.EUROPEAN,Locale.GERMANY);
    _managers.put(spec.getId(),spec);
  }
}
