package com.example.prueba;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

//IMPORTANTE!!, LA CLASE DEBE HEREDAR DE AppCompatActivity
public class MainActivity extends AppCompatActivity {

    //DECLARAR LOS BOTONES DEL ACTIVITY DENTRO DE LA CLASE
    private Button btnIrVentas;
    private Button btnIrClientes;
    private Button btnIrConsulta;

    //METODO ON CREATE, SE CREA FACILMENTE HACIENDO "ctrl + O"
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //SINCRONIZAR O CONCATENAR EL LAYOUT "activity_main" DENTRO DE LA CLASE
        setContentView(R.layout.activity_main);
       // AndroidNetworking.initialize(getApplicationContext())
        //SINCRONIZAR O CONCATENAR LOS BUTTON DE "activity_main" DENTRO DE LA CLASE
        btnIrVentas = findViewById(R.id.btnIrVentas);
        btnIrClientes = findViewById(R.id.btnIrClientes);
        btnIrConsulta = findViewById(R.id.btnIrConsulta);

        //METODO ON CLICK LISTENER DEL btnIrVentas
        btnIrVentas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             // LLAMAR DENTRO DEL METODO onClick AL METODO irPantallaVentas
             irPantallaVentas();
            }
        });
        // METODO ON CLICK LISTENER DEL btnIrVentas
        btnIrClientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             // LLAMAR DENTRO DEL METODO onClick AL METODO irPantallaClientes
             irPantallaClientes();
            }
        });
        // METODO ON CLICK LISTENER DEL btnIrConsulta
        btnIrConsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             irPantallaConsulta();
            }
        });

    }
    //METODO PARA IR A LA PANTALLA DE VENTAS O "ventas_activity"
    private void irPantallaVentas(){
        Intent intent = new Intent(this,VentasActivity.class);
        startActivity(intent);
    }
    //METODO PARA IR A LA PANTALLA DE CLIENTES O "clientes_activity"
    private void irPantallaClientes(){
        Intent intent = new Intent(this, ClientesActivity.class);
        startActivity(intent);
    }
    //METODO PARA IR A LA PANTALLA DE CONSULTA O "---"
    private void irPantallaConsulta(){
         Intent intent = new Intent(this,ConsultaAtivity.class);
         startActivity(intent);
    }


}
