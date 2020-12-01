Welcome!

Within the "src/app" folder, you will see three folders plus the main java file. 

Going into the "GUI" folder, you will see all the files related to the making of the GUI. There is a folder labeled "Pages" which consists of all the classes that were created to make each page of the GUI. The "GUI.java" and the "GUIPage.java" are both java files that include all of the JPanel and JFrame methods. 

Going into the "Backend" folder, you will see two folders: "Fileaccess" and "tree". The "Fileaccess" folder has the files needed for the program to read and manipulate the data in the partfile that was given. The "tree" folder has all the files needed to construct the B+tree within the program. There are four java class files within this folder and they all work together to make the B+tree work in our program. 

Going into the "res" folder, you will find two files. One of the files is the part file that was given to us that we are supposed to use in our program. The second file is the image file used on the main menu in our program (NYIT logo).

Moving forward. This program stores all the data from the part file into a B+tree format. The program starts off at the main menu page where there are three options: "Search Part", "Add a Part", and "Delete a Part". 

If the user chooses the "Search Part" button, they will be brought to a new page that asks for the part number. Once you enter the part number, the part as well as the description of the part will be shown to the user. If the part doens't exist, it will show an error. Users also have the option see the next ten parts after the searched part.

If the user chooses the "Add a Part" button, they will be brought to a new page where there is a space to add a part number and a part description. Once added, it will be stored into the B+tree and you will be able to search for it. If the part added already exists, it will show an error saying it already exists. 

If the user chooses the "Delete a Part" button, they will be brought to a new page where you enter the part number of the part you want to delete. Again if the part doesnt exist, it will show an error. 

Then there is an exit button on the bottom. If the user clicks that, they will be brought to a page that asks you if you want to save before you exit.

