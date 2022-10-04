package com.rabinpant.repository;

import com.rabinpant.Model.Employee;
import com.rabinpant.Repository.EmployeeRepository;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DataJpaTest
public class EmployeeRepositoryTests {

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;
    @BeforeEach
    public void setUp(){
        employee = Employee.builder()
                .firstName("Rabin")
                .lastName("Pant")
                .email("pantrabin12@gmail.com")
                .build();
    }


    //junit test for save employee operation
    @Test
    @DisplayName("junit test for save employee operation")
    public void givenEmployeeObject_whenSave_thenReturnSavedEmployee(){

        //given -precondition
//        Employee employee = Employee.builder()
//                .firstName("Rabin")
//                .lastName("Pant")
//                .email("pantrabin12@gmail.com")
//                .build();
        //when - action or behaviour that we're going test
        Employee saveEmployee = employeeRepository.save(employee);

        //then - verify the output
        assertThat(saveEmployee).isNotNull();
       assertThat(saveEmployee.getId()).isGreaterThan(0);
    }
    @Test
    @DisplayName("junit test case for the operation get all employee")
    public void givenEmployeeObject_whenFindAllEmployee_thenReturnFindEmployee(){

//        Employee employee = Employee.builder()
//                .firstName("rabin")
//                .lastName("pant")
//                .email("pantrabin12@gmail.com")
//                .build();
        Employee employee2 = Employee.builder()
                .firstName("abin")
                .lastName("pant")
                .email("pantabin1@gmail.com")
                .build();

        employeeRepository.save(employee);
        employeeRepository.save(employee2);

        List<Employee> employeeList = employeeRepository.findAll();

        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);

    }
    @Test
    @DisplayName("Operation to find Employee By Id")
    public void givenEmployeeObject_whenFindById_thenReturnEmployeeObject(){
        //given
//        Employee employee = Employee.builder()
//                .firstName("rajeev")
//                .lastName("neupane")
//                .email("rajneu12@gmail.com")
//                .build();

        Employee employee2 = Employee.builder()
                .firstName("rabin")
                .lastName("pant")
                .email("pantrabin12@gmail.com")
                .build();

        Employee employee3 = Employee.builder()
                .firstName("prajwol")
                .lastName("neupane")
                .email("praneu12@gmail.com")
                .build();


        employeeRepository.save(employee);
        employeeRepository.save(employee2);
        employeeRepository.save(employee3);

        //when
        Employee byId = employeeRepository.findById(employee.getId()).get();

        //then
        assertThat(byId).isNotNull();
    }
    @Test
    @DisplayName("Junit test case for the find by email")
    public void givenEmployeeObject_whenFindByEmail_thenReturnEmployeeObject(){

        //given
//        Employee employee1 = Employee.builder()
//                .firstName("rajeev")
//                .lastName("neupane")
//                .email("rajneu12@gmail.com")
//                .build();

        employeeRepository.save(employee);

        //when
        Employee employee2 = employeeRepository.findByEmail(employee.getEmail()).get();

        //then
        assertThat(employee2).isNotNull();
    }

    @Test
    @DisplayName("Junit test case for the update employee")
    public void givenEmployeeObject_whenUpdate_thenReturnNewEmployeeObject(){

        //given
//        Employee employee1 = Employee.builder()
//                .firstName("rabin")
//                .lastName("pant")
//                .email("rabpan12@gmail.com")
//                .build();

        Employee updateEmployee = Employee.builder()
                .firstName("abin")
                .lastName("ant")
                .email("rapa12@gmail.com")
                .build();

        employeeRepository.save(employee);

        //when
        Employee findEmpDb = employeeRepository.findById(employee.getId()).get();
        findEmpDb.setEmail(updateEmployee.getEmail());
        findEmpDb.setFirstName(updateEmployee.getFirstName());
        findEmpDb.setLastName(updateEmployee.getLastName());

        employeeRepository.save(findEmpDb);

        //then

        assertThat(findEmpDb.getFirstName()).isEqualTo("abin");
    }

    @Test
    @DisplayName("Junit test case for the delete operation")
    public void givenEmployeeObject_whenDelete_thenReturnDeleteObject(){
        //given
        Employee employee1 = Employee.builder()
                .firstName("rabin")
                .lastName("pant")
                .email("rabpan12@gmail.com")
                .build();
        employeeRepository.save(employee1);
        //when

        employeeRepository.delete(employee1);
        List<Employee> allEmp = employeeRepository.findAll();

        //then
        assertThat(allEmp.size()).isEqualTo(0);

    }
    @Test
    @DisplayName("JUnit test case for the custom JPQL index")
    public void givenEmployeeObject_whenFindByFirstAndLastName_thenReturnEmpObject(){
        //given
        Employee employee1 = Employee.builder()
                .firstName("rabin")
                .lastName("pant")
                .email("rabpan12@gmail.com")
                .build();
        employeeRepository.save(employee1);

        //when
        Employee byJPQLEmp = employeeRepository.findByJPQL(employee1.getFirstName(), employee1.getLastName());

        //then

        assertThat(byJPQLEmp.getFirstName()).isEqualTo("rabin");

    }

    @Test
    @DisplayName("Junit test case for the JPQL named parameter")
    public void givenFirstNameAndLastName_whenFindByJPQLNamedParamas_thenReturnEmployeeObject(){

        Employee employee = Employee.builder()
                .firstName("rabin")
                .lastName("pant")
                .email("pantrabin12@gmail.com")
                .build();

        employeeRepository.save(employee);

        String fistName = "rabin";
        String lastName = "pant";

        Employee byJPQLNamed = employeeRepository.findByJPQLNamed(fistName, lastName);

        assertThat(byJPQLNamed).isNotNull();
    }
    @Test
    @DisplayName("JUnit test case for the native index param")
    public void givenFirstNameAndLastName_whenFindByJPQLNative_thenReturnEmpObject(){

        Employee employee = Employee.builder()
                .firstName("rabin")
                .lastName("pant")
                .email("pantrabin12@gmail.com")
                .build();

        employeeRepository.save(employee);

        Employee byJPQLNamed = employeeRepository.findByNativeSQL(employee.getFirstName(), employee.getLastName());

        assertThat(byJPQLNamed).isNotNull();
    }
}
