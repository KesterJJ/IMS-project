Coverage: 34%
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




```
Give examples
```

### Installing

A step by step series of examples that tell you how to get a development env running

Say what the step will be

```
Give the example
```

And repeat

```
until finished
```

## How to use the application

To open the app, simply double click on the application shortcut. Alternatively, you can use the following command in your command-line:

****************************************COMMAND GOES HERE********************************

When the application is launched, your command-line will ask you which entity to use. Type "customer", "item", or "order" and press 'Enter' to select the entity. Alternatively, you can type "stop" to close the application. If you type anything other than these options, you will be asked to try again, using the same options.

Once you select the entity, the application will ask you whether you want to create a new entity of the selected type, read all entities of the selected type, update an entity of the selected type, or delete an entity of the selected type from the database.
Type "create", "read", "update", or "delete" and hit 'Enter'.

Selecting "read" will return all entities of the selected type.

Selecting "create" will ask for details of the entity you wish to create. Type answers to the prompts and hit enter. An entity of the selected type will be created and added to the database with the parameters you entered.

Selecting "update" will ask for the ID of the entity you wish to update. It is thus a good idea to select "read" first so that you can see the id of the entity you wish to update. Type in the id and hit enter. It will then ask for the updated parameters for the entity with the selected ID. Once you type responses to the promt and hit 'Enter', the entity will be updated in the database.

Selecting "delete" will ask for the ID of the entity you wish to delete. It is thus a good idea to select "read" first so that you can see the id of the entity you wish to update. Type in the id and hit enter. It wil then ask if you are sure you want to delete the entity. Type "y" or "yes" for "yes", or "n", or "no" for "no" and hit 'Enter'.  If you selected "yes", then the entity will be deleted from the database. If you selected "no", then the entity will remain unchanged and you will be taken back to the previous prompt.

Selecting "Return" at any point will return you to the previous prompt.

End with an example of getting some data out of the system or using it for a little demo

## Running the tests

Explain how to run the automated tests for this system. Break down into which tests and what they do

### Unit Tests 

Explain what these tests test, why and how to run them

```
Give an example
```

### Integration Tests 
Explain what these tests test, why and how to run them

```
Give an example
```

### And coding style tests

Explain what these tests test and why

```
Give an example
```

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
