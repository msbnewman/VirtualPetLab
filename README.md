# VirtualPetLab 

In this lab, you will design and implement a set of Java classes to represent a Virtual Pet based on the popular '90s Tamagotchi game.  

## VirtualPet1: Simple Pet

In Part 1, you will be implementing a simplified version of the ``VirtualPet`` class. The ``VirtualPet`` class will maintain its happiness and energy levels as it is played. Objects created from the ``VirtualPet`` class will enable users to feed and play with each virtual pet object, and these interactions will affect the object’s happiness and energy levels.  

### Instance Variables

The ``VirtualPet`` class, which you will write, *must* include **instance variables** for the pet's:  

- name
- energy level
- happiness level
- weight in grams
- age in years
- and age in months.

***This is a starting point! You may want to include more instance variables than this in your implementation!***  

### Constructors

The ``VirtualPet`` class must include a **constructor** that is passed a String parameter that stores the name of the pet.  

***This is a starting point! You may want to include more constructors than this in your implementation!***  

### Methods

Your implementation must also include the following methods.  

``getName()``, which returns the name of the pet.  

``toString()``, which returns a string representing the name of the pet and its **status**. The **status** includes the energy level, the happiness level, the weight, and the age of the pet in years and months.  

``feed()``, which increases the energy level and the weight of the pet by 1. The energy level must always be in the range of 0–10, inclusive.  

``getEnergyLevel()``, which returns an integer representing the energy level of the pet.  

``getHappinessLevel()``, which returns an integer representing the happiness level of the pet.  

``play()``, which increases the happiness level of the pet by 1 and decreases the weight of the pet by 1. For now, the happiness level must always be in the range of 0–10, inclusive, and the weight of the pet must not fall below the minimum value of 5.  

``updateStatus()``, which decreases the happiness and energy level of the pet by 1. For now, the energy and happiness levels must always be in the range of 0–10, inclusive. This will also increase the pet’s age by 1 month. If 12 months have elapsed, then the age in years should be changed by 1.  

***This is a starting point! You may want to include more methods than this in your implementation!***  

### Evaluation
Test your program by running the VirtualPetRunner and choosing each of the menu selections. updateStatus() is called every 10 seconds automatically, meaning your pet will age a month every 10 seconds. After 2 minutes, your pet should age a year. You can change the variable INTERVAL_IN_SECONDS to test at a different speed. 


## VirtualPet2: Food and Games

Create at least two additional classes that will enhance the interactivity of your ``VirtualPet`` class. At least one class should represent ``Food`` objects. 

### Food
A basic implementation of the ``Food`` class is outlined below.  

A ``Food`` object is created with parameters that define the name of the food, the increase in energy level, the increase in happiness level, and the amount of weight gained if the food is consumed by a VirtualPet object. The ``Food`` class should have a **constructor** with the following header: ``public Food(String name, int energyIncrease, int happinessIncrease, int weightGain)``. The ``Food`` class also has accessor methods for all instance variables.  

A version of the ``feed()`` method in the VirtualPet class should be created to interact with instances of the Food class. **Do not delete your original feed() method!**

The ``VirtualPet`` class may contain: (1) a mechanism for buying or otherwise obtaining Food objects; (2) a mechanism for viewing different kinds of Food objects that may be available to the player; (3) a mechanism for selecting which Food object to give a VirtualPet OR a random selection mechanism for feeding a VirtualPet; (4) a mechanism for maintaining an inventory of Food objects that a player or VirtualPet can use. Select at least one of these ideas, or come up with your own variation to enhance your gameplay! Make it as simple or as complex as you'd like.

### Games
The Game that the user can play with the VirtualPet must be implemented. It is recommended to create a class for this implementation. The Game must have methods that can take some form of user input, and have a way to increase happiness and decrease weight for the VirtualPet--like taking a dog to the park!

There should also be a way to "win" the game, and if the game is won, it should increase the pet's happiness! You should create a method ``isWinner()`` that returns a boolean value that is ``true`` if the game was won and ``false`` if the game was lost.

A version of the ``play()`` method should be created to interact with instances of the Game class. **Do not delete your original play() method!**

### The GUI

Starting in this section, you may use the VirtualPetGUIRunner. A GUI (Graphical User Interface) can generate a visually interesting, interactive user experience known as a front-end. BE CAREFUL when editing the GUIRunner, and make sure to save your work, commit your changes often, and test each change to ensure that your program still functions.

## VirtualPet3: Additional Features

The following are ideas for additional features, inspired by the original Tamagotchi: 

- Your virtual pet has a hidden health level based on their energy, happiness, sickness, and cleanliness.
- Your virtual pet will randomly use the bathroom after eating or get dirty after playing and will need to be cleaned.
- Your virtual pet will randomly get sick and need to be given medicine.
- Your virtual pet will notify the user if their energy level is 0, if their happiness level is 0, if they are sick, or if they need to be cleaned.
- Create a more interactive game in the Game class for the VirtualPet to play. 

You may implemet any combination of these features, or add additional features to enhance your project. Your final grade will be based on the number and complexity of features implemented, as well as the quality of the final gameplay experience.
