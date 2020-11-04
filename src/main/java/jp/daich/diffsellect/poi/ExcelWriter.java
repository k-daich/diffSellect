package jp.daich.diffsellect.common.poi;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import jp.daich.diffsellect.common.util.StringUtils;
import jp.daich.diffsellect.procedure.sub.WriteOneResultProcedure;
import jp.daich.diffsellect.procedure.sub.WriteQueryProcedure;

public class ExcelWriter {

  // SXSSF(xlsx)
  private final XSSFWorkbook book = new XSSFWorkbook();

  /**
   * Constructor
   * 
   * @param tableName
   */
  public ExcelWriter() {
  }

  /**
   * Sellectのクエリをエクセルに書き込む
   */
  public void writeQuery(String query) {
    // MessageDigest digest = MessageDigest.getInstance("SHA-512");
    String tableName = StringUtils.cut(query.toUpperCase(), "FROM ", " WHERE");
    new WriteQueryProcedure(book, tableName).execute(query);
  }

  /**
   * Sellect結果をエクセルに書き込む
   */
  public void writeSellectResult(String tableName, String sellectResult) {
    new WriteOneResultProcedure(book, tableName).execute(sellectResult);
  }
}
