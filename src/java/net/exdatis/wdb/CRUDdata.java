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
import java.sql.SQLException;

/**
 * Implementiraj perzistenciju.
 * @author morar
 */
public interface CRUDdata {
    
    /**
     * Sacuvaj novi zapis
     * @param connection (db connection)
     * @return (poruka o gresci ili uspehu)
     * @throws java.sql.SQLException
     */
    public String insertRec(Connection connection) throws SQLException;
    
    /**
     * Sacuvaj izmene na zapisu.
     * @param connection (db connection)
     * @return (poruka o gresci ili uspehu)
     * @throws java.sql.SQLException
     */
    public String updateRec(Connection connection) throws SQLException;
    
    /**
     * Brisanje zapisa.
     * @param connection (db connection)
     * @return (poruka o gresci ili uspehu)
     * @throws java.sql.SQLException
     */
    public String deleteRec(Connection connection) throws SQLException;
    
}
