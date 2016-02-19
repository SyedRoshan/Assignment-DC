# DC-RPC - LOGIN 

It is a RPC client server application wriiten in C 
It will let user to register and login to server. 

## ARCHITECTURE: 
```
	| | | | | | |                        | | | | | |
	| SERVER    |  <----- RPC Call --    | CLIENT  | 
	| | | | | | |                        | | | | | |
```

## To build application: 
```
>rpcgen Auth.x 
```
	It will create Auth_xdr, Auth_svc, Auth_clnt files 
```
> make 
```

## To run Application 
### To run server 
```
> ./AppServer 
```

### To run Client
``` 
> ./AppClient 172.16.2.203
```

#### To register user 
```
======================================================
Operations:
To Register [r/R]
To Login [l/L]
======================================================
r
Enter UserName :
demo
Enter Password :
demo
Registration successful
To login [y]. To Exit [n]
n
```
#### To Login 
```

======================================================
Operations:
To Register [r/R]
To Login [l/L]
======================================================
l
======================================================
Login Page
======================================================

Enter User Name :
demo
Enter Password :
demo
welcome demo !
```
#### login Admin 
```
======================================================
Operations:
To Register [r/R]
To Login [l/L]
======================================================
l
======================================================
Login Page
======================================================

Enter User Name :
admin
Enter Password :
admin
Welcome admin! Number of registered users are  1

```

#### Ragister with existing username 
``` 
======================================================
Operations:
To Register [r/R]
To Login [l/L]
======================================================
r
Enter UserName :
demo
Enter Password :
demo
Username already exists. Try a different one


Enter UserName :
demo123
Enter Password :
test
Registration successful
To login [y]. To Exit [n]
n
``` 
#### Invalid user Credentails 
``` 
======================================================
Operations:
To Register [r/R]
To Login [l/L]
======================================================
l
======================================================
Login Page
======================================================

Enter User Name :
dem
Enter Password :
demo
Invalid user
``` 







