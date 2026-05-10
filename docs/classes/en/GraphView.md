# Class Description: GraphView

________________________________________
## 1. General Information
*   **Class Name:** `GraphView`
*   **Type:** `View` (Custom Component)
*   **Purpose:** This class is responsible for the visual part of the app. It handles the drawing logic: drawing the axes (X and Y), the curve drawn by the user, and the calculated rectangles. It also "listens" to the user's touch to record the curve.
*   **Interaction:** 
    *   Controlled by `MainActivity` and `CurveDetailsActivity`.
    *   Provides coordinates of the curve to `MainActivity` for area calculation.

________________________________________
## 2. Variables (Class Fields)

| Name | Type | Purpose | Where is it used |
| :--- | :--- | :--- | :--- |
| `curvePaint` | `Paint` | Defines the style (color, thickness) for the blue curve. | `init`, `onDraw` |
| `rectanglePaint` | `Paint` | Defines the style for the semi-transparent green rectangles. | `init`, `onDraw` |
| `curvePath` | `Path` | A geometric path that connects all the points drawn by the user. | `init`, `onDraw`, `onTouchEvent` |
| `curvePoints` | `List<PointF>` | A list of exact coordinates (X, Y) of the curve. | `onTouchEvent`, `getCurvePoints` |
| `isEditable` | `boolean` | Flag to enable or disable drawing (e.g., disabled in history view). | `onTouchEvent`, `setEditable` |

________________________________________
## 3. Class Methods

### Method Name: `onDraw`
*   **Type:** `protected`
*   **Return Value:** `void`
*   **Parameters:** `Canvas canvas` (The "paper" to draw on).
*   **What it does:** 
    1. Draws the Black axes with arrows and labels (X, Y).
    2. Loops through the `rectangles` list and draws each one with green filling and gray borders.
    3. Draws the `curvePath` (the blue line).
*   **When called:** Automatically by the system whenever the view needs to be refreshed (e.g., when the user moves their finger or after calculation).

### Method Name: `onTouchEvent`
*   **Type:** `public`
*   **Return Value:** `boolean` (True if the event was handled).
*   **Parameters:** `MotionEvent event` (Contains info about where and how the user touched the screen).
*   **What it does:** 
    *   `ACTION_DOWN`: When the finger first touches, it clears the old curve and starts a new one.
    *   `ACTION_MOVE`: As the finger moves, it adds lines to the `curvePath` and records points into `curvePoints`.
*   **When called:** Every time the user touches the graph area.

### Method Name: `setCurve`
*   **Type:** `public`
*   **Return Value:** `void`
*   **Parameters:** `List<PointF> points`
*   **What it does:** Reconstructs the `curvePath` from a saved list of points. Used when loading data from the history database.

________________________________________
## 4. Lifecycle (Not applicable)
This is a standard View, not an Activity, so it doesn't have standard Android lifecycle methods like `onCreate`. Instead, it uses constructors and `onDraw`.

________________________________________
## 5. Interface Interaction (UI)
*   The entire surface of the `GraphView` acts as an input area.
*   The class translates finger movements into mathematical coordinates.

________________________________________
## 6. Interaction with other components
*   **MainActivity:** Calls `getCurvePoints()` to get data for math.
*   **CurveDetailsActivity:** Calls `setEditable(false)` and `setCurve()` to show saved results without allowing changes.

________________________________________
## 7. General Logic
1. User touches the screen.
2. `onTouchEvent` records points and creates a `Path`.
3. `invalidate()` is called, which triggers `onDraw`.
4. `onDraw` renders everything on the screen so the user sees their line.

________________________________________
## 8. Explanation in Simple Words
`GraphView` is like a **smart blackboard**. 
*   It knows how to draw its own borders and axes.
*   It follows your chalk (finger) and remembers exactly where you drew.
*   When the "math part" of the app decides to put green tiles (rectangles) on the board, `GraphView` displays them precisely where they belong.
*   It can also "lock" itself so you can look at old drawings without accidentally smudging them.
