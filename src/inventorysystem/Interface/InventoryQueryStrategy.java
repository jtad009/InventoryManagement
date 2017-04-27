package inventorysystem.Interface;

/**
 *Interface to enable injection of query strategies
 * since we could be required to find either expired product or not expired products
 * Plan is to eliminate sub-classing
 * @author Israel Edet
 */
public interface InventoryQueryStrategy {

    /**
     *Lowest quantity per item
     */
    public int THRESHOLD = 4;
    /**
     *Get all products which could be just expired
     * Not expired or a combination of both
     * @return string representing strategy to apply
     */
    String run();
}
