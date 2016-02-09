/*
 * Copyright (c) 2015 by Rafael Angel Aznar Aparici (rafaaznar at gmail dot com)
 * 
 * openAUSIAS: The stunning micro-library that helps you to develop easily 
 *             AJAX web applications by using Java and jQuery
 * openAUSIAS is distributed under the MIT License (MIT)
 * Sources at https://github.com/rafaelaznar/openAUSIAS
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
 */
package net.daw.service.implementation;

import com.google.gson.Gson;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import net.daw.bean.implementation.UsuarioBean;
import net.daw.connection.publicinterface.ConnectionInterface;
import net.daw.dao.implementation.UsuarioDao;

import net.daw.helper.statics.AppConfigurationHelper;
import static net.daw.helper.statics.AppConfigurationHelper.getSourceConnection;
import net.daw.helper.statics.EncodingUtilHelper;
import net.daw.helper.statics.ExceptionBooster;
import net.daw.helper.statics.FilterBeanHelper;
import net.daw.helper.statics.JsonMessage;
import net.daw.helper.statics.ParameterCook;

import net.daw.service.publicinterface.TableServiceInterface;
import net.daw.service.publicinterface.ViewServiceInterface;

public class UsuarioService implements TableServiceInterface, ViewServiceInterface {

    protected HttpServletRequest oRequest = null;

    public UsuarioService(HttpServletRequest request) {
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

    public String getenvironment() throws Exception {
        String data = "";
        if (this.checkpermission("getenvironment")) {
            data = "{"
                    + "\"OPENSHIFT_APP_DNS\":" + EncodingUtilHelper.quotate(System.getenv("OPENSHIFT_APP_DNS"))
                    + ",\"OPENSHIFT_APP_NAME\":" + EncodingUtilHelper.quotate(System.getenv("OPENSHIFT_APP_NAME"))
                    + ",\"OPENSHIFT_APP_UUID\":" + EncodingUtilHelper.quotate(System.getenv("OPENSHIFT_APP_UUID"))
                    + ",\"OPENSHIFT_JBOSSEWS_IP\":" + EncodingUtilHelper.quotate(System.getenv("OPENSHIFT_JBOSSEWS_IP"))
                    + ",\"OPENSHIFT_JBOSSEWS_PORT\":" + EncodingUtilHelper.quotate(System.getenv("OPENSHIFT_JBOSSEWS_PORT"))
                    + ",\"OPENSHIFT_SECRET_TOKEN\":" + EncodingUtilHelper.quotate(System.getenv("OPENSHIFT_SECRET_TOKEN"))
                    + ",\"OPENSHIFT_HOMEDIR\":" + EncodingUtilHelper.quotate(System.getenv("OPENSHIFT_HOMEDIR"))
                    + ",\"OPENSHIFT_DATA_DIR\":" + EncodingUtilHelper.quotate(System.getenv("OPENSHIFT_DATA_DIR"))
                    + ",\"OPENSHIFT_REPO_DIR\":" + EncodingUtilHelper.quotate(System.getenv("OPENSHIFT_REPO_DIR"))
                    + ",\"OPENSHIFT_TMP_DIR\":" + EncodingUtilHelper.quotate(System.getenv("OPENSHIFT_TMP_DIR"))
                    + ",\"OPENSHIFT_LOG_DIR\":" + EncodingUtilHelper.quotate(System.getenv("OPENSHIFT_LOG_DIR"))
                    + ",\"LOGSHIFTER_JBOSSEWS_MAX_FILESIZE\":" + EncodingUtilHelper.quotate(System.getenv("LOGSHIFTER_JBOSSEWS_MAX_FILESIZE"))
                    + ",\"LOGSHIFTER_JBOSSEWS_MAX_FILES\":" + EncodingUtilHelper.quotate(System.getenv("LOGSHIFTER_JBOSSEWS_MAX_FILES"))
                    + ",\"OPENSHIFT_MYSQL_DB_HOST\":" + EncodingUtilHelper.quotate(System.getenv("OPENSHIFT_MYSQL_DB_HOST"))
                    + ",\"OPENSHIFT_MYSQL_DB_PORT\":" + EncodingUtilHelper.quotate(System.getenv("OPENSHIFT_MYSQL_DB_PORT"))
                    + ",\"OPENSHIFT_MYSQL_DB_USERNAME\":" + EncodingUtilHelper.quotate(System.getenv("OPENSHIFT_MYSQL_DB_USERNAME"))
                    + ",\"OPENSHIFT_MYSQL_DB_PASSWORD\":" + EncodingUtilHelper.quotate(System.getenv("OPENSHIFT_MYSQL_DB_PASSWORD"))
                    + ",\"OPENSHIFT_MYSQL_DB_SOCKET\":" + EncodingUtilHelper.quotate(System.getenv("OPENSHIFT_MYSQL_DB_SOCKET"))
                    + ",\"OPENSHIFT_MYSQL_DB_URL\":" + EncodingUtilHelper.quotate(System.getenv("OPENSHIFT_MYSQL_DB_URL"))
                    + ",\"OPENSHIFT_MAX_SESSIONS_PER_GEAR\":" + EncodingUtilHelper.quotate(System.getenv("OPENSHIFT_MAX_SESSIONS_PER_GEAR"))
                    + ",\"OPENSHIFT_JBOSSEWS_LD_LIBRARY_PATH_ELEMENT\":" + EncodingUtilHelper.quotate(System.getenv("OPENSHIFT_JBOSSEWS_LD_LIBRARY_PATH_ELEMENT"))
                    + ",\"JENKINS_USERNAME\":" + EncodingUtilHelper.quotate(System.getenv("JENKINS_USERNAME"))
                    + ",\"JENKINS_PASSWORD\":" + EncodingUtilHelper.quotate(System.getenv("JENKINS_PASSWORD"))
                    + ",\"JENKINS_URL\":" + EncodingUtilHelper.quotate(System.getenv("JENKINS_URL"))
                    + ",\"JAVA_OPTS_EXT\":" + EncodingUtilHelper.quotate(System.getenv("JAVA_OPTS_EXT"))
                    + ",\"OPENSHIFT_GEAR_DNS\":" + EncodingUtilHelper.quotate(System.getenv("OPENSHIFT_GEAR_DNS"))
                    + ",\"OPENSHIFT_GEAR_NAME\":" + EncodingUtilHelper.quotate(System.getenv("OPENSHIFT_GEAR_NAME"))
                    + ",\"OPENSHIFT_GEAR_UUID\":" + EncodingUtilHelper.quotate(System.getenv("OPENSHIFT_GEAR_UUID"))
                    + "}";
            data = JsonMessage.getJson("200", data);

            return data;

        } else {
            return JsonMessage.getJsonMsg("401", "Unauthorized");
        }

    }

    @Override
    public String getcount() throws Exception {
        if (this.checkpermission("getcount")) {
            String data = null;
            ArrayList<FilterBeanHelper> alFilter = ParameterCook.prepareFilter(oRequest);
            Connection oConnection = null;
            ConnectionInterface oDataConnectionSource = null;
            try {
                oDataConnectionSource = getSourceConnection();
                oConnection = oDataConnectionSource.newConnection();
                UsuarioDao oUsuarioDao = new UsuarioDao(oConnection);
                data = JsonMessage.getJson("200", Integer.toString(oUsuarioDao.getCount(alFilter)));
            } catch (Exception ex) {
                ExceptionBooster.boost(new Exception(this.getClass().getName() + ":getCount ERROR: " + ex.getMessage()));
            } finally {
                if (oConnection != null) {
                    oConnection.close();
                }
                if (oDataConnectionSource != null) {
                    oDataConnectionSource.disposeConnection();
                }
            }
            return data;
        } else {
            return JsonMessage.getJsonMsg("401", "Unauthorized");
        }
    }

    @Override
    public String get() throws Exception {
        if (this.checkpermission("get")) {
            int id = ParameterCook.prepareId(oRequest);
            String data = null;
            Connection oConnection = null;
            ConnectionInterface oDataConnectionSource = null;
            try {
                oDataConnectionSource = getSourceConnection();
                oConnection = oDataConnectionSource.newConnection();
                UsuarioDao oUsuarioDao = new UsuarioDao(oConnection);
                UsuarioBean oUsuarioBean = new UsuarioBean(id);
                oUsuarioBean = oUsuarioDao.get(oUsuarioBean, AppConfigurationHelper.getJsonDepth());
                Gson gson = AppConfigurationHelper.getGson();
                data = JsonMessage.getJson("200", AppConfigurationHelper.getGson().toJson(oUsuarioBean));
            } catch (Exception ex) {
                ExceptionBooster.boost(new Exception(this.getClass().getName() + ":get ERROR: " + ex.getMessage()));
            } finally {
                if (oConnection != null) {
                    oConnection.close();
                }
                if (oDataConnectionSource != null) {
                    oDataConnectionSource.disposeConnection();
                }
            }
            return data;

        } else {
            return JsonMessage.getJsonMsg("401", "Unauthorized");
        }
    }

    @Override
    public String getall() throws Exception {
        if (this.checkpermission("getall")) {
            ArrayList<FilterBeanHelper> alFilter = ParameterCook.prepareFilter(oRequest);
            HashMap<String, String> hmOrder = ParameterCook.prepareOrder(oRequest);
            String data = null;
            Connection oConnection = null;
            ConnectionInterface oDataConnectionSource = null;

            try {
                oDataConnectionSource = getSourceConnection();
                oConnection = oDataConnectionSource.newConnection();
                UsuarioDao oUsuarioDao = new UsuarioDao(oConnection);
                ArrayList<UsuarioBean> arrBeans = oUsuarioDao.getAll(alFilter, hmOrder, 1);
                data = JsonMessage.getJson("200", AppConfigurationHelper.getGson().toJson(arrBeans));
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
        } else {
            return JsonMessage.getJsonMsg("401", "Unauthorized");
        }
    }

    @Override
    @SuppressWarnings("empty-statement")
    public String getpage() throws Exception {
        if (this.checkpermission("getpage")) {
            int intRegsPerPag = ParameterCook.prepareRpp(oRequest);;
            int intPage = ParameterCook.preparePage(oRequest);
            ArrayList<FilterBeanHelper> alFilter = ParameterCook.prepareFilter(oRequest);
            HashMap<String, String> hmOrder = ParameterCook.prepareOrder(oRequest);
            String data = null;
            Connection oConnection = null;
            ConnectionInterface oDataConnectionSource = null;
            try {
                oDataConnectionSource = getSourceConnection();
                oConnection = oDataConnectionSource.newConnection();
                UsuarioDao oUsuarioDao = new UsuarioDao(oConnection);
                List<UsuarioBean> arrBeans = oUsuarioDao.getPage(intRegsPerPag, intPage, alFilter, hmOrder, AppConfigurationHelper.getJsonDepth());
                data = JsonMessage.getJson("200", AppConfigurationHelper.getGson().toJson(arrBeans));
            } catch (Exception ex) {
                ExceptionBooster.boost(new Exception(this.getClass().getName() + ":getPage ERROR: " + ex.getMessage()));
            } finally {
                if (oConnection != null) {
                    oConnection.close();
                }
                if (oDataConnectionSource != null) {
                    oDataConnectionSource.disposeConnection();
                }
            }
            return data;
        } else {
            return JsonMessage.getJsonMsg("401", "Unauthorized");
        }
    }

    @Override
    public String getpages() throws Exception {
        if (this.checkpermission("getpages")) {
            int intRegsPerPag = ParameterCook.prepareRpp(oRequest);
            ArrayList<FilterBeanHelper> alFilter = ParameterCook.prepareFilter(oRequest);
            String data = null;
            Connection oConnection = null;
            ConnectionInterface oDataConnectionSource = null;
            try {
                oDataConnectionSource = getSourceConnection();
                oConnection = oDataConnectionSource.newConnection();
                UsuarioDao oUsuarioDao = new UsuarioDao(oConnection);
                data = JsonMessage.getJson("200", Integer.toString(oUsuarioDao.getPages(intRegsPerPag, alFilter)));
            } catch (Exception ex) {
                ExceptionBooster.boost(new Exception(this.getClass().getName() + ":getPages ERROR: " + ex.getMessage()));
            } finally {
                if (oConnection != null) {
                    oConnection.close();
                }
                if (oDataConnectionSource != null) {
                    oDataConnectionSource.disposeConnection();
                }
            }
            return data;
        } else {
            return JsonMessage.getJsonMsg("401", "Unauthorized");
        }
    }

    @Override
    public String getaggregateviewsome() throws Exception {
        if (this.checkpermission("getaggregateviewsome")) {
            String data = null;
            try {
                String page = this.getpage();
                String pages = this.getpages();
                String registers = this.getcount();
                data = "{"
                        + "\"page\":" + page
                        + ",\"pages\":" + pages
                        + ",\"registers\":" + registers
                        + "}";
                data = JsonMessage.getJson("200", data);
            } catch (Exception ex) {
                ExceptionBooster.boost(new Exception(this.getClass().getName() + ":getAggregateViewSome ERROR: " + ex.getMessage()));
            }
            return data;
        } else {
            return JsonMessage.getJsonMsg("401", "Unauthorized");
        }
    }

    @Override
    public String remove() throws Exception {
        if (this.checkpermission("remove")) {
            Integer id = ParameterCook.prepareId(oRequest);
            String resultado = null;
            Connection oConnection = null;
            ConnectionInterface oDataConnectionSource = null;
            try {
                oDataConnectionSource = getSourceConnection();
                oConnection = oDataConnectionSource.newConnection();
                oConnection.setAutoCommit(false);
                UsuarioDao oUsuarioDao = new UsuarioDao(oConnection);
                resultado = JsonMessage.getJson("200", (String) oUsuarioDao.remove(id).toString());
                oConnection.commit();
            } catch (Exception ex) {
                oConnection.rollback();
                ExceptionBooster.boost(new Exception(this.getClass().getName() + ":remove ERROR: " + ex.getMessage()));
            } finally {
                if (oConnection != null) {
                    oConnection.close();
                }
                if (oDataConnectionSource != null) {
                    oDataConnectionSource.disposeConnection();
                }
            }
            return resultado;
        } else {
            return JsonMessage.getJsonMsg("401", "Unauthorized");
        }
    }

    @Override
    public String set() throws Exception {
        if (this.checkpermission("set")) {
            String jason = ParameterCook.prepareJson(oRequest);
            String resultado = null;
            Connection oConnection = null;
            ConnectionInterface oDataConnectionSource = null;
            try {
                oDataConnectionSource = getSourceConnection();
                oConnection = oDataConnectionSource.newConnection();
                oConnection.setAutoCommit(false);
                UsuarioDao oUsuarioDao = new UsuarioDao(oConnection);
                UsuarioBean oUsuarioBean = new UsuarioBean();
                oUsuarioBean = AppConfigurationHelper.getGson().fromJson(jason, oUsuarioBean.getClass());
                if (oUsuarioBean != null) {
                    Integer iResult = oUsuarioDao.set(oUsuarioBean);
                    if (iResult >= 1) {
                        resultado = JsonMessage.getJson("200", iResult.toString());
                    } else {
                        resultado = JsonMessage.getJson("500", "Error during registry set");
                    }
                } else {
                    resultado = JsonMessage.getJson("500", "Error during registry set");
                }
                oConnection.commit();
            } catch (Exception ex) {
                oConnection.rollback();
                ExceptionBooster.boost(new Exception(this.getClass().getName() + ":set ERROR: " + ex.getMessage()));
            } finally {
                if (oConnection != null) {
                    oConnection.close();
                }
                if (oDataConnectionSource != null) {
                    oDataConnectionSource.disposeConnection();
                }
            }
            return resultado;
        } else {
            return JsonMessage.getJsonMsg("401", "Unauthorized");
        }
    }
    
    public String insertar() throws Exception {
        if (this.checkpermission("insertar")) {
            String nombre = oRequest.getParameter("nombre");
            String ape1 = oRequest.getParameter("ape1");
            String ape2 = oRequest.getParameter("ape2");
            int sexo = ParameterCook.prepareInt("sexo", oRequest);
            String fnac = oRequest.getParameter("fnac");
            String resultado = null;
            Connection oConnection = null;
            ConnectionInterface oDataConnectionSource = null;
            try {
                oDataConnectionSource = getSourceConnection();
                oConnection = oDataConnectionSource.newConnection();
                oConnection.setAutoCommit(false);
                UsuarioDao oUsuarioDao = new UsuarioDao(oConnection);
                UsuarioBean oUsuarioBean = new UsuarioBean();
                DateFormat format = new SimpleDateFormat("ddMMyyyy", Locale.ENGLISH);
                Date fechaNacimiento = format.parse(fnac);
                oUsuarioBean.setNombre(nombre);
                oUsuarioBean.setApe1(ape1);
                oUsuarioBean.setApe2(ape2);
                oUsuarioBean.setSexo(sexo);
                oUsuarioBean.setFnac(fechaNacimiento);
                String login = nombre.substring(0, 3);
                login +=ape1.substring(0, 3);
                login +=ape2.substring(0,3);
                login = login.toLowerCase();
                oUsuarioBean.setLogin(login);
                String password = oUsuarioDao.md5(login);
                password = password.substring(0, 7);
                oUsuarioBean.setPassword(password);
                if (oUsuarioBean != null) {
                    Integer iResult = oUsuarioDao.set(oUsuarioBean);
                    if (iResult >= 1) {
                        resultado = "{\"status\":OK,\"id\":" + iResult + ", login:\"" + oUsuarioBean.getLogin() + "\"}";
                    } else {
                        resultado = JsonMessage.getJson("500", "Error during registry set");
                    }
                } else {
                    resultado = JsonMessage.getJson("500", "Error during registry set");
                }
                oConnection.commit();
            } catch (Exception ex) {
                oConnection.rollback();
                ExceptionBooster.boost(new Exception(this.getClass().getName() + ":set ERROR: " + ex.getMessage()));
            } finally {
                if (oConnection != null) {
                    oConnection.close();
                }
                if (oDataConnectionSource != null) {
                    oDataConnectionSource.disposeConnection();
                }
            }
            return resultado;
        } else {
            return JsonMessage.getJsonMsg("401", "Unauthorized");
        }
    }
    
    public String login() throws SQLException, Exception {
        UsuarioBean oUserBean = (UsuarioBean) oRequest.getSession().getAttribute("userBean");
        String strAnswer = null;
        String strCode = "OK";
        if (oUserBean == null) {
            String login = oRequest.getParameter("login");
            String pass = oRequest.getParameter("pass");
            if (!login.equals("") && !pass.equals("")) {
                ConnectionInterface oDataConnectionSource = null;
                Connection oConnection = null;
                try {
                    oDataConnectionSource = getSourceConnection();
                    oConnection = oDataConnectionSource.newConnection();
                    UsuarioBean oUsuario = new UsuarioBean();
                    oUsuario.setLogin(login);
                    oUsuario.setPassword(pass);
                    UsuarioDao oUsuarioDao = new UsuarioDao(oConnection);
                    oUsuario = oUsuarioDao.getFromLogin(oUsuario);
                    if (oUsuario.getId() != 0) {
                        oRequest.getSession().setAttribute("userBean", oUsuario);
                        strAnswer= "{\"status\":OK,\"usuario\":" + oUsuario.getId() + "}";
                    } else {
                        strAnswer= "{\"status\":KO}";
                    }
                } catch (Exception ex) {
                    ExceptionBooster.boost(new Exception(this.getClass().getName() + ":login ERROR " + ex.toString()));
                } finally {
                    if (oConnection != null) {
                        oConnection.close();
                    }
                    if (oDataConnectionSource != null) {
                        oDataConnectionSource.disposeConnection();
                    }
                }
            }
        } else {
            strAnswer = "Already logged in";
        }
        return strAnswer;
    }
    
     public String check()throws Exception {
        ConnectionInterface oDataConnectionSource = null;
        Connection oConnection = null;
        oDataConnectionSource = getSourceConnection();
        oConnection = oDataConnectionSource.newConnection();
        UsuarioBean oUserBean = (UsuarioBean) oRequest.getSession().getAttribute("userBean");
        ArrayList<String> alarray = new ArrayList<String>();
        if (oUserBean == null) {
            return "{\"status\":KO}";
        } else {
           UsuarioDao oUsuarioDao = new UsuarioDao(oConnection);
           alarray = oUsuarioDao.tipocomprado(oUserBean.getId());
            return "{\"status\":OK,\"id\":" + oUserBean.getId() + ", \"nombrecompleto\":" + oUserBean.getNombre() + " " + oUserBean.getApe1() + " " + oUserBean.getApe2() +"}";
        }
    }
    
    

     public String logout() {
        UsuarioBean oUserBean = (UsuarioBean) oRequest.getSession().getAttribute("userBean");
        if (oUserBean == null) {
            return "{\"status\":KO}";
        } else {
            oRequest.getSession().invalidate();
            return "{\"status\":OK}";
        }
    }
    
     
     
    public String getsessionstatus() {
        String strAnswer = null;
        UsuarioBean oUserBean = (UsuarioBean) oRequest.getSession().getAttribute("userBean");
        if (oUserBean == null) {
            return JsonMessage.getJsonMsg("403", "ERROR: You don't have permission to perform this operation");
        } else {
            return JsonMessage.getJsonMsg("200", oUserBean.getLogin());
        }
    }

    public int sessionuserlevel() {
        String strAnswer = null;
        UsuarioBean oUserBean = (UsuarioBean) oRequest.getSession().getAttribute("userBean");
        if (oUserBean == null) {
            return 0;
        } else {
            return oUserBean.getId();
        }
    }

}


/*SELECT *, TIMESTAMPDIFF(YEAR, fnac, CURDATE()) AS edad FROM usuario*/

/*SELECT *, TIMESTAMPDIFF(YEAR, fnac, CURDATE()) AS edad FROM usuario,compra,producto where TIMESTAMPDIFF(YEAR, fnac, CURDATE())> 25 and compra.id_usuario = usuario.id and compra.cantidad >5 and compra.id_producto = producto.id*/