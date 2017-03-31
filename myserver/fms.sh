#!/bin/bash

SERVER_MODULE="."
SHARED_MODULE="shared"

#"com.example" is the package name.
#"Server" would be the class with the main function.
PACKAGE_NAME_TO_MAIN="main.java.server.ServerCommunicator"
PACKAGE_NAME_TO_TEST_DRIVER="test.java.server.TestMain"
LIBS_FOLDER="./lib"

# CODE_LOC="$SERVER_MODULE/src/main/* $SHARED_MODULE/*"
# TEST_CODE_LOC="$SERVER_MODULE/src/test/* $SHARED_MODULE/*"
CODE_LOC="$SERVER_MODULE/src/main/*"
TEST_CODE_LOC="$SERVER_MODULE/src/main/* $SERVER_MODULE/src/test/*"


function remove {
  echo "Removing ./bin folder"
  rm -rf ./bin
}

function compile {
  CODE_DIR_AUTO="$(find $CODE_LOC -name '*.java')"
  mkdir -p bin
  javac -d "./bin" -classpath "./$LIBS_FOLDER/*" $CODE_DIR_AUTO
  if [ $? -eq 0 ]; then
    echo "SERVER COMPILATION SUCCESSFUL!"
  else
    echo "SERVER COMPILATION FAILED!"
  fi;
}

function compileTest {
  mkdir -p bin
  CODE_DIR_AUTO="$(find $TEST_CODE_LOC -name '*.java')"
  javac -d "./bin" -classpath "./$LIBS_FOLDER/*" $CODE_DIR_AUTO
  if [ $? -eq 0 ]; then
    echo "JUnit TEST COMPILATION SUCCESSFUL!"
  else
    echo "JUnit TEST COMPILATION FAILED!"
  fi;
}

if [ "$1" == "compile" ]; then
  remove
  compile
elif [ "$1" == "run" ]; then
  remove
  compile
  java -cp "./bin:./"$LIBS_FOLDER"/*" $PACKAGE_NAME_TO_MAIN $2
elif [ "$1" == "compile-tests" ]; then
  remove
  compileTest
elif [ "$1" == "run-tests" ]; then
  remove
  compileTest
  java -cp "./bin:./"$LIBS_FOLDER"/*" $PACKAGE_NAME_TO_TEST_DRIVER $2 $3
elif [ "$1" == "clean" ]; then
  remove
elif [ "$1" == "-help" ]; then
  echo "USAGE: compile; run <port>; compile-tests; run-tests <host> <port>"
else
  echo "Invalid Command"
  echo "USAGE: compile; run <port>; compile-tests; run-tests <host> <port>"
fi;