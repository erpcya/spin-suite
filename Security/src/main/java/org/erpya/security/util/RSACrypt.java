/*************************************************************************************
 * Product: Spin-Suite (Mobile Suite)                       		                 *
 * Copyright (C) 2012-2018 E.R.P. Consultores y Asociados, C.A.                      *
 * Contributor(s): Yamel Senih ysenih@erpya.com				  		                 *
 * This program is free software: you can redistribute it and/or modify              *
 * it under the terms of the GNU General Public License as published by              *
 * the Free Software Foundation, either version 3 of the License, or                 *
 * (at your option) any later version.                                               *
 * This program is distributed in the hope that it will be useful,                   *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of                    *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the                     *
 * GNU General Public License for more details.                                      *
 * You should have received a copy of the GNU General Public License                 *
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.            *
 ************************************************************************************/
package org.erpya.security.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.sql.Timestamp;
import java.util.logging.Level;

import javax.crypto.Cipher;

import android.content.Context;
import android.util.Base64;

import org.erpya.base.util.Env;
import org.erpya.base.util.LogM;

/**
 * 
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpya.com Aug 5, 2015, 12:47:41 AM
 *
 */
public class RSACrypt implements SecureInterface {
	
	/**	Context					*/
	private Context				context;
	/**	Public Key				*/
	private Key					publicKey = null;
	/**	String Key Saved		*/
	private String 				keySaved = null;
	/**	Cipher					*/
	private Cipher				cipher = null;
	/**	Algorithm				*/
	public static final String 	RSA = "RSA";
	/** Standard Key			*/
	public static final String	KEY_FOR_KEY = "#KEY_FOR_KEY";
	

	public RSACrypt(Context context) {
		this.context = context;
		initCipher(false);
	}
	
	/**
	 * Decode Text
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpya.com
	 * @param text
	 * @return
	 * @return String
	 */
	private String decodeText(String text) {
		if (text == null || text.length() == 0)
			return text;
		boolean isEncrypted = text.startsWith(ENCRYPTEDVALUE_START) && text.endsWith(ENCRYPTEDVALUE_END);
		if (isEncrypted)
			text = text.substring(ENCRYPTEDVALUE_START.length(), text.length()-ENCRYPTEDVALUE_END.length());
		//	Return
		return text;
	}
	
	/**
	 * Encode text
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpya.com
	 * @param text
	 * @return
	 * @return String
	 */
	private String encodeText(String text) {
		initCipher(false);
		//	Valid Key
		if(keySaved == null) {
			LogM.log(context, getClass().getName(), Level.WARNING, "Not Key for encrypte");
			return text;
		}
		//	
		byte[] encodedBytes = null;
		String value = text;
		if(value == null) {
			value = "";
		}
		//	Valid Encrypted
		boolean isEncrypted = isEncrypted(value);
		if(isEncrypted)
			return value;
		//	
		try {
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			encodedBytes = cipher.doFinal(text.getBytes());
			//	Return
			return ENCRYPTEDVALUE_START + Base64.encodeToString(encodedBytes, Base64.DEFAULT) + ENCRYPTEDVALUE_END;
		} catch (Exception e) {
			LogM.log(Env.getContext(), getClass().getName(), Level.SEVERE, "Error while Encode text");
		}
		//	Return
		return CLEARVALUE_START + value + CLEARVALUE_END;
	}
	
	/**
	 * Verify if is encrypted
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpya.com
	 * @param value
	 * @return
	 * @return boolean
	 */
	public static boolean isEncrypted(String value) {
		if(value == null)
			return false;
		return value.startsWith(ENCRYPTEDVALUE_START) && value.endsWith(ENCRYPTEDVALUE_END);
	}
	
	/**
	 * Generate Key
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpya.com
	 * @return void
	 */
	public void initCipher(boolean force) {
		//	
		if(cipher != null
				&& !force)
			return;
		//	Generate
		try {
			keySaved = Env.getContext(context, KEY_FOR_KEY);
			byte[] keyBytes = null;
			if(keySaved == null) {
				KeyPairGenerator kpg = KeyPairGenerator.getInstance(RSA);
				kpg.initialize(1024);
				KeyPair kp = kpg.genKeyPair();
				Key publicKey = kp.getPublic();
				keyBytes = publicKey.getEncoded();
				String keyForSave = Base64.encodeToString(keyBytes, Base64.DEFAULT);
				//	Set Key
				Env.setContext(context, KEY_FOR_KEY, keyForSave);
			} else {
				keyBytes = Base64.decode(keySaved, Base64.DEFAULT);
			}
			//	Decode
		    X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
		    KeyFactory keyFactory = KeyFactory.getInstance(RSA);
		    publicKey = keyFactory.generatePublic(spec);
			cipher = Cipher.getInstance(RSA);
		} catch (Exception e) {
			LogM.log(Env.getContext(), getClass().getName(), Level.SEVERE, "Error while generate RSA Key");
		}
	}
	
	/**
	 * Get Public Key from String
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpya.com
	 * @param key
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @return Key
	 */
	public static Key getPublicKeyFromStringEx(String key) throws NoSuchAlgorithmException, InvalidKeySpecException {
		//	Valid null
		if(key == null)
			return null;
		//	Bytes
		byte[] keyBytes = Base64.decode(key, Base64.DEFAULT);
		X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
	    KeyFactory keyFactory = KeyFactory.getInstance(RSA);
	    //	Return
	    return keyFactory.generatePublic(spec);
	}
	
	/**
	 * Get Key from String
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpya.com
	 * @param key
	 * @return
	 * @return Key
	 */
	public static Key getPublicKeyFromString(String key) {
		try {
			return getPublicKeyFromStringEx(key);
		} catch (NoSuchAlgorithmException e) {
			LogM.log(Env.getContext(), RSACrypt.class, Level.SEVERE, "Error while get RSA Key", e);
		} catch (InvalidKeySpecException e) {
			LogM.log(Env.getContext(), RSACrypt.class, Level.SEVERE, "Error while get RSA Key", e);
		}
		//	Default
		return null;
	}
	
	/**
	 * Generate Public Key with Exception
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpya.com
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @return Key
	 */
	public static Key generatePublicKeyEx() throws NoSuchAlgorithmException {
		KeyPairGenerator kpg = KeyPairGenerator.getInstance(RSA);
		kpg.initialize(1024);
		KeyPair kp = kpg.genKeyPair();
		return kp.getPublic();
	}
	
	/**
	 * Generate Public key, handle exception
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpya.com
	 * @return
	 * @return Key
	 */
	public static Key generatePublicKey() {
		try {
			return generatePublicKeyEx();
		} catch (NoSuchAlgorithmException e) {
			LogM.log(Env.getContext(), RSACrypt.class, Level.SEVERE, "Error while generate RSA Key", e);
		}
		//	Default
		return null;
	}

	@Override
	public String encrypt(String value) {
		return encodeText(value);
	}

	@Override
	public String decrypt(String value) {
		if (value == null)
			return null;
		//	
		return decodeText(value);
	}

	@Override
	public Integer encrypt(Integer value) {
		//	not yet implemented
		return null;
	}

	@Override
	public Integer decrypt(Integer value) {
		//	not yet implemented
		return null;
	}

	@Override
	public BigDecimal encrypt(BigDecimal value) {
		//	not yet implemented
		return null;
	}

	@Override
	public BigDecimal decrypt(BigDecimal value) {
		//	not yet implemented
		return null;
	}

	@Override
	public Timestamp encrypt(Timestamp value) {
		//	not yet implemented
		return null;
	}

	@Override
	public Timestamp decrypt(Timestamp value) {
		//	not yet implemented
		return null;
	}

	@Override
	public String getDigest(String value) {
		//	not yet implemented
		return null;
	}

	@Override
	public boolean isDigest(String value) {
		//	not yet implemented
		return false;
	}

	@Override
	public String getSHA512Hash(int iterations, String value, byte[] salt)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		//	not yet implemented
		return null;
	}
}