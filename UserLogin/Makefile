.SUFFIXES:
.SUFFIXES: .c .o
CLNT = AppClient
SRVR = AppServer
CFLAGS = -g -Wall

SRVR_OBJ = Server.o Auth_xdr.o Auth_svc.o
CLNT_OBJ = Client.o Auth_xdr.o Auth_clnt.o

.c.o:; gcc -c -o $@ $(CFLAGS) $<

default: $(CLNT) $(SRVR)

$(CLNT): $(CLNT_OBJ) Auth.h
	gcc -o $(CLNT) $(CLNT_OBJ)

$(SRVR): $(SRVR_OBJ) Auth.h
	gcc -o $(SRVR) $(SRVR_OBJ)

clean:
	rm *.o $(CLNT) $(SRVR)
	rm -i *~
	rm core
