# Class Description: HistoryContract

________________________________________
## 1. General Information
*   **Class Name:** `HistoryContract`
*   **Type:** `Normal Class` (Metadata/Contract)
*   **Purpose:** This class acts as a **Schema Definition**. It defines the names of the tables and columns in the database as constants.
*   **Interaction:** Used by every class that touches the database (`MainActivity`, `HistoryDbHelper`, `HistoryActivity`, `CurveDetailsActivity`).

________________________________________
## 2. Variables (Class Fields)
All variables here are `public static final String`, meaning they are fixed constants.

| Name | Purpose | Value |
| :--- | :--- | :--- |
| `TABLE_NAME` | Name of the database table | "history" |
| `COLUMN_NAME_CURVE_ID` | Stores the unique ID for a curve | "curve_id" |
| `COLUMN_NAME_CURVE_POINTS`| Stores the JSON of coordinates | "curve_points" |
| `COLUMN_NAME_TOTAL_AREA` | Stores the final result | "total_area" |

________________________________________
## 3. General Logic
This class doesn't "do" anything. It just "is". It exists so that if we ever want to change the name of a column from `total_area` to `calculated_area`, we only have to change it in **one place**, and the whole app will be updated automatically. This prevents typos and bugs.

________________________________________
## 8. Explanation in Simple Words
Imagine you and your friends are building a secret base and you need a **code name** for everything. Instead of trying to remember the names, you write them all down on a single piece of paper. Whenever someone needs to talk about the "Blue Folder," they look at the paper to make sure they are using the correct name. `HistoryContract` is that piece of paper.
