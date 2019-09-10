package az.sufilter.bpm.controller;

import az.sufilter.bpm.entity.*;
import az.sufilter.bpm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/test")
public class TestController extends SkeletonController {
    @Autowired
    UserRepository userRepository;

    @GetMapping(value = "/ttt")
    public User getUser1 () {
        List<UserModuleOperation> userModuleOperations = userModuleOperationRepository.findAll();
        List<ModuleOperation> moduleOperations = moduleOperationRepository.findAll();
        List<Module> modules = moduleRepository.findAll();
        List<Organization> organizations = organizationRepository.findAll();
        List<Dictionary> dictionaries = dictionaryRepository.findAll();
        List<DictionaryType> dictionaryTypes = dictionaryTypeRepository.findAll();
        List<Operation> operations = operationRepository.findAll();
        return null;
    }

    @GetMapping(value = "/get1")
    public User getUser () {
        Optional<User> user = userRepository.findById(1);
        return null;
    }

    @GetMapping(value = "/get3")
    public ResponseEntity<List<Dictionary>> getLayout () {
        List<Dictionary> employees = dictionaryRepository.findAll();
        return null;
    }

    @GetMapping(value = "/employee")
    public List<Employee> getEmployee () {
        List<Employee> list = employeeRepository.findAll();
        return list;
    }

    @GetMapping(value = "/person")
    public List<Person> getPerson () {
        List<Person> list = personRepository.findAll();
        return list;
    }

    @GetMapping(value = "/dictionary")
    public List<Dictionary> getDictionary () {
        List<Dictionary> list = dictionaryRepository.findAll();
        return list;
    }
}
