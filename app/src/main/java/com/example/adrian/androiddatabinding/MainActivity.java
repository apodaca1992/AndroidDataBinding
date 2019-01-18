package com.example.adrian.androiddatabinding;

import android.app.ProgressDialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.example.adrian.androiddatabinding.clasesBinding.PersonaAfiliacion;
import com.example.adrian.androiddatabinding.databinding.ActivityMainBinding;
import com.example.adrian.androiddatabinding.utilerias.Utilerias;
import com.example.adrian.androiddatabinding.volley.volleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "AgregarNuevaAfiliacion";
    private PersonaAfiliacion personaAfiliacion = new PersonaAfiliacion("", "","","",0);

    private static DatePickerFragment oDiaglogFragment = null;

    //UI References
    private EditText txtApellidoPaterno;
    private EditText txtApellidoMaterno;
    private EditText txtNombre;
    private EditText txtFechaDeNacimiento;
    private Spinner spnSexo;
    private Spinner spnMunicipio;
    private Spinner spnOcupacion;
    private Spinner spnGradoDeEstudio;
    private EditText txtSeccion;
    private EditText txtTelefonoCasa;
    private EditText txtTelefonoMovil;
    private EditText txtCorreo;
    private Button btnConsultarPaciente;
    private Button btnAgregarPaciente;
    private Button btnAltaPaciente;
    private LinearLayout llAltaPaciente;
    private LinearLayout llAgregarPaciente;

    private List<JSONObject> lstSexo  = new ArrayList<>();
    private AdapterSexo oAdapterSexo;
    private ProgressDialog pdDescargaDatosSpinner;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("nombre", personaAfiliacion.getNombres());
        outState.putString("paterno", personaAfiliacion.getPaterno());
        outState.putString("materno", personaAfiliacion.getMaterno());
        outState.putString("fechaNacimiento", personaAfiliacion.getFechaNacimiento());
        outState.putInt("spinner_sexo", personaAfiliacion.getSelectedItemPositionSexo());
        /*outState.putInt("spinner_municipio", spnMunicipio.getSelectedItemPosition());
        outState.putInt("spinner_ocupacion", spnOcupacion.getSelectedItemPosition());
        outState.putInt("spinner_gradoEstudios", spnGradoDeEstudio.getSelectedItemPosition());
        outState.putString("seccion", txtSeccion.getText().toString());
        outState.putString("telefonoCasa", txtTelefonoCasa.getText().toString());
        outState.putString("telefonoMovil", txtTelefonoMovil.getText().toString());
        outState.putString("correo", txtCorreo.getText().toString());*/
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onRestoreInstanceState(final Bundle savedInstanceState) {
        // Check whether we're recreating a previously destroyed instance
        if (savedInstanceState != null) {
            personaAfiliacion.setNombres(savedInstanceState.getString("nombre"));
            personaAfiliacion.setPaterno(savedInstanceState.getString("paterno"));
            personaAfiliacion.setMaterno(savedInstanceState.getString("materno"));
            personaAfiliacion.setFechaNacimiento(savedInstanceState.getString("fechaNacimiento"));
            personaAfiliacion.setSelectedItemPositionSexo(savedInstanceState.getInt("spinner_sexo"));
        } else {
            // Probably initialize members with default values for a new instance
        }
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setPersonaAfiliacion(personaAfiliacion);


        //declaramos todos los controles de la IU.
        txtNombre = (EditText) findViewById(R.id.txtNombre);
        txtApellidoPaterno = (EditText) findViewById(R.id.txtApellidoPaterno);
        txtApellidoMaterno = (EditText) findViewById(R.id.txtApellidoMaterno);
        txtFechaDeNacimiento = (EditText) findViewById(R.id.txtFechaDeNacimiento);
        spnSexo = (Spinner) findViewById(R.id.spnSexo);
        /*spnMunicipio = (Spinner) findViewById(R.id.spnMunicipio);
        spnOcupacion = (Spinner) findViewById(R.id.spnOcupacion);
        spnGradoDeEstudio = (Spinner) findViewById(R.id.spnGradoDeEstudio);
        txtSeccion = (EditText) findViewById(R.id.txtSeccion);
        txtTelefonoCasa = (EditText) findViewById(R.id.txtTelefonoCasa);
        txtTelefonoMovil = (EditText) findViewById(R.id.txtTelefonoMovil);
        txtCorreo = (EditText) findViewById(R.id.txtCorreo);
        btnConsultarPaciente = (Button) findViewById(R.id.btnConsultarPaciente);
        btnAgregarPaciente = (Button) findViewById(R.id.btnAgregarPaciente);
        btnAltaPaciente = (Button) findViewById(R.id.btnAltaPaciente);
        llAltaPaciente = (LinearLayout) findViewById(R.id.llAltaPaciente);
        llAgregarPaciente = (LinearLayout) findViewById(R.id.llAgregarPaciente);*/

        //agregamos la propiedad para solo aceptar mayusculas a los EditText que lo requieran. asi como delimitar el numero de caracteres que se aceptan en cada campo.
        txtNombre.setFilters(new InputFilter[]{new InputFilter.AllCaps(),new InputFilter.LengthFilter(255)});
        txtApellidoPaterno.setFilters(new InputFilter[]{new InputFilter.AllCaps(),new InputFilter.LengthFilter(255)});
        txtApellidoMaterno.setFilters(new InputFilter[]{new InputFilter.AllCaps(),new InputFilter.LengthFilter(255)});
        /*txtSeccion.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});
        txtTelefonoCasa.setFilters(new InputFilter[]{new InputFilter.LengthFilter(255)});
        txtTelefonoMovil.setFilters(new InputFilter[]{new InputFilter.LengthFilter(255)});
        txtCorreo.setFilters(new InputFilter[]{new InputFilter.AllCaps(),new InputFilter.LengthFilter(255)});*/

        //establecer focus a los campos del formulario.
        txtNombre.setNextFocusDownId(R.id.txtApellidoPaterno);
        txtApellidoPaterno.setNextFocusDownId(R.id.txtApellidoMaterno);
        txtApellidoMaterno.setNextFocusDownId(R.id.txtFechaDeNacimiento);
        /*txtSeccion.setNextFocusDownId(R.id.txtTelefonoCasa);
        txtTelefonoCasa.setNextFocusDownId(R.id.txtTelefonoMovil);
        txtTelefonoMovil.setNextFocusDownId(R.id.txtCorreo);*/

        txtFechaDeNacimiento.setInputType(InputType.TYPE_NULL);
        txtFechaDeNacimiento.setKeyListener(null);
        txtFechaDeNacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(oDiaglogFragment == null){
                    if (txtFechaDeNacimiento.getText().toString().length() > 0) {
                        //el campo fechaDeNacimiento contiene valores.
                        String[] date = txtFechaDeNacimiento.getText().toString().split("-");
                        oDiaglogFragment = DatePickerFragment.newInstance(Integer.parseInt(date[0]), Integer.parseInt(date[1]) - 1, Integer.parseInt(date[2]));
                        oDiaglogFragment.show(getSupportFragmentManager(), "Date Picker");
                    } else {
                        final Calendar c = Calendar.getInstance();
                        int year = c.get(Calendar.YEAR);
                        int month = c.get(Calendar.MONTH);
                        int day = c.get(Calendar.DAY_OF_MONTH);

                        oDiaglogFragment = DatePickerFragment.newInstance(year, month, day);
                        oDiaglogFragment.show(getSupportFragmentManager(), "Date Picker");
                    }
                }
            }
        });

        //spinner sexo
        oAdapterSexo = new AdapterSexo(this,R.layout.item_selected_generic_spinner,lstSexo);
        spnSexo.setAdapter(oAdapterSexo);

        obtenerDatosSpinnerAgregarAfiliacion("https://applicationpas2.000webhostapp.com/Aplicacion1/ApiAndroid.php");


        Button btnPrueba = findViewById(R.id.btnPrueba);
        btnPrueba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonaAfiliacion personaAfiliacion2 = personaAfiliacion;
                String os = "";
            }
        });
    }

    public static void inicializeVarDiaglogFragment(){
        oDiaglogFragment = null;
    }

    private void obtenerDatosSpinnerAgregarAfiliacion(String url){
        String REQUEST_TAG_OBTENER_DATOS_SPINNERS = "AGREGARNUEVAAFILIACION.OBTENERDATOSSPINNERAGREGARAFILIACION";

        pdDescargaDatosSpinner = new ProgressDialog(MainActivity.this);
        pdDescargaDatosSpinner.setMessage(MainActivity.this.getString(R.string.strBusquedaDePaciente));
        pdDescargaDatosSpinner.setCancelable(false);
        pdDescargaDatosSpinner.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdDescargaDatosSpinner.setIndeterminateDrawable(getResources().getDrawable(R.drawable.custom_progress_dialog));
        pdDescargaDatosSpinner.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(pdDescargaDatosSpinner.isShowing())
                            pdDescargaDatosSpinner.dismiss();

                        obtenerSexos();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                        if(pdDescargaDatosSpinner.isShowing())
                            pdDescargaDatosSpinner.dismiss();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put(getResources().getString(R.string.strIdentificadorDispositivo),Utilerias.getIMEI(getApplicationContext()));
                params.put(getResources().getString(R.string.strControlApi), getResources().getString(R.string.strUrlLlenarDatosSpinner));
                return params;
            }
        };
        // Adding JsonObject request to request queue
        volleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest,REQUEST_TAG_OBTENER_DATOS_SPINNERS);
    }
    private void obtenerSexos(){
        lstSexo.clear();
        try {
            JSONObject oInit = new JSONObject();
            oInit.put("id_sexo", 0);
            oInit.put("nombre", "SELECCIONE EL SEXO");

            JSONObject oHombre = new JSONObject();
            oHombre.put("id_sexo",1);
            oHombre.put("nombre","HOMBRE");

            JSONObject oMujer = new JSONObject();
            oMujer.put("id_sexo",2);
            oMujer.put("nombre","MUJER");

            lstSexo.add(oInit);
            lstSexo.add(oHombre);
            lstSexo.add(oMujer);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (oAdapterSexo != null) {
            oAdapterSexo.notifyDataSetChanged();
        }

        spnSexo.post(new Runnable() {
            @Override
            public void run() {
                spnSexo.setSelection(personaAfiliacion.getSelectedItemPositionSexo(),true);
            }
        });
    }

    private class AdapterSexo extends ArrayAdapter<JSONObject> {
        private ArrayList<JSONObject> sexos;

        AdapterSexo(Context context, int textViewResourceId,List<JSONObject> prSexos) {
            super(context, textViewResourceId, prSexos);
            this.sexos = (ArrayList<JSONObject>) prSexos;
        }
        @Override
        public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
            View v = convertView;
            final ViewHolderDrop holder;
            //sexo oSexoSelected = (sexo) spnSexo.getSelectedItem();
            if (v == null) {
                LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.item_generic_spinner, parent, false);

                holder = new ViewHolderDrop();
                holder.txtDividerHead = (TextView) v.findViewById(R.id.txtDividerHead);

                holder.txtNombre = (TextView) v.findViewById(R.id.txtNombre);
                /*if(oSexoSelected.getId_sexo().equals(sexos.get(position).getId_sexo()))
                    //holder.txtNombre.setBackgroundColor(getColor(R.color.SpinnerBackgroundColor));
                    //holder.txtNombre.setBackgroundColor(getApplicationContext().getColor(R.color.SpinnerBackgroundColor));
                    holder.txtNombre.setBackgroundColor(getResources().getColor(R.color.SpinnerBackgroundColor));
                else
                    holder.txtNombre.setBackgroundColor(Color.WHITE);*/
                //holder.txtNombre.setPadding(25,5,5,5);
                v.setTag(holder);
            }
            else{
                holder = (ViewHolderDrop) v.getTag();
                /*if(oSexoSelected.getId_sexo().equals(sexos.get(position).getId_sexo()))
                    holder.txtNombre.setBackgroundColor(getResources().getColor(R.color.SpinnerBackgroundColor));
                else
                    holder.txtNombre.setBackgroundColor(Color.WHITE);*/
            }

            try {
                if (this.sexos.get(position).getString("nombre") != null)
                {
                    holder.txtNombre.setText(this.sexos.get(position).getString("nombre"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return v;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            View v = convertView;
            final ViewHolder holder;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.item_selected_generic_spinner, parent, false);
                holder = new ViewHolder();
                holder.txtNombre = (TextView) v.findViewById(R.id.txtNombre);
                v.setTag(holder);
            }
            else
                holder = (ViewHolder)v.getTag();

            try {
                if (this.sexos.get(position).getString("nombre") != null)
                {
                    holder.txtNombre.setText(this.sexos.get(position).getString("nombre"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return v;
        }
    }

    private class ViewHolder    {
        TextView txtNombre;
    }
    private class ViewHolderDrop {
        TextView txtDividerHead;
        TextView txtNombre;
    }
}
