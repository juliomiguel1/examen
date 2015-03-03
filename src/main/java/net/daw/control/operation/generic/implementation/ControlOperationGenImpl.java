/*
 * Copyright (C) July 2014 Rafael Aznar
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
package net.daw.control.operation.generic.implementation;

import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import net.daw.control.operation.publicinterface.ControlOperationInterface;
import net.daw.service.generic.implementation.TableServiceGenImpl;
import net.daw.connection.implementation.BoneConnectionPoolImpl;
import net.daw.connection.publicinterface.ConnectionInterface;
import net.daw.helper.statics.ExceptionBooster;
import net.daw.helper.statics.FilterBeanHelper;
import net.daw.helper.statics.ParameterCook;
import net.daw.helper.statics.PermissionManager;

public class ControlOperationGenImpl implements ControlOperationInterface {

    protected ConnectionInterface DataConnectionSource = null;
    protected Connection connection = null;
    protected String strObject = null;    
    protected boolean perm=true;
    protected TableServiceGenImpl oService = null;

    public ControlOperationGenImpl(HttpServletRequest request) throws Exception {
        try {
            DataConnectionSource = new BoneConnectionPoolImpl();
            connection = DataConnectionSource.newConnection();
            strObject = ParameterCook.prepareObject(request);
            Constructor oConstructor = Class.forName("net.daw.service.generic.specific.implementation." + ParameterCook.prepareCamelCaseObject(request) + "ServiceGenSpImpl").getConstructor(String.class, String.class, Connection.class);
            oService = (TableServiceGenImpl) oConstructor.newInstance(strObject, strObject, connection);
            //PermissionManager oPermissionM = new PermissionManager();
            //perm = oPermissionM.getPermission(request, connection); 
            
        } catch (Exception ex) {
            ExceptionBooster.boost(new Exception(this.getClass().getName() + ":ControlOperationGenImpl ERROR: " + ex.getMessage()));
        }
    }

    @Override
    public String get(HttpServletRequest request) throws Exception {
        String result = "";
        if (perm) {
            result = oService.get(ParameterCook.prepareId(request));
            closeDB();
        } else {
            result = "error";
        }
        return result;
    }

    @Override
    public String getaggregateviewone(HttpServletRequest request) throws Exception {
        String result = "";
        if (perm) {
            result = result = oService.getaggregateviewone(ParameterCook.prepareId(request));
            closeDB();
        } else {
            result = "error";
        }

        return result;
    }

    @Override
    public String getprettycolumns(HttpServletRequest request) throws Exception {
        String result = "";
        if (perm) {
            result = oService.getprettycolumns();
            closeDB();
        } else {
            result = "error";
        }

        return result;
    }

    @Override
    public String getcolumns(HttpServletRequest request) throws Exception {
        String result = "";
        if (perm) {
            result = oService.getcolumns();
            closeDB();
        } else {
            result = "error";
        }

        return result;
    }

    @Override
    public String getpage(HttpServletRequest request) throws Exception {
        String result = "";
        if (perm) {
            Integer intRegsPerPag = ParameterCook.prepareRpp(request);
            Integer intPage = ParameterCook.preparePage(request);
            ArrayList<FilterBeanHelper> alFilter = ParameterCook.prepareFilter(request);
            HashMap<String, String> hmOrder = ParameterCook.prepareOrder(request);
            result = oService.getpage(intRegsPerPag, intPage, alFilter, hmOrder);
            closeDB();
        } else {
            result = "error";
        }

        return result;
    }

    @Override
    public String getpages(HttpServletRequest request) throws Exception {
        String result = "";
        if (perm) {
            Integer intRegsPerPag = ParameterCook.prepareRpp(request);
            ArrayList<FilterBeanHelper> alFilter = ParameterCook.prepareFilter(request);
            result = oService.getpages(intRegsPerPag, alFilter);
            closeDB();
        } else {
            result = "error";
        }
        return result;
    }

    @Override
    public String getregisters(HttpServletRequest request) throws Exception {
        String result = "";
        if (perm) {
            ArrayList<FilterBeanHelper> alFilter = ParameterCook.prepareFilter(request);
            result = oService.getcount(alFilter);
            closeDB();
        } else {
            result = "error";
        }
        return result;
    }

    @Override
    public String getaggregateviewsome(HttpServletRequest request) throws Exception {
        String result = "";
        if (perm) {
            Integer intRegsPerPag = ParameterCook.prepareRpp(request);
            Integer intPage = ParameterCook.preparePage(request);
            ArrayList<FilterBeanHelper> alFilter = ParameterCook.prepareFilter(request);
            HashMap<String, String> hmOrder = ParameterCook.prepareOrder(request);
            result = oService.getaggregateviewsome(intRegsPerPag, intPage, alFilter, hmOrder);
            closeDB();
        } else {
            result = "error";
        }
        return result;
    }

    @Override
    public String remove(HttpServletRequest request) throws Exception {
        String result = "";
        if (perm) {
            result = oService.remove(ParameterCook.prepareId(request));
            closeDB();
        } else {
            result = "error";
        }
        return result;
    }

    @Override
    public String set(HttpServletRequest request) throws Exception {
        String result = "";
        if (perm) {
            result = oService.set(ParameterCook.prepareJson(request));
            closeDB();
        } else {
            result = "error";
        }
        return result;
    }



    protected void closeDB() throws SQLException, Exception {
        if (connection != null) {
            connection.close();
        }
        DataConnectionSource.disposeConnection();
    }

    @Override
    public String updateOne(HttpServletRequest request) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
