package com.ems_development.congreso_pccf.fragments.location;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import com.ems_development.congreso_pccf.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapFragment extends Fragment implements OnMapReadyCallback {

    private View root;
    private GoogleMap map;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_map, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return root;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        updateMap();
    }

    public void updateMap (){
        getActivity().findViewById(R.id.nav_view).setVisibility(View.GONE);

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)){
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 9999);
            return;
        }
        this.map.setMyLocationEnabled(true);

        LocationManager locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(location.getLatitude(), location.getLongitude())).zoom(14.0f).build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        map.moveCamera(cameraUpdate);
        map.getUiSettings().setZoomControlsEnabled(true);

        //TODO Modificar el latlng una vez que se disponga de la ubicacion del congreso
        map.addMarker(new MarkerOptions().position(new LatLng(-31.617375, -60.674830)).title("Congreso Argentino"));


        //TODO trazar ruta
        //https://developers.google.com/maps/documentation/directions/intro?hl=es-419#Waypoints
        //https://developers.google.com/maps/documentation/directions/start?hl=es-419
        //https://www.youtube.com/watch?v=YNM_-cR9QKQ
        LatLng myPosition = new LatLng(location.getLatitude(), location.getLongitude());
        LatLng congressPosition = new LatLng(-31.617375, -60.674830);
        traceRout(myPosition, congressPosition);
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().findViewById(R.id.nav_view).setVisibility(View.VISIBLE);
    }

    public void traceRout(LatLng origin, LatLng destiny){

    }
}