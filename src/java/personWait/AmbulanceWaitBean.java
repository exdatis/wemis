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
package personWait;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import net.exdatis.operator.CurrentUserBean;
import net.exdatis.waitingRoom.AmbulanceRoom;
import net.exdatis.wdb.Wdb;

/**
 *
 * @author morar
 */

@ManagedBean(name = "ambulanceWaitBean", eager = true)
@ViewScoped
public class AmbulanceWaitBean implements Serializable {
    
    // za db
    private final String currentUser = CurrentUserBean.getDB_USER();
    private final String currentPwd = CurrentUserBean.getDB_PWD();
    private final String currentHost = CurrentUserBean.getDB_HOST();
    
    private int awId;
    private Timestamp awTime;
    private int awPriority;
    private int awRoom;
    private int awPerson;
    private int awStatus;
    private String dbUser;
    private String roomCode;
    private String roomName;
    private String personName;
    private String personJMBG;
    private String personLBO;
    private String personHealthCard;
    
    private final Connection connection = Wdb.getDbConnection(this.currentUser, this.currentPwd, this.currentHost);
    /**
     * Lista prioriteta za comboBox;
     */
    private Map<String, Object> priorities = AmbulanceWait.getPriorityMap();
    
    /**
     * Lista ambulatnih cekaonica za comboBox.
     */
    private Map<String, Object> rooms = AmbulanceRoom.roomMap(connection);
    
    /**
     * Lista kreiranih cekanja(ambulanta). Var ce biti wait,
     */
    private ArrayList<AmbulanceWait> standby;
    
    
    
    
}
