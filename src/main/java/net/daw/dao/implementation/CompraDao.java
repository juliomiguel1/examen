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
package net.daw.dao.implementation;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import net.daw.bean.implementation.CompraBean;
import net.daw.bean.implementation.UltimasComprasBean;
import net.daw.data.implementation.MysqlDataSpImpl;
import net.daw.helper.statics.ExceptionBooster;
import net.daw.helper.statics.FilterBeanHelper;

/**
 *
 * @author juliomiguel
 */
public class CompraDao {
    
     private String strTable = "compra";
    private String strSQL = "select * from compra where 1=1 ";
    private MysqlDataSpImpl oMysql = null;
    private Connection oConnection = null;

    public CompraDao(Connection oPooledConnection) throws Exception {
        try {
            oConnection = oPooledConnection;
            oMysql = new MysqlDataSpImpl(oConnection);
        } catch (Exception ex) {
            ExceptionBooster.boost(new Exception(this.getClass().getName() + ":constructor ERROR: " + ex.getMessage()));
        }
    }
    
    public ArrayList<UltimasComprasBean> getAll(int cantidad, int usuario,ArrayList<FilterBeanHelper> alFilter, HashMap<String, String> hmOrder, Integer expand) throws Exception {
    
     ArrayList<UltimasComprasBean> arrCompra = new ArrayList<>();
     
     strSQL = "select producto.codigo, producto.descripcion, producto.precio, compra.cantidad, tipoproducto.descripcion AS tipo from compra, producto, tipoproducto WHERE compra.id_producto= producto.id and producto.id_tipoproducto = tipoproducto.id and compra.id_usuario = "+usuario +" ORDER BY compra.momento DESC LIMIT "+cantidad;
     ResultSet oResultSet = oMysql.getAllSql(strSQL);
     if(oResultSet != null){
         while(oResultSet.next()){
              UltimasComprasBean oUltimasComprasBean = new UltimasComprasBean();
              arrCompra.add(oUltimasComprasBean.fill(oResultSet, oConnection, expand));
         }
     }
     return arrCompra;
    }
    
    public int getprecio(int cantidad, int usuario,ArrayList<FilterBeanHelper> alFilter, HashMap<String, String> hmOrder, Integer expand) throws Exception {
    
     int precio = 0;
     
     strSQL = "select  producto.precio from compra, producto, tipoproducto WHERE compra.id_producto= producto.id and producto.id_tipoproducto = tipoproducto.id and compra.id_usuario = "+usuario +" ORDER BY compra.momento DESC LIMIT "+cantidad;
     ResultSet oResultSet = oMysql.getAllSql(strSQL);
     if(oResultSet != null){
         while(oResultSet.next()){
             
              precio += oResultSet.getInt("precio");
         }
     }
     return precio;
    }
    
    public int existeusuario(CompraBean oCompraBean)throws Exception{
    
        int valor = 0;
        
        String existeusuario = "select * from usuario where usuario.id="+oCompraBean.getId_usuario();
        
        ResultSet oResultSet = oMysql.getAllSql(existeusuario);
        if(oResultSet != null){
            
            while(oResultSet.next()){
                
                valor = oResultSet.getInt("id");
                
            }
            }
            
        
        
    return valor;
    }
    
    
    public int existeproducto(CompraBean oCompraBean)throws Exception{
    
        int valor = 0;
        
        
            String existeproducto = "select * from producto where producto.id="+oCompraBean.getId_producto();
            ResultSet oResultSetproducto = oMysql.getAllSql(existeproducto);
            if(oResultSetproducto != null){
                while(oResultSetproducto.next()){
                
                valor = oResultSetproducto.getInt("id");
                
            }
            }
            
        
    return valor;
    }
    
    
    public Integer set(CompraBean oCompraBean) throws Exception {
        
           
        
          Integer iResult = null;
        try {
            if (oCompraBean.getId() == 0) {
                strSQL = "INSERT INTO " + strTable + " ";
                strSQL += "(" + oCompraBean.getColumns() + ") ";
                strSQL += "VALUES (" + oCompraBean.getValues() + ")";
                iResult = oMysql.executeInsertSQL(strSQL);
            } else {
                strSQL = "UPDATE " + strTable + " ";
                strSQL += " SET " + oCompraBean.toPairs();
                strSQL += " WHERE id=" + oCompraBean.getId();
                iResult = oMysql.executeUpdateSQL(strSQL);
            }

        } catch (Exception ex) {
            ExceptionBooster.boost(new Exception(this.getClass().getName() + ":set ERROR: " + ex.getMessage()));
        }
        return iResult;
    }
    
}
