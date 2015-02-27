package com.vaadin.grails.datasource

import com.vaadin.data.util.sqlcontainer.connection.SimpleJDBCConnectionPool
import com.vaadin.grails.Grails
import org.codehaus.groovy.grails.commons.GrailsApplication

import java.sql.SQLException

class GrailsConnectionPool extends SimpleJDBCConnectionPool {

    GrailsConnectionPool() throws SQLException {
        super(
                Grails.get(GrailsApplication).config.dataSource.driverClassName as String,
                Grails.get(GrailsApplication).config.dataSource.url as String,
                Grails.get(GrailsApplication).config.dataSource.username as String,
                Grails.get(GrailsApplication).config.dataSource.password as String
        )
    }
}
