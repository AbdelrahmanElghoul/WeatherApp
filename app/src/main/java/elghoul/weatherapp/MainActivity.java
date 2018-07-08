package elghoul.weatherapp;

import android.Manifest;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.VoiceInteractor;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    static List<String> CityFound;//list of CityFound (Region,Country) to be Displayed to txtCity
    static List<SearchCity> CityName;//List of CityFound found by name
    AutoCompleteTextView txtCity;
    Spinner spinner;
    ImageView imgLocate;

    LocationManager locationManager;
    LocationListener locationListener;
    Rest_API api;

     RequestQueue requestQueue ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        requestQueue= Volley.newRequestQueue( MainActivity.this );
        txtCity = findViewById( R.id.txtCity );

        spinner = findViewById( R.id.DaysSpinner );
        imgLocate = findViewById( R.id.imgLocation );

        api = new Rest_API();
        CityName = new ArrayList<>();
        CityFound = new ArrayList<>();

        spinner.setSelection( 2 );

        txtCity.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {//when user change text not tje

                txtCity.clearListSelection();

                if (txtCity.isPerformingCompletion()) {
                    return;
                }
                requestQueue.cancelAll( requestQueue );
                api.Search( charSequence.toString(), txtCity,requestQueue );
            }


            @Override
            public void afterTextChanged(Editable editable) {

            }
        } );


        txtCity.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                txtCity.dismissDropDown();
                api.Temperature( CityName.get( i ).getLat(), CityName.get( i ).getLon(), spinner.getSelectedItemPosition() + 1 );
            }
        } );


        imgLocate.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                locationManager = (LocationManager) getSystemService( LOCATION_SERVICE );
                locationListener = new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        api.Temperature( location.getLatitude(), location.getLongitude(), spinner.getSelectedItemPosition() + 1 );

                    }

                    @Override
                    public void onStatusChanged(String s, int i, Bundle bundle) {

                    }

                    @Override
                    public void onProviderEnabled(String s) {

                        Toast.makeText( MainActivity.this, "Searching for GPS that may take a while", Toast.LENGTH_LONG ).show();

                    }

                    @Override
                    public void onProviderDisabled(String s) {
                        AlertDialog.Builder alertDialog=new AlertDialog.Builder( MainActivity.this );
                        alertDialog.setMessage( "Your GPS is off \n please turn it on to continue" )
                                .setIcon( null )
                                .setTitle(null )
                                .setPositiveButton( "Go to Setting", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        Intent intent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS );
                                        startActivity( intent );

                                    }
                                } ).setNegativeButton( "Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        } ).show();

                    }
                };
                configureButton();
            }
        } );


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 10:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    configureButton();
                    return;
                }
        }
    }

    private void configureButton() {

        try {

            boolean Case = (ActivityCompat.checkSelfPermission( MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION )
                    != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission( MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION )
                    != PackageManager.PERMISSION_GRANTED);

            if (!Case)

            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions( new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET
                    }, 10 );

                    return;
                } else {
                    locationManager.requestLocationUpdates( "gps", 0, 10000, locationListener );//refresh time in miliseconds,Distance in meters
                }
            }

        } catch (Exception e) {
            Toast.makeText( this, e.getMessage(), Toast.LENGTH_SHORT ).show();
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate( R.menu.setting,menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()){
            case R.id.AvgHumidity:
                item.setChecked( !item.isChecked() );
                return true;

        default:return true;
       }

    }


    class Rest_API {

        public void Search(String City, final AutoCompleteTextView autoCompleteTextView,RequestQueue requestQueue) {

if(City.isEmpty()){
    return;
}
            CityName.clear();
            CityFound.clear();
            final List<Double> list = new ArrayList<>();


            String API = "http://api.apixu.com/v1/search.json?key=" + getString( R.string.API_KEY ) + "&q=" + City.replace( " ","_" );

            JsonArrayRequest arrayRequest = new JsonArrayRequest( Request.Method.GET
                    , API
                    , null
                    , new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject object = response.getJSONObject( i );

                            String name = object.getString( "name" );
                            String region = object.getString( "region" );
                            String country = object.getString( "country" );
                            double lat = object.getDouble( "lat" );
                            if (list.contains( lat )|| CityFound.contains( region + "," + country )) {
                                continue;
                            }
                            list.add( lat );
                            double lon = object.getDouble( "lon" );
                            CityFound.add( region + "," + country );

                            CityName.add( new SearchCity( name, region, country, lat, lon ) );
                        }
                        autoCompleteTextView.setAdapter( null );
                        final ArrayAdapter<String> adapter = new ArrayAdapter<>( MainActivity.this, android.R.layout.simple_dropdown_item_1line, CityFound);
                        autoCompleteTextView.setAdapter( adapter );
                        autoCompleteTextView.showDropDown();
                        autoCompleteTextView.setOnClickListener( new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (!CityFound.isEmpty())
                                    autoCompleteTextView.setAdapter( adapter );
                                autoCompleteTextView.showDropDown();
                            }
                        } );


                    } catch (Exception e) {
                        if(!e.getMessage().isEmpty())
                        Toast.makeText( MainActivity.this, e.getMessage(), Toast.LENGTH_LONG ).show();
                    }
                }
            }
                    , new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if(!error.getMessage().isEmpty())
                    Toast.makeText( MainActivity.this, error.getMessage(), Toast.LENGTH_LONG ).show();
                }
            }
            );
            requestQueue.add( arrayRequest );


        }


        public void Temperature(double lat, double lon, int days) {

            txtCity.dismissDropDown();


            final CityTemperatureData data = new CityTemperatureData();
            String URL = "http://api.apixu.com/v1/forecast.json?key=" + getString( R.string.API_KEY ) + "&q=" + lat + "," + lon + "&days=" + days;
            final RequestQueue requestQueue = Volley.newRequestQueue( MainActivity.this );


            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest( Request.Method.GET
                    , URL
                    , null
                    , new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        //Location
                        String name = response.getJSONObject( "location" ).getString( "name" );
                        String region = response.getJSONObject( "location" ).getString( "region" );
                        String country = response.getJSONObject( "location" ).getString( "country" );
                        double lat = response.getJSONObject( "location" ).getDouble( "lat" );
                        double lon = response.getJSONObject( "location" ).getDouble( "lon" );
                        String tz_id = response.getJSONObject( "location" ).getString( "tz_id" );
                        int localtime_epoch = response.getJSONObject( "location" ).getInt( "localtime_epoch" );
                        String localtime = response.getJSONObject( "location" ).getString( "localtime" );




                        if(txtCity.getText().toString().length()==0){
                            txtCity.setText( region+","+country );
                            txtCity.dismissDropDown();
                        }
                        data.setLocation( new CityTemperatureData.Location
                                ( name, region, country, lat, lon, tz_id, localtime_epoch, localtime ) );

                        //Current
                        int last_updated_epoch = response.getJSONObject( "current" ).getInt( "last_updated_epoch" );
                        String last_updated = response.getJSONObject( "current" ).getString( "last_updated" );
                        double temp_c = response.getJSONObject( "current" ).getDouble( "temp_c" );
                        double temp_f = response.getJSONObject( "current" ).getDouble( "temp_f" );
                        int is_day = response.getJSONObject( "current" ).getInt( "is_day" );


                        //Condition condition;
                        String Condition_text = response.getJSONObject( "current" ).getJSONObject( "condition" ).getString( "text" );
                        String Condition_icon = "http:" + response.getJSONObject( "current" ).getJSONObject( "condition" ).getString( "icon" );
                        int Condition_code = response.getJSONObject( "current" ).getJSONObject( "condition" ).getInt( "code" );


                        double wind_mph = response.getJSONObject( "current" ).getDouble( "wind_mph" );
                        double wind_kph = response.getJSONObject( "current" ).getDouble( "wind_kph" );
                        int wind_degree = response.getJSONObject( "current" ).getInt( "wind_degree" );
                        String wind_dir = response.getJSONObject( "current" ).getString( "wind_dir" );
                        double pressure_mb = response.getJSONObject( "current" ).getDouble( "pressure_mb" );
                        double pressure_in = response.getJSONObject( "current" ).getDouble( "pressure_in" );
                        double precip_mm = response.getJSONObject( "current" ).getDouble( "precip_mm" );
                        double precip_in = response.getJSONObject( "current" ).getDouble( "precip_in" );
                        int humidity = response.getJSONObject( "current" ).getInt( "humidity" );
                        int cloud = response.getJSONObject( "current" ).getInt( "cloud" );
                        double feelslike_c = response.getJSONObject( "current" ).getDouble( "feelslike_c" );
                        double feelslike_f = response.getJSONObject( "current" ).getDouble( "feelslike_f" );


                        data.setCurrent( new CityTemperatureData.Current( last_updated_epoch, last_updated, temp_c
                                , temp_f, is_day, Condition_text, Condition_icon, Condition_code,
                                wind_mph, wind_kph, wind_degree, wind_dir, pressure_mb, pressure_in, precip_mm,
                                precip_in, humidity, cloud, feelslike_c, feelslike_f  ) );

                        List<CityTemperatureData.Forecast.Forecastday> forecastday = new ArrayList<>();
                        for (int i = 0; i < response.getJSONObject( "forecast" ).getJSONArray( "forecastday" ).length(); i++) {
                            JSONObject object = response.getJSONObject( "forecast" ).getJSONArray( "forecastday" ).getJSONObject( i );
                            String date = object.getString( "date" );
                            int date_epoch = object.getInt( "date_epoch" );

                            //Day
                            double maxtemp_c = object.getJSONObject( "day" ).getDouble( "maxtemp_c" );
                            double maxtemp_f = object.getJSONObject( "day" ).getDouble( "maxtemp_f" );

                            double mintemp_c = object.getJSONObject( "day" ).getDouble( "mintemp_c" );
                            double mintemp_f = object.getJSONObject( "day" ).getDouble( "mintemp_f" );

                            double avgtemp_c = object.getJSONObject( "day" ).getDouble( "avgtemp_c" );
                            double avgtemp_f = object.getJSONObject( "day" ).getDouble( "avgtemp_f" );

                            double maxwind_mph = object.getJSONObject( "day" ).getDouble( "maxwind_mph" );
                            double maxwind_kph = object.getJSONObject( "day" ).getDouble( "maxwind_kph" );

                            double totalprecip_mm = object.getJSONObject( "day" ).getDouble( "totalprecip_mm" );
                            double totalprecip_in = object.getJSONObject( "day" ).getDouble( "totalprecip_in" );

                            double avghumidity = object.getJSONObject( "day" ).getDouble( "avghumidity" );



                            // ConditionX
                            String text = object.getJSONObject( "day" ).getJSONObject( "condition" ).getString( "text" );
                            String icon = "http:" + object.getJSONObject( "day" ).getJSONObject( "condition" ).getString( "icon" );
                            int code = object.getJSONObject( "day" ).getJSONObject( "condition" ).getInt( "code" );

                            //Astro
                            String sunrise = object.getJSONObject( "astro" ).getString( "sunrise" );
                            String sunset = object.getJSONObject( "astro" ).getString( "sunset" );
                            String moonrise = object.getJSONObject( "astro" ).getString( "moonrise" );
                            String moonset = object.getJSONObject( "astro" ).getString( "moonset" );

                            forecastday.add( new CityTemperatureData.Forecast.Forecastday(
                                    date, date_epoch, maxtemp_c, maxtemp_f, mintemp_c, mintemp_f, avgtemp_c, avgtemp_f, maxwind_mph, maxwind_kph
                                    , totalprecip_mm, totalprecip_in,  avghumidity, text, icon, code, sunrise, sunset
                                    , moonrise, moonset )
                            );
                        }
                        data.setForecast( new CityTemperatureData.Forecast( forecastday ) );


                        FragmentManager TempManager = getFragmentManager();
                        TemperatureList_frag temperatureList_frag = new TemperatureList_frag();

                        Bundle bundle = new Bundle();
                        bundle.putParcelable( "Obj", data );
                        temperatureList_frag.setArguments( bundle );

                        FragmentTransaction fragmentTransaction = TempManager.beginTransaction();
                        fragmentTransaction.replace( R.id.fragTemp, temperatureList_frag );
                        fragmentTransaction.commit();


                    } catch (JSONException e) {
                        if(!e.getMessage().isEmpty())
                        Toast.makeText( MainActivity.this, e.getMessage(), Toast.LENGTH_LONG ).show();
                    }
                }
            }
                    , new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if(!error.getMessage().isEmpty())
                    Toast.makeText( MainActivity.this, error.getMessage(), Toast.LENGTH_LONG ).show();
                }
            } );
            requestQueue.add( jsonObjectRequest );


        }

        ;
    }


}
