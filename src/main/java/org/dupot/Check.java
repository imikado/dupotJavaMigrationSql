package org.dupot;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Check {

    public static  final String TABLE_NAME="migrationVersion";

    protected String username="postgres";
    protected String password="example";
    protected String stringConnexion="jdbc:postgresql://127.0.0.1:5432/myDb";

    Connection c = null;

    Statement stmt = null;

        public boolean doesTableExist(){

            try {

                Class.forName("org.postgresql.Driver");

                System.out.println("try to connect to:"+stringConnexion);



                c = DriverManager.getConnection(stringConnexion,username, password);

//              c.setAutoCommit(false);

                System.out.println("Successfully Connected.");



                stmt = c.createStatement();

                ResultSet rs = stmt.executeQuery( "SELECT tablename  FROM pg_catalog.pg_tables      WHERE schemaname != 'pg_catalog' AND           schemaname != 'information_schema';" );

                while ( rs.next() ) {


                    String  tableLoop = rs.getString("tablename");


                    System.out.printf( "table found : %s", tableLoop);

                    System.out.println();

                    if(tableLoop.equals(TABLE_NAME) ){

                        rs.close();

                        stmt.close();

                        c.close();

                        return true;
                    }

                }

                rs.close();

                stmt.close();

                c.close();

                return false;

            } catch ( Exception e ) {

                System.err.println("Exception: ");
                System.err.println( e.getClass().getName()+": "+ e.getMessage() );

                System.exit(0);

                return false;
            }
        }

        public void createTable(){
            try {

                Class.forName("org.postgresql.Driver");

                c = DriverManager.getConnection(stringConnexion,username, password);

                stmt = c.createStatement();

                String tableSql="CREATE SEQUENCE "+TABLE_NAME+"_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1;\n";
                tableSql+="CREATE TABLE \"public\".\""+TABLE_NAME+"\" ( ";
                tableSql+=" \"id\" integer DEFAULT nextval('"+TABLE_NAME+"_id_seq') NOT NULL, ";
                tableSql+=" \"datetime\" date NOT NULL, ";
                tableSql+=" \"version\" character varying(100) NOT NULL, ";
                tableSql+=" \"filename\" character varying(150) NOT NULL, ";
                tableSql+="CONSTRAINT \""+TABLE_NAME+"_pkey\" PRIMARY KEY (\"id\") ";
                tableSql+=") WITH (oids = false);";

                stmt.execute( tableSql );


                stmt.close();

                c.close();


            } catch ( Exception e ) {

                System.err.println("Exception: ");
                System.err.println( e.getClass().getName()+": "+ e.getMessage() );

                System.exit(0);

            }
        }




}
