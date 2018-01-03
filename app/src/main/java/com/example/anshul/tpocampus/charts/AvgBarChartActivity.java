package com.example.anshul.tpocampus.charts;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.anshul.tpocampus.R;
import com.github.mikephil.charting.charts.BarChart;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

public class AvgBarChartActivity extends AppCompatActivity {

    BarChart barChart;

    double cseAvg, itAvg, eceAvg, eeAvg, mechAvg, pieAvg, chemAvg, civilAvg, biotechAvg, mcaAvg;
    String stats_batch;

    TextView tv_avg_stats, tv_avg_package;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avg_bar_chart);

        setTitle("Placement/Internship Stats");

        tv_avg_stats = (TextView) findViewById(R.id.tv_avg_stats);
        tv_avg_package = (TextView) findViewById(R.id.avg_package);

        Intent intentExtras = getIntent();
        Bundle bundleExtras = intentExtras.getExtras();

        stats_batch = bundleExtras.getString("stats_batch");
        cseAvg = bundleExtras.getFloat("cseAvg");
        itAvg = bundleExtras.getFloat("itAvg");
        eceAvg = bundleExtras.getFloat("eceAvg");
        eeAvg = bundleExtras.getFloat("eeAvg");
        mechAvg = bundleExtras.getFloat("mechAvg");
        pieAvg = bundleExtras.getFloat("pieAvg");
        chemAvg = bundleExtras.getFloat("chemAvg");
        civilAvg = bundleExtras.getFloat("civilAvg");
        biotechAvg = bundleExtras.getFloat("bioAvg");
        mcaAvg = bundleExtras.getFloat("mcaAvg");

        tv_avg_stats.setText(stats_batch.toUpperCase());

        if(stats_batch.equalsIgnoreCase("pre final year stats"))
            tv_avg_package.setText("Average stipend offered in each branch(in thousands per month)");
        else
            tv_avg_package.setText("Average package offered in each branch(in lpa)");

        setupBarGraph();
    }

    private void setupBarGraph() {

        GraphView graph = (GraphView) findViewById(R.id.bargraph2);
        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[] {
                new DataPoint(1, cseAvg),
                new DataPoint(2, itAvg),
                new DataPoint(3, eceAvg),
                new DataPoint(4, eeAvg),
                new DataPoint(5, mechAvg),
                new DataPoint(6, pieAvg),
                new DataPoint(7, chemAvg),
                new DataPoint(8, civilAvg),
                new DataPoint(9, biotechAvg),
                new DataPoint(10, mcaAvg)
        });
        graph.addSeries(series);

        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0);

        graph.getGridLabelRenderer().setNumHorizontalLabels(10);
        graph.getGridLabelRenderer().setHorizontalLabelsAngle(120);

        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        staticLabelsFormatter.setHorizontalLabels(new String[] {"CSE", "IT", "ECE", "EE", "MECH", "PIE", "CHE", "CIV", "BIO", "MCA"});
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);

        // styling
        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                return Color.rgb((int) data.getX()*255/4, (int) Math.abs(data.getY()*255/6), 100);
            }
        });

        series.setSpacing(10);

        // draw values on top
        series.setDrawValuesOnTop(true);
        series.setValuesOnTopColor(Color.RED);
    }
}
