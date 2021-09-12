package gloridifice.watersource.common.item;

public class WaterBagItem extends DrinkContainerItem {
    public WaterBagItem(String name, Properties properties, int capacity) {
        super(name, properties.durability(capacity), capacity);
    }
}
