package jobscrawler;

public class JobVacancy {
	private String title;
	private String positionId;
	private String company;
	private String location;
	private String url;
	private String description;
	private String skills;
	private String employType;
	private String payRate; // salary range
	private String datePost;
	private String contactInfo;
	private String emailContact; // can be empty
	
	public JobVacancy(String title, String positionId, String company,
			String location, String url, String description, String skills,
			String employType, String payRate, String datePost,
			String contactInfo) {
		this.title = title;
		this.positionId = positionId;
		this.company = company;
		this.location = location;
		this.url = url;
		this.description = description;
		this.skills = skills;
		this.employType = employType;
		this.payRate = payRate;
		this.datePost = datePost;
		this.contactInfo = contactInfo;
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
	public String getSkills() {
		return skills;
	}
	public void setSkills(String skills) {
		this.skills = skills;
	}
	public String getEmployType() {
		return employType;
	}
	public void setEmployType(String employType) {
		this.employType = employType;
	}
	public String getPayRate() {
		return payRate;
	}
	public void setPayRate(String payRate) {
		this.payRate = payRate;
	}
	public String getDatePost() {
		return datePost;
	}
	public void setDatePost(String datePost) {
		this.datePost = datePost;
	}
	public String getContactInfo() {
		return contactInfo;
	}
	public void setContactInfo(String contactInfo) {
		this.contactInfo = contactInfo;
	}
}
