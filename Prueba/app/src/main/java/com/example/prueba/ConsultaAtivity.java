package com.example.prueba;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ConsultaAtivity extends AppCompatActivity {

    private ListView listVentas;
    private ArrayAdapter<String> adapter;
    private EditText txtFecUno;
    private EditText txtFecDos;

    private Button btnBuscarVentas;
    private Button btnLimpiar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consulta_activity);
        AndroidNetworking.initialize(getApplicationContext());
        listVentas = findViewById(R.id.listVentas);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        txtFecUno = findViewById(R.id.txtFecUno);
        txtFecDos = findViewById(R.id.txtFecDos);
        btnBuscarVentas = findViewById(R.id.btnBuscarVentas);
        btnLimpiar = findViewById(R.id.btnLimpiar);
        btnBuscarVentas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarVentasporFecha();
            }
                });

        btnLimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                limpiarTodo();
            }
        });
    }

    private void mostrarVentasporFecha(){
        listVentas.setAdapter(adapter);

        String fecuno = txtFecUno.getText().toString();
        String fecdos = txtFecDos.getText().toString();

        Map<String,String> datos = new HashMap<>();
        datos.put("fecha1", fecuno);
        datos.put("fecha2", fecdos);
        JSONObject jsonData = new JSONObject(datos);

        AndroidNetworking.post(Constantes.URL_CONSULTA_POST)
                .addJSONObjectBody(jsonData)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String respuesta = response.getString("respuesta");
                            if (respuesta.equals("200")){
                                JSONArray arrayProductos = response.getJSONArray("data");
                                for(int i=0;i<arrayProductos.length();i++){
                                    JSONObject jsonProducto = arrayProductos.getJSONObject(i);
                                    String id = "ID: " + jsonProducto.getString("id");
                                    String descripcion = "DESCRIPCION: " + jsonProducto.getString("descripcion");
                                    String fecha = "FECHA: " + jsonProducto.getString("fecha");
                                    String monto = "MONTO: " + jsonProducto.getDouble("monto") + " pesos MXN";
                                    String nombrecliente = "NOMBRE CLIENTE: " + jsonProducto.getString("nombrecliente");
                                    String apellidocliente = "APELLIDO CLIENTE: " + jsonProducto.getString("apellidocliente");

                                    String dataString = id + "\n" + descripcion + " \n" + fecha + " \n" + monto+ " \n" + nombrecliente+ " \n" + apellidocliente;
                                    adapter.add(dataString);
                                }
                            }else{
                                Toast.makeText(ConsultaAtivity.this, "No puede haber campos vacios ",Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(ConsultaAtivity.this, "Error: "+e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(ConsultaAtivity.this, "Error: "+anError.getErrorDetail(),Toast.LENGTH_SHORT).show();
                    }
    });
}
   private void limpiarTodo(){

   }
}
