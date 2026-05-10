# Class Description: HistoryActivity

________________________________________
## 1. General Information
*   **Class Name:** `HistoryActivity`
*   **Type:** `Activity`
*   **Purpose:** This screen displays a list of all unique curve IDs that have been saved in the database. It allows users to browse through their previous calculation sessions.
*   **Interaction:** 
    *   Reads data from `HistoryDbHelper`.
    *   Starts `CurveDetailsActivity` when an item is clicked.

________________________________________
## 2. Variables (Class Fields)

| Name | Type | Purpose | Where is it used |
| :--- | :--- | :--- | :--- |
| `curvesListView` | `ListView` | The UI element that displays the list of IDs. | `onCreate` |
| `dbHelper` | `HistoryDbHelper` | To connect to the database. | `onCreate`, `loadCurveIds` |
| `curveIds` | `ArrayList<String>` | The list that holds the unique IDs fetched from the database. | `loadCurveIds`, `OnItemClickListener` |
| `adapter` | `ArrayAdapter` | Bridges the `curveIds` list and the `ListView` UI. | `onCreate`, `loadCurveIds` |

________________________________________
## 3. Class Methods

### Method Name: `loadCurveIds`
*   **Type:** `private`
*   **Return Value:** `void`
*   **What it does:** 
    1. Opens the database in read mode.
    2. Runs a **Distinct Query** (using `db.query(true, ...)`). This is important because one curve can have multiple calculations (Left, Right, Middle methods). We only want to see the curve ID once in this list.
    3. Adds the IDs to the `curveIds` list.
    4. Tells the adapter to refresh the screen.
*   **When called:** Inside `onCreate` when the activity starts.

________________________________________
## 4. Lifecycle
*   **`onCreate()`**: Prepares the screen, sets up the list, and triggers the database loading.
*   **`onDestroy()`**: Closes the database helper.

________________________________________
## 5. Interface Interaction (UI)
*   **ListView:** The main interaction point.
*   **OnItemClickListener:** Handles clicks on the list. When you click an ID, it takes you to the details of that specific curve.

________________________________________
## 6. Interaction with other components
*   **Intent:** Passes the selected `CURVE_ID` as an "extra" to `CurveDetailsActivity`. This is how the next screen knows which curve to load.

________________________________________
## 7. General Logic
1. User opens the History screen.
2. The app searches the database for all unique IDs.
3. User sees a list of codes (UUIDs).
4. User clicks one, and a new screen opens showing that curve and its calculations.

________________________________________
## 8. Explanation in Simple Words
Think of this as the **Table of Contents** of a book. The "book" is your database. Each entry in the list is a page number or a chapter title. You don't see the whole story here, just the titles. When you click a title, you flip to that specific page to see all the details.
