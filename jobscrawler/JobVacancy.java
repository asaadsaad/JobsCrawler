package jobscrawler;

public class JobVacancy {
	private String title;
	private String positionId;
	private String company;
	private String location;
	private String url;
	private String description;
	private String salary;
	private String employType;
	private String contactInfo;
	private String emailContact; // can be empty
	private String postedTime;
	private String skillSets;
	
	public JobVacancy(String title, String url, String location, String company) {
		this.title = title;
		this.url = url;
		this.location = location;
		this.company = company;
	}
	public JobVacancy(String title, String url, String location, String company,
			String empType, String salary, String postedTime) {
		this.title = title;
		this.company = company;
		this.location = location;
		this.url = url;
		this.salary = salary;
		this.employType = empType;
		this.postedTime = postedTime;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPositionId() {
		return positionId;
	}
	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getEmployType() {
		return employType;
	}
	public void setEmployType(String employType) {
		this.employType = employType;
	}
	public String getContactInfo() {
		return contactInfo;
	}
	public void setContactInfo(String contactInfo) {
		this.contactInfo = contactInfo;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getEmailContact() {
		return emailContact;
	}

	public void setEmailContact(String emailContact) {
		this.emailContact = emailContact;
	}

	public String getPostedTime() {
		return postedTime;
	}

	public void setPostedTime(String postedTime) {
		this.postedTime = postedTime;
	}
	public String getSkillSets() {
		return skillSets;
	}
	public void setSkillSets(String skillSets) {
		this.skillSets = skillSets;
	}
}
