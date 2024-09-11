package com.upb.sgd.dataserver.database.infrastructure.mariadb;

import com.upb.sgd.dataserver.database.domain.port.driven.DatabaseServicePort;

import java.sql.Connection;

public class MariaDBService   {

    private final Connection connection;

    public MariaDBService(Connection connection){
        this.connection = connection;
    }


}
