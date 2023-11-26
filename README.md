# Personal Project: *Charming Cafés*

## Introduction

I intended to design an application that allows the user to log cafés they have visited.
The application will allow you to create a list of coffee shops, and record information about them such as ambience, 
volume, and amount of seating. In addition, users will be able to list the drinks or food they've tried at each 
coffee shop. These can be rated and potentially tagged with information such as their season, sweetness, flavour, 
etc. If possible, I intend to allow users to view filter lists of drinks/food from their logged coffee shops
depending on whether they have certain qualities (eg. winter drinks). In addition, I'd like to include a feature 
where cafés can be given a score based on the average rating of their items, and then ranked. 

This application could be used by a wide variety of people. It could be particularly helpful for students, as 
maintaining a list of coffee shops that could double as study spaces would be convenient.

This project is of interest to me as I particularly enjoy trying a variety of drinks at different coffee shops,
and comparing them to similar drinks at other ones. I also enjoy discovering new cafes, and would love to have
a charming app where I could keep a detailed record of them.

## User Stories

- As a user, I want to be able to add a coffee shop to my log.
- As a user, I want to be able to view a list of the coffee shops I've logged.
- As a user, I want to be able to record information about a coffee shop, such as its ambience and available seating.
- As a user, I want to be able to add an item (drink or food) to a coffee shop.
- As a user, I want to be able to view the list of items I've tried at a coffee shop.
- As a user, I want to be able to rate items from a coffee shop.
- As a user, I want to be able to rank the coffee shops by the average ratings of their items.
- As a user, I want to be given the option to save changes to my cafe log.
- As a user, I want to be given the option to load my cafe log.

## Instructions for Grader
When running CharmingCafesUI:
- You can generate the first required action by clicking the "add cafe" button and inputting a name and location
  (the name must be unique, and both a name and location must be added).
- You can delete a cafe by selecting a cafe in the list and clicking the "delete cafe" button.
- You can filter the list of cafes by entering a tag in the text field.
- You can rank the cafes my average rating by pressing rank in the menu bar.
- You can locate my visual component by looking on the first page, or by selecting a cafe, pressing "open cafe",
  and then viewing the "cafe" tab of the JFrame that opens.
- You can save the state of my application by pressing "file", then "save" in the menu bar of the main menu.
- You can reload the state of my application by pressing "file", then "load" in the menu bar of the main menu.

## Phase 4

### Phase 4: Task 2

Fri Nov 24 21:00:04 PST 2023
cafe 'butter baked goods' added

Fri Nov 24 21:00:20 PST 2023
chocolate raspberry cupcake's rating changed to 4 stars

Fri Nov 24 21:00:20 PST 2023
chocolate raspberry cupcake's price changed to $5.0

Fri Nov 24 21:00:20 PST 2023
item 'chocolate raspberry cupcake' added to butter baked goods

Fri Nov 24 21:00:23 PST 2023
tag 'bakery' added to butter baked goods

Fri Nov 24 21:00:27 PST 2023
cafes ranked

### Phase 4: Task 3
To improve the design of my program, I would use the observer pattern to remove the association between my CafeUI and 
CharmingCafesUI classes. A CafeUI is created when a cafe is opened using CharmingCafesUI's nested OpenCafeAction class.
CharmingCafesUI passes itself as a parameter to CafeUI's constructor, and CafeUI has a field of CharmingCafesUI titled
"home". In CafeUI, when "main menu" is pressed in the menu bar, the nested MainMenuAction class sets "home" to visible.
It would be better if instead, CharmingCafesUI implemented an Observer interface and CafeUI extended an abstract 
Observable class. Then, when MainMenuAction's actionPerformed() method is run, CharmingCafesUI could be notified, and 
it's update method could include the call to setVisible. In addition, in CafeUI's CafeWindowAction, CharmingCafesUI's 
printEventLog() method is called on "home". This could also be handled by notifying CharmingCafesUI as an observer
when CafeUI is closed. I would need to add two separate events to distinguish between these notifications. 

I would also consider using the singleton pattern for CafeLog, as only one instance of CafeLog should exist at a time
when the application is running. By making CafeLog a singleton class, it could be ensured that only one instance of 
CafeLog is instantiated, and it would make this instance easily accessible. This would also eliminate the associations
between CafeLog and both CharmingCafesUI and CharmingCafes (the console based application).