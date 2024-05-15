package delta.games.lotro;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * A place to play with number formats.
 * @author DAM
 */
public class MainPlayingWithNumberFormats
{
  private static void showNumbers(Number value)
  {
    System.out.println("Locale: "+Locale.getDefault().getDisplayName());
    DecimalFormat format=(DecimalFormat)NumberFormat.getNumberInstance();
    DecimalFormatSymbols symbols=format.getDecimalFormatSymbols();
    System.out.println("Decimal separator: ["+symbols.getDecimalSeparator()+"]");
    System.out.println("Grouping separator: ["+symbols.getGroupingSeparator()+"]");
    System.out.println("Value: "+format.format(value));
  }

  /**
   * Method method for this game.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    SimpleDateFormat format=new SimpleDateFormat();
    System.out.println("toLocalizedPattern: "+format.toLocalizedPattern());
    System.out.println("toPattern: "+format.toPattern());

    Number value=new Float(1234.567);
    showNumbers(value);
    Locale.setDefault(Locale.US);
    showNumbers(value);
    Locale.setDefault(Locale.GERMAN);
    showNumbers(value);
  }
}
