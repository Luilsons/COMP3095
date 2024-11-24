package ca.gbc.inventoryservice;

import ca.gbc.inventoryservice.model.Inventory;
import ca.gbc.inventoryservice.repository.InventoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class InventoryServiceApplicationTests {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Test
    void contextLoads() {
        assertThat(inventoryRepository).isNotNull();
    }

    @Test
    void testIsInStockReturnsTrueForPositiveQuantity() {
        Inventory inventory = new Inventory(null, "SKU123", 10);
        assertThat(inventory.isInStock()).isTrue();
    }

    @Test
    void testIsInStockReturnsFalseForZeroQuantity() {
        Inventory inventory = new Inventory(null, "SKU124", 0);
        assertThat(inventory.isInStock()).isFalse();
    }

    @Test
    void testIsInStockReturnsFalseForNullQuantity() {
        Inventory inventory = new Inventory(null, "SKU125", null);
        assertThat(inventory.isInStock()).isFalse();
    }

    @Test
    void testSaveInventoryToDatabase() {
        Inventory inventory = new Inventory(null, "SKU126", 50);
        Inventory savedInventory = inventoryRepository.save(inventory);

        assertThat(savedInventory).isNotNull();
        assertThat(savedInventory.getId()).isNotNull();
        assertThat(savedInventory.getSkuCode()).isEqualTo("SKU126");
        assertThat(savedInventory.getQuantity()).isEqualTo(50);
    }

    @Test
    void testUniqueConstraintOnSkuCode() {
        Inventory inventory1 = new Inventory(null, "SKU127", 20);
        Inventory inventory2 = new Inventory(null, "SKU127", 30);

        inventoryRepository.save(inventory1);

        Exception exception = null;
        try {
            inventoryRepository.save(inventory2);
        } catch (Exception e) {
            exception = e;
        }

        assertThat(exception).isNotNull();
    }
}
