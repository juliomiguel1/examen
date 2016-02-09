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
package net.daw.bean.implementation;

import com.google.gson.annotations.Expose;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import net.daw.bean.publicinterface.GenericBean;
import net.daw.dao.implementation.EstadoDao;
import net.daw.dao.implementation.TipousuarioDao;
import net.daw.helper.statics.EncodingUtilHelper;

public class UsuarioBean implements GenericBean {

    @Expose
    private Integer id;
     @Expose
    private String nombre = "";
    
    @Expose
    private String ape1 = "";
    @Expose
    private String ape2 = "";
    @Expose
    private Integer sexo;
     @Expose
    private String login = "";
    @Expose
    private Date fnac = new Date();
    @Expose
    private String password = "";

    public UsuarioBean() {
        this.id = 0;
    }

    public UsuarioBean(Integer id) {
        this.id = id;
    }

   
    

    public String toJson(Boolean expand) {
        String strJson = "{";
        strJson += "id:" + getId() + ",";
        strJson += "nombre:" + EncodingUtilHelper.quotate(nombre) + ",";
        strJson += "ape1:" + EncodingUtilHelper.quotate(ape1) + ",";
        strJson += "ap2:" + EncodingUtilHelper.quotate(ape2) + ",";
        strJson += "sexo:" + sexo + ",";
        strJson += "login:" + EncodingUtilHelper.quotate(login) + ",";
        strJson += "fnac:" + EncodingUtilHelper.stringifyAndQuotate(fnac) + ",";
        strJson += "password:" + EncodingUtilHelper.quotate(password) + ",";
        strJson += "}";
        return strJson;
    }

    @Override
    public String getColumns() {
        String strColumns = "";
        strColumns += "id,";
        strColumns += "nombre,";
        strColumns += "ape1,";
        strColumns += "ape2,";
        strColumns += "sexo,";
        strColumns += "login,";
        strColumns += "fnac,";
        strColumns += "password";

        return strColumns;
    }

    @Override
    public String getValues() {
        String strColumns = "";
        strColumns += getId() + ",";
        strColumns += EncodingUtilHelper.quotate(getNombre()) + ",";
        strColumns += EncodingUtilHelper.quotate(ape1) + ",";
        strColumns += EncodingUtilHelper.quotate(ape2) + ",";
        strColumns += sexo + ",";
        strColumns += EncodingUtilHelper.quotate(login) + ",";
        strColumns += EncodingUtilHelper.stringifyAndQuotate(fnac)+ ",";
        strColumns += EncodingUtilHelper.quotate(password);

        return strColumns;
    }

    @Override
    public String toPairs() {
        String strPairs = "";
        strPairs += "id=" + getId() + ",";
        strPairs += "nombre=" + EncodingUtilHelper.quotate(getNombre()) + ",";
        strPairs += "ape1=" + EncodingUtilHelper.quotate(ape1) + ",";
        strPairs += "ape2=" + EncodingUtilHelper.quotate(ape2) + ",";
        strPairs += "sexo=" + sexo + ",";
        strPairs += "login=" + EncodingUtilHelper.quotate(getLogin()) + ",";
        strPairs += "fnac=" + EncodingUtilHelper.stringifyAndQuotate(fnac) + ",";
        strPairs += "password=" + EncodingUtilHelper.quotate(password);
        return strPairs;
    }

    @Override
    public UsuarioBean fill(ResultSet oResultSet, Connection pooledConnection, Integer expand) throws SQLException, Exception {
        this.setId(oResultSet.getInt("id"));
        this.setNombre(oResultSet.getString("nombre"));
        this.setApe1(oResultSet.getString("ape1"));
        this.setApe2(oResultSet.getString("ape2"));
        this.setSexo(oResultSet.getInt("sexo"));
        this.setLogin(oResultSet.getString("login"));
        this.setFnac(oResultSet.getDate("fnac"));
        this.setPassword(oResultSet.getString("password"));
        return this;

    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the ape1
     */
    public String getApe1() {
        return ape1;
    }

    /**
     * @param ape1 the ape1 to set
     */
    public void setApe1(String ape1) {
        this.ape1 = ape1;
    }

    /**
     * @return the ape2
     */
    public String getApe2() {
        return ape2;
    }

    /**
     * @param ape2 the ape2 to set
     */
    public void setApe2(String ape2) {
        this.ape2 = ape2;
    }

    /**
     * @return the sexo
     */
    public Integer getSexo() {
        return sexo;
    }

    /**
     * @param sexo the sexo to set
     */
    public void setSexo(Integer sexo) {
        this.sexo = sexo;
    }

    /**
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return the fnac
     */
    public Date getFnac() {
        return fnac;
    }

    /**
     * @param fnac the fnac to set
     */
    public void setFnac(Date fnac) {
        this.fnac = fnac;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

}
