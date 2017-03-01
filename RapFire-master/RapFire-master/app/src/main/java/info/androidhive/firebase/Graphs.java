package info.androidhive.firebase;

    import android.content.Intent;
    import android.support.v7.app.AppCompatActivity;
    import android.os.Bundle;
    import android.view.View;

    import com.jjoe64.graphview.GraphView;
    import com.jjoe64.graphview.series.DataPoint;
    import com.jjoe64.graphview.series.LineGraphSeries;


public class Graphs extends AppCompatActivity {

    LineGraphSeries<DataPoint> series;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphs);

        double y,x;
        x = -5.0;

        GraphView graph = (GraphView) findViewById(R.id.graph);
        series = new LineGraphSeries<>();
        for(int i=0; i<500; i++) {
            x = x + 0.1;
            y = Math.sin(x);
            series.appendData(new DataPoint(x, y), true, 500);  //tests that there are equal numbers of x and y points
        }
        graph.addSeries(series);
    }

    public void onClickBack(View view){
        Intent intent = new Intent(this, Home_Screen.class);
        startActivity(intent);
    }
}
