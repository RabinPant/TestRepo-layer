package com.rabinpant.Repository;

import com.rabinpant.Model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmail(String email);

//    define the custom quesry using JPQL index parameter
    @Query("select e from Employee e where e.firstName=?1 and e.lastName=?2")
    Employee findByJPQL(String firstName, String lastName);

    //name parameter
    @Query("select e from Employee e where e.firstName=:firstName and e.lastName=:lastName")
    Employee findByJPQLNamed(@Param("firstName") String firstName, @Param("lastName") String lastName);


    //custom query using native sql with index params
    @Query(value = "select * from employees e where e.first_Name=?1 and e.last_Name=?2",nativeQuery = true)
    Employee findByNativeSQL(String firstName, String lastName);
}
