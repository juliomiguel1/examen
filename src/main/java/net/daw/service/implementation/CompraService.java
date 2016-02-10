/*
 * Copyright (c) 2015 by Rafael Angel Aznar Aparici (rafaaznar at gmail dot com)
 * 
 * openAUSIAS: The stunning micro-library that helps you to develop easily 
 *             AJAX web applications by using Java and jQuery
 * openAUSIAS is distributed under the MIT License (MIT)
 * Sources at https://github.com/rafaelaznar/
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * 
 */
package net.daw.service.implementation;

import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import net.daw.bean.implementation.CompraBean;
import net.daw.bean.implementation.UltimasComprasBean;
import net.daw.bean.implementation.UsuarioBean;
import net.daw.connection.publicinterface.ConnectionInterface;
import net.daw.dao.implementation.CompraDao;
import net.daw.helper.statics.AppConfigurationHelper;
import static net.daw.helper.statics.AppConfigurationHelper.getSourceConnection;
import net.daw.helper.statics.ExceptionBooster;
import net.daw.helper.statics.FilterBeanHelper;
import net.daw.helper.statics.JsonMessage;
import net.daw.helper.statics.ParameterCook;
import net.daw.service.publicinterface.TableServiceInterface;
import net.daw.service.publicinterface.ViewServiceInterface;

/**
 *
 * @author juliomiguel
 */
public class CompraService implements TableServiceInterface, ViewServiceInterface {

    protected HttpServletRequest oRequest = null;

    public CompraService(HttpServletRequest request) {
        oRequest = request;
    }

    private Boolean checkpermission(String strMethodName) throws Exception {
        UsuarioBean oUserBean = (UsuarioBean) oRequest.getSession().getAttribute("userBean");
        if (oUserBean != null) {
            return true;
        } else {
            return false;
        }
    }
//
//    public String listar() throws Exception {
//        if (this.checkpermission("listar")) {
//            ArrayList<FilterBeanHelper> alFilter = ParameterCook.prepareFilter(oRequest);
//            HashMap<String, String> hmOrder = ParameterCook.prepareOrder(oRequest);
//            String data = null;
//            int edad = ParameterCook.prepareInt("edad", oRequest);
//            int cantidad = ParameterCook.prepareInt("cantidad", oRequest);
//            Connection oConnection = null;
//            ConnectionInterface oDataConnectionSource = null;
//            try {
//                oDataConnectionSource = getSourceConnection();
//                oConnection = oDataConnectionSource.newConnection();
//                CompraDao oCompraDao = new CompraDao(oConnection);
//                ArrayList<CompraBean> arrBeans = oCompraDao.getAll(cantidad, edad, alFilter, hmOrder, 1);
//                data = JsonMessage.getJson("200", AppConfigurationHelper.getGson().toJson(arrBeans));
//
//            } catch (Exception ex) {
//                oConnection.rollback();
//                ExceptionBooster.boost(new Exception(this.getClass().getName() + ":set ERROR: " + ex.getMessage()));
//            } finally {
//                if (oConnection != null) {
//                    oConnection.close();
//                }
//                if (oDataConnectionSource != null) {
//                    oDataConnectionSource.disposeConnection();
//                }
//            }
//            return data;
//        } else {
//            return JsonMessage.getJsonMsg("401", "Unauthorized");
//        }
//    }

    @Override
    public String set() throws Exception {
        ConnectionInterface oDataConnectionSource = null;
        Connection oConnection = null;
        oDataConnectionSource = getSourceConnection();
        oConnection = oDataConnectionSource.newConnection();
        UsuarioBean oUserBean = (UsuarioBean) oRequest.getSession().getAttribute("userBean");
        ArrayList<String> alarray = new ArrayList<String>();
        if (oUserBean == null) {
            return "{\"status\":\"nosession\"}";
        } else {
            int id_producto = ParameterCook.prepareInt("id_producto", oRequest);
            int cantidad = ParameterCook.prepareInt("cantidad", oRequest);
            int id_usuario = ParameterCook.prepareInt("id_usuario", oRequest);
            CompraDao oCompraDao = new CompraDao(oConnection);
            CompraBean oCompraBean = new CompraBean();
            oCompraBean.setId_producto(id_producto);
            oCompraBean.setCantidad(cantidad);
            oCompraBean.setId_usuario(id_usuario);
            Date fecha = new Date();
            //DateFormat hourdateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            //fecha = hourdateFormat.format(fecha);
            oCompraBean.setMomento(fecha);

            int existeusuario = oCompraDao.existeusuario(oCompraBean);
            int existeproducto = oCompraDao.existeproducto(oCompraBean);

            if (existeproducto == 0) {
                return "{\"status\":\"error: no existe el producto\"}";
            } else if (existeusuario == 0) {
                return "{\"status\":\"error: no existe el usuario\"}";
            } else if (existeproducto != 0 && existeusuario != 0) {
                Integer iResult = oCompraDao.set(oCompraBean);
                if (iResult >= 1) {
                    return "{\"status\":OK, \"id\":\"" + iResult.toString() + "\"}";
                } else {
                    return "{\"status\":\"error\"}";
                }
            } else {
                return "{\"status\":\"error\"}";
            }
        }
    }

    public String ultimascompras() throws Exception {
        ConnectionInterface oDataConnectionSource = null;
        Connection oConnection = null;
        oDataConnectionSource = getSourceConnection();
        oConnection = oDataConnectionSource.newConnection();
        String data = null;
        UsuarioBean oUserBean = (UsuarioBean) oRequest.getSession().getAttribute("userBean");
        ArrayList<String> alarray = new ArrayList<String>();
        if (oUserBean == null) {
            return "{\"status\":\"nosession\"}";
        } else {

            try {
                oDataConnectionSource = getSourceConnection();
                oConnection = oDataConnectionSource.newConnection();
                CompraDao oCompraDao = new CompraDao(oConnection);
                ArrayList<FilterBeanHelper> alFilter = ParameterCook.prepareFilter(oRequest);
                HashMap<String, String> hmOrder = ParameterCook.prepareOrder(oRequest);
                int cantidad = ParameterCook.prepareInt("cantidad", oRequest);
                int id_usuario = ParameterCook.prepareInt("id_usuario", oRequest);
                
                int precio = oCompraDao.getprecio(cantidad, id_usuario, alFilter, hmOrder, cantidad);
              //  CompraBean oCompraBean = new CompraBean();                                
                ArrayList<UltimasComprasBean> arrBeans = oCompraDao.getAll(cantidad, id_usuario,alFilter, hmOrder, 1);
                return "{\"status\":\"OK\", \"total\":"+(precio*1.21)+",\"compras\":"+AppConfigurationHelper.getGson().toJson(arrBeans)+"}";
            } catch (Exception ex) {
                ExceptionBooster.boost(new Exception(this.getClass().getName() + ":getAll ERROR: " + ex.getMessage()));
            } finally {
                if (oConnection != null) {
                    oConnection.close();
                }
                if (oDataConnectionSource != null) {
                    oDataConnectionSource.disposeConnection();
                }
            }

            return data;
        }
    }


@Override
        public String get() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
        public String remove() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    

    @Override
        public String getall() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
        public String getpage() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
        public String getpages() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
        public String getcount() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
        public String getaggregateviewsome() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
