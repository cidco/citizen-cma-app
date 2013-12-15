package com.cma.pgrssystem.bean;

public class DrawerMenuBean {
	
	private int id;
	private String label;
	
	public DrawerMenuBean(int pId, String pLabel)
	{
		id = pId;
		label = pLabel;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}
	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SlidingMenuBean [id=" + id + ", "
				+ (label != null ? "label=" + label : "") + "]";
	}
	
	

}
