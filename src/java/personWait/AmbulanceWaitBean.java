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

import java.sql.Timestamp;
import java.util.Map;

/**
 *
 * @author morar
 */
public class AmbulanceWaitBean {
    
    private int waitId;
    private Timestamp waitTime;
    private int waitPriority;
    private int waitRoom;
    private int waitPersonId;
    private int waitStatus;
    private String dbUser;
    private String roomCode;
    private String roomName;
    private String personName;
    private String personJMBG;
    private String personLBO;
    private String personHealthCard;
    
    private Map<String, Object> priorities;
    
    
    
    
}
