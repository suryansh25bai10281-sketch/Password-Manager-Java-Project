# Password Manager Using Java

## Introduction

The project is a Password Manager which I have built using Java as a console based application where a user can keep track of details of multiple accounts, mainly web and financial accounts.

The aim of the project was to apply the concepts in Java covered in the course. The project involved classes, inheritance, abstraction, interface, exception handling, ArrayList, file handling, and multithreading. The program is mainly for academic purpose and should not be used to store the actual passwords.

## Problem

At times the modern humans need to maintain multiple accounts where remembering all the usernames and passwords can become cumbersome. Moreover, the use of very short passwords is not a good practice. Hence the aim to create a small program which can collectively keep all the account details and also provide a rough password strength assessment for account creation.

The program is not required to be a complete commercial grade password manager, rather a scaled down version to show how the system can be created using Java.

## Features

The application displays a menu on the terminal from where the user can add a web account or a financial account. Web account stores the title, username, password, and website URL, whereas the financial account stores title, username, password, and account number.

The user can view the accounts added. The passwords are masked during display on the screen and the program also shows the security level for each password.

The user can also update a password, and the option to delete an account by the ID is provided. The program checks for duplicate IDs to ensure that two accounts do not hold the same ID. The records can be saved in a text file (vault_records.txt), and the masked password is saved to this file. A small timer is also run using a separate thread for demonstration purposes. The concept of multithreading is demonstrated using a small timer thread.

## Project Files

The Java program is broken into multiple files so that it can be easy to understand. A single Java file with all the classes included would not be easy to understand. `VaultItem` holds the common account details. `WebAccount` and `FinancialAccount` are the two account types.

VaultManager manages the adding, finding and deletion of accounts. `SecurityLevel` covers the three levels of password security. `VaultSecurable` is an interface used for security check and record saving. `WeakPasswordException` is the custom exception for short passwords. `VaultAutoLockService` is one which covers the timer and `PasswordManagerApp` contains the program and the menu.

There is also a test file in the `test` folder which runs a few important functions of the program.

## Requirements

In order to run the project, Java JDK 17 or higher is required. There are no external libraries needed, and the project can be run from the command prompt, power-shell or any other terminal.

## How to Run

First, open the terminal in the project folder and compile all source and test files at once using the following command:

```text
javac -d out src/*.java test/*.java
After compilation, the main program can be started using:
                                                                                           java -cp out PasswordManagerApp                                                         Once the program is started, the main menu is presented, and the user can select the desired option.

To run the automated tests and verify everything is working, use:

java -cp out PasswordManagerTest

## Password Checking

The program does a basic password check, and passwords shorter than 6 characters are rejected during account creation, and when the password is updated.

A password which contains at least 10 characters, a number and one of the supported special characters is considered strong for a web account. A password with at least 8 characters is considered moderate, if it does not meet the strong condition.

The requirements for a strong financial account requires a minimum of 12 characters, number and a supported special character, while at least 8 characters with a number is a moderate password. The special characters checked are `! @ # $ % ^ & `.

The rules are kept simplistic as the main idea of the program is to demonstrate concepts related to Java, rather than a complex password system.

## Java Concepts Used

A number of Java concepts are used in the project. Encapsulation is evident due to account fields being held private. Inheritance relates to the fact that `WebAccount` and `FinancialAccount` both extend `VaultItem` class. The abstract class `VaultItem` is used as a concept of abstraction.

The use of the `VaultSecurable` interface demonstrates how an interface can be utilized for common operations. Polymorphism is used when both type of accounts are stored in a `List` object. The presence of the two `updatePassword()` methods is an example of method overloading. The overriding of methods is used for the security check and display methods used in the children classes.

Exception handling is used for the custom password exception, file and thread related exceptions. `ArrayList` is used to store the accounts during the program, and the file handling is done using Java writer classes, while the timer uses `Runnable` and `Thread`.

## File Storage

The program saves records in `vault_records.txt` file. The file is opened in append mode, thus subsequent save operations can append records to the existing file.

The actual password is not stored, but a masked version is written instead. The fact that the file is not encrypted makes this solution safer than a direct write of the password, but the file is not encrypted anyway.

## Testing

I have tested the important part of the program such as adding accounts, checking password strengths, duplicate IDs handling, finding and deletion of an account. It was also tested that the deleted account cannot be found again.

In addition to the normal tests, password validation was tested with 1,000 randomly generated password inputs having varying lengths and combinations. The random tests were able to pass successfully.

The project was also compiled from the command line in order to make sure it can be run without a dependency on the IDE.

## Limitations

The project has a number of limitations, such as passwords being kept as normal Java `String` values while the program is running and the saved text file is not encrypted. There is no master password in the current version.

The auto-lock is a timer demonstration only, and while the timer finishes it does not actually block the user from using the program. Moreover, the saved records are not loaded automatically on the start of the program. These limitations are acceptable for the academic version of the project.

## Future Scope

The project can be improved by adding a master password and encrypted storage, and a password generator can also be added as well to have users not always need to create the password manually.

Storage in a database and proper session locking can be added, along with a graphical interface. These features were not included in this version as the main idea of the project is to cover the Java concepts covered in the course.

## Conclusion

The Password Manager is a small project which allowed me to understand how various Java concepts can be utilized together in one application. It covers account handling, password checking, file handling, exception handling and basic multithreading.

The current version is simple and mainly serves the learning and project demonstration purpose. It can be extended in the future if more features regarding enhanced security and storage are needed.