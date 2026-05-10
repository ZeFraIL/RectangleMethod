# Class Description: HistoryDbHelper

________________________________________
## 1. General Information
*   **Class Name:** `HistoryDbHelper`
*   **Type:** `SQLiteOpenHelper` (Database Helper)
*   **Purpose:** This class is the "librarian" of the application. It is responsible for creating, updating, and managing the SQLite database file where all calculations are stored.
*   **Interaction:** 
    *   Used by `MainActivity` to save data.
    *   Used by `HistoryActivity` and `CurveDetailsActivity` to read data.

________________________________________
## 2. Variables (Class Fields)

| Name | Type | Purpose | Where is it used |
| :--- | :--- | :--- | :--- |
| `DATABASE_NAME` | `String` | The name of the file on the phone's disk ("History.db"). | Constructor |
| `DATABASE_VERSION` | `int` | Version number. Used to trigger database updates if the structure changes. | Constructor |
| `SQL_CREATE_ENTRIES` | `String` | The SQL command to create the "history" table. | `onCreate` |

________________________________________
## 3. Class Methods

### Method Name: `onCreate`
*   **Type:** `public`
*   **Return Value:** `void`
*   **Parameters:** `SQLiteDatabase db` (The database object).
*   **What it does:** Executes the SQL command to create the table. It defines columns like `curve_id`, `curve_points`, `method`, and `total_area`.
*   **When called:** Automatically the very first time the app tries to access the database.

### Method Name: `onUpgrade`
*   **Type:** `public`
*   **Return Value:** `void`
*   **What it does:** Deletes the old table and creates a new one if the version number increases. 
*   **Important:** In a real-world app, you would migrate data here instead of deleting it.

________________________________________
## 4. Lifecycle (Not applicable)
Managed by the Android system's database engine.

________________________________________
## 5. Interface Interaction (UI)
None. This class works entirely "under the hood" (backend).

________________________________________
## 6. Interaction with other components
*   Uses `HistoryContract` to get table and column names. This ensures consistency across the app.

________________________________________
## 7. General Logic
1. `MainActivity` asks for a "writable database".
2. If the file doesn't exist, `HistoryDbHelper` creates it via `onCreate`.
3. It provides the database object to other classes so they can run `INSERT` or `QUERY` commands.

________________________________________
## 8. Explanation in Simple Words
Imagine a big filing cabinet in the basement of the app. `HistoryDbHelper` is the clerk who has the keys. When the app is first installed, the clerk builds the cabinet. Whenever someone wants to file a new report or look up an old one, they talk to this clerk. He makes sure every report goes into the right folder and stays there safely even if you turn off the lights (close the app).
