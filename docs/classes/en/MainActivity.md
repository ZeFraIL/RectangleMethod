# Class Description: MainActivity

________________________________________
## 1. General Information
*   **Class Name:** `MainActivity`
*   **Type:** `Activity`
*   **Purpose:** This is the main screen of the application. It acts as the "control center," where the user interacts with the graph, configures calculation parameters, and sees the results.
*   **Interaction:** 
    *   Uses `GraphView` to display and draw the curve.
    *   Uses `HistoryDbHelper` to save calculation results.
    *   Starts `HistoryActivity` to show past calculations.

________________________________________
## 2. Variables (Class Fields)

| Name | Type | Purpose | Where is it used |
| :--- | :--- | :--- | :--- |
| `graphView` | `GraphView` | A custom view for drawing the curve and rectangles. | `onCreate`, `calculateArea`, `clearButton` |
| `rectanglesSeekBar` | `SeekBar` | A slider to select the number of rectangles. | `onCreate`, `calculateArea` |
| `methodRadioGroup` | `RadioGroup` | A group of buttons to select the integration method (Left, Right, Middle). | `calculateArea` |
| `dbHelper` | `HistoryDbHelper` | An object to help work with the SQLite database. | `onCreate`, `saveCalculationToDb`, `onDestroy` |
| `currentCurveId` | `String` | A unique identifier (UUID) for the curve currently being drawn. | `startNewCurve`, `saveCalculationToDb` |

________________________________________
## 3. Class Methods

### Method Name: `onCreate`
*   **Type:** `protected`
*   **Return Value:** `void`
*   **Parameters:** `Bundle savedInstanceState` (Saved state of the activity).
*   **What it does:** Initializes the user interface, sets up listeners for buttons and the slider, and creates a database helper.
*   **When called:** Automatically by the Android system when the app starts.

### Method Name: `calculateArea`
*   **Type:** `private`
*   **Return Value:** `void`
*   **What it does:** 
    1. Gets the points of the drawn curve from `graphView`.
    2. Determines the integration method and number of rectangles.
    3. Divides the X-axis into segments.
    4. For each segment, finds the height using the `findYForX` method.
    5. Calculates the area of the rectangle and adds it to the total.
    6. Updates the UI and saves data to the database.
*   **When called:** When the "Calculate" button is clicked.
*   **Important:** If the curve is empty, the method does nothing.

### Method Name: `findYForX`
*   **Type:** `private`
*   **Return Value:** `float` (The Y coordinate for a given X).
*   **Parameters:** 
    * `points`: `List<PointF>` (List of curve points).
    * `x`: `float` (The target X coordinate).
*   **What it does:** Uses **Linear Interpolation** to find the Y value. Since the user draws points with gaps, this method calculates the exact Y value on the line between two recorded points.
*   **Important:** This is the mathematical "heart" of the calculation accuracy.

________________________________________
## 4. Lifecycle
*   **`onCreate()`**: Called when the Activity is created. This is where we set up the "skeleton" of our screen.
*   **`onDestroy()`**: Called when the Activity is being destroyed. We use it to close the database connection to prevent memory leaks.

________________________________________
## 5. Interface Interaction (UI)
*   **Buttons:** `calculateButton`, `clearButton`, `historyButton`.
*   **SeekBar:** Used to set the precision (number of rectangles).
*   **ListView:** Shows a quick history of calculations on the current screen.
*   **Interaction:** Uses `findViewById` to link XML layout elements to Java objects.

________________________________________
## 6. Interaction with other components
*   **Intent:** Starts `HistoryActivity` when the history button is clicked.
*   **Database:** Saves every calculation to `History.db` using `HistoryDbHelper`.
*   **GSON:** Converts the list of points (objects) into a JSON string to store in the database.

________________________________________
## 7. General Logic
1. User draws a line.
2. User chooses "Middle" method and 10 rectangles.
3. User clicks "Calculate".
4. The app calculates the area and draws green rectangles on the graph.
5. The result is saved for later viewing.

________________________________________
## 8. Explanation in Simple Words
Imagine you have an irregularly shaped garden and you want to know its area. Since it's not a perfect square, you place several rectangular tiles inside it. You measure the height of the garden at the center of each tile. By adding the areas of all these tiles, you get an approximate size of your garden. `MainActivity` is the "gardener" who does all the measuring and math.
