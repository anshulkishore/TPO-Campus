package com.example.anshul.tpocampus.Admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.anshul.tpocampus.R;
import com.example.anshul.tpocampus.charts.AvgBarChartActivity;
import com.example.anshul.tpocampus.charts.BarChartActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class YearStats extends AppCompatActivity {

    String cseGot, itGot, eceGot, eeGot, mechGot, pieGot, chemGot, civilGot, bioGot, mcaGot;

    float cseAvg, itAvg, eceAvg, eeAvg, mechAvg, pieAvg, chemAvg, civilAvg, biotechAvg, mcaAvg;

    String stats_batch, batch;

    int cseCount, itCount, eceCount, eeCount, mechCount, pieCount, chemCount, civilCount, biotechCount, mcaCount;
    int cseCountTotal = 0, itCountTotal = 0, eceCountTotal = 0, eeCountTotal = 0, mechCountTotal = 0, pieCountTotal = 0,
            chemCountTotal = 0, civilCountTotal = 0, biotechCountTotal = 0, mcaCountTotal = 0;
    float cseTotal, itTotal, eceTotal,eeTotal, mechTotal, pieTotal, chemTotal, civilTotal, biotechTotal, mcaTotal;
    int companyStipend;

    private DatabaseReference cseDatabase, itDatabase, eceDatabase, eeDatabase, mechDatabase, pieDatabase, chemDatabase,
            civilDatabase, bioDatabase, mcaDatabase;

    private DatabaseReference cseDatabaseAvg, itDatabaseAvg, eceDatabaseAvg, eeDatabaseAvg, mechDatabaseAvg, pieDatabaseAvg, chemDatabaseAvg,
            civilDatabaseAvg, bioDatabaseAvg, mcaDatabaseAvg;

    private DatabaseReference companyDatabase;

    private Button intern, avg_stipend;

    ChartInfoClass chartInfoClass = null;
    AddInternCompanyInfo addInternCompanyInfo = null;
    AddFulltimeCompanyInfo addFulltimeCompanyInfo = null;
    CompanySelectionStats companySelectionStats = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_final_year_stats);

        setTitle("Stats");

        intern = (Button) findViewById(R.id.pre_final_year_intern_graph);
        avg_stipend = (Button) findViewById(R.id.pre_final_year_stipend_graph);

        Intent intentExtras = getIntent();
        Bundle bundleExtras = intentExtras.getExtras();

        stats_batch = bundleExtras.getString("stats_batch");

        if(stats_batch.equalsIgnoreCase("pre final year stats")) {
            intern.setText("INTERNSHIP OFFERS");
            avg_stipend.setText("AVERAGE STIPEND");
        }
        else{
            intern.setText("FULLTIME OFFERS");
            avg_stipend.setText("AVERAGE PACKAGE");
        }

        //for no. of internships in each branch

        chartInfoClass = new ChartInfoClass();

        cseDatabase = FirebaseDatabase.getInstance().getReference("admin").child(stats_batch).child("CSE");
        cseDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                chartInfoClass = dataSnapshot.getValue(ChartInfoClass.class);

                cseGot = chartInfoClass.getGot();
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
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        //for avg stipend of each branch
        addInternCompanyInfo = new AddInternCompanyInfo();

        if(stats_batch.equalsIgnoreCase("pre final year stats"))
            batch = "pre final year";
        else
            batch = "final year";

        companyDatabase = FirebaseDatabase.getInstance().getReference(batch).child("companies");
        companyDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot ds : dataSnapshot.getChildren()){

                    if(batch.equalsIgnoreCase("pre final year"))
                        addInternCompanyInfo = ds.getValue(AddInternCompanyInfo.class);
                    else
                        addFulltimeCompanyInfo = ds.getValue(AddFulltimeCompanyInfo.class);

                    companySelectionStats = ds.child("selection_stats").getValue(CompanySelectionStats.class);

                    if(batch.equalsIgnoreCase("pre final year")) {
                        companyStipend = Integer.parseInt(addInternCompanyInfo.getStipend());
                    }
                    else
                        companyStipend = Integer.parseInt(addFulltimeCompanyInfo.getSalary());

                    cseCount = Integer.parseInt(companySelectionStats.getCSE_stats());
                    cseCountTotal += cseCount;
                    cseTotal += (companyStipend * cseCount);

                    itCount = Integer.parseInt(companySelectionStats.getIT_stats());
                    itCountTotal += itCount;
                    itTotal += (companyStipend * itCount);

                    eceCount = Integer.parseInt(companySelectionStats.getECE_stats());
                    eceCountTotal += eceCount;
                    eceTotal += (companyStipend * eceCount);

                    eeCount = Integer.parseInt(companySelectionStats.getEE_stats());
                    eeCountTotal += eeCount;
                    eeTotal += (companyStipend * eeCount);

                    mechCount = Integer.parseInt(companySelectionStats.getMECHANICAL_stats());
                    mechCountTotal += mechCount;
                    mechTotal += (companyStipend * mechCount);

                    pieCount = Integer.parseInt(companySelectionStats.getPIE_stats());
                    pieCountTotal += pieCount;
                    pieTotal += (companyStipend * pieCount);

                    chemCount = Integer.parseInt(companySelectionStats.getCHEMICAL_stats());
                    chemCountTotal += chemCount;
                    chemTotal += (companyStipend * chemCount);

                    civilCount = Integer.parseInt(companySelectionStats.getCIVIL_stats());
                    civilCountTotal += civilCount;
                    civilTotal += (companyStipend * civilCount);

                    biotechCount = Integer.parseInt(companySelectionStats.getBIOTECH_stats());
                    biotechCountTotal += biotechCount;
                    biotechTotal += (companyStipend * biotechCount);

                    mcaCount = Integer.parseInt(companySelectionStats.getMCA_stats());
                    mcaCountTotal += mcaCount;
                    mcaTotal += (companyStipend * mcaCount);

                }

                if(cseCountTotal != 0)
                    cseAvg = cseTotal / (float)cseCountTotal;
                else
                    cseAvg = 0;

                if(itCountTotal != 0)
                    itAvg = itTotal / (float)itCountTotal;
                else
                    itAvg = 0;

                if(eceCountTotal != 0)
                    eceAvg = eceTotal / (float)eceCountTotal;
                else
                    eceAvg = 0;

                if(eeCountTotal != 0)
                    eeAvg = eeTotal / (float)eeCountTotal;
                else
                    eeAvg = 0;

                if(mechCountTotal != 0)
                    mechAvg = mechTotal / (float)mechCountTotal;
                else
                    mechAvg = 0;

                if(pieCountTotal != 0)
                    pieAvg = pieTotal / (float)pieCountTotal;
                else
                    pieAvg = 0;

                if(chemCountTotal != 0)
                    chemAvg = chemTotal / (float)chemCountTotal;
                else
                    chemAvg = 0;

                if(civilCountTotal != 0)
                    civilAvg = civilTotal / (float)civilCountTotal;
                else
                    civilAvg = 0;

                if(biotechCountTotal!= 0)
                    biotechAvg = biotechTotal / (float)biotechCountTotal;
                else
                    biotechAvg = 0;

                if(mcaCountTotal != 0)
                    mcaAvg = mcaTotal / (float)mcaCountTotal;
                else
                    mcaAvg = 0;



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        intern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(YearStats.this, BarChartActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("stats_batch", stats_batch);
                bundle.putFloat("cseGot", Float.parseFloat(cseGot));
                bundle.putFloat("itGot", Float.parseFloat(itGot));
                bundle.putFloat("eceGot", Float.parseFloat(eceGot));
                bundle.putFloat("eeGot", Float.parseFloat(eeGot));
                bundle.putFloat("mechGot", Float.parseFloat(mechGot));
                bundle.putFloat("pieGot", Float.parseFloat(pieGot));
                bundle.putFloat("chemGot", Float.parseFloat(chemGot));
                bundle.putFloat("civilGot", Float.parseFloat(civilGot));
                bundle.putFloat("bioGot", Float.parseFloat(bioGot));
                bundle.putFloat("mcaGot", Float.parseFloat(mcaGot));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        avg_stipend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(YearStats.this, AvgBarChartActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("stats_batch", stats_batch);
                bundle.putFloat("cseAvg", cseAvg);
                bundle.putFloat("itAvg", itAvg);
                bundle.putFloat("eceAvg", eceAvg);
                bundle.putFloat("eeAvg", eeAvg);
                bundle.putFloat("mechAvg", mechAvg);
                bundle.putFloat("pieAvg", pieAvg);
                bundle.putFloat("chemAvg", chemAvg);
                bundle.putFloat("civilAvg", civilAvg);
                bundle.putFloat("bioAvg", biotechAvg);
                bundle.putFloat("mcaAvg", mcaAvg);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }
}
