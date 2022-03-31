package delta.games.lotro.utils.l10n.numbers;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Manager for localized number formats managers.
 * @author DAM
 */
public class LocalizedNumberFormatsManager
{
  private Map<String,SingleLocaleNumberFormatsManager> _managers;

  /**
   * Constructor.
   */
  public LocalizedNumberFormatsManager()
  {
    _managers=new HashMap<String,SingleLocaleNumberFormatsManager>();
    initSpecifications();
  }

  /**
   * Get a number formats manager for a locale, using its internal identifier.
   * @param id An identifier.
   * @return A manager or <code>null</code> if not found.
   */
  public SingleLocaleNumberFormatsManager getNumberFormatsManager(String id)
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
    SingleLocaleNumberFormatsManager spec=new SingleLocaleNumberFormatsManager(NumberFormatID.AUTO,Locale.getDefault());
    _managers.put(spec.getId(),spec);
  }

  private void initUS()
  {
    SingleLocaleNumberFormatsManager spec=new SingleLocaleNumberFormatsManager(NumberFormatID.US,Locale.US);
    _managers.put(spec.getId(),spec);
  }

  private void initFrench()
  {
    SingleLocaleNumberFormatsManager spec=new SingleLocaleNumberFormatsManager(NumberFormatID.FRENCH,Locale.FRENCH);
    _managers.put(spec.getId(),spec);
  }

  private void initEuropean()
  {
    SingleLocaleNumberFormatsManager spec=new SingleLocaleNumberFormatsManager(NumberFormatID.EUROPEAN,Locale.GERMANY);
    _managers.put(spec.getId(),spec);
  }
}
