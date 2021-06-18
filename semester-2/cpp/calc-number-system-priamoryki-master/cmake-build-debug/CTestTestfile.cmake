# CMake generated Testfile for 
# Source directory: D:/Downloads/calc-number-system-priamoryki-master
# Build directory: D:/Downloads/calc-number-system-priamoryki-master/cmake-build-debug
# 
# This file includes the relevant testing commands required for 
# testing this directory and lists subdirectories to be tested as well.
add_test(tests "D:/Downloads/calc-number-system-priamoryki-master/cmake-build-debug/test/runUnitTests.exe")
set_tests_properties(tests PROPERTIES  _BACKTRACE_TRIPLES "D:/Downloads/calc-number-system-priamoryki-master/CMakeLists.txt;107;add_test;D:/Downloads/calc-number-system-priamoryki-master/CMakeLists.txt;0;")
subdirs("googletest")
subdirs("test")
