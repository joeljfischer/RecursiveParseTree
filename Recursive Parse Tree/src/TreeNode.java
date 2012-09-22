/**
 * 
 */

/**
 * @author jfischer
 *
 */
public class TreeNode {
	public TreeNode leftChild = null;
	public TreeNode rightChild = null;
	public TreeNode parent = null;
	public String data = null;
	public String dataType = null;
	public int numCreated = 0;
	
	public TreeNode(TreeNode parent, String data, int numCreated) {
		this.data = data;
		this.parent = parent;
		this.numCreated = numCreated;
	}
}
