package jp.daich.diffsellect.common.poi;

import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import jp.daich.diffsellect.common.poi.procedure.WriteOneResultProcedure;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExcelWriter {

  // SXSSF(xlsx)
  private final XSSFWorkbook book = new XSSFWorkbook();

  /**
   * Constructor
   * 
   * @param tableName
   */
  public ExcelWriter(String tableName) {
  }

  public static final String XLSX_FILE_PATH = ".\\outfile"
      + new SimpleDateFormat("yyyy_MM_dd(E)HH_mm_ss").format(new Date()) + ".xlsx";

  /**
   * Sellect結果をエクセルに書き込む
   */
  public void writeSellectResult(String tableName, String sellectResult) {
    new WriteOneResultProcedure(book, tableName).execute(sellectResult);
    new WriteOneResultProcedure(book, tableName).execute(sellectResult);
    new WriteOneResultProcedure(book, tableName).execute(sellectResult);
    new WriteOneResultProcedure(book, tableName).execute(sellectResult);
  }

  /**
   * エクセルファイルを書き込む
   */
  public void flush() {
    FileOutputStream out = null;
    try {
      out = new FileOutputStream(XLSX_FILE_PATH);
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
