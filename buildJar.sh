#!/bin/bash

cmdLog() {
  echo -e "  # start " $1
}

echo "### start buildJar.sh ###"

ls -la

CMD="javac -sourcepath src -d classes src/main/java/jp/daich/diffsellect/Main.java"
cmdLog "${CMD}"
${CMD}

#CMD="jar cvfm diffSellect.jar -C classes ."
CMD="jar cvfm diffSellect.jar META-INF/MANIFEST.MF -C classes ."

cmdLog "${CMD}"
${CMD}

CMD="java -jar diffSellect.jar"
cmdLog "${CMD}"
${CMD}

ls -la

echo "### ennd buildJar.sh ###"


