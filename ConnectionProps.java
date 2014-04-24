/**
 * Created by nsama on 4/22/14.
 */
import java.util.*;
import java.io.*;

public class ConnectionProps {

    Properties props = null;

    public ConnectionProps () {
        try{
            props = new Properties();
            FileInputStream in = new FileInputStream("database.ini");
            props.load(in);
            in.close();
        }
        catch( FileNotFoundException ex ) {
            //Setting default properties
            //JOptionPane.showMessageDialog(null, "File with database connection settings was not found. Default settings will be used.", "Warning", JOptionPane.WARNING_MESSAGE);
            props.put("DATABASE_URL","jdbc:mysql://");
            props.put("DRIVER","com.mysql.jdbc.Driver");
            props.put("DATABASE","mysql");
            props.put("USER_NAME","root");
            props.put("HOST_NAME","localhost");
            props.put("PORT","3306");
            System.out.println("2Warning: "+ex);

        }
        catch( SecurityException ex ) {
            System.out.println("Warning: "+ex);
            return;
        }
        catch( IOException ex ) {
            System.out.println("Warning: "+ex);
            return;
        }
    }

    public ConnectionProps (Properties p) {
        try {
            props = new Properties(p);
            FileOutputStream out = new FileOutputStream("database.ini");
            p.store(out, "Database connection settings");
        }
        catch( Exception ex ) {
            System.out.println("Warning: "+ex);
            return;
        }
    }
}

