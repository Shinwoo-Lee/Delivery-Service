import java.sql.*;
import java.util.Scanner;

class Database{
    Connection con;

    Database(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/delivery_service","testuser","testpw");
            this.con=con;
        }catch(Exception e){ System.out.println(e);}
    }

    public void update(String o_id, String state){
        try {
            PreparedStatement uStmt = this.con.prepareStatement("update Orders set state=? where id=?");
            uStmt.setString(1,state);
            uStmt.setString(2,o_id);
            uStmt.executeUpdate();
            int rows = uStmt.executeUpdate();
            if(rows==0) {System.out.println("<No rows Affected>");} else {System.out.println("<Order "+o_id+" is set to "+state+ ">");}
            System.out.println();
        } catch (SQLException e) { System.out.println(e);}
    }

    public void orderlist(String s_id){
        try {
            PreparedStatement olStmt = this.con.prepareStatement("select * from Orders where shop_id=?");
            olStmt.setString(1,s_id);
            ResultSet rs=olStmt.executeQuery();
            System.out.println("<Order List>");
            if (rs.next()) {
                do{
                    System.out.println(rs.getString(1)+"\t"+rs.getString(2)+"\t"+rs.getString(4)+"\t"+rs.getString(5)+"\t"+rs.getString(6)+"\t");
                } while(rs.next());
            } else {System.out.println("<No current order at your Shop>");}
            System.out.println();
        } catch (SQLException e) { System.out.println(e);}
    }

    public void groupBy(){
        try {
            Statement stmt = con.createStatement();
            ResultSet rs=stmt.executeQuery("select area, avg(delivery_fee) as average_fee from shop group by area");
            System.out.println("<Average Delivery Fee For Each Area>");
            while (rs.next()) {
                System.out.println("Area "+rs.getString(1) + ": " + rs.getInt(2));
            }
            System.out.println();
        } catch (SQLException e) { System.out.println(e);}
    }

    public void findByMembership(String s_id, String rank){

        try {
            PreparedStatement Stmt = this.con.prepareStatement("select customer.id,customer.name " +
                    "from order_info join customer on customer.id= order_info.C_id\n" +
                    "where order_info.S_id=? and customer.membership=?");
            Stmt.setString(1,s_id);
            Stmt.setString(2,rank);
            ResultSet rs = Stmt.executeQuery();
            if (rs.next()) {
                System.out.println("<Customer with membership "+rank+">");
                System.out.println("Customer ID: "+rs.getString(1) + "\tName: " + rs.getString(2));
                rs.close();
            } else {System.out.println("<No Customer with membership " + rank + ">");}
            System.out.println();
        } catch (SQLException e) { System.out.println(e);}
    }

    public void insertNewMenu(String s_id, String new_mid, String new_name, String new_price, String new_category){
        try {
            PreparedStatement pStmtForinsert = con.prepareStatement("INSERT INTO Menu VALUES (?, ?, ?, ?, ?)");
            pStmtForinsert.setString(1, new_mid);
            pStmtForinsert.setString(2, new_name);
            pStmtForinsert.setString(3, s_id);
            pStmtForinsert.setString(4, new_price);
            pStmtForinsert.setString(5, new_category);
            pStmtForinsert.executeUpdate();
            pStmtForinsert.close();
            System.out.println("<Insertion Complete>");
            System.out.println();
        } catch (SQLException e) {System.out.println(e);}
    }

    public void deleteMenu(String target_shop, String target_menu){
        try {
            PreparedStatement pStmtFordelete = con.prepareStatement("DELETE FROM Menu WHERE shop_id=? and id=?");
            pStmtFordelete.setString(1, target_shop);
            pStmtFordelete.setString(2, target_menu);
            int rows = pStmtFordelete.executeUpdate();
            if(rows==0) {System.out.println("<No rows Affected>");} else {System.out.println("<Deletion Complete>");}
            pStmtFordelete.close();
            System.out.println();
        } catch (SQLException e) {System.out.println(e);}
    }

    public void searchRider(String s_id){
        try {
            PreparedStatement pStmtForSearch = con.prepareStatement(
                    "SELECT DISTINCT r_id, r_name, r_tel "
                            + "FROM Not_in_delivery INNER JOIN Shop "
                            + "ON Not_in_delivery.r_area = Shop.area "
                            + "WHERE Shop.id = ?");
            pStmtForSearch.setString(1, s_id);
            ResultSet RiderList = pStmtForSearch.executeQuery();

            if (RiderList.next()) {
                System.out.println("<Current available riders in your area>");
                System.out.println(RiderList.getString(1) + " \t "
                        + RiderList.getString(2) + " \t "
                        + RiderList.getString(3));
                RiderList.close();
            } else {
                System.out.println("<No rider available in your area>");
            }
            System.out.println();
        } catch (SQLException e) {System.out.println(e);}
    }

    public void allMenu(String target_shopid) {
        try {
            String select_to_delete = "SELECT * FROM Menu WHERE shop_id = ?";
            PreparedStatement pstmt_tmp1 = con.prepareStatement(select_to_delete);
            pstmt_tmp1.setString(1, target_shopid);
            ResultSet rs = pstmt_tmp1.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getString(1) + " \t "
                        + rs.getString(2) + " \t "
                        + rs.getString(3) + " \t "
                        + rs.getString(4) + " \t "
                        + rs.getString(5));
            } rs.close();
            pstmt_tmp1.close();
        } catch (SQLException e) {System.out.println(e);}
    }

    public void close(){
        try {
            this.con.close();
        } catch (SQLException e) {System.out.println(e);}
    }
}

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Database db = new Database();

        int menu=0;
        String s_id;

        System.out.print("Enter your shop id: ");
        s_id=sc.nextLine();

        while(true){
            //print menu
            System.out.println("<USER MENU>");
            System.out.println("(1) Add Menu");
            System.out.println("(2) Delete Menu");
            System.out.println("(3) Change Order State");
            System.out.println("(4) Show Average delivery fee");
            System.out.println("(5) Find Rider");
            System.out.println("(6) Find Client By Membership Rank");
            System.out.println("(0) Exit");
            System.out.print("Enter the number of the menu: ");
            menu=sc.nextInt();

            if(menu==0) break;

            //switch for menu selection
            switch(menu){
                //Insert
                case 1:
                    db.allMenu(s_id);
                    System.out.print("Enter New Menu's ID: ");
                    String new_mid=sc.next();
                    System.out.print("Enter New Menu's name: ");
                    String new_name=sc.next();
                    System.out.print("Enter New Menu's  price: ");
                    String new_price=sc.next();
                    System.out.print("Enter New Menu's  category: ");
                    String new_category=sc.next();
                    db.insertNewMenu(s_id, new_mid, new_name, new_price, new_category); break;
                //Delete
                case 2:
                    db.allMenu(s_id);
                    String target_menu;
                    System.out.print("Enter deletion target menu id: ");
                    target_menu=sc.next(); sc.nextLine();
                    db.deleteMenu(s_id, target_menu); break;
                //Update
                case 3:
                    db.orderlist(s_id);
                    String o_id,state;
                    System.out.print("Enter Order id: ");
                    o_id=sc.next(); sc.nextLine();
                    System.out.print("Enter state(Cooking/In delivery/Complete): ");
                    state=sc.nextLine();
                    db.update(o_id,state); break;
                //Select group by
                case 4: db.groupBy(); break;
                //Select view1
                case 5:
                    db.searchRider(s_id); break;
                //Select view2
                case 6:
                    String rank;
                    System.out.print("Enter membership rank(S/A/B/C): ");
                    rank=sc.next(); sc.nextLine();
                    db.findByMembership(s_id,rank); break;

                default: System.out.println("Enter Valid Number\n");
            }
        }

        db.close();

    }
}