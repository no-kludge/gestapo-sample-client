package aspect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class PublicAspect {
	public static final String SERVICE_NAME = "Client";
	
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	Statement statement = null;
	static int seq = 0;
	static int invocationId = -1;
	
	@PostConstruct
	private void init() {
		try {
			System.out.println("in init");
			Class.forName("com.mysql.jdbc.Driver");
			
			connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3307/?user=root");
			
			preparedStatement = connection.prepareStatement("insert into seq_viz.data(invocation_id, seq_no, class, api, side, service) values (?,?,?,?,?,?)");
			statement = connection.createStatement(); 
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Before(value = "execution(public * *(..))", argNames = "joinPoint") 
	private void anyPublicOperation(JoinPoint jp) {
		System.out.println("from class: " + Thread.currentThread().getStackTrace()[15].getClassName().split("."));
		System.out.println("SourceLocation: " + jp.getStaticPart().getSignature().getDeclaringType().getSimpleName());
		
		String className = Thread.currentThread().getStackTrace()[15].getClassName();
		int index = className.lastIndexOf('.');//.split(".");
		String fromClass =  className.substring(index+1);
		
		String toClassName = jp.getTarget().getClass().getSimpleName();
		System.out.println("toClassName: " + toClassName);
		String[] signatureParts = jp.getSignature().toString().split(" ");
		
		String api = signatureParts[0] + " " + jp.getSignature().getName();
		
		if(preparedStatement == null) 
			init();
		
		 invocationId = invocationId==-1? getInvocationId() +1 : invocationId;
		
		 insertToDb(invocationId, ++seq, fromClass, api, "from");
		 insertToDb(invocationId, seq, toClassName, api, "to");	
	}
	
	private void insertToDb(int invocationId, int seq, String toClassName, String api, String side) {
		try {
			preparedStatement.setInt(1, invocationId);
			preparedStatement.setInt(2, seq);
			preparedStatement.setString(3, toClassName);
			preparedStatement.setString(4, api);
			preparedStatement.setString(5, side);
			preparedStatement.setString(6, SERVICE_NAME);
									
			preparedStatement.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	private int getInvocationId() {
		System.out.println("in invocation id");
		try {
			ResultSet rs = statement.executeQuery("select MAX(invocation_id) from seq_viz.data");
			
			rs.next();
			
			int invocation_id = rs.getInt(1);
			
			System.out.println("invocation id: " + invocation_id);
			return invocation_id;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;		
	}

	@PreDestroy
	private void destroy() {
		try {
			preparedStatement.close();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}