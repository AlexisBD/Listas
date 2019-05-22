package com.example.listas;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity {

    private ListView simpleList;
    private Button btnAgregar, btnActualizar, btnEliminar;
    private EditText txtAgregar, txtUpdate;
    private ArrayList<String> countries;
    private ArrayAdapter<String> arrayAdapter;
    private String countryList[] = { "Mexico", "USA", "Brazil"};
    private Dialog dialog;
    private AlertDialog.Builder dialog1;
    private TextView txtMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        create();

        simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                update(countries.get(position), position);
            }
        });

    }

    private void create() {
        btnAgregar = findViewById(R.id.btnAgregar);
        txtAgregar = findViewById(R.id.txtAgregar);

        simpleList = findViewById(R.id.simpleListView);
        countries = new ArrayList<String>(Arrays.asList(countryList));

        arrayAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.activity_listview, R.id.textView, countries);

        simpleList.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countries.add(txtAgregar.getText().toString());
                arrayAdapter.notifyDataSetChanged();
                txtAgregar.setText("");
            }
        });
    }

    private void update(String s, final int position) {
        dialog =  new Dialog(MainActivity.this);
        dialog.setTitle("Acutalizar/Eliminar");
        dialog.setContentView(R.layout.input_box);

        txtMessage =(TextView)dialog.findViewById(R.id.txtMessage);
        txtMessage.setText("Acutalizar/Eliminar");
        txtMessage.setTextColor(Color.parseColor("#ff2222"));

        txtUpdate = (EditText) dialog.findViewById(R.id.txtUpdate);
        txtUpdate.setText(s);

        btnActualizar = (Button) dialog.findViewById(R.id.btnDone);
        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countries.set(position, txtUpdate.getText().toString());
                arrayAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        btnEliminar = (Button) dialog.findViewById(R.id.btnEliminar);
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete(position);
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    private void delete(final int position) {
        final int posicion = position;

        dialog1 = new AlertDialog.Builder(MainActivity.this);
        dialog1.setTitle("Confirm delete");
        dialog1.setMessage("¿Desea eliminar esta ciudad?");
        dialog1.setCancelable(false);

        dialog1.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                countries.remove(posicion);
                arrayAdapter.notifyDataSetChanged();
            }
        });

        dialog1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        dialog1.show();
    }

}
