package vn.hcmute.webpr330479.dao.impl;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import vn.hcmute.webpr330479.config.JPAConfig;
import vn.hcmute.webpr330479.dao.CategoryDao;
import vn.hcmute.webpr330479.models.Category;
public class CategoryDaoImpl implements CategoryDao{
	@Override
	public List<Category> getAll(){
		EntityManager enma=JPAConfig.getEntityManager();
		try{
			return enma.createNamedQuery("Category.findAll", Category.class).getResultList();
		}finally{
			enma.close();
		}
	}
	@Override
	public Category getById(int id){
		EntityManager enma=JPAConfig.getEntityManager();
		try{
			return enma.find(Category.class, id);
		}finally{
			enma.close();
		}
	}
	@Override
	public void insert(Category category){
		EntityManager enma=JPAConfig.getEntityManager();
		EntityTransaction trans=enma.getTransaction();
		try{
			trans.begin();
			enma.persist(category);
			trans.commit();
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
	@Override
	public void update(Category category){
		EntityManager enma=JPAConfig.getEntityManager();
		EntityTransaction trans=enma.getTransaction();
		try{
			trans.begin();
			enma.merge(category);
			trans.commit();
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
	@Override
	public void delete(int id){
		EntityManager enma=JPAConfig.getEntityManager();
		EntityTransaction trans=enma.getTransaction();
		try{
			trans.begin();
			Category category=enma.find(Category.class, id);
			if(category!=null){
				enma.remove(category);
			}
			trans.commit();
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