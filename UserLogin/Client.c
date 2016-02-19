#include "Auth.h"

CLIENT *cl;

int main(int argc, char *argv[])
{
	
    int *result;
	int selection;
	char choice;
	
    if (argc < 2)
	{
		printf("USAGE: client <SERVER IP>");
        return 1;
	}
	cl = clnt_create(argv[1], PRINTER, PRINTER_V1, "tcp");
    if (cl == NULL) {
        printf("error: could not connect to server.\n");
        return 1;
    }
	while(1)
	{
	   printFilledLines();
	   printf("Operations:\n");
	   printf("To Register [r/R] \n");
	   printf("To Login [l/L] \n");
	   printFilledLines();
	   choice = getchar();
	   switch(tolower(choice))
	   {
		case 'r': 
			RegisterUser();
			break;
		case 'l':
			Login();
			break;
		default:
			printf("Enter Valid input.");
	   }  
	}
    return 0;
}

void RegisterUser()
{
	struct user *l, new;
	char username[15],password[15];
	int *result;
	char choice;
	printf("Enter UserName : \n");
	scanf("%s", &username);
	printf("Enter Password : \n");
	scanf("%s", &password);
	strcpy(new.id, username);
	strcpy(new.password,password);
    l = &new;
	
	if(isUserAvailable(l)== 1)
	{
		result = insert_user_1(l, cl);
		if (result == NULL) {
			printf("error: RPC failed!\n");
			return 1;
		}
		else
		{
			printf("Registration successful");
			printNewLine();
			printf("To login [y]. To Exit [n]");
			scanf("%s", &choice);
			switch(tolower(choice))
			{
				case 'y':
					Login();
					break;
				case 'n':
					exit(0);
					break;
				default:
					printf("enter y/n");
			}
		}
	}
	else
	{
	    printf("Username already exists. Try a different one \n");
		printNewLine();
		printNewLine();
		RegisterUser();
	}
}

void Login()
{
	struct user *l, new;
	char username[15],password[15];
	int result;
	
	printFilledLines();
    printf("Login Page");
	printNewLine();
	printFilledLines();
	printNewLine();
	
	printf("Enter User Name : \n");
	scanf("%s", &username);
	printf("Enter Password : \n");
	scanf("%s", &password);

	strcpy(new.id, username);
	strcpy(new.password,password);
    l = &new;
	
	result = isAutheticatedUser(l);
	
	if(result == 1)
	{
		printf("welcome %s !", l->id);
		printNewLine();
		printNewLine();
		printNewLine();
		exit(0);
	}
	else if(result == 2)
	{

		printf("Welcome admin! Number of registered users are  %d",getAllUserCount());
		printNewLine();
		printNewLine();
		printNewLine();
		exit(0);
	}
	else
	{
		printf("Invalid user");
		printNewLine();
		printNewLine();
		printNewLine();
		exit(0);
	}
}


int isUserAvailable(user *l)
{
int *result;
result = validate_user_1(l, cl);
		if (result == NULL) {
			printf("error: RPC failed!\n");
			return 1;
		}
  return *result;
}


int isAutheticatedUser(user *l)
{
 int *result = 0;
result = authenticate_user_1(l, cl);
		if (result == NULL) {
			printf("error: RPC failed!\n");
			return 1;
		}
  return *result;
}

int getAllUserCount()
{	
	struct user *l;
    int *result = 0;
	result = count_user_1(l, cl);
			if (result == NULL) {
				printf("error: RPC failed!\n");
				return 1;
			}
	  return *result;
}

void printFilledLines()
{
	printf("====================================================== \n");
}

void printNewLine()
{
	printf("\n");
}