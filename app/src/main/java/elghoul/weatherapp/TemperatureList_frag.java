package elghoul.weatherapp;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class TemperatureList_frag extends Fragment {
    RecyclerView TempRecyclerView;
    CityTemperatureData data;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       return inflater.inflate( R.layout.fragment_temperature_list_frag,container,false );
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated( savedInstanceState );
        TempRecyclerView=getActivity().findViewById( R.id.TempRecyclerView );
        TempRecyclerView.setHasFixedSize( false );

        data=getArguments().getParcelable( "Obj" );
       // TempRecyclerView.setLayoutManager( new GridLayoutManager( getActivity(),data.getForecast().getForecastday().size()));
        TempRecyclerView.setLayoutManager( new LinearLayoutManager( getActivity()));
        TempRecyclerView.setAdapter( new TemperatureAdapter( getActivity(),data ));
    }
}
