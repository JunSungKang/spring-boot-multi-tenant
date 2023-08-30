package jskang.springboot.multitenant.database.datasource;

import jskang.springboot.multitenant.database.DatabaseContextHolder;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DataSourceRouting extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return DatabaseContextHolder.getCustomerContext();
    }
}
