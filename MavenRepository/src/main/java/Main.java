import java.util.ArrayList;

import javax.management.relation.Role;

import com.revature.dao.UserDao;
import com.revature.models.User;
import com.revature.models.UserRole;

public class Main {

	public static void main(String[] args) {
		
		UserDao uDao = new UserDao(); 
		
		UserRole role = uDao.getRole(1);
		
		ArrayList<User> users = new ArrayList<User>();
		
		//uDao.insertElement(new User(1,"Virut","password","Gonzalo","Navarrete","gon300@gmail.com",role));
		
		users = (ArrayList<User>) uDao.getAllElements();
		
		for(User u : users) {
			System.out.println(u.toString());
			System.out.print(u.getUserRole().toString());
			
		}
			
	}

}
