package cli;

import static org.junit.Assert.*;

import org.junit.Test;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.Session;

public class CassandraInsertTest {
	private Cluster cluster;
	private Session session;

	@Test
	public void test() {
		CassandraInsertTest client = new CassandraInsertTest();
		client.connect("192.168.99.100");
		client.createSchema();
		client.loadData();
	}

	public void createSchema() {
		session.execute("CREATE KEYSPACE IF NOT EXISTS testrestdb WITH replication "
				+ "= {'class':'SimpleStrategy', 'replication_factor':3};");
		session.execute("CREATE TABLE IF NOT EXISTS testrestdb.member ("
				+ "id int PRIMARY KEY," + "email text," + "name text,"
				+ "phone_number text" + ");");
	}

	public void loadData() {
		session.execute("INSERT INTO testrestdb.member (id, email, name, phone_number) "
				+ "VALUES ("
				+ "1,"
				+ "'hello@example.com',"
				+ "'secondmember'," + "'0123456789'" + ");");
		session.execute("INSERT INTO testrestdb.member (id, email, name, phone_number) "
				+ "VALUES ("
				+ "2,"
				+ "'jay.chou@mailnator.com',"
				+ "'jaychousupdate'," + "'3334445550'" + ");");
	}

	public Session getSession() {
		return this.session;
	}

	public void connect(String node) {
		cluster = Cluster.builder().addContactPoint(node).build();
		Metadata metadata = cluster.getMetadata();
		System.out.printf("Connected to cluster: %s\n",
				metadata.getClusterName());
		for (Host host : metadata.getAllHosts()) {
			System.out.printf("Datacenter: %s; Host: %s; Rack: %s\n",
					host.getDatacenter(), host.getAddress(), host.getRack());
		}
		session = cluster.connect();
	}

	public void close() {
		cluster.close();
	}
}
