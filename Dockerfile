FROM jboss/wildfly
ADD target/cassandrawebapp.war /opt/jboss/wildfly/standalone/deployments/