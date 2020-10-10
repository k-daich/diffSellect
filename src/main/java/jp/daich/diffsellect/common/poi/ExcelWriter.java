package jp.daich.diffsellect.common.poi;

import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExcelWriter {

  private final String SHEET_NAME;

  // SXSSF(xlsx)
  private final SXSSFWorkbook book = new SXSSFWorkbook();

  // SXSSF(xlsx)
  private final SXSSFSheet sheet;

  /**
   * Constructor
   * 
   * @param tableName
   */
  public ExcelWriter(String tableName) {
    System.out.println("!!! Start ExcelWriter.java !!!");
    // set sheet name
    SHEET_NAME = tableName;
    // create sheet
    sheet = book.createSheet(SHEET_NAME);
    System.out.println("!!! End ExcelWriter.java !!!");
  }

  public static final String XLSX_FILE_PATH = ".\\outfile" + new SimpleDateFormat("yyMMddEHHmmss").format(new Date()) + ".xlsx";
  // public static final String XLSX_FILE_PATH = "./outfile/sample-xlsx-file" +
  // new Date() + ".xlsx";

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
      // finally{
      // try {
      // // out.close();
      // }catch(IOException ex){
      // throw new RuntimeException(" !!!! Error " +
      // this.getClass().getCanonicalName() + "@flush()@close()", ex);
      // }
    }
  }
}
