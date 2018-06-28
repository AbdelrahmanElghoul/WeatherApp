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
                    String name=response.getJSONObject( "Location" ).getString( "name" );
                    String region=response.getJSONObject( "Location" ).getString( "region" );
                    String country=response.getJSONObject( "Location" ).getString( "country" );
                    double lat=response.getJSONObject( "Location" ).getDouble( "lat" );
                    double lon=response.getJSONObject( "Location" ).getDouble( "lon" );
                    String tz_id=response.getJSONObject( "Location" ).getString( "tz_id" );
                    int localtime_epoch=response.getJSONObject( "Location" ).getInt( "localtime_epoch" );
                    String localtime=response.getJSONObject( "Location" ).getString( "localtime" );

                    data.setLocation( new CityTemperatureData.Location
                            (name,region,country,lat,lon,tz_id,localtime_epoch,localtime  ) );

                    //Current
                     int last_updated_epoch=response.getJSONObject( "Current" ).getInt( "last_updated_epoch" );
                     String last_updated=response.getJSONObject( "Current" ).getString( "last_updated" );
                     double temp_c=response.getJSONObject( "Current" ).getDouble( "temp_c" );
                     double temp_f=response.getJSONObject( "Current" ).getDouble( "temp_f" );
                     int is_day=response.getJSONObject( "Current" ).getInt( "is_day" );


                     //Condition condition;
                    String Condition_text=response.getJSONObject( "Current" ).getJSONObject( "Condition" ).getString( "text" );
                    String Condition_icon=response.getJSONObject( "Current" ).getJSONObject( "Condition" ).getString( "icon" );
                    int Condition_code=response.getJSONObject( "Current" ).getJSONObject( "Condition" ).getInt( "code" );


                     double wind_mph=response.getJSONObject( "Current" ).getDouble( "wind_mph" );
                     double wind_kph=response.getJSONObject( "Current" ).getDouble( "wind_kph" );
                     int wind_degree=response.getJSONObject( "Current" ).getInt( "wind_degree" );
                     String wind_dir=response.getJSONObject( "Current" ).getString( "wind_dir" );
                     double pressure_mb=response.getJSONObject( "Current" ).getDouble( "pressure_mb" );
                     double pressure_in=response.getJSONObject( "Current" ).getDouble( "pressure_in" );
                     double precip_mm=response.getJSONObject( "Current" ).getDouble( "precip_mm" );
                     double precip_in=response.getJSONObject( "Current" ).getDouble( "precip_in" );
                     int humidity=response.getJSONObject( "Current" ).getInt( "humidity" );
                     int cloud=response.getJSONObject( "Current" ).getInt( "cloud" );
                     double feelslike_c=response.getJSONObject( "Current" ).getDouble( "feelslike_c" );
                     double feelslike_f=response.getJSONObject( "Current" ).getDouble( "feelslike_f" );
                     double vis_km=response.getJSONObject( "Current" ).getDouble( "vis_km" );
                    double vis_miles=response.getJSONObject( "Current" ).getDouble( "vis_miles" );

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
                           double maxtemp_c=object.getJSONObject( "Day" ).getDouble( "maxtemp_c" );
                           double maxtemp_f=object.getJSONObject( "Day" ).getDouble( "maxtemp_f" );

                           double mintemp_c=object.getJSONObject( "Day" ).getDouble( "mintemp_c" );
                           double mintemp_f=object.getJSONObject( "Day" ).getDouble( "mintemp_f" );

                           double avgtemp_c=object.getJSONObject( "Day" ).getDouble( "avgtemp_c" );
                           double avgtemp_f=object.getJSONObject( "Day" ).getDouble( "avgtemp_f" );

                           double maxwind_mph=object.getJSONObject( "Day" ).getDouble( "maxwind_mph" );
                           double maxwind_kph=object.getJSONObject( "Day" ).getDouble( "maxwind_kph" );

                           double totalprecip_mm=object.getJSONObject( "Day" ).getDouble( "totalprecip_mm" );
                           double totalprecip_in=object.getJSONObject( "Day" ).getDouble( "totalprecip_in" );

                           double avgvis_km=object.getJSONObject( "Day" ).getDouble( "avgvis_km" );
                           double avgvis_miles=object.getJSONObject( "Day" ).getDouble( "avgvis_miles" );

                           double avghumidity=object.getJSONObject( "Day" ).getDouble( "avghumidity" );

                           double uv=object.getJSONObject( "Day" ).getDouble( "uv" );

                          // ConditionX
                          String text=object.getJSONObject( "Day" ).getJSONObject( "ConditionX" ).getString( "text");
                          String icon=object.getJSONObject( "Day" ).getJSONObject( "ConditionX" ).getString( "icon");
                          int code=object.getJSONObject( "Day" ).getJSONObject( "ConditionX" ).getInt( "code");

                          //Astro
                           String sunrise=object.getJSONObject( "Astro" ).getString( "sunrise" );
                           String sunset=object.getJSONObject( "Astro" ).getString( "sunset" );
                           String moonrise=object.getJSONObject( "Astro" ).getString( "moonrise" );
                           String moonset=object.getJSONObject( "Astro" ).getString( "moonset" );

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
