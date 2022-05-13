package com.example.prueba;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ClientesActivity extends AppCompatActivity {

    private EditText txtIdCliente;
    private EditText txtNombre;
    private EditText txtApellido;
    private EditText txtTelefono;
    private EditText txtDireccion;

    private Button btnBuscar;
    private Button btnGuardar;
    private Button btnEliminar;
    private Button btnActualizar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clientes_activity);
        AndroidNetworking.initialize(getApplicationContext());

        txtIdCliente = findViewById(R.id.txtIdCliente);
        txtNombre = findViewById(R.id.txtNombre);
        txtApellido = findViewById(R.id.txtApellido);
        txtTelefono = findViewById(R.id.txtTelefono);
        txtDireccion = findViewById(R.id.txtDireccion);

        btnBuscar = findViewById(R.id.btnBuscar);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnEliminar = findViewById(R.id.btnEliminar);
        btnActualizar = findViewById(R.id.btnActualizar);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             guardarCliente();
            }
        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actualizarCliente();
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              eliminarCliente();
            }
        });

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              buscarXId();
            }
        });
    }

    private void guardarCliente(){
        if(isValidarCampos()){

            String id = txtIdCliente.getText().toString();
            String nombre = txtNombre.getText().toString();
            String apellido = txtApellido.getText().toString();
            String telefono = txtTelefono.getText().toString();
            String direccion = txtDireccion.getText().toString();

            Map<String,String> datos = new HashMap<>();
            datos.put("id", id);
            datos.put("nombre", nombre);
            datos.put("apellido", apellido);
            datos.put("telefono", telefono);
            datos.put("direccion", direccion);
            JSONObject jsonData = new JSONObject(datos);

            AndroidNetworking.post(Constantes.URL_GUARDAR_CLIENTE)
                    .addJSONObjectBody(jsonData)
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                String estado = response.getString("estado");
                                String error = response.getString("error");
                                Toast.makeText(ClientesActivity.this, estado, Toast.LENGTH_SHORT).show();

                            }catch (JSONException e){
                                Toast.makeText(ClientesActivity.this, "Error: "+e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            Toast.makeText(ClientesActivity.this, "Error: "+anError.getErrorDetail(), Toast.LENGTH_SHORT).show();
                        }
                    });

        }else{
            Toast.makeText(this, "No se puede guardar la venta si existen campos vacios",Toast.LENGTH_SHORT).show();
        }
    }

    private void eliminarCliente(){
        if(isValidarId()){

            String id = txtIdCliente.getText().toString();
            Map<String,String> datos = new HashMap<>();
            datos.put("id",id);

            AndroidNetworking.post(Constantes.URL_ELIMINAR_CLIENTE)
                    .addJSONObjectBody(new JSONObject(datos))
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                String estado = response.getString("estado");
                                String error = response.getString("error");
                                Toast.makeText(ClientesActivity.this, estado, Toast.LENGTH_SHORT).show();
                                //Toast.makeText(GuardarProductoActivity.this, "Error: "+error, Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                Toast.makeText(ClientesActivity.this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onError(ANError anError) {
                            Toast.makeText(ClientesActivity.this, "Error: "+anError.getErrorDetail(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }else{
            Toast.makeText(this,"Los campos no pueden estar vacios",Toast.LENGTH_SHORT).show();
        }
    }

    private void actualizarCliente(){
        if (isValidarCampos()) { //SE ACTUALIZA EL PRODUCTO

            // TOMA LOS DATOS DE LOS EDIT TEXT
            String id = txtIdCliente.getText().toString();
            String nombre = txtNombre.getText().toString();
            String apellido = txtApellido.getText().toString();
            String telefono = txtTelefono.getText().toString();
            String direccion = txtDireccion.getText().toString();


            // LOS PREPARA EN UN MAP
            Map<String, String> datos = new HashMap<>();
            datos.put("id", id);
            datos.put("nombre", nombre);
            datos.put("apellido", apellido);
            datos.put("telefono", telefono);
            datos.put("direccion", direccion);

            // METE O PREPARA LOS DATOS EN UN JSON
            JSONObject jsonData = new JSONObject(datos);

            // ESPECIFICAR LA URL PARA QUE SE CARGUE EN JSON EN EL .php
            AndroidNetworking.post(Constantes.URL_ACTUALIZAR_CLIENTE)
                    .addJSONObjectBody(jsonData)
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                String estado = response.getString("estado");
                                String error = response.getString("error");
                                Toast.makeText(ClientesActivity.this, estado, Toast.LENGTH_SHORT).show();
                                //Toast.makeText(GuardarProductoActivity.this, "Error: "+error, Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                Toast.makeText(ClientesActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            Toast.makeText(ClientesActivity.this, "Error: " + anError.getErrorDetail(), Toast.LENGTH_SHORT).show();

                        }
                    });
        } else {
            Toast.makeText(this, "Existen campos vacios, no se puede actualizar", Toast.LENGTH_SHORT).show();
        }
    }

    private void buscarXId(){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JSONObject object = new JSONObject();
        String id = txtIdCliente.getText().toString().trim();
        String url = Constantes.URL_CLIENTE_X_ID+"?id="+id;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject datos = response.getJSONObject("data");
                    String nombre = datos.getString("nombre");
                    String apellido = datos.getString("apellido");
                    String telefono = datos.getString("telefono");
                    String direccion = datos.getString("direccion");
                    txtNombre.setText(nombre);
                    txtApellido.setText(apellido);
                    txtTelefono.setText(telefono);
                    txtDireccion.setText(direccion);
                }catch(JSONException e){
                    Toast.makeText(ClientesActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                txtNombre.setText(error.toString());
                txtApellido.setText(error.toString());
                txtTelefono.setText(error.toString());
                txtDireccion.setText(error.toString());
            }
        });
        requestQueue.add(jsonObjectRequest);

    }

    private boolean isValidarCampos(){
        return !txtIdCliente.getText().toString().trim().isEmpty() &&
                !txtNombre.getText().toString().trim().isEmpty() &&
                !txtApellido.getText().toString().trim().isEmpty() &&
                !txtTelefono.getText().toString().trim().isEmpty() &&
                !txtDireccion.getText().toString().trim().isEmpty();
    }

    private boolean isValidarId(){
        return !txtIdCliente.getText().toString().trim().isEmpty();
    }
}
