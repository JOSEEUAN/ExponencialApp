package com.developer.jose.exponenciald;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ResultadosActivity extends AppCompatActivity {

    //Declaración de objetos...
    Bundle bundleResultados;
    RelativeLayout rlDatos;
    TextView tvIngresarItems,tvSubtitulo,tiempoTotal,tiempoEsperado,tiempoPromedio;
    double datosRecolectados[],tTotal=0f,tEsperado=0f,tPromedio=0f;
    TableLayout tablaResultados;
    private String categorias[]={"Proceso","Aleatorio","X"};
    private ArrayList<double[]> filas=new ArrayList<>();
    private double numAleatorios[];
    private double tiemposServicio[];
    com.example.jose.exponenciald.TablaDinamicaResultados tablaDinamicaResultados;
    EditText etTiempo;
    int contador2=0,numDatos;
    double tiempo;
    //Declaración de elementos emergentes...
    Dialog signUpDialog;
    Button btnVerResultados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);

        //Instanciación de View's como objetos...
        tablaResultados=(TableLayout) findViewById(R.id.tablaResultados);
        etTiempo=(EditText) findViewById(R.id.etTiempo);
        tvIngresarItems=(TextView) findViewById(R.id.tvIngresarItems);
        tvSubtitulo=(TextView) findViewById(R.id.tvSubtitulo);
        btnVerResultados=(Button) findViewById(R.id.btnVerResultados);
        bundleResultados=getIntent().getExtras();
        rlDatos=(RelativeLayout) findViewById(R.id.rlDatos);
        tablaDinamicaResultados=new com.example.jose.exponenciald.TablaDinamicaResultados(tablaResultados,getApplicationContext());
        tablaDinamicaResultados.addCategorias(categorias);

        //Ejecución de los métodos de la clase...
        obtenerDatosVariablesAleatorias();
    }

    public void obtenerDatosVariablesAleatorias(){
        //Obteniendo los datos definidos en el formulario del método de solución...
        tvIngresarItems.setText("Variable \""+bundleResultados.getString("x")+"\"");
        tvSubtitulo.setText("Ingresa el dato No.1");
        datosRecolectados=bundleResultados.getDoubleArray("datos");
        numDatos=(int)datosRecolectados[2];
        numAleatorios=new double[numDatos];
        tiemposServicio=new double[numDatos];
    }

    public void addNuevoDato(View view){
        tiempo=Double.parseDouble(etTiempo.getText().toString().trim());
        if(tiempo>datosRecolectados[0] && tiempo>datosRecolectados[1]){
            if(contador2<datosRecolectados[2]){
                double ram=-Math.random()/datosRecolectados[0];
                double item[]=new double[]{contador2+1,(Math.pow(2.7118281828,ram)),tiempo};
                numAleatorios[contador2]=item[1];
                tiemposServicio[contador2]=item[2];
                tablaDinamicaResultados.addItems(item);
                tvSubtitulo.setText("Ingresa el dato No."+(contador2+2));
                etTiempo.setText("");//Se limpia la información puesta...
                contador2++;
            }else{
                //Que hacer cuando ya tiene todos los datos...
                rlDatos.setVisibility(View.GONE);//Desaparece el formulario para añadir más datos...
                btnVerResultados.setVisibility(View.VISIBLE);//Se habilita el botón para ver resultados en un Dialog....
            }
        }else{//Sino cumple con la condición...
            Toast.makeText(getApplicationContext(),"Ingrese un dato dentro del rango",Toast.LENGTH_LONG).show();
        }
    }
    public void verResultados(View v){
        //Ante la ejecución de este método, se muestra el dialogo de registro...
        showDialog(1);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        signUpDialog=new Dialog(this);
        Window windowSignUpDialog=signUpDialog.getWindow();
        int ParametrosDesenfoqueFondoWindow= WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        int ParametrosObscurecerFondoWindow=WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        windowSignUpDialog.setFlags(ParametrosDesenfoqueFondoWindow,ParametrosObscurecerFondoWindow);
        signUpDialog.setContentView(R.layout.resultados);
        //Instanciación de los View's del dialogo de registro como objetos...
        tiempoTotal=(TextView) signUpDialog.findViewById(R.id.tiempoTotal);
        tiempoEsperado=(TextView) signUpDialog.findViewById(R.id.tiempoEsperado);
        tiempoPromedio=(TextView) signUpDialog.findViewById(R.id.tiempoPromedio);
        for(int cont=0;cont<tiemposServicio.length;cont++){
            tTotal+=tiemposServicio[cont];
        }
        //Definiendo los resultados de la observación...
        tiempoTotal.setText("Tiempo total (x): "+tTotal);
        tEsperado=(int)tTotal;
        tPromedio=tTotal/numDatos;
        tiempoEsperado.setText("Tiempo esperado (µ): "+tEsperado);
        tiempoPromedio.setText("Tiempo promedio (x): "+tPromedio);
        return signUpDialog;
    }
}
