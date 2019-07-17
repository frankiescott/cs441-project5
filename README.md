# cs441-project5

This project is a simple number tracking application. There are four main components: an input field for numeric entry, an add button to append the input to the list, a clear button to wipe the list, and the list view itself. The list view (implemented as a `RecyclerView`) supports swipe to delete so the user can delete specific entries. As numbers are added or removed from the list, a running total display is updated. The application also saves the list's data to the internal file storage to be loaded in at a later time if the application is closed and reopened.

Building this project was rather quick. The initial layout with the widgets was simple to implement considering my experience with prior projects. Once I had the layout created, I began implementing the functionality behind the interface. I used a `ListView` and an `ArrayAdapter` provided by the library in order to code the input field, add, and clear functions. Once the implementation was completed, I converted the `ListView` to a `RecyclerView` with a tweaked version of project 4's adapter instead of using the stock `ArrayAdapter.` From here, I was able to implement a class that controls the swipe to delete feature by extending `ItemTouchHelper.SimpleCallback` to provide a callback that deletes the entry when swiped.

The trickiest part was finding a way for the adapter to communicate with the main activity in order to update the running total when an item is deleted by swiping. In order to overcome this, I created an interface that defines the `calculateTotal()` for the main activity to implement. When the adapter is instantiated, I pass the main activity's context to the constructor so the adapter has a hold on the `calculateTotal()` method. When the swipe to delete callback class initiates the adapter's `delete()` method, the adapter calls upon the `calculateTotal()` method made available through the interface. 

Finally, I implemented a function that saves the data to the device's internal storage every time a number is added, removed, or if the list is cleared. This data is then loaded upon application startup.

# Development Schedule

July 11th - Project initiation and layout development

July 13th - Widget and interface functionality 

July 15th - Loading & saving data and code cleanup

July 17th - Additional non-required features
