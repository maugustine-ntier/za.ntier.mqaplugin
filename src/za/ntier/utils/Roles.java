package za.ntier.utils;

import java.util.List;

public class Roles {
	public static boolean checkRole(String roles,int role_ID) {		
		boolean roleFound = false;
		List<String> roleList = List.of(roles.split(",")); 
		for (String role:roleList) {
			String roleID = role_ID + "";
			if (roleID.equals(role)) {
				roleFound = true;
			}
		}
		return roleFound;
	}
}
