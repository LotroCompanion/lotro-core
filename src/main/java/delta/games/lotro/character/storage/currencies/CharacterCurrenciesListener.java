package delta.games.lotro.character.storage.currencies;

import delta.games.lotro.character.CharacterFile;
import delta.games.lotro.character.details.CharacterDetails;
import delta.games.lotro.character.events.CharacterEvent;
import delta.games.lotro.character.events.CharacterEventType;
import delta.games.lotro.common.money.Money;
import delta.games.lotro.utils.events.EventsManager;
import delta.games.lotro.utils.events.GenericEventsListener;

/**
 * Listener to update character currencies (gold, xp).
 * @author DAM
 */
public class CharacterCurrenciesListener
{
  private GenericEventsListener<CharacterEvent> _characterEventsListener;

  /**
   * Constructor.
   */
  public CharacterCurrenciesListener()
  {
    _characterEventsListener=new GenericEventsListener<CharacterEvent>()
    {
      @Override
      public void eventOccurred(CharacterEvent event)
      {
        CharacterEventType type=event.getType();
        if (type==CharacterEventType.CHARACTER_DETAILS_UPDATED)
        {
          CharacterFile toon=event.getToonFile();
          CharacterDetails details=toon.getDetails();
          useDetails(toon,details);
        }
      }
    };
    EventsManager.addListener(CharacterEvent.class,_characterEventsListener);
  }

  private void useDetails(CharacterFile toon, CharacterDetails details)
  {
    CurrenciesManager mgr=new CurrenciesManager(toon);
    // Money
    Money money=details.getMoney();
    int value=money.getInternalValue();
    mgr.updateCurrency(CurrencyKeys.GOLD,value,true);
    // XP
    long xp=details.getXp();
    mgr.updateCurrency(CurrencyKeys.XP,(int)xp,true);
    mgr.save();
  }

  /**
   * Release all managed resources.
   */
  public void dispose()
  {
    EventsManager.removeListener(CharacterEvent.class,_characterEventsListener);
  }
}
