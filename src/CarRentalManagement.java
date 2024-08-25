import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class CarRentalManagement {
    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        String dburl = "jdbc:mysql://localhost:3306/car_rental";
        String dbuser = "root";
        String dbpass = "";
        String driver = "com.mysql.cj.jdbc.Driver";
        Class.forName(driver);
        Connection con = DriverManager.getConnection(dburl, dbuser, dbpass);
        if (con != null) {
            System.out.println("\t\t\t\t\t\u001B[95m=======================================\u001b[0m");
            System.out.println("\t\t\t\t\t           \u001B[32m\u001B[3mCar Rental System\u001B[0m\u001b[0m ");
            System.out.println("\t\t\t\t\t\u001B[95m=======================================\u001b[0m");
            /*
             * String reset = "\u001B[0m";
             * String red = "\u001B[31m";
             * String green = "\u001B[32m";
             * String yellow = "\u001B[33m";
             * String blue = "\u001B[34m";
             * String purple = "\u001B[35m";
             * String cyan = "\u001B[36m";
             * String white = "\u001B[37m";
             */
        } else {
            System.out.println("Connection Failed...");
        }
        boolean userchoice = true;
        while (userchoice) {
            Login l = new Login();
            System.out.println("\n[1] Admin \t[2] Customer\t[3]Exit");
            System.out.print("Enter Choice : ");
            int ch = sc.nextInt();
            switch (ch) {
                case 1:
                    boolean b = true;
                    while (b) {
                        System.out.print("Enter username : ");
                        String username = sc.next();
                        System.out.print("Enter Password : ");
                        String password = sc.next();
                        if (username.equals("admin123") && password.equals("Admin@123")) {
                            b = false;
                        } else {
                            System.out.println("Wrong password or username!!!\nTry Again...");
                        }
                    }
                    boolean b2 = true;
                    while (b2) {
                        System.out.println("\n╔════════════════════════════════════╗");
                        System.out.println("║    Car Rental Management System    ║");
                        System.out.println("╠════════════════════════════════════╣");
                        System.out.println("║  [1] List of Available Cars        ║");
                        System.out.println("║  [2] Add a New Car                 ║");
                        System.out.println("║  [3] Remove a Car                  ║");
                        System.out.println("║  [4] Rented Cars                   ║");
                        System.out.println("║  [5] Search for a Car              ║");
                        System.out.println("║  [6] Modify Rental Price           ║");
                        System.out.println("║  [7] Exit                          ║");
                        System.out.println("╚════════════════════════════════════╝");
                        int ch2 = sc.nextInt();
                        switch (ch2) {
                            case 1:

                                String sql01 = "SELECT * from cars";
                                java.sql.Statement st = con.createStatement();
                                ResultSet rs01 = st.executeQuery(sql01);
                                System.out.println();
                                int i = 0;
                                while (rs01.next()) {
                                    i++;
                                    System.out.println("\nCar Details [" + i + "] :");
                                    System.out.println("Make: " + rs01.getString("make"));
                                    System.out.println("Model: " + rs01.getString("model"));
                                    System.out.println("Year: " + rs01.getInt("year"));
                                    System.out.println("Colour: " + rs01.getString("color"));
                                    System.out.println("Rent : " + rs01.getDouble("rental_price"));
                                    System.out
                                            .println("Available: " + (rs01.getBoolean("availability") ? "Yes" : "No"));
                                }
                                break;

                            case 2:
                                String sql1 = "Insert into cars(car_no,make,model,year,rental_price,color,availability) values(?,?,?,?,?,?,?)";
                                PreparedStatement statement = con.prepareStatement(sql1);
                                int year;
                                System.out.print("Enter Car Number : ");
                                String car_no = sc.next();
                                System.out.print("Enter Car Make : ");
                                String make = sc.next();
                                System.out.print("Enter Car Model : ");
                                String model = sc.next();
                                boolean bo;
                                bo = false;
                                do {
                                    System.out.print("Enter Year : ");
                                    year = sc.nextInt();
                                    if (year > 2023) {
                                        System.out.println("Enter Valid Year..");
                                        System.out.print("Enter Year : ");
                                        year = sc.nextInt();
                                    } else {
                                        bo = false;
                                    }
                                } while (bo);
                                System.out.print("Enter Rental Price : ");
                                double rental_price = sc.nextDouble();
                                System.out.print("Enter Car Colour : ");
                                String color = sc.next();
                                boolean availability = true;

                                statement.setString(1, car_no);
                                statement.setString(2, make);
                                statement.setString(3, model);
                                statement.setInt(4, year);
                                statement.setDouble(5, rental_price);
                                statement.setString(6, color);
                                statement.setBoolean(7, availability);

                                int updated = statement.executeUpdate();
                                if (updated > 0) {
                                    System.out.println("Updated Successfully....");
                                }
                                break;
                            case 3:
                                String sql2 = "Delete from cars where car_no = ?";
                                PreparedStatement statement2 = con.prepareStatement(sql2);

                                System.out.println("Enter Car Number : ");
                                String delete_car_no = sc.next();
                                statement2.setString(1, delete_car_no);
                                int deleted = statement2.executeUpdate();
                                if (deleted > 0) {
                                    System.out.println("Deleted Successfully....");
                                }
                                break;
                            case 4:
                                System.out.println("\n======== Cars on Rent ========");
                                String sql3 = "SELECT * from cars where availability = 0";
                                java.sql.Statement st11 = con.createStatement();
                                ResultSet rs = st11.executeQuery(sql3);
                                System.out.println();
                                int i11 = 0;
                                while (rs.next()) {
                                    i11++;
                                    System.out.println("\nCar Details [" + i11 + "] :");
                                    System.out.println("Make: " + rs.getString("make"));
                                    System.out.println("Model: " + rs.getString("model"));
                                    System.out.println("Year: " + rs.getInt("year"));
                                    System.out.println("Colour: " + rs.getString("color"));
                                    System.out.println("Rent : " + rs.getDouble("rental_price"));
                                    System.out.println("Available: " + (rs.getBoolean("availability") ? "Yes" : "No"));
                                }
                                break;
                            case 5:
                                System.out.println("\n╭───────────────────────────╮");
                                System.out.println("│   Sort Cars by Criteria   │");
                                System.out.println("├───────────────────────────┤");
                                System.out.println("│ [1] By Make               │");
                                System.out.println("│ [2] By Model              │");
                                System.out.println("│ [3] By Year               │");
                                System.out.println("│ [4] By Car Number         │");
                                System.out.println("│ [5] By Colour             │");
                                System.out.println("╰───────────────────────────╯");
                                int ch3 = sc.nextInt();
                                switch (ch3) {
                                    case 1:
                                        String sql51 = "Select * from cars where make = ?";
                                        PreparedStatement ps1 = con.prepareStatement(sql51);
                                        System.out.print("Enter Make : ");
                                        String input1 = sc.next();
                                        ps1.setString(1, input1);
                                        ResultSet rs51 = ps1.executeQuery();
                                        int i1 = 0;
                                        while (rs51.next()) {
                                            i1++;
                                            System.out.println("\nCar Details [" + i1 + "] :");
                                            System.out.println("Make: " + rs51.getString("make"));
                                            System.out.println("Model: " + rs51.getString("model"));
                                            System.out.println("Year: " + rs51.getInt("year"));
                                            System.out.println("Colour: " + rs51.getString("color"));
                                            System.out.println("Rent : " + rs51.getDouble("rental_price"));
                                            System.out.println(
                                                    "Available: " + (rs51.getBoolean("availability") ? "Yes" : "No"));
                                        }
                                        break;
                                    case 2:
                                        String sql52 = "Select * from cars where model = ?";
                                        PreparedStatement ps2 = con.prepareStatement(sql52);
                                        System.out.print("Enter Model : ");
                                        String input2 = sc.next();
                                        ps2.setString(1, input2);
                                        ResultSet rs52 = ps2.executeQuery();
                                        int i2 = 0;
                                        while (rs52.next()) {
                                            i2++;
                                            System.out.println("\nCar Details [" + i2 + "] :");
                                            System.out.println("Make: " + rs52.getString("make"));
                                            System.out.println("Model: " + rs52.getString("model"));
                                            System.out.println("Year: " + rs52.getInt("year"));
                                            System.out.println("Colour: " + rs52.getString("color"));
                                            System.out.println("Rent : " + rs52.getDouble("rental_price"));
                                            System.out.println(
                                                    "Available: " + (rs52.getBoolean("availability") ? "Yes" : "No"));
                                        }
                                        break;
                                    case 3:
                                        String sql53 = "Select * from cars where year = ?";
                                        PreparedStatement ps3 = con.prepareStatement(sql53);
                                        System.out.print("Enter Year : ");
                                        String input3 = sc.next();
                                        ps3.setString(1, input3);
                                        ResultSet rs53 = ps3.executeQuery();
                                        int i3 = 0;
                                        while (rs53.next()) {
                                            i3++;
                                            System.out.println("\nCar Details [" + i3 + "] :");
                                            System.out.println("Make: " + rs53.getString("make"));
                                            System.out.println("Model: " + rs53.getString("model"));
                                            System.out.println("Year: " + rs53.getInt("year"));
                                            System.out.println("Colour: " + rs53.getString("color"));
                                            System.out.println("Rent : " + rs53.getDouble("rental_price"));
                                            System.out.println(
                                                    "Available: " + (rs53.getBoolean("availability") ? "Yes" : "No"));
                                        }
                                        break;
                                    case 4:
                                        String sql54 = "Select * from cars where car_no = ?";
                                        PreparedStatement ps4 = con.prepareStatement(sql54);
                                        System.out.print("Enter Car Number : ");
                                        String input4 = sc.next();
                                        ps4.setString(1, input4);
                                        ResultSet rs54 = ps4.executeQuery();
                                        int i4 = 0;
                                        while (rs54.next()) {
                                            i4++;
                                            System.out.println("\nCar Details [" + i4 + "] :");
                                            System.out.println("Make: " + rs54.getString("make"));
                                            System.out.println("Model: " + rs54.getString("model"));
                                            System.out.println("Year: " + rs54.getInt("year"));
                                            System.out.println("Colour: " + rs54.getString("color"));
                                            System.out.println("Rent : " + rs54.getDouble("rental_price"));
                                            System.out.println(
                                                    "Available: " + (rs54.getBoolean("availability") ? "Yes" : "No"));
                                        }
                                        break;
                                    case 5:
                                        String sql55 = "Select * from cars where color = ?";
                                        PreparedStatement ps5 = con.prepareStatement(sql55);
                                        System.out.print("Enter Colour : ");
                                        String input5 = sc.next();
                                        ps5.setString(1, input5);
                                        ResultSet rs55 = ps5.executeQuery();
                                        int i5 = 0;
                                        while (rs55.next()) {
                                            i5++;
                                            System.out.println("\nCar Details [" + i5 + "] :");
                                            System.out.println("Make: " + rs55.getString("make"));
                                            System.out.println("Model: " + rs55.getString("model"));
                                            System.out.println("Year: " + rs55.getInt("year"));
                                            System.out.println("Colour: " + rs55.getString("color"));
                                            System.out.println("Rent : " + rs55.getDouble("rental_price"));
                                            System.out.println(
                                                    "Available: " + (rs55.getBoolean("availability") ? "Yes" : "No"));
                                        }
                                        break;
                                    default:
                                        System.out.println("Enter valid choice...");
                                        break;
                                }
                                break;
                            case 6:
                                String sql6 = "Update cars set rental_price = ? where car_no = ?";
                                PreparedStatement ps6 = con.prepareStatement(sql6);
                                System.out.print("Enter Car Number : ");
                                String car_no_update = sc.next();
                                System.out.print("Enter Updated Price : ");
                                double rental_updated = sc.nextDouble();
                                ps6.setString(2, car_no_update);
                                ps6.setDouble(1, rental_updated);
                                int update6 = ps6.executeUpdate();
                                if (update6 > 0) {
                                    System.out.println("Updated Successfully...");
                                }
                                break;
                            case 7:
                                System.out.println("Exiting...");
                                b2 = false;
                                break;
                            default:
                                System.out.println("Choose Valid Option...");
                                break;
                        }
                    }
                    break;
                case 2:
                    boolean b1 = true;
                    while (b1) {
                        System.out.println("\n╔══════════════════════════════════════╗");
                        System.out.println("║      Welcome to Your Car Rental      ║");
                        System.out.println("╟──────────────────────────────────────╢");
                        System.out.println("║      [1] Login       [2] Signup      ║");
                        System.out.println("╚══════════════════════════════════════╝");
                        System.out.print("Enter Choice : ");
                        int ch1 = sc.nextInt();
                        switch (ch1) {
                            case 1:
                                System.out.println("\n========== Login ==========");
                                l.login();
                                b1 = false;
                                break;
                            case 2:
                                System.out.println("\n========== Sign Up ==========");
                                l.signUp();
                                b1 = false;
                                break;
                            default:
                                System.out.println("Enter Valid Choice!!!");
                                break;
                        }
                    }
                    boolean flag = true;
                    // DBTablePrinter.printTable(con, "cars",50,10);
                    while (flag) {
                        System.out.println("\n╔════════════════════════════════╗");
                        System.out.println("║      Car Rental Main Menu      ║");
                        System.out.println("╠════════════════════════════════╣");
                        System.out.println("║ [1] Filter Car                 ║");
                        System.out.println("║ [2] Sort by                    ║");
                        System.out.println("║ [3] Rent Car                   ║");
                        System.out.println("║ [4] Exit                       ║");
                        System.out.println("╚════════════════════════════════╝");
                        System.out.print("Enter Choice : ");
                        int ch4 = sc.nextInt();
                        switch (ch4) {
                            case 1:
                                System.out.println("\n╭────────────────────────────────────╮");
                                System.out.println("│     Filter Car Search Criteria     │");
                                System.out.println("├────────────────────────────────────┤");
                                System.out.println("│   [1] By Make                      │");
                                System.out.println("│   [2] By Model                     │");
                                System.out.println("│   [3] By Year                      │");
                                System.out.println("│   [4] By Car Number                │");
                                System.out.println("│   [5] By Colour                    │");
                                System.out.println("╰────────────────────────────────────╯");
                                int ch3 = sc.nextInt();
                                switch (ch3) {
                                    case 1:
                                        String sql51 = "Select * from cars where make=?";
                                        PreparedStatement ps1 = con.prepareStatement(sql51);
                                        System.out.print("Enter Make : ");
                                        String input1 = sc.next();
                                        ps1.setString(1, input1);
                                        ResultSet rs51 = ps1.executeQuery();
                                        int i1s = 0;
                                        while (rs51.next()) {
                                            i1s++;
                                            System.out.println("\nCar Details [" + i1s + "] :");
                                            System.out.println("Make: " + rs51.getString("make"));
                                            System.out.println("Model: " + rs51.getString("model"));
                                            System.out.println("Year: " + rs51.getInt("year"));
                                            System.out.println("Colour: " + rs51.getString("color"));
                                            System.out.println("Rent : " + rs51.getDouble("rental_price"));
                                            System.out.println(
                                                    "Available: " + (rs51.getBoolean("availability") ? "Yes" : "No"));
                                        }
                                        break;
                                    case 2:
                                        String sql52 = "Select * from cars where model= ?";
                                        PreparedStatement ps2 = con.prepareStatement(sql52);
                                        System.out.print("Enter Model : ");
                                        String input2 = sc.next();
                                        ps2.setString(1, input2);
                                        ResultSet rs52 = ps2.executeQuery();
                                        int i2 = 0;
                                        while (rs52.next()) {
                                            i2++;
                                            System.out.println("\nCar Details [" + i2 + "] :");
                                            System.out.println("Make: " + rs52.getString("make"));
                                            System.out.println("Model: " + rs52.getString("model"));
                                            System.out.println("Year: " + rs52.getInt("year"));
                                            System.out.println("Colour: " + rs52.getString("color"));
                                            System.out.println("Rent : " + rs52.getDouble("rental_price"));
                                            System.out.println(
                                                    "Available: " + (rs52.getBoolean("availability") ? "Yes" : "No"));
                                        }
                                        break;
                                    case 3:
                                        String sql53 = "Select * from cars where year = ?";
                                        PreparedStatement ps3 = con.prepareStatement(sql53);
                                        System.out.print("Enter Year : ");
                                        String input3 = sc.next();
                                        ps3.setString(1, input3);
                                        ResultSet rs53 = ps3.executeQuery();
                                        int i3 = 0;
                                        while (rs53.next()) {
                                            i3++;
                                            System.out.println("\nCar Details [" + i3 + "] :");
                                            System.out.println("Make: " + rs53.getString("make"));
                                            System.out.println("Model: " + rs53.getString("model"));
                                            System.out.println("Year: " + rs53.getInt("year"));
                                            System.out.println("Colour: " + rs53.getString("color"));
                                            System.out.println("Rent : " + rs53.getDouble("rental_price"));
                                            System.out.println(
                                                    "Available: " + (rs53.getBoolean("availability") ? "Yes" : "No"));
                                        }
                                        break;
                                    case 4:
                                        String sql54 = "Select * from cars where car_no = ?";
                                        PreparedStatement ps4 = con.prepareStatement(sql54);
                                        System.out.print("Enter Car Number : ");
                                        String input4 = sc.next();
                                        ps4.setString(1, input4);
                                        ResultSet rs54 = ps4.executeQuery();
                                        int i4 = 0;
                                        while (rs54.next()) {
                                            i4++;
                                            System.out.println("\nCar Details [" + i4 + "] :");
                                            System.out.println("Make: " + rs54.getString("make"));
                                            System.out.println("Model: " + rs54.getString("model"));
                                            System.out.println("Year: " + rs54.getInt("year"));
                                            System.out.println("Colour: " + rs54.getString("color"));
                                            System.out.println("Rent : " + rs54.getDouble("rental_price"));
                                            System.out.println(
                                                    "Available: " + (rs54.getBoolean("availability") ? "Yes" : "No"));
                                        }
                                        break;
                                    case 5:
                                        String sql55 = "Select * from cars where color = ?";
                                        PreparedStatement ps5 = con.prepareStatement(sql55);
                                        System.out.print("Enter Colour : ");
                                        String input5 = sc.next();
                                        ps5.setString(1, input5);
                                        ResultSet rs55 = ps5.executeQuery();
                                        int i5 = 0;
                                        while (rs55.next()) {
                                            i5++;
                                            System.out.println("\nCar Details [" + i5 + "] :");
                                            System.out.println("Make: " + rs55.getString("make"));
                                            System.out.println("Model: " + rs55.getString("model"));
                                            System.out.println("Year: " + rs55.getInt("year"));
                                            System.out.println("Colour: " + rs55.getString("color"));
                                            System.out.println("Rent : " + rs55.getDouble("rental_price"));
                                            System.out.println(
                                                    "Available: " + (rs55.getBoolean("availability") ? "Yes" : "No"));
                                        }
                                        break;
                                    default:
                                        System.out.println("Enter valid choice...");
                                        break;
                                }
                                break;
                            case 2:
                                System.out.println("\n╭───────────────────────────╮");
                                System.out.println("│      Choose Sorting:      │");
                                System.out.println("├───────────────────────────┤");
                                System.out.println("│   [1] Price Low To High   │");
                                System.out.println("│   [2] Price High To Low   │");
                                System.out.println("╰───────────────────────────╯");
                                System.out.print("Enter Choice : ");
                                int ch5 = sc.nextInt();
                                switch (ch5) {
                                    case 1:
                                        String sql2 = "select * from cars order by rental_price asc";
                                        PreparedStatement pst = con.prepareStatement(sql2);
                                        ResultSet rs = pst.executeQuery();
                                        int i = 0;
                                        while (rs.next()) {
                                            if (rs.getBoolean("availability")) {
                                                i++;
                                                System.out.println("\u001B[34m");
                                                System.out.println("Car Details [" + i + "] :");
                                                // System.out.println("Sr_No: "+rs.getInt("sr_no"));
                                                System.out.println("Make: " + rs.getString("make"));
                                                System.out.println("Model: " + rs.getString("model"));
                                                System.out.println("Year: " + rs.getInt("year"));
                                                System.out.println("Colour: " + rs.getString("color"));
                                                System.out.println("Rent : " + rs.getDouble("rental_price"));
                                                System.out.println("\u001B[0m");
                                            }
                                        }
                                        break;
                                    case 2:
                                        String sql22 = "select * from cars order by rental_price desc";
                                        PreparedStatement pst2 = con.prepareStatement(sql22);
                                        ResultSet rs2 = pst2.executeQuery();
                                        int i2 = 0;
                                        while (rs2.next()) {
                                            if (rs2.getBoolean("availability")) {
                                                i2++;
                                                System.out.println("\u001B[34m");
                                                System.out.println("Car Details [" + i2 + "] :");
                                                System.out.println("Make: " + rs2.getString("make"));
                                                System.out.println("Model: " + rs2.getString("model"));
                                                System.out.println("Year: " + rs2.getInt("year"));
                                                System.out.println("Colour: " + rs2.getString("color"));
                                                System.out.println("Rent : " + rs2.getDouble("rental_price"));
                                                System.out.println("\u001B[0m");
                                            }
                                        }
                                        break;

                                    default:
                                        break;
                                }
                                break;
                            case 3:
                                String sql58 = "select * from cars";
                                PreparedStatement pstt = con.prepareStatement(sql58);
                                ResultSet rsr = pstt.executeQuery();
                                while (rsr.next()) {
                                    if (rsr.getBoolean("availability")) {
                                        System.out.println("car_no : " + rsr.getString("car_no"));
                                        System.out.println("Make: " + rsr.getString("make"));
                                        System.out.println("Model: " + rsr.getString("model"));
                                        System.out.println("Year: " + rsr.getInt("year"));
                                        System.out.println("Colour: " + rsr.getString("color"));
                                        System.out.println("Rent : " + rsr.getDouble("rental_price"));
                                    }
                                }

                                String sql5s = "select * from cars where car_no=?";
                                PreparedStatement psst = con.prepareStatement(sql5s);
                                System.out.println("enter car no");
                                String carno = sc.next();
                                psst.setString(1, carno);
                                ResultSet rrss = psst.executeQuery();
                                double price = 0;
                                while (rrss.next()) {
                                    price = rrss.getDouble("rental_price");
                                }

                                String sql16 = "insert into customer(name,car_no,email,rent) values(?,?,?,?)";
                                PreparedStatement psst2 = con.prepareStatement(sql16);
                                System.out.println("enter name");
                                String name = sc.next();
                                psst2.setString(1, name);
                                psst2.setString(2, carno);
                                System.out.println("email");
                                String email = sc.next();
                                psst2.setString(3, email);
                                psst2.setDouble(4, price);
                                psst2.executeUpdate();

                                String sql17 = "update cars set availability=" + false;
                                PreparedStatement psst3 = con.prepareStatement(sql17);
                                psst3.executeUpdate();

                                break;

                            case 4:
                                System.out.println("Exiting...");
                                flag = false;
                                break;
                            default:
                                System.out.println("Enter Valid Input...");
                                break;
                        }
                    }
                    break;
                default:
                    System.out.println("Restart Again...");
                    userchoice = false;
                    break;
            }
        }
        sc.close();
    }
}

class Login {
    String dburl, dbuser, dbpass, driver;
    Connection con;
    Scanner sc = new Scanner(System.in);

    public Login() throws Exception {
        dburl = "jdbc:mysql://localhost:3306/car_rental";
        dbuser = "root";
        dbpass = "";
        driver = "com.mysql.cj.jdbc.Driver";
        Class.forName(driver);
        con = DriverManager.getConnection(dburl, dbuser, dbpass);
    }

    public void login() throws Exception {
        // Prepare a SELECT statement to query the database for the user name.
        String sql = "SELECT * FROM login WHERE username= ? and email_id= ? and password= ?";
        PreparedStatement statement = con.prepareStatement(sql);

        // Set the parameter for the user name.
        System.out.print("Enter Username : ");
        String username = sc.next();
        System.out.print("Enter email : ");
        String email = sc.next();
        System.out.print("Enter password : ");
        String password = sc.next();

        statement.setString(1, username);
        statement.setString(2, email);
        statement.setString(3, password);

        // Execute the SELECT statement and get the result set.
        ResultSet resultSet = statement.executeQuery();

        // Check if the result set is empty. If it is, then the user name does not exist
        // in the database. Otherwise, the user name does exist in the database.
        if (resultSet.next()) {
            System.out.println("Login Successfully...");
        } else {
            System.out.println("\nThe user name \u001B[31m" + username + "\u001b[0m does not exist.");
            boolean b = true;
            while (b) {
                System.out.println("[1] Login\t[2] Sign Up");
                int ch = sc.nextInt();
                if (ch == 1) {
                    login();
                    b = false;
                } else if (ch == 2) {
                    signUp();
                    b = false;
                } else {
                    System.out.println("Please enter valid choice!!!\nTry Again...");
                }
            }
        }

        // Close the connection to the database.
        con.close();
    }

    public void signUp() throws Exception {
        String sql = "Insert into login(username,email_id,password) values(?,?,?)";
        PreparedStatement statement = con.prepareStatement(sql);

        System.out.print("Enter Username : ");
        String username = sc.next();
        System.out.print("Enter email : ");
        String email = sc.next();
        boolean b = true;
        while (b) {
            System.out.print("Enter password : ");
            String password = sc.next();
            if (isStrongPassword(password)) {
                statement.setString(1, username);
                statement.setString(2, email);
                statement.setString(3, password);
                int update = statement.executeUpdate();
                if (update > 0) {
                    System.out.println("Account Created Successfully...");
                } else {
                    System.out.println("Account Already Exists...");
                    login();
                }
                con.close();
                b = false;
            } else {
                System.out.println("Please Enter Strong Password...");
            }
        }
    }

    public static boolean isStrongPassword(String password) {
        // At least 8 characters, at least one uppercase letter, one lowercase letter,
        // one digit, and one special character
        String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        Pattern pattern = Pattern.compile(passwordPattern);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}