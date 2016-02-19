struct user {
 char id[20]; 
 char password[20];
};

program PRINTER {
	version PRINTER_V1 {
		int AUTHENTICATE_USER(user) = 1;
		int INSERT_USER(user) = 2;
		int VALIDATE_USER(user) = 3;
		int COUNT_USER(user) = 4;
	} = 1;
} = 0x2fffffff;
