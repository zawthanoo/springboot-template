package org.mutu.example.common;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import io.r2dbc.spi.ColumnMetadata;
import io.r2dbc.spi.Row;

import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class ReactiveBeanPropertyRowMapper<T> implements Function<Row, T> {

    private final Class<T> targetType;
    private final Map<String, PropertyDescriptor> propertyMap = new HashMap<>();

    public ReactiveBeanPropertyRowMapper(Class<T> targetType) {
        this.targetType = targetType;
        initPropertyMap();
    }

    private void initPropertyMap() {
        BeanWrapperImpl wrapper = new BeanWrapperImpl(targetType);
        for (PropertyDescriptor pd : wrapper.getPropertyDescriptors()) {
            if (pd.getWriteMethod() != null) {
                // Map both camelCase and snake_case versions
                propertyMap.put(pd.getName().toLowerCase(), pd);
                propertyMap.put(toSnakeCase(pd.getName()).toLowerCase(), pd);
            }
        }
    }

    @Override
    public T apply(Row row) {
        try {
            T instance = targetType.getDeclaredConstructor().newInstance();
            BeanWrapper bw = new BeanWrapperImpl(instance);

            for (ColumnMetadata columnMetadata : row.getMetadata().getColumnMetadatas()) {
                String column = columnMetadata.getName();
                String normalizedColumn = column.toLowerCase();
                PropertyDescriptor pd = propertyMap.get(normalizedColumn);

                if (pd != null) {
                    Object value = row.get(column, pd.getPropertyType());
                    if (value != null) {
                        bw.setPropertyValue(pd.getName(), value);
                    }
                }
            }
            return instance;
        } catch (Exception e) {
            throw new RuntimeException("Mapping failed for " + targetType.getName(), e);
        }
    }

    private String toSnakeCase(String propertyName) {
        return propertyName.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
    }
}