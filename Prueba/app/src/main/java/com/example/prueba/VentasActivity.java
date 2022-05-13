package com.example.prueba;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class VentasActivity extends AppCompatActivity {

    private EditText txtId;
    private EditText txtDescripcion;
    private EditText txtFecha;
    private EditText txtMonto;
    private EditText txtNombreCliente;
    private EditText txtApellido;

    private Button btnBuscarCliente;
    private Button btnGuardarCliente;
    private Button btnEliminarCliente;
    private Button btnActualizarCliente;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ventas_activity);
        AndroidNetworking.initialize(getApplicationContext());

        txtId = findViewById(R.id.txtId);
        txtDescripcion = findViewById(R.id.txtDescripcion);
        txtFecha = findViewById(R.id.txtFecha);
        txtMonto = findViewById(R.id.txtMonto);
        txtNombreCliente = findViewById(R.id.txtNombreCliente);
        txtApellido = findViewById(R.id.txtApellido);

        btnBuscarCliente = findViewById(R.id.btnBuscarCliente);
        btnGuardarCliente = findViewById(R.id.btnGuardarCliente);
        btnEliminarCliente = findViewById(R.id.btnEliminarCliente);
        btnActualizarCliente = findViewById(R.id.btnActualizarCliente);

        btnGuardarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              guardarVenta();
            }
        });

        btnActualizarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              actualizarVenta();
            }
        });

        btnEliminarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             eliminarVenta();
            }
        });

        btnBuscarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              buscarXId();
            }
        });

    }

   private void guardarVenta(){
    if(isValidarCampos()){

        String id = txtId.getText().toString();
        String descripcion = txtDescripcion.getText().toString();
        String fecha = txtFecha.getText().toString();
        String monto = txtMonto.getText().toString();
        String nombrecliente = txtNombreCliente.getText().toString();
        String apellidocliente = txtApellido.getText().toString();

        Map<String,String> datos = new HashMap<>();
        datos.put("id", id);
        datos.put("descripcion", descripcion);
        datos.put("fecha", fecha);
        datos.put("monto", monto);
        datos.put("nombreCliente", nombrecliente);
        datos.put("apellidoCliente", apellidocliente);
        JSONObject jsonData = new JSONObject(datos);

        AndroidNetworking.post(Constantes.URL_GUARDAR_VENTA)
                .addJSONObjectBody(jsonData)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                      try {
                          String estado = response.getString("estado");
                          String error = response.getString("error");
                          Toast.makeText(VentasActivity.this, estado, Toast.LENGTH_SHORT).show();

                      }catch (JSONException e){
                          Toast.makeText(VentasActivity.this, "Error: "+e.getMessage(),Toast.LENGTH_SHORT).show();
                      }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(VentasActivity.this, "Error: "+anError.getErrorDetail(), Toast.LENGTH_SHORT).show();
                    }
                });

    }else{
        Toast.makeText(this, "No se puede guardar la venta si existen campos vacios",Toast.LENGTH_SHORT).show();
    }
   }

   private void eliminarVenta(){
       if(isValidarId()){

           String id = txtId.getText().toString();
           Map<String,String> datos = new HashMap<>();
           datos.put("id",id);

           AndroidNetworking.post(Constantes.URL_ELIMINAR_VENTA)
                   .addJSONObjectBody(new JSONObject(datos))
                   .setPriority(Priority.MEDIUM)
                   .build()
                   .getAsJSONObject(new JSONObjectRequestListener() {
                       @Override
                       public void onResponse(JSONObject response) {

                           try {
                               String estado = response.getString("estado");
                               String error = response.getString("error");
                               Toast.makeText(VentasActivity.this, estado, Toast.LENGTH_SHORT).show();
                               //Toast.makeText(GuardarProductoActivity.this, "Error: "+error, Toast.LENGTH_SHORT).show();
                           } catch (JSONException e) {
                               Toast.makeText(VentasActivity.this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                           }

                       }

                       @Override
                       public void onError(ANError anError) {
                           Toast.makeText(VentasActivity.this, "Error: "+anError.getErrorDetail(), Toast.LENGTH_SHORT).show();
                       }
                   });
       }else{
           Toast.makeText(this,"Los campos no pueden estar vacios",Toast.LENGTH_SHORT).show();
       }
   }

   private void actualizarVenta(){
       if (isValidarCampos()) { //SE ACTUALIZA EL PRODUCTO

           // TOMA LOS DATOS DE LOS EDIT TEXT
           String id = txtId.getText().toString();
           String descripcion = txtDescripcion.getText().toString();
           String fecha = txtFecha.getText().toString();
           String monto = txtMonto.getText().toString();
           String nombrecliente = txtNombreCliente.getText().toString();
           String apellido = txtApellido.getText().toString();


           // LOS PREPARA EN UN MAP
           Map<String, String> datos = new HashMap<>();
           datos.put("id", id);
           datos.put("descripcion", descripcion);
           datos.put("fecha", fecha);
           datos.put("monto", monto);
           datos.put("nombreCliente", nombrecliente);
           datos.put("apellidoCliente", apellido);

           // METE O PREPARA LOS DATOS EN UN JSON
           JSONObject jsonData = new JSONObject(datos);

           // ESPECIFICAR LA URL PARA QUE SE CARGUE EN JSON EN EL .php
           AndroidNetworking.post(Constantes.URL_ACTUALIZAR_VENTA)
                   .addJSONObjectBody(jsonData)
                   .setPriority(Priority.MEDIUM)
                   .build()
                   .getAsJSONObject(new JSONObjectRequestListener() {
                       @Override
                       public void onResponse(JSONObject response) {
                           try {
                               String estado = response.getString("estado");
                               String error = response.getString("error");
                               Toast.makeText(VentasActivity.this, estado, Toast.LENGTH_SHORT).show();
                               //Toast.makeText(GuardarProductoActivity.this, "Error: "+error, Toast.LENGTH_SHORT).show();
                           } catch (JSONException e) {
                               Toast.makeText(VentasActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                           }
                       }

                       @Override
                       public void onError(ANError anError) {
                           Toast.makeText(VentasActivity.this, "Error: " + anError.getErrorDetail(), Toast.LENGTH_SHORT).show();

                       }
                   });
       } else {
           Toast.makeText(this, "Existen campos vacios, no se puede actualizar", Toast.LENGTH_SHORT).show();
       }
   }

   private void buscarXId(){
       RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
       JSONObject object = new JSONObject();
       String id = txtId.getText().toString().trim();
       String url = Constantes.URL_BUSCAR_X_ID+"?id="+id;

       JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, object, new Response.Listener<JSONObject>() {
           @Override
           public void onResponse(JSONObject response) {
               try {
                   JSONObject datos = response.getJSONObject("data");
                   String descripcion = datos.getString("descripcion");
                   String fecha = datos.getString("fecha");
                   String monto = datos.getString("monto");
                   String nombrecliente = datos.getString("nombrecliente");
                   String apellidocliente = datos.getString("apellidocliente");
                   txtDescripcion.setText(descripcion);
                   txtFecha.setText(fecha);
                   txtMonto.setText(monto);
                   txtNombreCliente.setText(nombrecliente);
                   txtApellido.setText(apellidocliente);

               }catch(JSONException e){
                   Toast.makeText(VentasActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
               }
           }
       }, new Response.ErrorListener(){
           @Override
           public void onErrorResponse(VolleyError error) {
               txtDescripcion.setText(error.toString());
               txtFecha.setText(error.toString());
               txtMonto.setText(error.toString());
               txtNombreCliente.setText(error.toString());
               txtApellido.setText(error.toString());
           }
       });
       requestQueue.add(jsonObjectRequest);

    }

   private boolean isValidarCampos(){
         return !txtId.getText().toString().trim().isEmpty() &&
                !txtDescripcion.getText().toString().trim().isEmpty() &&
                !txtFecha.getText().toString().trim().isEmpty() &&
                !txtMonto.getText().toString().trim().isEmpty() &&
                !txtNombreCliente.getText().toString().trim().isEmpty() &&
                !txtApellido.getText().toString().trim().isEmpty();
   }

   private boolean isValidarId(){
       return !txtId.getText().toString().trim().isEmpty();
   }
}

