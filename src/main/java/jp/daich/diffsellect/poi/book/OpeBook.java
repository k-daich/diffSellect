package jp.daich.diffsellect.poi.book;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import jp.daich.diffsellect.poi.book.constants.FontMapKey;
import jp.daich.diffsellect.poi.book.constants.StyleMapKey;

public class OpeBook {

  /**
   * Constructor
   */
  public OpeBook(String filePath) {
    // ブックファイルPathを設定する
    this.filePath = filePath;

    this.book = new XSSFWorkbook();

    // フォントオブジェクトの生成＆Mapへの登録
    this.fontMap.put(FontMapKey.MIDDLE, createFont("Meiryo UI", (short) 9));
    this.fontMap.put(FontMapKey.SMALL, createFont("Meiryo UI", (short) 7));
    this.fontMap.put(FontMapKey.LARGE, createFont("Meiryo UI", (short) 11));

    // スタイルオブジェクトの生成＆Mapへの登録
    // 罫線なしスタイルをMapに登録する
    this.styleMap.put(StyleMapKey.NON_BORDER,
        createStyle(BorderStyle.NONE, IndexedColors.WHITE, this.fontMap.get(FontMapKey.MIDDLE)));
    // 罫線ありスタイルをMapに登録する
    this.styleMap.put(StyleMapKey.BORDER,
        createStyle(BorderStyle.HAIR, IndexedColors.WHITE, this.fontMap.get(FontMapKey.MIDDLE)));
    // 罫線あり/黄色背景スタイルをMapに登録する
    this.styleMap.put(StyleMapKey.NON_BORDER,
        createStyle(BorderStyle.NONE, IndexedColors.WHITE, this.fontMap.get(FontMapKey.MIDDLE)));
  }

  // Excel book file path
  private final String filePath;

  // SXSSF(xlsx)
  private final XSSFWorkbook book;

  // スタイルを保持するMap
  private final Map<StyleMapKey, CellStyle> styleMap = new HashMap<>();

  // スタイルを保持するMap
  private final Map<FontMapKey, Font> fontMap = new HashMap<>();

  /**
   * get sheet object. if sheet is not exist, return null.
   * 
   * @param sheetName
   * @return sheet
   */
  public Sheet getSheet(String sheetName) {
    return this.book.getSheet(sheetName);
  }

  /**
   * create new sheet.
   * 
   * @param sheetName
   * @return sheet
   */
  public Sheet createSheet(String sheetName) {
    Sheet _sheet = this.book.createSheet(sheetName);
    // シートの行の高さのデフォルト設定を行う
    // _sheet.setDefaultRowHeightInPoints((short) 12);
    // シートの列幅のデフォルト設定を行う
    _sheet.setDefaultColumnWidth((short) 32);

    return _sheet;
  }

  /**
   * 
   * 
   * @param borderStyle
   * @param color
   * @param font
   */
  private CellStyle createStyle(BorderStyle borderStyle, IndexedColors color, Font font) {

    CellStyle _style = this.book.createCellStyle();

    // 枠線のスタイルを設定する
    _style.setBorderTop(borderStyle);
    _style.setBorderBottom(borderStyle);
    _style.setBorderLeft(borderStyle);
    _style.setBorderRight(borderStyle);

    _style.setWrapText(true);

    // 枠線の色を設定する
    // _style.setTopBorderColor(color.getIndex());
    // _style.setBottomBorderColor(color.getIndex());
    // _style.setLeftBorderColor(color.getIndex());
    // _style.setRightBorderColor(color.getIndex());

    _style.setFillBackgroundColor(color.index);
    ;
    _style.setFont(font);

    return _style;
  }

  /**
   * フォントオブジェクトを作成する
   * 
   * @param fontName フォント名
   * @param fontSize フォントサイズ
   */
  private Font createFont(String fontName, short fontSize) {
    Font _font = this.book.createFont();
    _font.setFontName(fontName);
    _font.setFontHeightInPoints(fontSize);

    return _font;
  }

  public Map getStyleMap() {
    return styleMap;
  }

  /**
   * エクセルファイルを書き込む
   */
  public void flush() {
    FileOutputStream out = null;
    try {
      out = new FileOutputStream(this.filePath);
      this.book.write(out);
    } catch (IOException ex) {
      throw new RuntimeException("   !!!! Error " + this.getClass().getCanonicalName() + "@flush()", ex);
    } finally {
      try {
        out.close();
      } catch (IOException ex) {
        throw new RuntimeException(" !!!! Error " + this.getClass().getCanonicalName() + "@flush()@close()", ex);
      }
    }
  }

}
