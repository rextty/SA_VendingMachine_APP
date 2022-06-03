package com.example.sa_vendingmachine_app.Service;


import com.example.sa_vendingmachine_app.Model.DBMgr;
import com.example.sa_vendingmachine_app.Model.Entity.MarkerEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MapService {

    private DBMgr dbMgr = new DBMgr();

    public MapService() {}

    public ArrayList<MarkerEntity> getVendingMachineInformation() {
        ResultSet resultSet = dbMgr.getMachineInformation();

        ArrayList<MarkerEntity> markers = new ArrayList<>();

        try {
            while (resultSet.next()) {
                MarkerEntity marker = new MarkerEntity();
                marker.setLat(resultSet.getDouble("ST_X(location)"));
                marker.setLng(resultSet.getDouble("ST_Y(location)"));
                marker.setName(resultSet.getString("name"));
                marker.setState(resultSet.getString("state"));

                markers.add(marker);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return markers;
    }
}
