/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.*;
import java.sql.SQLException;

/**
 *
 * @author Jacob D'Onofrio
 */
public class MapFactory {

	DBManager db;
    
    /**
     * This constructor creates a factory class with a built in
     * database management object. THe DBManager is a copy of the
     * main DBManager object.
     * @param d
     * @throws SQLException
     */
    public MapFactory(DBManager d) throws SQLException{
        db = d;
        //Statement s = db.getConnection().createStatement();
        //s.executeUpdate("USE mapping_test");
        //s.close();
    }
    
    /**
     * This function connects to the database and fetches all
     * nodes and based on the types, are placed in certain lists
     * for the map to use.
     * @return
     * @throws SQLException
     */
    public Map Build() throws SQLException{
        Map m = new Map();
        
        //Get a list of all the nodes and add them to the map
        Statement s = db.getConnection().createStatement();
        
        s.executeQuery("SELECT * FROM room");
        ResultSet rs = s.getResultSet();
        while(rs.next()){
            //Create a new node for each entry in the table and assign location
            int n = rs.getInt("Number");
            double x = rs.getDouble("X");
            double y = rs.getDouble("Y");
            double z = rs.getDouble("Z");
            String type = rs.getString("Type");
            Node tmp = new Node(n,x,y,z);
            //System.out.println("Number : "+n+"\tX : "+x+"\tY : "+y+"\tZ : "+z+"\tType : "+type);
            
            tmp.label = type;
            
            //Parse returned neighbors and add them in the node
            String ns = rs.getString("neighbors");
            String [] na = ns.split(",");
            for(int i = 0; i < na.length; i++){
                int b = Integer.parseInt(na[i]);
                tmp.addNeighbor(b);
            }
            System.out.println("\n ===== printing node info ===== ");
            tmp.Print();
            m.addNode(tmp);      
            System.out.println("node addeed to Map "+tmp.getId());
        }
        System.out.println("all nodes addeed to Map ");
        rs.close();
        s.close();
        return m;
    }
}