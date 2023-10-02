package com.ti.fabricadosaber.services;

import java.util.List;
import java.util.Optional;

import com.ti.fabricadosaber.services.exceptions.DataBindingViolationException;
import com.ti.fabricadosaber.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.ti.fabricadosaber.models.Employee;
import com.ti.fabricadosaber.repositories.EmployeeRepository;

import jakarta.transaction.Transactional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee findById(Long id) {
        Optional<Employee> employee = this.employeeRepository.findById(id);
        return employee.orElseThrow(() -> new ObjectNotFoundException(
                "Funcionário não encontrado! id: " + id + ", Tipo: " + Employee.class.getName()));
    }

    public List<Employee> listAllEmployees() {
        try {
            return this.employeeRepository.findAll();
        } catch (EmptyResultDataAccessException error) {
            throw new RuntimeException("Nenhum funcionário cadastrado", error);
        }
    }

    @Transactional
    public Employee create(Employee obj) {
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
