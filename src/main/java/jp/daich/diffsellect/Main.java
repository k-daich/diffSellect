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
        }

        static void assertValidArgs(String[] args) {
            if (args == null || args.length != 1 || StringUtils.isEmpty(args[0])) {
                throw new DiffSellectException("Invalid Args : " + ArrayUtils.toString(args));
            }
        }

        private static String arg_sqlFilePath;
    }

    public static void main(String[] args) {
        System.out.println("!!! Start Main.java !!!");
        Arguments arguments = new Arguments(args);
        new MainProcedure().execute(arguments.arg_sqlFilePath);
        System.out.println("!!! End Main.java !!!");
    }
}
