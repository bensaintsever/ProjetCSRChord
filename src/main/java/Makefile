#Makefile

JCFLAGS = -g
JC = javac

.SUFFIXES: .java .class
.java.class:
	$(JC) $(JCFLAGS) $*.java

CLASSES = $(wildcard *.java)

all: classes

default: classes

classes: $(CLASSES:.java=.class)

kill_process:
	kill -9 `ps | grep java | head -5c` `ps | grep rmiregistry | head -5c` 

test:
	rmiregistry &
	sleep 1
	java ClientAdmin &
	
clean:
	rm -f *.class