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

import org.erpya.base.util.Env;
import org.erpya.base.util.LogM;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.logging.Level;

/**
 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpya.com Aug 18, 2015, 12:50:07 AM
 *
 */
public class KeyStoreHandler {
	
	/**
	 * Load Public Key from File
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpya.com
	 * @param algorithm
	 * @param input
	 * @return
	 * @return Key
	 * @throws IOException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeySpecException 
	 */
	public static Key loadPublicKeyEx(String algorithm, InputStream input) throws IOException,
						NoSuchAlgorithmException, InvalidKeySpecException {
		//	Get Public Key from file
		byte[] encodedPKey = new byte[(int) input.available()];
		input.read(encodedPKey);
		input.close();
			
		// Generate KeyPair.
		KeyFactory keyF = KeyFactory.getInstance(algorithm);
		X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(
				encodedPKey);
		PublicKey publicKey = keyF.generatePublic(publicKeySpec);
		//	Return
		return publicKey;
	}
	
	/**
	 * Load Public Key without throws
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpya.com
	 * @param algorithm
	 * @param input
	 * @return
	 * @return Key
	 */
	public static Key loadPublicKey(String algorithm, InputStream input) {
		try {
			return loadPublicKeyEx(algorithm, input);
		} catch (IOException e) {
			LogM.log(Env.getContext(), KeyStoreHandler.class, Level.SEVERE, "loadPublicKey()", e);
		} catch (NoSuchAlgorithmException e) {
			LogM.log(Env.getContext(), KeyStoreHandler.class, Level.SEVERE, "loadPublicKey()", e);
		} catch (InvalidKeySpecException e) {
			LogM.log(Env.getContext(), KeyStoreHandler.class, Level.SEVERE, "loadPublicKey()", e);
		} catch (Exception e) {
			LogM.log(Env.getContext(), KeyStoreHandler.class, Level.SEVERE, "loadPublicKey()", e);
		}
		//	Default
		return null;
	}
	
	/**
	 * Save Public Key
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpya.com
	 * @param publicKey
	 * @param filePath
	 * @throws IOException
	 * @return void
	 */
	public static void savePublicKeyEx(Key publicKey, String filePath) throws IOException {
		// Store Public Key.
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(
				publicKey.getEncoded());
		FileOutputStream fos = new FileOutputStream(filePath);
		fos.write(x509EncodedKeySpec.getEncoded());
		fos.close();
	}
	
	/**
	 * Save Public Key without throws
	 * @author Yamel Senih, ysenih@erpcya.com, ERPCyA http://www.erpya.com
	 * @param publicKey
	 * @param filePath
	 * @return void
	 */
	public static void savePublicKey(Key publicKey, String filePath) {
		try {
			savePublicKeyEx(publicKey, filePath);
		} catch (IOException e) {
			LogM.log(Env.getContext(), KeyStoreHandler.class, Level.SEVERE, "savePublicKey(Key)", e);
		} catch (Exception e) {
			LogM.log(Env.getContext(), KeyStoreHandler.class, Level.SEVERE, "savePublicKey(Key)", e);
		}
	}
}