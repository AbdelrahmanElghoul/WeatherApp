package elghoul.weatherapp;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

public class TemperatureAdapter extends RecyclerView.Adapter<TemperatureAdapter.TemperatureViewHolder> {
private Context context;
CityTemperatureData tempData;

    public TemperatureAdapter(Context context,CityTemperatureData tempData) {
        this.context=context;
        this.tempData=tempData;
    }

    @Override
    public TemperatureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from( context );
       View view=inflater.inflate( R.layout.city_temperature,parent,false );
        return new TemperatureViewHolder( view );
    }

    @Override
    public void onBindViewHolder(TemperatureViewHolder holder, int position) {
       //holder.txtLocation.setText( tempData.getLocation().getRegion()+","+tempData.getLocation().getCountry());
        holder.txtDate.setText( tempData.getForecast().getForecastday().get( position ).getDate().toString() );
        holder.txtCondition.setText( tempData.getForecast().getForecastday().get( position ).getDay().getCondition().getText() );

            Picasso.Builder builder = new Picasso.Builder(context);
        builder.listener(new Picasso.Listener()
        {

            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                Toast.makeText( context, exception.getMessage(), Toast.LENGTH_LONG ).show();
            }
        });
        builder.build().load(tempData.getForecast().getForecastday().get( position ).getDay().getCondition().getIcon()).into(holder.imgTempState);


holder.txtWeather.setText( tempData.getForecast().getForecastday().get( position ).getDay().getMaxtemp_c()+" °"
        +"  |  "+
        tempData.getForecast().getForecastday().get( position ).getDay().getMintemp_c()+" °");


    }

    @Override
    public int getItemCount() {
        return this.tempData.getForecast().getForecastday().size();
    }



    class TemperatureViewHolder extends RecyclerView.ViewHolder{

        ImageView imgTempState;

        TextView txtCondition,txtLocation,txtDate,txtWeather;
    public TemperatureViewHolder(View itemView) {
        super( itemView );
        imgTempState=itemView.findViewById( R.id.imgTemp );

        txtCondition=itemView.findViewById( R.id.txtCondition );
        txtDate=itemView.findViewById( R.id.txtDate );
        //txtLocation=itemView.findViewById( R.id.txtLocation );
        txtWeather=itemView.findViewById( R.id.txtWeather );

    }
}
}
