package org.mass.framework.generator.translate;

/**
 * 表字段类
 * @author Administrator
 *
 */
public class ColumnData {

	private String columnName;
	private String dataType;
	private String columnComment;
	private String colField;
	private boolean isPrimaryKey;
	public String getColField() {
		return colField;
	}
	public void setColField(String colField) {
		this.colField = colField;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getColumnComment() {
		return columnComment;
	}
	public void setColumnComment(String columnComment) {
		this.columnComment = columnComment;
	}
	public boolean isPrimaryKey() {
		return isPrimaryKey;
	}
	public void setPrimaryKey(boolean isPrimaryKey) {
		this.isPrimaryKey = isPrimaryKey;
	}

}
