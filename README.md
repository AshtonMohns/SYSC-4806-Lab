# SYSC 4806 - Labs
Ashton Mohns - 101074479

## Accessing Requests - LAB 4
There is an attached Postman collection which can access the different endpoints.
To use these, first run the "Get All Address Books" to get an id of an AddressBook,
then use that as the path param for the following requests.

There is also a very simple UI, as required, which can be viewed through the
endpoint localhost:8080/ui. This will take an ID as parameter (once again, get
this through the Postman request or through the main request at localhost:8080),
then display buddies for that AddressBook.


## Lab 5
I somewhat misunderstood the requirements for lab 5, so I ended up enhancing the
localhost:8080 main page to have a few extra options for adding buddies and creating
new address books, even though these functionalities were already present through
other requests.

These changes were moved to the non-jquery-index.html file due to adding jquery
logic to index.html. If you want to see the updated main page, switch the names of 
those files.

The updated html for this lab can be found in the index.html class.
There are functions to view and create address books and add a buddy.

## Lab 6
https://github.com/AshtonMohns/SYSC-4806-Lab/tree/main
https://app.circleci.com/pipelines/github/AshtonMohns/SYSC-4806-Lab?branch=main&filter=all
https://dashboard.heroku.com/apps/sysc4806lab-ashton/deploy/github
