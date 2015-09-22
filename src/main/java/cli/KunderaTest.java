package cli;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.jboss.tools.examples.model.Member;

import com.impetus.client.cassandra.common.CassandraConstants;

public class KunderaTest {

	public static void main(String[] args) {
		testReadOld();
	}

	public static void testReadOld() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("cassandra_pu");
        EntityManager em = emf.createEntityManager();

        List<Member> members = (List<Member>) em.createQuery("select x from Member x").getResultList();
        System.out.println(members == null ? "null" : members.size());
		for (Member m : members) {
			System.out.println("member=" + m.getEmail());
		}
        em.close();    
        emf.close();
		
	}
	
	public static void testInsertOld() {
		//key column value
		Member user = new Member();
        user.setId(new Long(0003));
        user.setName("John Smith");
        user.setEmail("johnsmith@London.com");
        user.setPhoneNumber("12345");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("cassandra_pu");
        EntityManager em = emf.createEntityManager();

        em.persist(user);
        em.close();    
        emf.close();

	}
	
	public static void testRead() {
		testInsert();
        Map<String, String> propertyMap = new HashMap<String, String>();
        propertyMap.put(CassandraConstants.CQL_VERSION, CassandraConstants.CQL_VERSION_3_0);
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("cassandra_pu", propertyMap);

        //using cassandra-cli
        //EntityManagerFactory emf = Persistence.createEntityManagerFactory("cassandra_pu");
        EntityManager em = emf.createEntityManager();

        List<Member> members = (List<Member>) em.createQuery("select x from Member x").getResultList();
        System.out.println(members == null ? "null" : members.size());
		for (Member m : members) {
			System.out.println("member=" + m.getEmail());
		}
		//        Member members = (Member) em.createQuery("SELECT x FROM Member x WHERE x.id = "+new Long(1)).getSingleResult();
		//    System.out.println(members == null ? "null" : "member="+members.getEmail());
        em.close();    
        emf.close();
		
	}
	
	public static void testInsert() {
		Member user = new Member();
        user.setId(new Long(0001));
        user.setName("John Smith");
        user.setEmail("johnsmith@London.com");
        user.setPhoneNumber("12345");
        
        Map<String, String> propertyMap = new HashMap<String, String>();
        propertyMap.put(CassandraConstants.CQL_VERSION, CassandraConstants.CQL_VERSION_3_0);
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("cassandra_pu", propertyMap);
        
        //using cassandra-cli to create keyspace
        //EntityManagerFactory emf = Persistence.createEntityManagerFactory("cassandra_pu");
        EntityManager em = emf.createEntityManager();

        em.persist(user);
        em.close();    
        emf.close();

	}
	/**
	 * 2014-08-23 07:34:52,688 [ERROR] [main] com.impetus.client.cassandra.thrift.ThriftClient  - Error while persisting record, Caused by: .
InvalidRequestException(why:Not enough bytes to read value of component 0)
	at org.apache.cassandra.thrift.Cassandra$batch_mutate_result.read(Cassandra.java:20833)
	at org.apache.thrift.TServiceClient.receiveBase(TServiceClient.java:78)
	at org.apache.cassandra.thrift.Cassandra$Client.recv_batch_mutate(Cassandra.java:964)
	at org.apache.cassandra.thrift.Cassandra$Client.batch_mutate(Cassandra.java:950)
	at com.impetus.client.cassandra.thrift.ThriftClient.onPersist(ThriftClient.java:158)
	at com.impetus.kundera.client.ClientBase.persist(ClientBase.java:88)
	at com.impetus.client.cassandra.thrift.ThriftClient.persist(ThriftClient.java:132)
	at com.impetus.kundera.lifecycle.states.ManagedState.handleFlush(ManagedState.java:183)
	at com.impetus.kundera.graph.Node.flush(Node.java:533)
	at com.impetus.kundera.persistence.PersistenceDelegator.flush(PersistenceDelegator.java:413)
	at com.impetus.kundera.persistence.PersistenceDelegator.persist(PersistenceDelegator.java:151)
	at com.impetus.kundera.persistence.EntityManagerImpl.persist(EntityManagerImpl.java:168)
	at cli.KunderaTest.testInsert(KunderaTest.java:35)
	at cli.KunderaTest.main(KunderaTest.java:14)
Exception in thread "main" com.impetus.kundera.KunderaException: com.impetus.kundera.KunderaException: InvalidRequestException(why:Not enough bytes to read value of component 0)
	at com.impetus.kundera.persistence.EntityManagerImpl.persist(EntityManagerImpl.java:174)
	at cli.KunderaTest.testInsert(KunderaTest.java:35)
	at cli.KunderaTest.main(KunderaTest.java:14)
	 */
}
