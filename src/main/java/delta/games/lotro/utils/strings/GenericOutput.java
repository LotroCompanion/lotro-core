package delta.games.lotro.utils.strings;

import delta.games.lotro.common.Identifiable;
import delta.games.lotro.utils.style.StyleElement;

/**
 * Generic output interface.
 * @author DAM
 */
public interface GenericOutput
{
  /**
   * Start document.
   * @param sb Output.
   */
  void startDocument(StringBuilder sb);
  /**
   * End document.
   * @param sb Output.
   */
  void endDocument(StringBuilder sb);
  /**
   * Start body.
   * @param sb Output.
   */
  void startBody(StringBuilder sb);
  /**
   * End body.
   * @param sb Output.
   */
  void endBody(StringBuilder sb);
  /**
   * Print a link.
   * @param sb Output.
   * @param text Link text.
   * @param to Link target.
   */
  void printLink(StringBuilder sb, String text, Identifiable to);
  /**
   * Print text.
   * @param sb Output.
   * @param text Text.
   */
  void printText(StringBuilder sb, String text);
  /**
   * Print text.
   * @param sb Output.
   * @param text Text.
   * @param styles Styles to use.
   */
  void printText(StringBuilder sb, String text, StyleElement... styles);
  /**
   * Start a paragraph.
   * @param sb Output.
   */
  void startParagraph(StringBuilder sb);
  /**
   * End a paragraph.
   * @param sb Output.
   */
  void endParagraph(StringBuilder sb);
  /**
   * New line.
   * @param sb Output.
   */
  void newLine(StringBuilder sb);
  /**
   * Switch to bold.
   * @param sb Output.
   */
  void startBold(StringBuilder sb);
  /**
   * End bold.
   * @param sb Output.
   */
  void endBold(StringBuilder sb);
  /**
   * Switch to italic.
   * @param sb Output.
   */
  void startItalic(StringBuilder sb);
  /**
   * End italic.
   * @param sb Output.
   */
  void endItalic(StringBuilder sb);
  /**
   * Switch to underline.
   * @param sb Output.
   */
  void startUnderline(StringBuilder sb);
  /**
   * End underline.
   * @param sb Output.
   */
  void endUnderline(StringBuilder sb);
  /**
   * Start a title.
   * @param sb Output.
   * @param level Title level.
   */
  void startTitle(StringBuilder sb, int level);
  /**
   * End a title.
   * @param sb Output.
   * @param level Title level.
   */
  void endTitle(StringBuilder sb, int level);

  /**
   * Start a style.
   * @param sb Output.
   * @param elements Style elements.
   */
  void startStyle(StringBuilder sb, StyleElement...elements);

  /**
   * End a style.
   * @param sb Output.
   * @param elements Style elements.
   */
  void endStyle(StringBuilder sb, StyleElement...elements);
}
