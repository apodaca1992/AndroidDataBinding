package com.example.adrian.androiddatabinding.clasesBinding;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.adrian.androiddatabinding.BR;

public class PersonaAfiliacion extends BaseObservable {
    private String nombres;
    private String paterno;
    private String materno;
    private String fechaNacimiento;
    private int selectedItemPositionSexo;

    public PersonaAfiliacion(String prNombres, String prPaterno, String prMaterno, String prFechaNacimiento, int prSelectedItemPositionSexo) {
        this.nombres = prNombres;
        this.paterno = prPaterno;
        this.materno = prMaterno;
        this.fechaNacimiento = prFechaNacimiento;
        this.selectedItemPositionSexo = prSelectedItemPositionSexo;
    }

    @Bindable
    public String getNombres() {
        return this.nombres;
    }

    @Bindable
    public String getPaterno() {
        return this.paterno;
    }

    @Bindable
    public String getMaterno(){
        return this.materno;
    }

    @Bindable
    public String getFechaNacimiento(){
        return this.fechaNacimiento;
    }

    @Bindable
    public int getSelectedItemPositionSexo() {
        return this.selectedItemPositionSexo;
    }

    public void setNombres(String nombres){
        this.nombres = nombres;
        notifyPropertyChanged(BR.nombres);
    }

    public void setPaterno(String paterno){
        this.paterno = paterno;
        notifyPropertyChanged(BR.paterno);
    }

    public void setMaterno(String materno){
        this.materno = materno;
        notifyPropertyChanged(BR.materno);
    }

    public void setFechaNacimiento(String fechaNacimiento){
        this.fechaNacimiento = fechaNacimiento;
        notifyPropertyChanged(BR.fechaNacimiento);
    }

    public void setSelectedItemPositionSexo(int selectedItemPositionSexo) {
        this.selectedItemPositionSexo = selectedItemPositionSexo;
        notifyPropertyChanged(BR.selectedItemPositionSexo);
    }
}
