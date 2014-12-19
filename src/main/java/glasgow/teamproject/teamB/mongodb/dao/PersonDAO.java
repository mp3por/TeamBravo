package glasgow.teamproject.teamB.mongodb.dao;

import glasgow.teamproject.teamB.mongodb.model.Person;

public interface PersonDAO {
	public void create(Person p);
    
    public Person readById(String id);
     
    public void update(Person p);
     
    public int deleteById(String id);
}
