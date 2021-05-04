# AndroidStudio-AD340
Repo for Android Studio work for Mobile Dev class AD340.

## Create a new project in Android Studio
 - include a name that will be appropriate as you extend the application throughout this course.
 Be sure to use a unique 'domain' name, as it will be difficult to change later.

 - Select a minimum API level that works for at least 80% of Android devices. For now, it's ok to support just phones in portrait mode.

 - Your app should have a main activity that displays a custom title, your name, and any other fun facts about yourself when the application runs.


## UI Layouts (Homework 2)
 - Add a text entry field and 'send' button to your main activity.
   - The text field should have fixed spacing to the left edge of the screen and to the button on it's right. Field width should vary according to screen size. The button should have fixed spacing on it's right to the screen edge.

 - Add a 2 x 2 grid of buttons below the text entry field.
   - Each button should have unique text. The left edge of the grid should be aligned with the text field, and right edge should be aligned with the screen margin.
   - *Spacing between each column of buttons should vary according to screen size.*

 - Enable each button with a click event, where tapping the button displays a 'toast' specific to that button. 

 - UI elements should be fully visible on a range of phone screen sizes.

 - Text strings should be defined in a resource file separate from your layout elements.

## Zombie Movies List (Homework 3)
- Add two new activities to your app - 'Movies', 'Movie Details'

- Update home page to launch 'Movies' when the corresponding button in the 'grid' is clicked.

- The Movies activity should display the title and year for each movie in this list. (source: https://collider.com/best-zombie-movies-of-all-time/)

- When a list item is selected, launch the movie detail activity to display  title, year, director, and description for the selected item.
  - Your detail activity should receive movie data as part of the intent bundle.

- Enable screen title and 'back' navigation on each activity

- Layout & sizing of UI elements should appropriate for a range of phone screen sizes. List and description fields should be scrollable.

## Live Cams (Homework 4)
 - Add a new activity to display a list of traffic camera locations.
   - The list should display description and image for each camera. You can re-use much of the layout code from HW3.

 - Camera data can be loaded from https://web6.seattle.gov/Travelers/api/Map/Data?zoomId=13&type=2. 

 - JSON data should be mapped to a Camera class for use within the application.

 - Activity should check the device connectivity status and display a graceful warning if not connected. You should only make a network request if the application has connectivity.
 - Update one of the buttons on your main screen to launch this new activity when clicked
