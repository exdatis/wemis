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
package net.exdatis.medicineOfWork;

import java.sql.Timestamp;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import net.exdatis.operator.CurrentUserBean;

/**
 *
 * @author morar
 */

@ManagedBean(name="mowBean", eager = true)
@ViewScoped
public class MOWwaitBean {
    
    // za db
    private final String currentUser = CurrentUserBean.getDB_USER();
    private final String currentPwd = CurrentUserBean.getDB_PWD();
    private final String currentHost = CurrentUserBean.getDB_HOST();

    // iz tabele
    private int waitId;
    private Timestamp waitTime;
    private int waitReason;
    private int waitPerson;
    
    // dodatak iz pogleda
    private String roomName;
    private String personName;
    private String personJMBG;    
}
