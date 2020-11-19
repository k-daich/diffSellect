package jp.daich.diffsellect;

import jp.daich.diffsellect.common.exception.DiffSellectException;
import jp.daich.diffsellect.util.ArrayUtils;
import jp.daich.diffsellect.util.StringUtils;
import jp.daich.diffsellect.procedure.MainProcedure;

public class Main {

    static class Arguments {

        Arguments(String[] args) {
            assertValidArgs(args);
            arg_sqlFilePath = args[0];
            arg_outPutFilePath = args[1];
        }

        static void assertValidArgs(String[] args) {
            if (args == null || args.length != 2 || ArrayUtils.isEmpty(args)) {
                throw new DiffSellectException("Invalid Args : " + ArrayUtils.toString(args));
            }
        }

        private static String arg_sqlFilePath;
        private static String arg_outPutFilePath;
    }

    public static void main(String[] args) {
        System.out.println("!!! Start Main.java !!!");
        Arguments arguments = new Arguments(args);
        new MainProcedure().execute(arguments.arg_sqlFilePath, arguments.arg_outPutFilePath);
        System.out.println("!!! End Main.java !!!");
    }
}
