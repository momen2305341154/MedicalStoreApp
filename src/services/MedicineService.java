
package services;

import dao.MedicineDAO;
import models.Medicine;

public class MedicineService {
    private MedicineDAO medicineDAO = new MedicineDAO();

    public void addMedicine(Medicine medicine) {
        medicineDAO.saveMedicine(medicine);
    }

    public Medicine getMedicineById(String id) {
        return medicineDAO.getMedicineById(id);
    }

    public void updateMedicine(Medicine medicine) {
        medicineDAO.updateMedicine(medicine);
    }

    public void deleteMedicine(String id) {
        medicineDAO.deleteMedicine(id);
    }
}
