/*
 * Copyright (C) 2016 morar
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package net.exdatis.wdb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Klasa sa statickom metodom za dobijanje konekcije sa bazom podataka.
 * @author morar
 */
public final class Wdb {
    
    private static final String DB_PORT = "3306";
    private static final String DB_NAME = "wemis";

    private Wdb() {
    }
    
    public static Connection getDbConnection(String userName, String userPassword, String dbHost) throws ClassNotFoundException{
        Class.forName("com.mysql.jdbc.Driver"); // ucitaj biblioteku
        Connection connection = null;
        
        String url = "jdbc:mysql://" + dbHost + ":" + DB_PORT + "/" + DB_NAME;
        
        try{
            connection = DriverManager.getConnection(url, userName, userPassword);
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return connection;
        }
        
        return connection;
    }
    
}
