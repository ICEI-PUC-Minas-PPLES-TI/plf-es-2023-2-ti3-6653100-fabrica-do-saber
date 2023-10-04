package com.ti.fabricadosaber.services;

import java.util.List;
import com.ti.fabricadosaber.security.UserSpringSecurity;
import com.ti.fabricadosaber.services.exceptions.DataBindingViolationException;
import com.ti.fabricadosaber.services.exceptions.ObjectNotFoundException;
import com.ti.fabricadosaber.utils.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ti.fabricadosaber.models.Employee;
import com.ti.fabricadosaber.repositories.EmployeeRepository;
import jakarta.transaction.Transactional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee findById(Long id) {
        Employee employee =
                this.employeeRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(
                "Funcionário não encontrado! id: " + id + ", Tipo: " + Employee.class.getName()));

        UserSpringSecurity userSpringSecurity = UserService.authenticated();
        SecurityUtils.checkUser(userSpringSecurity);

        return employee;
    }

    public List<Employee> listAllEmployees() {
        UserSpringSecurity userSpringSecurity = UserService.authenticated();
        SecurityUtils.checkUser(userSpringSecurity);
        List<Employee> employees = this.employeeRepository.findAll();
        if (employees.isEmpty()) {
            throw new ObjectNotFoundException("Nenhum funcionário cadastrado");
        }
        return employees;
    }


    @Transactional
    public Employee create(Employee obj) {
        UserSpringSecurity userSpringSecurity = UserService.authenticated();
        SecurityUtils.checkUser(userSpringSecurity);
        obj.setId(null);
        obj = this.employeeRepository.save(obj);
        return obj;
    }

    public Employee update(Employee obj) {

        Employee newObj = findById(obj.getId());

        BeanUtils.copyProperties(obj, newObj, "id");

        return this.employeeRepository.save(newObj);
    }

    public void delete(Long id) {
        Employee employee = findById(id);

        try {
            this.employeeRepository.delete(employee);
        } catch (Exception e) {
            throw new DataBindingViolationException("Não é possível excluir pois há entidades relacionadas");
        }
    }



}
