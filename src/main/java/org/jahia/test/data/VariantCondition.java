package org.jahia.test.data;

public class VariantCondition
{
	private String variant;
	private String fallback;
	private String category;
	private String subCategory;
	private String subSubCategory;
	private String conditionName;
	private String conditionValue;
	private String subConditionName;
	private String subConditionValue;
	private String lastConditionName;
	private String lastConditionValue;
	private String conjunction;

	public VariantCondition(String variant, String fallback, String category, String subCategory,
			String subSubCategory, String conditionName, String conditionValue,
			String subConditionName, String subConditionValue, String lastConditionName,
			String lastConditionValue, String conjunction)
	{
		this.setVariant(variant);
		this.setFallback(fallback);
		this.setCategory(category);
		this.setSubCategory(subCategory);
		this.setSubSubCategory(subSubCategory);
		this.setConditionName(conditionName);
		this.setConditionValue(conditionValue);
		this.setSubConditionName(subConditionName);
		this.setSubConditionValue(subConditionValue);
		this.setLastConditionName(lastConditionName);
		this.setLastConditionValue(lastConditionValue);
		this.setConjunction(conjunction);
	}

	public String getVariant()
	{
		return variant;
	}

	public void setVariant(String variant)
	{
		this.variant = variant;
	}

	public String getFallback()
	{
		return fallback;
	}

	public void setFallback(String fallback)
	{
		this.fallback = fallback;
	}

	public String getCategory()
	{
		return category;
	}

	public void setCategory(String category)
	{
		this.category = category;
	}

	public String getConditionValue()
	{
		return conditionValue;
	}

	public void setConditionValue(String conditionValue)
	{
		this.conditionValue = conditionValue;
	}

	public String getConditionName()
	{
		return conditionName;
	}

	public void setConditionName(String conditionName)
	{
		this.conditionName = conditionName;
	}

	public String getSubCategory()
	{
		return subCategory;
	}

	public void setSubCategory(String subCategory)
	{
		this.subCategory = subCategory;
	}

	public String getSubConditionValue()
	{
		return subConditionValue;
	}

	public void setSubConditionValue(String subConditionValue)
	{
		this.subConditionValue = subConditionValue;
	}

	public String getSubConditionName()
	{
		return subConditionName;
	}

	public void setSubConditionName(String subConditionName)
	{
		this.subConditionName = subConditionName;
	}

	public String getLastConditionName()
	{
		return lastConditionName;
	}

	public void setLastConditionName(String lastConditionName)
	{
		this.lastConditionName = lastConditionName;
	}

	public String getLastConditionValue()
	{
		return lastConditionValue;
	}

	public void setLastConditionValue(String lastConditionValue)
	{
		this.lastConditionValue = lastConditionValue;
	}

	public String getConjunction()
	{
		return conjunction;
	}

	public void setConjunction(String conjunction)
	{
		this.conjunction = conjunction;
	}

	public String getSubSubCategory()
	{
		return subSubCategory;
	}

	public void setSubSubCategory(String subSubCategory)
	{
		this.subSubCategory = subSubCategory;
	}

}