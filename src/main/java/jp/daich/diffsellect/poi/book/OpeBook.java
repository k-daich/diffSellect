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
import jp.daich.diffsellect.poi.book.style.constants.StyleMapKey;
import jp.daich.diffsellect.poi.cell.style.CellStyleBuilder;
import jp.daich.diffsellect.poi.sheet.OpeSheet;

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
    this.styleMap.put(StyleMapKey.NORMAL,
        new CellStyleBuilder(this.book, IndexedColors.WHITE, true, this.fontMap.get(FontMapKey.MIDDLE)).build());
    // 罫線なし文字小さめスタイルをMapに登録する
    this.styleMap.put(StyleMapKey.SMALL_TEXT,
        new CellStyleBuilder(this.book, IndexedColors.WHITE, true, this.fontMap.get(FontMapKey.MIDDLE)).build());
    // 罫線ありスタイル（左上）をMapに登録する
    this.styleMap.put(StyleMapKey.RANGE_BOX_LEFT_TOP,
        new CellStyleBuilder(this.book, IndexedColors.SKY_BLUE, true, this.fontMap.get(FontMapKey.MIDDLE))
            .borderTop(BorderStyle.THICK).borderRight(BorderStyle.HAIR).borderBottom(BorderStyle.HAIR)
            .borderLeft(BorderStyle.THICK).build());
    // 罫線ありスタイル（左中間）をMapに登録する
    this.styleMap.put(StyleMapKey.RANGE_BOX_LEFT_MIDDLE,
        new CellStyleBuilder(this.book, IndexedColors.AQUA, true, this.fontMap.get(FontMapKey.MIDDLE))
            .borderTop(BorderStyle.HAIR).borderRight(BorderStyle.HAIR).borderBottom(BorderStyle.HAIR)
            .borderLeft(BorderStyle.THICK).build());
    // 罫線ありスタイル（左下）をMapに登録する
    this.styleMap.put(StyleMapKey.RANGE_BOX_LEFT_BOTTOM,
        new CellStyleBuilder(this.book, IndexedColors.WHITE, true, this.fontMap.get(FontMapKey.MIDDLE))
            .borderTop(BorderStyle.HAIR).borderRight(BorderStyle.HAIR).borderBottom(BorderStyle.THICK)
            .borderLeft(BorderStyle.THICK).build());
    // 罫線ありスタイル（中間上）をMapに登録する
    this.styleMap.put(StyleMapKey.RANGE_BOX_MIDDLE_TOP,
        new CellStyleBuilder(this.book, IndexedColors.WHITE, true, this.fontMap.get(FontMapKey.MIDDLE))
            .borderTop(BorderStyle.THICK).borderRight(BorderStyle.HAIR).borderBottom(BorderStyle.HAIR)
            .borderLeft(BorderStyle.HAIR).build());
    // 罫線ありスタイル（中間）をMapに登録する
    this.styleMap.put(StyleMapKey.RANGE_BOX_MIDDLE_MIDDLE,
        new CellStyleBuilder(this.book, IndexedColors.WHITE, true, this.fontMap.get(FontMapKey.MIDDLE))
            .borderTop(BorderStyle.HAIR).borderRight(BorderStyle.HAIR).borderBottom(BorderStyle.HAIR)
            .borderLeft(BorderStyle.HAIR).build());
    // 罫線ありスタイル（中間下）をMapに登録する
    this.styleMap.put(StyleMapKey.RANGE_BOX_MIDDLE_BOTTOM,
        new CellStyleBuilder(this.book, IndexedColors.WHITE, true, this.fontMap.get(FontMapKey.MIDDLE))
            .borderTop(BorderStyle.HAIR).borderRight(BorderStyle.HAIR).borderBottom(BorderStyle.THICK)
            .borderLeft(BorderStyle.HAIR).build());
    // 罫線ありスタイル（右上）をMapに登録する
    this.styleMap.put(StyleMapKey.RANGE_BOX_RIGHT_TOP,
        new CellStyleBuilder(this.book, IndexedColors.WHITE, true, this.fontMap.get(FontMapKey.MIDDLE))
            .borderTop(BorderStyle.THICK).borderRight(BorderStyle.THICK).borderBottom(BorderStyle.HAIR)
            .borderLeft(BorderStyle.HAIR).build());
    // 罫線ありスタイル（右中間）をMapに登録する
    this.styleMap.put(StyleMapKey.RANGE_BOX_RIGHT_MIDDLE,
        new CellStyleBuilder(this.book, IndexedColors.WHITE, true, this.fontMap.get(FontMapKey.MIDDLE))
            .borderTop(BorderStyle.HAIR).borderRight(BorderStyle.THICK).borderBottom(BorderStyle.HAIR)
            .borderLeft(BorderStyle.HAIR).build());
    // 罫線ありスタイル（右下）をMapに登録する
    this.styleMap.put(StyleMapKey.RANGE_BOX_RIGHT_BOTTOM,
        new CellStyleBuilder(this.book, IndexedColors.WHITE, true, this.fontMap.get(FontMapKey.MIDDLE))
            .borderTop(BorderStyle.HAIR).borderRight(BorderStyle.THICK).borderBottom(BorderStyle.THICK)
            .borderLeft(BorderStyle.HAIR).build());
    // 罫線ありスタイル（1列上）をMapに登録する
    this.styleMap.put(StyleMapKey.RANGE_BOX_ONE_COLUMN_TOP,
        new CellStyleBuilder(this.book, IndexedColors.WHITE, true, this.fontMap.get(FontMapKey.MIDDLE))
            .borderTop(BorderStyle.THICK).borderRight(BorderStyle.THICK).borderBottom(BorderStyle.HAIR)
            .borderLeft(BorderStyle.THICK).build());
    // 罫線ありスタイル（1列中間）をMapに登録する
    this.styleMap.put(StyleMapKey.RANGE_BOX_ONE_COLUMN_MIDDLE,
        new CellStyleBuilder(this.book, IndexedColors.WHITE, true, this.fontMap.get(FontMapKey.MIDDLE))
            .borderTop(BorderStyle.HAIR).borderRight(BorderStyle.THICK).borderBottom(BorderStyle.HAIR)
            .borderLeft(BorderStyle.THICK).build());
    // 罫線ありスタイル（1列下）をMapに登録する
    this.styleMap.put(StyleMapKey.RANGE_BOX_ONE_COLUMN_BOTTOM,
        new CellStyleBuilder(this.book, IndexedColors.WHITE, true, this.fontMap.get(FontMapKey.MIDDLE))
            .borderTop(BorderStyle.HAIR).borderRight(BorderStyle.THICK).borderBottom(BorderStyle.THICK)
            .borderLeft(BorderStyle.THICK).build());
  }

  // Excel book file path
  private final String filePath;

  // SXSSF(xlsx)
  private final XSSFWorkbook book;

  // 全シートを保持するMap
  private final Map<String, OpeSheet> sheetMap = new HashMap<>();

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
  public OpeSheet getSheet(String sheetName) {
    return this.sheetMap.get(sheetName);
  }

  /**
   * create new sheet.
   * 
   * @param sheetName
   * @return OpeSheet
   */
  public OpeSheet createSheet(String sheetName) {
    Sheet _sheet = this.book.createSheet(sheetName);
    // シートの行の高さのデフォルト設定を行う
    // _sheet.setDefaultRowHeightInPoints((short) 11);
    // シートの列幅のデフォルト設定を行う
    _sheet.setDefaultColumnWidth((short) 24);

    OpeSheet _opeSheet = new OpeSheet(_sheet, this.styleMap);
    this.sheetMap.put(sheetName, _opeSheet);
    return _opeSheet;
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
