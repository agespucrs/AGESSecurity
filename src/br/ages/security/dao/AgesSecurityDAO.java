package br.ages.security.dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.ages.security.infrastructure.ConnectionUtil;
import br.ages.security.interfaces.dao.IAgesSecurityDAO;
import br.ages.security.interfaces.models.IAgesSecurityUser;
import br.ages.security.models.AgesSecurityUser;

public class AgesSecurityDAO implements IAgesSecurityDAO {

	@Override
	public IAgesSecurityUser getUserByUsernameAndPassword(String username, String password) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException {
		AgesSecurityUser user = null;
				
		Connection connection = ConnectionUtil.getConnection();
		
		StringBuilder sql = new StringBuilder();
		sql.append("select * from ages_security_user where username = ? and password = ?");

		PreparedStatement statement = connection.prepareStatement(sql.toString());
		statement.setString(1, username);

		//TODO - voltar com a criptografia após a método de criação do usuário 
		// statement.setString(2, encrytpPassword(password));
		statement.setString(2, password);

		ResultSet resultset = statement.executeQuery();
		if (resultset.next()) {
			user = new AgesSecurityUser();
			user.setUsername(resultset.getString("USERNAME"));
		}
		
		return user;
	}
	
	public boolean deleteUserbyId(Integer idUsuario) throws ClassNotFoundException, SQLException{
		
		Connection connection = ConnectionUtil.getConnection();
		
		boolean isSucceed = false;
			
		java.sql.Statement st = connection.createStatement();
		StringBuilder sql = new StringBuilder();
		sql.append("delete * from ages_security_user where id = "+ idUsuario );
		isSucceed= true;
			
		return isSucceed;
	}
	
	private String encrytpPassword(String passwordToHash) throws NoSuchAlgorithmException {
        // Create MessageDigest instance for MD5
        MessageDigest md = MessageDigest.getInstance("MD5");
        
        //Add password bytes to digest
        md.update(passwordToHash.getBytes());
        
        //Get the hash's bytes 
        byte[] bytes = md.digest();
        
        //This bytes[] has bytes in decimal format;
        //Convert it to hexadecimal format
        StringBuilder sb = new StringBuilder();
        for(int i=0; i< bytes.length ;i++)
        {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        
        //Get complete hashed password in hex format
        return sb.toString();
	}
}
