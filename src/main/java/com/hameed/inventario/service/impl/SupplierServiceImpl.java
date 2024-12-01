package com.hameed.inventario.service.impl;

import com.hameed.inventario.exception.ResourceNotFoundException;
import com.hameed.inventario.mapper.SupplierMapper;
import com.hameed.inventario.model.dto.basic.SupplierDTO;
import com.hameed.inventario.model.entity.Supplier;
import com.hameed.inventario.repository.SupplierRepository;
import com.hameed.inventario.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;

    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository,
                               SupplierMapper supplierMapper) {
        this.supplierRepository = supplierRepository;
        this.supplierMapper = supplierMapper;
    }

    @Override
    public SupplierDTO addSupplier(SupplierDTO supplierDTO) {
        Supplier supplier = supplierMapper.supplierDTOToSupplier(supplierDTO);
        Supplier resultSupplier = supplierRepository.save(supplier);
        return supplierMapper.supplierToSupplierDTO(resultSupplier);
    }

    @Override
    @Transactional
    public SupplierDTO updateSupplier(Long supplierId, SupplierDTO supplierDTO) {

        Optional<Supplier> optionalSupplier = supplierRepository.findById(supplierId);
        if(optionalSupplier.isPresent()) {
            Supplier supplier = optionalSupplier.get();
            // map fields of dto to supplier
            supplier.setSupplierName(supplierDTO.getSupplierName());
            supplier.setContactName(supplierDTO.getContactName());
            supplier.setContactPhone(supplierDTO.getContactPhone());
            supplier.setEmail(supplierDTO.getEmail());
            supplier.setAddress(supplierDTO.getAddress());

            // save
            Supplier resultSupplier = supplierRepository.save(supplier);

            // return the updated DTO
            return supplierMapper.supplierToSupplierDTO(resultSupplier);
        } else {
            throw new ResourceNotFoundException("Supplier with this Id: " + supplierId + " could not be found");
        }
    }

    @Override
    @Transactional
    public void deleteSupplier(Long supplierId) {
        supplierRepository.findById(supplierId).ifPresentOrElse(
                supplier -> {
                    // handling the link with other entities before deleting
                    supplier.getPurchaseOrders().forEach(purchaseOrder -> purchaseOrder.setSupplier(null));
                    supplier.getProducts().forEach(product -> product.removeSupplier(supplier));

                    supplierRepository.delete(supplier);
                },
                () -> {
                    throw new ResourceNotFoundException("Supplier with this Id: " + supplierId + " could not be found");
                }
        );
    }

    @Override
    public SupplierDTO getSupplierById(Long supplierId) {
        Supplier supplier = getSupplierEntityById(supplierId);
        return supplierMapper.supplierToSupplierDTO(supplier);
    }

    @Override
    public Page<SupplierDTO> getAllSuppliers(Pageable pageable) {
        Page<Supplier> pageSuppliers = supplierRepository.findAll(pageable);
        return pageSuppliers.map(supplierMapper::supplierToSupplierDTO);
    }

    @Override
    public Supplier getSupplierEntityById(Long supplierId) {
        return supplierRepository.findById(supplierId).orElseThrow(() -> new ResourceNotFoundException("Supplier with this Id: " + supplierId + " could not be found"));
    }
}
