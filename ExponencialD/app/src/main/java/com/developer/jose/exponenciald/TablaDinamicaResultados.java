package com.example.jose.exponenciald;

import android.content.Context;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class TablaDinamicaResultados {
    //Variables de la clase...
    private TableLayout tableLayout;
    private Context context;
    private String categorias[];
    private ArrayList<double[]> datos=new ArrayList<>();
    private TableRow fila;
    private TextView valorCelda;
    private int indiceFila;
    private int indiceColumna;

    public TablaDinamicaResultados(TableLayout tableLayout, Context context) {
        this.tableLayout = tableLayout;
        this.context = context;
    }

    public void addCategorias(String categorias[]){
        this.categorias=categorias;
        crearEncabezado();
    }
    public void addDatos(ArrayList<double[]> datos){
        this.datos=datos;
        crearDatosTabla();
    }
    public void addItems(double item[]){
        String info;
        datos.add(item);
        indiceColumna=0;
        nuevaFila();
        while(indiceColumna<categorias.length){
            nuevaCelda();
            info=String.valueOf((indiceColumna<item.length)?item[indiceColumna++]:"");
            valorCelda.setText(info);
            fila.addView(valorCelda,nuevosParametrosFila());
        }
        tableLayout.addView(fila);
    }
    private void nuevaFila(){
        fila=new TableRow(context);
    }
    private void nuevaCelda(){
        valorCelda=new TextView(context);
        valorCelda.setGravity(Gravity.CENTER);
        valorCelda.setTextSize(20f);
    }
    private void crearEncabezado(){
        indiceColumna=0;
        nuevaFila();
        while (indiceColumna<categorias.length){
            nuevaCelda();
            valorCelda.setText(categorias[indiceColumna++]);
            fila.addView(valorCelda,nuevosParametrosFila());
        }
        tableLayout.addView(fila);
    }

    public void crearDatosTabla(){
        String info;
        for(indiceFila=1;indiceFila<=categorias.length;indiceFila++){
            nuevaFila();
            for(indiceColumna=0;indiceColumna<categorias.length;indiceColumna++){
                nuevaCelda();
                double filas[]=datos.get(indiceFila-1);
                info=String.valueOf((indiceColumna<filas.length)?filas[indiceColumna++]:"");
                valorCelda.setText(info);
                fila.addView(valorCelda,nuevosParametrosFila());
            }
            tableLayout.addView(fila);
        }
    }

    private TableRow.LayoutParams nuevosParametrosFila(){
        TableRow.LayoutParams layoutParams= new TableRow.LayoutParams();
        layoutParams.setMargins(1,1,1,1);
        layoutParams.weight=1;
        return layoutParams;
    }
}
