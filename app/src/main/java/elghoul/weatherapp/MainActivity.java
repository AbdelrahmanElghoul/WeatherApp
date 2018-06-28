package elghoul.weatherapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.VoiceInteractor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.FrameLayout;
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

public class MainActivity extends AppCompatActivity {
    List<String> cities;//list of cities name to be Displayed to txtCity
    List<SearchCity> city;//List of cities found by name
    AutoCompleteTextView txtCity;
    Spinner spinner;
    Button btnSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        txtCity=findViewById( R.id.txtCity );
        btnSearch=findViewById( R.id.btnSearch );
        spinner=findViewById( R.id.DaysSpinner );

        city=new ArrayList<>();
        cities=new ArrayList<>(  );

        spinner.setSelection( 2 );

        btnSearch.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Search(txtCity.getText().toString(),txtCity  );
            }
        } );
        txtCity.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Temperature( city.get( i ).getLat(), city.get( i ).getLon(),spinner.getSelectedItemPosition()+1);
            }
        } );

    }

    public void Search(String City,final AutoCompleteTextView autoCompleteTextView){
        city.clear();
        cities.clear();
       final List<Double> list=new ArrayList<>( );


        String API="http://api.apixu.com/v1/search.json?key="+getString( R.string.API_KEY)+"&q="+City;
        final RequestQueue requestQueue= Volley.newRequestQueue(this);
        JsonArrayRequest arrayRequest=new JsonArrayRequest( Request.Method.GET
                , API
                , null
                , new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                for(int i=0;i<response.length();i++){
                    JSONObject object=response.getJSONObject( i );

                     String name=object.getString( "name" );
                     String region=object.getString( "region" );
                     String country=object.getString( "country" );
                     double lat=object.getDouble( "lat" );
                    if(list.contains( lat )){
                        continue;
                    }
                     list.add( lat );
                     double lon=object.getDouble( "lon" );
                     cities.add( region+","+country );

                   city.add( new SearchCity( name,region,country,lat,lon ) );
                    }

                   final ArrayAdapter<String> adapter= new ArrayAdapter<>( MainActivity.this,android.R.layout.simple_dropdown_item_1line,cities);
                     autoCompleteTextView.setAdapter( adapter );
                     autoCompleteTextView.showDropDown();
                     autoCompleteTextView.setOnClickListener( new View.OnClickListener() {
                         @Override
                         public void onClick(View view) {
                             if(!cities.isEmpty())
                             autoCompleteTextView.setAdapter( adapter );
                             autoCompleteTextView.showDropDown();
                         }
                     } );





                } catch (Exception e) {
                    Toast.makeText( MainActivity.this, e.getMessage(), Toast.LENGTH_LONG ).show();
                }
            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText( MainActivity.this, error.getMessage(), Toast.LENGTH_LONG ).show();
            }
        }
        );
        requestQueue.add( arrayRequest );
    }

    public void Temperature(double lat,double lon,int days){
            final CityTemperatureData data = new CityTemperatureData(  );
        String URL="http://api.apixu.com/v1/forecast.json?key="+getString( R.string.API_KEY )+"&q="+lat+","+lon+"&days="+ days;
        final RequestQueue requestQueue= Volley.newRequestQueue(this);


        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest( Request.Method.GET
                , URL
                , null
                , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //Location
                    String name=response.getJSONObject( "location" ).getString( "name" );
                    String region=response.getJSONObject( "location" ).getString( "region" );
                    String country=response.getJSONObject( "location" ).getString( "country" );
                    double lat=response.getJSONObject( "location" ).getDouble( "lat" );
                    double lon=response.getJSONObject( "location" ).getDouble( "lon" );
                    String tz_id=response.getJSONObject( "location" ).getString( "tz_id" );
                    int localtime_epoch=response.getJSONObject( "location" ).getInt( "localtime_epoch" );
                    String localtime=response.getJSONObject( "location" ).getString( "localtime" );

                    data.setLocation( new CityTemperatureData.Location
                            (name,region,country,lat,lon,tz_id,localtime_epoch,localtime  ) );

                    //Current
                     int last_updated_epoch=response.getJSONObject( "current" ).getInt( "last_updated_epoch" );
                     String last_updated=response.getJSONObject( "current" ).getString( "last_updated" );
                     double temp_c=response.getJSONObject( "current" ).getDouble( "temp_c" );
                     double temp_f=response.getJSONObject( "current" ).getDouble( "temp_f" );
                     int is_day=response.getJSONObject( "current" ).getInt( "is_day" );


                     //Condition condition;
                    String Condition_text=response.getJSONObject( "current" ).getJSONObject( "condition" ).getString( "text" );
                    String Condition_icon=response.getJSONObject( "current" ).getJSONObject( "condition" ).getString( "icon" );
                    int Condition_code=response.getJSONObject( "current" ).getJSONObject( "condition" ).getInt( "code" );


                     double wind_mph=response.getJSONObject( "current" ).getDouble( "wind_mph" );
                     double wind_kph=response.getJSONObject( "current" ).getDouble( "wind_kph" );
                     int wind_degree=response.getJSONObject( "current" ).getInt( "wind_degree" );
                     String wind_dir=response.getJSONObject( "current" ).getString( "wind_dir" );
                     double pressure_mb=response.getJSONObject( "current" ).getDouble( "pressure_mb" );
                     double pressure_in=response.getJSONObject( "current" ).getDouble( "pressure_in" );
                     double precip_mm=response.getJSONObject( "current" ).getDouble( "precip_mm" );
                     double precip_in=response.getJSONObject( "current" ).getDouble( "precip_in" );
                     int humidity=response.getJSONObject( "current" ).getInt( "humidity" );
                     int cloud=response.getJSONObject( "current" ).getInt( "cloud" );
                     double feelslike_c=response.getJSONObject( "current" ).getDouble( "feelslike_c" );
                     double feelslike_f=response.getJSONObject( "current" ).getDouble( "feelslike_f" );
                     double vis_km=response.getJSONObject( "current" ).getDouble( "vis_km" );
                    double vis_miles=response.getJSONObject( "current" ).getDouble( "vis_miles" );

                     data.setCurrent( new CityTemperatureData.Current(last_updated_epoch,last_updated,  temp_c
                             ,  temp_f,  is_day,    Condition_text, Condition_icon,  Condition_code,
                             wind_mph,  wind_kph,  wind_degree,  wind_dir,  pressure_mb,  pressure_in,  precip_mm,
                             precip_in,  humidity,  cloud,  feelslike_c,  feelslike_f,  vis_km, vis_miles));

                  List<CityTemperatureData.Forecast.Forecastday> forecastday=new ArrayList<>(  );
                    for(int i=0;i<response.getJSONObject( "forecast" ).getJSONArray("forecastday").length();i++)
                    {
                          JSONObject object=response.getJSONObject( "forecast" ).getJSONArray("forecastday").getJSONObject( i );
                          String date=object.getString( "date" );
                          int date_epoch=object.getInt( "date_epoch" );

                            //Day
                           double maxtemp_c=object.getJSONObject( "day" ).getDouble( "maxtemp_c" );
                           double maxtemp_f=object.getJSONObject( "day" ).getDouble( "maxtemp_f" );

                           double mintemp_c=object.getJSONObject( "day" ).getDouble( "mintemp_c" );
                           double mintemp_f=object.getJSONObject( "day" ).getDouble( "mintemp_f" );

                           double avgtemp_c=object.getJSONObject( "day" ).getDouble( "avgtemp_c" );
                           double avgtemp_f=object.getJSONObject( "day" ).getDouble( "avgtemp_f" );

                           double maxwind_mph=object.getJSONObject( "day" ).getDouble( "maxwind_mph" );
                           double maxwind_kph=object.getJSONObject( "day" ).getDouble( "maxwind_kph" );

                           double totalprecip_mm=object.getJSONObject( "day" ).getDouble( "totalprecip_mm" );
                           double totalprecip_in=object.getJSONObject( "day" ).getDouble( "totalprecip_in" );

                           double avgvis_km=object.getJSONObject( "day" ).getDouble( "avgvis_km" );
                           double avgvis_miles=object.getJSONObject( "day" ).getDouble( "avgvis_miles" );

                           double avghumidity=object.getJSONObject( "day" ).getDouble( "avghumidity" );

                           double uv=object.getJSONObject( "day" ).getDouble( "uv" );

                          // ConditionX
                          String text=object.getJSONObject( "day" ).getJSONObject( "condition" ).getString( "text");
                          String icon=object.getJSONObject( "day" ).getJSONObject( "condition" ).getString( "icon");
                          int code=object.getJSONObject( "day" ).getJSONObject( "condition" ).getInt( "code");

                          //Astro
                           String sunrise=object.getJSONObject( "astro" ).getString( "sunrise" );
                           String sunset=object.getJSONObject( "astro" ).getString( "sunset" );
                           String moonrise=object.getJSONObject( "astro" ).getString( "moonrise" );
                           String moonset=object.getJSONObject( "astro" ).getString( "moonset" );

                           forecastday.add(new CityTemperatureData.Forecast.Forecastday (
                                   date,  date_epoch, maxtemp_c,  maxtemp_f,  mintemp_c,  mintemp_f,  avgtemp_c,  avgtemp_f,  maxwind_mph,  maxwind_kph
                                   ,  totalprecip_mm,  totalprecip_in,  avgvis_km,  avgvis_miles,  avghumidity, text,  icon,  code,  uv, sunrise,  sunset
                                   ,  moonrise,  moonset)
                           );
                      }
                        data.setForecast(new CityTemperatureData.Forecast(forecastday));


                    FragmentManager TempManager=getFragmentManager();
                    TemperatureList_frag temperatureList_frag=new TemperatureList_frag();

                    Bundle bundle=new Bundle(  );
                    bundle.putParcelable( "Obj",data );
                    temperatureList_frag.setArguments( bundle );

                    FragmentTransaction fragmentTransaction=TempManager.beginTransaction();
                    fragmentTransaction.replace( R.id.fragTemp,temperatureList_frag );
                    fragmentTransaction.commit();




                } catch (JSONException e) {
                    Toast.makeText( MainActivity.this,e.getMessage(),Toast.LENGTH_LONG ).show();
                }
            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
Toast.makeText( MainActivity.this,error.getMessage(),Toast.LENGTH_LONG ).show();
            }
        } );
        requestQueue.add( jsonObjectRequest );


    };



}
