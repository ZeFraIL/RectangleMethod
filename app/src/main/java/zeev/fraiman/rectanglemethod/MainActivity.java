package zeev.fraiman.rectanglemethod;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private GraphView graphView;
    private SeekBar rectanglesSeekBar;
    private TextView rectanglesLabel;
    private RadioGroup methodRadioGroup;
    private Button calculateButton;
    private Button showAreasButton;
    private Button clearButton;
    private Button historyButton;
    private TextView totalAreaTextView;
    private ListView historyListView;

    private ArrayList<String> areasList = new ArrayList<>();
    private ArrayAdapter<String> historyAdapter;
    private ArrayList<String> historyList = new ArrayList<>();
    private float totalArea = 0;

    private HistoryDbHelper dbHelper;
    private String currentCurveId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new HistoryDbHelper(this);

        graphView = findViewById(R.id.graphView);
        rectanglesSeekBar = findViewById(R.id.rectanglesSeekBar);
        rectanglesLabel = findViewById(R.id.rectanglesLabel);
        methodRadioGroup = findViewById(R.id.methodRadioGroup);
        calculateButton = findViewById(R.id.calculateButton);
        showAreasButton = findViewById(R.id.showAreasButton);
        clearButton = findViewById(R.id.clearButton);
        historyButton = findViewById(R.id.historyButton);
        totalAreaTextView = findViewById(R.id.totalAreaTextView);
        historyListView = findViewById(R.id.historyListView);

        historyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, historyList);
        historyListView.setAdapter(historyAdapter);

        rectanglesSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                rectanglesLabel.setText("Number of Rectangles: " + (progress + 1));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        calculateButton.setOnClickListener(v -> calculateArea());
        showAreasButton.setOnClickListener(v -> showAreasDialog());
        clearButton.setOnClickListener(v -> {
            graphView.clearCurve();
            currentCurveId = null;
        });
        historyButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
            startActivity(intent);
        });

        startNewCurve();
    }

    private void startNewCurve(){
        graphView.clearCurve();
        currentCurveId = UUID.randomUUID().toString();
    }

    private void calculateArea() {
        List<PointF> curvePoints = graphView.getCurvePoints();
        if (curvePoints.isEmpty()) {
            return;
        }

        int numRectangles = rectanglesSeekBar.getProgress() + 1;

        int selectedMethodId = methodRadioGroup.getCheckedRadioButtonId();
        RadioButton selectedMethod = findViewById(selectedMethodId);
        String method = selectedMethod.getText().toString();

        List<RectF> rectangles = new ArrayList<>();
        areasList.clear();
        totalArea = 0;

        float minX = curvePoints.get(0).x;
        float maxX = curvePoints.get(curvePoints.size() - 1).x;
        float deltaX = (maxX - minX) / numRectangles;

        for (int i = 0; i < numRectangles; i++) {
            float x1 = minX + i * deltaX;
            float x2 = x1 + deltaX;
            float y = 0;

            if (method.equals("Left")) {
                y = findYForX(curvePoints, x1);
            } else if (method.equals("Right")) {
                y = findYForX(curvePoints, x2);
            } else if (method.equals("Middle")) {
                y = findYForX(curvePoints, x1 + deltaX / 2);
            }

            float area = deltaX * (graphView.getHeight() - 50 - y);
            areasList.add(String.format(Locale.getDefault(), "Rectangle %d: %.2f", i + 1, area));
            totalArea += area;

            rectangles.add(new RectF(x1, y, x2, graphView.getHeight() - 50));
        }

        graphView.setRectangles(rectangles);

        totalAreaTextView.setText(String.format(Locale.getDefault(), "Total Area: %.2f", totalArea));

        historyList.add(0, String.format(Locale.getDefault(), "%s, %d rects: %.2f", method, numRectangles, totalArea));
        historyAdapter.notifyDataSetChanged();

        saveCalculationToDb(curvePoints, method, numRectangles, totalArea);
    }

    private void saveCalculationToDb(List<PointF> curvePoints, String method, int numRectangles, float totalArea) {
        if (currentCurveId == null) {
            currentCurveId = UUID.randomUUID().toString();
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(HistoryContract.HistoryEntry.COLUMN_NAME_CURVE_ID, currentCurveId);
        values.put(HistoryContract.HistoryEntry.COLUMN_NAME_CURVE_POINTS, new Gson().toJson(curvePoints));
        values.put(HistoryContract.HistoryEntry.COLUMN_NAME_METHOD, method);
        values.put(HistoryContract.HistoryEntry.COLUMN_NAME_RECTANGLE_COUNT, numRectangles);
        values.put(HistoryContract.HistoryEntry.COLUMN_NAME_TOTAL_AREA, totalArea);

        db.insert(HistoryContract.HistoryEntry.TABLE_NAME, null, values);
    }

    private float findYForX(List<PointF> points, float x) {
        for (int i = 0; i < points.size() - 1; i++) {
            PointF p1 = points.get(i);
            PointF p2 = points.get(i + 1);
            if (x >= p1.x && x <= p2.x) {
                // Linear interpolation
                return p1.y + (p2.y - p1.y) * (x - p1.x) / (p2.x - p1.x);
            }
        }
        return points.get(points.size() - 1).y; // Return last point if not found
    }

    private void showAreasDialog() {
        if (areasList.isEmpty()) {
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Areas");

        ListView listView = new ListView(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, areasList);
        listView.setAdapter(adapter);

        builder.setView(listView);
        builder.setPositiveButton("OK", null);
        builder.setMessage(String.format(Locale.getDefault(), "Total Area: %.2f", totalArea));

        builder.create().show();
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}
