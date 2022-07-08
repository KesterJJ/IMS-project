Coverage: 77.9%
# Inventory Management System

This is a simple CRUD Application used to manage the inventory of a small business. It allows a user to connect to a database through the command line and create, read, update, and delete data. It has 3 types of stored data that can be managed. These are customers, items, and orders. An order includes the customer and the items and the application will automatically calculate the price of the order. Customers, items, and orders can be added, viewed, updated, or deleted one by one or as a group. Customers and items must already exist in the database before they can be added to an order.

## Contents
* [Getting Started](#Getting-Started)
* [Prerequisites](#Prerequisites)
    * [Required dependencies](#Required-Dependencies)
    * [Recommended dependencies](#Recommended-Dependencies)
* [Installing](#Installing)
* [How to use the application](#How-to-use-the-application)
* [Running the tests](#Running-the-tests)
    * [unit tests](#unit-tests)
    * [integration tests](#integration-tests)
    * [And coding style tests](#And-coding-style-tests)
* [Deployment](#Deployment)
* [Built With](#Built-With)
* [Versioning](#Versioning)
* [Authors](#Authors)
* [License](#License)
* [Acknowledgments](#Acknowledgments)

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

#### Required dependencies:

In order to run the application, you need to install MySQL. It will not function without it.

Find the download here: https://dev.mysql.com/downloads/windows/installer/8.0.html
 and instructions to install MySQL here: https://dev.mysql.com/doc/mysql-installation-excerpt/5.7/en/installing.html

#### Recommended dependencies:

Although it is not necessarily required, it is recommended to install the following dependencies in order to avoid any issues with the application:

Java (17 or later): Download:https://www.oracle.com/java/technologies/downloads/#jdk18-windows
                    Install: https://www.java.com/en/download/help/download_options.html

Maven: Download: https://maven.apache.org/download.cgi
       Install: https://maven.apache.org/install.html



### Installing

Once you have installed the dependencies, you can install the application simply by downloading the file located in "target" folder in this repository, called "ims-0.0.1-jar-with-dependencies.jar". Store this anywhere you like in your file system.


## How to use the application

To open the app, open your command line and navigate to the folder containing the file you downloaded for the installation. Then enter the command: java -jar .\ims-0.0.1-jar-with-dependencies.jar
Hit 'Enter'. The application will start in your command line.

When the application is launched, your command-line will ask you which entity to use. Type "customer", "item", or "order" and press 'Enter' to select the entity. Alternatively, you can type "stop" to close the application. If you type anything other than these options, you will be asked to try again, using the same options.

Once you select the entity, the application will ask you whether you want to create a new entity of the selected type, read all entities of the selected type, update an entity of the selected type, or delete an entity of the selected type from the database.
Type "create", "read", "update", or "delete" and hit 'Enter'.

Selecting "read" will return all entities of the selected type.

Selecting "create" will ask for details of the entity you wish to create. Type answers to the prompts and hit enter. An entity of the selected type will be created and added to the database with the parameters you entered.

Selecting "update" will ask for the ID of the entity you wish to update. It is thus a good idea to select "read" first so that you can see the id of the entity you wish to update. Type in the id and hit enter. It will then ask for the updated parameters for the entity with the selected ID. Once you type responses to the promt and hit 'Enter', the entity will be updated in the database.

Selecting "delete" will ask for the ID of the entity you wish to delete. It is thus a good idea to select "read" first so that you can see the id of the entity you wish to delete. Type in the id and hit enter.  The entity will be deleted from the database.

Selecting "Return" at any point will return you to the previous prompt.

For example, if we give these responses to the prompts, in order:

Order [id=null, customerId=3, orderId=1, customerForename=Shirley, customerSurname=Rice, items=[[item id: 2, item name: toothbrush, price: £1.2]]Total cost: £1.2]
Order [id=null, customerId=1, orderId=2, customerForename=jordan, customerSurname=harrison, items=[[item id: 1, item name: apple, price: £0.5]]Total cost: £0.5]
Order [id=null, customerId=2, orderId=3, customerForename=Jack, customerSurname=Smith, items=[[item id: 2, item name: toothbrush, price: £1.2]]Total cost: £1.2]

ORDER
READ

We will see this result, based on the example data already in the database:


## Running the tests

In order to run the tests, you must download the entire repository and then open it in an IDE, such as Eclipse.

You can download Eclipse here: https://www.eclipse.org/downloads/

Once you open the projet in your IDE, you must select the project and check the coverage. In Eclipse, you can do this by right clicking on the project folder in the project explorer. The go down to "Coverage As" and select "Junit test". This will run all tests for the project.

### Unit Tests 

For the unit tests, I have used Junit in order to check that the individual methods are working properly and that they reaturn the expected results. Here is an example of how the tests in this project are created:

![alt text](documentation/unit-test-example.png)

The test code is within the method on the left, called "testReadAll()". It creates an ArrayList, which is what we expect to find when the readAll method is called. It then creates a list of items that would be the items associated with the order and populates it with the same item that is in the test database (Lines59-61). It then populates the first ArrayList with a new order that it creates, which is itself populated with the item list. The result is stored what we expect to see when the "ReadAll()" method on the right is called.
We use assertEquals() to verify that the order that we just created is the same as the order that is created when the "ReadAll()" method is called. If they both match (which they do), the test is passed.


### Integration Tests 
The integration tests are created using Mockito and they are there to make sure that the controllers produce the expected results, regardless of whether or not the other parts of the application are working.

Here is an example of an integration test in this project:


![alt text](documentation/unit-test-example.png)

In this example, the integration test is on the left and the method it is testing is on the right. Lines 35 and 36 are variables needed to create an order. Lines 37-39 are creating a list of items and populating it with one item, which is the same one as in the test database. This is because an order needs to have a list of items with at least one item in it to be initialised. Line 40 creates an order with the created variables, including the item list. This is what we expect to be returned when we create a new order using the method we are testing and given the responses in the next lines of code in the test.

Lines 42 and 43 are refering to the "create()" method on the right. They are making sure that instead of invoking other methods, it will just receive the expected responses when the methods in the brackets are called. This is so we know we are just checking the functionality of this one method and the test will not fail if there is an error in another method. Line 45 actually tests the method, using assertEquals to verify that the expected result is the same as the actual result. Lines 47 and 48 check whether the expected methods are called.

## Deployment

Add additional notes about how to deploy this on a live system

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

## Versioning

We use [SemVer](http://semver.org/) for versioning.

## Authors

* **Chris Perrins** - *Initial work* - [christophperrins](https://github.com/christophperrins)
* **Kester Jones** - *Development* - [kesterjones](https://github.com/KesterJJ)

## License

This project is licensed under the MIT license - see the [LICENSE.md](LICENSE.md) file for details 

*For help in [Choosing a license](https://choosealicense.com/)*

## Acknowledgments

* Chritopher Yiangou - an inspiring hero who taught me everything I needed to know about coding and life.
* Hat tip to anyone whose code was used
* Inspiration
* etc
