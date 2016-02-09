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
package net.daw.bean.implementation;

import com.google.gson.annotations.Expose;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import net.daw.bean.publicinterface.GenericBean;
import net.daw.dao.implementation.TipoproductoDao;
import net.daw.helper.statics.EncodingUtilHelper;

/**
 *
 * @author juliomiguel
 */
public class ProductoBean implements GenericBean{
    
    @Expose
    private Integer id;    
    @Expose
    private String codigo = "";
    @Expose
    private String descripcion = "";
    @Expose
    private Double precio;
    @Expose(serialize = false)
    private Integer id_tipoproducto = 0;
    @Expose(deserialize = false)
    private TipoproductoBean obj_tipoproducto = null;
    
    public ProductoBean() {
        this.id = 0;
    }

    public ProductoBean(Integer id) {
        this.id = id;
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
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the desripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param desripcion the desripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the precio
     */
    public Double getPrecio() {
        return precio;
    }

    /**
     * @param precio the precio to set
     */
    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    /**
     * @return the id_tipoproducto
     */
    public Integer getId_tipoproducto() {
        return id_tipoproducto;
    }

    /**
     * @param id_tipoproducto the id_tipoproducto to set
     */
    public void setId_tipoproducto(Integer id_tipoproducto) {
        this.id_tipoproducto = id_tipoproducto;
    }

    /**
     * @return the obj_tipoproducto
     */
    public TipoproductoBean getObj_tipoproducto() {
        return obj_tipoproducto;
    }

    /**
     * @param obj_tipoproducto the obj_tipoproducto to set
     */
    public void setObj_tipoproducto(TipoproductoBean obj_tipoproducto) {
        this.obj_tipoproducto = obj_tipoproducto;
    }

   
    @Override
    public String getColumns() {
       String strColumns = "";
        strColumns += "id,";   
        strColumns += "codigo,";
        strColumns += "descripcion,";
        strColumns += "precio,";
        strColumns += "id_tipoproducto";
        return strColumns;
    }

     @Override
    public String getValues() {
         String strColumns = "";
        strColumns += id + ",";
        strColumns += codigo +",";
        strColumns += descripcion +",";
        strColumns += precio+",";
        strColumns += id_tipoproducto ;
        return strColumns;
    }

    @Override
    public String toPairs() {    
        String strPairs = "";
        //strPairs += "id=" + id + ",";
        strPairs += "codigo" + codigo + ",";
        strPairs += "descripcion=" + descripcion + ",";
        strPairs += "precio=" + precio + ",";
        strPairs += "id_tipoproducto=" +  id_tipoproducto;
        
        return strPairs;
    }

    @Override
    public ProductoBean fill(ResultSet oResultSet, Connection pooledConnection, Integer expand) throws SQLException, Exception {
        this.setId(oResultSet.getInt("id"));
        this.setCodigo(oResultSet.getString("codigo"));
        this.setDescripcion(oResultSet.getString("descripcion"));
        this.setPrecio(oResultSet.getDouble("precio"));
        if (expand > 0) {
            TipoproductoBean oTipoproductoBean = new TipoproductoBean();
            TipoproductoDao oTipoproductoDao = new TipoproductoDao(pooledConnection);
            oTipoproductoBean.setId(oResultSet.getInt("id_tipoproducto"));
            oTipoproductoBean = oTipoproductoDao.get(oTipoproductoBean, expand - 1);
            this.setObj_tipoproducto(oTipoproductoBean);
        } else {
            this.setId_tipoproducto(oResultSet.getInt("id_tipoproducto"));
        }
        return this;
    }
}
