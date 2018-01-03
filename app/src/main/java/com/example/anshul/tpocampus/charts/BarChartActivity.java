package com.example.anshul.tpocampus.charts;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.anshul.tpocampus.Admin.ChartInfoClass;
import com.example.anshul.tpocampus.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.google.firebase.database.DatabaseReference;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

public class BarChartActivity extends AppCompatActivity {

    PieChart pieChart;
    BarChart barChart;

    float cseGot, itGot, eceGot, eeGot, mechGot, pieGot, chemGot, civilGot, bioGot, mcaGot;
    String cseTotal, itTotal, eceTotal, eeTotal, mechTotal, pieTotal, chemTotal, civilTotal, bioTotal, mcaTotal;
    String stats_batch;

    TextView tv_stats, tv_no_of_students;

    private DatabaseReference cseDatabase, itDatabase, eceDatabase, eeDatabase, mechDatabase, pieDatabase, chemDatabase,
            civilDatabase, bioDatabase, mcaDatabase;

    ChartInfoClass chartInfoClass = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);

        setTitle("Placement/Internship Stats");

        tv_stats = (TextView) findViewById(R.id.tv_stats);
        tv_no_of_students = (TextView) findViewById(R.id.no_of_students);

        Intent intentExtras = getIntent();
        Bundle bundleExtras = intentExtras.getExtras();

        stats_batch = bundleExtras.getString("stats_batch");
        cseGot = bundleExtras.getFloat("cseGot");
        itGot = bundleExtras.getFloat("itGot");
        eceGot = bundleExtras.getFloat("eceGot");
        eeGot = bundleExtras.getFloat("eeGot");
        mechGot = bundleExtras.getFloat("mechGot");
        pieGot = bundleExtras.getFloat("pieGot");
        chemGot = bundleExtras.getFloat("chemGot");
        civilGot = bundleExtras.getFloat("civilGot");
        bioGot = bundleExtras.getFloat("bioGot");
        mcaGot = bundleExtras.getFloat("mcaGot");

        tv_stats.setText(stats_batch.toUpperCase());

        if(stats_batch.equalsIgnoreCase("pre final year stats"))
            tv_no_of_students.setText("No. of students who got internship offer");
        else
            tv_no_of_students.setText("No. of students who got fulltime offer");

        setupBarGraph();

        /*chartInfoClass = new ChartInfoClass();

        cseDatabase = FirebaseDatabase.getInstance().getReference("admin").child(stats_batch).child("CSE");
        cseDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                chartInfoClass = dataSnapshot.getValue(ChartInfoClass.class);

                cseGot = chartInfoClass.getGot();
                cseTotal = chartInfoClass.getTotal();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        itDatabase = FirebaseDatabase.getInstance().getReference("admin").child(stats_batch).child("IT");
        itDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                chartInfoClass = dataSnapshot.getValue(ChartInfoClass.class);

                itGot = chartInfoClass.getGot();
                itTotal = chartInfoClass.getTotal();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        eceDatabase = FirebaseDatabase.getInstance().getReference("admin").child(stats_batch).child("ECE");
        eceDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                chartInfoClass = dataSnapshot.getValue(ChartInfoClass.class);

                eceGot = chartInfoClass.getGot();
                eceTotal = chartInfoClass.getTotal();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        eeDatabase = FirebaseDatabase.getInstance().getReference("admin").child(stats_batch).child("EE");
        eeDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                chartInfoClass = dataSnapshot.getValue(ChartInfoClass.class);

                eeGot = chartInfoClass.getGot();
                eeTotal = chartInfoClass.getTotal();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mechDatabase = FirebaseDatabase.getInstance().getReference("admin").child(stats_batch).child("mechanical");
        mechDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                chartInfoClass = dataSnapshot.getValue(ChartInfoClass.class);

                mechGot = chartInfoClass.getGot();
                mechTotal = chartInfoClass.getTotal();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        pieDatabase = FirebaseDatabase.getInstance().getReference("admin").child(stats_batch).child("PIE");
        pieDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                chartInfoClass = dataSnapshot.getValue(ChartInfoClass.class);

                pieGot = chartInfoClass.getGot();
                pieTotal = chartInfoClass.getTotal();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        chemDatabase = FirebaseDatabase.getInstance().getReference("admin").child(stats_batch).child("chemical");
        chemDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                chartInfoClass = dataSnapshot.getValue(ChartInfoClass.class);

                chemGot = chartInfoClass.getGot();
                chemTotal = chartInfoClass.getTotal();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        civilDatabase = FirebaseDatabase.getInstance().getReference("admin").child(stats_batch).child("civil");
        civilDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                chartInfoClass = dataSnapshot.getValue(ChartInfoClass.class);

                civilGot = chartInfoClass.getGot();
                civilTotal = chartInfoClass.getTotal();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        bioDatabase = FirebaseDatabase.getInstance().getReference("admin").child(stats_batch).child("biotech");
        bioDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                chartInfoClass = dataSnapshot.getValue(ChartInfoClass.class);

                bioGot = chartInfoClass.getGot();
                bioTotal = chartInfoClass.getTotal();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mcaDatabase = FirebaseDatabase.getInstance().getReference("admin").child(stats_batch).child("MCA");
        mcaDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                chartInfoClass = dataSnapshot.getValue(ChartInfoClass.class);

                mcaGot = chartInfoClass.getGot();
                mcaTotal = chartInfoClass.getTotal();

                setupBarChart();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

    }

    private void setupBarGraph() {
        GraphView graph = (GraphView) findViewById(R.id.bargraph);
        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[] {
                new DataPoint(1, cseGot),
                new DataPoint(2, itGot),
                new DataPoint(3, eceGot),
                new DataPoint(4, eeGot),
                new DataPoint(5, mechGot),
                new DataPoint(6, pieGot),
                new DataPoint(7, chemGot),
                new DataPoint(8, civilGot),
                new DataPoint(9, bioGot),
                new DataPoint(10, mcaGot)
        });
        graph.addSeries(series);

        //double xInterval=1.0;
        /*graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setYAxisBoundsManual(true);

        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(11);
        graph.getViewport().setMinY(0);*/
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0);

        /*if (series instanceof BarGraphSeries ) {
            graph.getViewport().setMinX(series.getLowestValueX() - (xInterval/2.0));
            graph.getViewport().setMaxX(series.getHighestValueX() + (xInterval/2.0));
        } else {
            graph.getViewport().setMinX(series.getLowestValueX() );
            graph.getViewport().setMaxX(series.getHighestValueX());
        }*/

        //graph.getViewport().setMaxXAxisSize(12);
        //graph.getViewport().setScrollable(true);
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
//series.setValuesOnTopSize(50);
    }

}

   /* private void setupBarChart() {

        barChart = (BarChart) findViewById(R.id.barchart);
        barChart.getDescription().setEnabled(false);

        //barChart.setDrawBarShadow(false);
        barChart.setFitBars(true);
        barChart.setPinchZoom(true);
        //barChart.setNoDataTextColor(Color.WHITE);

        List<BarEntry> barEntries = new ArrayList<>();

        barEntries .add(new BarEntry(1, cseGot));
        barEntries .add(new BarEntry(2, itGot));
        barEntries .add(new BarEntry(3, eceGot));
        barEntries .add(new BarEntry(4, eeGot));
        barEntries .add(new BarEntry(5, mechGot));
        barEntries .add(new BarEntry(6, pieGot));
        barEntries .add(new BarEntry(7, chemGot));
        barEntries .add(new BarEntry(8, civilGot));
        barEntries .add(new BarEntry(9, bioGot));
        barEntries .add(new BarEntry(10, mcaGot));

        *//*barEntries .add(new BarEntry(1, 5));
        barEntries .add(new BarEntry(2, 4));
        barEntries .add(new BarEntry(3, 3));
        barEntries .add(new BarEntry(4, 2));
        barEntries .add(new BarEntry(5, 1));
        barEntries .add(new BarEntry(6, 0));
        barEntries .add(new BarEntry(7, 0));
        barEntries .add(new BarEntry(8, 1));
        barEntries .add(new BarEntry(9, 3));
        barEntries .add(new BarEntry(10, 1));*//*


        BarDataSet dataSet = new BarDataSet(barEntries, "Internship");
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        dataSet.setDrawValues(true);

        BarData data = new BarData(dataSet);

        barChart.setData(data);

        //1st element of the array is getting printed at last(DOM'T KNOW WHY!!)
        //that's why 1st element is "MCA"
        String[] branches = new String[] {"MCA", "CSE", "IT", "ECE", "EE", "MECH", "PIE", "CHEM", "CIVIL", "BIOTECH"};
        XAxis xAxis = barChart.getXAxis();
        xAxis.setTextSize(10f);
        xAxis.setDrawAxisLine(true);
        xAxis.setLabelCount(10);
        xAxis.setLabelRotationAngle(300f);
        xAxis.setValueFormatter(new MyXAxisValueFormatter(branches));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1);
        xAxis.setCenterAxisLabels(true);
        //xAxis.setAxisMinimum(1);

        barChart.animateY(2000);
        barChart.invalidate();
        *//*List<PieEntry> pieEntries = new ArrayList<>();

        pieEntries.add(new PieEntry(5, "CSE"));
        pieEntries.add(new PieEntry(4, "IT"));
        pieEntries.add(new PieEntry(3, "ECE"));
        pieEntries.add(new PieEntry(1, "EE"));
        pieEntries.add(new PieEntry(1, "MECH"));
        pieEntries.add(new PieEntry(0, "PIE"));
        pieEntries.add(new PieEntry(0, "CIVIL"));
        pieEntries.add(new PieEntry(1, "CHEMICAL"));
        pieEntries.add(new PieEntry(0, "BIOTECH"));
        pieEntries.add(new PieEntry(1, "MCA"));*//*

        //barChart.setDrawValueAboveBar(true);
        //barChart.setMaxVisibleValueCount(50);

        //barChart.setDrawGridBackground(true);
        *//*List<BarEntry> barEntries1 = new ArrayList<>();

        barEntries1 .add(new BarEntry(1, 7));
        barEntries1 .add(new BarEntry(2, 5));
        barEntries1 .add(new BarEntry(3, 2));
        barEntries1 .add(new BarEntry(4, 0));
        barEntries1 .add(new BarEntry(5, 2));
        barEntries1 .add(new BarEntry(6, 1));
        barEntries1 .add(new BarEntry(7, 3));
        barEntries1 .add(new BarEntry(8, 1));
        barEntries1 .add(new BarEntry(9, 0));
        barEntries1 .add(new BarEntry(10, 1));*//*

        *//*BarDataSet dataSet1 = new BarDataSet(barEntries1, "Fulltime");
        dataSet1.setColors(ColorTemplate.JOYFUL_COLORS);
        dataSet1.setDrawValues(true);*//*



        *//*float groupSpace = 0.1f;
        float barSpace = 0.02f;*//*
       // float barWidth = 0.43f;



        //data.setBarWidth(barWidth);
        //barChart.groupBars(1, groupSpace, barSpace);



        *//*PieDataSet dataSet = new PieDataSet(pieEntries, "Internship");
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        PieData data = new PieData(dataSet);
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.YELLOW);

        //Get the chart
        PieChart chart = (PieChart) findViewById(R.id.piechart);

        chart.setExtraOffsets(5, 10, 5, 5);
        chart.setDragDecelerationFrictionCoef(0.95f);
        chart.setDrawHoleEnabled(true);
        chart.setHoleColor(Color.WHITE);
        chart.setTransparentCircleRadius(61f);

        chart.setData(data);
        chart.animateY(1000);
        chart.invalidate();*//*
    }

    public class MyXAxisValueFormatter implements IAxisValueFormatter{

        private String[] mValues;
        public MyXAxisValueFormatter(String[] values) {
            this.mValues = values;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return mValues[(int)value % mValues.length];
        }
    }
}*/
