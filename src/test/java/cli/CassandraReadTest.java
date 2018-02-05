package cli;

import static org.junit.Assert.*;

import org.junit.Test;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

public class CassandraReadTest {
	private Cluster cluster;
	private Session session;

	@Test
	public void test() {
		CassandraReadTest client = new CassandraReadTest();
		client.connect("192.168.99.100");
		
		client.querySchema();
		client.close();
	}

	public void querySchema() {
		ResultSet results = session.execute("SELECT * FROM testrestdb.member "
				+ ";");
		for (Row row : results) {
			int user = row.getInt("id");
			String website = row.getString("email");
			String summary = row.getString("name");
			String comment = row.getString("phone_number");
			System.out.println("User: " + user);
			System.out.println("Website: " + website);
			System.out.println("Summary: " + summary);
			System.out.println("Comment: " + comment);
		}
		System.out.println();
		
	}
	
	public Session getSession() {
		return this.session;
	}

	public void connect(String node) {
		cluster = Cluster.builder().addContactPoint(node).build();
		Metadata metadata = cluster.getMetadata();
		session = cluster.connect();
	}

	public void close() {
		cluster.close();
	}

}
