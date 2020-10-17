package jp.daich.diffsellect.procedure.sub;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class SqlTextReadProcedure {

    // 読み込むファイルパス
    private final String filePath;

    /**
     * Constructor
     */
    public SqlTextReadProcedure(String filePath) {
        this.filePath = filePath;
    }

    public void execute() {
        // 1行ずつ読み込む
        try (BufferedReader in = new BufferedReader(new FileReader(new File(filePath)))) {
            String line;
            // 読み込んだ行をシステム標準出力する
            while ((line = in.readLine()) != null)
                System.out.println(line);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
