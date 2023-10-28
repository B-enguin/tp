---
layout: default.md
title: "Developer Guide"
pageNav: 3
---

# HomeBoss Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

---

## **Acknowledgements**

_{ list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the
original source as well }_

---

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

---

## **Design**

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The **_Architecture Diagram_** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of
classes [`Main`](https://github.com/AY2324S1-CS2103T-T13-3/tp/tree/master/src/main/java/seedu/address/Main.java)
and [`MainApp`](https://github.com/AY2324S1-CS2103T-T13-3/tp/tree/master/src/main/java/seedu/address/MainApp.java)) is
in charge of the app launch and shut down.

- At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
- At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

- [**`UI`**](#ui-component): The UI of the App.
- [**`Logic`**](#logic-component): The command executor.
- [**`Model`**](#model-component): Holds the data of the App in memory.
- [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The _Sequence Diagram_ below shows how the components interact with each other for the scenario where the user issues
the command `delete 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

- defines its _API_ in an `interface` with the same name as the Component.
- implements its functionality using a concrete `{Component Name}Manager` class which follows the corresponding
  API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using
the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component
through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the
implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified
in [`Ui.java`](https://github.com/AY2324S1-CS2103T-T13-3/tp/tree/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`
, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures
the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that
are in the `src/main/resources/view` folder. For example, the layout of
the [`MainWindow`](https://github.com/AY2324S1-CS2103T-T13-3/tp/tree/master/src/main/java/seedu/address/ui/MainWindow.java)
is specified
in [`MainWindow.fxml`](https://github.com/AY2324S1-CS2103T-T13-3/tp/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

- executes user commands using the `Logic` component.
- listens for changes to `Model` data so that the UI can be updated with the modified data.
- keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
- depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/AY2324S1-CS2103T-T13-3/tp/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API
call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of
PlantUML, the lifeline reaches the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates
   a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which
   is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a customer).
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:

- When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a
  placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse
  the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as
  a `Command` object.
- All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser`
  interface so that they can be treated similarly where possible e.g, during testing.

### Model component

**API** : [`Model.java`](https://github.com/AY2324S1-CS2103T-T13-3/tp/tree/master/src/main/java/seedu/address/model/Model.java)


<puml src="diagrams/ModelClassDiagram.puml" width="450" />

The `Model` component,

* stores the address book data i.e., all `Customer` objects (which are contained in a `UniqueCustomerList` object). The
  address book is exposed to the outside as a `ReadOnlyBook` objects.
* stores the delivery book data i.e., all `Delivery` objects (which are contained in a `UniqueDeliveryList` object). The
  delivery book is exposed to the outside as a `ReadOnlyBook` objects.
* stores the currently filtered `Customer` objects (e.g., results of a search query) as a separate _filteredCustomers_
  list
* stores the currently filtered `Delivery` objects (e.g., results of a status filter query) as a separate
  _filteredDeliveries_ list
* stores the currently sorted `Delivery` objects (e.g., results of a sort query) as a separate _sortedDeliveries_
  list
* stores an unmodifiable `ObservableList<ListItem>` that can be 'observed' e.g. the UI can be bound
  to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as
  a `ReadOnlyUserPref` objects.

#### User Model

<puml src="diagrams/UserClassDiagram.puml" width="450" />

The `User` model,

* stores the user data i.e, the username and password of the user.

#### Delivery Model

<puml src="diagrams/DeliveryClassDiagram.puml" width="450" />

The `Delivery` model,

* stores the delivery data i.e, the delivery name, customer, delivery status, order date, delivery date and note for the
  delivery.

#### Customer Model

<puml src="diagrams/CustomerClassDiagram.puml" width="450" />

The `Customer` model,

* stores the customer data i.e, the customer address, phone, email and address.

### Storage component

**API** : [`Storage.java`](https://github.com/AY2324S1-CS2103T-T13-3/tp/tree/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,

* can save user preference data, address book data and delivery book data in JSON format,
* and read them back into corresponding objects.
* inherits from  `UserPrefStorage`
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects
  that belong to the `Model`)

The concrete implementation of storage is done through `StorageManger`, which holds an instance of `UserPrefsStorage`,
`BookStorage` and `BookStorageWithReference`. Which represents the User Preference Data, Address Book and Delivery Book
respectively.

### Common classes

Classes used by multiple components are in the `seedu.addressbook.commons` package.

---

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

- [Update Delivery Status](#update-delivery-status-feature)
- [Create Delivery Note](#create-note-for-delivery-feature)
- [User Register Account Command](#user-register-account-command)
- [User Login](#user-login-command)
- User Update Details
- [User Logout](#user-logout-command)
- User Account Recovery
- User Account Deletion
- [Add Customer](#add-customer-command)

### Update Delivery Status Feature

#### Overview

The `delivery status` command is used to update the `DeliveryStatus` of a selected delivery with the new status
specified by the user.

The format of the `delivery status` command can be found
[here](./UserGuide.md#update-delivery-status)

#### Feature Details

1. The user will specify a `Delivery` through its `id`. The user must specify a `DeliveryStatus` to replace the
   current status of the selected delivery
2. If a number followed by a string representing a status is not specified, or is in the incorrect format,
   an `ParseException` will be thrown.
3. If the command is parsed successfully, a new `DeliveryStatusCommand` is created, and executed
4. If the user is not logged in during command execution, a `CommandException` will be thrown.
5. The number provided is used to fetch the associated `Delivery` from `Model` if it exists. If the provided number
   does not match any of the IDs of the `Delivery` stored in `Model`, a `CommandException` is thrown.
6. If the command completes successfully, the selected `Delivery` will be replaced by an identical `Delivery` except
   for an updated `DeliveryStatus`.

The following activity diagram illustrates the logic of updating the `DeliveryStatus` of a `Delivery`.

<puml src="diagrams/DeliveryStatusCommandActivityDiagram.puml" width="450" />

The sequence of the `delivery status` command is as follows:

1. The command `delivery status DELIVERY_ID STATUS` is entered by the user (e.g. `delivery status 1 completed`)
2. `LogicManager` calls the `AddressBookParser#parseCommand()` with `delivery status DELIVERY_ID STATUS`
3. `AddressBookParser` will parse the command, and creates a new instance of `DeliveryStatusCommandParser` calling
   `DeliveryStatusCommandParser#parse()` to parse the remaining input after the command word has been removed
   (i.e. the command arguments)
4. `DeliveryStatusCommandParser` will parse the arguments, and return a new instance of `DeliveryStatusCommand` with
   the parsed `DELIVERY_ID` and `STATUS` fields
5. `LogicManager` calls `DeliveryStatusCommand#execute()`, first checking if the user is logged in by calling
   `Model#getUserLoginStatus()`
6. It then attempts to fetch the `Delivery` with the specified `DELIVERY_ID`, and replaces that `Delivery`
   using `Model#setDelivery()` with a newly created `Delivery` with identical fields except for its status which is
   the updated `DeliveryStatus`
7. It creates and returns a new `CommandResult` with the result of the execution

The following sequence diagram illustrates the `delivery status` command sequence:

<puml src="diagrams/DeliveryStatusCommandSequenceDiagram.puml" width="450" />

This section describes some noteworthy details on how certain features are implemented.

- [Create Delivery Note](#create-note-for-delivery-feature)
- [User Register Account Command](#user-register-account-command)
- [User Login Command](#user-login-command)
- User Update Details Command
- [User Logout Command](#user-logout-command)
- User Account Recovery
- User Account Deletion
- Customer Edit Command
- Delivery Add Command

### Create Note for Delivery Feature

#### Overview

The `delivery note` command is used to create a new `Note` a selected delivery with the new note
specified by the user

The format of the `delivery note` command can be found
[here](./UserGuide.md#create-a-note-for-a-delivery)

#### Feature Details

1. The user will specify a `Delivery` through its `id`. The user must specify a non-empty `Note` to replace the
   current status of the selected delivery.
2. If a number followed by the `Note` prefix followed by a non-empty string is not specified, or is in the incorrect
   format, a `ParseException` will be thrown.
3. If the command is parsed successfully, a new `DeliveryCreateNoteCommand` is created, and executed.
4. If the user is not logged in during command execution, a `CommandException` will be thrown.
5. The number provided is used to fetch the associated `Delivery` from `Model` if it exists. If the provided
   number does not match any of the IDs of the `Delivery`s stored in `Model`, a `CommandException` is thrown.
6. If the command completes successfully, the selected `Delivery` will be replaced by an identical `Delivery`
   (i.e. all field are the same) except for a modified `Note`.

The following activity diagram illustrates the logic of creating a `Note` for a `Delivery`

<puml src="diagrams/DeliveryCreateNoteActivityDiagram.puml" width="450" />

The sequence of the `delivery note` command is as follows:

1. The command `delivery note DELIVERY_ID --note NOTE` is entered by the user
   (e.g. `delivery note 1 --note This is a note`)
2. The `LogicManager` calls the `AdressBookParser#parseCommand()` with `delivery note DELIVERY_ID --note NOTE`
3. `AddressBookParser` will parse the command, and creates a new instance of `DeliveryCreateNoteCommandParser` calling
   `DeliveryCreateNoteCommandParser#parse()` to parse the remaining input after the command word has been removed
   (i.e. the command arguments)
4. `DeliveryCreateNoteCommandParser` will parse the arguments, and return a new instance of `DeliveryCreateNoteCommand`
   with the parsed `DELIVERY_ID` and `Note`
5. `LogicManager` calls `DeliveryCreateNoteCommand#execute()`, first checking if the user is logged in by calling
   `Model#getUserLoginStatus()`
6. It then attempts to fetch the `Delivery` with the specified `DELIVERY_ID`, and replaces that `Delivery` using
   `Model#setDelivery()` with a newly created `Delivery` with identical fields except for its note which is
   the updated `Note`
7. It creates and returns a new `CommandResult` with the result of the execution

If the specified `Delivery` already has an existing `Note`, it will be overridden by the new `Note` supplied if the
command executes successfully

The following diagram illustrates the `delivery note` command sequence:

<puml src="diagrams/DeliveryCreateNoteSequenceDiagram.puml" width="450" />

### User Register Account Command

**Overview:**
The `register` command is used to register a new user account.
Once registered, the user will be able to log in to the account and access all the commands available.

The format for the `register` command can be found [here](UserGuide.md#register).

**Feature details:**

1. The user specifies the `Username`, `Password`, `Confirm Password`, `Forget Password Question`
   and `Forget Password Answer` in the `register` command.
2. If any of the fields is not provided, an error message with the correct command usage will be shown.
3. If invalid command parameters are provided, an error message with the correct parameter format will be shown.
4. If the `Password` and `Confirm Password` fields do not match, an error message will be shown.
5. If the user is currently logged in, an error message will be shown.
6. The `User` is then cross-referenced with the stored user in `Model` to check if there's already a user stored. If
   there's already a user stored, an error message will be shown.
7. If all the previous steps are completed without exceptions, the user will be registered and the `User` will be stored
   in `Model`. The `isLoggedIn` status in `Model` will be updated to `true`.

The following activity diagram shows the logic of a user registering an account:

<puml src="diagrams/UserRegisterActivityDiagram.puml" alt="UserRegisterActivityDiagram" />

The sequence of the `register` command is as follows:

1. The command `register INPUT` is entered by the user (e.g., register --user gab --password pass1234 --confirmPass
   pass1234 --secretQn First Pet? --answer Koko).
2. Logic Manager calls the `AddressBookParser#parseCommand` with the `INPUT`.
3. The `AddressBookParser` parses the command word, creating an instance of `UserRegisterCommandParser` to parse the
   rest of the command.
4. If all fields are present, it checks if password and confirm password match.
5. If password and confirm password match, it creates an instance of `UserRegisterCommand`.
6. `Logic Manager` executes `UserRegisterCommand` by calling `UserRegisterCommand#execute()`.
7. `UserRegisterCommand` checks if a user is already registered by calling `Model#getStoredUser`.
8. If no user is registered, `UserRegisterCommand` calls `Model#registerUser` to store the user. Login status is set to
   true.
9. `UserRegisterCommand` calls `Model#updateFilteredPersonList` to display the list of customers.
10. `UserRegisterCommand` returns a `CommandResult` with a success message.

The following sequence diagram shows how the `register` command works:

<puml src="diagrams/UserRegisterSequenceDiagram.puml" alt="UserRegisterSequenceDiagram" />

### List Delivery Feature

### Overview

The `delivery list` command is used to list all deliveries in the delivery book.

The format of the `delivery list` command can be found
[here](./UserGuide.md#view-all-deliveries)

### Feature Details

1. The user enters the `delivery list` command.
2. If the user enters no arguments, the `delivery list` command will list all
   deliveries in the delivery book.
3. If the user enters an invalid or empty status that is prefixed by `--status`, a `ParseException` will be thrown.
4. If the user enters an invalid or empty sort that is prefixed by `--sort`, a `ParseException` will be thrown.
5. If the command is parsed successfully, a `DeliveryListCommand` object will be created, and executed.
6. If the user is not logged in, a `CommandException` will be thrown.
7. If the status was provided, the status is used to filter the current delivery list with the specified status.
8. If the customer id was provided, the customer id is used to filter the current delivery list with the specified
   customer id.
9. If the delivery date was provided, the delivery date is used to filter the current delivery list with the specified
   delivery date.
10. If the sort was provided, the sort is used to sort the current delivery list. By default, the deliveries will be
    sorted in descending order of their delivery date.
11. The list on the ui will be updated with the filtered and sorted deliveries.
12. If the command completed successfully, a `CommandResult` object will be created, and returned.

The following activity diagram illustrates the logic for listing `Delivery`

<puml src="diagrams/implementation/delivery/DeliveryListActivityDiagram.puml" width="450"> </puml>

The sequence of the `delivery list` command is as follows:

1. The command `delivery list --status STATUS --sort SORT` is entered by the user
   (e.g. `delivery list --status created --sort ASC`)
2. The `LogicManager` calls the `AdressBookParser#parseCommand()` with `delivery list --status STATUS --sort SORT`
3. `AddressBookParser` will parse the command, and creates a new instance of `DeliveryListCommandParser` calling
   `DeliveryListCommandParser#parse()` to parse the remaining input after the command word has been removed
   (i.e. the command arguments)
4. `DeliveryListCommandParser` will parse the arguments, and return a new instance of `DeliveryListCommand`
   with the parsed `STATUS` and `SORT`
5. `LogicManager` calls `DeliveryListCommand#execute()`, first checking if the user is logged in by calling
   `Model#getUserLoginStatus()`
6. If status is not null, `DeliveryListCommand` will call `Model#updateFilteredDeliveryListByStatus(Predicate)` to
   filter the
   delivery list by the specified status.
7. If delivery date is not null, `DeliveryListCommand` will call `Model#updateFilteredDeliveryListByStatus(Predicate)`
   to filter the delivery list by the specified date.
8. If customer id is not null, `DeliveryListCommand` will call `Model#updateFilteredDeliveryListByStatus(Predicate)`
   to filter the delivery list by the specified customer id.
9. If the sort is `asc`, `DeliveryListCommand` will call `Model#sortFilteredDeliveryList(Comparator)` to sort the
   delivery list by delivery date in descending order.
10. Else, `DeliveryListCommand` will call `Model#sortFilteredDeliveryList()` to sort the delivery list by delivery
    date in descending order.
11. It creates a new "CommandResult" with the result of the execution.

The default delivery sort is `asc`.

The following sequence diagram illustrates the `delivery list` command sequence:

### View Delivery Feature

#### Overview

The `delivery view` command is used to view a selected delivery with the id specified by the user.

The format of the `delivery view` command can be found
[here](./UserGuide.md#view-details-of-deliveries)

#### Feature Details

1. The user will specify a `Delivery` through its `id`.
2. If a number is not specified, or is in the incorrect format, an `ParseException` will be thrown.
3. If the command is parsed successfully, a new `DeliveryViewCommand` is created, and executed
4. If the user is not logged in during command execution, a `CommandException` will be thrown.
5. The number provided is used to fetch the associated `Delivery` from `Model` if it exists. If the provided number does
   not match any of the IDs of the `Delivery` stored in `Model`, a `CommandException` is thrown.
6. It creates and returns a new `CommandResult` with the result of the execution.

The following activity diagram illustrates the logic of viewing a `Delivery`.

<puml src="diagrams/implementations/DeliveryViewActivityDiagram.puml" width="450" />

The sequence of the `delivery view` command is as follows:

1. The command `delivery view DELIVERY_ID` is entered by the user (e.g. `delivery view 1`)
2. `LogicManager` calls the `AddressBookParser#parseCommand()` with `delivery view 1`
3. `AddressBookParser` will parse the command, and creates a new instance of `DeliveryViewCommandParser` calling
   `DeliveryViewCommandParser#parse()` to parse the remaining input after the command word has been removed
   (i.e. the command arguments)
4. `DeliveryViewCommandParser` will parse the arguments, and return a new instance of `DeliveryViewCommand` with
   the parsed `DELIVERY_ID`
5. `LogicManager` calls `Delivery#execute()`, first checking if the user is logged in by calling
   `Model#getUserLoginStatus()`
6. It then attempts to fetch the `Delivery` with the specified `DELIVERY_ID`, using `Model#getDelivery(DELIVERY_ID)`
   and returns the delivery.
7. It creates and returns a new `CommandResult` with the result of the execution

The following sequence diagram illustrates the `delivery view` command sequence:

<puml src="diagrams/implementations/DeliveryViewCommandSequenceDiagram.puml" width="450" />

### User Login Command

**Overview:**

The `login` command is used to log in to the user's account.
Once logged in, the user will have access to all the commands available.

The format for the `login` command can be found [here](UserGuide.md#login).

**Feature details:**

1. The user specifies the `Username` and `Password` in the `login` command.
2. If any of the fields is not provided, an error message with the correct command usage will be shown.
3. If invalid command parameters are provided, an error message with the correct parameter format will be shown.
4. If the user is currently logged in, an error message will be shown.
5. The `User` is then cross-referenced with the stored user in `Model` to check if the credentials match.
   If incorrect credentials are provided, an error message regarding wrong credentials will be shown.
6. If all the previous steps are completed without exceptions, the user will be logged in and the
   `isLoggedIn` status in `Model` will be updated to `true`.

The following activity diagram shows the logic of a user logging in:

<puml src="diagrams/UserLoginActivityDiagram.puml" alt="UserLoginActivityDiagram" />

The sequence of the `login` command is as follows:

1. Upon launching the application, the `ModelManager` will be initialized with
   the `User` constructed with details from the authentication.json file.
2. The user inputs the `login` command with the username and password.
3. The `userLoginCommandParser` checks whether all the required fields are present.
   If all fields are present, it creates a new `userLoginCommand`.
4. The `userLoginCommand` checks whether the user is currently logged in by calling `Model#getUserLoginStatus()`.
5. The `userLoginCommand` then checks if the user credentials match the stored user by calling `Model#userMatches()`.
6. If the user is not logged in and the credentials match, the `userLoginCommand` calls `Model#setLoginSuccess()`,
   changing the login status to true and enabling the user access to all commands.
7. The `userLoginCommand` also calls `Model#updateFilteredPersonList()` to display the list of customers.

The following sequence diagram shows how the `login` command works:

<puml src="diagrams/UserLoginSequenceDiagram.puml" alt="UserLoginSequenceDiagram" />

### User Logout Command

**Overview:**

The `logout` command is used to log out from the user's account.
Once logged out, the user will have no access to all the commands available, except for `help`, `exit`,
`register`, `login`, `recover` and `delete account`.

The format for the `logout` command can be found [here](UserGuide.md#logout).

**Feature details:**

1. The user executes the `logout` command.
2. If extra command parameters are provided after specifying `logout`, the logout command will still be executed.
3. If the user is currently logged out, an error message will be shown.
4. If all the previous steps are completed without exceptions, the user will be logged out and the
   `isLoggedIn` status in `Model` will be updated to `false`.

The following activity diagram shows the logic of a user logging out:

<puml src="diagrams/UserLogoutActivityDiagram.puml" alt="UserLogoutActivityDiagram" />

The sequence of the `logout` command is as follows:

1. The user inputs the `logout` command.
2. A new `userLogoutCommand` is created and checks whether the user is currently logged out
   by calling `Model#getUserLoginStatus()`.
3. If the user is currently logged in, the `userLogoutCommand` calls `Model#setLogoutSuccess()`,
   changing the login status to false and restricting the user access to most commands.
4. The `userLoginCommand` also calls `Model#updateFilteredPersonList()` to hide the list of customers.

The following sequence diagram shows how the `login` command works:

<puml src="diagrams/UserLogoutSequenceDiagram.puml" alt="UserLogoutSequenceDiagram" />

### Add Customer Command

**Overview:**

The `customer add` command is used to create a new customer with information fields `Name`, `Phone`, `Email` and
`Address`. A unique `ID` will be assigned to the customer upon creation.

The format for the `customer add` command can be found [here](UserGuide.md#add-a-customer).

**Feature details:**

1. The user executes the `customer add` command.
2. If any of the fields is not provided, an error message with the correct command usage will be shown.
3. If invalid command parameters are provided, an error message with the correct parameter format will be shown.
4. If the user is currently not logged in, an error message will be shown.
5. The `Customer` is then cross-referenced in the `Model` to check if a customer with the same `Name` already exists.
   If a customer with the same `Name` exists, an error message will be shown.
6. If all the previous steps are completed without exceptions, the new `Customer` will be successfully added to the
   database.

The following activity diagram shows the logic of adding a `Customer` into the database:

<puml src="diagrams/CustomerAddActivityDiagram.puml" alt="CustomerAddActivityDiagram" />

The sequence of the `customer add` command is as follows:

1. The user inputs the `customer add ARG` command (e.g. `customer add --name Gabriel --phone 87654321
   --email gabrielrocks@gmail.com --address RVRC Block B`).
2. The `LogicManager` calls the `AddressBookParser#parseCommand` with `ARG` to parse the command.
3. The `AddressBookParser` then creates a new `CustomerAddCommandParser` to parse the fields provided by the user.
4. A corresponding `Customer` is created by the `CustomerAddCommandParser`, which is used to
   create a new `CustomerAddCommand`.
5. The `CustomerAddCommand` checks whether the user is currently logged in by calling `Model#getUserLoginStatus()`.
6. The `CustomerAddCommand` then checks if the `Model` contains a customer with the same `Name`
   by calling `Model#hasPerson`.
7. If the user is logged in and the `Model` does not contain a customer with the same `Name`, the `CustomerAddCommand`
   calls `Model#addPerson` to add the new `Customer` to the database.

The following sequence diagram shows how the `login` command works:

<puml src="diagrams/CustomerAddSequenceDiagram.puml" alt="CustomerAddSequenceDiagram" />

### Customer Edit Command

**Overview:**

The `customer edit` command is used to edit an existing Customer with at least one of the information fields
specified by the user, namely the customer's `Name`, `Phone`, `Email` or/and `Address`.

The format for the `customer edit` command can be found [here](UserGuide.md#update-customer-details).

**Feature details:**

1. The user specifies the customer id of the `Customer` to be edited, followed by at least one of the information
fields to be edited,`Name`, `Phone`, `Email` or/and `Address`.
e.g.(`customer edit 1 --name John --phone 92149601`)
2. If no fields are provided, an error message will prompt the user to key in at least one of the fields.
3. If the customer id provided is negative or zero, an error message will prompt the user to key in an unsigned
positive integer.
4. The customer id provided is then cross-referenced with the stored customer list in `Model` to ensure that
it corresponds to an existing `Customer`. If the customer id is not tied to any `Customer`, an error message will
inform the user that that is the case.
5. If the details provided exactly match the details of the `Customer` that was specified, an error message will inform
   the user that the customer already exists in the address book.
6. If all the previous steps are completed without exceptions, the fields of the `Customer` that was specified will
be updated with the new information provided by the user.

The following activity diagram shows the logic of a user editing a customer's information:

<puml src="diagrams/CustomerEditActivityDiagram.puml" alt="CustomerEditActivityDiagram" />

The sequence of the `customer edit` command is as follows:

1. The user inputs the `customer edit` command with `input` as the customer id and `Name`, `Phone`, `Email` and/or
`Address` as the fields to be edited. e.g.(`customer edit 1 --name John --phone 92149601)
2. The `LogicManager` calls `AddressBookParser#parseCommand` to create its corresponding CommandParser.
3. In this case, the `AddressBookParser` creates an instance of `CustomerEditCommandParser` and calls
`CustomerEditCommandParser#parse` to parse the given `input` using various parse methods from `ParserUtil` and
   creates a `CustomerEditDescriptor` object.
4. The `CustomerEditCommandParser` then creates a `CustomerEditCommand` object. The `CustomerEditCommand` object
takes in the `CustomerEditDescriptor` instance and the customer id from Step 1 as a parameter.
5. The `CustomerEditCommand` is then returned to the `LogicManager` where its execute method is called. This creates
a `Customer` object by calling `CustomerEditCommand#createEditedCustomer`. Also, it edits the `Customer` with the
   customer id input in Step 1. This is done by calling `Model#setCustomer`.
6. With the `Customer` specified edited, a `CommandResult` with a success message is then returned.

The following sequence diagram shows how the `customer edit` command works:

<puml src="diagrams/CustomerEditSequenceDiagram.puml" alt="CustomerEditSequenceDiagram" />

### Delivery Add Command

**Overview:**

The `delivery add` command is used to add a new Delivery with all the given information fields
specified by the user, namely the delivery's `DeliveryName`, customer id of a `Customer` and `DeliveryDate`. All 
fields are compulsory.

The format for the `delivery add` command can be found [here](UserGuide.md#create-delivery).

**Feature details:**

1. The user inputs `delivery add`, followed by the `DeliveryName`, customer id of a `Customer` and `DeliveryDate`.
e.g.(delivery add --name Chocolate Cake --customer 1 --date 2024-10-10)
2. If no fields or incorrect fields are provided, an error message will inform the user of the correct command usage.
3. If the delivery date provided is before today's date, an error message will prompt the user to key in a date that
   is today or after today.
4. The customer id provided is then cross-referenced with the stored customer list in `Model` to ensure that
   it corresponds to an existing `Customer`. If the customer id is not tied to any `Customer`, an error message will
   inform the user that that is the case.
5. If all the previous steps are completed without exceptions, a new `Delivery` will be added with the
   information provided by the user and added to the `Model`.

The following activity diagram shows the logic of a user adding a delivery:

<puml src="diagrams/DeliveryAddActivityDiagram.puml" alt="DeliveryAddActivityDiagram" />

The sequence of the `delivery add` command is as follows:

1. The user inputs the `delivery add` command with `input` as the `DeliveryName`, customer id of a `Customer` and
`DeliveryDate`.
e.g.(`delivery add --name Chocolate Cake --customer 1 --date 2024-10-10`)
2. The `LogicManager` calls `AddressBookParser#parseCommand` to create its corresponding CommandParser.
3. In this case, the `AddressBookParser` creates an instance of `DeliveryAddCommandParser` and calls
   `DeliveryAddCommandParser#parse` to parse the given `input` using various parse methods from `ParserUtil` and
   creates a `DeliveryAddCommand` object.
4. The `DeliveryAddCommandParser` then creates a `DeliveryAddCommand` object.
   The `DeliveryAddCommand` object takes in the `DeliveryAddDescriptor` instance as a parameter.
5. The `DeliveryAddCommand` is then returned to the `LogicManager` where its execute method is called. This creates
   a `Delivery` object by calling `DeliveryAddCommand#createDelivery`. Also, it adds this new `Delivery` with the
   details input by the user in Step 1 to the `Model`. This is done by calling `Model#addDelivery`.
6. With the `Delivery` created with the details specified, a `CommandResult` with a success message is then returned.

The following sequence diagram shows how the `delivery add` command works:

<puml src="diagrams/DeliveryAddSequenceDiagram.puml" alt="DeliveryAddSequenceDiagram" />

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo
history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the
following operations:

- `VersionedAddressBook#commit()`— Saves the current address book state in its history.
- `VersionedAddressBook#undo()`— Restores the previous address book state from its history.
- `VersionedAddressBook#redo()`— Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()`
and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the
initial address book state, and the `currentStatePointer` pointing to that single address book state.

<puml src="diagrams/UndoRedoState0.puml" alt="UndoRedoState0" />

Step 2. The user executes `delete 5` command to delete the 5th customer in the address book. The `delete` command
calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes
to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book
state.

<puml src="diagrams/UndoRedoState1.puml" alt="UndoRedoState1" />

Step 3. The user executes `add n/David …​` to add a new customer. The `add` command also
calls `Model#commitAddressBook()`
, causing another modified address book state to be saved into the `addressBookStateList`.

<puml src="diagrams/UndoRedoState2.puml" alt="UndoRedoState2" />

<box type="info" seamless>

**Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will
not be saved into the `addressBookStateList`.

</box>

Step 4. The user now decides that adding the customer was a mistake, and decides to undo that action by executing
the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer`
once to the left, pointing it to the previous address book state, and restores the address book to that state.

<puml src="diagrams/UndoRedoState3.puml" alt="UndoRedoState3" />

<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no
previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the
case. If so, it will return an error to the user rather
than attempting to perform the undo.

</box>

The following sequence diagram shows how the undo operation works:

<puml src="diagrams/UndoSequenceDiagram.puml" alt="UndoSequenceDiagram" />

<box type="info" seamless>

**Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the
lifeline reaches the end of diagram.

</box>

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once
to the right, pointing to the previously undone state, and restores the address book to that state.

<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address
book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()`
to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</box>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such
as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`.
Thus, the `addressBookStateList` remains unchanged.

<puml src="diagrams/UndoRedoState4.puml" alt="UndoRedoState4" />

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not
pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be
purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern
desktop applications follow.

<puml src="diagrams/UndoRedoState5.puml" alt="UndoRedoState5" />

The following activity diagram summarizes what happens when a user executes a new command:

<puml src="diagrams/CommitActivityDiagram.puml" width="250" />

#### Design considerations:

**Aspect: How undo & redo executes:**

- **Alternative 1 (current choice):** Saves the entire address book.

    - Pros: Easy to implement.
    - Cons: May have performance issues in terms of memory usage.

- **Alternative 2:** Individual command knows how to undo/redo by
  itself.
    - Pros: Will use less memory (e.g. for `delete`, just save the customer being deleted).
    - Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_

### \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_

---

## **Documentation, logging, testing, configuration, dev-ops**

- [Documentation guide](Documentation.md)
- [Testing guide](Testing.md)
- [Logging guide](Logging.md)
- [Configuration guide](Configuration.md)
- [DevOps guide](DevOps.md)

---

## **Appendix: Requirements**

### Product scope

**Target user profile**:

- has a home business
- want to oversee customers in an organised manner
- want to manage deliveries efficiently and effectively
- prefer desktop apps over other types
- can type fast
- prefers typing to mouse interactions
- is reasonably comfortable using CLI apps

**Value proposition**:
Home-based business owners can have a huge base of customers.
HomeBoss streamlines and simplifies the management of customer contacts and deliveries,
thereby improving efficiency for business owners.

### User stories

Priorities: High (must have) - `***`, Medium (nice to have) - `**`, Low (unlikely to have) - `*`

| Priority | As a …​            | I want to …​                                                                  | So that I can…​                                                                                                                                                                         |
|----------|--------------------|-------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `***`    | an owner           | create a local account                                                        | I can personalise and secure my account.                                                                                                                                                |
| `***`    | a registered owner | log in to my local account                                                    | I can access my data.                                                                                                                                                                   |
| `***`    | a forgetful owner  | retrieve my account                                                           | I can still recover my data.                                                                                                                                                            |
| `***`    | a logged-in owner  | log out of my account                                                         | I can keep my data secure.                                                                                                                                                              |
| `***`    | a registered owner | update my password                                                            | I can constantly keep my data secure.                                                                                                                                                   |
| `***`    | a registered owner | delete my account                                                             | I can have greater control over my data and account removal for privacy reasons.                                                                                                        |
| `***`    | a registered owner | update my details                                                             | I can change my personalisation.                                                                                                                                                        |
| `***`    | a registered owner | create a customer                                                             | I can tie deliveries to customers’ information.                                                                                                                                         |
| `***`    | a registered owner | view a customer                                                               | I can see their detailed information.                                                                                                                                                   |
| `***`    | a registered owner | update a customer                                                             | I can change details if keyed in wrongly.                                                                                                                                               |
| `***`    | a registered owner | delete a customer                                                             | I can remove redundant or incorrect customer records, especially when unforeseen errors occur.                                                                                          |
| `***`    | a registered owner | view a list of customers                                                      | I can have a comprehensive overview of my customer base.                                                                                                                                |
| `***`    | a registered owner | see a list of deliveries sorted by status for the customer                    | I can easily see if products are delivered or not.                                                                                                                                      |
| `***`    | a registered owner | quickly search for the details of a client                                    | I can monitor the progress of an order efficiently and effectively.                                                                                                                     |
| `***`    | a registered owner | create a delivery                                                             | I can efficiently organise and access delivery information.                                                                                                                             |
| `***`    | a registered owner | create notes about deliveries                                                 | I can add additional information about deliveries.                                                                                                                                      |
| `***`    | a registered owner | view a list of deliveries                                                     | I can plan the optimal route.                                                                                                                                                           |
| `***`    | a registered owner | see the list of deliveries that would be delivered for the day                | I can prioritise particular orders.                                                                                                                                                     |
| `***`    | a registered owner | add a customer to a delivery                                                  | I know who the delivery is for.                                                                                                                                                         |
| `***`    | a registered owner | remove a customer from a delivery                                             | The delivery details are updated.                                                                                                                                                       |
| `***`    | a registered owner | specify the method of delivery                                                | I know how to send the orders over.                                                                                                                                                     |
| `***`    | a registered owner | quickly search for the details of a delivery                                  | I can monitor the progress of a                                                                 delivery.                                                                               |
| `***`    | a registered owner | see a list of deliveries sorted by their delivery status and date of delivery | It is                                                                                                     more organised and easier for me to get and overview of all orders.           |
| `***`    | a registered owner | see the location of the delivery                                              | I know where to deliver the order to.                                                                                                                                                   |
| `***`    | a registered owner | view the details of a delivery                                                | I know what the order is and where to deliver it to.                                                                                                                                    |
| `***`    | a registered owner | update the status of the delivery                                             | I can keep track of the delivery progress and notify my client.                                                                                                                         |
| `***`    | a registered owner | update delivery details                                                       | I can change any information if there was an error from                                                                                                                        user/me. |
| `***`    | a registered owner | delete a delivery                                                             | I can get rid of deliveries that are redundant.                                                                                                                                         |
| `*`      | a registered owner | relate my inventory to my orders                                              | I can keep track of my inventory.                                                                                                                                                       |
| `*`      | a registered owner | know the sum of all the materials required for a fixed delivery schedule      | I can plan my                                                                                             inventory.                                                                    |
| `*`      | a registered owner | have different user authorisation levels                                      | I can control who has access to what.                                                                                                                                                   |

*{More to be added}*

### Use cases

(For all use cases below, the **System** is the `HomeBoss` and the **Actor** is the `user`, unless specified
otherwise)

#### Use Case: UC01 - Register an Account

**System:** User System (US)

**Actor:** Unregistered owner

**Preconditions:** User system has no account.

**Guarantees:**

- Account is created if the command is executed successfully.
- Unregistered owner is registered and logged in if the command is executed successfully.

**MSS:**

1. Unregistered owner opens HomeBoss application.
2. Unregistered owner enters register command with his username, password, confirm password, a "forget password"
   question and answer.
3. US creates an account and shows a welcome message with the newly created username.
   Use case ends.

**Extensions:**

* 2a. Unregistered owner does not enter one of the fields.

    * 2a1. US requests unregistered owner to fill up all the required fields.

      Use case ends.

* 2b. Unregistered owner types incorrect confirm password.

    * 2b1. US requests unregistered owner to retype their confirm password.

      Use case ends.

---

#### Use Case: UC02 - Login

**System:** User System (US)

**Actor:** Registered owner

**Preconditions:** Registered owner is logged out.

**Guarantees:**

- Registered owner is logged in.

**MSS:**

1. Registered owner opens the HomeBoss application.
2. Registered owner enters the login command with his username, password.
3. US logs in and shows a welcome message.
   Use case ends.

**Extensions:**

* 2a. Registered owner does not enter one of the fields
    * 2a1. US requests registered owner to fill up all the required fields

      Use case ends.

* 2b. Registered owner types incorrect password or username
    * 2b1. US requests registered owner to retype their username or password

      Use case ends.

---

#### Use Case: UC03 - Account Recovery

**System:** User System (US)

**Actor:** Registered owner

**Preconditions:** Registered owner is logged out.

**Guarantees:**

- Password would be changed.

**MSS:**

1. Registered owner opens the HomeBoss application.
2. Registered owner enters the account recovery command without any command flags (i.e., `--answer`).
3. US displays the forget password question that the user set during account registration.
4. Registered owner enters the account recovery command, this time with the answer, new password and confirm password
   fields.
5. US logins and shows a success message.
   Use case ends.

**Extensions:**

* 4a. Registered owner does not enter the answer field.
    * 4a1. US requests registered owner to fill up the answer field.

      Use case ends.

* 4b. Registered owner types incorrect answer
    * 4b1. US requests registered owner to retype their answer.

      Use case ends.

* 4c. Registered owner does not enter one of the password or confirm password fields.
    * 4c1. US requests registered owner to fill up all the required fields

      Use case ends.

* 4d. Registered owner types incorrect confirm password.
    * 4d1. US requests registered owner to retype their confirm password.

      Use case ends.

---

#### Use Case: UC04 - Logout

**System:** User System (US)

**Actor:** Logged-In owner

**Preconditions:** Owner is logged in.

**Guarantees:**

- Logged-in owner would be logged out.

**MSS:**

1. Logged-In owner types logout
2. US logs owner out and shows success message.
   Use case ends.

---

#### Use Case: UC05 - Delete Account

**System:** User System (US)

**Actor:** Logged-in owner.

**Preconditions:** Account is present.

**Guarantees:**

* User account is deleted.

**MSS:**

1. Logged-in owner types command to delete his account.
2. User system shows a success message.

   Use case ends.

---

#### Use Case: UC06 - Update Details

**System:** User System (US)

**Actor:** Logged-in owner

**Preconditions:** Owner is logged in

**Guarantees:**

* Old details will be changed to the new details keyed in only if the command is executed successfully

**MSS:**

1. Logged-in Owner types in command and new details to update details.
2. US shows a success message.

   Use Case ends.

**Extensions:**

* 1a. Logged-in Owner does not specify at least one updated field(s).

    * 1a1. US requests Logged-in Owner to specify at least one updated field.

      Use Case ends.

* 1b. Logged-in Owner specifies Password field and not Confirm Password field.

    * 1b1. US requests Logged-in Owner to specify both Password and Confirm Password field.

      Use Case ends.

* 1c. Logged-in Owner specifies Confirm Password field and not Password field.

    * 1c1. US requests Logged-in Owner to specify both Password and Confirm Password field.

      Use Case ends.

* 1d. Logged-in Owner types incorrect confirm password.

    * 1d1. US requests Logged-in Owner to retype their confirm password.

      Use Case ends.

* 1e. Logged-in Owner specifies Secret Question field and not Answer field.

    * 1e1. US requests Logged-in Owner to specify both Secret Question and Answer field.

      Use Case ends.

* 1f. Logged-in Owner specifies Answer field and not Secret Question field.

    * 1f1. US requests Logged-in Owner to specify both Secret Question and Answer field.

      Use Case ends.

---

#### **Use Case: UC07 - Create Customer**

**System:** Customer Management System (CMS)

**Actor:** Logged-in owner

**Preconditions:** Owner is logged in

**Guarantees**

- Customer is created only if the command is executed successfully.
- The total number of customers will increase or remain the same.

**MSS:**

1. Logged-in Owner types in command and customer’s details to create a customer.
2. CMS shows success message.

   Use Case ends.

**Extensions:**

- 1a. Logged-in Owner does not specify the required field(s)

    - 1a1. CMS requests Logged-in Owner to key in all the fields required to create a customer.

      Use Case ends.

---

#### **Use Case: UC08 - View customer’s details**

**System:** Customer Management System (CMS)

**Actor:** Logged-in owner

**Preconditions:** Owner is logged in

**Guarantees**

- Shows customer’s details if the command is executed successfully.

**MSS:**

1. Logged-in Owner types in command and customer’s id.
2. CMS shows that customer’s details.

   Use Case ends.

**Extensions:**

- 1a. Logged-in Owner does not specify the id.

    - 1a1. CMS requests Logged-in Owner to key in an id.

      Use Case ends.

- 1b. Logged-in Owner specifies a customer id that does not exist.

    - 1b1. CMS displays a message that customer id does not exist.

      Use Case ends.

---

#### **Use Case: UC09 - Sort customers**

**System:** Customer Management System (CMS)

**Actor:** Logged-in owner

**Preconditions:** Owner is logged in

**Guarantees**

* A list of deliveries of a customer is shown if the command is executed successfully.

**MSS:**

1. Logged-in Owner types in command to view what deliveries a particular customer has.
2. CMS shows the list of deliveries of that specific customer.

---

#### **Use Case: UC09 - Search for a Customer**

**System:** Customer Management System (CMS)

**Actor:** Logged-in owner

**Preconditions:** Owner is logged in

**Guarantees**

- List of customers with the specified keyword will be shown only if the command is executed successfully.

**MSS:**

1. Logged-in Owner types in the command and keyword to search for a customer.
2. US shows a list of customers, with that keyword, and all their details.

   Use Case ends.

**Extensions**

- 1a. Logged-in Owner does not include any keyword.

    - 1a1. CMS requests Logged-in Owner to specify a keyword.

      Use Case ends.

- 1b. No customer with specified keyword is found.

    - 1b1. CMS displays a message where no customers with the specified keyword is found.

      Use Case ends.

- 1c. There are no customers.

    - 1c1. CMS displays a message where there are no customers at all.

      Use Case ends.

---

#### **Use case:** UC10 - Customer Detail Update

**System:** Customer Management System (CMS)

**Actor:** Logged-in owner.

**Preconditions:** Owner is logged in.

**Guarantees:**

- Selected customer’s details are updated only if the command is executed successfully.

**MSS:**

1. Logged-in Owner types command to update a customer’s details with at least one field specified.
2. CMS shows success message.

   Use Case Ends.

**Extensions:**

- 1a. Logged-in Owner does not specify at least one updated field(s).

    - 1a1. CMS displays an error to Logged-in Owner to specify at least one field to update.

      Use Case Ends.

- 1b. Logged-in Owner specifies invalid customer.

    - 1b1. CMS displays an error to Logged-in Owner that the specified customer does not exist.

      Use Case Ends.

- 1c. Logged-in Owner does not specify customer.

    - 1c1. CMS displays an error to Logged-in Owner to specify a customer to update.

      Use Case Ends.

---

#### **Use case:** UC11 - Customer Deletion

**System:** Customer Management System (CMS)

**Actor:** Logged-in owner.

**Preconditions:** Owner is logged in.

**Guarantees:**

- Selected customer is deleted only if the command is executed successfully.

**MSS:**

1. Logged-in Owner types command to delete a customer.
2. CMS shows success message.

   Use Case Ends.

**Extensions:**

- 1a. Logged-in Owner specifies invalid customer.

    - 1a1. CMS displays an error to Logged-in Owner that the specified customer does not exist.

      Use Case Ends.

- 1b. Logged-in Owner does not specify customer.

    - 1b1. CMS displays an error to Logged-in Owner to specify a customer to update.

      Use Case Ends.

---

#### **Use case:** UC12 - List Customers

**System:** Customer Management System (CMS)

**Actor:** Logged-in owner.

**Preconditions:** Owner is logged in.

**Guarantees:**

- All Customers are listed only if the command is executed successfully.

**MSS:**

1. Logged-in Owner types command to list all customers.
2. CMS shows list of all customers.

   Use Case Ends.

---

#### **Use case:** UC13 - Delivery Creation

**System:** Delivery Management System (DMS)

**Actor:** Logged-in owner.

**Preconditions:** Owner is logged in.

**Guarantees:**

- A new delivery is created only if the command is executed successfully.

**MSS:**

1. Logged-in Owner types command to create a delivery.
2. DMS shows success message.

   Use Case Ends.

**Extensions:**

- 1a. Command has missing fields.

    - 1a1. DMS displays an error to Logged-in Owner to specify all required fields.

      Use Case Ends.

- 1b. Command has invalid date.

    - 1b1. DMS displays an error to Logged-in Owner that an invalid date was given.

      Use Case Ends.

- 1c. Command has invalid date format.

    - 1c1. DMS displays an error to Logged-in Owner to specify the date in a valid format.

      Use Case Ends.

---

#### **Use case:** UC14 - Delivery Notes Creation

**System:** Delivery Management System (DMS)

**Actor:** Logged-in owner.

**Preconditions:** Owner is logged in.

**Guarantees:**

- A new note is added to a delivery only if the command is executed successfully.

**MSS:**

1. Logged-in Owner types command to create a note for a delivery.
2. DMS shows success message.

   Use Case Ends.

**Extensions:**

- 1a. Command has missing fields.

    - 1a1. DMS displays an error to Logged-in Owner to specify all required fields.

      Use Case Ends.

---

#### **Use case:** UC15 - Delivery List

**System:** Delivery Management System (DMS)
**Actor:** Logged-in owner.

**Preconditions:** Owner is logged in.

**Guarantees:**

- A list of deliveries is displayed only if the command is executed successfully.

**MSS:**

1. Logged-in Owner types command to view a list of deliveries.
2. DMS displays a list of all deliveries sorted in descending delivery date (newest to oldest).

   Use Case Ends.

**Extensions:**

* 1a. User specifies status field in command.
    * 1a1. DMS display a list of deliveries filtered by the specified status.

      Use Case Ends.

* 1b. User specifies customer field in command.
    * 1b1. DMS displays a list of deliveries filtered by the specified customer.

      Use Case Ends.

* 1c. User specifies delivery date field in command.
    * 1c1. DMS displays a list of deliveries filtered by the specified delivery date.

      Use Case Ends.

* 1d. User specifies both status and customer fields.
    * 1c1. DMS displays a list of deliveries filtered by the specified status and customer.

      Use Case Ends.

* 1e. User specifies both status and delivery date fields.
    * 1e1. DMS displays a list of deliveries filtered by the specified status and delivery date.

      Use Case Ends.

* 1f. User specifies both customer and delivery date fields.
    * 1f1. DMS displays a list of deliveries filtered by the specified customer and delivery date.

      Use Case Ends.
* 1g. User specifies customer, delivery date and status fields.
    * 1g1. DMS displays a list of deliveries filtered by the specified customer, delivery date and status.

      Use Case Ends.

* 1g. User specifies sort field in command.
    * 1b1. DMS displays a list of all deliveries sorted by the specified sort order.

      Use Case Ends.

* 1h. User Specifies both filter fields and sort fields.
    * 1c1. DMS displays a list of deliveries filtered by the specified filters and then delivery date sorted by the
      specified sort order.

      Use Case Ends.---

#### **Use case:** UC16 - Delivery List for the Day

**System:** Delivery Management System (DMS)
**Actor:** Logged-in owner.

**Preconditions:** Owner is logged in.

**Guarantees:**

- A list of deliveries for the day is displayed only if the command is executed successfully.

**MSS:**

1. Logged-in Owner types command to view a list of deliveries for the day.
2. DMS displays a list of deliveries for the day.

   Use Case Ends.

**Extensions:**

- 1a. Command has missing fields.

    - 1a1. DMS displays an error to Logged-in Owner.

      Use Case Ends.

---

#### **Use case:** UC17 - Add Customer to Delivery

**System:** Delivery Management System (DMS)
**Actor:** Logged-in owner.

**Preconditions:** Owner is logged in.

**Guarantees:**

- A customer is added to a delivery only if the command is executed successfully.

**MSS:**

1. Logged-in Owner types command to add a customer to a delivery.
2. DMS shows success message.

   Use Case Ends.

**Extensions:**

- 1a. Command has missing fields.

    - 1a1. DMS displays an error to Logged-in Owner to specify all required fields.

      Use Case Ends.

- 1b. Command has invalid customer details.

    - 1b1. DMS displays an error to Logged-in Owner that the specified customer details is invalid.

      Use Case Ends.

---

#### **Use case:** UC18 - Remove Customer from Delivery

**System:** Delivery Management System (DMS)
**Actor:** Logged-in owner.

**Preconditions:** Owner is logged in.

**Guarantees:**

- A customer is removed from a delivery only if the command is executed successfully.

**MSS:**

1. Logged-in Owner types command to remove a customer from a delivery.
2. DMS shows success message.

   Use Case Ends.

**Extensions:**

- 1a. Command has missing fields.

    - 1a1. DMS displays an error to Logged-in Owner to specify all required fields.

      Use Case Ends.

- 1b. Command has invalid customer details.

    - 1b1. DMS displays an error to Logged-in Owner that the specified customer cannot be found.

      Use Case Ends.

---

#### **Use case:** UC19 - Specify Delivery Method

**System:** Delivery Management System (DMS)
**Actor:** Logged-in owner.

**Preconditions:** Owner is logged in.

**Guarantees:**

- A delivery method is specified only if the command is executed successfully.

**MSS:**

1. Logged-in Owner types command to specify a delivery method.
2. DMS shows success message.

   Use Case Ends.

**Extensions:**

- 1a. Command has missing fields.

    - 1a1. DMS displays an error to Logged-in Owner to specify all required fields.

      Use Case Ends.

- 1b. Command has invalid delivery options.

    - 1b1. DMS displays an error to Logged-in Owner that the specified delivery method is invalid.

      Use Case Ends.

---

#### **Use case:** UC20 - Search for Delivery

**System:** Delivery Management System (DMS)
**Actor:** Logged-in owner.

**Preconditions:** Owner is logged in.

**Guarantees:**

- A delivery is searched for only if the command is executed successfully.

**MSS:**

1. Logged-in Owner types command and keywords to search for a delivery.
2. DMS displays a list of deliveries that match the keywords in the search query.

   Use Case Ends.

**Extensions:**

- 1a. Command has missing fields.

    - 1a1. DMS displays an error to Logged-in Owner to specify all required fields.

      Use Case Ends.

---

#### Use Case: UC21 - View location of delivery

**System:** Delivery Management System (DMS)

**Actor:** Logged-in owner.

**Preconditions:** Owner is logged-in.

**Guarantees:**

- Delivery location is shown only if the command is executed successfully.

**MSS:**

1. Logged-in owner types command to view location of delivery.
2. DMS displays the address of the customer associated with the delivery.

   Use case ends.

**Extensions**

- 1a. Logged-in owner did not specify the delivery id.

    - 1a1. DMS informs the logged-in owner of the missing field.

      Use case ends.

- 1b. Logged-in owner specified a delivery id that does not exist.

    - 1b1. DMS informs the logged-in owner of invalid delivery id being entered.

      Use case ends.

---

#### Use Case: UC22 - View details of delivery

**System:** Delivery Management System (DMS)

**Actor:** Logged-in owner.

**Preconditions:** Owner is logged-in.

**Guarantees:**

- Details of the delivery are displayed only if the command is executed successfully.

**MSS:**

1. Logged-in owner types command to view details of delivery.
2. DMS shows details of the delivery.

   Use case ends.

**Extensions**

- 1a. Logged-in owner did not specify the delivery id.

    - 1a1. DMS informs the logged-in owner of the missing field.

      Use case ends.

- 1b. Logged-in owner specified a delivery id that does not exist.

    - 1b1. DMS informs the logged-in owner of invalid delivery id being entered.

      Use case ends.

---

#### Use Case: UC23 - Update delivery status

**System:** Delivery Management System (DMS)

**Actor:** Logged-in owner.

**Preconditions:** Owner is logged-in.

**Guarantees:**

- The status of the delivery is updated only if the command is executed successfully.
- The delivery date of the delivery is updated only if the status is completed and the command is executed successfully.

**MSS:**

1. Logged-in owner types command to update the status of a delivery.
2. DMS updates the status of the delivery and shows a success message.

   Use case ends.

**Extensions**

- 1a. Logged-in owner did not specify the delivery id or delivery status.

    - 1a1. DMS informs the logged-in owner of the missing field.

      Use case ends.

- 1b. Logged-in owner specified a delivery id that does not exist.

    - 1b1. DMS informs the logged-in owner of an invalid delivery id being entered.

      Use case ends.

- 1c. Logged-in owner specified an invalid delivery status.

    - 1c1. DMS informs the logged-in owner of an invalid delivery status being entered.

      Use case ends.

- 1d. Logged-in owner specified the delivery status as completed.

   - 1d1. DMS will update the DeliveryDate the specified delivery (if present) to the current date.

---

#### Use Case: UC24 - Update delivery details

**System:** Delivery Management System (DMS)

**Actor:** Logged-in owner.

**Preconditions:** Owner is logged-in.

**Guarantees:**

- The details of the delivery is updated only if the command is executed successfully.

**MSS:**

1. Logged-in owner types command to update the details(date) of a delivery.
2. DMS updates the details of the delivery and shows a success message.

   Use case ends.

**Extensions**

- 1a. Logged-in owner did not specify all the fields.

    - 1a1. DMS informs the logged-in owner to specify all the fields.

      Use case ends.

- 1b. Logged-in owner specified a delivery id that does not exist.

    - 1b1. DMS informs the logged-in owner of invalid delivery id being entered.

      Use case ends.

- 1c. Logged-in owner entered date in the wrong format.

    - 1c1. DMS informs the logged-in owner of invalid format and shows the correct format.

      Use case ends

---

#### Use Case: UC25 - Delete delivery

**System:** Delivery Management System (DMS)

**Actor:** Logged-in owner.

**Preconditions:** Owner is logged-in.

**Guarantees:**

- The delivery is deleted only if the command is executed successfully.

**MSS:**

1. Logged-in owner types command to delete a delivery.
2. DMS deletes the delivery and shows a success message.

   Use case ends.

**Extensions**

- 1a. Logged-in owner did not specify the delivery id.

    - 1a1. DMS informs the logged-in owner of the missing field.

      Use case ends.

- 1b. Logged-in owner specified a delivery id that does not exist.

    - 1b1. DMS informs the logged-in owner of invalid delivery id being entered.

      Use case ends.

- 1c. Logged-in owner specified a delivery that is in-progress.

    - 1c1. DMS informs the logged-in owner of the status of the delivery and does not delete it.

      Use case ends

---

### Non-Functional Requirements

1. Should work on any _mainstream OS_ as long as it has Java `11` or above installed.
2. Should be able to hold up to
    1. 1000 customers without a noticeable sluggishness in performance for typical usage.
    2. 1000 deliveries without a noticeable sluggishness in performance for typical usage.
3. The system should be easily picked up by a novice with no experience with delivery management software.
4. A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be
   able to accomplish most of the tasks faster using commands than using the mouse.
5. Data stored should be persistent until removal by user, and Private Contact Details should be secure.
6. The project is expected to adhere to a schedule which delivers a feature set every milestone up to _V1.3_
7. The application is not expected to
    1. Perform Inventory Management
    2. Perform Route Planning

_{More to be added}_

### Glossary

- **Mainstream OS**: Windows, Linux, Unix, OS-X
- **Private Contact Detail**: A contact detail that is not meant to be shared with others
- **CLI**: Command Line Interface
- **Owner**: The customer who owns the home-based business and who uses the app

---

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more _exploratory_ testing.

</box>

### Launch and shutdown

1. Initial launch

    1. Download the jar file and copy into an empty folder

    1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be
       optimum.

1. Saving window preferences

    1. Resize the window to an optimum size. Move the window to a different location. Close the window.

    1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### Deleting a customer

1. Deleting a customer while all customers are being shown

    1. Prerequisites: List all customers using the `list` command. Multiple customers in the list.

    1. Test case: `delete 1`<br>
       Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message.
       Timestamp in the status bar is updated.

    1. Test case: `delete 0`<br>
       Expected: No customer is deleted. Error details shown in the status message. Status bar remains the same.

    1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
       Expected: Similar to previous.

1. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

    1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_
