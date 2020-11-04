package jp.daich.diffsellect.poi;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import jp.daich.diffsellect.util.StringUtils;
import jp.daich.diffsellect.procedure.sub.WriteOneResultProcedure;
import jp.daich.diffsellect.procedure.sub.WriteQueryProcedure;

public class ExcelWriter {

  /**
   * Constructor
   * 
   * @param tableName
   */
  public ExcelWriter(String filePath) {
    this.filePath = filePath;
  }

  // SXSSF(xlsx)
  private final XSSFWorkbook book = new XSSFWorkbook();

  // Excel book file path
  private final String filePath;

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
    return this.book.createSheet(sheetName);
  }

  /**
   * get Font Object.
   * 
   * @return font
   */
  public Font getFont() {
    return this.book.createFont();
  }

  /**
   * エクセルファイルを書き込む
   */
  public void flush() {
    FileOutputStream out = null;
    try {
      out = new FileOutputStream(filePath);
      book.write(out);
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
