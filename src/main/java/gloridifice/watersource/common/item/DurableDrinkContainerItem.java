package gloridifice.watersource.common.item;



public class DurableDrinkContainerItem extends DrinkContainerItem {
    public DurableDrinkContainerItem(String name, Properties properties, int capacity) {
        super(name, properties.durability(capacity).defaultDurability(0), capacity);
    }
}
