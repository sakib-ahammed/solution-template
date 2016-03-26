package com.tigerit.exam;


import static com.tigerit.exam.IO.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

/**
 * All of your application logic should be placed inside this class.
 * Remember we will load your application from our custom container.
 * You may add private method inside this class but, make sure your
 * application's execution points start from inside run method.
 */
public class Solution implements Runnable {
	private static ArrayList table_Name ;
	private static ArrayList<ArrayList<String>> table_col_Name ;
	private static ArrayList<ArrayList<ArrayList<Integer>>> DataBase;
   
   public Solution(){
	   table_Name = new ArrayList<String>();
	   Collections.synchronizedList(table_Name);
	   table_col_Name = new ArrayList<ArrayList<String>>();
	   Collections.synchronizedList(table_col_Name);
	   DataBase = new ArrayList<ArrayList<ArrayList<Integer>>>();
	   Collections.synchronizedList(DataBase);
   }
   
	
	@Override
    public void run() {
        // your application entry point

        // sample input process
//        String string = readLine();
//
//        Integer integer = readLineAsInteger();
		
		Integer T = readLineAsInteger();
		Integer test = 0;
		
		while(T != 0){
			T -= 1;
			
			table_Name.clear();
			table_col_Name.clear();
			DataBase.clear();
			
			Integer nT = readLineAsInteger();
			
			for(int i=0; i<nT; i++){
				String str = readLine();
				table_Name.add(str.trim());
				
				str = readLine();
				String[] RC = str.split("\\s+");
				
				Integer nC = Integer.parseInt(RC[0]);
				Integer nD = Integer.parseInt(RC[1]);
				
				str = readLine();
				String[] column_name = str.split("\\s+");
				
				ArrayList temp = new ArrayList<String>();
				
				for(int j=0; j<nC; j++){
					temp.add(column_name[j]);
				}
				
				table_col_Name.add(temp);
				
				ArrayList<ArrayList<Integer>> temp2D = new ArrayList<ArrayList<Integer>>();
				
				for(int j=0; j<nD; j++){
					str = readLine();
					String[] column_val = str.split("\\s+");
					
					ArrayList tempD = new ArrayList<Integer>();
					
					for(int k=0; k<nC; k++){
						tempD.add(Integer.parseInt(column_val[k]));
					}
					
					temp2D.add(tempD);	
				}
				
				DataBase.add(temp2D);
				//printLine(temp2D);
			}
			
			printLine("Test: "+ (++test));
			
			Integer nQ = readLineAsInteger();
			
			for(int i=0; i<nQ; i++){
				Integer table1=0;
				Integer table2=0;
				Integer tb1_c=0;
				Integer tb2_c=0;
				String tab1_sub = "";
				String tab2_sub = "";
				
				int t1, t2, t3;
				
				String select = readLine();
				String[] select_val = select.split("\\s*,\\s*");
				
				String from = readLine();
				String[] from_val = from.split("\\s+");
				
				String join = readLine();
				String[] join_val = join.split("\\s+");
				
				String on = readLine();
				String[] on_val = on.split("\\s+");
				
				
				// parsing query
				
				if(from_val.length == 2)
					table1 = table_Name.indexOf(from_val[1]);				
				else if(from_val.length == 3){
					table1 = table_Name.indexOf(from_val[1]);
					tab1_sub = from_val[2];
				}
				
				if(join_val.length == 2)
					table2 = table_Name.indexOf(join_val[1]);
				else if(join_val.length == 3){
					table2 = table_Name.indexOf(join_val[1]);
					tab2_sub = join_val[2];
				}
				
				// select parsing
				ArrayList select_tab1 = new ArrayList<Integer>();
				ArrayList select_tab2 = new ArrayList<Integer>();
				
				String col_name = "";
				select_val[0] = select_val[0].split("\\s+")[1];
				
				if( select_val[0].equals("*") )
				{
					for(int ss=0; ss<table_col_Name.get(table1).size(); ss++){
						col_name += table_col_Name.get(table1).get(ss) + " ";
						select_tab1.add(ss);
					}
					
					for(int ss=0; ss<table_col_Name.get(table2).size(); ss++){
						col_name += table_col_Name.get(table2).get(ss) + " ";
						select_tab2.add(ss);
					}
				}
				else{
					for(int s=0; s<select_val.length; s++){
						String[] tem_str = select_val[s].split("\\.");
						
						if(table_Name.indexOf(tem_str[0]) >= 0 ){
							if(table_Name.indexOf(tem_str[0]) == table1)
								select_tab1.add(table_col_Name.get(table1).indexOf(tem_str[1]));
							else if(table_Name.indexOf(tem_str[0]) == table2)
								select_tab2.add(table_col_Name.get(table2).indexOf(tem_str[1]));
						}
						else{
							if( tem_str[0].equals(tab1_sub) )
								select_tab1.add(table_col_Name.get(table1).indexOf(tem_str[1]));
							else if(tem_str[0].equals(tab2_sub))
								select_tab2.add(table_col_Name.get(table2).indexOf(tem_str[1]));
						}
							
					}

					for(int ss=0; ss<select_tab1.size(); ss++){
						col_name += table_col_Name.get(table1).get((int) select_tab1.get(ss)) + " ";
					}
						
					for(int ss=0; ss<select_tab2.size(); ss++){
						col_name += table_col_Name.get(table2).get((int)select_tab2.get(ss)) + " ";
					}
			
				}
				
				printLine(col_name);
				
				// On condition parsing
				String cnd1 = on_val[1];
				String cnd2 = on_val[3];
				
				String[] cnd1_split = cnd1.split("\\.");
				String[] cnd2_split = cnd2.split("\\.");
				
				//printLine(DataBase.get(table2));
				Integer result_table1=0;
				
				if(table_Name.indexOf(cnd1_split[0]) >= 0 )
					result_table1 = table_Name.indexOf(cnd1_split[0]);
				else{
					if( cnd1_split[0].equals(tab1_sub) )
						result_table1 = table1;
					else if( cnd1_split[0].equals(tab2_sub) )
						result_table1 = table2;
				}
				
				Integer result_table2=0;
				
				if(table_Name.indexOf(cnd2_split[0]) >= 0 )
					result_table2 = table_Name.indexOf(cnd2_split[0]);
				else{
					if( cnd2_split[0].equals(tab1_sub) )
						result_table2 = table1;
					else if( cnd2_split[0].equals(tab2_sub) )
						result_table2 = table2;
				}
				
				Integer result_table1_col = table_col_Name.get(result_table1).indexOf(cnd1_split[1]);
				Integer result_table2_col = table_col_Name.get(result_table2).indexOf(cnd2_split[1]);
							
				for(int tab1 = 0; tab1<DataBase.get(result_table1).size(); tab1++){
					for(int tab2=0; tab2<DataBase.get(result_table2).size(); tab2++){
						String col_val = "";
						if(DataBase.get(result_table1).get(tab1).get(result_table1_col) == DataBase.get(result_table2).get(tab2).get(result_table2_col)){
//							printLine(DataBase.get(result_table1).get(tab1));
//							printLine(DataBase.get(result_table2).get(tab2));
							
							for(int val=0; val<select_tab1.size(); val++)
								col_val += DataBase.get(result_table1).get(tab1).get((int) select_tab1.get(val)).toString() + " ";
							
							for(int val=0; val<select_tab2.size(); val++)
								col_val += DataBase.get(result_table2).get(tab2).get((int) select_tab2.get(val)).toString() + " ";
							
							printLine(col_val);
						}
					}
				}
				
				String string = readLine();
				printLine("");
			}

		}
        

    }
}
