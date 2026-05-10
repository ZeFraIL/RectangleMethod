# Class Description: CurveDetailsActivity

________________________________________
## 1. General Information
*   **Class Name:** `CurveDetailsActivity`
*   **Type:** `Activity`
*   **Purpose:** This screen shows the details of a specific saved curve. It displays the drawing again and lists all calculations (different methods or rectangle counts) performed for that curve.
*   **Interaction:** 
    *   Uses `GraphView` in read-only mode.
    *   Queries the database for specific data related to a `CURVE_ID`.

________________________________________
## 2. Variables (Class Fields)

| Name | Type | Purpose | Where is it used |
| :--- | :--- | :--- | :--- |
| `curveGraphView` | `GraphView` | Displays the static (non-editable) curve. | `onCreate`, `loadCurve` |
| `calculations` | `ArrayList<String>` | List of text summaries for each calculation (e.g., "Left, 10 rects: 150.5"). | `loadCalculations` |

________________________________________
## 3. Class Methods

### Method Name: `loadCurve`
*   **Type:** `private`
*   **Return Value:** `void`
*   **Parameters:** `String curveId`
*   **What it does:** 
    1. Finds the first entry in the DB for this ID.
    2. Extracts the JSON string of points.
    3. Uses **GSON** to convert the JSON back into a `List<PointF>`.
    4. Passes the list to `curveGraphView.setCurve()`.

### Method Name: `loadCalculations`
*   **Type:** `private`
*   **Return Value:** `void`
*   **What it does:** Fetches all rows from the database that match the `curveId`. It gathers the method name, rectangle count, and total area for each saved calculation and adds them to the list on the screen.

________________________________________
## 4. Lifecycle
*   **`onCreate()`**: Retrieves the `CURVE_ID` from the Intent and starts the loading process.

________________________________________
## 5. Interface Interaction (UI)
*   **GraphView:** Set to `setEditable(false)` so the user can't change the history.
*   **ListView:** Shows the results of all math done on this curve.

________________________________________
## 6. Interaction with other components
*   **Intent:** Receives data from `HistoryActivity`.
*   **GSON:** Used for "Deserialization" (converting text back into code objects).

________________________________________
## 7. General Logic
1. Activity starts and receives an ID.
2. It draws the old curve on the screen.
3. It lists all the areas calculated for this curve in a list below the graph.

________________________________________
## 8. Explanation in Simple Words
This is like a **Detail Page** for a specific project. Imagine you saved a drawing and several different measurements for it. This screen pulls that drawing out of the "filing cabinet" (database) and shows you exactly what you drew and all the math results you saved for it previously.
