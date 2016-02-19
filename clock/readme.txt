Lamport Scalar Clock
====================
1. Pre-requisites:

    a. JDK is installed.

    b. Ensure command 'java', 'javac' are available in PATH.
		
		Set JAVA_HOME as follows using syntax
		
		Example: export JAVA_HOME=<path-to-jdk>
		export PATH=$JAVA_HOME/bin:$PATH


2. Run the following sequence of command to get the class files:
    javac *.java

3. Run the below command:
    java LamportClock        (This runs 3 processes, with 5 events by default)
	
	java LamportClock 3 8    (This runs 3 processes, with 8 events)

Sample output:
=================

~\Assignment-DC\clock\src> javac *.java

~\Assignment-DC\clock\src>java LamportClock
Initialized process - P1 (clock: 0)
Initialized process - P2 (clock: 0)
Initialized process - P3 (clock: 0)
P1 : 0 : Local
P3 : 2 : Receive
P2 : 1 : Local
P3 : 3 : Local
P1 : 1 : Local
P2 : 3 : Send
P1 : 4 : Receive
P3 : 4 : Local
P2 : 4 : Local
P3 : 5 : Local
P1 : 6 : Local
P2 : 7 : Receive
P2 : 8 : Local
P1 : 7 : Local
P3 : 7 : Send
P1 : 8 : Local
P2 : 9 : Local
P3 : 8 : Local
P1 : 10 : Local
P3 : 11 : Receive
P2 : 10 : Local
------ END of P2 (clock time: 11) ------
------ END of P1 (clock time: 11) ------
------ END of P3 (clock time: 12) ------

~\Assignment-DC\clock\src>