import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.Arrays;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.Set;
import java.util.Iterator;
import java.io.Serializable;
import java.io.OutputStream;
import java.nio.file.Paths;


class Employee implements Serializable, Comparable<Employee> {

    private static final long serialVersionUID = 1L;
    private Long employeeID;
    private String employeeName;
    private String employeeDepartment;
    private Long employeePhoneNumber;

    public void setEmployeeID(Long employeeID) {
        this.employeeID = employeeID;
    }
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
    public void setEmployeeDepartment(String employeeDepartment) {
        this.employeeDepartment = employeeDepartment;
    }
    public void setEmployeePhoneNumber(Long employeePhoneNumber) {
        this.employeePhoneNumber = employeePhoneNumber;
    }

    public Long getEmployeeID() {
        return this.employeeID;
    }
    public String getEmployeeName() {
        return this.employeeName;
    }
    public String getEmployeeDepartment() {
        return this.employeeDepartment;
    }
    public Long getEmployeePhoneNumber() {
        return this.employeePhoneNumber;
    }
    @Override
    public String toString() {
        return "Employee ID = "+this.getEmployeeID();
    }
    @Override
    public int compareTo(Employee e) {
        return this.getEmployeeID().compareTo(e.getEmployeeID());
    }
}

public class Main extends Employee {
    private static final long serialVersionUID = 1L;
    private int user_input;
    private static int counter;
	static File f = null;
	public static ArrayList<String> fl = new ArrayList<String>();
	public static ArrayList<Employee> al = new ArrayList<>();
	static File f1 = null;
	public static int ic = 0;
	public static int fc = 1;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    
    public static ArrayList<Employee> read(File f) throws Exception {
        ArrayList<Employee> al = new ArrayList<Employee>();
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(f);
            ois = new ObjectInputStream(fis);
            while(fis.available() > 0) {
                Employee e = null;
                try {
                    e = (Employee)ois.readObject();
                    if(e == null)
                        System.out.println("Error..");
                    al.add(e);
                }
                catch(Exception x) {
                    x.printStackTrace();
                }   
            }
        }
        finally {
            try {
            if(ois == null)
                ois.close();
            if(fis == null)
                fis.close();
            }
            catch(NullPointerException np) {
                np.printStackTrace();
            }
        }
        return al;
    }
    public static void write(File f, boolean append, Employee e) throws Exception {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            if(append == false) {
				fos = new FileOutputStream(f);
				oos = new ObjectOutputStream(fos);
				oos.writeObject(e);
				System.out.println("Writing Employee with ID: "+e.getEmployeeID()+" in "+f.getAbsolutePath());
            }
            else {
                oos = new AppendingObjectOutputStream(new FileOutputStream(f, append));
				oos.writeObject(e);
				System.out.println("Appending Employee with ID: "+e.getEmployeeID()+" in "+f.getAbsolutePath());
            }
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
        finally {
            try {
            if(oos != null)
                oos.close();
            if(fos != null)
                fos.close();
			}
			catch(IOException io) {
				io.printStackTrace();
			}
		}
    }
    public static void writePropertiesFile(Long id, String fileName) {
        FileOutputStream fileOut = null;
        FileInputStream fileIn = null;
        try {
            Properties prop = new Properties();
			File file = new File(Paths.get("").toAbsolutePath().toString()+"\\employees.properties");
            if(!file.exists())
                file.createNewFile();
            fileIn = new FileInputStream(file);
            prop.load(fileIn);
            prop.setProperty(Long.toString(id), fileName);
            fileOut = new FileOutputStream(file);
            prop.store(fileOut, null);

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
			try {
                fileOut.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
	public static String readPropertiesFile(Long id) {
		String fileName = null;
		FileInputStream fin = null;
		try {
			Properties prop = new Properties();
			File f = new File(Paths.get("").toAbsolutePath().toString()+"\\"+"employees.properties");
			if(!f.exists())
				System.out.println("File Does not exists");
			fin = new FileInputStream(f);
			prop.load(fin);
			Set<String> keys = prop.stringPropertyNames();
			for(String key : keys) {
				if(key.equals(Long.toString(id)))
					fileName = prop.getProperty(key);
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return fileName;
	}

	public static void update(ArrayList<String> as, BufferedReader br) throws Exception {
		Employee e = new Employee();
		ArrayList<Employee> al = null;
		System.out.println("Enter ID of Employee whose Phone Number you want to update");
		Long id = Long.parseLong(br.readLine());
		String fileName = readPropertiesFile(id);
		if(fileName == null) {
			 System.out.println("Sorry, Employee ID does not exists");
			 return;
		} 
		else{
			try {
				al = read(new File(fileName));
			}
			catch(FileNotFoundException fe) {
				System.out.println(fe.getMessage());
				return ;
			}
			catch(IOException ioe) {
				System.out.println(ioe.getMessage());
				return ;
			}
			catch(ClassNotFoundException cce) {
				System.out.println(cce.getMessage());
				return;
			} 
			String name = getEmployeeRecord(al, id).get(1);
			String dept = getEmployeeRecord(al, id).get(2);
			System.out.println("Enter New Phone Number");
			Long newPhone = Long.parseLong(br.readLine());  
			e.setEmployeeID(id);
			e.setEmployeeName(name);
			e.setEmployeeDepartment(dept);
			e.setEmployeePhoneNumber(newPhone);
			Iterator it = al.iterator();
			while(it.hasNext()) {
				Employee temp = (Employee)it.next();
				if(temp.getEmployeeID().equals(id))
					it.remove();
			}
			al.add(e);
			new File(fileName).delete();
			it = al.iterator();
			int ct = 0;
			File f = new File(fileName);
			try {
			while(it.hasNext()) {
				Employee temp = (Employee)it.next();
				if(ct == 0)
				write(f, false, temp);
				else 
				write(f, true, temp);
				ct++;
			}
			}
			catch(Exception ex) {
				ex.printStackTrace();
			}
			System.out.println("Okay, Record Updated in File "+fileName);
		}
	}
	public static int getLastFileSize() throws Exception {
		ArrayList<String> al = getEmployeeRecordFileNamesFromBaseFolder();
		int count = 0;
		try {
			count = read(new File(al.get(al.size() - 1))).size();

		}
		catch(FileNotFoundException fnex) {
			return count;
		}
		catch(ClassCastException ccex) {
			return count;
		}
		catch(IOException ioex) {
			return count;
		}
		return count;
	}
	public static boolean isLessThanFiveRecords() throws Exception {
		ArrayList<String> al = getEmployeeRecordFileNamesFromBaseFolder();
		try {
			if (read(new File(al.get(al.size() - 1))).size() < 5) {
				return true;
			}
		}
		catch(FileNotFoundException fnex) {
			return false;
		}
		catch(ClassCastException ccex) {
			return false;
		}
		catch(IOException ioex) {
			return false;
		}
		return false;
	}
		
    public static boolean isEmployeeIDAlreadyExists(ArrayList<Employee> e, Long id) {
        if(e.isEmpty())
            System.out.println("List is Empty");
        for(Employee emp : e) 
            if(emp.getEmployeeID().equals(id))
                return true;
        return false;
    }
	
	public static ArrayList<String> getEmployeeRecordFileNamesFromBaseFolder() {
		ArrayList<String> al = new ArrayList<>();
		String directory = Paths.get("").toAbsolutePath().toString();
		File[] afl = new File(directory).listFiles();
		Arrays.sort(afl, new Comparator<File>() {
			@Override
			public int compare(File o1, File o2) {
				int n1 = extractNumber(o1.getName());
				int n2 = extractNumber(o2.getName());
				return n1 - n2;
			}

			private int extractNumber(String name) {
				int i = 0;
				try {
					int s = name.indexOf(' ') + 1;
					int e = name.lastIndexOf('.');
					String number = name.substring(s, e);
					i = Integer.parseInt(number);
				} catch(Exception e) {
					i = 0;
				}
				return i;
			}
        });
		for (int i = 0; i < afl.length; ++i) {
			if (afl[i].isFile() && afl[i].getName().endsWith(".ser")) {
				al.add(afl[i].getName());
			}
		}
		return al;
	}
	
	public static Employee returnObjectAfterSetting() throws IOException {
		Employee e = new Employee();
		try {
			System.out.println("Enter Employee ID: ");
			Long i = Long.parseLong(br.readLine());
			e.setEmployeeID(i);
			System.out.println("Enter Employee Name: ");
			e.setEmployeeName(br.readLine());
			System.out.println("Enter Employee Department: ");
			e.setEmployeeDepartment(br.readLine());
			System.out.println("Enter Employee Phone Number: ");
			e.setEmployeePhoneNumber(Long.parseLong(br.readLine()));
		}
		catch(Exception x) {
			x.printStackTrace();
		}
		return e;
	}
	
	public static File returnFileObject(int fc) throws FileNotFoundException, IOException {
		File f = null;
		try {
			f = new File("Employee "+fc+".ser");
			if (!f.exists()) {
				f.createNewFile();
			}
		}
		catch(FileNotFoundException e){
			System.out.println(e.getMessage());
		}
		return f;
	}
		
    public static void displayFormattedOutput(ArrayList<Employee> e) {
        System.out.format("%35s%n", "EMPLOYEES INFORMATION");
        System.out.println("-----------------------------------------------");
        System.out.format("%-10s%-10s%10s%15s%n", "ID", "Name", "Dept", "Phone No");
        System.out.println("-----------------------------------------------");
        for(Employee emp : e) {
            System.out.format("%-10d%-10s%10s%15d%n", emp.getEmployeeID(), emp.getEmployeeName(), emp.getEmployeeDepartment(), emp.getEmployeePhoneNumber());
        }
        System.out.println("-----------------------------------------------");    
    }
	
    public static ArrayList<String> getEmployeeRecord(ArrayList<Employee> e, Long id) {
        ArrayList<String> result = new ArrayList<>();
        for(Employee emp : e) {
            if(emp.getEmployeeID().equals(id)) {
                result.add(Long.toString(emp.getEmployeeID()));
                result.add(emp.getEmployeeName());
                result.add(emp.getEmployeeDepartment());
                result.add(Long.toString(emp.getEmployeePhoneNumber()));
            }
        }
        return result;
    }
	
    public void displayUserChoices() {
        System.out.println("Menu:");
        System.out.println("1. Add Employee");
        System.out.println("2. View All Employees Information");
        System.out.println("3. Update Employee Information");
        System.out.println("4. Exit");
        System.out.println("Enter Choice Number to select Choice");
    }

    public static void main(String[] args) throws Exception {
        Main q = new Main();
		Employee emp_ = null;
		int total = 0;
        while(true) {
            q.displayUserChoices();
            q.user_input = Integer.parseInt(br.readLine());
            switch(q.user_input) {
                case 1:
                {
                    try {
                        if(counter == 0 || counter % 5 == 0) {
							boolean append = false;
							int size = getEmployeeRecordFileNamesFromBaseFolder().size();
							if(size == 0) {
								f = returnFileObject(fc);
								++counter;
							}
							else {
								 if(isLessThanFiveRecords()) {
									fc = size;
									f = returnFileObject(fc);
									counter = getLastFileSize();
									++counter;
									append = true;
								 }
								 else {
									total += 5;
									fc++;
									f = returnFileObject(fc);
									append = false;
									++counter;
								 }
							}
							emp_ = returnObjectAfterSetting();
							write(f, append, emp_);
							writePropertiesFile(emp_.getEmployeeID(), f.getName());
						}
							
						else if (counter < total + 5) {
							emp_ = returnObjectAfterSetting();
							write(f, true, emp_);
							writePropertiesFile(emp_.getEmployeeID(), f.getName());
							counter++;
						}
					}
                    catch(FileNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    catch(IOException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                }
				
                case 2: {
					try {
							fl = getEmployeeRecordFileNamesFromBaseFolder();
							if(!fl.isEmpty()) {
								for(String u : fl) {
								  System.out.println("File Name: "+u);
								  ArrayList<Employee> a  = read(new File(u));
								  displayFormattedOutput(a);
								}
							}
							else {
								System.out.println("Cannot Read..");
							}
					}
					catch(FileNotFoundException e) {
						System.out.println(e.getMessage());
					}
					catch(IOException e) {
						System.out.println(e.getMessage());
					}
					catch(ClassNotFoundException e) {
						System.out.println(e.getMessage());
					}  
					break;
                }
				
				case 3: {
						try {
							update(fl, br);
						}
						catch(Exception ex) {
							ex.printStackTrace();
						}
						break;
				}
				
				case 4: {
					System.out.println("Exiting, Bye");
					System.exit(0);
					break;
				}
				
				default : {
					System.out.println("Please Enter Correct Choice");
				}
            }
        }
    }
}
class AppendingObjectOutputStream extends ObjectOutputStream {
    public AppendingObjectOutputStream(OutputStream out) throws IOException {
      super(out);
    }
    @Override
    protected void writeStreamHeader() throws IOException {
      reset();
    }
}
