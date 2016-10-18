
import java.io.*;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.nio.ByteBuffer;

public class DBproj2 {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		System.out.println("Select you option:");
		System.out.println("1. Parse into Binary | 2. Search");
		Scanner scan=new Scanner(System.in);
		String s=scan.next();		
		Integer x = Integer.valueOf(s);
		
		Map<Integer,ArrayList<Integer>> idmap=new HashMap<Integer,ArrayList<Integer>>();
		Map<String,ArrayList<Integer>> companymap=new HashMap<String,ArrayList<Integer>>();
		Map<String,ArrayList<Integer>> drugidmap=new HashMap<String,ArrayList<Integer>>();
		Map<Short,ArrayList<Integer>> trialsmap=new HashMap<Short,ArrayList<Integer>>();
		Map<Short,ArrayList<Integer>> patientsmap=new HashMap<Short,ArrayList<Integer>>();
		Map<Short,ArrayList<Integer>> dosagemap=new HashMap<Short,ArrayList<Integer>>();
		Map<Float,ArrayList<Integer>> readingmap=new HashMap<Float,ArrayList<Integer>>();
		Map<Boolean,ArrayList<Integer>> doubleblindmap=new HashMap<Boolean,ArrayList<Integer>>();
		Map<Boolean,ArrayList<Integer>> controlledstudymap=new HashMap<Boolean,ArrayList<Integer>>();
		Map<Boolean,ArrayList<Integer>> govtfundedmap=new HashMap<Boolean,ArrayList<Integer>>();
		Map<Boolean,ArrayList<Integer>> fdamap=new HashMap<Boolean,ArrayList<Integer>>();
		if(x==1){
			String csvfile="C:\\Users\\Arnav\\Desktop\\PHARMA_TRIALS_1000B.csv";
			//File binaryfile=new File("C:\\arnav\\Desktop\\Database.db");
			BufferedReader br=null;
			try {
				br = new BufferedReader(new FileReader(csvfile));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			File file = new File("data.db");
			file.createNewFile();
			FileOutputStream fo=new FileOutputStream(file); 
			int loc=0;
			String line="";
			int qq=0;
			try {
				while((line=br.readLine())!=null){
					String[] entry= line.split(",");
					String companyname="";
					int count=1;
					if(qq==0){
						qq++;
						continue;
					}
					
					//String ee=entry[0];
					//System.out.println(ee);
					int id=Integer.parseInt(entry[0]);
					
					char c=entry[1].charAt(0);
					char c1=entry[2].charAt(entry[2].length()-1);
					char c2=entry[3].charAt(entry[3].length()-1);
					if(c=='"' && c1=='"'){
						companyname=entry[1]+entry[2];
						count=2;
					}
										
					else if(c=='"' && c2=='"'){
						companyname=entry[1]+entry[2]+entry[3];
						count=3;
					}
					else{
						companyname=entry[1];
					}
					
					byte[] companynamebyte=companyname.getBytes();
					byte[] drug_id= entry[count+1].getBytes();					 
					short trials=Short.parseShort(entry[count+2]);
					short patients=Short.parseShort(entry[count+3]);
					short dosage_mg=Short.parseShort(entry[count+4]);
					float reading=Float.parseFloat(entry[count+5]);
					boolean double_blind=Boolean.parseBoolean(entry[count+6]);
					boolean controlled_study=Boolean.parseBoolean(entry[count+7]);
					boolean govt_funded=Boolean.parseBoolean(entry[count+8]);
					boolean fda_approved=Boolean.parseBoolean(entry[count+9]);
					//System.out.println(""+id+" "+companyname+" "+entry[count+1]);

					byte[] idbyte=ByteBuffer.allocate(4).putInt(id).array();
					fo.write(idbyte);

					int companycount=companynamebyte.length;
					byte companycountbyte=(byte)companycount;
					fo.write(companycountbyte);
					fo.write(companynamebyte);
					fo.write(drug_id);
					byte[] trialsbyte=ByteBuffer.allocate(2).putShort(trials).array();
					fo.write(trialsbyte);
					byte[] patientsbyte=ByteBuffer.allocate(2).putShort(patients).array();
					fo.write(patientsbyte);
					byte[] dosage_mg_byte=ByteBuffer.allocate(2).putShort(dosage_mg).array();
					fo.write(dosage_mg_byte);
					byte[] readingbyte=ByteBuffer.allocate(4).putFloat(reading).array();
					fo.write(readingbyte);
					int k=0; 
					if(double_blind==true){
						k=k+8;
					}
					if(controlled_study==true){
						k=k+4;
					}
					if(govt_funded){
						k=k+2;
					}
					if(fda_approved){
						k=k+1;
					}
					byte b=(byte) k;
					fo.write(b);
					
					//ID
					if(idmap.containsKey(id))
					{
						ArrayList<Integer> list=idmap.get(id);
						list.add(loc);
					}
					else
					{
						ArrayList<Integer> list=new ArrayList<Integer>();
						list.add(loc);
						idmap.put(id,list);
					}
					
					//Company
					if(companymap.containsKey(companyname))
					{
						ArrayList<Integer> list=companymap.get(companyname);
						list.add(loc);
					}
					else
					{
						ArrayList<Integer> list=new ArrayList<Integer>();
						list.add(loc);
						companymap.put(companyname,list);
					}
					
					//Drug ID
					if(drugidmap.containsKey(entry[count+1]))
					{
						ArrayList<Integer> list=drugidmap.get(entry[count+1]);
						list.add(loc);
					}
					else
					{
						ArrayList<Integer> list=new ArrayList<Integer>();
						list.add(loc);
						drugidmap.put(entry[count+1],list);
					}
					
					//Trials
					if(trialsmap.containsKey(trials))
					{
						ArrayList<Integer> list=trialsmap.get(trials);
						list.add(loc);
					}
					else
					{
						ArrayList<Integer> list=new ArrayList<Integer>();
						list.add(loc);
						trialsmap.put(trials,list);
					}
					
					//Patients
					if(patientsmap.containsKey(patients))
					{
						ArrayList<Integer> list=patientsmap.get(patients);
						list.add(loc);
					}
					else
					{
						ArrayList<Integer> list=new ArrayList<Integer>();
						list.add(loc);
						patientsmap.put(patients,list);
					}
					
					//Dosage MG
					if(dosagemap.containsKey(dosage_mg))
					{
						ArrayList<Integer> list=dosagemap.get(dosage_mg);
						list.add(loc);
					}
					else
					{
						ArrayList<Integer> list=new ArrayList<Integer>();
						list.add(loc);
						dosagemap.put(dosage_mg,list);
					}
					
					//Reading
					if(readingmap.containsKey(reading))
					{
						ArrayList<Integer> list=readingmap.get(reading);
						list.add(loc);
					}
					else
					{
						ArrayList<Integer> list=new ArrayList<Integer>();
						list.add(loc);
						readingmap.put(reading,list);
					}
					
					//Double Blind
					if(doubleblindmap.containsKey(double_blind))
					{
						ArrayList<Integer> list=doubleblindmap.get(double_blind);
						list.add(loc);
					}
					else
					{
						ArrayList<Integer> list=new ArrayList<Integer>();
						list.add(loc);
						doubleblindmap.put(double_blind,list);
					}
					
					//Controlled Study
					if(controlledstudymap.containsKey(controlled_study))
					{
						ArrayList<Integer> list=controlledstudymap.get(controlled_study);
						list.add(loc);
					}
					else
					{
						ArrayList<Integer> list=new ArrayList<Integer>();
						list.add(loc);
						controlledstudymap.put(controlled_study,list);
					}
					
					//Govt Funded
					if(govtfundedmap.containsKey(govt_funded))
					{
						ArrayList<Integer> list=govtfundedmap.get(govt_funded);
						list.add(loc);
					}
					else
					{
						ArrayList<Integer> list=new ArrayList<Integer>();
						list.add(loc);
						govtfundedmap.put(govt_funded,list);
					}
					
					//FDA Approved
					if(fdamap.containsKey(fda_approved))
					{
						ArrayList<Integer> list=fdamap.get(fda_approved);
						list.add(loc);
					}
					else
					{
						ArrayList<Integer> list=new ArrayList<Integer>();
						list.add(loc);
						fdamap.put(fda_approved,list);
					}
					//if(id==39) System.out.println("loc- "+loc);
					loc=loc+4+companycount+1+6+6+4+1;
					
				}
				
				
				FileOutputStream fid = new FileOutputStream("id.ndx");
				ObjectOutputStream idout = new ObjectOutputStream(fid);
				idout.writeObject(idmap);
				
				FileOutputStream fcompany = new FileOutputStream("company.ndx");
				ObjectOutputStream fout = new ObjectOutputStream(fcompany);
				fout.writeObject(companymap);
				
				FileOutputStream filedrug = new FileOutputStream("drug_id.ndx");
				ObjectOutputStream drugout = new ObjectOutputStream(filedrug);
				drugout.writeObject(drugidmap);

				FileOutputStream filetrials = new FileOutputStream("trials.ndx");
				ObjectOutputStream trailsout = new ObjectOutputStream(filetrials);
				trailsout.writeObject(trialsmap);
				
				FileOutputStream filepatients = new FileOutputStream("patients.ndx");
				ObjectOutputStream patientsout = new ObjectOutputStream(filepatients);
				patientsout.writeObject(patientsmap);
				
				FileOutputStream filedosage_mg = new FileOutputStream("dosage_mg.ndx");
				ObjectOutputStream doage_mgout = new ObjectOutputStream(filedosage_mg);
				doage_mgout.writeObject(dosagemap);
				
				FileOutputStream filereading = new FileOutputStream("reading.ndx");
				ObjectOutputStream readingout = new ObjectOutputStream(filereading);
				readingout.writeObject(readingmap);

				FileOutputStream filedoubleblind = new FileOutputStream("double_blind.ndx");
				ObjectOutputStream doubleblindout = new ObjectOutputStream(filedoubleblind);
				doubleblindout.writeObject(doubleblindmap);
				
				FileOutputStream filecontrolled = new FileOutputStream("controlled.ndx");
				ObjectOutputStream controlledout = new ObjectOutputStream(filecontrolled);
				controlledout.writeObject(controlledstudymap);
				
				FileOutputStream filegovt_funded = new FileOutputStream("govt_funded.ndx");
				ObjectOutputStream govt_fundedout = new ObjectOutputStream(filegovt_funded);
				govt_fundedout.writeObject(govtfundedmap);
				
				FileOutputStream filefda = new FileOutputStream("fda_approved.ndx");
				ObjectOutputStream fdaout = new ObjectOutputStream(filefda);
				fdaout.writeObject(fdamap);
				
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
			
		}
		if(x==2){
			System.out.println(" 1. ID \n 2. Company \n 3. Drug_ID \n 4. Trials \n 5. Patients \n 6. Dosage_mg \n 7. Reading \n 8. Double Blind \n 9. Controlled Study \n 10. Govt Funded \n 11. FDA Approved");
			Scanner s1=new Scanner(System.in);
			String scan1=s1.next();
			Integer x1 = Integer.valueOf(scan1);
			
			System.out.println("Select one of these: =,>,<,!=");
			Scanner s2=new Scanner(System.in);
			String scan2=s2.next();
			
			
			System.out.println("Enter Value");
			Scanner s3=new Scanner(System.in);
			String scan3=s3.nextLine();
			
			
			if(x1==1){
				Integer scan33 = Integer.valueOf(scan3);
				FileInputStream fileIn = new FileInputStream("id.ndx");
		        ObjectInputStream in = new ObjectInputStream(fileIn);
		        HashMap<Integer, ArrayList<Integer>> e=null;
				try {
					e = (HashMap <Integer,ArrayList<Integer>>) in.readObject();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				for(int i:e.keySet())
				{
					//System.out.println(scan2);
					if(scan2.equals(">")){
						if(i>scan33){
						ArrayList<Integer> b=e.get(i);
						int x2=0;
						//System.out.println(b);
						for(x2=0;x2<b.size();x2++){
							int tt=0;
							tt=b.get(x2);
							FileInputStream datadb = new FileInputStream("data.db");
							
							printing(datadb,tt);	
							datadb.close();
						}
						}
					}
					if(scan2.equals("<")){
						if(i<scan33){
						ArrayList<Integer> b=e.get(i);
						int x2=0;
							//System.out.println(b);
						for(x2=0;x2<b.size();x2++){
							int tt=0;
							tt=b.get(x2);
							FileInputStream datadb = new FileInputStream("data.db");
								
							printing(datadb,tt);	
							datadb.close();
						}
						}	
					}
					
					if(scan2.equals("!=")){
						if(i!=scan33){
						ArrayList<Integer> b=e.get(i);
						int x2=0;
							//System.out.println(b);
						for(x2=0;x2<b.size();x2++){
							int tt=0;
							tt=b.get(x2);
							FileInputStream datadb = new FileInputStream("data.db");
								
							printing(datadb,tt);	
							datadb.close();
						}
						}	
					}
					
				}
				if(scan2.equals("=")){
					ArrayList<Integer> b=e.get(scan33);
					int x2=0;
					//System.out.println(b);
					for(x2=0;x2<b.size();x2++){
						int tt=0;
						tt=b.get(x2);
						FileInputStream datadb = new FileInputStream("data.db");
						
						printing(datadb,tt);	
						datadb.close();
					}
				}
				
				
			}
			if(x1==2){
				FileInputStream fileIn1 = new FileInputStream("company.ndx");
		        ObjectInputStream in1 = new ObjectInputStream(fileIn1);
		        HashMap<String, ArrayList<Integer>> e=null;
		        try {
					e = (HashMap <String,ArrayList<Integer>>) in1.readObject();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		        //System.out.println(scan3);
		        for(String i:e.keySet()){
		        	if(scan2.equals("=")){
		        		if(i.equals(scan3)){
		        			ArrayList<Integer> b=e.get(i);
		    		        int x2=0;
		    				//System.out.println(b);
		    				for(x2=0;x2<b.size();x2++){
		    					int tt=0;
		    					tt=b.get(x2);
		    					FileInputStream datadb = new FileInputStream("data.db");
		    					//datadb.skip(tt);
		    					
		    					printing(datadb,tt);
		    					
		    						
		    					datadb.close();
		        		}
		        	}
		        }
		        		if(scan2.equals("!=")){
			        		if(!i.equals(scan3)){
			        			ArrayList<Integer> b=e.get(i);
			    		        int x2=0;
			    				//System.out.println(b);
			    				for(x2=0;x2<b.size();x2++){
			    					int tt=0;
			    					tt=b.get(x2);
			    					FileInputStream datadb = new FileInputStream("data.db");
			    					//datadb.skip(tt);
			    					
			    					printing(datadb,tt);
			    					
			    						
			    					datadb.close();
			        		}
			        	}		
		        
				}

		        }
		    }
			
			if(x1==3){
				FileInputStream fileIn1 = new FileInputStream("drug_id.ndx");
		        ObjectInputStream in1 = new ObjectInputStream(fileIn1);
		        HashMap<String, ArrayList<Integer>> e=null;
		        try {
					e = (HashMap <String,ArrayList<Integer>>) in1.readObject();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		       // System.out.println(scan3);
		        for(String i:e.keySet()){
		        	if(scan2.equals("=")){
		        		if(i.equals(scan3)){
		        			ArrayList<Integer> b=e.get(i);
		    		        int x2=0;
		    				//System.out.println(b);
		    				for(x2=0;x2<b.size();x2++){
		    					int tt=0;
		    					tt=b.get(x2);
		    					FileInputStream datadb = new FileInputStream("data.db");
		    					//datadb.skip(tt);
		    					
		    					printing(datadb,tt);
		    					
		    						
		    					datadb.close();
		        		}
		        	}
		        }
		        		if(scan2.equals("!=")){
			        		if(!i.equals(scan3)){
			        			ArrayList<Integer> b=e.get(i);
			    		        int x2=0;
			    				//System.out.println(b);
			    				for(x2=0;x2<b.size();x2++){
			    					int tt=0;
			    					tt=b.get(x2);
			    					FileInputStream datadb = new FileInputStream("data.db");
			    					//datadb.skip(tt);
			    					
			    					printing(datadb,tt);
			    					
			    						
			    					datadb.close();
			        		}
			        	}		
		        
				}

		        }	        
				
		}
			
			if(x1==4){
				short scan33=Short.parseShort(scan3);
				FileInputStream fileIn1 = new FileInputStream("trials.ndx");
		        ObjectInputStream in1 = new ObjectInputStream(fileIn1);
		        HashMap<Short, ArrayList<Integer>> e=null;
		        try {
					e = (HashMap <Short,ArrayList<Integer>>) in1.readObject();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		       // System.out.println(scan3);
		        for(short i:e.keySet())
				{
					//System.out.println(scan2);
					if(scan2.equals(">")){
						if(i>scan33){
						ArrayList<Integer> b=e.get(i);
						int x2=0;
						//System.out.println(b);
						for(x2=0;x2<b.size();x2++){
							int tt=0;
							tt=b.get(x2);
							FileInputStream datadb = new FileInputStream("data.db");
							
							printing(datadb,tt);	
							datadb.close();
						}
						}
					}
					if(scan2.equals("<")){
						if(i<scan33){
						ArrayList<Integer> b=e.get(i);
						int x2=0;
							//System.out.println(b);
						for(x2=0;x2<b.size();x2++){
							int tt=0;
							tt=b.get(x2);
							FileInputStream datadb = new FileInputStream("data.db");
								
							printing(datadb,tt);	
							datadb.close();
						}
						}	
					}
					
					if(scan2.equals("!=")){
						if(i!=scan33){
						ArrayList<Integer> b=e.get(i);
						int x2=0;
							//System.out.println(b);
						for(x2=0;x2<b.size();x2++){
							int tt=0;
							tt=b.get(x2);
							FileInputStream datadb = new FileInputStream("data.db");
								
							printing(datadb,tt);	
							datadb.close();
						}
						}	
					}
					
				}
		        if(scan2.equals("=")){
					ArrayList<Integer> b=e.get(scan33);
					int x2=0;
					//System.out.println(b);
					for(x2=0;x2<b.size();x2++){
						int tt=0;
						tt=b.get(x2);
						FileInputStream datadb = new FileInputStream("data.db");
						
						printing(datadb,tt);	
						datadb.close();
					}
				}
		}
		if(x1==5){
			short scan33=Short.parseShort(scan3);
			FileInputStream fileIn1 = new FileInputStream("patients.ndx");
	        ObjectInputStream in1 = new ObjectInputStream(fileIn1);
	        HashMap<Short, ArrayList<Integer>> e=null;
	        try {
				e = (HashMap <Short,ArrayList<Integer>>) in1.readObject();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	       // System.out.println(scan3);
	        for(short i:e.keySet())
			{
				//System.out.println(scan2);
				if(scan2.equals(">")){
					if(i>scan33){
					ArrayList<Integer> b=e.get(i);
					int x2=0;
					//System.out.println(b);
					for(x2=0;x2<b.size();x2++){
						int tt=0;
						tt=b.get(x2);
						FileInputStream datadb = new FileInputStream("data.db");
						
						printing(datadb,tt);	
						datadb.close();
					}
					}
				}
				if(scan2.equals("<")){
					if(i<scan33){
					ArrayList<Integer> b=e.get(i);
					int x2=0;
						//System.out.println(b);
					for(x2=0;x2<b.size();x2++){
						int tt=0;
						tt=b.get(x2);
						FileInputStream datadb = new FileInputStream("data.db");
							
						printing(datadb,tt);	
						datadb.close();
					}
					}	
				}
				
				if(scan2.equals("!=")){
					if(i!=scan33){
					ArrayList<Integer> b=e.get(i);
					int x2=0;
						//System.out.println(b);
					for(x2=0;x2<b.size();x2++){
						int tt=0;
						tt=b.get(x2);
						FileInputStream datadb = new FileInputStream("data.db");
							
						printing(datadb,tt);	
						datadb.close();
					}
					}	
				}
				
			}
	        if(scan2.equals("=")){
				ArrayList<Integer> b=e.get(scan33);
				int x2=0;
				//System.out.println(b);
				for(x2=0;x2<b.size();x2++){
					int tt=0;
					tt=b.get(x2);
					FileInputStream datadb = new FileInputStream("data.db");
					
					printing(datadb,tt);	
					datadb.close();
				}
			}
	        
	        		
		}
		if(x1==6){
			short scan33=Short.parseShort(scan3);
			FileInputStream fileIn1 = new FileInputStream("dosage_mg.ndx");
	        ObjectInputStream in1 = new ObjectInputStream(fileIn1);
	        HashMap<Short, ArrayList<Integer>> e=null;
	        try {
				e = (HashMap <Short,ArrayList<Integer>>) in1.readObject();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	       // System.out.println(scan3);
	        for(short i:e.keySet())
			{
				//System.out.println(scan2);
				if(scan2.equals(">")){
					if(i>scan33){
					ArrayList<Integer> b=e.get(i);
					int x2=0;
					//System.out.println(b);
					for(x2=0;x2<b.size();x2++){
						int tt=0;
						tt=b.get(x2);
						FileInputStream datadb = new FileInputStream("data.db");
						
						printing(datadb,tt);	
						datadb.close();
					}
					}
				}
				if(scan2.equals("<")){
					if(i<scan33){
					ArrayList<Integer> b=e.get(i);
					int x2=0;
						//System.out.println(b);
					for(x2=0;x2<b.size();x2++){
						int tt=0;
						tt=b.get(x2);
						FileInputStream datadb = new FileInputStream("data.db");
							
						printing(datadb,tt);	
						datadb.close();
					}
					}	
				}
				
				if(scan2.equals("!=")){
					if(i!=scan33){
					ArrayList<Integer> b=e.get(i);
					int x2=0;
						//System.out.println(b);
					for(x2=0;x2<b.size();x2++){
						int tt=0;
						tt=b.get(x2);
						FileInputStream datadb = new FileInputStream("data.db");
							
						printing(datadb,tt);	
						datadb.close();
					}
					}	
				}
				
			}
	        if(scan2.equals("=")){
				ArrayList<Integer> b=e.get(scan33);
				int x2=0;
				//System.out.println(b);
				for(x2=0;x2<b.size();x2++){
					int tt=0;
					tt=b.get(x2);
					FileInputStream datadb = new FileInputStream("data.db");
					
					printing(datadb,tt);	
					datadb.close();
				}
			}
	       
		}
		if(x1==7){
			FileInputStream fileIn1 = new FileInputStream("reading.ndx");
	        ObjectInputStream in1 = new ObjectInputStream(fileIn1);
	        HashMap<Float, ArrayList<Integer>> e=null;
	        try {
				e = (HashMap <Float,ArrayList<Integer>>) in1.readObject();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	       // System.out.println(scan3);
	        float reading=Float.parseFloat(scan3);
	        for(float i:e.keySet())
			{
				//System.out.println(scan2);
				if(scan2.equals(">")){
					if(i>reading){
					ArrayList<Integer> b=e.get(i);
					int x2=0;
					//System.out.println(b);
					for(x2=0;x2<b.size();x2++){
						int tt=0;
						tt=b.get(x2);
						FileInputStream datadb = new FileInputStream("data.db");
						
						printing(datadb,tt);	
						datadb.close();
					}
					}
				}
				if(scan2.equals("<")){
					if(i<reading){
					ArrayList<Integer> b=e.get(i);
					int x2=0;
						//System.out.println(b);
					for(x2=0;x2<b.size();x2++){
						int tt=0;
						tt=b.get(x2);
						FileInputStream datadb = new FileInputStream("data.db");
							
						printing(datadb,tt);	
						datadb.close();
					}
					}	
				}
				
				if(scan2.equals("!=")){
					if(i!=reading){
					ArrayList<Integer> b=e.get(i);
					int x2=0;
						//System.out.println(b);
					for(x2=0;x2<b.size();x2++){
						int tt=0;
						tt=b.get(x2);
						FileInputStream datadb = new FileInputStream("data.db");
							
						printing(datadb,tt);	
						datadb.close();
					}
					}	
				}
				
			}
	        if(scan2.equals("=")){
				ArrayList<Integer> b=e.get(reading);
				int x2=0;
				//System.out.println(b);
				for(x2=0;x2<b.size();x2++){
					int tt=0;
					tt=b.get(x2);
					FileInputStream datadb = new FileInputStream("data.db");
					
					printing(datadb,tt);	
					datadb.close();
				}
			}
	        
		}
		if(x1==8){
			FileInputStream fileIn1 = new FileInputStream("double_blind.ndx");
	        ObjectInputStream in1 = new ObjectInputStream(fileIn1);
	        HashMap<Boolean, ArrayList<Integer>> e=null;
	        try {
				e = (HashMap <Boolean,ArrayList<Integer>>) in1.readObject();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	       // System.out.println(scan3);
	        boolean blind=Boolean.parseBoolean(scan3);
	        
	        for(boolean i:e.keySet())
			{
				//System.out.println(scan2);
				
				
				
				if(scan2.equals("!=")){
					if(i!=blind){
					ArrayList<Integer> b=e.get(i);
					int x2=0;
						//System.out.println(b);
					for(x2=0;x2<b.size();x2++){
						int tt=0;
						tt=b.get(x2);
						FileInputStream datadb = new FileInputStream("data.db");
							
						printing(datadb,tt);	
						datadb.close();
					}
					}	
				}
				
			}
	        if(scan2.equals("=")){
				ArrayList<Integer> b=e.get(blind);
				int x2=0;
				//System.out.println(b);
				for(x2=0;x2<b.size();x2++){
					int tt=0;
					tt=b.get(x2);
					FileInputStream datadb = new FileInputStream("data.db");
					
					printing(datadb,tt);	
					datadb.close();
				}
			}
	       
		}
		if(x1==9){
			FileInputStream fileIn1 = new FileInputStream("controlled.ndx");
	        ObjectInputStream in1 = new ObjectInputStream(fileIn1);
	        HashMap<Boolean, ArrayList<Integer>> e=null;
	        try {
				e = (HashMap <Boolean,ArrayList<Integer>>) in1.readObject();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	       // System.out.println(scan3);
	        boolean blind=Boolean.parseBoolean(scan3);
	        
	        
	        for(boolean i:e.keySet())
			{
				//System.out.println(scan2);
				
				
				
				if(scan2.equals("!=")){
					if(i!=blind){
					ArrayList<Integer> b=e.get(i);
					int x2=0;
						//System.out.println(b);
					for(x2=0;x2<b.size();x2++){
						int tt=0;
						tt=b.get(x2);
						FileInputStream datadb = new FileInputStream("data.db");
							
						printing(datadb,tt);	
						datadb.close();
					}
					}	
				}
				
			}
	        if(scan2.equals("=")){
				ArrayList<Integer> b=e.get(blind);
				int x2=0;
				//System.out.println(b);
				for(x2=0;x2<b.size();x2++){
					int tt=0;
					tt=b.get(x2);
					FileInputStream datadb = new FileInputStream("data.db");
					
					printing(datadb,tt);	
					datadb.close();
				}
			}
		}
		if(x1==10){
			FileInputStream fileIn1 = new FileInputStream("govt_funded.ndx");
	        ObjectInputStream in1 = new ObjectInputStream(fileIn1);
	        HashMap<Boolean, ArrayList<Integer>> e=null;
	        try {
				e = (HashMap <Boolean,ArrayList<Integer>>) in1.readObject();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	       // System.out.println(scan3);
	        boolean blind=Boolean.parseBoolean(scan3);
	        
	        
	        for(boolean i:e.keySet())
			{
				//System.out.println(scan2);
				
				
				
				if(scan2.equals("!=")){
					if(i!=blind){
					ArrayList<Integer> b=e.get(i);
					int x2=0;
						//System.out.println(b);
					for(x2=0;x2<b.size();x2++){
						int tt=0;
						tt=b.get(x2);
						FileInputStream datadb = new FileInputStream("data.db");
							
						printing(datadb,tt);	
						datadb.close();
					}
					}	
				}
				
			}
	        if(scan2.equals("=")){
				ArrayList<Integer> b=e.get(blind);
				int x2=0;
				//System.out.println(b);
				for(x2=0;x2<b.size();x2++){
					int tt=0;
					tt=b.get(x2);
					FileInputStream datadb = new FileInputStream("data.db");
					
					printing(datadb,tt);	
					datadb.close();
				}
			}
		}
		if(x1==11){
			FileInputStream fileIn1 = new FileInputStream("fda_approved.ndx");
	        ObjectInputStream in1 = new ObjectInputStream(fileIn1);
	        HashMap<Boolean, ArrayList<Integer>> e=null;
	        try {
				e = (HashMap <Boolean,ArrayList<Integer>>) in1.readObject();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	       // System.out.println(scan3);
	        boolean blind=Boolean.parseBoolean(scan3);
	        
	        
	        for(boolean i:e.keySet())
			{
				//System.out.println(scan2);
				
				
				
				if(scan2.equals("!=")){
					if(i!=blind){
					ArrayList<Integer> b=e.get(i);
					int x2=0;
						//System.out.println(b);
					for(x2=0;x2<b.size();x2++){
						int tt=0;
						tt=b.get(x2);
						FileInputStream datadb = new FileInputStream("data.db");
							
						printing(datadb,tt);	
						datadb.close();
					}
					}	
				}
				
			}
	        if(scan2.equals("=")){
				ArrayList<Integer> b=e.get(blind);
				int x2=0;
				//System.out.println(b);
				for(x2=0;x2<b.size();x2++){
					int tt=0;
					tt=b.get(x2);
					FileInputStream datadb = new FileInputStream("data.db");
					
					printing(datadb,tt);	
					datadb.close();
				}
			}
		}
		
	}

}
	public static void printing(FileInputStream datadb,int tt){
		try {
			datadb.skip(tt);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		byte[] hh=new byte[4];
		try {
			datadb.read(hh);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int result = ByteBuffer.wrap(hh).getInt();
		System.out.print(result);
		
		byte[] hh1=new byte[1];
		try {
			datadb.read(hh1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int companysize = (int) hh1[0];
		byte[] comp=new byte[companysize];
		try {
			datadb.read(comp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String s5=new String(comp);
		System.out.print(" "+s5);
		
		byte[] hh2=new byte[6];
		try {
			datadb.read(hh2);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String s6=new String(hh2);
		System.out.print(" "+s6);
		
		byte[] hh3=new byte[2];
		try {
			datadb.read(hh3);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Short s7 = ByteBuffer.wrap(hh3).getShort();
		System.out.print(" "+s7);

		byte[] hh4=new byte[2];
		try {
			datadb.read(hh4);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Short s8 = ByteBuffer.wrap(hh4).getShort();
		System.out.print(" "+s8);
		
		byte[] hh5=new byte[2];
		try {
			datadb.read(hh5);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Short s9 = ByteBuffer.wrap(hh5).getShort();
		System.out.print(" "+s9);
		
		byte[] hh6=new byte[4];
		try {
			datadb.read(hh6);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Float s10 = ByteBuffer.wrap(hh6).getFloat();
		System.out.print(" "+s10);
		
		byte[] hh7=new byte[1];
		try {
			datadb.read(hh7);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int truefalse=(int) hh7[0];
		
		if(truefalse>7){
			System.out.print(" True");
			truefalse=truefalse-8;
		}
		else
			System.out.print(" False");
		
		if(truefalse>3){
			System.out.print(" True");
			truefalse=truefalse-4;
		}
		else
			System.out.print(" False");
		
		if(truefalse>1){
			System.out.print(" True");
			truefalse=truefalse-2;
		}
		else
			System.out.print(" False");
		
		if(truefalse==1)
			System.out.println(" True");
		else if(truefalse==0)
			System.out.println(" False");
	}
}
