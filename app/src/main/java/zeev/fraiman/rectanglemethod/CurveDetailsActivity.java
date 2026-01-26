package zeev.fraiman.rectanglemethod;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PointF;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CurveDetailsActivity extends AppCompatActivity {

    private GraphView curveGraphView;
    private ListView calculationsListView;
    private HistoryDbHelper dbHelper;
    private ArrayList<String> calculations = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curve_details);

        dbHelper = new HistoryDbHelper(this);
        curveGraphView = findViewById(R.id.curveGraphView);
        calculationsListView = findViewById(R.id.calculationsListView);

        curveGraphView.setEditable(false);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, calculations);
        calculationsListView.setAdapter(adapter);

        String curveId = getIntent().getStringExtra("CURVE_ID");
        if (curveId != null) {
            loadCurve(curveId);
            loadCalculations(curveId);
        }
    }

    private void loadCurve(String curveId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = { HistoryContract.HistoryEntry.COLUMN_NAME_CURVE_POINTS };
        String selection = HistoryContract.HistoryEntry.COLUMN_NAME_CURVE_ID + " = ?";
        String[] selectionArgs = { curveId };

        Cursor cursor = db.query(
                HistoryContract.HistoryEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null,
                "1"
        );

        if (cursor.moveToFirst()) {
            String curvePointsJson = cursor.getString(cursor.getColumnIndexOrThrow(HistoryContract.HistoryEntry.COLUMN_NAME_CURVE_POINTS));
            Type type = new TypeToken<ArrayList<PointF>>() {}.getType();
            List<PointF> curvePoints = new Gson().fromJson(curvePointsJson, type);
            curveGraphView.setCurve(curvePoints);
        }
        cursor.close();
    }

    private void loadCalculations(String curveId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                HistoryContract.HistoryEntry.COLUMN_NAME_METHOD,
                HistoryContract.HistoryEntry.COLUMN_NAME_RECTANGLE_COUNT,
                HistoryContract.HistoryEntry.COLUMN_NAME_TOTAL_AREA
        };

        String selection = HistoryContract.HistoryEntry.COLUMN_NAME_CURVE_ID + " = ?";
        String[] selectionArgs = { curveId };

        Cursor cursor = db.query(
                HistoryContract.HistoryEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                HistoryContract.HistoryEntry.COLUMN_NAME_RECTANGLE_COUNT + " ASC"
        );

        while (cursor.moveToNext()) {
            String method = cursor.getString(cursor.getColumnIndexOrThrow(HistoryContract.HistoryEntry.COLUMN_NAME_METHOD));
            int rectangleCount = cursor.getInt(cursor.getColumnIndexOrThrow(HistoryContract.HistoryEntry.COLUMN_NAME_RECTANGLE_COUNT));
            float totalArea = cursor.getFloat(cursor.getColumnIndexOrThrow(HistoryContract.HistoryEntry.COLUMN_NAME_TOTAL_AREA));

            String calculation = String.format(Locale.getDefault(), "%s, %d rects: %.2f", method, rectangleCount, totalArea);
            calculations.add(calculation);
        }
        cursor.close();

        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}
