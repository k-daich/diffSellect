package jp.daich.diffsellect.poi.controler;

import org.apache.poi.ss.usermodel.Workbook;

public class OpeWorkBookControlerFactory {
    
    public OpeWorkBookControlerFactory() {
    }

    public OpeWorkBookControler create(String bookFilePath) {
        return new OpeWorkBookControler(bookFilePath);
    }
}

