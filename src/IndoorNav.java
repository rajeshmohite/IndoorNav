import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.ArrayList;

public class IndoorNav {
//	private Socket server;
   
	private static DBManager db;
public static void main(String args[]) throws IOException{
	
	String line,input;
    DataInputStream in ;
    PrintStream out;
    String cordinates="";
	db = new DBManager();
	
        
        //========================
        input="";
        line="";
 /*      while((line = in.readLine()) != null && !line.equals("quit")) {
		    input=input + line;
	*/	    
//	    String ar [] = line.split(" ");   

        String argss [] = {"directions","331.309","50.888","3.0","5"};//from node 1 (LT)
      // String argss [] = {"directions","331.309","50.888","3.0","8"};//from node 5 (A-302B)
		    try{
		        Process(argss);
		    }
		    catch(SQLException e){
		        System.out.println(e);
		    }
	//	}

    //       System.out.println(server.getInetAddress() + " Disconnected ...");
		System.out.println("Bye");

        //============================
  }
    
    /**
     * This function is called from the main thread loop. It checks the command
     * with all known commands which performs and returns the proper
     * information.
     * 
     * @param l
     * @throws SQLException
     */
    public static void Process(String [] l) throws SQLException{
    //    System.out.print(server.getInetAddress() + ": ");
        for(int i = 0; i< l.length; i++){
            System.out.print(l[i] + " ");
        }
        System.out.println("\nHere is your arrray^^^^");

        System.out.println("l[0] = "+l[0]);
        if(l[0].equals("directions")){
            int start, destination;
            double x,y,z;
            
            x = Float.parseFloat(l[1]);
            
            y = Float.parseFloat(l[2]);
            
            z = Float.parseFloat(l[3]);
          
            destination = Integer.parseInt(l[4]);
            System.out.println("Destination node is : "+destination);
            db.Connect();
            System.out.println("connected to database susccesfully");
            MapFactory t = new MapFactory(db);
            System.out.println("map factory object created");
            Map m = t.Build();
            System.out.println("map created");
            start = m.FindNearestNode(x, y, z);
            
            System.out.println("Source ID : "+start+"\nDest ID : "+destination);
            ArrayList<Node> tmp = m.getDirection(start,destination);
            
            System.out.println("Printing arrayList from source to destination ");
            for(int i = 0; i< tmp.size();i++){
              //  out.println(tmp.get(i).getId());
                System.out.println(tmp.get(i).getId());
            }
           // out.println("DONE");
            System.out.println("DONE IT FINALLY");
            db.Disconnect();
        }/*
        if(l[0].equals("get-router-coord")){
            if(l[1] != null){
                db.Connect();
                RouterLookup(l[1]);
                db.Disconnect();
            }
            else{
                out.println("INVALID COMMAND");
            }
        }
        if(l[0].equals("get-routers")){
            db.Connect();
            AllRouters();
            db.Disconnect();
        }
        if(l[0].equals("get-room-list")){
            db.Connect();
            getRoomList();
            db.Disconnect();
        }        
        if(l[0].equals("db_test")){
            db.Connect();
            out.println(db.isConnected());
            db.Disconnect();
        }*/
    }
    /*
     * This function takes in a mac address, looks up its information
     * in the SQL database, and returns the information
     */
    /*private void RouterLookup(String mac) throws SQLException{
        Statement s = (Statement) db.getConnection().createStatement();
        s.executeQuery("SELECT * FROM routers WHERE mac=" + mac);
        ResultSet rs = s.getResultSet();
        if(rs.next()){
            double x = rs.getDouble("x");
            double y = rs.getDouble("y");
            double z = rs.getDouble("z");
            System.out.println(mac + " " + x + " " + y + " " + z);
        }
        else{
            System.out.println("ERROR: " + mac);
        }        
    }*/
    /*
     * This function retrieves all rooms from the database
     * and sends them to the connected client
     */
   /* private void getRoomList() throws SQLException{
        Statement s = (Statement) db.getConnection().createStatement();
        s.executeQuery("SELECT * FROM rooms");
        ResultSet rs = s.getResultSet();
        
        while(rs.next()){
            int rn = rs.getInt("number");
            String type = rs.getString("type");
            float x = rs.getFloat("x");
            float y = rs.getFloat("y");
            float z = rs.getFloat("z");
            
            
            
            System.out.println(rn + " " + type + " " + x + " " + y + " " + z);
        }
        System.out.println("DONE");
    }*/
    /*
     * This function retrieves all information about
     * the access points and sends them to the connected client
     */
    /*private void AllRouters() throws SQLException{
        Statement s = (Statement) db.getConnection().createStatement();
        s.executeQuery("SELECT * FROM routers");
        ResultSet rs = s.getResultSet();
        String message = "";
        while(rs.next()){
            String mac;
            float x,y,z;
            
            mac = rs.getString("mac");
            x = rs.getFloat("x");
            y = rs.getFloat("y");
            z = rs.getFloat("z");
            
            message += mac + " " + x + " " + y + " " + z + " ";
        }
        System.out.println(message);
    }    */
}
