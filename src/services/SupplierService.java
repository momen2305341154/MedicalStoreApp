package services;

import dao.SupplierDAO;
import models.Supplier;

public class SupplierService {
    private SupplierDAO supplierDAO = new SupplierDAO();

    public void addSupplier(Supplier supplier) {
        supplierDAO.saveSupplier(supplier);
    }

    public Supplier getSupplierById(String id) {
        return supplierDAO.getSupplierById(id);
    }

    public void updateSupplier(Supplier supplier) {
        supplierDAO.updateSupplier(supplier);
    }

    public void deleteSupplier(String id) {
        supplierDAO.deleteSupplier(id);
    }
}

