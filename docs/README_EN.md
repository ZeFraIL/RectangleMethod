# 📱 Android App Documentation: RectangleMethod

________________________________________
## 🧾 General Information
**Project Name:**  
RectangleMethod  

**Author(s):**  
Zeev Fraiman  

**Date:**  
May 2024  

**Language:**  
Java  

**Development Environment:**  
Android Studio  

**Android Version (minSdk / targetSdk):**  
28 / 36  
________________________________________
## 🎯 Project Goal
•	The application visualizes the process of numerical integration using the rectangle method. It allows the user to manually draw an arbitrary curve and calculate the area under it.

•	This task is important for educational purposes, helping to clearly understand the principles of mathematical analysis and approximate calculations.

•	Target Audience: Students, mathematics teachers, and anyone interested in computational geometry.
________________________________________
## 📌 Application Requirements
**Functional Requirements**
•	Ability to draw a curve with a finger on the screen.
•	Choice of rectangle method: Left, Right, or Middle.
•	Adjustment of the number of rectangles via SeekBar.
•	Calculation of the total area and visualization of the rectangles.
•	Saving calculation history to an SQLite database.
•	Viewing detailed information about saved curves.

**Non-functional Requirements**
•	Performance: Smooth real-time curve drawing.
•	Usability: Intuitive interface with minimal steps to get a result.
•	Reliability: Data persistence upon application restart.
________________________________________
## 🧠 General Architecture
•	**Selected Approach:**  
MVC (Model-View-Controller)

•	**Why it was chosen:**  
For a project of this scale, MVC provides a clear separation between rendering logic (View), data (Model - SQLite/GSON), and control logic (Activity).

•	**Main System Components:**  
- `MainActivity`: Controller and main interface.
- `GraphView`: Custom UI component for graphics.
- `HistoryDbHelper`: Data access layer (SQLite).
- `GSON`: For converting point lists to JSON.
________________________________________
## 🧩 UML Diagram
**Structure Description:**
[MainActivity] –> [GraphView] (Rendering and Input)
[MainActivity] –> [HistoryDbHelper] (Data Persistence)
[HistoryActivity] –> [HistoryDbHelper] (List Query)
[CurveDetailsActivity] –> [GraphView] (Displaying Saved Curve)

**Package Organization:**
All classes are located in the `zeev.fraiman.rectanglemethod` package.
- This provides compactness for a small project.
- Scaling is possible by separating into `.ui`, `.db`, and `.logic` packages.
________________________________________
## 🧩 Detailed Class Description
### 📌 Class: MainActivity
**Role:**  
Main screen of the application.

**Responsibility:**  
Handling user input, managing the calculation process, connecting UI and the database.

**Main Methods:**  
- `onCreate()` — Initialization of components and listeners.
- `calculateArea()` — Core logic for area calculation using the rectangle method with interpolation.
- `saveCalculationToDb()` — Serializing curve points and saving the result.

**Interaction with other classes:**  
Receives point data from `GraphView`, passes data to `HistoryDbHelper`, and opens `HistoryActivity`.
________________________________________
### 📌 Class: GraphView
**Role:**  
Graph visualizer.

**Why it is used:**  
Standard Android components do not support freehand curve drawing followed by mathematical processing of coordinates.

**Main Methods:**  
- `onDraw()` — Rendering axes, curve, and rectangles.
- `onTouchEvent()` — Processing gestures for drawing.
________________________________________
### 📌 Class: HistoryDbHelper / Data Layer
**Role:**  
Managing the SQLite database for storing calculation history.
________________________________________
## 🔄 Application Workflow Diagram
1. User draws a curve on `GraphView`.
2. User selects a method and the number of rectangles.
3. User clicks "Calculate".
4. The system calculates the height of each rectangle via interpolation of the curve points.
5. The result is displayed on the screen and saved in the DB.
________________________________________
## 🎨 UI/UX Analysis
•	The interface is built on a single screen for quick access to all tools (Single Source of Truth).
•	Principles:
–	**Simplicity:** Minimum settings.
–	**Logic:** Sequence of actions (Draw -> Configure -> Calculate).
–	**Accessibility:** Large controls (SeekBar, buttons).
________________________________________
## ⚙️ Thread Management
•	**Used:**  
In the current version, operations are performed on the main thread (UI Thread), as calculations for a small number of rectangles are instantaneous.

•	**Prevention of:**  
–	Hangs (ANR): Use of optimized loops.
–	Memory leaks: Closing DB cursors in `onDestroy()`.
________________________________________
## 💾 Data Management
•	Data is stored in SQLite.
•	JSON (GSON) is used to store complex structures (list of `PointF` points) in a database text field.
•	Persistence is ensured through standard SQLite mechanisms.
________________________________________
## 🔐 Security
•	No sensitive data involved. The app works locally on the device.
________________________________________
## 🧪 Testing
•	Validation of interpolation correctness in `findYForX`.
•	Validation of rectangle display for different methods (Left/Right/Middle).
________________________________________
## 🐞 Error Handling
•	Check for empty curve before calculation.
•	Handling absence of data in the DB.
________________________________________
## 📊 Project Self-Assessment
| Criterion | Rating (1–10) |
| :--- | :--- |
| Architecture | 8 |
| Code | 9 |
| UI/UX | 8 |
| Reliability | 10 |
| **Total Level** | **9** |
________________________________________
## 🏁 Conclusion
•	The visualization of rectangles over a user-drawn curve turned out best.
•	A challenging point was implementing linear interpolation to find the rectangle height at points where the user didn't draw a line.
•	Skills acquired: Working with Custom View, Canvas API, SQLite integration with GSON.
