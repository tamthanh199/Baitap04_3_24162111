package vn.hcmute.webpr330479.config;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import vn.hcmute.webpr330479.models.Category;
public class JPATest{
	public static void main(String[] args){
		EntityManager enma=JPAConfig.getEntityManager();
		EntityTransaction trans=enma.getTransaction();
		Category category=new Category();
		category.setName("Test JPA");
		category.setIcon("test.jpg");
		try{
			trans.begin();
			enma.persist(category);
			trans.commit();
			System.out.println("TEST JPA THANH CONG");
			System.out.println("ID vua tao: "+category.getId());
		}catch(Exception e){
			e.printStackTrace();
			if(trans.isActive()){
				trans.rollback();
			}
			throw e;
		}finally{
			enma.close();
		}
	}
}