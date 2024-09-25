package com.w3lsolucoes.dscommerceprinc.services;

import com.w3lsolucoes.dscommerceprinc.dto.ProductMinDTO;
import com.w3lsolucoes.dscommerceprinc.entities.Product;
import com.w3lsolucoes.dscommerceprinc.repositories.ProductRepository;
import com.w3lsolucoes.dscommerceprinc.services.exceptions.DataBaseException;
import com.w3lsolucoes.dscommerceprinc.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.w3lsolucoes.dscommerceprinc.dto.ProductDTO;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;
import java.util.Optional;


@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        //Product product = repository.findById(id).get();
        Product product = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(id));
        return new ProductDTO(product);
    }

    @Transactional(readOnly = true)
    public Page<ProductMinDTO> findAll(Pageable pageable) {
        Page<Product> productPage = repository.findAll(pageable);
        return productPage.map(ProductMinDTO::new);
    }

    @Transactional(readOnly = true)
    public Page<ProductMinDTO> findByName(String name, Pageable pageable) {
        Page<Product> productPage = repository.searchByName(name, pageable);
        return productPage.map(ProductMinDTO::new);
    }

    @Transactional
    public ProductDTO save(ProductDTO dto) {
        Product product = new Product();
        BeanUtils.copyProperties(dto, product);
        return new ProductDTO(repository.save(product));
    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO dto) {
        try {
            Product product = repository.getReferenceById(id);
            String[] ignoredProperties = {"id"};
            BeanUtils.copyProperties(dto, product, ignoredProperties);
            return new ProductDTO(repository.save(product));
        }catch (EntityNotFoundException | FatalBeanException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    @Transactional /*(propagation = Propagation.SUPPORTS)*/
    public void delete(Long id) {
        repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(id));
        try {
            repository.deleteById(id);
            repository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException("Integrity violation");
        }
    }

}