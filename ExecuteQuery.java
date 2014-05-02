import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.StringTokenizer;


public class ExecuteQuery {

	private static ExecuteQuery instance = null;
	
	private ExecuteQuery() {
		
	}
	
	public static ExecuteQuery getInstance() {
		if (instance == null) {
			instance = new ExecuteQuery();
		}
		return instance;
	}
	
	private static String spacedString(String s, int w) {
        if ( s==null ) s="null";
        while ( s.length()<w ) {
            s+=" ";
        }
        return s;
    }
    private static String dashedString(String s, int w) {
        while ( s.length()<w ) {
            s+="-";
        }
        return s;
    }



    public static String executeResults(String sql, DBConnection dbc) {
    	String result = "";
    	
        String str = "";
        StringTokenizer st = new StringTokenizer(sql);

        if ( st.hasMoreElements() ) {
            str+=st.nextElement();
        }
        if ( st.hasMoreElements() ) {
            str+=st.nextElement();
        }

        if ( str.length()==0 ) {
            result = "SQL statement is empty";
            return result;
        }

        if ( (str.length()>=6 && str.substring(0,6).equalsIgnoreCase("SELECT")) || (str.length()>=4 && str.substring(0,4).equalsIgnoreCase("SHOW")) || (str.length()>=4 && str.substring(0,4).equalsIgnoreCase("DESC")) ) {

            try {
                ResultSet r = dbc.executeQuery(sql);
                ResultSetMetaData meta = r.getMetaData();
                String out="";
                int w[] = new int[meta.getColumnCount()];
                for ( int i=1; i<=meta.getColumnCount(); i++ ) {
                    w[i-1] = meta.getColumnLabel(i).length();
                    r.beforeFirst();
                    while ( r.next() ) {
                        String temp = new String("");
                        if(r.getString(i) == null) temp="----";
                        else temp = r.getString(i);
                        if (w[i-1] < temp.length()) w[i-1] = temp.length();
                    }
                    r.beforeFirst();
                }

                for ( int i=1; i<meta.getColumnCount(); i++ ) {
                    w[i-1] += 3;
                }

                for ( int i=1; i<=meta.getColumnCount(); i++ ) {
                    out += spacedString(meta.getColumnLabel(i),w[i-1]);
                }
                out+="\n";
                for ( int i=1; i<=meta.getColumnCount(); i++ ) {
                    out += dashedString("",w[i-1]-1);
                    if (i==meta.getColumnCount()) out+="-+";
                    else out+="+";
                }
                out+="\n";

                while ( r.next() ) {
                    for ( int i=1; i<=meta.getColumnCount(); i++ ) {
                        out+=spacedString(r.getString(i),w[i-1]);
                    }
                    out+="\n";
                }
                result = out;

                return result;
            }
            catch (SQLException ex) {
                result = "Error: "+ex;
                return result;
            }
        }


        try {
            int r = dbc.executeUpdate(sql);

            if ( str.equalsIgnoreCase("CREATETABLE") && r == 0 ) {result = "Table was created successfully";}
            else
            if ( str.equalsIgnoreCase("CREATEINDEX") && r == 0 ) {result = "Index was created successfully";}
            else
            if ( str.equalsIgnoreCase("DROPTABLE") && r == 0 )   {result = "Table was droped successfully";}
            else
            if ( str.equalsIgnoreCase("DROPINDEX") && r == 0 )   {result = "Index was droped successfully";}
            else
            if ( str.length()>=6 && str.substring(0,6).equalsIgnoreCase("INSERT") ) {result = Integer.toString(r)+" rows were inserted successfully";}
            else
            if ( str.length()>=6 && str.substring(0,6).equalsIgnoreCase("DELETE") ) {result = Integer.toString(r)+" rows were deleted successfully";}
            else
            if ( str.length()>=6 && str.substring(0,6).equalsIgnoreCase("UPDATE") ) {result = Integer.toString(r)+" rows were updated successfully";}
            else
            if ( r == 0 )   {result = "Statement was executed successfully";}


        }
        catch (SQLException ex) {
            result = "Error: "+ex;
            return result;
        }
        
        return result;
    }
}
