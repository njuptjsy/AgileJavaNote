package sis.studentinfo;

public class Course {//用于构造简单的数据对象
	private String department;
	private String number;
	
	public Course (String department, String number){
		this.department = department;
		this.number = number;
	}
	
	public String getDepartment(){
		return department;
	}
	
	public String getNumber(){
		return number;
	}
	
	@Override
	public boolean equals(Object object){
		if (object == null) {//防卫语句：防止nullpoint异常
			return false;
		}
//		if (this.getClass() != object.getClass()) {//防卫语句：防止传入非Course类型
//			return false;//类常量是唯一的所以可是使用 ！= 或者equals
//		}
		if (!(object instanceof Course)) {//判断对象是否是目标类的实例，如果需要在继承的层次上不考虑层次位置的比较对象
			return false;
		}
		Course that = (Course)object;
		return this.department.equals(that.department) && this.number.equals(that.number);
	}
	
	@Override
	public int hashCode(){
		final int hashMultiplier = 41;
		int result = 7;
		result = result * hashMultiplier + department.hashCode();
		result = result * hashMultiplier + number.hashCode();
		return result;
	}
}
