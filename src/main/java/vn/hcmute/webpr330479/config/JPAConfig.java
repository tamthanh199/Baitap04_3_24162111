package vn.hcmute.webpr330479.config;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
public class JPAConfig{
	private static final EntityManagerFactory factory=Persistence.createEntityManagerFactory("jpa-hibernate-mysql");
	public static EntityManager getEntityManager(){
		return factory.createEntityManager();
	}
}