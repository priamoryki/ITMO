MAINCLASS = src/Main

all:
	javac -d out -encoding utf-8 $(MAINCLASS).java

run:
	@java -cp out $(MAINCLASS)

pack:
	zip A.zip -r Makefile src

clean:
	rm -rf out
