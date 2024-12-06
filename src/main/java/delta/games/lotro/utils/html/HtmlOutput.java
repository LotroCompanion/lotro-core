package delta.games.lotro.utils.html;

import delta.games.lotro.common.Identifiable;
import delta.games.lotro.utils.strings.GenericOutput;
import delta.games.lotro.utils.style.FontStyle;
import delta.games.lotro.utils.style.Style;
import delta.games.lotro.utils.style.StyleElement;
import delta.games.lotro.utils.style.TitleStyle;

/**
 * HTML output.
 * @author DAM
 */
public class HtmlOutput implements GenericOutput
{
  private LinkGenerator _linkGenerator;

  /**
   * Constructor.
   * @param linkGenerator
   */
  public HtmlOutput(LinkGenerator linkGenerator)
  {
    _linkGenerator=linkGenerator;
  }

  @Override
  public void startDocument(StringBuilder sb)
  {
    sb.append("<html>");
  }

  @Override
  public void endDocument(StringBuilder sb)
  {
    sb.append("</html>");
  }

  @Override
  public void startBody(StringBuilder sb)
  {
    sb.append("<body>");
  }

  @Override
  public void endBody(StringBuilder sb)
  {
    sb.append("</body>");
  }

  @Override
  public void printLink(StringBuilder sb, String text, Identifiable to)
  {
    String link=_linkGenerator.buildURL(to);
    HtmlUtils.printLink(sb,link,text);
  }

  @Override
  public void printText(StringBuilder sb, String text)
  {
    sb.append(HtmlUtils.toHtml(text));
  }

  @Override
  public void printText(StringBuilder sb, String text, StyleElement... styles)
  {
    startStyle(sb,styles);
    printText(sb,text);
    endStyle(sb,styles);
  }

  @Override
  public void startParagraph(StringBuilder sb)
  {
    sb.append("<p>");
  }

  @Override
  public void endParagraph(StringBuilder sb)
  {
    sb.append("</p>");
  }

  @Override
  public void newLine(StringBuilder sb)
  {
    sb.append("<br>");
  }

  @Override
  public void startBold(StringBuilder sb)
  {
    sb.append("<b>");
  }

  @Override
  public void endBold(StringBuilder sb)
  {
    sb.append("</b>");
  }

  @Override
  public void startItalic(StringBuilder sb)
  {
    sb.append("<i>");
  }

  @Override
  public void endItalic(StringBuilder sb)
  {
    sb.append("</i>");
  }

  @Override
  public void startUnderline(StringBuilder sb)
  {
    sb.append("<u>");
  }

  @Override
  public void endUnderline(StringBuilder sb)
  {
    sb.append("</u>");
  }

  @Override
  public void startTitle(StringBuilder sb, int level)
  {
    sb.append("<h").append(level).append('>');
  }

  @Override
  public void endTitle(StringBuilder sb, int level)
  {
    sb.append("</h").append(level).append('>');
  }

  @Override
  public void startStyle(StringBuilder sb, StyleElement... elements)
  {
    for(StyleElement element : elements)
    {
      if (element==Style.BOLD)
      {
        startBold(sb);
      }
      else if (element==Style.ITALIC)
      {
        startItalic(sb);
      }
      else if (element==Style.UNDERLINE)
      {
        startUnderline(sb);
      }
      else if (element instanceof TitleStyle)
      {
        int level=((TitleStyle)element).getLevel();
        startTitle(sb,level);
      }
      else if (element instanceof FontStyle)
      {
        startFontStyle(sb,(FontStyle)element);
      }
    }
  }

  private void startFontStyle(StringBuilder sb, FontStyle fontStyle)
  {
    sb.append("<font");
    String name=fontStyle.getName();
    if (name!=null)
    {
      sb.append(" name=\"").append(name).append('"');
    }
    Integer size=fontStyle.getSize();
    if (size!=null)
    {
      sb.append(" size=\"").append(size).append('"');
    }
    String color=fontStyle.getColor();
    if (color!=null)
    {
      sb.append(" color=\"").append(color).append('"');
    }
    sb.append(">");
  }

  private void endFontStyle(StringBuilder sb)
  {
    sb.append("</font>");
  }

  @Override
  public void endStyle(StringBuilder sb, StyleElement... elements)
  {
    int nbElements=elements.length;
    for(int i=nbElements-1;i>=0;i--)
    {
      StyleElement element=elements[i];
      endStyle(sb,element);
    }
  }

  private void endStyle(StringBuilder sb, StyleElement element)
  {
    if (element==Style.BOLD)
    {
      endBold(sb);
    }
    else if (element==Style.ITALIC)
    {
      endItalic(sb);
    }
    else if (element==Style.UNDERLINE)
    {
      endUnderline(sb);
    }
    else if (element instanceof TitleStyle)
    {
      int level=((TitleStyle)element).getLevel();
      endTitle(sb,level);
    }
    else if (element instanceof FontStyle)
    {
      endFontStyle(sb);
    }
  }
}
