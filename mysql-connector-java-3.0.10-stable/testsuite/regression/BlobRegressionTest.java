/*
   Copyright (C) 2003 MySQL AB

      This program is free software; you can redistribute it and/or modify
      it under the terms of the GNU General Public License as published by
      the Free Software Foundation; either version 2 of the License, or
      (at your option) any later version.

      This program is distributed in the hope that it will be useful,
      but WITHOUT ANY WARRANTY; without even the implied warranty of
      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
      GNU General Public License for more details.

      You should have received a copy of the GNU General Public License
      along with this program; if not, write to the Free Software
      Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

 */
package testsuite.regression;

import java.sql.PreparedStatement;

import testsuite.BaseTestCase;

/**
 * Tests for blob-related regressions.
 * 
 * @author Mark Matthews
 * 
 * @version $Id: BlobRegressionTest.java,v 1.1.4.1 2003/12/12 20:46:51 mmatthew Exp $
 */
public class BlobRegressionTest extends BaseTestCase {

	/**
	 * Creates a new BlobRegressionTest.
	 * 
	 * @param name name of the test to run
	 */
	public BlobRegressionTest(String name) {
		super(name);
	}
	
	public void testUpdateLongBlobGT16M() throws Exception {
		try {
			byte[] blobData = new byte[18 * 1024 * 1024]; // 18M blob
			
			this.stmt.executeUpdate("DROP TABLE IF EXISTS testUpdateLongBlob");
			this.stmt.executeUpdate("CREATE TABLE testUpdateLongBlob(blobField LONGBLOB)");
			this.stmt.executeUpdate("INSERT INTO testUpdateLongBlob (blobField) VALUES (NULL)");
			
			PreparedStatement pStmt = this.conn.prepareStatement("UPDATE testUpdateLongBlob SET blobField=?");
			pStmt.setBytes(1, blobData);
			pStmt.executeUpdate();
		} finally {
			this.stmt.executeUpdate("DROP TABLE IF EXISTS testUpdateLongBlob");
		}
	}

}
