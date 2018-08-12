import com.mysql.jdbc.Connection;
import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Text;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/* @author yogesh */
public class compiler extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet compiler</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet compiler at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(compiler.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            Connection conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/compilerproj?", "root", "");
            String code = request.getParameter("code");
            Path file1=Paths.get("allcsedhjbfsdkjfbode.txt");
            List<String> lines = Arrays.asList(code);
            try{
            Files.write(file1,lines, Charset.forName("UTF-8"));
            }catch(Exception e){
            System.out.println(e);
            }
            String input = request.getParameter("input");
            java.sql.PreparedStatement ps = conn.prepareStatement("insert into datavalue value (?,?)");
            ps.setString(1, code);
            ps.setString(2, input);
            int i = ps.executeUpdate();
            if(i<=0){
                out.println("Failed ");
            }
        } catch (SQLException ex) {
            Logger.getLogger(compiler.class.getName()).log(Level.SEVERE, null, ex);
        }
        String s=request.getParameter("code");
        Path file=Paths.get("thecode.cpp");
        String i=request.getParameter("input");
        Path file2=Paths.get("input.txt");
        List<String> lines = Arrays.asList(s);
        List<String> lines1 = Arrays.asList(i);
        try{
            Files.write(file,lines, Charset.forName("UTF-8"));
            Files.write(file2,lines1, Charset.forName("UTF-8"));
        }catch(Exception e){
           out.println(e);
        }
        try
        {
            String command="g++ -o program C:\\Users\\yogesh\\AppData\\Roaming\\NetBeans\\8.2\\config\\GF_4.1.1\\domain1\\config\\thecode.cpp\"";
            Process p=Runtime.getRuntime().exec(command);
            String command1="cmd /c start cmd.exe /K \"dir && g++ -o program C:\\Users\\yogesh\\AppData\\Roaming\\NetBeans\\8.2\\config\\GF_4.1.1\\domain1\\config\\thecode.cpp&C:\\Users\\yogesh\\AppData\\Roaming\\NetBeans\\8.2\\config\\GF_4.1.1\\domain1\\config\\program.exe>out.txt<input.txt\"";
            Process p1=Runtime.getRuntime().exec(command1);
            Thread.sleep(3000);
        }
        catch (Exception e)
        {
            System.out.println("HEY Buddy ! U r Doing Something Wrong ");
            e.printStackTrace();
        }

            File file1 = new File("C:\\Users\\yogesh\\AppData\\Roaming\\NetBeans\\8.2\\config\\GF_4.1.1\\domain1\\config\\out.txt");
            BufferedReader br = new BufferedReader(new FileReader(file1));
            String st;
            out.println("<html>");
            out.println("<body style=\"background: linear-gradient(rgb(0,0,0), rgb(185, 185, 185));\">");
            out.println("<div style='width:203px;height:30px;color:white;background:black;'>");out.println("Output");out.println("</div>");
            out.println("<div style='border:1px solid black;width:200px;height:150px;color:white'>");
            while ((st = br.readLine()) != null)
                out.println(st);
            out.println("</div");
            out.println("</body>");
            out.println("</html>");
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
