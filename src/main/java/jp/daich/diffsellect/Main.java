package jp.daich.diffsellect;

import jp.daich.diffsellect.common.exception.DiffSellectException;
import jp.daich.diffsellect.procedure.MainProcedure;

public class Main {

    static class Arguments {

        Arguments(String[] args) {
            if (!isValidArgs(args)) {
                throw new DiffSellectException("Invalid Args : " + args);
            }
            arg0_tableName = args[0];
            arg1_sellectResult = args[1];
        }

        static boolean isValidArgs(String[] args) {
            if (args == null || args.length != 2) {
                return false;
            }
            return true;
        }

        private static String arg0_tableName;
        private static String arg1_sellectResult;
    }

    public static void main(String[] args) {
        System.out.println("!!! Start Main.java !!!");
        Arguments arguments = new Arguments(args);
        new MainProcedure().execute(arguments.arg0_tableName, arguments.arg1_sellectResult);
        System.out.println("!!! End Main.java !!!");
    }
}
