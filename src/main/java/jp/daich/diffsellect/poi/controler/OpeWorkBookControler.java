package jp.daich.diffsellect.poi.controler;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import jp.daich.diffsellect.common.util.LogUtil;

public class OpeWorkBookControler {

    /**
     * Invalidated Constructor
     */
    OpeWorkBookControler(String bookFilePath) {
        this.bookFilePath = bookFilePath;
    }

    private final String bookFilePath;
    private final Workbook book = new XSSFWorkbook();
    // 作成したシートを登録しておくMap
    private final Map<Integer, Sheet> sheetMap = new HashMap<Integer, Sheet>();

    /**
     * 
     * @param tableName
     */
    public Sheet getSheet(String tableName) {
        if (sheetMap.get(tableName.hashCode()) != null) {
            return sheetMap.get(tableName.hashCode());
        }
        // シート登録されていないテーブル名のシートを取得しようとしていた場合はエラー扱い
        throw new RuntimeException("Not Found Sheet. table name is [ :" + tableName + "]");
    }

    /**
     * ターブル名に準じたシートを設定（無ければ新規作成）する。
     * 
     * @param tableName
     */
    public void setSheet(String tableName) {
        LogUtil.debug("[table name] : " + tableName + " ,[hash code] : " + tableName.hashCode());
        // 指定したテーブル名のシートがない場合は新規作成する
        if (sheetMap.get(tableName.hashCode()) == null) {
            // create sheet
            Sheet sheet = book.createSheet(tableName + "_" + tableName.hashCode());
            // シートの行の高さのデフォルト設定を行う
            sheet.setDefaultRowHeightInPoints((short) 12);
            // シートの列幅のデフォルト設定を行う
            sheet.setDefaultColumnWidth((short) 32);
            // シートMapに登録する
            sheetMap.put(tableName.hashCode(), sheet);
        } else {
            // get existed sheet
            Sheet sheet = sheetMap.get(tableName.hashCode());
        }
    }

    /**
     * エクセルファイルを書き込む
     */
    public void flush() {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(bookFilePath);
            book.write(out);
        } catch (IOException ex) {
            throw new RuntimeException("   !!!! Error " + this.getClass().getCanonicalName() + "@flush()", ex);
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                throw new RuntimeException(" !!!! Error " + this.getClass().getCanonicalName() + "@flush()@close()",
                        ex);
            }
        }
    }

}
