#include <stdlib.h>
#include <unistd.h>
#include "Auth.h"

int result;


int *authenticate_user_1_svc(user *lst, struct svc_req *req)
{
	printf("Autheticate user");
	checkExistance();
	result = 0;
	user accounts[10];
    FILE *fp;
    int i=0;   
    int c;
	size_t len = 0;
	char *line = NULL;
	char *username,*password;

    fp=fopen("name_pass.txt", "r");
	while (getline(&line, &len, fp) != -1) 
    { 
		username = strtok(line," ");
		password = strtok(NULL," ");
		if(strcmp(lst->id,username) == 0)
		 {
			printf("success!. %s", username);
			if(strcmp(lst->id,"admin") == 0)
			{
				result = 2;
			}
			else
			{
				result = 1;
			}
		 }
    }
	fclose(fp);
    return &result;
}

int *insert_user_1_svc(user *lst, struct svc_req *req)
{
	printf("Inserting new User");
	checkExistance();
	FILE *fp;
	strcat(lst->id," ");
	strcat(lst->id,lst->password);
	fp=fopen("name_pass.txt", "ab");
	fprintf(fp, lst->id);
	fprintf(fp, "\n");
	fclose(fp);
	result = 0;
	return &result;
}

int *validate_user_1_svc(user *lst, struct svc_req *req)
{
	printf("Validate user id Existance");
	checkExistance();
	FILE *fp;
	size_t len = 0;
	char *line = NULL;
	char *username;
	fp=fopen("name_pass.txt", "r");
	result = 1;
	while (getline(&line, &len, fp) != -1) 
    { 
		username = strtok(line," ");
	if(strcmp(lst->id,username) == 0)
	 {
			result = 0;
			printf("User name already exists! :(");
	 }
    }
	return &result;
}


int *count_user_1_svc(user *lst, struct svc_req *req)
{
	printf("Count the total user");
	checkExistance();
	FILE *fp;
	size_t len = 0;
	char *line = NULL;
	char *username;
	int ch=0;
	int users = 0;
	fp=fopen("name_pass.txt", "r");
	while(!feof(fp))
	{
	  ch = fgetc(fp);
	  if(ch == '\n')
	  {
		users++;
	  }
	}
	return &users;
}

void checkExistance()
{
if( access( "name_pass.txt", F_OK ) != -1 ) {
    printf("DB exists!.");
} else {
	printf("DB file does not existis and creating DB and Inserting Admin Entry !.");
    FILE *fp;
	fp=fopen("name_pass.txt", "ab");
	fputs("admin admin", fp);
	fputs("\n", fp);
    fclose(fp);
}
}
