
package org.jboss.tools.examples.data;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jboss.tools.examples.model.Member;

import com.impetus.client.cassandra.common.CassandraConstants;

@ApplicationScoped
public class MemberRepository {

    //@PersistenceContext(unitName = "cassandra_pu", type = PersistenceContextType.EXTENDED)
    //@PersistenceContext
	@Inject
    private EntityManager em;

    public MemberRepository() {
    	/*Map<String, String> propertyMap = new HashMap<String, String>();
        propertyMap.put(CassandraConstants.CQL_VERSION, CassandraConstants.CQL_VERSION_3_0);
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("cassandra_pu", propertyMap);

        em = emf.createEntityManager();*/
    }
    
    public Member findById(Long id) {
        return em.find(Member.class, id);
    }

    public Member findByEmail(String email) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Member> criteria = cb.createQuery(Member.class);
        Root<Member> member = criteria.from(Member.class);
        // Swap criteria statements if you would like to try out type-safe criteria queries, a new
        // feature in JPA 2.0
        // criteria.select(member).where(cb.equal(member.get(Member_.email), email));
        criteria.select(member).where(cb.equal(member.get("email"), email));
        return em.createQuery(criteria).getSingleResult();
    }

    public List<Member> findAllOrderedByName() {    	
    	Query query = em.createQuery("SELECT x from Member x");
    	return query.getResultList();
    }
    /**
     *org.apache.commons.lang.NotImplementedException: Criteria Query currently not supported by Kundera
	com.impetus.kundera.persistence.EntityManagerFactoryImpl.getCriteriaBuilder(EntityManagerFactoryImpl.java:257)
	com.impetus.kundera.persistence.EntityManagerImpl.getCriteriaBuilder(EntityManagerImpl.java:793)
	org.jboss.tools.examples.data.MemberRepository.findAllOrderedByName(MemberRepository.java:49)
	org.jboss.tools.examples.data.MemberRepository$Proxy$_$$_WeldClientProxy.findAllOrderedByName(MemberRepository$Proxy$_$$_WeldClientProxy.java)
	org.jboss.tools.examples.data.MemberListProducer.retrieveAllMembersOrderedByName(MemberListProducer.java:37)
	sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method) 
     */
    
    /**
     * 
     * @param member
     */
    public void delete(Member member) {
    	em.remove(member);
    	
    }

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}
    
}
